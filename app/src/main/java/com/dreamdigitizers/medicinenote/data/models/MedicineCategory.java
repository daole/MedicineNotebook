package com.dreamdigitizers.medicinenote.data.models;

import android.database.Cursor;

import com.dreamdigitizers.medicinenote.data.dal.tables.Table;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicineCategory;

import java.util.ArrayList;
import java.util.List;

public class MedicineCategory extends Model {
    private String mMedicineCategoryName;
    private String mMedicineCategoryNote;

    public String getMedicineCategoryName() {
        return this.mMedicineCategoryName;
    }

    public void setMedicineCategoryName(String pMedicineCategoryName) {
        this.mMedicineCategoryName = pMedicineCategoryName;
    }

    public String getMedicineCategoryNote() {
        return this.mMedicineCategoryNote;
    }

    public void setMedicineCategoryNote(String pMedicineCategoryNote) {
        this.mMedicineCategoryNote = pMedicineCategoryNote;
    }

    public static List<MedicineCategory> fetchData(Cursor pCursor) {
        List<MedicineCategory> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                MedicineCategory model = MedicineCategory.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static MedicineCategory fetchDataAtCurrentPosition(Cursor pCursor) {
        return MedicineCategory.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static MedicineCategory fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        MedicineCategory model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
            }
            String medicineCategoryName = pCursor.getString(pCursor.getColumnIndex(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME));
            String medicineCategoryNote = pCursor.getString(pCursor.getColumnIndex(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NOTE));

            model = new MedicineCategory();
            model.setRowId(rowId);
            model.setMedicineCategoryName(medicineCategoryName);
            model.setMedicineCategoryNote(medicineCategoryNote);
        }

        return  model;
    }
}
