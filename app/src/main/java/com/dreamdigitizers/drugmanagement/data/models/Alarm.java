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
    private boolean mIsAlarm;
    private boolean mIsDone;

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

    public boolean getIsAlarm() {
        return this.mIsAlarm;
    }

    public void setIsAlarm(boolean pIsAlarm) {
        this.mIsAlarm = pIsAlarm;
    }

    public boolean getIsDone() {
        return this.mIsDone;
    }

    public void setIsDone(boolean pIsDone) {
        this.mIsDone = pIsDone;
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
        return Alarm.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static Alarm fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        Alarm model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
            }
            long scheduleId = pCursor.getLong(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__SCHEDULE_ID));
            int alarmYear = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_YEAR));
            int alarmMonth = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_MONTH));
            int alarmDate = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_DATE));
            int alarmHour = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_HOUR));
            int alarmMinute = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_MINUTE));
            boolean isAlarm = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__IS_ALARM)) != 0 ? true : false;
            boolean isDone = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__IS_DONE)) != 0 ? true : false;

            model = new Alarm();
            model.setRowId(rowId);
            model.setScheduleId(scheduleId);
            model.setAlarmYear(alarmYear);
            model.setAlarmMonth(alarmMonth);
            model.setAlarmDate(alarmDate);
            model.setAlarmHour(alarmHour);
            model.setAlarmMinute(alarmMinute);
            model.setIsAlarm(isAlarm);
            model.setIsDone(isDone);
        }

        return  model;
    }
}
