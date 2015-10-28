package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableMedicineTime extends Table {
    public static final String TABLE_NAME = "medicine_time";

    public static final String COLUMN_NAME__MEDICINE_TIME_NAME = "medicine_time_name";
    public static final String COLUMN_NAME__MEDICINE_TIME_VALUE = "medicine_time_value";

    public static final int COLUMN_INDEX__MEDICINE_TIME_NAME = 1;
    public static final int COLUMN_INDEX__MEDICINE_TIME_VALUE = 2;

    private static String CREATE_STATEMENT = "CREATE TABLE `" + TableMedicineTime.TABLE_NAME + "` ("
            + "`" + TableMedicineTime.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME + "` TEXT NOT NULL,"
            + "`" + TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE + "` TEXT NOT NULL UNIQUE"
            + ");";

    private static String UPDATE_STATEMENT = "";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(TableMedicineTime.COLUMN_NAME__ID);
        columns.add(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME);
        columns.add(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE);

        return columns;
    }

    public static void onCreate(SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicineTime.CREATE_STATEMENT);
    }

    public static void onUpgrade(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
