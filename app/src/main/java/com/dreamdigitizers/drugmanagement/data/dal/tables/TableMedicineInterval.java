package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableMedicineInterval extends Table {
    public static final String TABLE_NAME = "medicine_interval";

    public static final String COLUMN_NAME__INTERVAL_NAME = "medicine_interval_name";
    public static final String COLUMN_NAME__INTERVAL_VALUE = "medicine_interval_value";

    public static final int COLUMN_INDEX__INTERVAL_NAME = 1;
    public static final int COLUMN_INDEX__INTERVAL_VALUE = 2;

    private static String CREATE_STATEMENT = "CREATE TABLE `" + TableMedicineInterval.TABLE_NAME + "` ("
            + "`" + TableMedicineInterval.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicineInterval.COLUMN_NAME__INTERVAL_NAME + "` TEXT NOT NULL,"
            + "`" + TableMedicineInterval.COLUMN_NAME__INTERVAL_VALUE + "` INTEGER NOT NULL"
            + ");";

    private static String UPDATE_STATEMENT = "";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(TableMedicineInterval.COLUMN_NAME__ID);
        columns.add(TableMedicineInterval.COLUMN_NAME__INTERVAL_NAME);
        columns.add(TableMedicineInterval.COLUMN_NAME__INTERVAL_VALUE);

        return columns;
    }

    public static void onCreate(SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicineInterval.CREATE_STATEMENT);
    }

    public static void onUpdate(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
