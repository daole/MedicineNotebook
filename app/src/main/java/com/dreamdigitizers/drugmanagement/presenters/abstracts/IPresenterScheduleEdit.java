package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import android.graphics.Bitmap;

public interface IPresenterScheduleEdit extends IPresenter {
    Bitmap loadImage(String pFilePath, int pWidth, int pHeight);
    void select(long pRowId);
    void changeAlarmStatus(boolean pIsAlarm);
}
