package com.dreamdigitizers.medicinenote.data.models;

import android.database.Cursor;

import com.dreamdigitizers.medicinenote.data.dal.tables.Table;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableTakenMedicine;

import java.util.ArrayList;
import java.util.List;

public class TakenMedicine extends Model {
    private long mScheduleId;
    private long mMedicineId;
    private String mFallbackMedicineName;
    private String mDose;

    public long getScheduleId() {
        return this.mScheduleId;
    }

    public void setScheduleId(long pScheduleId) {
        this.mScheduleId = pScheduleId;
    }

    public long getMedicineId() {
        return this.mMedicineId;
    }

    public void setMedicineId(long pMedicineId) {
        this.mMedicineId = pMedicineId;
    }

    public String getFallbackMedicineName() {
        return this.mFallbackMedicineName;
    }

    public void setFallbackMedicineName(String pFallbackMedicineName) {
        this.mFallbackMedicineName = pFallbackMedicineName;
    }

    public String getDose() {
        return this.mDose;
    }

    public void setDose(String pDose) {
        this.mDose = pDose;
    }

    public static List<TakenMedicine> fetchData(Cursor pCursor) {
        List<TakenMedicine> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                TakenMedicine model = TakenMedicine.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static TakenMedicine fetchDataAtCurrentPosition(Cursor pCursor) {
        return TakenMedicine.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static TakenMedicine fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        TakenMedicine model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                int rowIdColumnIndex = pCursor.getColumnIndex(TableTakenMedicine.COLUMN_ALIAS__ID);
                if(rowIdColumnIndex < 0) {
                    rowIdColumnIndex = pCursor.getColumnIndex(Table.COLUMN_NAME__ID);
                }
                rowId = pCursor.getLong(rowIdColumnIndex);
            }
            long scheduleId = pCursor.getLong(pCursor.getColumnIndex(TableTakenMedicine.COLUMN_NAME__SCHEDULE_ID));
            long medicineId = pCursor.getLong(pCursor.getColumnIndex(TableTakenMedicine.COLUMN_NAME__MEDICINE_ID));
            String fallbackMedicineName = pCursor.getString(pCursor.getColumnIndex(TableTakenMedicine.COLUMN_NAME__FALLBACK_MEDICINE_NAME));
            String dose = pCursor.getString(pCursor.getColumnIndex(TableTakenMedicine.COLUMN_NAME__DOSE));

            model = new TakenMedicine();
            model.setRowId(rowId);
            model.setScheduleId(scheduleId);
            model.setMedicineId(medicineId);
            model.setFallbackMedicineName(fallbackMedicineName);
            model.setDose(dose);
        }

        return  model;
    }
}
