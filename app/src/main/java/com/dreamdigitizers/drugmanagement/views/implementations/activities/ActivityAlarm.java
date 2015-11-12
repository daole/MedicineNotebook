package com.dreamdigitizers.drugmanagement.views.implementations.activities;

import android.os.Bundle;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenAlarm;

public class ActivityAlarm extends ActivityBase {
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.setContentView(R.layout.activity__alarm);
        if(!this.mIsRecreated) {
            this.setStartScreen();
        }
    }

    private void setStartScreen() {
        ScreenAlarm screen = new ScreenAlarm();
        this.changeScreen(screen);
    }
}
