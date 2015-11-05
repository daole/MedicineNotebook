package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCamera;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("deprecation")
class PresenterCamera implements IPresenterCamera {
    private IViewCamera mView;

    public PresenterCamera(IViewCamera pView) {
        this.mView = pView;
    }

    public void saveImage(byte[] pData) {
        String fileName = new SimpleDateFormat(Constants.FORMAT__DATE_TIME_STRING).format(new Date()) + Constants.EXTENSION__JPG;
        File file = FileUtils.getOutputMediaFile(Constants.FOLDER__IMAGE, fileName);
        if(file == null) {
            this.mView.showError(R.string.error__image_save_failed);
            return;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(pData);
            this.mView.onImageSaved(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.mView.showError(R.string.error__image_save_failed);
        } catch (IOException e) {
            e.printStackTrace();
            this.mView.showError(R.string.error__image_save_failed);
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
