package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterMedicineCategoryAdd extends IPresenter {
    void insert(String pMedicineCategoryName, String pMedicineCategoryNote);
}
