package com.dreamdigitizers.medicinenote.presenters.abstracts;

public interface IPresenterMedicineIntervalEdit extends IPresenter {
    void select(long pRowId);
    void edit(long pRowId, String pMedicineIntervalName, String pMedicineIntervalValue);
}
