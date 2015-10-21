package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.database.sqlite.SQLiteDatabase;

public class TableMedicineTimeSetting extends Table {
    public static final String TABLE_NAME = "medicine_time_setting";

    public static final String COLUMN_NAME__FAMILY_MEMBER_ID = "family_member_id";
    public static final String COLUMN_NAME__MEDICINE_TIME_ID = "medicine_time_id";
    public static final String COLUMN_NAME__MEDICINE_INTERVAL_ID = "medicine_interval_id";
    public static final String COLUMN_NAME__START_DATE = "start_date";
    public static final String COLUMN_NAME__MEDICINE_TAKING_TIMES = "medicine_taking_period";
    public static final String COLUMN_NAME__IMAGE_PATH = "image_path";
    public static final String COLUMN_NAME__IS_ALERT = "is_alert";
    public static final String COLUMN_NAME__NOTE = "note";

    public static final int COLUMN_INDEX__FAMILY_MEMBER_ID = 1;
    public static final int COLUMN_INDEX__MEDICINE_TIME_ID = 2;
    public static final int COLUMN_INDEX__MEDICINE_INTERVAL_ID = 3;
    public static final int COLUMN_INDEX__START_DATE = 4;
    public static final int COLUMN_INDEX__MEDICINE_TAKING_TIMES = 5;
    public static final int COLUMN_INDEX__IMAGE_PATH = 6;
    public static final int COLUMN_INDEX__IS_ALERT = 7;
    public static final int COLUMN_INDEX__NOTE = 8;

    private static String CREATE_STATEMENT = "CREATE TABLE `" + TableMedicineTimeSetting.TABLE_NAME + "` ("
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__FAMILY_MEMBER_ID + "` INTEGER NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__MEDICINE_TIME_ID + "` INTEGER NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__MEDICINE_INTERVAL_ID + "` INTEGER NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__START_DATE + "` TEXT NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__MEDICINE_TAKING_TIMES + "` INTEGER NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__IMAGE_PATH + "` TEXT,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__IS_ALERT + "` INTEGER NOT NULL DEFAULT '0',"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__NOTE + "` TEXT"
            + ");";

    private static String UPDATE_STATEMENT = "";

    public static void onCreate(SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicineTimeSetting.CREATE_STATEMENT);
    }

    public static void onUpdate(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
