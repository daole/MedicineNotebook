package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

import com.dreamdigitizers.drugmanagement.data.models.Medicine;

public interface IPresenterMedicineSelect extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    boolean checkInputData(Medicine pMedicine, String pDose);
    void close();
}
