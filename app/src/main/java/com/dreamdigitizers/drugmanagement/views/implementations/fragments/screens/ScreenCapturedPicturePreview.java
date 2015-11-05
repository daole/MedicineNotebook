package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;

public class ScreenCapturedPicturePreview extends Screen {
    private ImageView mImgCapturedPicture;
    private Button mBtnOK;
    private Button mBtnBack;

    private byte[] mCapturedImageData;

    @Override
    protected void retrieveArguments(Bundle pArguments) {
        this.mCapturedImageData = pArguments.getByteArray(Constants.BUNDLE_KEY__CAPTURED_PICTURE_DATA);
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__captured_picture_preview, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mImgCapturedPicture = (ImageView)pView.findViewById(R.id.imgCapturedPicture);
        this.mBtnOK = (Button)pView.findViewById(R.id.btnOK);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenCapturedPicturePreview.this.buttonOKClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenCapturedPicturePreview.this.buttonBackClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    public void buttonOKClick() {

    }

    public void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }
}
