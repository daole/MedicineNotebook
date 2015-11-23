package com.dreamdigitizers.medicinenote.views.abstracts;

import android.support.v4.app.LoaderManager;
import android.widget.SpinnerAdapter;

public interface IViewPrescriptionAdd extends IViewAdd {
    LoaderManager getViewLoaderManager();
    void setAdapter(SpinnerAdapter pAdapter);
}
