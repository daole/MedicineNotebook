package com.dreamdigitizers.drugmanagement.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.fragments.screens.Screen;

public class MyActivity  extends AppCompatActivity {
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
}
