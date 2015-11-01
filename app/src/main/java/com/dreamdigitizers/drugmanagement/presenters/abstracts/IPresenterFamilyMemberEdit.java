package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterFamilyMemberEdit extends IPresenter {
    void select(long pRowId);
    void edit(long pRowId, String pFamilyMemberName);
}
