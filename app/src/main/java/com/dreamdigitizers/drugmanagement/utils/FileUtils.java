package com.dreamdigitizers.drugmanagement.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FileUtils {
    private static final String TAG = FileUtils.class.getName();

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean deleteFile(String pFilePath) {
        boolean result = true;
        File file = new File(pFilePath);
        if(file.exists()) {
            result = file.delete();
        }

        return result;
    }

    public static File getOutputMediaFile(String pFolderName, String pFileName) {
        if(!FileUtils.isExternalStorageWritable()) {
            return null;
        }

        File mediaStorageFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), pFolderName);
        if(!mediaStorageFolder.exists()) {
            if(!mediaStorageFolder.mkdirs()) {
                Log.d(TAG, "Failed to create directory");
                return null;
            }
        }

        File mediaFile;
        mediaFile = new File(mediaStorageFolder.getPath() + File.separator + pFileName);

        return mediaFile;
    }

    public static Bitmap decodeSampledBitmapFromFile(String pImagePath, int pWidth, int pHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pImagePath, options);

        options.inSampleSize = FileUtils.calculateInSampleSize(options, pWidth, pHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pImagePath, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options pOptions, int pWidth, int pHeight) {
        int width = pOptions.outWidth;
        int height = pOptions.outHeight;
        int inSampleSize = 1;

        if (width > pWidth || height > pHeight) {
            int halfWidth = width / 2;
            int halfHeight = height / 2;

            while ((halfWidth / inSampleSize) >= pWidth && (halfHeight / inSampleSize) >= pHeight) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
