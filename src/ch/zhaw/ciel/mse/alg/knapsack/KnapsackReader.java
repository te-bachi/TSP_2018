package ch.zhaw.ciel.mse.alg.knapsack;

import ch.zhaw.ciel.mse.alg.knapsack.metaheuristics.NaiveGreedy;
import ch.zhaw.ciel.mse.alg.knapsack.metaheuristics.SmarterGreedy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class KnapsackReader {

    private static int SIZE_HEADER      = 2;
    private static int SIZE_BLANK       = 1;
    private static int OFFSET_WEIGHTS   = 2;
    private static int OFFSET_VALUES    = 3;

    public static Knapsack load(Path filePath) throws IOException {

        List<String> lines      = Files.readAllLines(filePath);
        int          numItems   = Integer.parseInt(lines.get(0));
        int          maxWeight  = Integer.parseInt(lines.get(1));

        if (lines.size() < (SIZE_HEADER + (2 * numItems) + SIZE_BLANK)) {
            throw new IOException("number of lines doesn't match content");
        }

        int[] weights = lines.subList(OFFSET_WEIGHTS, numItems + OFFSET_WEIGHTS).stream().mapToInt(Integer::valueOf).toArray();
        int[] values  = lines.subList(numItems + OFFSET_VALUES, (2 * numItems) + OFFSET_VALUES).stream().mapToInt(Integer::valueOf).toArray();

        if (numItems != weights.length || numItems != values.length) {
            throw new IOException("numItem doesn't match weights or values");
        }

        List<Item> items = new ArrayList<>(numItems);
        for (int i = 0; i < numItems; i++) {
            items.add(new Item(i + 1, weights[i], values[i]));
        }

        return new Knapsack(maxWeight, items);
    }
}
