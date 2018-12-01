package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BestInsertion {

    public static List<Point> solve(Instance instance) {
        List<Point> R                       = instance.clonePointList();
        List<Point> S                       = new ArrayList<>(R.size());
        Point       e1;
        Point       e2;
        double      distance                = 0.0;
        double      bestDistance            = 0.0;
        double      sDistance               = 0.0;
        double      eDistance               = 0.0;
        double      tDistance               = 0.0;
        int         e1Idx                    = 0;
        int         e2Idx                   = 0;
        int         i                       = 0;
        int         k                       = 0;

        /* Find two nearest cities (exclude start-end distance)*/
        for (i = 0; i < R.size(); i++) {
            e1 = R.get(i);
            for (k = 0; k < R.size(); k++) {
                if (i != k) {
                    e2 = R.get(k);
                    distance = Utils.euclideanDistance2D(e1, e2);
                    if (bestDistance == 0.0 || distance < bestDistance) {
                        bestDistance = distance;
                        e1Idx = i;
                        e2Idx = k;
                        //System.out.println(i + " - found better nearest cities, length=" + bestDistance);
                    }
                }
            }
        }
        System.out.println(e1Idx + "/" + e2Idx);
        /* Removing from list changes indexes */
        /* Prevent it to tryRemoveFromContent the largest index first */
        if (e1Idx < e2Idx) {
            S.add(R.remove(e2Idx));
            S.add(R.remove(e1Idx));
        } else {
            S.add(R.remove(e1Idx));
            S.add(R.remove(e2Idx));
        }

        sDistance = Utils.euclideanDistance2D(S);

        do {
            /* Add another city so that the total distance is minimal */
            bestDistance = 0.0;
            for (i = 0; i < R.size(); i++) {
                //S.tryAddToContent(R.get(i));
                tDistance  = Utils.euclideanDistance2D(S.get(S.size() - 1), R.get(i));
                //tDistance += Utils.euclideanDistance2D(R.get(i), S.get(0));
                distance   = sDistance + tDistance;
                if (bestDistance == 0.0 || distance < bestDistance) {
                    bestDistance = distance;
                    eDistance = tDistance;
                    e1Idx = i;
                }
                //S.tryRemoveFromContent(S.size() - 1);
                //System.out.println(i);
            }
            //System.out.println(R.size() + " " + eIdx);
            S.add(R.remove(e1Idx));
            sDistance += eDistance;
            System.out.println("temp=" + Utils.euclideanDistance2D(S) + ", " + sDistance);

        } while (R.size() > 0);
        //tDistance = Utils.euclideanDistance2D(S.get(0), S.get(S.size() - 1));
        //System.out.println((sDistance + tDistance));
        System.out.println(sDistance);
        return S;
    }
}
