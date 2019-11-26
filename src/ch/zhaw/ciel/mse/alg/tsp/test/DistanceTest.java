package ch.zhaw.ciel.mse.alg.tsp.test;

import ch.zhaw.ciel.mse.alg.tsp.utils.Line;
import ch.zhaw.ciel.mse.alg.tsp.utils.Point;
import ch.zhaw.ciel.mse.alg.tsp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DistanceTest {

    public static int N = 10;

    public static void main(String[] args) {
        int x;
        int y;
        int id = 1;
        List<Point> points = new ArrayList<>((N * N) / 2);
        List<Line>  lines  = new ArrayList<>(Utils.calcNumLines(points.size()));
        Point p0 = new Point(0, 0, 0);
        Point pi;
        points.add(p0);
        for (x = 1; x <= N; x++) {
            for (y = x; y <= N; y++) {
                pi = new Point(id++, x, y);
                points.add(pi);
                lines.add(new Line(p0, pi));
            }
        }

        lines.stream().map(l -> l.getP1().getId() + "-" + l.getP2().getId() + ": "+ l.getDistance()).forEach(System.out::println);
        System.out.println("Done!");
    }
}
