package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterInitialization;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewInitialization;

class PresenterInitialization implements IPresenterInitialization {
    private IViewInitialization mView;
    private SharedPreferences mSharedPreferences;

    public PresenterInitialization(IViewInitialization pView) {
        this.mView = pView;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mView.getViewContext());
    }

    @Override
    public boolean isPreferredLanguageAlreadySet() {
        String preferredLanguage = this.getPreferredLanguage();
        return !TextUtils.isEmpty(preferredLanguage);
    }

    @Override
    public String getPreferredLanguage() {
        String preferredLanguage = this.mSharedPreferences.getString(Constants.SHARED_PREFERENCES_FLAG__LOCALE, "");
        return preferredLanguage;
    }

    @Override
    public void setPreferredLanguage(String pLanguage) {
        SharedPreferences.Editor editor = this.mSharedPreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCES_FLAG__LOCALE, pLanguage);
        editor.commit();
    }
}
