package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableAlert extends Table {
    public static final String TABLE_NAME = "alert";

    public static final String COLUMN_NAME__MEDICINE_TIME_SETTING_ID = "medicine_time_setting_id";
    public static final String COLUMN_NAME__ALERT_YEAR = "alert_year";
    public static final String COLUMN_NAME__ALERT_MONTH = "alert_month";
    public static final String COLUMN_NAME__ALERT_DAY = "alert_day";
    public static final String COLUMN_NAME__ALERT_HOUR = "alert_hour";
    public static final String COLUMN_NAME__ALERT_MINUTE = "alert_minute";
    public static final String COLUMN_NAME__IS_DONE = "is_done";

    public static final int COLUMN_INDEX__MEDICINE_TIME_SETTING_ID = 1;
    public static final int COLUMN_INDEX__ALERT_YEAR = 2;
    public static final int COLUMN_INDEX__ALERT_MONTH = 3;
    public static final int COLUMN_INDEX__ALERT_DAY = 4;
    public static final int COLUMN_INDEX__ALERT_HOUR = 5;
    public static final int COLUMN_INDEX__ALERT_MINUTE = 6;
    public static final int COLUMN_INDEX__IS_DONE = 7;

    private static String CREATE_STATEMENT = "CREATE TABLE `" + TableAlert.TABLE_NAME + "` ("
        + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
        + "`" + TableAlert.COLUMN_NAME__MEDICINE_TIME_SETTING_ID + "` INTEGER NOT NULL,"
        + "`" + TableAlert.COLUMN_NAME__ALERT_YEAR + "` INTEGER NOT NULL,"
        + "`" + TableAlert.COLUMN_NAME__ALERT_MONTH + "` INTEGER NOT NULL,"
        + "`" + TableAlert.COLUMN_NAME__ALERT_DAY + "` INTEGER NOT NULL,"
        + "`" + TableAlert.COLUMN_NAME__ALERT_HOUR + "` INTEGER NOT NULL,"
        + "`" + TableAlert.COLUMN_NAME__ALERT_MINUTE + "` INTEGER NOT NULL,"
        + "`" + TableAlert.COLUMN_NAME__IS_DONE + "` INTEGER NOT NULL DEFAULT '0'"
        + ");";

    private static String UPDATE_STATEMENT = "";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(Table.COLUMN_NAME__ID);
        columns.add(TableAlert.COLUMN_NAME__MEDICINE_TIME_SETTING_ID);
        columns.add(TableAlert.COLUMN_NAME__ALERT_YEAR);
        columns.add(TableAlert.COLUMN_NAME__ALERT_MONTH);
        columns.add(TableAlert.COLUMN_NAME__ALERT_DAY);
        columns.add(TableAlert.COLUMN_NAME__ALERT_HOUR);
        columns.add(TableAlert.COLUMN_NAME__ALERT_MINUTE);
        columns.add(TableAlert.COLUMN_NAME__IS_DONE);

        return columns;
    }

    public static void onCreate(SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableAlert.CREATE_STATEMENT);
    }

    public static void onUpgrade(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
