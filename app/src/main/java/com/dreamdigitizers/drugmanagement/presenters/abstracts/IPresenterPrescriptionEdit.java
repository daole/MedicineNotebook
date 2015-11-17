package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.app.LoaderManager;

public interface IPresenterPrescriptionEdit extends IPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    Bitmap loadImage(String pFilePath, int pWidth, int pHeight);
    void deleteImage(String pFilePath);
    void select(long pRowId);
    void edit(long pRowId, String pPrescriptionName, String pPrescriptionDate, long pFamilyMemberId, String pImagePath, String pPrescriptionNote);
}
