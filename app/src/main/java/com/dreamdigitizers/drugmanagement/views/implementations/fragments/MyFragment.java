package com.dreamdigitizers.drugmanagement.views.implementations.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class MyFragment extends Fragment {
    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        if(pSavedInstanceState != null) {
            this.recoverInstanceState(pSavedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, Bundle pSavedInstanceState) {
        View view = this.loadView(pInflater, pContainer);
        this.retrieveScreenItems(view);
        this.mapInformationToScreenItems();
        this.setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu pMenu, MenuInflater pInflater) {
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(this.getTitle());
    }

    public boolean onBackPressed() {
        return false;
    }

    protected ActionBar getActionBar() {
        return ((AppCompatActivity)this.getActivity()).getSupportActionBar();
    }

    protected abstract View loadView(LayoutInflater pInflater, ViewGroup pContainer);
    protected abstract void retrieveScreenItems(View pView);
    protected abstract void recoverInstanceState(Bundle pSavedInstanceState);
    protected abstract void mapInformationToScreenItems();
    protected abstract int getTitle();
}
