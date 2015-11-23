package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dreamdigitizers.medicinenote.Constants;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterSettings;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewSettings;

class PresenterSettings implements IPresenterSettings {
    private IViewSettings mView;
    private SharedPreferences mSharedPreferences;

    public PresenterSettings(IViewSettings pView) {
        this.mView = pView;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mView.getViewContext());
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
