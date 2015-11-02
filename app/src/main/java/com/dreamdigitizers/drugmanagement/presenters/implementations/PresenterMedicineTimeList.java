package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeList;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeList;

import java.util.ArrayList;
import java.util.List;

class PresenterMedicineTimeList implements IPresenterMedicineTimeList {
    private IViewMedicineTimeList mViewMedicineTimeList;
    private SimpleCursorAdapter mSimpleCursorAdapter;
    private List<Integer> mSelectedPositions;
    private List<Long> mSelectedRowIds;

    public PresenterMedicineTimeList(IViewMedicineTimeList pViewMedicineTimeList) {
        this.mViewMedicineTimeList = pViewMedicineTimeList;
        this.mViewMedicineTimeList.getViewLoaderManager().initLoader(0, null, this);
        this.mSelectedPositions = new ArrayList<>();
        this.mSelectedRowIds = new ArrayList<>();
        this.createAdapter();
    }

    @Override
    public void delete() {
        if(this.mSelectedRowIds.isEmpty()) {
            this.mViewMedicineTimeList.showError(R.string.error__no_data_selected);
            return;
        }

        Uri uri = MedicineContentProvider.CONTENT_URI__MEDICINE_TIME;
        final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        for(long rowId : this.mSelectedRowIds) {
            operations.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(uri, rowId)).build());
        }

        DialogUtils.IOnDialogButtonClickListener listener = new DialogUtils.IOnDialogButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Activity pActivity, String pTitle, String pMessage, boolean pIsTwoButton, String pPositiveButtonText, String pNegativeButtonText) {
                try {
                    PresenterMedicineTimeList.this.mViewMedicineTimeList.getViewContext().getContentResolver().applyBatch(MedicineContentProvider.AUTHORITY, operations);
                    PresenterMedicineTimeList.this.mSelectedPositions.clear();
                    PresenterMedicineTimeList.this.mSelectedRowIds.clear();
                    PresenterMedicineTimeList.this.mViewMedicineTimeList.showMessage(R.string.message__delete_successful);
                } catch (RemoteException e) {
                    PresenterMedicineTimeList.this.mViewMedicineTimeList.showError(R.string.error__unknown_error);
                } catch (OperationApplicationException e) {
                    PresenterMedicineTimeList.this.mViewMedicineTimeList.showError(R.string.error__unknown_error);
                }
            }

            @Override
            public void onNegativeButtonClick(Activity pActivity, String pTitle, String pMessage, boolean pIsTwoButton, String pPositiveButtonText, String pNegativeButtonText) {

            }
        };
        this.mViewMedicineTimeList.showConfirmation(R.string.confirmation__delete_data, listener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        projection = TableMedicineTime.getColumns().toArray(projection);
        CursorLoader cursorLoader = new CursorLoader(this.mViewMedicineTimeList.getViewContext(),
                MedicineContentProvider.CONTENT_URI__MEDICINE_TIME, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> pLoader, Cursor pData) {
        this.mSimpleCursorAdapter.swapCursor(pData);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> pLoader) {
        this.mSimpleCursorAdapter.swapCursor(null);
    }

    private void createAdapter() {
        String[] from = new String[] {TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME, TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE, TableMedicineTime.COLUMN_NAME__ID};
        int[] to = new int[] {R.id.lblMedicineTimeName, R.id.lblMedicineTimeValue, R.id.chkSelect};
        this.mSimpleCursorAdapter = new SimpleCursorAdapter(this.mViewMedicineTimeList.getViewContext(),
                R.layout.part__medicine_time, null, from, to, 0);
        this.mSimpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View pView, Cursor pCursor, int pColumnIndex) {
                if(pView.getId() == R.id.chkSelect) {
                    CheckBox checkBox = (CheckBox)pView;
                    final int position = pCursor.getPosition();
                    final long rowId = pCursor.getLong(TableMedicineTime.COLUMN_INDEX__ID);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                            PresenterMedicineTimeList.this.check(position, rowId, pIsChecked);
                        }
                    });

                    if(PresenterMedicineTimeList.this.mSelectedPositions.contains(position)) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }
                    return true;
                }
                return false;
            }
        });
        this.mViewMedicineTimeList.setAdapter(this.mSimpleCursorAdapter);
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
