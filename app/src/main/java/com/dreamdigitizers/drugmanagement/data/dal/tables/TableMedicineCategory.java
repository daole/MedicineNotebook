package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dreamdigitizers.drugmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class TableMedicineCategory extends Table {
    public static final String TABLE_NAME = "medicine_category";

    public static final String COLUMN_NAME__MEDICINE_CATEGORY_NAME = "medicine_category_name";
    public static final String COLUMN_NAME__MEDICINE_CATEGORY_NOTE = "medicine_category_note";

    public static final int COLUMN_INDEX__MEDICINE_CATEGORY_NAME = 1;
    public static final int COLUMN_INDEX__MEDICINE_CATEGORY_NOTE = 2;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableMedicineCategory.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME + "` TEXT NOT NULL UNIQUE,"
            + "`" + TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NOTE + "` TEXT NOT NULL"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    private static final String STATEMENT__INSERT = "INSERT INTO `" + TableMedicineCategory.TABLE_NAME + "` VALUES(%d, '%s', '%s')";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(Table.COLUMN_NAME__ID);
        columns.add(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME);
        columns.add(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NOTE);

        return columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableMedicineCategory.STATEMENT__CREATE);
        TableMedicineCategory.insertSampleData(pContext, pSQLiteDatabase);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }

    private static void insertSampleData(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        String[] names = pContext.getResources().getStringArray(R.array.medicine_category_names);
        String[] notes = pContext.getResources().getStringArray(R.array.medicine_category_notes);
        for(int i = 0; i < names.length; i++) {
            pSQLiteDatabase.execSQL(String.format(TableMedicineCategory.STATEMENT__INSERT, i + 1, names[i], notes[i]));
        }
    }
}
