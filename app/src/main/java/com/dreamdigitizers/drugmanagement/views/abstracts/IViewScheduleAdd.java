package com.dreamdigitizers.drugmanagement.views.abstracts;

import android.support.v4.app.LoaderManager;
import android.widget.SpinnerAdapter;

public interface IViewScheduleAdd extends IViewAdd {
    LoaderManager getViewLoaderManager();
    void setFamilyMemberAdapter(SpinnerAdapter pAdapter);
    //void setMedicineAdapter(SpinnerAdapter pAdapter);
    void setMedicineTimeAdapter(SpinnerAdapter pAdapter);
    void setMedicineIntervalAdapter(SpinnerAdapter pAdapter);
}
