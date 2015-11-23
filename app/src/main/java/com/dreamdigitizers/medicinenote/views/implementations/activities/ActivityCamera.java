package com.dreamdigitizers.medicinenote.views.implementations.activities;

import android.os.Bundle;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.views.implementations.fragments.screens.ScreenCamera;

public class ActivityCamera extends ActivityBase {
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.setContentView(R.layout.activity__camera);
        if(!this.mIsRecreated) {
            this.setStartScreen();
        }
    }

    private void setStartScreen() {
        ScreenCamera screen = new ScreenCamera();
        this.changeScreen(screen);
    }
}
