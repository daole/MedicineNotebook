package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;

import java.util.ArrayList;
import java.util.List;

public class Alarm extends Model {
    private long mScheduleId;
    private int mAlarmYear;
    private int mAlarmMonth;
    private int mAlarmDate;
    private int mAlarmHour;
    private int mAlarmMinute;
    private boolean mIsDone;
    private long mFamilyMemberId;
    private String mFamilyMemberName;

    public long getScheduleId() {
        return this.mScheduleId;
    }

    public void setScheduleId(long pScheduleId) {
        this.mScheduleId = pScheduleId;
    }

    public int getAlarmYear() {
        return this.mAlarmYear;
    }

    public void setAlarmYear(int pAlarmYear) {
        this.mAlarmYear = pAlarmYear;
    }

    public int getAlarmMonth() {
        return this.mAlarmMonth;
    }

    public void setAlarmMonth(int pAlarmMonth) {
        this.mAlarmMonth = pAlarmMonth;
    }

    public int getAlarmDate() {
        return this.mAlarmDate;
    }

    public void setAlarmDate(int pAlarmDate) {
        this.mAlarmDate = pAlarmDate;
    }

    public int getAlarmHour() {
        return this.mAlarmHour;
    }

    public void setAlarmHour(int pAlarmHour) {
        this.mAlarmHour = pAlarmHour;
    }

    public int getAlarmMinute() {
        return this.mAlarmMinute;
    }

    public void setAlarmMinute(int pAlarmMinute) {
        this.mAlarmMinute = pAlarmMinute;
    }

    public boolean isDone() {
        return this.mIsDone;
    }

    public void setDone(boolean pIsDone) {
        this.mIsDone = pIsDone;
    }

    public long getFamilyMemberId() {
        return this.mFamilyMemberId;
    }

    public void setFamilyMemberId(long pFamilyMemberId) {
        this.mFamilyMemberId = pFamilyMemberId;
    }

    public String getFamilyMemberName() {
        return this.mFamilyMemberName;
    }

    public void setFamilyMemberName(String pFamilyMemberName) {
        this.mFamilyMemberName = pFamilyMemberName;
    }

    public static List<Alarm> fetchData(Cursor pCursor) {
        List<Alarm> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                Alarm model = Alarm.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static Alarm fetchDataAtCurrentPosition(Cursor pCursor) {
        Alarm model = null;

        if(pCursor != null) {
            long rowId = pCursor.getLong((Table.COLUMN_INDEX__ID));
            long scheduleId = pCursor.getLong((TableAlarm.COLUMN_INDEX__SCHEDULE_ID));
            int alarmYear = pCursor.getInt((TableAlarm.COLUMN_INDEX__ALARM_YEAR));
            int alarmMonth = pCursor.getInt((TableAlarm.COLUMN_INDEX__ALARM_MONTH));
            int alarmDate = pCursor.getInt((TableAlarm.COLUMN_INDEX__ALARM_DATE));
            int alarmHour = pCursor.getInt((TableAlarm.COLUMN_INDEX__ALARM_HOUR));
            int alarmMinute = pCursor.getInt((TableAlarm.COLUMN_INDEX__ALARM_MINUTE));
            boolean isDone = pCursor.getInt((TableAlarm.COLUMN_INDEX__IS_DONE)) != 0 ? true : false;
            long familyMemberId = pCursor.getLong((TableAlarm.COLUMN_INDEX__FAMILY_MEMBER_ID));
            String familyMemberName = pCursor.getString(TableAlarm.COLUMN_INDEX__FAMILY_MEMBER_NAME);

            model = new Alarm();
            model.setRowId(rowId);
            model.setScheduleId(scheduleId);
            model.setAlarmYear(alarmYear);
            model.setAlarmMonth(alarmMonth);
            model.setAlarmDate(alarmDate);
            model.setAlarmHour(alarmHour);
            model.setAlarmMinute(alarmMinute);
            model.setFamilyMemberId(familyMemberId);
            model.setFamilyMemberName(familyMemberName);
        }

        return  model;
    }
}
