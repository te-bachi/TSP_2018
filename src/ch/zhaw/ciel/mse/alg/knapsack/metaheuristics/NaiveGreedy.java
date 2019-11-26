package ch.zhaw.ciel.mse.alg.knapsack.metaheuristics;

import ch.zhaw.ciel.mse.alg.knapsack.Item;
import ch.zhaw.ciel.mse.alg.knapsack.Knapsack;

import java.util.List;

public class NaiveGreedy implements Solver {
    @Override
    public void solve(Knapsack knapsack) {
        List<Item> repertory        = knapsack.getRepertory();
        Item itemMaxValue;

        while (repertory.size() > 0) {
            /* Find max. value in repertory */
            itemMaxValue = null;
            for (Item item : repertory) {
                if (itemMaxValue == null || item.getValue() > itemMaxValue.getValue()) {
                    itemMaxValue = item;
                }
            }

            /* Check if item fits in knapsack */
            if (!knapsack.tryAddToContent(itemMaxValue)) {
                /* If it doesn't fit into knapsack: throw it out of the repertory */
                repertory.remove(itemMaxValue);

            }
            knapsack.printOverviewContent();
            knapsack.increaseIteration();
        }

        return;
    }
}
