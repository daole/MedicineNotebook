package com.dreamdigitizers.drugmanagement.presenters.abstracts;

public interface IPresenterCamera extends IPresenter {
    void saveImage(byte[] pData, int pDegrees, int pImageType, boolean pIsCropped);
}
