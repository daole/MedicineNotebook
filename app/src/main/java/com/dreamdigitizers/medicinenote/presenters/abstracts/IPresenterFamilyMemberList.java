package com.dreamdigitizers.medicinenote.presenters.abstracts;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;

public interface IPresenterFamilyMemberList extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    void delete();
}
