package com.dreamdigitizers.drugmanagement.data;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.data.dal.Dao;
import com.dreamdigitizers.drugmanagement.data.dal.DaoAlert;
import com.dreamdigitizers.drugmanagement.data.dal.DaoFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicineTime;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicineTimeSetting;
import com.dreamdigitizers.drugmanagement.data.dal.DaoTakenMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlert;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTimeSetting;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;
import com.dreamdigitizers.drugmanagement.utils.StringUtils;

import java.util.ArrayList;

public class MedicineContentProvider extends ContentProvider {
    protected static final String ERROR_MESSAGE__UNKNOWN_COLUMNS = "There are unknown columns in projection";
    protected static final String ERROR_MESSAGE__UNKNOWN_URI = "Unknown Uri: %s";

    private static final int ALERTS = 0;
    private static final int ALERT = 1;
    private static final int FAMILY_MEMBERS = 10;
    private static final int FAMILY_MEMBER = 11;
    private static final int MEDICINES = 20;
    private static final int MEDICINE = 21;
    private static final int MEDICINE_CATEGORIES = 30;
    private static final int MEDICINE_CATEGORY = 31;
    private static final int MEDICINE_INTERVALS = 40;
    private static final int MEDICINE_INTERVAL = 41;
    private static final int MEDICINE_TIMES = 50;
    private static final int MEDICINE_TIME = 51;
    private static final int MEDICINE_TIME_SETTINGS = 60;
    private static final int MEDICINE_TIME_SETTING = 61;
    private static final int TAKEN_MEDICINES = 70;
    private static final int TAKEN_MEDICINE = 71;

    private static final String SCHEME = "content://";
    private static final String AUTHORITY = "com.dreamdigitizers.drugmanagement.contentprovider";

    public static final Uri CONTENT_URI__ALERT = Uri.parse(MedicineContentProvider.SCHEME + MedicineContentProvider.AUTHORITY + "/" + TableAlert.TABLE_NAME);
    public static final Uri CONTENT_URI__FAMILY_MEMBER = Uri.parse(MedicineContentProvider.SCHEME + MedicineContentProvider.AUTHORITY + "/" + TableFamilyMember.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE = Uri.parse(MedicineContentProvider.SCHEME + MedicineContentProvider.AUTHORITY + "/" + TableMedicine.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE_CATEGORY = Uri.parse(MedicineContentProvider.SCHEME + MedicineContentProvider.AUTHORITY + "/" + TableMedicineCategory.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE_INTERVAL = Uri.parse(MedicineContentProvider.SCHEME + MedicineContentProvider.AUTHORITY + "/" + TableMedicineInterval.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE_TIME = Uri.parse(MedicineContentProvider.SCHEME + MedicineContentProvider.AUTHORITY + "/" + TableMedicineTime.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE_TIME_SETTING = Uri.parse(MedicineContentProvider.SCHEME + MedicineContentProvider.AUTHORITY + "/" + TableMedicineTimeSetting.TABLE_NAME);
    public static final Uri CONTENT_URI__TAKEN_MEDICINE = Uri.parse(MedicineContentProvider.SCHEME + MedicineContentProvider.AUTHORITY + "/" + TableTakenMedicine.TABLE_NAME);

    public static final String CONTENT_TYPE__ALERT = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableAlert.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__ALERT = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableAlert.TABLE_NAME;

    public static final String CONTENT_TYPE__FAMILY_MEMBER = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableFamilyMember.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__FAMILY_MEMBER = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableFamilyMember.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicine.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicine.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE_CATEGORY = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicineCategory.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE_CATEGORY = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicineCategory.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE_INTERVAL = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicineInterval.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE_INTERVAL = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicineInterval.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE_TIME = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicineTime.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE_TIME = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicineTime.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE_TIME_SETTING = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicineTimeSetting.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE_TIME_SETTING = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + MedicineContentProvider.AUTHORITY + "." + TableMedicineTimeSetting.TABLE_NAME;

    public static final String CONTENT_TYPE__TAKEN_MEDICINE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TableTakenMedicine.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__TAKEN_MEDICINE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TableTakenMedicine.TABLE_NAME;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableAlert.TABLE_NAME, MedicineContentProvider.ALERTS);
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableAlert.TABLE_NAME + "/#", MedicineContentProvider.ALERT);

        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableFamilyMember.TABLE_NAME, MedicineContentProvider.FAMILY_MEMBERS);
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableFamilyMember.TABLE_NAME + "/#", MedicineContentProvider.FAMILY_MEMBER);

        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicine.TABLE_NAME, MedicineContentProvider.MEDICINES);
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicine.TABLE_NAME + "/#", MedicineContentProvider.MEDICINE);

        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicineCategory.TABLE_NAME, MedicineContentProvider.MEDICINE_CATEGORIES);
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicineCategory.TABLE_NAME + "/#", MedicineContentProvider.MEDICINE_CATEGORY);

        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicineInterval.TABLE_NAME, MedicineContentProvider.MEDICINE_INTERVALS);
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicineInterval.TABLE_NAME + "/#", MedicineContentProvider.MEDICINE_INTERVAL);

        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicineTime.TABLE_NAME, MedicineContentProvider.MEDICINE_TIMES);
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicineTime.TABLE_NAME + "/#", MedicineContentProvider.MEDICINE_TIME);

        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicineTimeSetting.TABLE_NAME, MedicineContentProvider.MEDICINE_TIME_SETTINGS);
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableMedicineTimeSetting.TABLE_NAME + "/#", MedicineContentProvider.MEDICINE_TIME_SETTING);

        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableTakenMedicine.TABLE_NAME, MedicineContentProvider.TAKEN_MEDICINES);
        MedicineContentProvider.uriMatcher.addURI(MedicineContentProvider.AUTHORITY, TableTakenMedicine.TABLE_NAME + "/#", MedicineContentProvider.TAKEN_MEDICINE);
    }

    private DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        this.mDatabaseHelper = new DatabaseHelper(this.getContext());
        return true;
    }

    @Override
    public String getType(Uri pUri) {
        String type = null;
        int uriType = MedicineContentProvider.uriMatcher.match(pUri);
        switch (uriType) {
            case MedicineContentProvider.ALERTS:
                type = MedicineContentProvider.CONTENT_TYPE__ALERT;
                break;
            case MedicineContentProvider.ALERT:
                type = MedicineContentProvider.CONTENT_ITEM_TYPE__ALERT;
                break;
            case MedicineContentProvider.FAMILY_MEMBERS:
                type = MedicineContentProvider.CONTENT_TYPE__FAMILY_MEMBER;
                break;
            case MedicineContentProvider.FAMILY_MEMBER:
                type = MedicineContentProvider.CONTENT_ITEM_TYPE__FAMILY_MEMBER;
                break;
            case MedicineContentProvider.MEDICINES:
                type = MedicineContentProvider.CONTENT_TYPE__MEDICINE;
                break;
            case MedicineContentProvider.MEDICINE:
                type = MedicineContentProvider.CONTENT_ITEM_TYPE__MEDICINE;
                break;
            case MedicineContentProvider.MEDICINE_CATEGORIES:
                type = MedicineContentProvider.CONTENT_TYPE__MEDICINE_CATEGORY;
                break;
            case MedicineContentProvider.MEDICINE_CATEGORY:
                type = MedicineContentProvider.CONTENT_ITEM_TYPE__MEDICINE_CATEGORY;
                break;
            case MedicineContentProvider.MEDICINE_INTERVALS:
                type = MedicineContentProvider.CONTENT_TYPE__MEDICINE_INTERVAL;
                break;
            case MedicineContentProvider.MEDICINE_INTERVAL:
                type = MedicineContentProvider.CONTENT_ITEM_TYPE__MEDICINE_INTERVAL;
                break;
            case MedicineContentProvider.MEDICINE_TIMES:
                type = MedicineContentProvider.CONTENT_TYPE__MEDICINE_TIME;
                break;
            case MedicineContentProvider.MEDICINE_TIME:
                type = MedicineContentProvider.CONTENT_ITEM_TYPE__MEDICINE_TIME;
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTINGS:
                type = MedicineContentProvider.CONTENT_TYPE__MEDICINE_TIME_SETTING;
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTING:
                type = MedicineContentProvider.CONTENT_ITEM_TYPE__MEDICINE_TIME_SETTING;
                break;
            case MedicineContentProvider.TAKEN_MEDICINES:
                type = MedicineContentProvider.CONTENT_TYPE__TAKEN_MEDICINE;
                break;
            case MedicineContentProvider.TAKEN_MEDICINE:
                type = MedicineContentProvider.CONTENT_ITEM_TYPE__TAKEN_MEDICINE;
                break;
            default:
                throw new IllegalArgumentException(String.format(MedicineContentProvider.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }
        return type;
    }

    @Override
    public Cursor query(Uri pUri, String[] pProjection, String pSelection, String[] pSelectionArgs, String pSortOrder) {
        Cursor cursor = null;
        String id = null;
        Dao dao = null;

        int uriType = MedicineContentProvider.uriMatcher.match(pUri);
        switch (uriType) {
            case MedicineContentProvider.ALERTS:
                dao = new DaoAlert(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.ALERT:
                id = pUri.getLastPathSegment();
                dao = new DaoAlert(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.FAMILY_MEMBERS:
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.FAMILY_MEMBER:
                id = pUri.getLastPathSegment();
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINES:
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_CATEGORIES:
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_CATEGORY:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_INTERVALS:
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_INTERVAL:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIMES:
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTINGS:
                dao = new DaoMedicineTimeSetting(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTING:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTimeSetting(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.TAKEN_MEDICINES:
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.TAKEN_MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            default:
                throw new IllegalArgumentException(String.format(MedicineContentProvider.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }

        if(dao != null) {
            boolean checkResult = dao.checkColumns(pProjection);
            if(!checkResult) {
                throw new IllegalArgumentException(MedicineContentProvider.ERROR_MESSAGE__UNKNOWN_COLUMNS);
            }
            if(id != null) {
                if(!StringUtils.isEmpty(pSelection)) {
                    pSelection += " and ";
                }
                pSelection += Table.COLUMN_NAME__ID + " = " + id;
            }
            cursor = dao.select(pProjection, pSelection, pSelectionArgs, null, null, pSortOrder);
            cursor.setNotificationUri(this.getContext().getContentResolver(), pUri);
        }

        return cursor;
    }

    @Override
    public Uri insert(Uri pUri, ContentValues pValues) {
        Uri newDataUri = null;
        Dao dao = null;

        int uriType = MedicineContentProvider.uriMatcher.match(pUri);
        switch (uriType) {
            case MedicineContentProvider.ALERTS:
                dao = new DaoAlert(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.FAMILY_MEMBERS:
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINES:
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_CATEGORIES:
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_INTERVALS:
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIMES:
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTINGS:
                dao = new DaoMedicineTimeSetting(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.TAKEN_MEDICINES:
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            default:
                throw new IllegalArgumentException(String.format(MedicineContentProvider.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }

        if(dao != null) {
            long newId = dao.insert(pValues);
            newDataUri = ContentUris.withAppendedId(pUri, newId);
            this.getContext().getContentResolver().notifyChange(pUri, null);
        }

        return newDataUri;
    }

    @Override
    public int update(Uri pUri, ContentValues pValues, String pSelection, String[] pSelectionArgs) {
        int affectedRows = 0;
        String id = null;
        Dao dao = null;

        int uriType = MedicineContentProvider.uriMatcher.match(pUri);
        switch (uriType) {
            case MedicineContentProvider.ALERTS:
                dao = new DaoAlert(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.ALERT:
                id = pUri.getLastPathSegment();
                dao = new DaoAlert(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.FAMILY_MEMBERS:
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.FAMILY_MEMBER:
                id = pUri.getLastPathSegment();
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINES:
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_CATEGORIES:
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_CATEGORY:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_INTERVALS:
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_INTERVAL:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIMES:
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTINGS:
                dao = new DaoMedicineTimeSetting(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTING:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTimeSetting(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.TAKEN_MEDICINES:
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.TAKEN_MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            default:
                throw new IllegalArgumentException(String.format(MedicineContentProvider.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }

        if(dao != null) {
            if(id != null) {
                if(!StringUtils.isEmpty(pSelection)) {
                    pSelection += " and ";
                }
                pSelection += Table.COLUMN_NAME__ID + " = " + id;
            }
            affectedRows = dao.update(pValues, pSelection, pSelectionArgs);
            this.getContext().getContentResolver().notifyChange(pUri, null);
        }

        return affectedRows;
    }

    @Override
    public int delete(Uri pUri, String pSelection, String[] pSelectionArgs) {
        int affectedRows = 0;
        String id = null;
        Dao dao = null;

        int uriType = MedicineContentProvider.uriMatcher.match(pUri);
        switch (uriType) {
            case MedicineContentProvider.ALERTS:
                dao = new DaoAlert(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.ALERT:
                id = pUri.getLastPathSegment();
                dao = new DaoAlert(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.FAMILY_MEMBERS:
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.FAMILY_MEMBER:
                id = pUri.getLastPathSegment();
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINES:
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_CATEGORIES:
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_CATEGORY:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_INTERVALS:
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_INTERVAL:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIMES:
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTINGS:
                dao = new DaoMedicineTimeSetting(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.MEDICINE_TIME_SETTING:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTimeSetting(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.TAKEN_MEDICINES:
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            case MedicineContentProvider.TAKEN_MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            default:
                throw new IllegalArgumentException(String.format(MedicineContentProvider.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }

        if(dao != null) {
            if(id != null) {
                if(!TextUtils.isEmpty(pSelection)) {
                    pSelection += " and ";
                }
                pSelection += Table.COLUMN_NAME__ID + " = " + id;
            }
            affectedRows = dao.delete(pSelection, pSelectionArgs);
            this.getContext().getContentResolver().notifyChange(pUri, null);
        }

        return affectedRows;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> pOperations) throws OperationApplicationException {
        this.mDatabaseHelper.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(pOperations);
            this.mDatabaseHelper.commitTransaction();
            return results;
        } catch (OperationApplicationException e){
            this.mDatabaseHelper.rollbackTransaction();
            throw e;
        }
    }
}
