package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.ContentProviderMedicine;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TablePrescription;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterPrescriptionAdd;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewPrescriptionAdd;

class PresenterPrescriptionAdd implements IPresenterPrescriptionAdd {
    private IViewPrescriptionAdd mView;
    private SimpleCursorAdapter mAdapter;

    public PresenterPrescriptionAdd(IViewPrescriptionAdd pView) {
        this.mView = pView;
        this.mView.getViewLoaderManager().initLoader(0, null, this);
        this.createAdapter();
    }

    @Override
    public Bitmap loadImage(String pFilePath) {
        return FileUtils.decodeBitmapFromFile(pFilePath);
    }

    @Override
    public Bitmap loadImage(String pFilePath, int pWidth, int pHeight) {
        return FileUtils.decodeSampledBitmapFromFile(pFilePath, pWidth, pHeight);
    }

    @Override
    public void deleteImage(String pFilePath) {
        if(!TextUtils.isEmpty(pFilePath)) {
            FileUtils.deleteFile(pFilePath);
        }
    }

    @Override
    public void insert(String pPrescriptionName, String pPrescriptionDate, long pFamilyMemberId, String pImagePath, String pPrescriptionNote) {
        int result = this.checkInputData(pPrescriptionName);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TablePrescription.COLUMN_NAME__PRESCRIPTION_NAME, pPrescriptionName);
        if(!TextUtils.isEmpty(pPrescriptionDate)) {
            contentValues.put(TablePrescription.COLUMN_NAME__PRESCRIPTION_DATE, pPrescriptionDate);
        }
        if(pFamilyMemberId > Constants.ROW_ID__NO_SELECT) {
            contentValues.put(TablePrescription.COLUMN_NAME__FAMILY_MEMBER_ID, pFamilyMemberId);
        }
        if(!TextUtils.isEmpty(pImagePath)) {
            contentValues.put(TablePrescription.COLUMN_NAME__IMAGE_PATH, pImagePath);
        }
        if(!TextUtils.isEmpty(pPrescriptionNote)) {
            contentValues.put(TablePrescription.COLUMN_NAME__PRESCRIPTION_NOTE, pPrescriptionNote);
        }

        Uri uri = this.mView.getViewContext().getContentResolver().insert(
                ContentProviderMedicine.CONTENT_URI__PRESCRIPTION, contentValues);
        long newId = Long.parseLong(uri.getLastPathSegment());
        if(newId == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            this.mView.showError(R.string.error__duplicated_data);
        } else if(newId == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            this.mView.showError(R.string.error__unknown_error);
        } else {
            this.mView.clearInput();
            this.mView.showMessage(R.string.message__insert_successful);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        projection = TableFamilyMember.getColumns().toArray(projection);
        CursorLoader cursorLoader = new CursorLoader(this.mView.getViewContext(),
                ContentProviderMedicine.CONTENT_URI__FAMILY_MEMBER, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> pLoader, Cursor pData) {
        String[] projection = new String[0];
        projection = TableFamilyMember.getColumns().toArray(projection);
        MatrixCursor extras = new MatrixCursor(projection);
        extras.addRow(new Object[] {Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select)});
        Cursor cursor = new MergeCursor(new Cursor[]{extras, pData});
        this.mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> pLoader) {
        this.mAdapter.swapCursor(null);
    }

    private void createAdapter() {
        String[] from = new String[] {TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setAdapter(this.mAdapter);
    }

    private int checkInputData(String pPrescriptionName) {
        if(TextUtils.isEmpty(pPrescriptionName)) {
            return R.string.error__blank_prescription_name;
        }
        return 0;
    }
}
