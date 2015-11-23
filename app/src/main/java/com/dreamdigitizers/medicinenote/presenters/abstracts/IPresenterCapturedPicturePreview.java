package com.dreamdigitizers.medicinenote.presenters.abstracts;

import android.graphics.Bitmap;

public interface IPresenterCapturedPicturePreview extends IPresenter {
    Bitmap loadImage(String pFilePath, int pWidth, int pHeight);
    void deleteImage(String pFilePath);
}
