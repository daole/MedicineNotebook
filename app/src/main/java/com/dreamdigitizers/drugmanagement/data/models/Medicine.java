package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;

import java.util.ArrayList;
import java.util.List;

public class Medicine extends Model {
    private long mMedicineCategoryId;
    private String mMedicineName;
    private String mMedicineImagePath;
    private String mMedicineNote;

    public long getMedicineCategoryId() {
        return this.mMedicineCategoryId;
    }

    public void setMedicineCategoryId(long pMedicineCategoryId) {
        this.mMedicineCategoryId = pMedicineCategoryId;
    }

    public String getMedicineName() {
        return mMedicineName;
    }

    public void setMedicineName(String pMedicineName) {
        this.mMedicineName = pMedicineName;
    }

    public String getMedicineImagePath() {
        return this.mMedicineImagePath;
    }

    public void setMedicineImagePath(String pMedicineImagePath) {
        this.mMedicineImagePath = pMedicineImagePath;
    }

    public String getMedicineNote() {
        return this.mMedicineNote;
    }

    public void setMedicineNote(String pMedicineNote) {
        this.mMedicineNote = pMedicineNote;
    }

    public static List<Medicine> fetchData(Cursor pCursor) {
        List<Medicine> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                Medicine model = Medicine.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static Medicine fetchDataAtCurrentPosition(Cursor pCursor) {
        return Medicine.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static Medicine fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        Medicine model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
            }
            long medicineCategoryId = pCursor.getLong(pCursor.getColumnIndex(TableMedicine.COLUMN_NAME__MEDICINE_CATEGORY_ID));
            String medicineName = pCursor.getString(pCursor.getColumnIndex(TableMedicine.COLUMN_NAME__MEDICINE_NAME));
            String medicineImagePath = pCursor.getString(pCursor.getColumnIndex(TableMedicine.COLUMN_NAME__MEDICINE_IMAGE_PATH));
            String medicineNote = pCursor.getString(pCursor.getColumnIndex(TableMedicine.COLUMN_NAME__MEDICINE_NOTE));

            model = new Medicine();
            model.setRowId(rowId);
            model.setMedicineCategoryId(medicineCategoryId);
            model.setMedicineName(medicineName);
            model.setMedicineImagePath(medicineImagePath);
            model.setMedicineNote(medicineNote);
        }

        return  model;
    }
}
