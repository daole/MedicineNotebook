package com.dreamdigitizers.drugmanagement.views.implementations.activities;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenAlarm;

public class ActivityAlarm extends ActivityBase {
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
