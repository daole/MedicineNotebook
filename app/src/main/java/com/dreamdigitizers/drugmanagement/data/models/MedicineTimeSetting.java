package com.dreamdigitizers.drugmanagement.data.models;

import java.util.ArrayList;

public class MedicineTimeSetting extends Model {
    private FamilyMember mFamilyMember;
    private ArrayList<Medicine> mMedicineList;
    private MedicineTime mTime;
    private Interval mInterval;
    private String mStartDate;
    private int mAlertTimes;
    private String mImagePath;
    private boolean mIsAlert;
    private String mAlertNote;

    public FamilyMember getFamilyMember() {
        return this.mFamilyMember;
    }

    public void setFamilyMember(FamilyMember pFamilyMember) {
        this.mFamilyMember = pFamilyMember;
    }

    public ArrayList<Medicine> getMedicineList() {
        return this.mMedicineList;
    }

    public void setMedicineList(ArrayList<Medicine> pMedicineList) {
        this.mMedicineList = pMedicineList;
    }

    public MedicineTime getTime() {
        return this.mTime;
    }

    public void setTime(MedicineTime pTime) {
        this.mTime = pTime;
    }

    public Interval getInterval() {
        return this.mInterval;
    }

    public void setInterval(Interval pInterval) {
        this.mInterval = pInterval;
    }

    public String getmStartDate() {
        return this.mStartDate;
    }

    public void setStartDate(String pStartDate) {
        this.mStartDate = pStartDate;
    }

    public int getAlertTimes() {
        return this.mAlertTimes;
    }

    public void setAlertTimes(int pAlertTimes) {
        this.mAlertTimes = pAlertTimes;
    }

    public String getImagePath() {
        return this.mImagePath;
    }

    public void setImagePath(String pImagePath) {
        this.mImagePath = pImagePath;
    }

    public boolean getIsAlert() {
        return this.mIsAlert;
    }

    public void setIsAlert(boolean pIsAlert) {
        this.mIsAlert = pIsAlert;
    }

    public String getAlertNote() {
        return this.mAlertNote;
    }

    public void setAlertNote(String pAlertNote) {
        this.mAlertNote = pAlertNote;
    }
}
