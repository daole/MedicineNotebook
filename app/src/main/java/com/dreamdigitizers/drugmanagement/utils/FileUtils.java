package com.dreamdigitizers.drugmanagement.utils;

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
}
