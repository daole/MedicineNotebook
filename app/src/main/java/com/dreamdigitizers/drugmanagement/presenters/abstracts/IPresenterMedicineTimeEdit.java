package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterMedicineTimeEdit extends IPresenter {
    void select(long pRowId);
    void edit(long pRowId, String pMedicineTimeName, String pMedicineTimeValue);
}
