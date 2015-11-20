package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterInitialization;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewInitialization;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityMain;

import java.util.Locale;

public class ScreenInitialization extends Screen implements IViewInitialization {
    private Spinner mSelLanguages;
    private Button mBtnSelectLanguage;

    private IPresenterInitialization mPresenter;
    private AdapterLanguages mAdapter;

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        this.mPresenter = (IPresenterInitialization)PresenterFactory.createPresenter(IPresenterInitialization.class, this);
        if(this.mPresenter.isPreferredLanguageAlreadySet()) {
            Intent intent = new Intent(this.getContext(), ActivityMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
            this.getActivity().finish();
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
            if(language == defaultLanguage) {
                this.mSelLanguages.setSelection(i);
                break;
            }
        }
    }

    private void buttonSelectLanguageClick() {
        String selectedLanguage = (String)this.mSelLanguages.getSelectedItem();
        this.mPresenter.setPreferredLanguage(selectedLanguage);

    }

    private static class AdapterLanguages extends BaseAdapter {
        private Context mContext;
        private TypedArray mFlagIcons;
        private String[] mLanguages;
        private String[] mLocales;

        public AdapterLanguages(Context pContext, int pFlagIconsKey, int pLanguagesKeys, int pLocalesKey) {
            this.mContext = pContext;
            this.mFlagIcons = this.mContext.getResources().obtainTypedArray(pFlagIconsKey);
            this.mLanguages = this.mContext.getResources().getStringArray(pLanguagesKeys);
            this.mLocales = this.mContext.getResources().getStringArray(pLocalesKey);
        }

        @Override
        public int getCount() {
            return mLanguages.length;
        }

        @Override
        public Object getItem(int pPosition) {
            return mLocales[pPosition];
        }

        @Override
        public long getItemId(int pPosition) {
            return pPosition;
        }

        @Override
        public View getView(int pPosition, View pConvertView, ViewGroup pParent) {
            return this.getCustomView(pPosition, pConvertView, pParent);
        }

        @Override
        public View getDropDownView(int pPosition, View pConvertView, ViewGroup pParent) {
            return this.getCustomView(pPosition, pConvertView, pParent);
        }

        private View getCustomView(int pPosition, View pConvertView, ViewGroup pParent) {
            ViewHolder viewHolder;
            if(pConvertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                pConvertView = layoutInflater.inflate(R.layout.part__language_item, pParent, false);

                viewHolder = new ViewHolder();
                viewHolder.mImgFlagIcon = (ImageView)pConvertView.findViewById(R.id.imgFlagIcon);
                viewHolder.mLblLanguage = (TextView)pConvertView.findViewById(R.id.lblLanguage);
                pConvertView.setTag(viewHolder);
            }

            viewHolder = (ViewHolder)pConvertView.getTag();
            viewHolder.mImgFlagIcon.setImageResource(this.mFlagIcons.getResourceId(pPosition, -1));
            viewHolder.mLblLanguage.setText(this.mLanguages[pPosition]);

            return pConvertView;
        }

        private static class ViewHolder {
            public ImageView mImgFlagIcon;
            public TextView mLblLanguage;
        }
    }
}
