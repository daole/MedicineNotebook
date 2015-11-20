package com.dreamdigitizers.drugmanagement;

import android.app.Application;
import android.content.res.Configuration;
import android.text.TextUtils;

import java.util.Locale;

public class ApplicationBase extends Application {
    private String mLanguage;

    @Override
    public void onConfigurationChanged(Configuration pNewConfig) {
        super.onConfigurationChanged(pNewConfig);
        if(!TextUtils.isEmpty(this.mLanguage)) {
            this.setLocale(this.mLanguage);
        }
    }

    public void setLocale(String pLanguage) {
        this.mLanguage = pLanguage;
        Configuration configuration = new Configuration();
        Locale locale = new Locale(this.mLanguage);
        Locale.setDefault(locale);
        configuration.locale = locale;
        this.getResources().updateConfiguration(configuration, null);
    }
}
