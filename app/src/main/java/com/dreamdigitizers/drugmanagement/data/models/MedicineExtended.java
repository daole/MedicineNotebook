package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;

import java.util.ArrayList;
import java.util.List;

public class MedicineExtended extends Medicine {
    private MedicineCategory mMedicineCategory;

    public MedicineExtended() {

    }

    public MedicineExtended(Medicine pMedicine) {
        this.setRowId(pMedicine.getRowId());
        this.setMedicineCategoryId(pMedicine.getMedicineCategoryId());
        this.setMedicineName(pMedicine.getMedicineName());
        this.setMedicineImagePath(pMedicine.getMedicineImagePath());
        this.setMedicineNote(pMedicine.getMedicineNote());
    }

    public MedicineCategory getMedicineCategory() {
        return this.mMedicineCategory;
    }

    public void setMedicineCategory(MedicineCategory pMedicineCategory) {
        this.mMedicineCategory = pMedicineCategory;
    }

    public static List<MedicineExtended> fetchExtendedData(Cursor pCursor) {
        List<MedicineExtended> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                MedicineExtended model = MedicineExtended.fetchExtendedDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static MedicineExtended fetchExtendedDataAtCurrentPosition(Cursor pCursor) {
        return MedicineExtended.fetchExtendedDataAtCurrentPosition(pCursor, 0);
    }

    public static MedicineExtended fetchExtendedDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        MedicineExtended model = null;

        if(pCursor != null) {
            Medicine medicine = Medicine.fetchDataAtCurrentPosition(pCursor, pRowId);
            MedicineCategory medicineCategory = MedicineCategory.fetchDataAtCurrentPosition(pCursor, medicine.getMedicineCategoryId());

            model = new MedicineExtended(medicine);
            model.setMedicineCategory(medicineCategory);
        }

        return  model;
    }
}
