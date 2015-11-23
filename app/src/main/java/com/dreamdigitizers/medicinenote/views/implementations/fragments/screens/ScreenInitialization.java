package com.dreamdigitizers.medicinenote.views.implementations.fragments.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterInitialization;
import com.dreamdigitizers.medicinenote.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.medicinenote.utils.DialogUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewInitialization;
import com.dreamdigitizers.medicinenote.views.implementations.activities.ActivityMain;
import com.dreamdigitizers.medicinenote.views.implementations.adapters.AdapterLanguages;

import java.util.Locale;

public class ScreenInitialization extends Screen implements IViewInitialization {
    private Spinner mSelLanguages;
    private Button mBtnSelectLanguage;

    private IPresenterInitialization mPresenter;
    private AdapterLanguages mAdapter;

    private AsyncTask<Void, Void, Void> mAsyncTask;

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.mPresenter = (IPresenterInitialization)PresenterFactory.createPresenter(IPresenterInitialization.class, this);
        if(this.mPresenter.isPreferredLanguageAlreadySet()) {
            String language = this.mPresenter.getPreferredLanguage();
            this.changeLanguage(language);
            this.goToMainActivity();
            return;
        }

        this.mAdapter = new AdapterLanguages(this.getContext(), R.array.flag_icons, R.array.languages, R.array.locales);
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__initialization, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mSelLanguages = (Spinner)pView.findViewById(R.id.selLanguages);
        this.mBtnSelectLanguage = (Button)pView.findViewById(R.id.btnSelectLanguage);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mSelLanguages.setAdapter(this.mAdapter);
        this.bindLocale();

        this.mBtnSelectLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenInitialization.this.buttonSelectLanguageClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    private void bindLocale() {
        String defaultLanguage = Locale.getDefault().getLanguage();
        for(int i = 0; i < this.mSelLanguages.getCount(); i++) {
            String language = (String)this.mSelLanguages.getItemAtPosition(i);
            if(language.equals(defaultLanguage)) {
                this.mSelLanguages.setSelection(i);
                break;
            }
        }
    }

    private void buttonSelectLanguageClick() {
        String language = (String)this.mSelLanguages.getSelectedItem();
        this.mPresenter.setPreferredLanguage(language);
        this.changeLanguage(language);
        this.mAsyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                DialogUtils.displayProgressDialog(ScreenInitialization.this.getActivity(),
                        ProgressDialog.STYLE_SPINNER,
                        ScreenInitialization.this.getString(R.string.title__dialog),
                        ScreenInitialization.this.getString(R.string.message__initialization),
                        false,
                        null,
                        false,
                        true,
                        null);
            }

            @Override
            protected Void doInBackground(Void[] pParams) {
                ScreenInitialization.this.mPresenter.initialize();
                return null;
            }

            @Override
            protected void onPostExecute(Void pResult) {
                DialogUtils.closeProgressDialog();
                ScreenInitialization.this.goToMainActivity();
            }
        };
        this.mAsyncTask.execute();
    }

    private void changeLanguage(String pLanguage) {
        this.mScreenActionsListener.changeLanguage(pLanguage);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this.getContext(), ActivityMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        this.getActivity().finish();
    }
}
