package ch.zhaw.ciel.mse.alg.tsp;

public class InstanceName {
    public boolean enabled;
    public String name;

    public InstanceName(boolean enabled, String name) {
        this.enabled = enabled;
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }
}
