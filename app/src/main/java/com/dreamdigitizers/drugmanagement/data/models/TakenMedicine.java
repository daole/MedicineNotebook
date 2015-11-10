package com.dreamdigitizers.drugmanagement.data.models;

public class TakenMedicine extends Model {
    private long mMedicineTimeSettingId;
    private long mMedicineId;
    private String mMedicineName;
    private String mDose;

    public long getMedicineTimeSettingId() {
        return this.mMedicineTimeSettingId;
    }

    public void setMedicineTimeSettingId(long pMedicineTimeSettingId) {
        this.mMedicineTimeSettingId = pMedicineTimeSettingId;
    }

    public long getMedicineId() {
        return this.mMedicineId;
    }

    public void setMedicineId(long pMedicineId) {
        this.mMedicineId = pMedicineId;
    }

    public String getMedicineName() {
        return this.mMedicineName;
    }

    public void setMedicineName(String pMedicineName) {
        this.mMedicineName = pMedicineName;
    }

    public String getDose() {
        return this.mDose;
    }

    public void setDose(String pDose) {
        this.mDose = pDose;
    }
}
