package com.dreamdigitizers.drugmanagement.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlert;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTimeSetting;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	private static final String DB__NAME = "drug_management.db";
	private static final int DB__VERSION = 1;

	public MySQLiteOpenHelper(Context pContext) {
		super(pContext, MySQLiteOpenHelper.DB__NAME, null, MySQLiteOpenHelper.DB__VERSION);
		pContext.deleteDatabase(MySQLiteOpenHelper.DB__NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase pSQLiteDatabase) {
		TableAlert.onCreate(pSQLiteDatabase);
		TableFamilyMember.onCreate(pSQLiteDatabase);
		TableMedicine.onCreate(pSQLiteDatabase);
		TableMedicineCategory.onCreate(pSQLiteDatabase);
		TableMedicineInterval.onCreate(pSQLiteDatabase);
		TableMedicineTime.onCreate(pSQLiteDatabase);
		TableMedicineTimeSetting.onCreate(pSQLiteDatabase);
		TableTakenMedicine.onCreate(pSQLiteDatabase);
	}

	@Override
	public void onUpgrade(SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {
		TableAlert.onUpgrade(pSQLiteDatabase, pOldVersion, pNewVersion);
		TableFamilyMember.onUpgrade(pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicine.onUpgrade(pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicineCategory.onUpgrade(pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicineInterval.onUpgrade(pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicineTime.onUpgrade(pSQLiteDatabase, pOldVersion, pNewVersion);
		TableMedicineTimeSetting.onUpgrade(pSQLiteDatabase, pOldVersion, pNewVersion);
		TableTakenMedicine.onUpgrade(pSQLiteDatabase, pOldVersion, pNewVersion);
	}
}
