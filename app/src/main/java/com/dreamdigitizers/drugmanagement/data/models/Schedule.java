package com.dreamdigitizers.drugmanagement.data.models;

import java.util.ArrayList;

public class Schedule extends Model {
    private FamilyMember mFamilyMember;
    private MedicineTime mTime;
    private MedicineInterval mInterval;
    private ArrayList<TakenMedicine> mMedicineList;
    private String mStartDate;
    private int mMedicineTakingTimes;
    private String mImagePath;
    private boolean mIsAlert;
    private String mNote;

    public FamilyMember getFamilyMember() {
        return this.mFamilyMember;
    }

    public void setFamilyMember(FamilyMember pFamilyMember) {
        this.mFamilyMember = pFamilyMember;
    }

    public ArrayList<TakenMedicine> getMedicineList() {
        return this.mMedicineList;
    }

    public void setMedicineList(ArrayList<TakenMedicine> pMedicineList) {
        this.mMedicineList = pMedicineList;
    }

    public MedicineTime getTime() {
        return this.mTime;
    }

    public void setTime(MedicineTime pTime) {
        this.mTime = pTime;
    }

    public MedicineInterval getInterval() {
        return this.mInterval;
    }

    public void setInterval(MedicineInterval pInterval) {
        this.mInterval = pInterval;
    }

    public String getmStartDate() {
        return this.mStartDate;
    }

    public void setStartDate(String pStartDate) {
        this.mStartDate = pStartDate;
    }

    public int getMedicineTakingTimes() {
        return this.mMedicineTakingTimes;
    }

    public void setMedicineTakingTimes(int pMedicineTakingTimes) {
        this.mMedicineTakingTimes = pMedicineTakingTimes;
    }

    public String getImagePath() {
        return this.mImagePath;
    }

    public void setImagePath(String pImagePath) {
        this.mImagePath = pImagePath;
    }

    public boolean isAlert() {
        return this.mIsAlert;
    }

    public void setAlert(boolean pIsAlert) {
        this.mIsAlert = pIsAlert;
    }

    public String getNote() {
        return this.mNote;
    }

    public void setNote(String pNote) {
        this.mNote = pNote;
    }
}
