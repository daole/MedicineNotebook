package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterCapturedPicturePreview;
import com.dreamdigitizers.medicinenote.utils.FileUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewCapturedPicturePreview;

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
