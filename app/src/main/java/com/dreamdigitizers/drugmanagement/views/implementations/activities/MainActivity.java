package com.dreamdigitizers.drugmanagement.views.implementations.activities;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.MyFragment;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.NavigationDrawerFragment;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.NavigationDrawerFragment.INavigationDrawerItemSelectListener;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.Screen;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenFamilyMemberList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenMedicineIntervalList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenMedicineList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenMedicineTimeList;
import com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens.ScreenSchedule;

public class MainActivity extends MyActivity implements INavigationDrawerItemSelectListener {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.setContentView(R.layout.activity__main);

        this.mNavigationDrawerFragment = (NavigationDrawerFragment)this.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        this.mNavigationDrawerFragment.setUp(R.array.navigation_drawer_icons, R.array.navigation_drawer_titles, R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        if(!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            this.getMenuInflater().inflate(R.menu.menu__main, pMenu);
            this.restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(pMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = pItem.getItemId();

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
            this.changeScreen(new ScreenSchedule());
        }
    }

    @Override
    public boolean isBeingCovered(MyFragment pFragment) {
        return this.mNavigationDrawerFragment.isDrawerOpen() && !(pFragment instanceof NavigationDrawerFragment);
    }

    @Override
    public void onNavigationDrawerItemSelected(int pPosition) {
        // Update the menu__main content by replacing fragments
        Screen screen = null;
        switch (pPosition) {
            case Constants.NAVIGATION_DRAWER_ITEM_ID__SCHEDULE:
                if(!(this.mCurrentScreen instanceof ScreenSchedule)) {
                    screen = new ScreenSchedule();
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
