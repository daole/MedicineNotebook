package com.dreamdigitizers.medicinenote.data.models;

import android.database.Cursor;

import com.dreamdigitizers.medicinenote.data.dal.tables.Table;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicineInterval;

import java.util.ArrayList;
import java.util.List;

public class MedicineInterval extends Model {
    private String mMedicineIntervalName;
    private int mMedicineIntervalValue;

    public String getMedicineIntervalName() {
        return this.mMedicineIntervalName;
    }

    public void setMedicineIntervalName(String pMedicineIntervalName) {
        this.mMedicineIntervalName = pMedicineIntervalName;
    }

    public int getMedicineIntervalValue() {
        return this.mMedicineIntervalValue;
    }

    public void setMedicineIntervalValue(int pMedicineIntervalValue) {
        this.mMedicineIntervalValue = pMedicineIntervalValue;
    }

    public static List<MedicineInterval> fetchData(Cursor pCursor) {
        List<MedicineInterval> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                MedicineInterval model = MedicineInterval.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static MedicineInterval fetchDataAtCurrentPosition(Cursor pCursor) {
        return MedicineInterval.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static MedicineInterval fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        MedicineInterval model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
            }
            String medicineIntervalName = pCursor.getString(pCursor.getColumnIndex(TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_NAME));
            int medicineIntervalValue = pCursor.getInt(pCursor.getColumnIndex(TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_VALUE));

            model = new MedicineInterval();
            model.setRowId(rowId);
            model.setMedicineIntervalName(medicineIntervalName);
            model.setMedicineIntervalValue(medicineIntervalValue);
        }

        return  model;
    }
}
