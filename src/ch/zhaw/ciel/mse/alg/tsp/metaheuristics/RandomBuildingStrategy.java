package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.List;

public class RandomBuildingStrategy {
    public static List<Point> solve(Instance instance){

        List<Point> solution;
        List<Point> bestSolution;

        int numSolutions = 2000;
        bestSolution = null;
        for (int i = 0; i < numSolutions; i++) {

            solution = RandomBuildingStrategy.solve(instance);
            System.out.print("Solution " + i + " has length: " + Utils.euclideanDistance2D(solution));
            if (bestSolution == null || Utils.euclideanDistance2D(solution) < Utils.euclideanDistance2D(bestSolution)) {
                System.out.println(" => replace");
                bestSolution = solution;
            } else {
                System.out.println(" => keep existing solution with length: " + Utils.euclideanDistance2D(bestSolution));
            }
        }
        //List<Point> points = instance.clonePointList();
        //Collections.sort(points, (p1, p2) -> Double.compare(p1.getX(), p2.getX()));

        List<Point> points = instance.clonePointList();
        List<Point> randomPoints = new ArrayList<>(points.size());
        Random random = new Random();

        while (points.size() > 0) {
            randomPoints.add(points.remove(random.nextInt(points.size())));
        }
        return randomPoints;
    }
}
