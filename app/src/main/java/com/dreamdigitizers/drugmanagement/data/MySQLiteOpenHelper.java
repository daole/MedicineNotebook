package com.dreamdigitizers.drugmanagement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	private static final String DB__NAME = "DrugManagement";
	private static final int DB__VERSION = 1;

	public MySQLiteOpenHelper(Context pContext) {
		super(pContext, MySQLiteOpenHelper.DB__NAME, null, MySQLiteOpenHelper.DB__VERSION);
		//pContext.deleteDatabase(MySQLiteOpenHelper.DB__NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase pDatabase) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase pDatabase, int pOldVersion, int pNewVersion) {

	}
}
