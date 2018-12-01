package ch.zhaw.ciel.mse.alg.knapsack.metaheuristics;

import ch.zhaw.ciel.mse.alg.knapsack.Item;
import ch.zhaw.ciel.mse.alg.knapsack.Knapsack;

import java.util.Comparator;
import java.util.List;

public class SmarterGreedy implements Solver {
    @Override
    public void solve(Knapsack knapsack) {
        List<Item> repertory        = knapsack.getRepertory();
        //List<Item> ratio            = new ArrayList<>(repertory.size());

        /* Sort */
        repertory.sort(Comparator.comparingDouble(Item::getRatio).reversed());

        while (repertory.size() > 0) {
            /* Check if item fits in knapsack */
            if (!knapsack.tryAddToContent(repertory.get(0))) {
                repertory.remove(0);
            }
        }
    }
}
