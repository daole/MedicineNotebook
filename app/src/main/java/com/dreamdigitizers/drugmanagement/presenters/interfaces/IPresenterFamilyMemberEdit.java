package com.dreamdigitizers.drugmanagement.presenters.interfaces;

public interface IPresenterFamilyMemberEdit extends IPresenter {
    void select(long pRowId);
    void edit(long pRowId, String pFamilyMemberName);
}
