package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import android.graphics.Bitmap;

public interface IPresenterMedicineInformation extends IPresenter {
    Bitmap loadImage(String pFilePath);
    Bitmap loadImage(String pFilePath, int pWidth, int pHeight);
    void select(long pRowId);
}
