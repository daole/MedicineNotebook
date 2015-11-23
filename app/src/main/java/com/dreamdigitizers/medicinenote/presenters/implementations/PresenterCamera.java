package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.dreamdigitizers.medicinenote.Constants;
import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterCamera;
import com.dreamdigitizers.medicinenote.utils.FileUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewCamera;

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

    public void saveImage(byte[] pData, int pDegrees, int pImageType, boolean pIsCropped) {
        String prefix = "";
        switch (pImageType) {
            case Constants.IMAGE_TYPE__MEDICINE:
                prefix = Constants.PREFIX__MEDICINE;
                break;
            case Constants.IMAGE_TYPE__PRESCRIPTION:
                prefix = Constants.PREFIX__PRESCRIPTION;
                break;
            default:
                 break;
        }
        String fileName = prefix
                + new SimpleDateFormat(Constants.FORMAT__DATE_TIME_STRING).format(new Date())
                + Constants.EXTENSION__JPG;
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
            rotatedBitmap = this.rotateAndCropBitmap(bitmap, pDegrees, pIsCropped);

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

    private Bitmap rotateAndCropBitmap(Bitmap pBitmap, int pDegrees, boolean pIsCropped) {
        Matrix matrix = new Matrix();
        matrix.postRotate(pDegrees);
        int width = pBitmap.getWidth();
        int height = pBitmap.getHeight();
        if(pIsCropped) {
            if (width >= height) {
                return Bitmap.createBitmap(pBitmap, (width - height) / 2, 0, height, height, matrix, true);
            } else {
                return Bitmap.createBitmap(pBitmap, 0, (height - width) / 2, width, width, matrix, true);
            }
        } else {
            return Bitmap.createBitmap(pBitmap, 0, 0, width, height, matrix, true);
        }
    }
}
