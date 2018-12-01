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
//                System.out.println("Can't add item!");
//                System.out.println("itemWeight=" + itemMaxValue.getWeight() + " contentWeight= " + knapsack.getContentWeight());
//                System.out.println("itemWeight + contentWeight =" + (itemMaxValue.getWeight() + knapsack.getContentWeight()) + " <=> maxWeight=" + knapsack.getMaxWeight());
//
//                System.out.println(itemMaxValue.toString());
                repertory.remove(itemMaxValue);
            }
        }
    }
}
