package com.dreamdigitizers.medicinenote.views.abstracts;

import android.support.v4.app.LoaderManager;
import android.widget.SpinnerAdapter;

import com.dreamdigitizers.medicinenote.data.models.PrescriptionExtended;

public interface IViewPrescriptionEdit extends IViewEdit<PrescriptionExtended> {
    LoaderManager getViewLoaderManager();
    void setAdapter(SpinnerAdapter pAdapter);
    void onFamilyMemberDataLoaded();
    void onDataEdited();
}
