package com.dreamdigitizers.medicinenote.presenters.abstracts;

public interface IPresenterInitialization extends IPresenter {
    void initialize();
    boolean isPreferredLanguageAlreadySet();
    String getPreferredLanguage();
    void setPreferredLanguage(String pLanguage);
}
