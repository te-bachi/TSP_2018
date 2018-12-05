package ch.zhaw.ciel.mse.alg.knapsack;

import ch.zhaw.ciel.mse.alg.knapsack.metaheuristics.NaiveGreedy;
import ch.zhaw.ciel.mse.alg.knapsack.metaheuristics.TabuGreedy;

import java.io.IOException;
import java.nio.file.Paths;

public class KnapsackMain {

    private static String PATH_INSTANCE = "Knapsack_Instance";
    private static String INSTANCE_FILE_EXTENSION = ".txt";

    public static void main(String[] args) throws IOException {
        //runSingleKnapsackInstance("KS_P08");
        runSingleKnapsackInstance("Tabu_Search_01");
    }

    private static void runSingleKnapsackInstance(String instanceName) throws IOException {
        String path = PATH_INSTANCE + java.io.File.separator + instanceName + INSTANCE_FILE_EXTENSION;
        Knapsack knapsack = KnapsackReader.load(Paths.get(path));

        knapsack.printHeader();
        //new NaiveGreedy().solve(knapsack);
        //new SmarterGreedy.solve(knapsack);
        new TabuGreedy().solve(knapsack);

        knapsack.printFooter();
        System.out.printf("Done");
    }
}
