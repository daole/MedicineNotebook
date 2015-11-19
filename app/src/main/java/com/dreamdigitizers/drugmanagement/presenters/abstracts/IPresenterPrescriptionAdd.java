package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.LoaderManager;

public interface IPresenterPrescriptionAdd extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    Bitmap loadImage(String pFilePath);
    Bitmap loadImage(String pFilePath, int pWidth, int pHeight);
    void deleteImage(String pFilePath);
    void insert(String pPrescriptionName, String pPrescriptionDate, long pFamilyMemberId, String pImagePath, String pPrescriptionNote);
}
