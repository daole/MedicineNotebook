package com.dreamdigitizers.drugmanagement.views.implementations.activities;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.utils.AdListener;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.FragmentBase;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.FragmentNavigationDrawer;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.FragmentNavigationDrawer.INavigationDrawerItemSelectListener;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.Screen;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenFamilyMemberList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenMedicineIntervalList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenMedicineList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenMedicineTimeList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenPrescriptionList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenScheduleList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenSettings;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ActivityMain extends ActivityBase implements INavigationDrawerItemSelectListener {
    private FragmentNavigationDrawer mNavigationDrawerFragment;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.setContentView(R.layout.activity__main);
        this.mNavigationDrawerFragment = (FragmentNavigationDrawer)this.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        this.mNavigationDrawerFragment.setUp(R.array.navigation_drawer_icons, R.array.navigation_drawer_titles, R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));
        this.mAdView = (AdView)this.findViewById(R.id.adView);
        this.mAdView.setAdListener(new AdListener(this.mAdView));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        if(!mNavigationDrawerFragment.isDrawerOpen()) {
            this.getMenuInflater().inflate(R.menu.menu__main, pMenu);
            this.restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(pMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        return super.onOptionsItemSelected(pItem);
    }

    @Override
    public void onBackPressed() {
        if(this.mNavigationDrawerFragment.isDrawerOpen()){
            this.mNavigationDrawerFragment.closeDrawer();
        } else {
            if(this.mCurrentScreen != null) {
                boolean isHandled = this.mCurrentScreen.onBackPressed();
                if(isHandled) {
                    return;
                }
            }

            int backStackEntryCount = this.getSupportFragmentManager().getBackStackEntryCount();
            if(backStackEntryCount <= 1) {
                this.finish();
                return;
            }

            this.mNavigationDrawerFragment.setItemChecked(Constants.NAVIGATION_DRAWER_ITEM_ID__SCHEDULE);
            this.changeScreen(new ScreenScheduleList());
        }
    }

    @Override
    public boolean isBeingCovered(FragmentBase pFragment) {
        return this.mNavigationDrawerFragment.isDrawerOpen() && !(pFragment instanceof FragmentNavigationDrawer);
    }

    @Override
    public void onNavigationDrawerItemSelected(int pPosition) {
        Screen screen = null;
        switch (pPosition) {
            case Constants.NAVIGATION_DRAWER_ITEM_ID__SCHEDULE:
                if(!(this.mCurrentScreen instanceof ScreenScheduleList)) {
                    screen = new ScreenScheduleList();
                }
                break;
            case Constants.NAVIGATION_DRAWER_ITEM_ID__FAMILY:
                if(!(this.mCurrentScreen instanceof ScreenFamilyMemberList)) {
                    screen = new ScreenFamilyMemberList();
                }
                break;
            case Constants.NAVIGATION_DRAWER_ITEM_ID__MEDICINES:
                if(!(this.mCurrentScreen instanceof ScreenMedicineList)) {
                    screen = new ScreenMedicineList();
                }
                break;
            case Constants.NAVIGATION_DRAWER_ITEM_ID__MEDICINE_CATEGORIES:
                if(!(this.mCurrentScreen instanceof ScreenMedicineCategoryList)) {
                    screen = new ScreenMedicineCategoryList();
                }
                break;
            case Constants.NAVIGATION_DRAWER_ITEM_ID__MEDICINE_TIME:
                if(!(this.mCurrentScreen instanceof ScreenMedicineTimeList)) {
                    screen = new ScreenMedicineTimeList();
                }
                break;
            case Constants.NAVIGATION_DRAWER_ITEM_ID__MEDICINE_INTERVALS:
                if(!(this.mCurrentScreen instanceof ScreenMedicineIntervalList)) {
                    screen = new ScreenMedicineIntervalList();
                }
                break;
            case Constants.NAVIGATION_DRAWER_ITEM_ID__MEDICINE_PRESCRIPTIONS:
                if(!(this.mCurrentScreen instanceof ScreenPrescriptionList)) {
                    screen = new ScreenPrescriptionList();
                }
                break;
            case Constants.NAVIGATION_DRAWER_ITEM_ID__SETTINGS:
                if(!(this.mCurrentScreen instanceof ScreenSettings)) {
                    screen = new ScreenSettings();
                }
                break;
            default:
                break;
        }

        if(screen != null) {
            this.changeScreen(screen);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
    }
}
