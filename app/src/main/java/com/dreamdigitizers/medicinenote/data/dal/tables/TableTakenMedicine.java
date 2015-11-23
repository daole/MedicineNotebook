package com.dreamdigitizers.medicinenote.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableTakenMedicine extends Table {
    public static final String TABLE_NAME = "taken_medicine";

    public static final String COLUMN_ALIAS__ID = "taken_medicine_id";

    public static final String COLUMN_NAME__ID = TableTakenMedicine.TABLE_NAME + "." + Table.COLUMN_NAME__ID + " as " + TableTakenMedicine.COLUMN_ALIAS__ID;
    public static final String COLUMN_NAME__SCHEDULE_ID = "schedule_id";
    public static final String COLUMN_NAME__MEDICINE_ID = "medicine_id";
    public static final String COLUMN_NAME__FALLBACK_MEDICINE_NAME = "fallback_medicine_name";
    public static final String COLUMN_NAME__DOSE = "dose";

    public static final int COLUMN_INDEX__SCHEDULE_ID = 1;
    public static final int COLUMN_INDEX__MEDICINE_ID = 2;
    public static final int COLUMN_INDEX__FALLBACK_MEDICINE_NAME = 3;
    public static final int COLUMN_INDEX__DOSE = 4;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableTakenMedicine.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableTakenMedicine.COLUMN_NAME__SCHEDULE_ID + "` INTEGER NOT NULL,"
            + "`" + TableTakenMedicine.COLUMN_NAME__MEDICINE_ID + "` INTEGER NOT NULL,"
            + "`" + TableTakenMedicine.COLUMN_NAME__FALLBACK_MEDICINE_NAME + "` TEXT NOT NULL,"
            + "`" + TableTakenMedicine.COLUMN_NAME__DOSE + "` TEXT NOT NULL,"
            + "UNIQUE (`" + TableTakenMedicine.COLUMN_NAME__SCHEDULE_ID + "`, `" + TableTakenMedicine.COLUMN_NAME__MEDICINE_ID + "`)"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    public static List<String> getColumns() {
        return TableTakenMedicine.getColumns(false, true);
    }

    public static List<String> getColumns(boolean pIncludeTableName, boolean pIncludeIdColumn) {
        List<String> columns = new ArrayList<String>();

        String tableName = "";
        if(pIncludeTableName) {
            tableName = TableTakenMedicine.TABLE_NAME + ".";
        }

        if(pIncludeIdColumn) {
            columns.add(tableName + Table.COLUMN_NAME__ID);
        }
        columns.add(tableName + TableTakenMedicine.COLUMN_NAME__SCHEDULE_ID);
        columns.add(tableName + TableTakenMedicine.COLUMN_NAME__MEDICINE_ID);
        columns.add(tableName + TableTakenMedicine.COLUMN_NAME__FALLBACK_MEDICINE_NAME);
        columns.add(tableName + TableTakenMedicine.COLUMN_NAME__DOSE);

        return columns;
    }

    public static List<String> getColumnsForJoin() {
        List<String> columns = TableTakenMedicine.getColumns(true, true);

        List<String> medicineColumns = TableMedicine.getColumns(true, false);
        columns.addAll(medicineColumns);

        List<String> medicineCategoryColumns = TableMedicineCategory.getColumns(true, false);
        columns.addAll(medicineCategoryColumns);

        return  columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableTakenMedicine.STATEMENT__CREATE);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
