package com.dreamdigitizers.drugmanagement;

public class Constants {
    public static final String EXTENSION__JPG = ".jpg";

    public static final String FOLDER__IMAGE = "Drug Management";

    public static final String DELIMITER__TIME = ":";
    public static final String DELIMITER__DATA = ", ";

    public static final String FORMAT__TIME = "HH:mm";
    public static final String FORMAT__DATE_TIME_STRING = "yyyyMMdd_HHmmss";
    public static final String FORMAT__TIME_VALUE = "%02d";

    public static final String PREFIX__MEDICINE = "MED_";
    public static final String PREFIX__PRESCRIPTION = "PRE_";

    public static final String INTENT_EXTRA_KEY__DATA = "extra";

    public static final String BUNDLE_KEY__ROW_ID = "row_id";
    public static final String BUNDLE_KEY__IMAGE_TYPE = "image_type";
    public static final String BUNDLE_KEY__IS_CROPPED = "is_cropped";
    public static final String BUNDLE_KEY__CAPTURED_PICTURE_FILE_PATH = "captured_picture";

    public static final int IMAGE_TYPE__MEDICINE = 0;
    public static final int IMAGE_TYPE__PRESCRIPTION = 1;

    public static final int ROW_ID__NO_SELECT = 0;

    public static final int REQUEST_CODE__CAMERA = 0;

    public static final int ORIENTATION_THRESHOLD = 45;
    public static final int ORIENTATION__UNDEFINED = -1;
    public static final int ORIENTATION__PORTRAIT = 0;
    public static final int ORIENTATION__REVERSED_LANDSCAPE = 90;
    public static final int ORIENTATION__REVERSED_PORTRAIT = 180;
    public static final int ORIENTATION__LANDSCAPE = 270;

    public static final int NAVIGATION_DRAWER_ITEM_ID__SCHEDULE = 0;
    public static final int NAVIGATION_DRAWER_ITEM_ID__FAMILY = 1;
    public static final int NAVIGATION_DRAWER_ITEM_ID__MEDICINES = 2;
    public static final int NAVIGATION_DRAWER_ITEM_ID__MEDICINE_CATEGORIES = 3;
    public static final int NAVIGATION_DRAWER_ITEM_ID__MEDICINE_TIME = 4;
    public static final int NAVIGATION_DRAWER_ITEM_ID__MEDICINE_INTERVALS = 5;
    public static final int NAVIGATION_DRAWER_ITEM_ID__MEDICINE_PRESCRIPTIONS = 6;
}
