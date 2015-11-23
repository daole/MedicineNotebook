package com.dreamdigitizers.medicinenote.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TablePrescription extends Table {
    public static final String TABLE_NAME = "prescription";

    public static final String COLUMN_NAME__FAMILY_MEMBER_ID = "family_member_id";
    public static final String COLUMN_NAME__PRESCRIPTION_NAME = "prescription_name";
    public static final String COLUMN_NAME__PRESCRIPTION_DATE = "prescription_date";
    public static final String COLUMN_NAME__IMAGE_PATH = "image_path";
    public static final String COLUMN_NAME__PRESCRIPTION_NOTE = "prescription_note";

    public static final int COLUMN_INDEX__FAMILY_MEMBER_ID = 1;
    public static final int COLUMN_INDEX__PRESCRIPTION_NAME = 2;
    public static final int COLUMN_INDEX__PRESCRIPTION_DATE = 3;
    public static final int COLUMN_INDEX__IMAGE_PATH = 4;
    public static final int COLUMN_INDEX__PRESCRIPTION_NOTE = 5;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TablePrescription.TABLE_NAME + "` ("
            + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + "`" + TablePrescription.COLUMN_NAME__FAMILY_MEMBER_ID + "` INTEGER,"
            + "`" + TablePrescription.COLUMN_NAME__PRESCRIPTION_NAME + "` TEXT NOT NULL,"
            + "`" + TablePrescription.COLUMN_NAME__PRESCRIPTION_DATE + "` TEXT,"
            + "`" + TablePrescription.COLUMN_NAME__IMAGE_PATH + "` TEXT,"
            + "`" + TablePrescription.COLUMN_NAME__PRESCRIPTION_NOTE + "` TEXT"
            + ");";

    private static final String STATEMENT__UPDATE = "";

    public static List<String> getColumns() {
        return TablePrescription.getColumns(false, true);
    }

    public static List<String> getColumns(boolean pIncludeTableName, boolean pIncludeIdColumn) {
        List<String> columns = new ArrayList<String>();

        String tableName = "";
        if(pIncludeTableName) {
            tableName = TablePrescription.TABLE_NAME + ".";
        }

        if(pIncludeIdColumn) {
            columns.add(tableName + Table.COLUMN_NAME__ID);
        }
        columns.add(tableName + TablePrescription.COLUMN_NAME__FAMILY_MEMBER_ID);
        columns.add(tableName + TablePrescription.COLUMN_NAME__PRESCRIPTION_NAME);
        columns.add(tableName + TablePrescription.COLUMN_NAME__PRESCRIPTION_DATE);
        columns.add(tableName + TablePrescription.COLUMN_NAME__IMAGE_PATH);
        columns.add(tableName + TablePrescription.COLUMN_NAME__PRESCRIPTION_NOTE);

        return columns;
    }

    public static List<String> getColumnsForJoin() {
        List<String> columns = TablePrescription.getColumns(true, true);

        List<String> familyMemberColumns = TableFamilyMember.getColumns(true, false);
        columns.addAll(familyMemberColumns);

        return  columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TablePrescription.STATEMENT__CREATE);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
