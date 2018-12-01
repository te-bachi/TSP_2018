package ch.zhaw.ciel.mse.alg.knapsack;

import java.util.ArrayList;
import java.util.List;

public class Knapsack {

    private int iteration;
    private int maxWeight;
    private int contentWeight;
    private int contentValue;
    private List<Item> content;
    private List<Item> repertory;

    public Knapsack(int maxWeight, List<Item> repertory) {
        this.iteration     = 1;
        this.maxWeight     = maxWeight;
        this.contentWeight = 0;
        this.contentValue  = 0;
        this.repertory     = repertory;
        this.content       = new ArrayList<>(repertory.size());
    }

    public boolean tryAddToContent(Item item) {
        if (item != null && (item.getWeight() + contentWeight) <= maxWeight) {
            if (repertory.remove(item)) {
                content.add(item);
                contentWeight += item.getWeight();
                contentValue += item.getValue();
                return true;
            }
        }

        return false;
    }

    public boolean tryRemoveFromContent(Item item) {
        boolean removed = content.remove(item);
        if (removed) {
            repertory.add(item);
            contentWeight -= item.getWeight();
            contentValue -= item.getValue();
        }

        return removed;
    }

    public void increaseIteration() {
        iteration++;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getContentWeight() {
        return contentWeight;
    }

    public int getContentValue() {
        return contentValue;
    }

    public List<Item> getContent() {
        return content;
    }

    public List<Item> getRepertory() {
        return repertory;
    }

    public void printOverviewContent() {
        int value = 0;
        int weight = 0;

        System.out.println("Iteration " + iteration);
        for (Item item : content) {
            value += item.getValue();
            weight += item.getWeight();
            System.out.printf("%s %,10d | %,10d |\n", item.toString(), value, weight);
        }
        System.out.println("|=====|============|============|=====|============|============|");
    }

    public void printFullContent() {
        System.out.println("| Nr  | Item value | Item weight| Tabu | KS value   | KS weight  |");
        System.out.println("|=====|============|============|=====|============|============|");
        printOverviewContent();
        System.out.printf("|     | %,10d | %,10d |\n", contentValue, contentWeight);
        System.out.println("|=====|============|============|=====|============|============|");
        System.out.println("                     " + String.format("%,10d", maxWeight) + " max. Weight");
        System.out.println("                     " + String.format("%,10d", (maxWeight - contentWeight)) + " diff");
    }
}
