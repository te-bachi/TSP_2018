package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.OptionalInt;

public class AntColonyOptimization implements Solver {
    private double      c               = 1.0;      /* original number of trails */
    private double      alpha           = 1;        /* pheromone importance */
    private double      beta            = 5;        /* distance priority, beta > alpha */
    private double      evaporation     = 0.5;      /* evaporating in every iteration */
    private double      Q               = 500;      /* total amount of pheromone left on the trail by each Ant */
    private double      antFactor       = 0.8;      /* how many ants used per city */
    private double      randomFactor    = 0.01;     /* randomness */

    private int         maxIterations   = 1000;

    private int         numberOfCities;
    private int         numberOfAnts;
    private double      distance[][];
    private double      trails[][];
    private List<Ant>   ants            = new ArrayList<>();
    private Random      random          = new Random();
    private double      probabilities[];

    private int         currentIndex;

    private int[]       bestTourOrder;
    private double      bestTourLength;

    @Override
    public List<Point> solve(Instance instance) {
        List<Point> points = instance.clonePointList();
        int n = points.size();

        distance = new double[n][n];
        generateDistanceMatrix(points);

        numberOfCities = distance.length;
        numberOfAnts = (int) (numberOfCities * antFactor);

        trails = new double[numberOfCities][numberOfCities];
        probabilities = new double[numberOfCities];
        IntStream.range(0, numberOfAnts)
                .forEach(i -> ants.add(new Ant(numberOfCities)));

        startAntOptimization();
        return null;
    }

    /*---------------------------------------------------------*/

    public class Ant {

        protected int trailSize;
        protected int trail[];
        protected boolean visited[];

        public Ant(int tourSize) {
            this.trailSize = tourSize;
            this.trail = new int[tourSize];
            this.visited = new boolean[tourSize];
        }

        protected void visitCity(int currentIndex, int city) {
            trail[currentIndex + 1] = city;
            visited[city] = true;
        }

        protected boolean visited(int i) {
            return visited[i];
        }

        protected double trailLength(double graph[][]) {
            double length = graph[trail[trailSize - 1]][trail[0]];
            for (int i = 0; i < trailSize - 1; i++) {
                length += graph[trail[i]][trail[i + 1]];
            }
            return length;
        }

        protected void clear() {
            for (int i = 0; i < trailSize; i++)
                visited[i] = false;
        }
    }

    /*---------------------------------------------------------*/

    public AntColonyOptimization() {

    }

    public void generateDistanceMatrix(List<Point> points) {
        int i;
        int k;
        for (i = 0; i < points.size(); i++) {
            for (k = 0; k < points.size(); k++) {
                distance[i][k] = Utils.euclideanDistance2D(points.get(i), points.get(k));
            }
        }
    }

    /**
     * Generate initial solution
     */
    public double[][] generateRandomMatrix(int n) {
        double[][] randomMatrix = new double[n][n];
        IntStream.range(0, n)
                .forEach(i -> IntStream.range(0, n)
                        .forEach(j -> randomMatrix[i][j] = Math.abs(random.nextInt(100) + 1)));
        return randomMatrix;
    }

    /**
     * Perform ant optimization
     */
    public void startAntOptimization() {
        IntStream.rangeClosed(1, 3)
                .forEach(i -> {
                    System.out.println("Attempt #" + i);
                    solve();
                });
    }

    /**
     * Use this method to run the main logic
     */
    public int[] solve() {
        setupAnts();
        clearTrails();
        IntStream.range(0, maxIterations)
                .forEach(i -> {
                    moveAnts();
                    updateTrails();
                    updateBest();
                });
        System.out.println("Best tour length: " + (bestTourLength - numberOfCities));
        System.out.println("Best tour order: " + Arrays.toString(bestTourOrder));
        return bestTourOrder.clone();
    }

    /**
     * Prepare ants for the simulation
     */
    private void setupAnts() {
        IntStream.range(0, numberOfAnts)
                .forEach(i -> {
                    ants.forEach(ant -> {
                        ant.clear();
                        ant.visitCity(-1, random.nextInt(numberOfCities));
                    });
                });
        currentIndex = 0;
    }

    /**
     * At each iteration, move ants
     */
    private void moveAnts() {
        IntStream.range(currentIndex, numberOfCities - 1)
                .forEach(i -> {
                    ants.forEach(ant -> ant.visitCity(currentIndex, selectNextCity(ant)));
                    currentIndex++;
                });
    }

    /**
     * Select next city for each ant
     */
    private int selectNextCity(Ant ant) {
        int t = random.nextInt(numberOfCities - currentIndex);
        if (random.nextDouble() < randomFactor) {
            OptionalInt cityIndex = IntStream.range(0, numberOfCities)
                    .filter(i -> i == t && !ant.visited(i))
                    .findFirst();
            if (cityIndex.isPresent()) {
                return cityIndex.getAsInt();
            }
        }
        calculateProbabilities(ant);
        double r = random.nextDouble();
        double total = 0;
        for (int i = 0; i < numberOfCities; i++) {
            total += probabilities[i];
            if (total >= r) {
                return i;
            }
        }

        throw new RuntimeException("There are no other cities");
    }

    /**
     * Calculate the next city picks probabilites
     */
    public void calculateProbabilities(Ant ant) {
        int i = ant.trail[currentIndex];
        double pheromone = 0.0;
        for (int l = 0; l < numberOfCities; l++) {
            if (!ant.visited(l)) {
                pheromone += Math.pow(trails[i][l], alpha) * Math.pow(1.0 / distance[i][l], beta);
            }
        }
        for (int j = 0; j < numberOfCities; j++) {
            if (ant.visited(j)) {
                probabilities[j] = 0.0;
            } else {
                double numerator = Math.pow(trails[i][j], alpha) * Math.pow(1.0 / distance[i][j], beta);
                probabilities[j] = numerator / pheromone;
            }
        }
    }

    /**
     * Update trails that ants used
     */
    private void updateTrails() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                trails[i][j] *= evaporation;
            }
        }
        for (Ant a : ants) {
            double contribution = Q / a.trailLength(distance);
            for (int i = 0; i < numberOfCities - 1; i++) {
                trails[a.trail[i]][a.trail[i + 1]] += contribution;
            }
            trails[a.trail[numberOfCities - 1]][a.trail[0]] += contribution;
        }
    }

    /**
     * Update the best solution
     */
    private void updateBest() {
        if (bestTourOrder == null) {
            bestTourOrder = ants.get(0).trail;
            bestTourLength = ants.get(0)
                    .trailLength(distance);
        }
        for (Ant a : ants) {
            if (a.trailLength(distance) < bestTourLength) {
                bestTourLength = a.trailLength(distance);
                bestTourOrder = a.trail.clone();
            }
        }
    }

    /**
     * Clear trails after simulation
     */
    private void clearTrails() {
        IntStream.range(0, numberOfCities)
                .forEach(i -> {
                    IntStream.range(0, numberOfCities)
                            .forEach(j -> trails[i][j] = c);
                });
    }
}
