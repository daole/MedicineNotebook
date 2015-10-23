package com.dreamdigitizers.drugmanagement.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.fragments.NavigationDrawerFragment;
import com.dreamdigitizers.drugmanagement.fragments.NavigationDrawerFragment.INavigationDrawerCallbacks;

public class MainActivity extends AppCompatActivity implements INavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.setContentView(R.layout.activity__main);

        this.mTitle = this.getTitle();
        this.mNavigationDrawerFragment = (NavigationDrawerFragment)this.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        this.mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int pPosition) {
        // Update the main content by replacing fragments
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(pPosition + 1)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            this.getMenuInflater().inflate(R.menu.main, pMenu);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(pItem);
    }

    public void onSectionAttached(int pNumber) {
        switch (pNumber) {
            case 1:
                this.mTitle = this.getString(R.string.title_section1);
                break;
            case 2:
                this.mTitle = this.getString(R.string.title_section2);
                break;
            case 3:
                this.mTitle = this.getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(this.mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int pSectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, pSectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, Bundle pSavedInstanceState) {
            View rootView = pInflater.inflate(R.layout.fragment__main, pContainer, false);
            return rootView;
        }

        @Override
        public void onAttach(Context pContext) {
            super.onAttach(pContext);
            ((MainActivity) pContext).onSectionAttached(this.getArguments().getInt(PlaceholderFragment.ARG_SECTION_NUMBER));
        }
    }

}
