package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.ContentProviderMedicine;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;
import com.dreamdigitizers.drugmanagement.data.models.AlarmExtended;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicineExtended;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleEdit;
import com.dreamdigitizers.drugmanagement.utils.AlarmUtils;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleEdit;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityAlarm;

import java.util.List;

class PresenterScheduleEdit implements IPresenterScheduleEdit {
    private IViewScheduleEdit mView;
    private AlarmExtended mModel;

    public PresenterScheduleEdit(IViewScheduleEdit pView) {
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
        if (alarmCursor != null) {
            List<AlarmExtended> list = AlarmExtended.fetchExtendedData(alarmCursor);
            if(list.size() > 0) {
                this.mModel = list.get(0);

                projection = new String[0];
                projection = TableTakenMedicine.getColumnsForJoin().toArray(projection);
                String selection = TableTakenMedicine.COLUMN_NAME__SCHEDULE_ID + " = " + this.mModel.getSchedule().getRowId();
                uri = ContentProviderMedicine.CONTENT_URI__TAKEN_MEDICINE;
                Cursor takenMedicineCursor = this.mView.getViewContext().getContentResolver().query(uri, projection, selection, null, null);
                List<TakenMedicineExtended> takenMedicines = TakenMedicineExtended.fetchExtendedData(takenMedicineCursor);
                takenMedicineCursor.close();
                this.mModel.getSchedule().setTakenMedicines(takenMedicines);

                this.mView.bindData(this.mModel);
            }
            alarmCursor.close();
        }
    }

    @Override
    public void changeAlarmStatus(boolean pIsAlarm) {
        if(this.mModel != null) {
            long rowId = this.mModel.getRowId();
            int year = this.mModel.getAlarmYear();
            int month = this.mModel.getAlarmMonth();
            int date = this.mModel.getAlarmDate();
            int hour = this.mModel.getAlarmHour();
            int minute = this.mModel.getAlarmMinute();

            boolean result = this.updateAlarmStatus(rowId, pIsAlarm);
            if(result) {
                this.updateAlarmIntent(rowId, year, month, date, hour, minute, pIsAlarm);
            }
        }
    }

    private boolean updateAlarmStatus(long pRowId, boolean pIsAlarm) {
        boolean result = false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableAlarm.COLUMN_NAME__IS_ALARM, pIsAlarm);

        Uri uri = ContentProviderMedicine.CONTENT_URI__ALARM;
        uri = ContentUris.withAppendedId(uri, pRowId);
        int affectedRows = this.mView.getViewContext().getContentResolver().update(
                uri, contentValues, null, null);
        if(affectedRows == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            this.mView.showError(R.string.error__duplicated_data);
        } else if(affectedRows == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            this.mView.showError(R.string.error__unknown_error);
        } else {
            this.mView.showMessage(R.string.message__edit_successful);
            result = true;
        }

        return result;
    }

    private void updateAlarmIntent(long pRowId, int pYear, int pMonth, int pDate, int pHour, int pMinute, boolean pIsAlarm) {
        Bundle extras = new Bundle();
        extras.putLong(Constants.BUNDLE_KEY__ROW_ID, pRowId);
        PendingIntent pendingIntent = AlarmUtils.createPendingIntent(this.mView.getViewContext(),
                ActivityAlarm.class,
                (int)pRowId,
                Intent.FLAG_ACTIVITY_NEW_TASK,
                extras);

        if(pIsAlarm) {
            AlarmUtils.setAlarm(this.mView.getViewContext(), pendingIntent, pYear, pMonth, pDate, pHour, pMinute);
        } else {
            AlarmUtils.cancelAlarm(this.mView.getViewContext(), pendingIntent);
        }
    }
}
