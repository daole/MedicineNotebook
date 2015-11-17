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
import com.dreamdigitizers.drugmanagement.data.dal.DaoAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.DaoFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.DaoMedicineTime;
import com.dreamdigitizers.drugmanagement.data.dal.DaoPrescription;
import com.dreamdigitizers.drugmanagement.data.dal.DaoSchedule;
import com.dreamdigitizers.drugmanagement.data.dal.DaoTakenMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TablePrescription;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableSchedule;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;
import com.dreamdigitizers.drugmanagement.utils.StringUtils;

import java.util.ArrayList;

public class ContentProviderMedicine extends ContentProvider {
    protected static final String ERROR_MESSAGE__UNKNOWN_COLUMNS = "There are unknown columns in projection";
    protected static final String ERROR_MESSAGE__UNKNOWN_URI = "Unknown Uri: %s";

    private static final int ALARMS = 0;
    private static final int ALARM = 1;
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
    private static final int PRESCRIPTIONS = 60;
    private static final int PRESCRIPTION = 61;
    private static final int SCHEDULES = 70;
    private static final int SCHEDULE = 71;
    private static final int TAKEN_MEDICINES = 80;
    private static final int TAKEN_MEDICINE = 81;

    private static final String SCHEME = "content://";

    public static final String AUTHORITY = "com.dreamdigitizers.drugmanagement.contentprovider";

    public static final Uri CONTENT_URI__ALARM = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TableAlarm.TABLE_NAME);
    public static final Uri CONTENT_URI__FAMILY_MEMBER = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TableFamilyMember.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TableMedicine.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE_CATEGORY = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TableMedicineCategory.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE_INTERVAL = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TableMedicineInterval.TABLE_NAME);
    public static final Uri CONTENT_URI__MEDICINE_TIME = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TableMedicineTime.TABLE_NAME);
    public static final Uri CONTENT_URI__PRESCRIPTION = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TablePrescription.TABLE_NAME);
    public static final Uri CONTENT_URI__SCHEDULE = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TableSchedule.TABLE_NAME);
    public static final Uri CONTENT_URI__TAKEN_MEDICINE = Uri.parse(ContentProviderMedicine.SCHEME + ContentProviderMedicine.AUTHORITY + "/" + TableTakenMedicine.TABLE_NAME);

    public static final String CONTENT_TYPE__ALARM = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableAlarm.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__ALARM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableAlarm.TABLE_NAME;

    public static final String CONTENT_TYPE__FAMILY_MEMBER = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableFamilyMember.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__FAMILY_MEMBER = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableFamilyMember.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableMedicine.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableMedicine.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE_CATEGORY = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableMedicineCategory.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE_CATEGORY = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableMedicineCategory.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE_INTERVAL = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableMedicineInterval.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE_INTERVAL = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableMedicineInterval.TABLE_NAME;

    public static final String CONTENT_TYPE__MEDICINE_TIME = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableMedicineTime.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__MEDICINE_TIME = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableMedicineTime.TABLE_NAME;

    public static final String CONTENT_TYPE__PRESCRIPTION = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TablePrescription.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__PRESCRIPTION = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TablePrescription.TABLE_NAME;

    public static final String CONTENT_TYPE__SCHEDULE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableSchedule.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__SCHEDULE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + ContentProviderMedicine.AUTHORITY + "." + TableSchedule.TABLE_NAME;

    public static final String CONTENT_TYPE__TAKEN_MEDICINE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TableTakenMedicine.TABLE_NAME;
    public static final String CONTENT_ITEM_TYPE__TAKEN_MEDICINE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + TableTakenMedicine.TABLE_NAME;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableAlarm.TABLE_NAME, ContentProviderMedicine.ALARMS);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableAlarm.TABLE_NAME + "/#", ContentProviderMedicine.ALARM);

        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableFamilyMember.TABLE_NAME, ContentProviderMedicine.FAMILY_MEMBERS);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableFamilyMember.TABLE_NAME + "/#", ContentProviderMedicine.FAMILY_MEMBER);

        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableMedicine.TABLE_NAME, ContentProviderMedicine.MEDICINES);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableMedicine.TABLE_NAME + "/#", ContentProviderMedicine.MEDICINE);

        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableMedicineCategory.TABLE_NAME, ContentProviderMedicine.MEDICINE_CATEGORIES);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableMedicineCategory.TABLE_NAME + "/#", ContentProviderMedicine.MEDICINE_CATEGORY);

        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableMedicineInterval.TABLE_NAME, ContentProviderMedicine.MEDICINE_INTERVALS);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableMedicineInterval.TABLE_NAME + "/#", ContentProviderMedicine.MEDICINE_INTERVAL);

        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableMedicineTime.TABLE_NAME, ContentProviderMedicine.MEDICINE_TIMES);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableMedicineTime.TABLE_NAME + "/#", ContentProviderMedicine.MEDICINE_TIME);

        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TablePrescription.TABLE_NAME, ContentProviderMedicine.PRESCRIPTIONS);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TablePrescription.TABLE_NAME + "/#", ContentProviderMedicine.PRESCRIPTION);

        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableSchedule.TABLE_NAME, ContentProviderMedicine.SCHEDULES);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableSchedule.TABLE_NAME + "/#", ContentProviderMedicine.SCHEDULE);

        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableTakenMedicine.TABLE_NAME, ContentProviderMedicine.TAKEN_MEDICINES);
        ContentProviderMedicine.uriMatcher.addURI(ContentProviderMedicine.AUTHORITY, TableTakenMedicine.TABLE_NAME + "/#", ContentProviderMedicine.TAKEN_MEDICINE);
    }

    private DatabaseHelper mDatabaseHelper;

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

    @Override
    public boolean onCreate() {
        this.mDatabaseHelper = new DatabaseHelper(this.getContext());
        return true;
    }

    @Override
    public String getType(Uri pUri) {
        String type = null;
        int uriType = ContentProviderMedicine.uriMatcher.match(pUri);
        switch (uriType) {
            case ContentProviderMedicine.ALARMS:
                type = ContentProviderMedicine.CONTENT_TYPE__ALARM;
                break;
            case ContentProviderMedicine.ALARM:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__ALARM;
                break;
            case ContentProviderMedicine.FAMILY_MEMBERS:
                type = ContentProviderMedicine.CONTENT_TYPE__FAMILY_MEMBER;
                break;
            case ContentProviderMedicine.FAMILY_MEMBER:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__FAMILY_MEMBER;
                break;
            case ContentProviderMedicine.MEDICINES:
                type = ContentProviderMedicine.CONTENT_TYPE__MEDICINE;
                break;
            case ContentProviderMedicine.MEDICINE:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__MEDICINE;
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORIES:
                type = ContentProviderMedicine.CONTENT_TYPE__MEDICINE_CATEGORY;
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORY:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__MEDICINE_CATEGORY;
                break;
            case ContentProviderMedicine.MEDICINE_INTERVALS:
                type = ContentProviderMedicine.CONTENT_TYPE__MEDICINE_INTERVAL;
                break;
            case ContentProviderMedicine.MEDICINE_INTERVAL:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__MEDICINE_INTERVAL;
                break;
            case ContentProviderMedicine.MEDICINE_TIMES:
                type = ContentProviderMedicine.CONTENT_TYPE__MEDICINE_TIME;
                break;
            case ContentProviderMedicine.MEDICINE_TIME:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__MEDICINE_TIME;
                break;
            case ContentProviderMedicine.PRESCRIPTIONS:
                type = ContentProviderMedicine.CONTENT_TYPE__PRESCRIPTION;
                break;
            case ContentProviderMedicine.PRESCRIPTION:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__PRESCRIPTION;
                break;
            case ContentProviderMedicine.SCHEDULES:
                type = ContentProviderMedicine.CONTENT_TYPE__SCHEDULE;
                break;
            case ContentProviderMedicine.SCHEDULE:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__SCHEDULE;
                break;
            case ContentProviderMedicine.TAKEN_MEDICINES:
                type = ContentProviderMedicine.CONTENT_TYPE__TAKEN_MEDICINE;
                break;
            case ContentProviderMedicine.TAKEN_MEDICINE:
                type = ContentProviderMedicine.CONTENT_ITEM_TYPE__TAKEN_MEDICINE;
                break;
            default:
                throw new IllegalArgumentException(String.format(ContentProviderMedicine.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }
        return type;
    }

    @Override
    public Cursor query(Uri pUri, String[] pProjection, String pSelection, String[] pSelectionArgs, String pSortOrder) {
        Cursor cursor = null;
        String id = null;
        Dao dao = null;

        int uriType = ContentProviderMedicine.uriMatcher.match(pUri);
        switch (uriType) {
            case ContentProviderMedicine.ALARMS:
                dao = new DaoAlarm(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.ALARM:
                id = pUri.getLastPathSegment();
                dao = new DaoAlarm(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.FAMILY_MEMBERS:
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.FAMILY_MEMBER:
                id = pUri.getLastPathSegment();
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINES:
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORIES:
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORY:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_INTERVALS:
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_INTERVAL:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_TIMES:
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_TIME:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.PRESCRIPTIONS:
                dao = new DaoPrescription(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.PRESCRIPTION:
                id = pUri.getLastPathSegment();
                dao = new DaoPrescription(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.SCHEDULES:
                dao = new DaoSchedule(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.SCHEDULE:
                id = pUri.getLastPathSegment();
                dao = new DaoSchedule(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.TAKEN_MEDICINES:
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.TAKEN_MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            default:
                throw new IllegalArgumentException(String.format(ContentProviderMedicine.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }

        if(dao != null) {
            /*
            boolean checkResult = dao.checkColumns(pProjection);
            if(!checkResult) {
                throw new IllegalArgumentException(ContentProviderMedicine.ERROR_MESSAGE__UNKNOWN_COLUMNS);
            }
            */
            if(id != null) {
                if(StringUtils.isEmpty(pSelection)) {
                    pSelection = "";
                } else {
                    pSelection += " and ";
                }
                pSelection += dao.getTableName() + "." + Table.COLUMN_NAME__ID + " = " + id;
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

        int uriType = ContentProviderMedicine.uriMatcher.match(pUri);
        switch (uriType) {
            case ContentProviderMedicine.ALARMS:
                dao = new DaoAlarm(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.FAMILY_MEMBERS:
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINES:
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORIES:
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_INTERVALS:
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_TIMES:
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.PRESCRIPTIONS:
                dao = new DaoPrescription(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.SCHEDULES:
                dao = new DaoSchedule(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.TAKEN_MEDICINES:
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            default:
                throw new IllegalArgumentException(String.format(ContentProviderMedicine.ERROR_MESSAGE__UNKNOWN_URI, pUri));
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

        int uriType = ContentProviderMedicine.uriMatcher.match(pUri);
        switch (uriType) {
            case ContentProviderMedicine.ALARMS:
                dao = new DaoAlarm(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.ALARM:
                id = pUri.getLastPathSegment();
                dao = new DaoAlarm(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.FAMILY_MEMBERS:
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.FAMILY_MEMBER:
                id = pUri.getLastPathSegment();
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINES:
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORIES:
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORY:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_INTERVALS:
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_INTERVAL:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_TIMES:
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_TIME:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.PRESCRIPTIONS:
                dao = new DaoPrescription(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.PRESCRIPTION:
                id = pUri.getLastPathSegment();
                dao = new DaoPrescription(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.SCHEDULES:
                dao = new DaoSchedule(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.SCHEDULE:
                id = pUri.getLastPathSegment();
                dao = new DaoSchedule(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.TAKEN_MEDICINES:
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.TAKEN_MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            default:
                throw new IllegalArgumentException(String.format(ContentProviderMedicine.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }

        if(dao != null) {
            if(id != null) {
                if(StringUtils.isEmpty(pSelection)) {
                    pSelection = "";
                } else {
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

        int uriType = ContentProviderMedicine.uriMatcher.match(pUri);
        switch (uriType) {
            case ContentProviderMedicine.ALARMS:
                dao = new DaoAlarm(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.ALARM:
                id = pUri.getLastPathSegment();
                dao = new DaoAlarm(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.FAMILY_MEMBERS:
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.FAMILY_MEMBER:
                id = pUri.getLastPathSegment();
                dao = new DaoFamilyMember(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINES:
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORIES:
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_CATEGORY:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineCategory(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_INTERVALS:
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_INTERVAL:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineInterval(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_TIMES:
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.MEDICINE_TIME:
                id = pUri.getLastPathSegment();
                dao = new DaoMedicineTime(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.PRESCRIPTIONS:
                dao = new DaoPrescription(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.PRESCRIPTION:
                id = pUri.getLastPathSegment();
                dao = new DaoPrescription(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.SCHEDULES:
                dao = new DaoSchedule(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.SCHEDULE:
                id = pUri.getLastPathSegment();
                dao = new DaoSchedule(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.TAKEN_MEDICINES:
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            case ContentProviderMedicine.TAKEN_MEDICINE:
                id = pUri.getLastPathSegment();
                dao = new DaoTakenMedicine(this.mDatabaseHelper);
                break;
            default:
                throw new IllegalArgumentException(String.format(ContentProviderMedicine.ERROR_MESSAGE__UNKNOWN_URI, pUri));
        }

        if(dao != null) {
            if(id != null) {
                if(TextUtils.isEmpty(pSelection)) {
                    pSelection = "";
                } else {
                    pSelection += " and ";
                }
                pSelection += Table.COLUMN_NAME__ID + " = " + id;
            }
            affectedRows = dao.delete(pSelection, pSelectionArgs);
            this.getContext().getContentResolver().notifyChange(pUri, null);
        }

        return affectedRows;
    }
}
