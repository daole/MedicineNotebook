package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;

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
                long rowId = pCursor.getLong(Table.COLUMN_INDEX__ID);
                String medicineCategoryName = pCursor.getString(TableMedicineCategory.COLUMN_INDEX__MEDICINE_CATEGORY_NAME);
                String medicineCategoryNote = pCursor.getString(TableMedicineCategory.COLUMN_INDEX__MEDICINE_CATEGORY_NOTE);

                MedicineCategory model = new MedicineCategory();
                model.setId(rowId);
                model.setMedicineCategoryName(medicineCategoryName);
                model.setMedicineCategoryNote(medicineCategoryNote);

                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }
}
