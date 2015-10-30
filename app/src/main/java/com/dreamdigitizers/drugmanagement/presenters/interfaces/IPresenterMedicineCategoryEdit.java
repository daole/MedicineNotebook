package com.dreamdigitizers.drugmanagement.presenters.interfaces;

public interface IPresenterMedicineCategoryEdit extends IPresenter {
    void select(long pRowId);
    void edit(long pRowId, String pMedicineCategoryName, String pMedicineCategoryNote);
}
