package com.dreamdigitizers.drugmanagement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableSchedule;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;

class MySQLiteOpenHelper extends SQLiteOpenHelper {
	private static final String DB__NAME = "drug_management.db";
	private static final int DB__VERSION = 1;

	private Context mContext;

	public MySQLiteOpenHelper(Context pContext) {
		super(pContext, MySQLiteOpenHelper.DB__NAME, null, MySQLiteOpenHelper.DB__VERSION);
		this.mContext = pContext;
		pContext.deleteDatabase(MySQLiteOpenHelper.DB__NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase pSQLiteDatabase) {
		TableAlarm.onCreate(this.mContext, pSQLiteDatabase);
		TableFamilyMember.onCreate(this.mContext, pSQLiteDatabase);
		TableMedicine.onCreate(this.mContext, pSQLiteDatabase);
		TableMedicineCategory.onCreate(this.mContext, pSQLiteDatabase);
		TableMedicineInterval.onCreate(this.mContext, pSQLiteDatabase);
		TableMedicineTime.onCreate(this.mContext, pSQLiteDatabase);
		TableSchedule.onCreate(this.mContext, pSQLiteDatabase);
		TableTakenMedicine.onCreate(this.mContext, pSQLiteDatabase);
	}

	@Override
	public void onUpgrade(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {
		TableAlarm.onUpgrade(this.mContext, pSQLiteDatabase, pOldVersion, pNewVersion);
		TableFamilyMember.onUpgrade(this.mContext, pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicine.onUpgrade(this.mContext, pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicineCategory.onUpgrade(this.mContext, pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicineInterval.onUpgrade(this.mContext, pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicineTime.onUpgrade(this.mContext, pSQLiteDatabase, pOldVersion, pNewVersion);
		TableSchedule.onUpgrade(this.mContext, pSQLiteDatabase, pOldVersion, pNewVersion);
		TableTakenMedicine.onUpgrade(this.mContext, pSQLiteDatabase, pOldVersion, pNewVersion);
	}
}
