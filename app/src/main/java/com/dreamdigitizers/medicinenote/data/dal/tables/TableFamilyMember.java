package com.dreamdigitizers.medicinenote.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dreamdigitizers.medicinenote.R;

import java.util.ArrayList;
import java.util.List;

public class TableFamilyMember extends Table {
    public static final String TABLE_NAME = "family_member";

    public static final String COLUMN_NAME__FAMILY_MEMBER_NAME = "family_member_name";

    public static final int COLUMN_INDEX__FAMILY_MEMBER_NAME = 1;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableFamilyMember.TABLE_NAME + "` ("
        + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
        + "`" + TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME + "` TEXT NOT NULL UNIQUE"
        + ");";

    private static final String STATEMENT__UPDATE = "";

    private static final String STATEMENT__INSERT = "INSERT INTO `" + TableFamilyMember.TABLE_NAME + "` VALUES(%d, '%s')";

    public static List<String> getColumns() {
        return TableFamilyMember.getColumns(false, true);
    }

    public static List<String> getColumns(boolean pIncludeTableName, boolean pIncludeIdColumn) {
        List<String> columns = new ArrayList<String>();

        String tableName = "";
        if(pIncludeTableName) {
            tableName = TableFamilyMember.TABLE_NAME + ".";
        }

        if(pIncludeIdColumn) {
            columns.add(tableName + Table.COLUMN_NAME__ID);
        }
        columns.add(tableName + TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME);

        return columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableFamilyMember.STATEMENT__CREATE);
        TableFamilyMember.insertSampleData(pContext, pSQLiteDatabase);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }

    private static void insertSampleData(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        String[] names = pContext.getResources().getStringArray(R.array.family_member_names);
        for(int i = 0; i < names.length; i++) {
            pSQLiteDatabase.execSQL(String.format(TableFamilyMember.STATEMENT__INSERT, i + 1, names[i]));
        }
    }
}
