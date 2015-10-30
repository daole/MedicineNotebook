package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableMedicineCategory extends Table {
    public static final String TABLE_NAME = "medicine_category";

    public static final String COLUMN_NAME__MEDICINE_CATEGORY_NAME = "medicine_category_name";
    public static final String COLUMN_NAME__MEDICINE_CATEGORY_NOTE = "medicine_category_note";

    public static final int COLUMN_INDEX__MEDICINE_CATEGORY_NAME = 1;
    public static final int COLUMN_INDEX__MEDICINE_CATEGORY_NOTE = 2;

    private static String CREATE_STATEMENT = "CREATE TABLE `" + TableMedicineCategory.TABLE_NAME + "` ("
            + "`" + TableMedicineCategory.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME + "` TEXT NOT NULL UNIQUE,"
            + "`" + TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NOTE + "` TEXT NOT NULL"
            + ");";

    private static String UPDATE_STATEMENT = "";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(TableMedicineCategory.COLUMN_NAME__ID);
        columns.add(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME);
        columns.add(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NOTE);

        return columns;
    }

    public static void onCreate(SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicineCategory.CREATE_STATEMENT);
    }

    public static void onUpgrade(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
