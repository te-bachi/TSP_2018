package ch.zhaw.ciel.mse.alg.knapsack.metaheuristics;

import ch.zhaw.ciel.mse.alg.knapsack.Item;
import ch.zhaw.ciel.mse.alg.knapsack.Knapsack;

import java.util.ArrayList;
import java.util.List;

public class TabuGreedy implements Solver {

    public static int TABU_DURATION = 4;

    private Item findMostValuableInRepertoryThatFitsInContent(Knapsack knapsack) {
        Item mostValuableItem;

        mostValuableItem = null;
        for (Item item : knapsack.getRepertory()) {
            /* item is not tabu? */
            if (item.getTabuDuration() == 0) {
                /* item fits into the knapsack */
                if ((item.getWeight() + knapsack.getContentWeight()) <= knapsack.getMaxWeight()) {
                    /* item has more value that most valuable item so far */
                    if (mostValuableItem == null || item.getValue() > mostValuableItem.getValue()) {
                        mostValuableItem = item;
                    }
                }
            }
        }
        return mostValuableItem;
    }

    private Item findLeastValuableInContent(Knapsack knapsack) {
        Item leastValuableItem;

        leastValuableItem = null;
        for (Item item : knapsack.getContent()) {
            if (item.getTabuDuration() == 0) {
                if (leastValuableItem == null ||
                        (item.getValue() < leastValuableItem.getValue())) {
                    leastValuableItem = item;
                }
            }
        }

        return leastValuableItem;
    }

    private void decreaseTabuDurationInContent(Knapsack knapsack) {
        for (Item item : knapsack.getRepertory()) {
            item.decreaseTabuDuration();
        }
        for (Item item : knapsack.getContent()) {
            item.decreaseTabuDuration();
        }
    }

    @Override
    public void solve(Knapsack knapsack) {
        List<Item> items = knapsack.getRepertory();
        List<Item> tabuList  = new ArrayList<>(items.size());

        Item mostValuableItem;
        Item leastValuableItem = null;
        boolean run = true;

        //while (items.size() > 0) {
        while (run) {
            /* Find most valuable */
            mostValuableItem = findMostValuableInRepertoryThatFitsInContent(knapsack);


            /* If found, add */
            if (mostValuableItem != null) {
                if(!knapsack.tryAddToContent(mostValuableItem)) {
                    System.out.println("can't add most valuable: " + mostValuableItem.toString());
                    return;
                }

                /** set tabu duration **/
                mostValuableItem.setTabuDuration(TABU_DURATION);

                knapsack.printOverviewContent();

            /* If not found... */
            } else {

                /* Do while most valuable found */
                do {
                    /* Remove least valuable */
                    leastValuableItem = findLeastValuableInContent(knapsack);
                    if (!knapsack.tryRemoveFromContent(leastValuableItem)) {
                        System.out.println("can't remove least valuable!");
                        return;
                    }

                    /** set tabu duration **/
                    leastValuableItem.setTabuDuration(TABU_DURATION);

                    /* Find most valuable */
                    mostValuableItem = findMostValuableInRepertoryThatFitsInContent(knapsack);
                } while (mostValuableItem == null);

                if(!knapsack.tryAddToContent(mostValuableItem)) {
                    System.out.println("can't add most valuable: " + mostValuableItem.toString());
                    return;
                }

                /** set tabu duration **/
                mostValuableItem.setTabuDuration(TABU_DURATION);

                /* Least removed is most added?*/
                if (leastValuableItem == mostValuableItem) {

                    /* Exit */
                    System.out.println("least removed is most added: " + mostValuableItem.toString());
                    return;
                }
            }
            /* Decrease tabu duration */
            decreaseTabuDurationInContent(knapsack);

        }
    }
}
