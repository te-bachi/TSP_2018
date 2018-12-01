package ch.zhaw.ciel.mse.alg.tsp.utils;

import java.util.List;

public class PrinterSettings {
    public double margin = 0;

    public double minX = Double.MAX_VALUE;
    public double minY = Double.MAX_VALUE;
    public double maxX = 0;
    public double maxY = 0;
    public double xTransform;
    public double yTransform;
    public int width;
    public int height;

    public PrinterSettings(List<Point> solution) {

        for(Point point : solution){
            maxX = Math.max(maxX, point.getX());
            maxY = Math.max(maxY, point.getY());

            minX = Math.min(minX, point.getX());
            minY = Math.min(minY, point.getY());
        }

        xTransform = margin - minX;
        yTransform = margin - minY;

        width = (int)(maxX + xTransform + margin);
        height = (int)(maxY + margin + yTransform);

        System.out.println("maxX=" + maxX);
        System.out.println("maxY=" + maxY);
        System.out.println("minX=" + minX);
        System.out.println("minY=" + minY);
        System.out.println("xTransform=" + xTransform);
        System.out.println("yTransform=" + yTransform);
        System.out.println("width=" + width);
        System.out.println("height=" + height);
    }
}