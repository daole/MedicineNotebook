package com.dreamdigitizers.drugmanagement.presenters.interfaces;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

public interface IPresenterMedicineCategoryList extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    void delete();
}
