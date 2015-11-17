package com.dreamdigitizers.drugmanagement.views.abstracts;

import android.support.v4.app.LoaderManager;
import android.widget.SpinnerAdapter;

import com.dreamdigitizers.drugmanagement.data.models.PrescriptionExtended;

public interface IViewPrescriptionEdit extends IViewEdit<PrescriptionExtended> {
    LoaderManager getViewLoaderManager();
    void setAdapter(SpinnerAdapter pAdapter);
    void onDataEdited();
}
