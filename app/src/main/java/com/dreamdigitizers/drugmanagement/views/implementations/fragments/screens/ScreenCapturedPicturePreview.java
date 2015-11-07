package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCapturedPicturePreview;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewCapturedPicturePreview;

public class ScreenCapturedPicturePreview extends Screen implements IViewCapturedPicturePreview {
    private ImageView mImgCapturedPicture;
    private Button mBtnOK;
    private Button mBtnBack;
    private View mCovBottom;

    private String mCapturedPictureFilePath;

    private IPresenterCapturedPicturePreview mPresenter;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putString(Constants.BUNDLE_KEY__CAPTURED_PICTURE_FILE_PATH, this.mCapturedPictureFilePath);
    }

    @Override
    protected void retrieveArguments(Bundle pArguments) {
        this.mCapturedPictureFilePath = pArguments.getString(Constants.BUNDLE_KEY__CAPTURED_PICTURE_FILE_PATH);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mCapturedPictureFilePath = pSavedInstanceState.getString(Constants.BUNDLE_KEY__CAPTURED_PICTURE_FILE_PATH);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!this.mIsRecoverable) {
            this.mPresenter.deleteImage(this.mCapturedPictureFilePath);
        }
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
        this.mCovBottom = pView.findViewById(R.id.covBottom);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        pView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                ScreenCapturedPicturePreview.this.loadCapturedPicture();
                ScreenCapturedPicturePreview.this.resizeCoverBottom();
                ScreenCapturedPicturePreview.this.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

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

        this.mPresenter = (IPresenterCapturedPicturePreview) PresenterFactory.createPresenter(IPresenterCapturedPicturePreview.class, this);
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    private void buttonOKClick() {
        Intent data = new Intent();
        data.putExtra(Constants.BUNDLE_KEY__CAPTURED_PICTURE_FILE_PATH, this.mCapturedPictureFilePath);
        this.mScreenActionsListener.returnActivityResult(Activity.RESULT_OK, data);
    }

    private void buttonBackClick() {
        this.mPresenter.deleteImage(this.mCapturedPictureFilePath);
        this.mScreenActionsListener.onBack();
    }

    private void loadCapturedPicture() {
        this.mImgCapturedPicture.setImageBitmap(
                this.mPresenter.loadImage(
                        this.mCapturedPictureFilePath,
                        this.mImgCapturedPicture.getWidth(),
                        this.mImgCapturedPicture.getHeight()));
    }

    private void resizeCoverBottom() {
        int coverHeight = (this.getView().getHeight() - this.getView().getWidth()) / 2;

        ViewGroup.LayoutParams params = this.mCovBottom.getLayoutParams();
        params.height = coverHeight;
        this.mCovBottom.setLayoutParams(params);
    }
}
