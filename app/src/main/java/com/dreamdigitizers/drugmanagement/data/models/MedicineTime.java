package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;

import java.util.ArrayList;
import java.util.List;

public class MedicineTime extends Model {
    private String mMedicineTimeName;
    private String[] mMedicineTimeValues;

    public String getMedicineTimeName() {
        return this.mMedicineTimeName;
    }

    public void setMedicineTimeName(String pMedicineTimeName) {
        this.mMedicineTimeName = pMedicineTimeName;
    }

    public String[] getMedicineTimeValues() {
        return this.mMedicineTimeValues;
    }

    public void setMedicineTimeValues(String[] pMedicineTimeValue) {
        this.mMedicineTimeValues = pMedicineTimeValue;
    }

    public static List<MedicineTime> fetchData(Cursor pCursor) {
        List<MedicineTime> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                MedicineTime model = MedicineTime.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static MedicineTime fetchDataAtCurrentPosition(Cursor pCursor) {
        return MedicineTime.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static MedicineTime fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        MedicineTime model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
            }
            String medicineTimeName = pCursor.getString(pCursor.getColumnIndex(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME));
            String medicineTimeValue = pCursor.getString(pCursor.getColumnIndex(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE));
            String[] medicineTimeValues = medicineTimeValue.split(Constants.DELIMITER__DATA);

            model = new MedicineTime();
            model.setRowId(rowId);
            model.setMedicineTimeName(medicineTimeName);
            model.setMedicineTimeValues(medicineTimeValues);
        }

        return  model;
    }
}
