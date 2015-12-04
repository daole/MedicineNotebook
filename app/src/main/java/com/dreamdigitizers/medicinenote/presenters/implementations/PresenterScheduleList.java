package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamdigitizers.medicinenote.Constants;
import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.data.ContentProviderMedicine;
import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.Table;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableAlarm;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableSchedule;
import com.dreamdigitizers.medicinenote.data.models.Alarm;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterScheduleList;
import com.dreamdigitizers.medicinenote.utils.AlarmUtils;
import com.dreamdigitizers.medicinenote.utils.DialogUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewScheduleList;
import com.dreamdigitizers.medicinenote.views.implementations.activities.ActivityAlarm;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class PresenterScheduleList implements IPresenterScheduleList {
    private IViewScheduleList mView;
    private SimpleCursorAdapter mAdapter;
    private List<Integer> mSelectedPositions;
    private ArrayMap<Long, Alarm> mSelectedAlarms;
    private Calendar mCalendar;

    public PresenterScheduleList(IViewScheduleList pView) {
        this.mView = pView;
        this.mSelectedPositions = new ArrayList<>();
        this.mSelectedAlarms = new ArrayMap<>();
        this.mCalendar = Calendar.getInstance();
        this.initLoader();
        this.createAdapter();
    }

    @Override
    public void delete() {
        if(this.mSelectedAlarms.isEmpty()) {
            this.mView.showError(R.string.error__no_data_selected);
            return;
        }

        Uri uri = ContentProviderMedicine.CONTENT_URI__ALARM;
        final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        for(Alarm alarm : this.mSelectedAlarms.values()) {
            operations.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(uri, alarm.getRowId())).build());
        }

        DialogUtils.IOnDialogButtonClickListener listener = new DialogUtils.IOnDialogButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Activity pActivity, String pTitle, String pMessage, boolean pIsTwoButton, String pPositiveButtonText, String pNegativeButtonText) {
                try {
                    for(Alarm alarm : PresenterScheduleList.this.mSelectedAlarms.values()) {
                        PresenterScheduleList.this.updateAlarmIntent(alarm.getRowId(), alarm.getAlarmYear(), alarm.getAlarmMonth(), alarm.getAlarmDate(), alarm.getAlarmMonth(), alarm.getAlarmMinute(), false);
                    }

                    PresenterScheduleList.this.mView.getViewContext().getContentResolver().applyBatch(ContentProviderMedicine.AUTHORITY, operations);
                    PresenterScheduleList.this.mSelectedPositions.clear();
                    PresenterScheduleList.this.mSelectedAlarms.clear();
                    PresenterScheduleList.this.mView.showMessage(R.string.message__delete_successful);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    PresenterScheduleList.this.mView.showError(R.string.error__unknown_error);
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                    PresenterScheduleList.this.mView.showError(R.string.error__unknown_error);
                }
            }

            @Override
            public void onNegativeButtonClick(Activity pActivity, String pTitle, String pMessage, boolean pIsTwoButton, String pPositiveButtonText, String pNegativeButtonText) {

            }
        };
        this.mView.showConfirmation(R.string.confirmation__delete_data, listener);
    }

    @Override
    public void previous() {
        this.mCalendar.add(Calendar.DATE, -1);
        this.initLoader();
    }

    @Override
    public void next() {
        this.mCalendar.add(Calendar.DATE, 1);
        this.initLoader();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        projection = TableAlarm.getColumnsForJoin().toArray(projection);

        int year = this.mCalendar.get(Calendar.YEAR);
        int month = this.mCalendar.get(Calendar.MONTH);
        int date = this.mCalendar.get(Calendar.DATE);
        String selection = this.buildDateSelectionClause(null, year, month, date);
        //selection = this.buildNotDoneSelectionClause(selection);

        String sortOrder = this.buildSortOrderByString();

        CursorLoader cursorLoader = new CursorLoader(this.mView.getViewContext(),
                ContentProviderMedicine.CONTENT_URI__ALARM, projection, selection, null, sortOrder);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> pLoader, Cursor pData) {
        this.mAdapter.swapCursor(pData);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> pLoader) {
        this.mAdapter.swapCursor(null);
    }

    private void createAdapter() {
        String[] from = new String[] {TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE, TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME, TableAlarm.COLUMN_NAME__IS_DONE, TableAlarm.COLUMN_NAME__IS_ALARM, Table.COLUMN_NAME__ID};
        int[] to = new int[] {R.id.lblMedicineTimeValue, R.id.lblFamilyMemberName, R.id.imgDone, R.id.chkAlarm, R.id.chkSelect};
        this.mAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                R.layout.part__schedule, null, from, to, 0);
        this.mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View pView, final Cursor pCursor, int pColumnIndex) {
                if (pView.getId() == R.id.lblMedicineTimeValue) {
                    TextView textView = (TextView) pView;
                    int hour = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_HOUR));
                    int minute = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_MINUTE));
                    String timeValue = String.format(Constants.FORMAT__TIME_VALUE, hour)
                            + Constants.DELIMITER__TIME
                            + String.format(Constants.FORMAT__TIME_VALUE, minute);
                    textView.setText(timeValue);
                    return true;
                }
                if (pView.getId() == R.id.lblFamilyMemberName) {
                    TextView textView = (TextView) pView;
                    String familyMemberName = pCursor.getString(pCursor.getColumnIndex(TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME));
                    if(TextUtils.isEmpty(familyMemberName)) {
                        String fallbackFamilyMemberName = pCursor.getString(pCursor.getColumnIndex(TableSchedule.COLUMN_NAME__FALLBACK_FAMILY_MEMBER_NAME));
                        textView.setText(fallbackFamilyMemberName);
                    } else {
                        textView.setText(familyMemberName);
                    }
                    return true;
                }
                if (pView.getId() == R.id.imgDone) {
                    ImageView imageView = (ImageView)pView;
                    boolean isDone = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__IS_DONE)) != 0 ? true : false;
                    if(isDone) {
                        imageView.setImageBitmap(BitmapFactory.decodeResource(PresenterScheduleList.this.mView.getViewContext().getResources(), R.drawable.icon__done));
                    } else {
                        imageView.setImageBitmap(null);
                    }
                    return true;
                }
                if (pView.getId() == R.id.chkAlarm) {
                    CheckBox checkBox = (CheckBox) pView;
                    checkBox.setOnCheckedChangeListener(null);
                    boolean isAlarm = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__IS_ALARM)) != 0 ? true : false;
                    checkBox.setChecked(isAlarm);

                    boolean isDone = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__IS_DONE)) != 0 ? true : false;
                    checkBox.setEnabled(!isDone);

                    final long rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
                    final int year = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_YEAR));
                    final int month = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_MONTH));
                    final int date = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_DATE));
                    final int hour = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_HOUR));
                    final int minute = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__ALARM_MINUTE));
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                            PresenterScheduleList.this.changeAlarmStatus(rowId, year, month, date, hour, minute, pIsChecked);
                        }
                    });

                    return true;
                }
                if (pView.getId() == R.id.chkSelect) {
                    CheckBox checkBox = (CheckBox) pView;
                    final int position = pCursor.getPosition();
                    final Alarm alarm = Alarm.fetchDataAtCurrentPosition(pCursor);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                            PresenterScheduleList.this.check(position, alarm, pIsChecked);
                        }
                    });

                    if (PresenterScheduleList.this.mSelectedPositions.contains(position)) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }

                    return true;
                }
                return false;
            }
        });
        this.mView.setAdapter(this.mAdapter);
    }

    private void initLoader() {
        PresenterScheduleList.this.mSelectedPositions.clear();
        PresenterScheduleList.this.mSelectedAlarms.clear();
        this.mView.getViewLoaderManager().destroyLoader(0);
        this.mView.getViewLoaderManager().initLoader(0, null, this);
        this.mView.bindSelectionDate(this.buildSelectionDateString());
    }

    private String buildSelectionDateString() {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.mView.getViewContext());
        dateFormat.setCalendar(this.mCalendar);
        String date = dateFormat.format(this.mCalendar.getTime());
        return date;
    }

    private String buildDateSelectionClause(String pSelection, int pYear, int pMonth, int pDate) {
        StringBuilder stringBuilder = new StringBuilder();

        if(!TextUtils.isEmpty(pSelection)) {
            stringBuilder.append(pSelection);
            stringBuilder.append(" AND ");
        }

        stringBuilder.append(TableAlarm.COLUMN_NAME__ALARM_YEAR);
        stringBuilder.append(" = ");
        stringBuilder.append(pYear);
        stringBuilder.append(" AND ");
        stringBuilder.append(TableAlarm.COLUMN_NAME__ALARM_MONTH);
        stringBuilder.append(" = ");
        stringBuilder.append(pMonth);
        stringBuilder.append(" AND ");
        stringBuilder.append(TableAlarm.COLUMN_NAME__ALARM_DATE);
        stringBuilder.append(" = ");
        stringBuilder.append(pDate);

        return stringBuilder.toString();
    }

    private String buildSortOrderByString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(TableAlarm.COLUMN_NAME__ALARM_HOUR);
        stringBuilder.append(" asc, ");
        stringBuilder.append(TableAlarm.COLUMN_NAME__ALARM_MINUTE);
        stringBuilder.append(" asc ");

        return stringBuilder.toString();
    }

    /*
    private String buildNotDoneSelectionClause(String pSelection) {
        StringBuilder stringBuilder = new StringBuilder();

        if(!TextUtils.isEmpty(pSelection)) {
            stringBuilder.append(pSelection);
            stringBuilder.append(" AND ");
        }

        stringBuilder.append(TableAlarm.COLUMN_NAME__IS_DONE);
        stringBuilder.append(" = 0 ");

        return stringBuilder.toString();
    }
    */

    private void changeAlarmStatus(Long pRowId, int pYear, int pMonth, int pDate, int pHour, int pMinute, boolean pIsChecked) {
        boolean result = this.updateAlarmStatus(pRowId, pIsChecked);
        if(result) {
            this.updateAlarmIntent(pRowId, pYear, pMonth, pDate, pHour, pMinute, pIsChecked);
        }
    }

    private boolean updateAlarmStatus(long pRowId, boolean pIsAlarm) {
        boolean result = false;

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableAlarm.COLUMN_NAME__IS_ALARM, pIsAlarm);

        Uri uri = ContentProviderMedicine.CONTENT_URI__ALARM;
        uri = ContentUris.withAppendedId(uri, pRowId);
        int affectedRows = this.mView.getViewContext().getContentResolver().update(
                uri, contentValues, null, null);
        if(affectedRows == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            this.mView.showError(R.string.error__duplicated_data);
        } else if(affectedRows == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            this.mView.showError(R.string.error__unknown_error);
        } else {
            this.mView.showMessage(R.string.message__edit_successful);
            result = true;
        }

        return result;
    }

    private void updateAlarmIntent(long pRowId, int pYear, int pMonth, int pDate, int pHour, int pMinute, boolean pIsAlarm) {
        Bundle extras = new Bundle();
        extras.putLong(Constants.BUNDLE_KEY__ROW_ID, pRowId);
        PendingIntent pendingIntent = AlarmUtils.createPendingIntent(this.mView.getViewContext(),
                ActivityAlarm.class,
                (int)pRowId,
                Intent.FLAG_ACTIVITY_NEW_TASK,
                extras);

        if(pIsAlarm) {
            AlarmUtils.setAlarm(this.mView.getViewContext(), pendingIntent, pYear, pMonth, pDate, pHour, pMinute);
        } else {
            AlarmUtils.cancelAlarm(this.mView.getViewContext(), pendingIntent);
        }
    }

    private void check(Integer pPosition, Alarm pAlarm, boolean pIsChecked) {
        if(pIsChecked) {
            if(!this.mSelectedPositions.contains(pPosition)) {
                this.mSelectedPositions.add(pPosition);
            }
            if(!this.mSelectedAlarms.containsKey(pAlarm.getRowId())) {
                this.mSelectedAlarms.put(pAlarm.getRowId(), pAlarm);
            }
        } else {
            if(this.mSelectedPositions.contains(pPosition)) {
                this.mSelectedPositions.remove(pPosition);
            }
            if(this.mSelectedAlarms.containsKey(pAlarm.getRowId())) {
                this.mSelectedAlarms.remove(pAlarm.getRowId());
            }
        }
    }
}
