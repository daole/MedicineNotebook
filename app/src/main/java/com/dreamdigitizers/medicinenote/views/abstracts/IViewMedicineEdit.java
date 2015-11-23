package com.dreamdigitizers.medicinenote.views.abstracts;

import android.support.v4.app.LoaderManager;
import android.widget.SpinnerAdapter;

import com.dreamdigitizers.medicinenote.data.models.Medicine;

public interface IViewMedicineEdit extends IViewEdit<Medicine> {
    LoaderManager getViewLoaderManager();
    void setAdapter(SpinnerAdapter pAdapter);
    void onMedicineCategoryDataLoaded();
    void onDataEdited();
}
