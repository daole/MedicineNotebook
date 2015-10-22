package com.dreamdigitizers.drugmanagement.data.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;

public abstract class Dao {
    protected static final String ERROR_MESSAGE__UNKNOWN_COLUMNS = "There are unknown columns in projection";

    protected DatabaseHelper mDatabaseHelper;
    protected SQLiteQueryBuilder mSQLiteQueryBuilder;

    public Dao(DatabaseHelper pDatabaseHelper) {
        this.mDatabaseHelper = pDatabaseHelper;
        this.mSQLiteQueryBuilder =  new SQLiteQueryBuilder();
    }

    public long insert(ContentValues pValues) {
        return this.insert(pValues, true);
    }

    public long insert(ContentValues pValues, boolean pIsCloseOnEnd) {
        long newID = this.mDatabaseHelper.insert(this.getTableName(), pValues, pIsCloseOnEnd);
        return newID;
    }

    public int update(ContentValues pValues, String pWhereClause, String[] pWhereArgs) {
        return this.update(pValues, pWhereClause, pWhereArgs, true);
    }

    public int update(ContentValues pValues, String pWhereClause, String[] pWhereArgs, boolean pIsCloseOnEnd) {
        int affectedRows = this.mDatabaseHelper.update(this.getTableName(), pValues, pWhereClause, pWhereArgs, pIsCloseOnEnd);
        return affectedRows;
    }

    public int delete(String pWhereClause, String[] pWhereArgs) {
        return this.delete(pWhereClause, pWhereArgs, true);
    }

    public int delete(String pWhereClause, String[] pWhereArgs, boolean pIsCloseOnEnd) {
        int affectedRows = this.mDatabaseHelper.delete(this.getTableName(), pWhereClause, pWhereArgs, pIsCloseOnEnd);
        return affectedRows;
    }

    public Cursor select(String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder) {
        return this.select(pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, true);
    }

    public Cursor select(String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, boolean pIsCloseOnEnd) {
        boolean checkResult = this.checkColumns(pProjection);
        if(!checkResult) {
            throw new IllegalArgumentException(Dao.ERROR_MESSAGE__UNKNOWN_COLUMNS);
        }

        this.mSQLiteQueryBuilder.setTables(this.getTableName());
        Cursor cursor = this.mDatabaseHelper.select(this.mSQLiteQueryBuilder, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, null, pIsCloseOnEnd);
        return  cursor;
    }

    protected abstract String getTableName();
    protected abstract boolean checkColumns(String[] pProjection);
}
