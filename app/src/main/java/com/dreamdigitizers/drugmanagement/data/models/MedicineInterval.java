package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;

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
                long rowId = pCursor.getLong(Table.COLUMN_INDEX__ID);
                String medicineIntervalName = pCursor.getString(TableMedicineInterval.COLUMN_INDEX__MEDICINE_INTERVAL_NAME);
                int medicineIntervalValue = pCursor.getInt(TableMedicineInterval.COLUMN_INDEX__MEDICINE_INTERVAL_VALUE);

                MedicineInterval model = new MedicineInterval();
                model.setId(rowId);
                model.setMedicineIntervalName(medicineIntervalName);
                model.setMedicineIntervalValue(medicineIntervalValue);

                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }
}
