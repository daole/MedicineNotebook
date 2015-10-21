package com.dreamdigitizers.drugmanagement.data.models;

public class Alert extends Model {
    private MedicineTimeSetting mMedicineTimeSetting;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    public MedicineTimeSetting getMedicineTimeSetting() {
        return this.mMedicineTimeSetting;
    }

    public void setMedicineTimeSetting(MedicineTimeSetting pMedicineTimeSetting) {
        this.mMedicineTimeSetting = pMedicineTimeSetting;
    }

    public int getYear() {
        return this.mYear;
    }

    public void setYear(int pYear) {
        this.mYear = pYear;
    }

    public int getMonth() {
        return this.mMonth;
    }

    public void setMonth(int pMonth) {
        this.mMonth = pMonth;
    }

    public int getDay() {
        return this.mDay;
    }

    public void setDay(int pDay) {
        this.mDay = pDay;
    }

    public int getHour() {
        return this.mHour;
    }

    public void setHour(int pHour) {
        this.mHour = pHour;
    }

    public int getMinute() {
        return this.mMinute;
    }

    public void setMinute(int pMinute) {
        this.mMonth = pMinute;
    }
}
