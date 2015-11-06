package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineAdd;

class PresenterMedicineAdd implements IPresenterMedicineAdd {
    private IViewMedicineAdd mView;

    public PresenterMedicineAdd(IViewMedicineAdd pView) {
        this.mView = pView;
    }

    @Override
    public void insert(String pMedicineName, int pMedicineCategoryId, String pMedicineImagePath, String pMedicineNote) {

    }
}
