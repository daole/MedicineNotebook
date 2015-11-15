package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.app.PendingIntent;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.View;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.ContentProviderMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableSchedule;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;
import com.dreamdigitizers.drugmanagement.data.models.FamilyMember;
import com.dreamdigitizers.drugmanagement.data.models.MedicineInterval;
import com.dreamdigitizers.drugmanagement.data.models.MedicineTime;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicine;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleAdd;
import com.dreamdigitizers.drugmanagement.utils.AlarmUtils;
import com.dreamdigitizers.drugmanagement.utils.StringUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleAdd;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityAlarm;
import com.dreamdigitizers.drugmanagement.views.implementations.receivers.ReceiverBoot;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class PresenterScheduleAdd implements IPresenterScheduleAdd {
    private static final int LOADER_ID__FAMILY_MEMBER = 0;
    //private static final int LOADER_ID__MEDICINE = 1;
    private static final int LOADER_ID__MEDICINE_TIME = 1;
    private static final int LOADER_ID__MEDICINE_INTERVAL = 2;

    private IViewScheduleAdd mView;
    private SimpleCursorAdapter mFamilyMemberAdapter;
    //private SimpleCursorAdapter mMedicineAdapter;
    private SimpleCursorAdapter mMedicineTimeAdapter;
    private SimpleCursorAdapter mMedicineIntervalAdapter;

    public PresenterScheduleAdd(IViewScheduleAdd pView) {
        this.mView = pView;
        this.mView.getViewLoaderManager().initLoader(PresenterScheduleAdd.LOADER_ID__FAMILY_MEMBER, null, this);
        //this.mView.getViewLoaderManager().initLoader(PresenterScheduleAdd.LOADER_ID__MEDICINE, null, this);
        this.mView.getViewLoaderManager().initLoader(PresenterScheduleAdd.LOADER_ID__MEDICINE_TIME, null, this);
        this.mView.getViewLoaderManager().initLoader(PresenterScheduleAdd.LOADER_ID__MEDICINE_INTERVAL, null, this);
        this.createFamilyMemberAdapter();
        //this.createMedicineAdapter();
        this.createMedicineTimeAdapter();
        this.createMedicineIntervalAdapter();
    }

    @Override
    public void insert(FamilyMember pFamilyMember,
                        ArrayMap<Long, TakenMedicine> pTakenMedicines,
                        String pStartDate,
                        MedicineTime pMedicineTime,
                        MedicineInterval pMedicineInterval,
                        boolean pIsAlarm,
                        String pTimes,
                        String pScheduleNote) {
        int result = this.checkInputData(pFamilyMember,
                pTakenMedicines,
                pStartDate,
                pMedicineTime,
                pMedicineInterval,
                pTimes);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues schedule = this.buildScheduleData(pFamilyMember,
                pStartDate,
                pMedicineTime,
                pMedicineInterval,
                pTimes,
                pScheduleNote);

        List<ContentValues> takenMedicines = this.buildTakenMedicineData(pTakenMedicines);

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(ContentProviderOperation.newInsert(ContentProviderMedicine.CONTENT_URI__SCHEDULE)
                .withValues(schedule)
                .build());

        for(ContentValues takenMedicine : takenMedicines) {
            operations.add(ContentProviderOperation.newInsert(ContentProviderMedicine.CONTENT_URI__TAKEN_MEDICINE)
                    .withValueBackReference(TableTakenMedicine.COLUMN_NAME__SCHEDULE_ID, 0)
                    .withValues(takenMedicine)
                    .build());
        }

        List<ContentValues> alarms = null;
        try {
            alarms = this.buildAlarmData(pFamilyMember,
                    pStartDate,
                    pMedicineTime,
                    pMedicineInterval,
                    pIsAlarm,
                    pTimes);

            for(ContentValues alarm : alarms) {
                operations.add(ContentProviderOperation.newInsert(ContentProviderMedicine.CONTENT_URI__ALARM)
                        .withValueBackReference(TableAlarm.COLUMN_NAME__SCHEDULE_ID, 0)
                        .withValues(alarm)
                        .build());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            this.mView.showError(R.string.error__unknown_error);
            return;
        }

        ContentProviderResult[] results;
        try {
            results = this.mView.getViewContext().getContentResolver().applyBatch(ContentProviderMedicine.AUTHORITY, operations);
        } catch (RemoteException e) {
            e.printStackTrace();
            this.mView.showError(R.string.error__unknown_error);
            return;
        } catch (OperationApplicationException e) {
            e.printStackTrace();
            this.mView.showError(R.string.error__unknown_error);
            return;
        }

        if(pIsAlarm && alarms != null && alarms.size() > 0) {
            AlarmUtils.enableBootReceiver(this.mView.getViewContext(), ReceiverBoot.class);

            int alarmResultStartIndex = takenMedicines.size() + 1;
            for (int i = alarmResultStartIndex; i < results.length; i++) {
                ContentValues alarm = alarms.get(i - alarmResultStartIndex);
                int year = alarm.getAsInteger(TableAlarm.COLUMN_NAME__ALARM_YEAR);
                int month = alarm.getAsInteger(TableAlarm.COLUMN_NAME__ALARM_MONTH);
                int date = alarm.getAsInteger(TableAlarm.COLUMN_NAME__ALARM_DATE);
                int hour = alarm.getAsInteger(TableAlarm.COLUMN_NAME__ALARM_HOUR);
                int minute = alarm.getAsInteger(TableAlarm.COLUMN_NAME__ALARM_MINUTE);
                long rowId = Long.parseLong(results[i].uri.getLastPathSegment());

                Bundle extras = new Bundle();
                extras.putLong(Constants.BUNDLE_KEY__ROW_ID, rowId);
                PendingIntent pendingIntent = AlarmUtils.createPendingIntent(this.mView.getViewContext(),
                        ActivityAlarm.class,
                        (int)rowId,
                        Intent.FLAG_ACTIVITY_NEW_TASK,
                        extras);

                AlarmUtils.setAlarm(this.mView.getViewContext(), pendingIntent, year, month, date, hour, minute);
            }
        }

        this.mView.clearInput();
        this.mView.showMessage(R.string.message__insert_successful);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        CursorLoader cursorLoader;
        Uri contentUri;

        switch(pId) {
            case PresenterScheduleAdd.LOADER_ID__FAMILY_MEMBER:
                projection = TableFamilyMember.getColumns().toArray(projection);
                contentUri = ContentProviderMedicine.CONTENT_URI__FAMILY_MEMBER;
                break;
            /*
            case PresenterScheduleAdd.LOADER_ID__MEDICINE:
                projection = TableMedicine.getColumns().toArray(projection);
                contentUri = ContentProviderMedicine.CONTENT_URI__MEDICINE;
                break;
            */
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_TIME:
                projection = TableMedicineTime.getColumns().toArray(projection);
                contentUri = ContentProviderMedicine.CONTENT_URI__MEDICINE_TIME;
                break;
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_INTERVAL:
                contentUri = ContentProviderMedicine.CONTENT_URI__MEDICINE_INTERVAL;
                projection = TableMedicineInterval.getColumns().toArray(projection);
                break;
            default:
                return null;
        }

        cursorLoader = new CursorLoader(this.mView.getViewContext(), contentUri, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> pLoader, Cursor pData) {
        String[] projection = new String[0];
        MatrixCursor extras;
        Cursor cursor;
        SimpleCursorAdapter adapter;
        int id = pLoader.getId();

        switch(id) {
            case PresenterScheduleAdd.LOADER_ID__FAMILY_MEMBER:
                projection = TableFamilyMember.getColumns().toArray(projection);
                extras = new MatrixCursor(projection);
                extras.addRow(new Object[]{Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select)});
                adapter = this.mFamilyMemberAdapter;
                break;
            /*
            case PresenterScheduleAdd.LOADER_ID__MEDICINE:
                projection = TableMedicine.getColumns().toArray(projection);
                extras = new MatrixCursor(projection);
                extras.addRow(new Object[] {Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select), "", ""});
                adapter = this.mMedicineAdapter;
                break;
            */
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_TIME:
                projection = TableMedicineTime.getColumns().toArray(projection);
                extras = new MatrixCursor(projection);
                extras.addRow(new Object[] {Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select), ""});
                adapter = this.mMedicineTimeAdapter;
                break;
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_INTERVAL:
                projection = TableMedicineInterval.getColumns().toArray(projection);
                extras = new MatrixCursor(projection);
                extras.addRow(new Object[] {Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select), ""});
                adapter = this.mMedicineIntervalAdapter;
                break;
            default:
                return;
        }

        cursor = new MergeCursor(new Cursor[]{extras, pData});
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> pLoader) {
        SimpleCursorAdapter adapter;
        int id = pLoader.getId();

        switch(id) {
            case PresenterScheduleAdd.LOADER_ID__FAMILY_MEMBER:
                adapter = this.mFamilyMemberAdapter;
                break;
            /*
            case PresenterScheduleAdd.LOADER_ID__MEDICINE:
                adapter = this.mMedicineAdapter;
                break;
            */
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_TIME:
                adapter = this.mMedicineTimeAdapter;
                break;
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_INTERVAL:
                adapter = this.mMedicineIntervalAdapter;
                break;
            default:
                return;
        }

        adapter.swapCursor(null);
    }

    private void createFamilyMemberAdapter() {
        String[] from = new String[] {TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mFamilyMemberAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mFamilyMemberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setFamilyMemberAdapter(this.mFamilyMemberAdapter);
    }

    /*
    private void createMedicineAdapter() {
        String[] from = new String[] {TableMedicine.COLUMN_NAME__FALLBACK_MEDICINE_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mMedicineAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mMedicineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setMedicineAdapter(this.mMedicineAdapter);
    }
    */

    private void createMedicineTimeAdapter() {
        String[] from = new String[] {TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME, TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE};
        int[] to = new int[] {R.id.lblText1, R.id.lblText2};
        this.mMedicineTimeAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                R.layout.part__spinner_2_text_views, null, from, to, 0);
        this.mMedicineTimeAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View pView, Cursor pCursor, int pColumnIndex) {
                if (pView.getId() == R.id.lblText2) {
                    String medicineTimeValue = pCursor.getString(pCursor.getColumnIndex(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE));
                    if(TextUtils.isEmpty(medicineTimeValue)) {
                        pView.setVisibility(View.GONE);
                    } else {
                        pView.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
        this.mMedicineTimeAdapter.setDropDownViewResource(R.layout.part__spinner_2_text_views);
        this.mView.setMedicineTimeAdapter(this.mMedicineTimeAdapter);
    }

    private void createMedicineIntervalAdapter() {
        String[] from = new String[] {TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mMedicineIntervalAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mMedicineIntervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setMedicineIntervalAdapter(this.mMedicineIntervalAdapter);
    }

    private ContentValues buildScheduleData(FamilyMember pFamilyMember,
                                            String pStartDate,
                                            MedicineTime pMedicineTime,
                                            MedicineInterval pMedicineInterval,
                                            String pTimes,
                                            String pScheduleNote) {
        long familyMemberId = pFamilyMember.getRowId();
        String fallbackFamilyMemberName = pFamilyMember.getFamilyMemberName();
        long medicineTimeId = pMedicineTime.getRowId();
        long medicineIntervalId = pMedicineInterval.getRowId();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableSchedule.COLUMN_NAME__FAMILY_MEMBER_ID, familyMemberId);
        contentValues.put(TableSchedule.COLUMN_NAME__MEDICINE_TIME_ID, medicineTimeId);
        contentValues.put(TableSchedule.COLUMN_NAME__MEDICINE_INTERVAL_ID, medicineIntervalId);
        contentValues.put(TableSchedule.COLUMN_NAME__FALLBACK_FAMILY_MEMBER_NAME, fallbackFamilyMemberName);
        contentValues.put(TableSchedule.COLUMN_NAME__START_DATE, pStartDate);
        contentValues.put(TableSchedule.COLUMN_NAME__TIMES, pTimes);
        contentValues.put(TableSchedule.COLUMN_NAME__SCHEDULE_NOTE, pScheduleNote);

        return contentValues;
    }

    private List<ContentValues> buildTakenMedicineData(ArrayMap<Long, TakenMedicine> pTakenMedicines) {
        List<ContentValues> takenMedicines = new ArrayList<>();
        for(int i = 0; i < pTakenMedicines.size(); i++) {
            TakenMedicine takenMedicine = pTakenMedicines.valueAt(i);
            long medicineId = takenMedicine.getMedicineId();
            String fallbackMedicineName = takenMedicine.getFallbackMedicineName();
            String dose = takenMedicine.getDose();

            ContentValues contentValues = new ContentValues();
            contentValues.put(TableTakenMedicine.COLUMN_NAME__MEDICINE_ID, medicineId);
            contentValues.put(TableTakenMedicine.COLUMN_NAME__FALLBACK_MEDICINE_NAME, fallbackMedicineName);
            contentValues.put(TableTakenMedicine.COLUMN_NAME__DOSE, dose);

            takenMedicines.add(contentValues);
        }

        return takenMedicines;
    }

    private List<ContentValues> buildAlarmData(FamilyMember pFamilyMember,
                                String pStartDate,
                                MedicineTime pMedicineTime,
                                MedicineInterval pMedicineInterval,
                                boolean pIsAlarm,
                                String pTimes) throws ParseException {

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.mView.getViewContext());
        Date startDate = dateFormat.parse(pStartDate);

        int intervalValue = pMedicineInterval.getMedicineIntervalValue();
        int times = Integer.parseInt(pTimes);

        String[] medicineTimeValues = pMedicineTime.getMedicineTimeValues();
        int[] hours = new int[medicineTimeValues.length];
        int[] minutes = new int[medicineTimeValues.length];
        for(int i = 0; i < medicineTimeValues.length; i++) {
            String[] medicineTime = medicineTimeValues[i].split(Constants.DELIMITER__TIME);
            hours[i] = Integer.parseInt(medicineTime[0]);
            minutes[i] = Integer.parseInt(medicineTime[1]);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        List<ContentValues> alarms = new ArrayList<>();
        if(intervalValue <= 0) {
            times = 1;
        }
        for(int i = 0; i < times; i++) {
            if(i > 0) {
                calendar.add(Calendar.DATE, intervalValue);
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int date = calendar.get(Calendar.DATE);
            for(int j = 0; j < medicineTimeValues.length; j++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TableAlarm.COLUMN_NAME__ALARM_YEAR, year);
                contentValues.put(TableAlarm.COLUMN_NAME__ALARM_MONTH, month);
                contentValues.put(TableAlarm.COLUMN_NAME__ALARM_DATE, date);
                contentValues.put(TableAlarm.COLUMN_NAME__ALARM_HOUR, hours[j]);
                contentValues.put(TableAlarm.COLUMN_NAME__ALARM_MINUTE, minutes[j]);
                contentValues.put(TableAlarm.COLUMN_NAME__IS_ALARM, pIsAlarm);

                alarms.add(contentValues);
            }
        }

        return alarms;
    }

    private int checkInputData(FamilyMember pFamilyMember,
                               ArrayMap pTakenMedicines,
                               String pStartDate,
                               MedicineTime pMedicineTime,
                               MedicineInterval pMedicineInterval,
                               String pTimes) {
        if(pFamilyMember == null) {
            return R.string.error__blank_family_member;
        }
        if(pTakenMedicines == null || pTakenMedicines.size() <= 0) {
            return R.string.error__blank_medicine;
        }
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.mView.getViewContext());
        if(!StringUtils.isTime(pStartDate, dateFormat)) {
            return R.string.error__invalid_start_date;
        }
        if(pMedicineTime == null) {
            return R.string.error__blank_medicine_time;
        }
        if(pMedicineInterval == null) {
            return R.string.error__blank_medicine_interval;
        }
        if(!StringUtils.isInteger(pTimes)) {
            return R.string.error__invalid_alarm_times;
        }
        return 0;
    }
}
