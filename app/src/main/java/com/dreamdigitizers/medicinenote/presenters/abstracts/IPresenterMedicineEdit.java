package com.dreamdigitizers.medicinenote.presenters.abstracts;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.LoaderManager;

public interface IPresenterMedicineEdit extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    Bitmap loadImage(String pFilePath);
    Bitmap loadImage(String pFilePath, int pWidth, int pHeight);
    void deleteImage(String pFilePath);
    void select(long pRowId);
    void edit(long pRowId, String pMedicineName, long pMedicineCategoryId, String pMedicineImagePath, String pMedicineNote);
}
