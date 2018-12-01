package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

import java.util.List;

public class SimulatedAnnealing {

    // Calculate the acceptance probability
    public static double acceptanceProbability(double currentDistance, double newDistance, double temperature) {
        // If the new solution is better, accept it
        if (newDistance < currentDistance) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((currentDistance - newDistance) / temperature);
    }

    public static List<Point> solve(Instance instance) {
        // Set initial temp
        double temp = 100;

        // Cooling rate
        double coolingRate = 0.01;

        // Initialize intial solution
        List<Point> currentSolution = NearestNeighbor.solve(instance);
        List<Point> newSolution;

        System.out.println("Initial solution distance: " + Utils.euclideanDistance2D(currentSolution));

        // Set as current best
        List<Point> best = Utils.clonePointList(currentSolution);

        // Loop until system has cooled
        while (temp > 0.1) {

            newSolution = Utils.clonePointList(currentSolution);

            // Get a random positions in the tour
            int pos1 = (int) (newSolution.size() * Math.random());
            int pos2 = (int) (newSolution.size() * Math.random());

            // Get the cities at selected positions in the tour
            Point swap1 = newSolution.get(pos1);
            Point swap2 = newSolution.get(pos2);

            // Swap them
            newSolution.set(pos2, swap1);
            newSolution.set(pos1, swap2);

            // Get energy of solutions
            double currentDistance = Utils.euclideanDistance2D(currentSolution);
            double newDistance     = Utils.euclideanDistance2D(newSolution);

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentDistance, newDistance, temp) > Math.random()) {
                System.out.println("accept new solution " + newDistance);
                currentSolution = Utils.clonePointList(newSolution);
            } else {
                System.out.println("don't accept solution!");
            }

            // Keep track of the best solution found
            if (Utils.euclideanDistance2D(currentSolution) < Utils.euclideanDistance2D(best)) {
                System.out.println("replace best");
                best = Utils.clonePointList(currentSolution);
            }

            // Cool system
            temp *= 1 - coolingRate;
            //System.out.println("temp=" + temp);
        }

        System.out.println("Final solution distance: " + Utils.euclideanDistance2D(best));
        System.out.println("Tour: " + best);

        return best;
    }
}
