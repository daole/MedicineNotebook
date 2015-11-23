package com.dreamdigitizers.medicinenote.views.implementations.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamdigitizers.medicinenote.R;

public class AdapterLanguages extends BaseAdapter {
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
