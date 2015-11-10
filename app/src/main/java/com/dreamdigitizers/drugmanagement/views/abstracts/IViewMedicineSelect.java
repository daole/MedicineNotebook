package com.dreamdigitizers.drugmanagement.views.abstracts;

import android.support.v4.app.LoaderManager;
import android.widget.SpinnerAdapter;

public interface IViewMedicineSelect extends IView {
    LoaderManager getViewLoaderManager();
    void setAdapter(SpinnerAdapter pAdapter);
}
