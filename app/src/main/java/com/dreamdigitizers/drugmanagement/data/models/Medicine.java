package com.dreamdigitizers.drugmanagement.data.models;

public class Medicine extends Model {
    private String mMedicineName;
    private String mMedicineNote;
    private String mMedicineImagePath;
    private MedicineCategory mMedicineCategory;

    public String getMedicineName() {
        return mMedicineName;
    }

    public void setMedicineName(String pMedicineName) {
        this.mMedicineName = pMedicineName;
    }

    public String getMedicineNote() {
        return this.mMedicineNote;
    }

    public void setMedicineNote(String pMedicineNote) {
        this.mMedicineNote = pMedicineNote;
    }

    public String getMedicineImagePath() {
        return this.mMedicineImagePath;
    }

    public void setMedicineImagePath(String pMedicineImagePath) {
        this.mMedicineImagePath = pMedicineImagePath;
    }

    public MedicineCategory getMedicineCategory() {
        return this.mMedicineCategory;
    }

    public void setMedicineCategory(MedicineCategory pMedicineCategory) {
        this.mMedicineCategory = pMedicineCategory;
    }
}
