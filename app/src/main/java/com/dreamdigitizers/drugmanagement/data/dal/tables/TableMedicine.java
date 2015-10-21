package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.database.sqlite.SQLiteDatabase;

public class TableMedicine extends Table {
    public static final String TABLE_NAME = "medicine";

    public static final String COLUMN_NAME__MEDICINE_CATEGORY_ID = "medicine_category_id";
    public static final String COLUMN_NAME__MEDICINE_NAME = "medicine_name";
    public static final String COLUMN_NAME__MEDICINE_IMAGE_PATH = "medicine_image_path";
    public static final String COLUMN_NAME__MEDICINE_NOTE = "medicine_note";

    public static final int COLUMN_INDEX__MEDICINE_CATEGORY_ID = 1;
    public static final int COLUMN_INDEX__MEDICINE_NAME = 2;
    public static final int COLUMN_INDEX__MEDICINE_IMAGE_PATH = 3;
    public static final int COLUMN_INDEX__MEDICINE_NOTE = 4;

    private static String CREATE_STATEMENT = "CREATE TABLE `" + TableMedicine.TABLE_NAME + "` ("
            + "`" + TableMedicine.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicine.COLUMN_NAME__MEDICINE_CATEGORY_ID + "` INTEGER,"
            + "`" + TableMedicine.COLUMN_NAME__MEDICINE_NAME + "` TEXT NOT NULL,"
            + "`" + TableMedicine.COLUMN_NAME__MEDICINE_IMAGE_PATH + "` TEXT,"
            + "`" + TableMedicine.COLUMN_NAME__MEDICINE_NOTE + "` TEXT"
            + ");";

    private static String UPDATE_STATEMENT = "";

    public static void onCreate(SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicine.CREATE_STATEMENT);
    }

    public static void onUpdate(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
