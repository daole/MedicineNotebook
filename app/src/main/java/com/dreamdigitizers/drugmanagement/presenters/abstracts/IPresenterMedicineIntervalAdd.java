package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterMedicineIntervalAdd extends IPresenter {
    void insert(String pMedicineIntervalName, String pMedicineIntervalValue);
}
