package ch.zhaw.ciel.mse.alg.tsp.metaheuristics;

import ch.zhaw.ciel.mse.alg.tsp.utils.Instance;
import ch.zhaw.ciel.mse.alg.tsp.utils.Line;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class AllDistances implements Solver {

    @Override
    public List<Point> solve(Instance instance) {
        int i;
        int k;

        DecimalFormat df = new DecimalFormat("#.0");
        List<Point> points =  new ArrayList<Point>(instance.getPoints());
        List<Line> lines = new ArrayList<Line>(Utils.calcNumLines(points.size() - 1));
        Line line;
        Point p1;
        Point p2;

        for (i = 0; i < points.size(); i++) {
            for (k = i + 1; k < points.size(); k++) {
                p1 = points.get(i);
                p2 = points.get(k);
                lines.add(new Line(p1, p2, Utils.euclideanDistance2D(p1, p2)));
            }
        }


        System.out.println(Utils.calcNumLines(points.size() - 1));
        System.out.println("---");
        lines.stream().map(l -> "1 & $p_{" + l.getP1().getId() + "}$ & $p_{" + l.getP2().getId() + "}$ & "+ df.format(l.getDistance()) + " \\\\").forEach(System.out::println);
        System.out.println("---");
        Collections.sort(lines, new Comparator<Line>() {
            @Override
            public int compare(Line l1, Line l2) {
                if (l1.getDistance() > l2.getDistance()) return 1;
                if (l1.getDistance() < l2.getDistance()) return -1;
                return 0;
            }
        });
        lines.stream().map(l -> "1 & $p_{" + l.getP1().getId() + "}$ & $p_{" + l.getP2().getId() + "}$ & "+ df.format(l.getDistance()) + " \\\\").forEach(System.out::println);


        return null;
    }
}
