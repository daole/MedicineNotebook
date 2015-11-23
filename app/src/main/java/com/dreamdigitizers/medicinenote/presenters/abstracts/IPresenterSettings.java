package com.dreamdigitizers.medicinenote.presenters.abstracts;

public interface IPresenterSettings extends IPresenter {
    String getPreferredLanguage();
    void setPreferredLanguage(String pLanguage);
}
