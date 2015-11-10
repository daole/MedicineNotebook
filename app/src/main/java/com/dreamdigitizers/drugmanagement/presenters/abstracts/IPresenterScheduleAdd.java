package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.util.ArrayMap;

import com.dreamdigitizers.drugmanagement.data.models.FamilyMember;
import com.dreamdigitizers.drugmanagement.data.models.MedicineInterval;
import com.dreamdigitizers.drugmanagement.data.models.MedicineTime;

public interface IPresenterScheduleAdd extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    void insert(FamilyMember pFamilyMember,
                ArrayMap pTakenMedicines,
                String pStartDate,
                MedicineTime pMedicineTime,
                MedicineInterval pMedicineInterval,
                boolean pIsAlarm,
                String pAlarmTimes,
                String pScheduleNote);
}
