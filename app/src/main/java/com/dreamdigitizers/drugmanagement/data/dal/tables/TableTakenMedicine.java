package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableTakenMedicine extends Table {
    public static final String TABLE_NAME = "taken_medicine";

    public static final String COLUMN_NAME__MEDICINE_TIME_SETTING_ID = "medicine_time_setting_id";
    public static final String COLUMN_NAME__MEDICINE_ID = "medicine_id";
    public static final String COLUMN_NAME__MEDICINE_NAME = "medicine_name";
    public static final String COLUMN_NAME__DOSE = "dose";

    public static final int COLUMN_INDEX__MEDICINE_TIME_SETTING_ID = 1;
    public static final int COLUMN_INDEX__MEDICINE_ID = 2;
    public static final int COLUMN_INDEX__MEDICINE_NAME = 3;
    public static final int COLUMN_INDEX__DOSE = 4;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableTakenMedicine.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableTakenMedicine.COLUMN_NAME__MEDICINE_TIME_SETTING_ID + "` INTEGER NOT NULL,"
            + "`" + TableTakenMedicine.COLUMN_NAME__MEDICINE_ID + "` INTEGER NOT NULL,"
            + "`" + TableTakenMedicine.COLUMN_NAME__MEDICINE_NAME + "` TEXT NOT NULL,"
            + "`" + TableTakenMedicine.COLUMN_NAME__DOSE + "` TEXT NOT NULL,"
            + "UNIQUE (`" + TableTakenMedicine.COLUMN_NAME__MEDICINE_TIME_SETTING_ID + "`, `" + TableTakenMedicine.COLUMN_NAME__MEDICINE_ID + "`)"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(Table.COLUMN_NAME__ID);
        columns.add(TableTakenMedicine.COLUMN_NAME__MEDICINE_TIME_SETTING_ID);
        columns.add(TableTakenMedicine.COLUMN_NAME__MEDICINE_ID);
        columns.add(TableTakenMedicine.COLUMN_NAME__MEDICINE_NAME);
        columns.add(TableTakenMedicine.COLUMN_NAME__DOSE);

        return columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableTakenMedicine.STATEMENT__CREATE);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
