package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;

import com.dreamdigitizers.drugmanagement.data.ContentProviderMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;
import com.dreamdigitizers.drugmanagement.data.models.AlarmExtended;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicineExtended;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleEdit;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleEdit;

import java.util.List;

class PresenterScheduleEdit implements IPresenterScheduleEdit {
    private IViewScheduleEdit mView;

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
    public void changeAlarmStatus(boolean pIsAlarm) {

    }
}
