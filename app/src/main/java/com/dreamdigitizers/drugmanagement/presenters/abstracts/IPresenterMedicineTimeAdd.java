package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterMedicineTimeAdd extends IPresenter {
    void insert(String pMedicineTimeName, String pMedicineTimeValue);
}
