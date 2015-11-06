package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCapturedPicturePreview;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewCapturedPicturePreview;

class PresenterCapturedPicturePreview implements IPresenterCapturedPicturePreview {
    private IViewCapturedPicturePreview mView;

    public PresenterCapturedPicturePreview(IViewCapturedPicturePreview pView) {
        this.mView = pView;
    }

    public void deleteFile(String pFilePath) {
        FileUtils.deleteFile(pFilePath);
    }
}
