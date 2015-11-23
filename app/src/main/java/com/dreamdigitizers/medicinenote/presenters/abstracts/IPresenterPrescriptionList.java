package com.dreamdigitizers.medicinenote.presenters.abstracts;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

public interface IPresenterPrescriptionList extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    void delete();
}
