package com.dreamdigitizers.medicinenote.presenters.abstracts;

public interface IPresenterMedicineCategoryAdd extends IPresenter {
    void insert(String pMedicineCategoryName, String pMedicineCategoryNote);
}
