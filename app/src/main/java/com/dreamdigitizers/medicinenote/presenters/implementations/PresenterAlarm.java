package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;

import com.dreamdigitizers.medicinenote.data.ContentProviderMedicine;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableAlarm;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableTakenMedicine;
import com.dreamdigitizers.medicinenote.data.models.AlarmExtended;
import com.dreamdigitizers.medicinenote.data.models.TakenMedicineExtended;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterAlarm;
import com.dreamdigitizers.medicinenote.utils.FileUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewAlarm;

import java.util.List;

class PresenterAlarm implements IPresenterAlarm {
    private IViewAlarm mView;

    public PresenterAlarm(IViewAlarm pView) {
        this.mView = pView;
    }

    @Override
    public Bitmap loadImage(String pFilePath, int pWidth, int pHeight) {
        return FileUtils.decodeSampledBitmapFromFile(pFilePath, pWidth, pHeight);
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
                takenMedicineCursor.close();
                model.getSchedule().setTakenMedicines(takenMedicines);

                this.mView.bindData(model);
            }
            alarmCursor.close();
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
