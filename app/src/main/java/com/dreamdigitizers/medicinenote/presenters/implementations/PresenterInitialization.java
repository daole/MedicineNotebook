package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.dreamdigitizers.medicinenote.Constants;
import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterInitialization;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewInitialization;

class PresenterInitialization implements IPresenterInitialization {
    private IViewInitialization mView;
    private SharedPreferences mSharedPreferences;

    public PresenterInitialization(IViewInitialization pView) {
        this.mView = pView;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mView.getViewContext());
    }

    @Override
    public void initialize() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this.mView.getViewContext());
        databaseHelper.open(SQLiteDatabase.OPEN_READWRITE);
        databaseHelper.close();
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
