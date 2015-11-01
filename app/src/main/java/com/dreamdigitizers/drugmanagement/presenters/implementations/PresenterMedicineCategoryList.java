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
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineCategoryList;

import java.util.ArrayList;
import java.util.List;

class PresenterMedicineCategoryList implements IPresenterMedicineCategoryList {
    private IViewMedicineCategoryList mViewMedicineCategoryList;
    private SimpleCursorAdapter mSimpleCursorAdapter;
    private List<Integer> mSelectedPositions;
    private List<Long> mSelectedRowIds;

    public PresenterMedicineCategoryList(IViewMedicineCategoryList pViewMedicineCategoryList) {
        this.mViewMedicineCategoryList = pViewMedicineCategoryList;
        this.mViewMedicineCategoryList.getViewLoaderManager().initLoader(0, null, this);
        this.mSelectedPositions = new ArrayList<>();
        this.mSelectedRowIds = new ArrayList<>();
        this.createAdapter();
    }

    @Override
    public void delete() {
        if(this.mSelectedRowIds.isEmpty()) {
            this.mViewMedicineCategoryList.showError(R.string.error__no_data_selected);
            return;
        }

        Uri uri = MedicineContentProvider.CONTENT_URI__MEDICINE_CATEGORY;
        final ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        for(long rowId : this.mSelectedRowIds) {
            operations.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(uri, rowId)).build());
        }

        DialogUtils.IOnDialogButtonClickListener listener = new DialogUtils.IOnDialogButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Activity pActivity, String pTitle, String pMessage, boolean pIsTwoButton, String pPositiveButtonText, String pNegativeButtonText) {
                try {
                    PresenterMedicineCategoryList.this.mViewMedicineCategoryList.getViewContext().getContentResolver().applyBatch(MedicineContentProvider.AUTHORITY, operations);
                    PresenterMedicineCategoryList.this.mSelectedPositions.clear();
                    PresenterMedicineCategoryList.this.mSelectedRowIds.clear();
                    PresenterMedicineCategoryList.this.mViewMedicineCategoryList.showMessage(R.string.message__delete_successful);
                } catch (RemoteException e) {
                    PresenterMedicineCategoryList.this.mViewMedicineCategoryList.showError(R.string.error__unknown_error);
                } catch (OperationApplicationException e) {
                    PresenterMedicineCategoryList.this.mViewMedicineCategoryList.showError(R.string.error__unknown_error);
                }
            }

            @Override
            public void onNegativeButtonClick(Activity pActivity, String pTitle, String pMessage, boolean pIsTwoButton, String pPositiveButtonText, String pNegativeButtonText) {

            }
        };
        this.mViewMedicineCategoryList.showConfirmation(R.string.confirmation__delete_successful, listener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        projection = TableMedicineCategory.getColumns().toArray(projection);
        CursorLoader cursorLoader = new CursorLoader(this.mViewMedicineCategoryList.getViewContext(),
                MedicineContentProvider.CONTENT_URI__MEDICINE_CATEGORY, projection, null, null, null);
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
        String[] from = new String[] {TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME, TableMedicineCategory.COLUMN_NAME__ID};
        int[] to = new int[] {R.id.lblMedicineCategoryName, R.id.chkSelect};
        this.mSimpleCursorAdapter = new SimpleCursorAdapter(this.mViewMedicineCategoryList.getViewContext(),
                R.layout.part__medicine_category, null, from, to, 0);
        this.mSimpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View pView, Cursor pCursor, int pColumnIndex) {
                if(pView.getId() == R.id.chkSelect) {
                    CheckBox checkBox = (CheckBox)pView;
                    final int position = pCursor.getPosition();
                    final long rowId = pCursor.getLong(TableFamilyMember.COLUMN_INDEX__ID);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                            PresenterMedicineCategoryList.this.check(position, rowId, pIsChecked);
                        }
                    });

                    if(PresenterMedicineCategoryList.this.mSelectedPositions.contains(position)) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }
                    return true;
                }
                return false;
            }
        });
        this.mViewMedicineCategoryList.setAdapter(this.mSimpleCursorAdapter);
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
