package com.dreamdigitizers.medicinenote.views.implementations.activities;

import android.os.Bundle;
import android.view.WindowManager;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.utils.AdListener;
import com.dreamdigitizers.medicinenote.views.implementations.fragments.screens.ScreenAlarm;
import com.google.android.gms.ads.AdView;

public class ActivityAlarm extends ActivityBase {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.setContentView(R.layout.activity__alarm);
        this.mAdView = (AdView)this.findViewById(R.id.adView);
        this.mAdView.setAdListener(new AdListener(this.mAdView));
        if(!this.mIsRecreated) {
            this.setStartScreen();
        }
    }

    private void setStartScreen() {
        ScreenAlarm screen = new ScreenAlarm();
        this.changeScreen(screen);
    }
}
