package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.dreamdigitizers.drugmanagement.data.ContentProviderMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;
import com.dreamdigitizers.drugmanagement.data.models.AlarmExtended;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicineExtended;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterAlarm;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewAlarm;

import java.util.List;

class PresenterAlarm implements IPresenterAlarm {
    private IViewAlarm mView;

    public PresenterAlarm(IViewAlarm pView) {
        this.mView = pView;
    }

    @Override
    public void select(long pRowId) {
        String[] projection = new String[0];
        projection = TableAlarm.getColumnsForJoin().toArray(projection);
        Uri uri = ContentProviderMedicine.CONTENT_URI__ALARM;
        uri = ContentUris.withAppendedId(uri, pRowId);
        Cursor alarmCursor = this.mView.getViewContext().getContentResolver().query(uri, projection, null, null, null);
        if(alarmCursor != null) {
            List<AlarmExtended> list = AlarmExtended.fetchExtendedData(alarmCursor);
            if(list.size() > 0) {
                AlarmExtended model = list.get(0);

                projection = new String[0];
                projection = TableTakenMedicine.getColumnsForJoin().toArray(projection);
                String selection = TableTakenMedicine.COLUMN_NAME__SCHEDULE_ID + " = " + model.getSchedule().getRowId();
                uri = ContentProviderMedicine.CONTENT_URI__TAKEN_MEDICINE;
                Cursor takenMedicineCursor = this.mView.getViewContext().getContentResolver().query(uri, projection, selection, null, null);
                List<TakenMedicineExtended> takenMedicines = TakenMedicineExtended.fetchExtendedData(takenMedicineCursor);
                model.getSchedule().setTakenMedicines(takenMedicines);

                this.mView.bindData(model);
            }
        }
    }

    @Override
    public boolean setAlarmDone(long pRowId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableAlarm.COLUMN_NAME__IS_DONE, true);

        Uri uri = ContentProviderMedicine.CONTENT_URI__ALARM;
        uri = ContentUris.withAppendedId(uri, pRowId);
        int affectedRows = this.mView.getViewContext().getContentResolver().update(
                uri, contentValues, null, null);

        return affectedRows >= 0 ? true : false;
    }
}
