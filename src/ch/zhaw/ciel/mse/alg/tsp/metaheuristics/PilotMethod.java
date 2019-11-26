package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PilotMethod  implements Solver {

    private int    start;
    private Solver heuristic;

    public PilotMethod(int start, Solver heuristic) {
        this.start = start;
        this.heuristic = heuristic;
    }

    @Override
    public List<Point> solve(Instance instance) {
        List<Point> prefix      = new ArrayList<Point>(instance.getPoints().subList(0, start));
        List<Point> postfix     = new ArrayList<Point>(instance.getPoints().subList(start + 1, instance.getPoints().size()));
        List<Point> points      = new ArrayList<>(prefix);
        points.addAll(postfix);
        List<Point> bestTour    = new ArrayList<>(points.size());
        List<Point> remaining   = new ArrayList<>(points.size());

        double      distance                = 0.0;
        double      bestTourDistance        = 0.0;

        int i;
        int j;
        int k = calcNumLines(points.size());

        /* for all points */
        for (i = 0; i < k; i++) {
            //for (j = )
            /* Create a sublist */
            remaining.addAll(points.subList(i - 1, points.size()));

            /* Complete tour with heuristic */

        }
        /*
        do {

        } while ()
        */

        return bestTour;
    }

    private static int calcNumLines(int n) {
        return IntStream.rangeClosed(1, n)
                .reduce(0, (int x, int y) -> x + y);
    }
}
