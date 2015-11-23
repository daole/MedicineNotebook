package com.dreamdigitizers.medicinenote.presenters.abstracts;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

import com.dreamdigitizers.medicinenote.data.models.Medicine;

public interface IPresenterMedicineSelect extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    boolean checkInputData(Medicine pMedicine, String pDose);
    void close();
}
