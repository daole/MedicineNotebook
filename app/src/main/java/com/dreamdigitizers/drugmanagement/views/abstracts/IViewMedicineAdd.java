package com.dreamdigitizers.drugmanagement.views.abstracts;

import android.support.v4.app.LoaderManager;
import android.widget.SpinnerAdapter;

public interface IViewMedicineAdd extends IViewAdd {
    LoaderManager getViewLoaderManager();
    void setAdapter(SpinnerAdapter pAdapter);
}
