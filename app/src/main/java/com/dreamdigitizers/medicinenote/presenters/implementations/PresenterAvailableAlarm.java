package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.dreamdigitizers.medicinenote.Constants;
import com.dreamdigitizers.medicinenote.data.ContentProviderMedicine;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableAlarm;
import com.dreamdigitizers.medicinenote.data.models.Alarm;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterAvailableAlarm;
import com.dreamdigitizers.medicinenote.utils.AlarmUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewAvailableAlarm;
import com.dreamdigitizers.medicinenote.views.implementations.activities.ActivityAlarm;

import java.util.List;

class PresenterAvailableAlarm implements IPresenterAvailableAlarm {
    private IViewAvailableAlarm mView;

    public PresenterAvailableAlarm(IViewAvailableAlarm pView) {
        this.mView = pView;
        List<Alarm> alarms = this.select();
        this.setAlarms(alarms);
    }

    private List<Alarm> select() {
        String[] projection = new String[0];
        projection = TableAlarm.getColumns(true, true).toArray(projection);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TableAlarm.COLUMN_NAME__IS_ALARM);
        stringBuilder.append(" = 1 AND ");
        stringBuilder.append(TableAlarm.COLUMN_NAME__IS_DONE);
        stringBuilder.append(" = 0");
        String selection = stringBuilder.toString();
        Cursor cursor = this.mView.getViewContext().getContentResolver().query(ContentProviderMedicine.CONTENT_URI__ALARM, projection, selection, null, null);
        List<Alarm> alarms = Alarm.fetchData(cursor);
        return  alarms;
    }

    private void setAlarms(List<Alarm> pAlarms) {
        for(Alarm alarm : pAlarms) {
            long rowId = alarm.getRowId();
            int year = alarm.getAlarmYear();
            int month = alarm.getAlarmMonth();
            int date = alarm.getAlarmDate();
            int hour = alarm.getAlarmHour();
            int minute = alarm.getAlarmMinute();

            Bundle extras = new Bundle();
            extras.putLong(Constants.BUNDLE_KEY__ROW_ID, rowId);
            PendingIntent pendingIntent = AlarmUtils.createPendingIntent(this.mView.getViewContext(),
                    ActivityAlarm.class,
                    (int)rowId,
                    Intent.FLAG_ACTIVITY_NEW_TASK,
                    extras);

            AlarmUtils.setAlarm(this.mView.getViewContext(), pendingIntent, year, month, date, hour, minute);
        }
    }
}
