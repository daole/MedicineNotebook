package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
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
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.models.Medicine;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineEdit;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineEdit;

import java.util.List;

class PresenterMedicineEdit implements IPresenterMedicineEdit {
    private IViewMedicineEdit mView;
    private SimpleCursorAdapter mAdapter;
    private boolean mIsMedicineCategoryDataLoaded;

    public PresenterMedicineEdit(IViewMedicineEdit pView) {
        this.mView = pView;
        this.mView.getViewLoaderManager().initLoader(0, null, this);
        this.createAdapter();
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
    public  void select(long pRowId) {
        String[] projection = new String[0];
        projection = TableMedicine.getColumns().toArray(projection);
        Uri uri = ContentProviderMedicine.CONTENT_URI__MEDICINE;
        uri = ContentUris.withAppendedId(uri, pRowId);
        Cursor cursor = this.mView.getViewContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            List<Medicine> list = Medicine.fetchData(cursor);
            if(list.size() > 0) {
                Medicine model = list.get(0);
                this.mView.bindData(model);
            }
            cursor.close();
        }
    }

    @Override
    public void edit(long pRowId, String pMedicineName, long pMedicineCategoryId, String pMedicineImagePath, String pMedicineNote) {
        int result = this.checkInputData(pMedicineName);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableMedicine.COLUMN_NAME__MEDICINE_NAME, pMedicineName);
        contentValues.put(TableMedicine.COLUMN_NAME__MEDICINE_CATEGORY_ID, pMedicineCategoryId);
        contentValues.put(TableMedicine.COLUMN_NAME__MEDICINE_IMAGE_PATH, pMedicineImagePath);
        contentValues.put(TableMedicine.COLUMN_NAME__MEDICINE_NOTE, pMedicineNote);

        Uri uri = ContentProviderMedicine.CONTENT_URI__MEDICINE;
        uri = ContentUris.withAppendedId(uri, pRowId);
        int affectedRows = this.mView.getViewContext().getContentResolver().update(
                uri, contentValues, null, null);
        if(affectedRows == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            this.mView.showError(R.string.error__duplicated_data);
        } else if(affectedRows == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            this.mView.showError(R.string.error__unknown_error);
        } else {
            this.mView.showMessage(R.string.message__edit_successful);
            this.mView.onDataEdited();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        projection = TableMedicineCategory.getColumns().toArray(projection);
        CursorLoader cursorLoader = new CursorLoader(this.mView.getViewContext(),
                ContentProviderMedicine.CONTENT_URI__MEDICINE_CATEGORY, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> pLoader, Cursor pData) {
        String[] projection = new String[0];
        projection = TableMedicineCategory.getColumns().toArray(projection);
        MatrixCursor extras = new MatrixCursor(projection);
        extras.addRow(new Object[] {Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select), ""});
        Cursor cursor = new MergeCursor(new Cursor[]{extras, pData});
        this.mAdapter.swapCursor(cursor);

        if(!this.mIsMedicineCategoryDataLoaded) {
            this.mIsMedicineCategoryDataLoaded = true;
            this.mView.onMedicineCategoryDataLoaded();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> pLoader) {
        this.mAdapter.swapCursor(null);
    }

    private void createAdapter() {
        String[] from = new String[] {TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setAdapter(this.mAdapter);
    }

    private int checkInputData(String pMedicineName) {
        if(TextUtils.isEmpty(pMedicineName)) {
            return R.string.error__blank_medicine_name;
        }
        return 0;
    }
}
