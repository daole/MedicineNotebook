package com.dreamdigitizers.medicinenote.data.models;

import android.database.Cursor;

import com.dreamdigitizers.medicinenote.data.dal.tables.Table;

import java.util.ArrayList;
import java.util.List;

public class TakenMedicineExtended extends TakenMedicine {
    private ScheduleExtended mSchedule;
    private MedicineExtended mMedicine;

    public TakenMedicineExtended() {

    }

    public TakenMedicineExtended(TakenMedicine pTakenMedicine) {
        this.setRowId(pTakenMedicine.getRowId());
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
        return TakenMedicineExtended.fetchExtendedData(pCursor, false);
    }

    public static List<TakenMedicineExtended> fetchExtendedData(Cursor pCursor, boolean pIsJoint) {
        long currentAlarmId = 0;
        List<TakenMedicineExtended> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                if(pIsJoint) {
                    long alarmId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
                    if (currentAlarmId > 0 && currentAlarmId != alarmId) {
                        pCursor.moveToPrevious();
                        break;
                    }
                    currentAlarmId = alarmId;
                }

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
