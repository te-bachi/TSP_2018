package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

public class NearestNeighbor {

    /*
    private void printTour(List<Point> tour) {
        for (Point city : tour) {
            System.out.print(city);
        }
    }

    private void calcTour(List<Point> tour) {

    }
    */

    public static List<Point> solve(Instance instance){
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

        i = 0;
        //for (i = 0; i < points.size(); i++) {
            /* load remaining cities list */
            remainingCities.addAll(points);

            nearestNeighborIdx = i;
            do {
                /* tryRemoveFromContent */
                currentCity = remainingCities.remove(nearestNeighborIdx);
                visitedCities.add(currentCity);

                /* Find nearest neighbor */
                nearestNeighbor = null;
                if (remainingCities.size() > 0) {
                    for (k = 0; k < remainingCities.size(); k++) {
                        distance = Utils.euclideanDistance2D(currentCity, remainingCities.get(k));

                        if (nearestNeighbor == null || distance < nearestNeighborDistance) {
                            nearestNeighbor = remainingCities.get(k);
                            nearestNeighborDistance = distance;
                            nearestNeighborIdx = k;
                        }
                    }
                    //System.out.println("nearest neighbor " + nearestNeighbor.getId() + ": " + nearestNeighbor.getX() + "/" + nearestNeighbor.getY());
                    //System.out.println("remainingCities.size: " + remainingCities.size());
                }
            } while (remainingCities.size() > 0);

            distance = Utils.euclideanDistance2D(visitedCities);
            System.out.print("Solution " + i + " has length: " + distance + ", best solution: " + bestTourDistance);
            if (bestTourDistance == 0.0 || distance < bestTourDistance) {
                bestTourDistance = distance;
                bestTour.clear();
                bestTour.addAll(visitedCities);
                System.out.println(" => replace");
            } else {
                System.out.println(" => stay");
            }
            visitedCities.clear();
        //}

        return bestTour;
    }
}
