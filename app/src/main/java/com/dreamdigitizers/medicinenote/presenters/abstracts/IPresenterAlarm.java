package com.dreamdigitizers.medicinenote.presenters.abstracts;

import android.graphics.Bitmap;

public interface IPresenterAlarm extends IPresenter {
    Bitmap loadImage(String pFilePath, int pWidth, int pHeight);
    void select(long pRowId);
    boolean setAlarmDone(long pRowId);
}
