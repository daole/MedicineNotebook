package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCapturedPicturePreview;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewCapturedPicturePreview;

class PresenterCapturedPicturePreview implements IPresenterCapturedPicturePreview {
    private IViewCapturedPicturePreview mView;

    public PresenterCapturedPicturePreview(IViewCapturedPicturePreview pView) {
        this.mView = pView;
    }

    @Override
    public Bitmap loadImage(String pFilePath, int pWidth, int pHeight) {
        return FileUtils.decodeSampledBitmapFromFile(pFilePath, pWidth, pHeight);
    }

    @Override
    public void deleteImage(String pFilePath) {
        if(!TextUtils.isEmpty(pFilePath)) {
            FileUtils.deleteFile(pFilePath);
        }
    }
}
