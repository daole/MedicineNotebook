package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableSchedule;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends Model {
    private long mFamilyMemberId;
    private long mMedicineTimeId;
    private long mMedicineIntervalId;
    private String mFallbackFamilyMemberName;
    private int mFallbackIntervalValue;
    private String mStartDate;
    private int mTimes;
    private String mImagePath;
    private String mScheduleNote;

    public long getFamilyMemberId() {
        return this.mFamilyMemberId;
    }

    public void setFamilyMemberId(long pFamilyMemberId) {
        this.mFamilyMemberId = pFamilyMemberId;
    }

    public long getMedicineTimeId() {
        return this.mMedicineTimeId;
    }

    public void setMedicineTimeId(long pMedicineTimeId) {
        this.mMedicineTimeId = pMedicineTimeId;
    }

    public long getMedicineIntervalId() {
        return this.mMedicineIntervalId;
    }

    public void setMedicineIntervalId(long pMedicineIntervalId) {
        this.mMedicineIntervalId = pMedicineIntervalId;
    }

    public String getFallbackFamilyMemberName() {
        return this.mFallbackFamilyMemberName;
    }

    public void setFallbackFamilyMemberName(String pFallbackFamilyMemberName) {
        this.mFallbackFamilyMemberName = pFallbackFamilyMemberName;
    }

    public int getFallbackIntervalValue() {
        return this.mFallbackIntervalValue;
    }

    public void setFallbackIntervalValue(int pFallbackIntervalValue) {
        this.mFallbackIntervalValue = pFallbackIntervalValue;
    }

    public String getStartDate() {
        return this.mStartDate;
    }

    public void setStartDate(String pStartDate) {
        this.mStartDate = pStartDate;
    }

    public int getTimes() {
        return this.mTimes;
    }

    public void setTimes(int pTimes) {
        this.mTimes = pTimes;
    }

    public String getImagePath() {
        return this.mImagePath;
    }

    public void setImagePath(String pImagePath) {
        this.mImagePath = pImagePath;
    }

    public String getScheduleNote() {
        return this.mScheduleNote;
    }

    public void setScheduleNote(String pScheduleNote) {
        this.mScheduleNote = pScheduleNote;
    }

    public static List<Schedule> fetchData(Cursor pCursor) {
        List<Schedule> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                Schedule model = Schedule.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static Schedule fetchDataAtCurrentPosition(Cursor pCursor) {
        return Schedule.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static Schedule fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        Schedule model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
            }
            long familyMemberId = pCursor.getLong(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__FAMILY_MEMBER_ID));
            long medicineTimeId = pCursor.getLong(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__MEDICINE_TIME_ID));
            long medicineIntervalId = pCursor.getLong(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__MEDICINE_INTERVAL_ID));
            String fallbackFamilyMemberName = pCursor.getString(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__FALLBACK_FAMILY_MEMBER_NAME));
            int fallbackIntervalValue = pCursor.getInt(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__FALLBACK_INTERVAL_VALUE));
            String startDate = pCursor.getString(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__START_DATE));
            int alarmTimes = pCursor.getInt(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__TIMES));
            String imagePath = pCursor.getString(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__IMAGE_PATH));
            String scheduleNote = pCursor.getString(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__SCHEDULE_NOTE));

            model = new Schedule();
            model.setRowId(rowId);
            model.setFamilyMemberId(familyMemberId);
            model.setMedicineTimeId(medicineTimeId);
            model.setMedicineIntervalId(medicineIntervalId);
            model.setFallbackFamilyMemberName(fallbackFamilyMemberName);
            model.setFallbackIntervalValue(fallbackIntervalValue);
            model.setStartDate(startDate);
            model.setTimes(alarmTimes);
            model.setImagePath(imagePath);
            model.setScheduleNote(scheduleNote);
        }

        return  model;
    }
}
