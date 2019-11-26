package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomSampling implements Solver {

    private static String format =  "%8.2f";

    public List<Point> solve(Instance instance) {

        List<Point> random =  new ArrayList<Point>(instance.getPoints());
        List<Point> best =  new ArrayList<Point>(random);
        double distRandom;
        double distBest;

        distBest = Utils.euclideanDistance2D(best);
        //for (int i = 0; i < 100000; i++) {
        while (distBest > 7000.0) {
            Collections.shuffle(random);

            distRandom = Utils.euclideanDistance2D(random);
            if (distRandom < distBest) {
                System.out.println("==> r=" + String.format(format, distRandom) + " b=" + String.format(format, distBest));
                distBest = distRandom;
                best =  new ArrayList<Point>(random);
            } else {
                //System.out.println("--- r=" + String.format(format, distRandom) + " b=" + String.format(format, distBest));
            }
        }

        return best;
    }
}
