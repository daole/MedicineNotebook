package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;

import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeList;

class PresenterMedicineTimeList implements IPresenterMedicineTimeList {
    private IViewMedicineTimeList mViewMedicineTimeList;

    public PresenterMedicineTimeList(IViewMedicineTimeList pViewMedicineTimeList) {
        this.mViewMedicineTimeList = pViewMedicineTimeList;
    }

    @Override
    public void delete() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
