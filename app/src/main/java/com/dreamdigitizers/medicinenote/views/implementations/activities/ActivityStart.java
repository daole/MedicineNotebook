package com.dreamdigitizers.medicinenote.views.implementations.activities;

import android.os.Bundle;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.views.implementations.fragments.screens.ScreenInitialization;

public class ActivityStart extends ActivityBase {
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.setContentView(R.layout.activity__start);
        if(!this.mIsRecreated) {
            this.setStartScreen();
        }
    }

    private void setStartScreen() {
        ScreenInitialization screen = new ScreenInitialization();
        this.changeScreen(screen);
    }
}
