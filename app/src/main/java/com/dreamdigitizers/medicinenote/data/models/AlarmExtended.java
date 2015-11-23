package com.dreamdigitizers.medicinenote.data.models;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class AlarmExtended extends Alarm {
    private ScheduleExtended mSchedule;

    public AlarmExtended() {

    }

    public AlarmExtended(Alarm pAlarm) {
        this.setRowId(pAlarm.getRowId());
        this.setScheduleId(pAlarm.getScheduleId());
        this.setAlarmYear(pAlarm.getAlarmYear());
        this.setAlarmMonth(pAlarm.getAlarmMonth());
        this.setAlarmDate(pAlarm.getAlarmDate());
        this.setAlarmHour(pAlarm.getAlarmHour());
        this.setAlarmMinute(pAlarm.getAlarmMinute());
        this.setIsAlarm(pAlarm.getIsAlarm());
        this.setIsDone(pAlarm.getIsDone());
    }

    public ScheduleExtended getSchedule() {
        return this.mSchedule;
    }

    public void setSchedule(ScheduleExtended pSchedule) {
        this.mSchedule = pSchedule;
    }

    public static List<AlarmExtended> fetchExtendedData(Cursor pCursor) {
        List<AlarmExtended> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                AlarmExtended model = AlarmExtended.fetchExtendedDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static AlarmExtended fetchExtendedDataAtCurrentPosition(Cursor pCursor) {
        return AlarmExtended.fetchExtendedDataAtCurrentPosition(pCursor, 0);
    }

    public static AlarmExtended fetchExtendedDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        AlarmExtended model = null;

        if(pCursor != null) {
            Alarm alarm = Alarm.fetchDataAtCurrentPosition(pCursor, pRowId);
            ScheduleExtended schedule = ScheduleExtended.fetchExtendedDataAtCurrentPosition(pCursor, alarm.getScheduleId());

            model = new AlarmExtended(alarm);
            model.setSchedule(schedule);
        }

        return  model;
    }
}
