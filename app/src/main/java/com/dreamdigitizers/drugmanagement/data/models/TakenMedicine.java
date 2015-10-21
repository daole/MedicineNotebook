package com.dreamdigitizers.drugmanagement.data.models;

public class TakenMedicine extends Model {
    private Medicine mMedicine;
    private String mDose;

    public Medicine getMedicine() {
        return this.mMedicine;
    }

    public void setMedicine(Medicine pMedicine) {
        this.mMedicine = pMedicine;
    }

    public String getDose() {
        return this.mDose;
    }

    public void setDose(String pDose) {
        this.mDose = pDose;
    }
}
