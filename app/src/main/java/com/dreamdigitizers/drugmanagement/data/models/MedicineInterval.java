package com.dreamdigitizers.drugmanagement.data.models;

public class MedicineInterval extends Model {
    private String mMedicineIntervalName;
    private int mMedicineIntervalValue;

    public String getMedicineIntervalName() {
        return this.mMedicineIntervalName;
    }

    public void setMedicineIntervalName(String pMedicineIntervalName) {
        this.mMedicineIntervalName = pMedicineIntervalName;
    }

    public int getMedicineIntervalValue() {
        return this.mMedicineIntervalValue;
    }

    public void setMedicineIntervalValue(int pMedicineIntervalValue) {
        this.mMedicineIntervalValue = pMedicineIntervalValue;
    }
}
