package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.ContentProviderMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleList;

import java.util.ArrayList;
import java.util.List;

class PresenterScheduleList implements IPresenterScheduleList {
    private IViewScheduleList mView;
    private SimpleCursorAdapter mAdapter;
    private List<Integer> mSelectedPositions;
    private List<Long> mSelectedRowIds;

    public PresenterScheduleList(IViewScheduleList pView) {
        this.mView = pView;
        this.mView.getViewLoaderManager().initLoader(0, null, this);
        this.mSelectedPositions = new ArrayList<>();
        this.mSelectedRowIds = new ArrayList<>();
        this.createAdapter();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        projection = TableAlarm.getColumnsForJoin().toArray(projection);
        CursorLoader cursorLoader = new CursorLoader(this.mView.getViewContext(),
                ContentProviderMedicine.CONTENT_URI__ALARM, projection, null, null, null);
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
        String[] from = new String[] {TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE, TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME, TableAlarm.COLUMN_NAME__IS_ALARM, Table.COLUMN_NAME__ID};
        int[] to = new int[] {R.id.lblMedicineTimeValue, R.id.lblFamilyMemberName, R.id.chkAlarm, R.id.chkSelect};
        this.mAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                R.layout.part__schedule, null, from, to, 0);
        this.mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View pView, Cursor pCursor, int pColumnIndex) {
                if (pView.getId() == R.id.chkAlarm) {
                    CheckBox checkBox = (CheckBox)pView;
                    boolean isAlarm = pCursor.getInt(pCursor.getColumnIndex(TableAlarm.COLUMN_NAME__IS_ALARM)) != 0 ? true : false;
                    checkBox.setChecked(isAlarm);

                    final int position = pCursor.getPosition();
                    final long rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                            PresenterScheduleList.this.changeAlarmStatus(position, rowId, pIsChecked);
                        }
                    });

                    return true;
                }
                if (pView.getId() == R.id.chkSelect) {
                    CheckBox checkBox = (CheckBox)pView;
                    final int position = pCursor.getPosition();
                    final long rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                            PresenterScheduleList.this.check(position, rowId, pIsChecked);
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

    private void changeAlarmStatus(Integer pPosition, Long pRowId, boolean pIsChecked) {

    }

    private void check(Integer pPosition, Long pRowId, boolean pIsChecked) {
        if(pIsChecked) {
            if(!this.mSelectedPositions.contains(pPosition)) {
                this.mSelectedPositions.add(pPosition);
            }
            if(!this.mSelectedRowIds.contains(pRowId)) {
                this.mSelectedRowIds.add(pRowId);
            }
        } else {
            if(this.mSelectedPositions.contains(pPosition)) {
                this.mSelectedPositions.remove(pPosition);
            }
            if(this.mSelectedRowIds.contains(pRowId)) {
                this.mSelectedRowIds.remove(pRowId);
            }
        }
    }
}
