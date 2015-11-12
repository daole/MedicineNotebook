package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableMedicine.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicine.COLUMN_NAME__MEDICINE_CATEGORY_ID + "` INTEGER,"
            + "`" + TableMedicine.COLUMN_NAME__MEDICINE_NAME + "` TEXT NOT NULL UNIQUE,"
            + "`" + TableMedicine.COLUMN_NAME__MEDICINE_IMAGE_PATH + "` TEXT,"
            + "`" + TableMedicine.COLUMN_NAME__MEDICINE_NOTE + "` TEXT"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    public static List<String> getColumns() {
        return TableMedicine.getColumns(false, true);
    }

    public static List<String> getColumns(boolean pIncludeTableName, boolean pIncludeIdColumn) {
        List<String> columns = new ArrayList<String>();

        String tableName = "";
        if(pIncludeTableName) {
            tableName = TableMedicine.TABLE_NAME + ".";
        }

        if(pIncludeIdColumn) {
            columns.add(tableName + Table.COLUMN_NAME__ID);
        }
        columns.add(tableName + TableMedicine.COLUMN_NAME__MEDICINE_CATEGORY_ID);
        columns.add(tableName + TableMedicine.COLUMN_NAME__MEDICINE_NAME);
        columns.add(tableName + TableMedicine.COLUMN_NAME__MEDICINE_IMAGE_PATH);
        columns.add(tableName + TableMedicine.COLUMN_NAME__MEDICINE_NOTE);

        return columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicine.STATEMENT__CREATE);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
