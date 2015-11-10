package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableMedicineTimeSetting extends Table {
    public static final String TABLE_NAME = "schedule";

    public static final String COLUMN_NAME__FAMILY_MEMBER_ID = "family_member_id";
    public static final String COLUMN_NAME__MEDICINE_TIME_ID = "medicine_time_id";
    public static final String COLUMN_NAME__MEDICINE_INTERVAL_ID = "medicine_interval_id";
    public static final String COLUMN_NAME__START_DATE = "start_date";
    public static final String COLUMN_NAME__IS_ALERT = "is_alert";
    public static final String COLUMN_NAME__ALERT_TIMES = "alert_times";
    public static final String COLUMN_NAME__IMAGE_PATH = "image_path";
    public static final String COLUMN_NAME__SCHEDULE_NOTE = "schedule_note";

    public static final int COLUMN_INDEX__FAMILY_MEMBER_ID = 1;
    public static final int COLUMN_INDEX__MEDICINE_TIME_ID = 2;
    public static final int COLUMN_INDEX__MEDICINE_INTERVAL_ID = 3;
    public static final int COLUMN_INDEX__START_DATE = 4;
    public static final int COLUMN_INDEX__IS_ALERT = 5;
    public static final int COLUMN_INDEX__ALERT_TIMES = 6;
    public static final int COLUMN_INDEX__IMAGE_PATH = 7;
    public static final int COLUMN_INDEX__SCHEDULE_NOTE = 8;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableMedicineTimeSetting.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__FAMILY_MEMBER_ID + "` INTEGER NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__MEDICINE_TIME_ID + "` INTEGER NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__MEDICINE_INTERVAL_ID + "` INTEGER NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__START_DATE + "` TEXT NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__IS_ALERT + "` INTEGER NOT NULL DEFAULT '0',"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__ALERT_TIMES + "` INTEGER NOT NULL,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__IMAGE_PATH + "` TEXT,"
            + "`" + TableMedicineTimeSetting.COLUMN_NAME__SCHEDULE_NOTE + "` TEXT"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(Table.COLUMN_NAME__ID);
        columns.add(TableMedicineTimeSetting.COLUMN_NAME__FAMILY_MEMBER_ID);
        columns.add(TableMedicineTimeSetting.COLUMN_NAME__MEDICINE_TIME_ID);
        columns.add(TableMedicineTimeSetting.COLUMN_NAME__MEDICINE_INTERVAL_ID);
        columns.add(TableMedicineTimeSetting.COLUMN_NAME__START_DATE);
        columns.add(TableMedicineTimeSetting.COLUMN_NAME__IS_ALERT);
        columns.add(TableMedicineTimeSetting.COLUMN_NAME__ALERT_TIMES);
        columns.add(TableMedicineTimeSetting.COLUMN_NAME__IMAGE_PATH);
        columns.add(TableMedicineTimeSetting.COLUMN_NAME__SCHEDULE_NOTE);

        return columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicineTimeSetting.STATEMENT__CREATE);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
