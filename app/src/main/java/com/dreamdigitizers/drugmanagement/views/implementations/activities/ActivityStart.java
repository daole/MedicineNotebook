package com.dreamdigitizers.drugmanagement.views.implementations.activities;

import android.os.Bundle;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenInitialization;

public class ActivityStart extends ActivityBase {
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.setContentView(R.layout.activity__base);
        if(!this.mIsRecreated) {
            this.setStartScreen();
        }
    }

    private void setStartScreen() {
        ScreenInitialization screen = new ScreenInitialization();
        this.changeScreen(screen);
    }
}
