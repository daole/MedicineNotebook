package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class TakenMedicineExtended extends TakenMedicine {
    private ScheduleExtended mSchedule;
    private MedicineExtended mMedicine;

    public TakenMedicineExtended() {

    }

    public TakenMedicineExtended(TakenMedicine pTakenMedicine) {
        this.setScheduleId(pTakenMedicine.getScheduleId());
        this.setMedicineId(pTakenMedicine.getMedicineId());
        this.setFallbackMedicineName(pTakenMedicine.getFallbackMedicineName());
        this.setDose(pTakenMedicine.getDose());
    }

    public ScheduleExtended getSchedule() {
        return this.mSchedule;
    }

    public void setSchedule(ScheduleExtended pSchedule) {
        this.mSchedule = pSchedule;
    }

    public MedicineExtended getMedicine() {
        return this.mMedicine;
    }

    public void setMedicine(MedicineExtended pMedicine) {
        this.mMedicine = pMedicine;
    }

    public static List<TakenMedicineExtended> fetchExtendedData(Cursor pCursor) {
        List<TakenMedicineExtended> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                TakenMedicineExtended model = TakenMedicineExtended.fetchExtendedDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static TakenMedicineExtended fetchExtendedDataAtCurrentPosition(Cursor pCursor) {
        return TakenMedicineExtended.fetchExtendedDataAtCurrentPosition(pCursor, 0);
    }

    public static TakenMedicineExtended fetchExtendedDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        TakenMedicineExtended model = null;

        if(pCursor != null) {
            TakenMedicine takenMedicine = TakenMedicine.fetchDataAtCurrentPosition(pCursor, pRowId);
            MedicineExtended medicine = MedicineExtended.fetchExtendedDataAtCurrentPosition(pCursor, takenMedicine.getMedicineId());

            model = new TakenMedicineExtended(takenMedicine);
            model.setMedicine(medicine);
        }

        return  model;
    }
}
