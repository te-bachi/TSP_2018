package ch.zhaw.ciel.mse.alg.knapsack.metaheuristics;

import ch.zhaw.ciel.mse.alg.knapsack.Item;
import ch.zhaw.ciel.mse.alg.knapsack.Knapsack;

import java.util.ArrayList;
import java.util.List;

public class TabuGreedy implements Solver {

    public static int TABU_DURATION = 3 + 1;

    private Item findMostValuableInRepertoryThatFitsInContent(Knapsack knapsack) {
        Item mostValuableItem;

        mostValuableItem = null;
        for (Item item : knapsack.getRepertory()) {
            /* item is not tabu? */
            if (item.getTabuDuration() == 0) {
                /* item fits into the knapsack */
                if ((item.getWeight() + knapsack.getContentWeight()) <= knapsack.getMaxWeight()) {
                    /* no most valuable item _OR_ item has more value that most valuable item so far */
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

    private boolean compareToBest(List<Item> bestItems, List<Item> content) {
        if (bestItems != null && bestItems.size() == content.size()) {
            if (bestItems.containsAll(content)) {
                return true;
            }
        }

        /* there is no best item list _OR_ sizes are not equal _OR_ best doesn't contain all from content */
        /* best list != content list */
        return false;
    }

    @Override
    public void solve(Knapsack knapsack) {
        List<Item> bestItems = null;
        int bestContentWeight = 0;
        boolean removed;

        Item mostValuableItem;
        Item leastValuableItem = null;

        while (knapsack.getContentWeight() != knapsack.getMaxWeight()) {
            removed = false;

            /* Find most valuable that fits in bag */
            mostValuableItem = findMostValuableInRepertoryThatFitsInContent(knapsack);


            /* If found, add */
            if (mostValuableItem != null) {
                if(!knapsack.tryAddToContent(mostValuableItem)) {
                    System.out.println("can't add most valuable: " + mostValuableItem.toString());
                    return;
                }

                /* Loop */
                if (compareToBest(bestItems, knapsack.getContent())) {
                    System.out.println("Loop!");
                    return;
                }

                /** set tabu duration **/
                mostValuableItem.setTabuDuration(TABU_DURATION);

            /* If not found... (because no space in bag, etc.) */
            } else {
                /* Remove least valuable */
                leastValuableItem = findLeastValuableInContent(knapsack);
                if (!knapsack.tryRemoveFromContent(leastValuableItem)) {
                    System.out.println("can't remove least valuable!");
                    return;
                }
                removed = true;

                /** set tabu duration **/
                leastValuableItem.setTabuDuration(TABU_DURATION);
            }
            /* Decrease tabu duration */
            decreaseTabuDurationInContent(knapsack);

            if (knapsack.getContentWeight() > bestContentWeight) {
                bestContentWeight = knapsack.getContentWeight();
                bestItems = new ArrayList<>(knapsack.getContent());
            }

            knapsack.printOverviewContent();
            if (removed) {
                System.out.println(leastValuableItem);
            }

            knapsack.increaseIteration();
            System.out.print("");
        }
    }
}
