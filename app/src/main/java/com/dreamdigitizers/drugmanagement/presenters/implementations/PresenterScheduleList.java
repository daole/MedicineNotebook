package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import com.dreamdigitizers.drugmanagement.data.ContentProviderMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleList;

class PresenterScheduleList implements IPresenterScheduleList {
    private IViewScheduleList mView;
    private SimpleCursorAdapter mAdapter;

    public PresenterScheduleList(IViewScheduleList pView) {
        this.mView = pView;
        this.mView.getViewLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        projection = TableAlarm.getColumns().toArray(projection);
        CursorLoader cursorLoader = new CursorLoader(this.mView.getViewContext(),
                ContentProviderMedicine.CONTENT_URI__SCHEDULE, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> pLoader, Cursor pData) {
        this.mAdapter.swapCursor(pData);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> pLoader) {
        this.mAdapter.swapCursor(null);
    }
}
