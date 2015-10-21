package com.dreamdigitizers.drugmanagement.data.models;

public class Interval extends Model {
    private String mIntervalName;
    private int mIntervalValue;

    public String getName() {
        return this.mIntervalName;
    }

    public void setName(String pIntervalName) {
        this.mIntervalName = pIntervalName;
    }

    public int getIntervalValue() {
        return this.mIntervalValue;
    }

    public void setValue(int pIntervalValue) {
        this.mIntervalValue = pIntervalValue;
    }
}
