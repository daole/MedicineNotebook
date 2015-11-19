package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.LoaderManager;

public interface IPresenterMedicineAdd extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    Bitmap loadImage(String pFilePath);
    Bitmap loadImage(String pFilePath, int pWidth, int pHeight);
    void deleteImage(String pFilePath);
    void insert(String pMedicineName, long pMedicineCategoryId, String pMedicineImagePath, String pMedicineNote);
}
