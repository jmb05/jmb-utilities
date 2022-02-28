package net.jmb19905.util;

public class SmoothFloat {

    private float agility;

    private float target;
    private float actual;

    public SmoothFloat(float initialValue, float agility) {
        this.target = initialValue;
        this.actual = initialValue;
        this.agility = agility;
    }

    public void update(float delta) {
        float offset = target - actual;
        float change = offset * delta * agility;
        actual += change;
    }

    public void increaseTarget(float dT) {
        this.target += dT;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public void instantIncrease(float increase) {
        this.actual += increase;
    }

    public void set(float value) {
        this.target = value;
        this.actual = value;
    }

    public float get() {
        return actual;
    }

    public void force() {
    }

    public float getTarget() {
        return target;
    }

    public float getAgility() {
        return agility;
    }

    public void setAgility(float agility) {
        this.agility = agility;
    }

    public void invert() {
        target = -target;
    }

    public boolean hasReachedTarget() {
        return actual == target;
    }

}
