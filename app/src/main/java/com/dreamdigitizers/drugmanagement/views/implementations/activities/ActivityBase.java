package com.dreamdigitizers.drugmanagement.views.implementations.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.FragmentBase;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.Screen;

public class ActivityBase extends AppCompatActivity implements FragmentBase.IBeingCoveredChecker, Screen.IOnScreenActionsListener {
    protected static final String BUNDLE_KEY__DUMP = "dump";
    protected static final String BUNDLE_VALUE__DUMP = "dump";

    protected Screen mCurrentScreen;
    protected boolean mIsRecreated;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        if(pSavedInstanceState != null) {
            this.recoverInstanceState(pSavedInstanceState);
        }

        Bundle extras = this.getIntent().getExtras();
        if(extras != null) {
            this.handleExtras(extras);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putString(ActivityBase.BUNDLE_KEY__DUMP, ActivityBase.BUNDLE_VALUE__DUMP);
    }

    @Override
    public void onBackPressed() {
        if(this.mCurrentScreen != null) {
            boolean isHandled = this.mCurrentScreen.onBackPressed();
            if(isHandled) {
                return;
            }
        }

        this.back();
    }

    @Override
    public boolean isBeingCovered(FragmentBase pFragment) {
        return false;
    }

    @Override
    public void onSetCurrentScreen(Screen pCurrentScreen) {
        this.mCurrentScreen = pCurrentScreen;
    }

    @Override
    public void onChangeScreen(Screen pScreen) {
        this.changeScreen(pScreen);
    }

    @Override
    public void returnActivityResult(int pResultCode, Intent pData) {
        this.setResult(pResultCode, pData);
        this.finish();
    }

    @Override
    public void onBack() {
        this.back();
    }

    public void changeScreen(Screen pScreen) {
        this.changeScreen(pScreen, true, true);
    }

    public void changeScreen(Screen pScreen, boolean pIsUseAnimation) {
        this.changeScreen(pScreen, true, pIsUseAnimation);
    }

    public void changeScreen(Screen pScreen, boolean pIsUseAnimation, boolean pIsAddToTransaction) {
        try {
            if (pScreen != null) {
                String className = pScreen.getClass().getName();

				boolean result = this.getSupportFragmentManager().popBackStackImmediate(className, 0);
                if (result) {
					return;
				}

                FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

                if(pIsUseAnimation) {
                    //fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_right, R.animator.slide_out_right);
                }

                fragmentTransaction.replace(R.id.container, pScreen);

                if (pIsAddToTransaction) {
                    fragmentTransaction.addToBackStack(className);
                }

                fragmentTransaction.commit();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            this.finish();
        }
    }

    public void back() {
        int backStackEntryCount = this.getSupportFragmentManager().getBackStackEntryCount();
        if(backStackEntryCount <= 1) {
            this.finish();
            return;
        }

        boolean result = this.getSupportFragmentManager().popBackStackImmediate();
        if (!result) {
            this.finish();
        }
    }

    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mIsRecreated = true;
    }

    protected void handleExtras(Bundle pExtras) {

    }
}
