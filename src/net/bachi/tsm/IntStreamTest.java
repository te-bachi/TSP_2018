package net.bachi.tsm;

import java.util.Random;
import java.util.stream.IntStream;

public class IntStreamTest {

    public static double[][] generateRandomMatrix(int n) {
        Random random           = new Random();
        double[][] randomMatrix = new double[n][n];

        IntStream.range(0, n)
                .forEach(i -> IntStream.range(0, n)
                        .forEach(j -> randomMatrix[i][j] = Math.abs(random.nextInt(100) + 1)));
        return randomMatrix;
    }

    public static void main(String[] args) {
        double[][] graph = generateRandomMatrix(10);

        System.out.printf("Done!");
    }
}
