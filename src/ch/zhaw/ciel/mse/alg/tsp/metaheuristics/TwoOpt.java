package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;

import java.util.ArrayList;
import java.util.List;

public class TwoOpt {
    public static List<Point> solve(Instance instance) {
        List<Point> points                  = instance.clonePointList();
        List<Point> remainingCities         = new ArrayList<>(points.size());
        List<Point> visitedCities           = new ArrayList<>(points.size());
        List<Point> bestTour                = new ArrayList<>(points.size());
        Point       currentCity             = null;
        Point       nearestNeighbor         = null;
        double      distance                = 0.0;
        double      nearestNeighborDistance = 0.0;
        double      bestTourDistance        = 0.0;
        int         nearestNeighborIdx;
        int         i;
        int         k;


        for (i = 0; i < points.size(); i++) {
            for (k = 0; k < points.size(); k++) {

            }
        }
        return null;
    }
}
