package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableFamilyMember extends Table {
    public static final String TABLE_NAME = "family_member";

    public static final String COLUMN_NAME__FAMILY_MEMBER_NAME = "family_member_name";

    public static final int COLUMN_INDEX__FAMILY_MEMBER_NAME = 1;

    private static String CREATE_STATEMENT = "CREATE TABLE `" + TableFamilyMember.TABLE_NAME + "` ("
        + "`" + TableFamilyMember.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
        + "`" + TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME + "` TEXT NOT NULL"
        + ");";

    private static String UPDATE_STATEMENT = "";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(TableFamilyMember.COLUMN_NAME__ID);
        columns.add(TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME);

        return columns;
    }

    public static void onCreate(SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableFamilyMember.CREATE_STATEMENT);
    }

    public static void onUpdate(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
