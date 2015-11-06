package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterMedicineAdd extends IPresenter {
    void insert(String pMedicineName, int pMedicineCategoryId, String pMedicineImagePath, String pMedicineNote);
}
