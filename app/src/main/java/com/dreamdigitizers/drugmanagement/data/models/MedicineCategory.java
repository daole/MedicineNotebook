package com.dreamdigitizers.drugmanagement.data.models;

public class MedicineCategory extends Model {
    private String mMedicineCategoryName;

    public String getMedicineCategoryName() {
        return this.mMedicineCategoryName;
    }

    public void setMedicineCategoryName(String pMedicineCategoryName) {
        this.mMedicineCategoryName = pMedicineCategoryName;
    }
}
