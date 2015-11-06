package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

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

    public void saveImage(byte[] pData, int pDegrees) {
        String fileName = new SimpleDateFormat(Constants.FORMAT__DATE_TIME_STRING).format(new Date()) + Constants.EXTENSION__JPG;
        File file = FileUtils.getOutputMediaFile(Constants.FOLDER__IMAGE, fileName);
        if(file == null) {
            this.mView.showError(R.string.error__image_save_failed);
            return;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDither = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[pData.length];
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeByteArray(pData, 0, pData.length, options);

            Bitmap rotatedBitmap = bitmap;
            if(pDegrees > 0) {
                rotatedBitmap = this.rotate(bitmap, pDegrees);
            }

            boolean result = rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            if(rotatedBitmap != bitmap) {
                rotatedBitmap.recycle();
            }
            bitmap.recycle();

            if(!result) {
                this.mView.showError(R.string.error__image_save_failed);
                return;
            }

            this.mView.onImageSaved(file);
        } catch (FileNotFoundException e) {
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

    private Bitmap rotate(Bitmap pBitmap, int pDegrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(pDegrees);
        return Bitmap.createBitmap(pBitmap, 0, 0, pBitmap.getWidth(), pBitmap.getHeight(), matrix, true);
    }
}
