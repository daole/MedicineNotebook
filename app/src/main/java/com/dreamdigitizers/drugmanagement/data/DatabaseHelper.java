package com.dreamdigitizers.drugmanagement.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BichThao on 18/10/2015.
 */
public class DatabaseHelper {
    public static interface IDataFetcher {
        public void fetchData(Cursor pCursor);
    }

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
            if(e instanceof SQLiteConstraintException) {
                newID = DatabaseHelper.DB_ERROR_CODE__CONSTRAINT;
            } else {
                newID = DatabaseHelper.DB_ERROR_CODE__OTHER;
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

    public void select(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher) {
        this.select(pSql, pSelectionArgs, pDataFetcher, false, false, false);
    }

    public void select(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher, boolean pIsBeginTransaction) {
        this.select(pSql, pSelectionArgs, pDataFetcher, pIsBeginTransaction, false, false);
    }

    public void select(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        this.select(pSql, pSelectionArgs, pDataFetcher, false, pIsCommitTransaction, pIsCloseOnEnd);
    }

    public void selectThenCommit(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher) {
        this.select(pSql, pSelectionArgs, pDataFetcher, false, true, true);
    }

    public void selectThenClose(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher) {
        this.select(pSql, pSelectionArgs, pDataFetcher, false, false, true);
    }

    public void select(String pSql, String[] pSelectionArgs, IDataFetcher pDataFetcher, boolean pIsBeginTransaction, boolean pIsCommitTransaction, boolean pIsCloseOnEnd) {
        this.open(SQLiteDatabase.OPEN_READONLY);
        if(pIsBeginTransaction) {
            this.beginTransaction();
        }

        Cursor cursor = this.mSQLiteDatabase.rawQuery(pSql, pSelectionArgs);
        if(pDataFetcher != null) {
            pDataFetcher.fetchData(cursor);
        }

        if(pIsCommitTransaction) {
            this.commitTransaction();
        }
        if(pIsCloseOnEnd) {
            this.close();
        }
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
}
