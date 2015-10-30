package com.dreamdigitizers.drugmanagement.presenters.interfaces;

public interface IPresenterMedicineCategoryAdd extends IPresenter {
    void insert(String pMedicineCategoryName, String pMedicineCategoryNote);
}
