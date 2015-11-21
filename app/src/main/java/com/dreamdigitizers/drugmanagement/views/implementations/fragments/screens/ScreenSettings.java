package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterSettings;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewSettings;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityMain;
import com.dreamdigitizers.drugmanagement.views.implementations.adapters.AdapterLanguages;

public class ScreenSettings extends Screen implements IViewSettings {
    private Spinner mSelLanguages;

    private IPresenterSettings mPresenter;
    private AdapterLanguages mAdapter;

    private String mPreferredLanguage;

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPresenter = (IPresenterSettings)PresenterFactory.createPresenter(IPresenterSettings.class, this);
        this.mAdapter = new AdapterLanguages(this.getContext(), R.array.flag_icons, R.array.languages, R.array.locales);
        this.mPreferredLanguage = this.mPresenter.getPreferredLanguage();
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__settings, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mSelLanguages = (Spinner)pView.findViewById(R.id.selLanguages);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mSelLanguages.setAdapter(this.mAdapter);
        this.bindLanguage();
        this.mSelLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pId) {
                ScreenSettings.this.selectLanguage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> pParent) {

            }
        });
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_settings;
    }

    private void bindLanguage() {
        for(int i = 0; i < this.mSelLanguages.getCount(); i++) {
            String language = (String)this.mSelLanguages.getItemAtPosition(i);
            if(language.equals(this.mPreferredLanguage)) {
                this.mSelLanguages.setSelection(i);
                break;
            }
        }
    }

    private void selectLanguage() {
        String language = (String)this.mSelLanguages.getSelectedItem();
        if(!language.equals(this.mPreferredLanguage)) {
            this.mPresenter.setPreferredLanguage(language);
            this.changeLanguage(language);
            this.refresh();
        }
    }

    private void changeLanguage(String pLanguage) {
        this.mScreenActionsListener.changeLanguage(pLanguage);
    }

    private void refresh() {
        Bundle extras = new Bundle();
        extras.putInt(Constants.BUNDLE_KEY__STATE_SELECTED_POSITION, Constants.NAVIGATION_DRAWER_ITEM_ID__SETTINGS);
        Intent intent = new Intent(this.getContext(), ActivityMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.INTENT_EXTRA_KEY__DATA, extras);
        this.startActivity(intent);
        this.getActivity().finish();
    }
}
