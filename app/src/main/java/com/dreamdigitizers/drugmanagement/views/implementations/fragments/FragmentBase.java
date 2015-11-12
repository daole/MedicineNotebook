package com.dreamdigitizers.drugmanagement.views.implementations.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentBase extends Fragment {
    private static final String ERROR_MESSAGE__CONTEXT_NOT_IMPLEMENTS_INTERFACE = "Activity must implement IStateChecker.";

    protected IStateChecker mStateChecker;

    @Override
    public void onAttach(Context pContext) {
        super.onAttach(pContext);

        try {
            this.mStateChecker = (IStateChecker)pContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(FragmentBase.ERROR_MESSAGE__CONTEXT_NOT_IMPLEMENTS_INTERFACE);
        }
    }

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        Bundle extras = this.getActivity().getIntent().getExtras();
        if(extras != null) {
            this.handleExtras(extras);
        }

        Bundle arguments = this.getArguments();
        if(arguments != null) {
            this.retrieveArguments(arguments);
        }

        if(pSavedInstanceState != null) {
            this.recoverInstanceState(pSavedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, Bundle pSavedInstanceState) {
        View view = this.loadView(pInflater, pContainer);
        this.retrieveScreenItems(view);
        this.mapInformationToScreenItems(view);
        this.setHasOptionsMenu(true);
        return view;
    }

    @Override
    final public void onCreateOptionsMenu(Menu pMenu, MenuInflater pInflater) {
        if(!this.mStateChecker.isBeingCovered(this)) {
            this.createOptionsMenu(pMenu, pInflater);
            ActionBar actionBar = this.getActionBar();
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(this.getTitle());
        }
    }

    public boolean onBackPressed() {
        return false;
    }

    protected ActionBar getActionBar() {
        return ((AppCompatActivity)this.getActivity()).getSupportActionBar();
    }

    protected void createOptionsMenu(Menu pMenu, MenuInflater pInflater) {

    }

    protected void handleExtras(Bundle pExtras) {

    }

    protected void retrieveArguments(Bundle pArguments) {

    }

    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    protected abstract View loadView(LayoutInflater pInflater, ViewGroup pContainer);
    protected abstract void retrieveScreenItems(View pView);
    protected abstract void mapInformationToScreenItems(View pView);
    protected abstract int getTitle();

    public interface IStateChecker {
        boolean isRecreated();
        boolean isBeingCovered(FragmentBase pFragment);
    }
}
