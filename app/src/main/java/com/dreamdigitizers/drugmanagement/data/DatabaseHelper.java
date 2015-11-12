package com.dreamdigitizers.drugmanagement.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

public class DatabaseHelper {
    public static final int DB_ERROR_CODE__CONSTRAINT = -1;
    public static final int DB_ERROR_CODE__OTHER = -2;

    private MySQLiteOpenHelper mMySQLiteHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private boolean mIsInTransaction;

    public DatabaseHelper(Context pContext) {
        this.mMySQLiteHelper = new MySQLiteOpenHelper(pContext);
    }

    public void open(int pOpenMode) throws SQLException {
        if(this.mSQLiteDatabase == null) {
            if(pOpenMode == SQLiteDatabase.OPEN_READONLY) {
                this.mSQLiteDatabase = this.mMySQLiteHelper.getReadableDatabase();
            } else if(pOpenMode == SQLiteDatabase.OPEN_READWRITE) {
                this.mSQLiteDatabase = this.mMySQLiteHelper.getWritableDatabase();
            }
        }
    }

    public synchronized void close() {
        if(this.mSQLiteDatabase != null) {
            this.endTransaction();
            this.mSQLiteDatabase.close();
            this.mSQLiteDatabase = null;
        }
    }

    public long insert(String pTableName, ContentValues pValues) {
        return this.insert(pTableName, pValues, false, false, false);
    }

    public long insert(String pTableName, ContentValues pValues, boolean pIsBeginTransaction) {
        return this.insert(pTableName, pValues, pIsBeginTransaction, false, false);
    }

    public long insert(String pTableName, ContentValues pValues, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        return this.insert(pTableName, pValues, false, pIsCommitTransaction, pIsCloseOnEnd);
    }

    public long insertThenCommit(String pTableName, ContentValues pValues) {
        return this.insert(pTableName, pValues, false, true, true);
    }

    public long insertThenClose(String pTableName, ContentValues pValues) {
        return this.insert(pTableName, pValues, false, false, true);
    }

    public long insert(String pTableName, ContentValues pValues, boolean pIsBeginTransaction, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        long newID = 0;
        this.open(SQLiteDatabase.OPEN_READWRITE);
        if(pIsBeginTransaction) {
            this.beginTransaction();
        }

        try {
            newID = this.mSQLiteDatabase.insertOrThrow(pTableName, null, pValues);

            if(pIsCommitTransaction) {
                this.commitTransaction();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if(e instanceof SQLiteConstraintException) {
                newID = DatabaseHelper.DB_ERROR_CODE__CONSTRAINT;
            } else {
                newID = DatabaseHelper.DB_ERROR_CODE__OTHER;
            }

            if(pIsCommitTransaction) {
                this.rollbackTransaction();
            }
        } finally {
            if(pIsCloseOnEnd) {
                this.close();
            }
            return newID;
        }
    }

    public int update(String pTableName, ContentValues pValues, String pWhereClause, String[] pWhereArgs) {
        return this.update(pTableName, pValues, pWhereClause, pWhereArgs, false, false, false);
    }

    public int update(String pTableName, ContentValues pValues, String pWhereClause, String[] pWhereArgs, boolean pIsBeginTransaction) {
        return this.update(pTableName, pValues, pWhereClause, pWhereArgs, pIsBeginTransaction, false, false);
    }

    public int update(String pTableName, ContentValues pValues, String pWhereClause, String[] pWhereArgs, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        return this.update(pTableName, pValues, pWhereClause, pWhereArgs, false, pIsCommitTransaction, pIsCloseOnEnd);
    }

    public int updateThenCommit(String pTableName, ContentValues pValues, String pWhereClause, String[] pWhereArgs) {
        return this.update(pTableName, pValues, pWhereClause, pWhereArgs, false, true, true);
    }

    public int updateThenClose(String pTableName, ContentValues pValues, String pWhereClause, String[] pWhereArgs) {
        return this.update(pTableName, pValues, pWhereClause, pWhereArgs, false, false, true);
    }

    public int update(String pTableName, ContentValues pValues, String pWhereClause, String[] pWhereArgs, boolean pIsBeginTransaction, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        int affectedRows = 0;
        this.open(SQLiteDatabase.OPEN_READWRITE);
        if(pIsBeginTransaction) {
            this.beginTransaction();
        }

        try {
            affectedRows = this.mSQLiteDatabase.update(pTableName, pValues, pWhereClause, pWhereArgs);

            if(pIsCommitTransaction) {
                this.commitTransaction();
            }
        } catch (SQLException e) {
            if(e instanceof SQLiteConstraintException) {
                affectedRows = DatabaseHelper.DB_ERROR_CODE__CONSTRAINT;
            } else {
                affectedRows = DatabaseHelper.DB_ERROR_CODE__OTHER;
            }

            if(pIsCommitTransaction) {
                this.rollbackTransaction();
            }
        } finally {
            if(pIsCloseOnEnd) {
                this.close();
            }
            return affectedRows;
        }
    }

    public int delete(String pTableName, String pWhereClause, String[] pWhereArgs) {
        return this.delete(pTableName, pWhereClause, pWhereArgs, false, false, false);
    }

    public int delete(String pTableName, String pWhereClause, String[] pWhereArgs, boolean pIsBeginTransaction) {
        return this.delete(pTableName, pWhereClause, pWhereArgs, pIsBeginTransaction, false, false);
    }

    public int delete(String pTableName, String pWhereClause, String[] pWhereArgs, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        return this.delete(pTableName, pWhereClause, pWhereArgs, false, pIsCommitTransaction, pIsCloseOnEnd);
    }

    public int deleteThenCommit(String pTableName, String pWhereClause, String[] pWhereArgs) {
        return this.delete(pTableName, pWhereClause, pWhereArgs, false, true, true);
    }

    public int deleteThenClose(String pTableName, String pWhereClause, String[] pWhereArgs) {
        return this.delete(pTableName, pWhereClause, pWhereArgs, false, false, true);
    }

    public int delete(String pTableName, String pWhereClause, String[] pWhereArgs, boolean pIsBeginTransaction, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        this.open(SQLiteDatabase.OPEN_READWRITE);
        if(pIsBeginTransaction) {
            this.beginTransaction();
        }

        int affectedRows = this.mSQLiteDatabase.delete(pTableName, pWhereClause, pWhereArgs);

        if(pIsCommitTransaction) {
            this.commitTransaction();
        }
        if(pIsCloseOnEnd) {
            this.close();
        }
        return affectedRows;
    }

    public Cursor select(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher) {
        return this.select(pSql, pSelectionArgs, pDataFetcher, false, false, false);
    }

    public Cursor select(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher, boolean pIsBeginTransaction) {
        return this.select(pSql, pSelectionArgs, pDataFetcher, pIsBeginTransaction, false, false);
    }

    public Cursor select(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        return this.select(pSql, pSelectionArgs, pDataFetcher, false, pIsCommitTransaction, pIsCloseOnEnd);
    }

    public Cursor selectThenCommit(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher) {
        return this.select(pSql, pSelectionArgs, pDataFetcher, false, true, true);
    }

    public Cursor selectThenClose(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher) {
        return this.select(pSql, pSelectionArgs, pDataFetcher, false, false, true);
    }

    public Cursor select(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher, boolean pIsBeginTransaction, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        this.open(SQLiteDatabase.OPEN_READONLY);
        if(pIsBeginTransaction) {
            this.beginTransaction();
        }

        Cursor cursor = this.mSQLiteDatabase.rawQuery(pSql, pSelectionArgs);
        if(pDataFetcher != null) {
            pDataFetcher.fetchData(cursor);
            cursor.close();
            cursor = null;
        }

        if(pIsCommitTransaction) {
            this.commitTransaction();
        }
        if(pIsCloseOnEnd) {
            this.close();
        }

        return cursor;
    }

    public Cursor select(SQLiteQueryBuilder pSQLiteQueryBuilder, String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, IDataFetcher pDataFetcher) {
        return this.select(pSQLiteQueryBuilder, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, pDataFetcher, false, false, false);
    }

    public Cursor select(SQLiteQueryBuilder pSQLiteQueryBuilder, String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, IDataFetcher pDataFetcher, boolean pIsBeginTransaction) {
        return this.select(pSQLiteQueryBuilder, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, pDataFetcher, pIsBeginTransaction, false, false);
    }

    public Cursor select(SQLiteQueryBuilder pSQLiteQueryBuilder, String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, IDataFetcher pDataFetcher, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        return this.select(pSQLiteQueryBuilder, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, pDataFetcher, false, pIsCommitTransaction, pIsCloseOnEnd);
    }

    public Cursor selectThenCommit(SQLiteQueryBuilder pSQLiteQueryBuilder, String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, IDataFetcher pDataFetcher) {
        return this.select(pSQLiteQueryBuilder, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, pDataFetcher, false, true, true);
    }

    public Cursor selectThenClose(SQLiteQueryBuilder pSQLiteQueryBuilder, String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, IDataFetcher pDataFetcher) {
        return this.select(pSQLiteQueryBuilder, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, pDataFetcher, false, false, true);
    }

    public Cursor select(SQLiteQueryBuilder pSQLiteQueryBuilder, String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, IDataFetcher pDataFetcher, boolean pIsBeginTransaction, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        this.open(SQLiteDatabase.OPEN_READWRITE);
        if(pIsBeginTransaction) {
            this.beginTransaction();
        }

        Cursor cursor = pSQLiteQueryBuilder.query(this.mSQLiteDatabase, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder);
        if(pDataFetcher != null) {
            pDataFetcher.fetchData(cursor);
            cursor.close();
            cursor = null;
        }

        if(pIsCommitTransaction) {
            this.commitTransaction();
        }
        if(pIsCloseOnEnd) {
            this.close();
        }

        return cursor;
    }

    synchronized public void beginTransaction() {
        if(!this.mIsInTransaction) {
            this.mSQLiteDatabase.beginTransaction();
            this.mIsInTransaction = true;
        }
    }

    synchronized public void commitTransaction() {
        if(this.mIsInTransaction) {
            this.mSQLiteDatabase.setTransactionSuccessful();
            this.endTransaction();
        }
    }

    synchronized public void rollbackTransaction() {
        this.endTransaction();
    }

    synchronized public void endTransaction() {
        if(this.mIsInTransaction) {
            this.mSQLiteDatabase.endTransaction();
            this.mIsInTransaction = false;
        }
    }

    public interface IDataFetcher {
        void fetchData(Cursor pCursor);
    }
}
