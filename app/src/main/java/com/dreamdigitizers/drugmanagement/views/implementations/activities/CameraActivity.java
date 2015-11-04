package com.dreamdigitizers.drugmanagement.views.implementations.activities;

import android.content.res.Configuration;
import android.os.Bundle;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenCamera;

public class CameraActivity extends MyActivity {
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.setContentView(R.layout.activity__camera);
        this.setStartScreen();
    }

    /*
    @Override
    public void onConfigurationChanged(Configuration pNewConfig) {
        super.onConfigurationChanged(pNewConfig);

        if (pNewConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
        if (pNewConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
    }
    */

    private void setStartScreen() {
        ScreenCamera screen = new ScreenCamera();
        this.changeScreen(screen);
    }
}
