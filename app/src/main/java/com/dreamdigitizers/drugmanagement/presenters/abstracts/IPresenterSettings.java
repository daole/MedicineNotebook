package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterSettings extends IPresenter {
    String getPreferredLanguage();
    void setPreferredLanguage(String pLanguage);
}
