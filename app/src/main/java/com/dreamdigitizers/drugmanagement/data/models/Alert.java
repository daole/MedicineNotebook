package com.dreamdigitizers.drugmanagement.data.models;

public class Alert extends Model {
    private MedicineTimeSetting mMedicineTimeSetting;
    private int mAlertYear;
    private int mAlertMonth;
    private int mAlertDay;
    private int mAlertHour;
    private int mAlertMinute;
    private boolean mIsDone;

    public MedicineTimeSetting getMedicineTimeSetting() {
        return this.mMedicineTimeSetting;
    }

    public void setMedicineTimeSetting(MedicineTimeSetting pMedicineTimeSetting) {
        this.mMedicineTimeSetting = pMedicineTimeSetting;
    }

    public int getAlertYear() {
        return this.mAlertYear;
    }

    public void setAlertYear(int pAlertYear) {
        this.mAlertYear = pAlertYear;
    }

    public int getAlertMonth() {
        return this.mAlertMonth;
    }

    public void setMonth(int pAlertMonth) {
        this.mAlertMonth = pAlertMonth;
    }

    public int getAlertDay() {
        return this.mAlertDay;
    }

    public void setAlertDay(int pAlertDay) {
        this.mAlertDay = pAlertDay;
    }

    public int getAlertHour() {
        return this.mAlertHour;
    }

    public void setAlertHour(int pAlertHour) {
        this.mAlertHour = pAlertHour;
    }

    public int getAlertMinute() {
        return this.mAlertMinute;
    }

    public void setAlertMinute(int pAlertMinute) {
        this.mAlertMinute = pAlertMinute;
    }

    public boolean isDone() {
        return this.mIsDone;
    }

    public void setDone(boolean pIsDone) {
        this.mIsDone = pIsDone;
    }
}
