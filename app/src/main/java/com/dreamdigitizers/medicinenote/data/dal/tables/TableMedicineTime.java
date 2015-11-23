package com.dreamdigitizers.medicinenote.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dreamdigitizers.medicinenote.R;

import java.util.ArrayList;
import java.util.List;

public class TableMedicineTime extends Table {
    public static final String TABLE_NAME = "medicine_time";

    public static final String COLUMN_NAME__MEDICINE_TIME_NAME = "medicine_time_name";
    public static final String COLUMN_NAME__MEDICINE_TIME_VALUE = "medicine_time_value";

    public static final int COLUMN_INDEX__MEDICINE_TIME_NAME = 1;
    public static final int COLUMN_INDEX__MEDICINE_TIME_VALUE = 2;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableMedicineTime.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME + "` TEXT NOT NULL,"
            + "`" + TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE + "` TEXT NOT NULL UNIQUE"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    private static final String STATEMENT__INSERT = "INSERT INTO `" + TableMedicineTime.TABLE_NAME + "` VALUES(%d, '%s', '%s')";

    public static List<String> getColumns() {
        return TableMedicineTime.getColumns(false, true);
    }

    public static List<String> getColumns(boolean pIncludeTableName, boolean pIncludeIdColumn) {
        List<String> columns = new ArrayList<String>();

        String tableName = "";
        if(pIncludeTableName) {
            tableName = TableMedicineTime.TABLE_NAME + ".";
        }

        if(pIncludeIdColumn) {
            columns.add(tableName + Table.COLUMN_NAME__ID);
        }
        columns.add(tableName + TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME);
        columns.add(tableName + TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE);

        return columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicineTime.STATEMENT__CREATE);
        TableMedicineTime.insertSampleData(pContext, pSQLiteDatabase);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }

    private static void insertSampleData(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        String[] names = pContext.getResources().getStringArray(R.array.medicine_time_names);
        String[] values = pContext.getResources().getStringArray(R.array.medicine_time_values);
        for(int i = 0; i < names.length; i++) {
            pSQLiteDatabase.execSQL(String.format(TableMedicineTime.STATEMENT__INSERT, i + 1, names[i], values[i]));
        }
    }
}
