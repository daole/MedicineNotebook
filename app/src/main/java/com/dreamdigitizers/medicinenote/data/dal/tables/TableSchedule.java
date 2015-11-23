package com.dreamdigitizers.medicinenote.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableSchedule extends Table {
    public static final String TABLE_NAME = "schedule";

    public static final String COLUMN_NAME__FAMILY_MEMBER_ID = "family_member_id";
    public static final String COLUMN_NAME__MEDICINE_TIME_ID = "medicine_time_id";
    public static final String COLUMN_NAME__MEDICINE_INTERVAL_ID = "medicine_interval_id";
    public static final String COLUMN_NAME__FALLBACK_FAMILY_MEMBER_NAME = "fallback_family_member_name";
    public static final String COLUMN_NAME__FALLBACK_INTERVAL_VALUE = "fallback_interval_value";
    public static final String COLUMN_NAME__START_DATE = "start_date";
    public static final String COLUMN_NAME__TIMES = "times";
    public static final String COLUMN_NAME__IMAGE_PATH = "image_path";
    public static final String COLUMN_NAME__SCHEDULE_NOTE = "schedule_note";

    public static final int COLUMN_INDEX__FAMILY_MEMBER_ID = 1;
    public static final int COLUMN_INDEX__MEDICINE_TIME_ID = 2;
    public static final int COLUMN_INDEX__MEDICINE_INTERVAL_ID = 3;
    public static final int COLUMN_INDEX__FALLBACK_FAMILY_MEMBER_NAME = 4;
    public static final int COLUMN_INDEX__FALLBACK_INTERVAL_VALUE = 5;
    public static final int COLUMN_INDEX__START_DATE = 6;
    public static final int COLUMN_INDEX__TIMES = 7;
    public static final int COLUMN_INDEX__IMAGE_PATH = 8;
    public static final int COLUMN_INDEX__SCHEDULE_NOTE = 9;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableSchedule.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableSchedule.COLUMN_NAME__FAMILY_MEMBER_ID + "` INTEGER NOT NULL,"
            + "`" + TableSchedule.COLUMN_NAME__MEDICINE_TIME_ID + "` INTEGER NOT NULL,"
            + "`" + TableSchedule.COLUMN_NAME__MEDICINE_INTERVAL_ID + "` INTEGER NOT NULL,"
            + "`" + TableSchedule.COLUMN_NAME__FALLBACK_FAMILY_MEMBER_NAME + "` TEXT NOT NULL,"
            + "`" + TableSchedule.COLUMN_NAME__FALLBACK_INTERVAL_VALUE + "` INTEGER NOT NULL,"
            + "`" + TableSchedule.COLUMN_NAME__START_DATE + "` TEXT NOT NULL,"
            + "`" + TableSchedule.COLUMN_NAME__TIMES + "` INTEGER NOT NULL,"
            + "`" + TableSchedule.COLUMN_NAME__IMAGE_PATH + "` TEXT,"
            + "`" + TableSchedule.COLUMN_NAME__SCHEDULE_NOTE + "` TEXT"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    public static List<String> getColumns() {
        return TableSchedule.getColumns(false, true);
    }

    public static List<String> getColumns(boolean pIncludeTableName, boolean pIncludeIdColumn) {
        List<String> columns = new ArrayList<String>();

        String tableName = "";
        if(pIncludeTableName) {
            tableName = TableSchedule.TABLE_NAME + ".";
        }

        if(pIncludeIdColumn){
            columns.add(tableName + Table.COLUMN_NAME__ID);
        }
        columns.add(tableName + TableSchedule.COLUMN_NAME__FAMILY_MEMBER_ID);
        columns.add(tableName + TableSchedule.COLUMN_NAME__MEDICINE_TIME_ID);
        columns.add(tableName + TableSchedule.COLUMN_NAME__MEDICINE_INTERVAL_ID);
        columns.add(tableName + TableSchedule.COLUMN_NAME__FALLBACK_FAMILY_MEMBER_NAME);
        columns.add(tableName + TableSchedule.COLUMN_NAME__FALLBACK_INTERVAL_VALUE);
        columns.add(tableName + TableSchedule.COLUMN_NAME__START_DATE);
        columns.add(tableName + TableSchedule.COLUMN_NAME__TIMES);
        columns.add(tableName + TableSchedule.COLUMN_NAME__IMAGE_PATH);
        columns.add(tableName + TableSchedule.COLUMN_NAME__SCHEDULE_NOTE);

        return columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableSchedule.STATEMENT__CREATE);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
