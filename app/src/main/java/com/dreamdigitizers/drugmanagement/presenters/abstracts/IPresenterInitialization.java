package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterInitialization extends IPresenter {
    boolean isPreferredLanguageAlreadySet();
    String getPreferredLanguage();
    void setPreferredLanguage(String pLanguage);
}
