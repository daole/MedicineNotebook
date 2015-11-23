package com.dreamdigitizers.medicinenote.presenters.abstracts;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

public interface IPresenterScheduleList extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    void delete();
    void previous();
    void next();
}
