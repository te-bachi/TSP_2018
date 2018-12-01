package ch.zhaw.ciel.mse.alg.knapsack;

public class Item {
    private int nr;
    private int weight;
    private int value;
    private double ratio;
    private int tabuDuration;

    private Item() {
        this.nr = 0;
        this.weight = 0;
        this.value = 0;
        this.ratio = 0;
        this.tabuDuration = 0;
    }

    public Item(int nr, int weight, int value) {
        this.nr     = nr;
        this.weight = weight;
        this.value  = value;
        this.ratio = ((double) value) / ((double) weight);
        this.tabuDuration = 0;
    }

    public int getNr() {
        return nr;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public double getRatio() {
        return ratio;
    }

    public void setTabuDuration(int tabuDuration) {
        this.tabuDuration = tabuDuration;
    }

    public int getTabuDuration() {
        return tabuDuration;
    }

    public void decreaseTabuDuration() {
        if (tabuDuration > 0) {
            tabuDuration--;
        }
    }

    public String toString() {
        return String.format("| %3d | %,10d | %,10d | %3d |", nr, value, weight,tabuDuration);
    }
}
