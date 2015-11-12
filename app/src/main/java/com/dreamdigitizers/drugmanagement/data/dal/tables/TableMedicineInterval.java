package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dreamdigitizers.drugmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class TableMedicineInterval extends Table {
    public static final String TABLE_NAME = "medicine_interval";

    public static final String COLUMN_NAME__MEDICINE_INTERVAL_NAME = "medicine_interval_name";
    public static final String COLUMN_NAME__MEDICINE_INTERVAL_VALUE = "medicine_interval_value";

    public static final int COLUMN_INDEX__MEDICINE_INTERVAL_NAME = 1;
    public static final int COLUMN_INDEX__MEDICINE_INTERVAL_VALUE = 2;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableMedicineInterval.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_NAME + "` TEXT NOT NULL,"
            + "`" + TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_VALUE + "` INTEGER NOT NULL UNIQUE"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    private static final String STATEMENT__INSERT = "INSERT INTO `" + TableMedicineInterval.TABLE_NAME + "` VALUES(%d, '%s', '%s')";

    public static List<String> getColumns() {
        return TableMedicineInterval.getColumns(false, true);
    }

    public static List<String> getColumns(boolean pIncludeTableName, boolean pIncludeIdColumn) {
        List<String> columns = new ArrayList<String>();

        String tableName = "";
        if(pIncludeTableName) {
            tableName = TableMedicineInterval.TABLE_NAME + ".";
        }

        if(pIncludeIdColumn) {
            columns.add(tableName + Table.COLUMN_NAME__ID);
        }
        columns.add(tableName + TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_NAME);
        columns.add(tableName + TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_VALUE);

        return columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicineInterval.STATEMENT__CREATE);
        TableMedicineInterval.insertSampleData(pContext, pSQLiteDatabase);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }

    private static void insertSampleData(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        String[] names = pContext.getResources().getStringArray(R.array.medicine_interval_names);
        String[] values = pContext.getResources().getStringArray(R.array.medicine_interval_values);
        for(int i = 0; i < names.length; i++) {
            pSQLiteDatabase.execSQL(String.format(TableMedicineInterval.STATEMENT__INSERT, i + 1, names[i], values[i]));
        }
    }
}
