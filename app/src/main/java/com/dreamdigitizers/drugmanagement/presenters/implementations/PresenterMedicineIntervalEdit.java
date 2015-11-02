package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.models.MedicineInterval;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineIntervalEdit;

import java.util.List;

class PresenterMedicineIntervalEdit implements IPresenterMedicineIntervalEdit {
    private IViewMedicineIntervalEdit mView;

    public PresenterMedicineIntervalEdit(IViewMedicineIntervalEdit pView) {
        this.mView = pView;
    }

    @Override
    public  void select(long pRowId) {
        String[] projection = new String[0];
        projection = TableMedicineInterval.getColumns().toArray(projection);
        Uri uri = MedicineContentProvider.CONTENT_URI__MEDICINE_INTERVAL;
        uri = ContentUris.withAppendedId(uri, pRowId);
        Cursor cursor = this.mView.getViewContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            List<MedicineInterval> list = MedicineInterval.fetchData(cursor);
            if(list.size() > 0) {
                MedicineInterval model = list.get(0);
                this.mView.bindData(model);
            }
            cursor.close();
        }
    }

    @Override
    public void edit(long pRowId, String pMedicineIntervalName, String pMedicineIntervalValue) {
        int result = this.checkInputData(pMedicineIntervalName, pMedicineIntervalValue);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_NAME, pMedicineIntervalName);
        contentValues.put(TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_VALUE, pMedicineIntervalValue);

        Uri uri = MedicineContentProvider.CONTENT_URI__MEDICINE_INTERVAL;
        uri = ContentUris.withAppendedId(uri, pRowId);
        int affectedRows = this.mView.getViewContext().getContentResolver().update(
                uri, contentValues, null, null);
        if(affectedRows == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            this.mView.showError(R.string.error__duplicated_data);
        } else if(affectedRows == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            this.mView.showError(R.string.error__unknown_error);
        } else {
            this.mView.showMessage(R.string.message__edit_successful);
        }
    }

    private int checkInputData(String pMedicineIntervalName, String pMedicineIntervalValue) {
        if(TextUtils.isEmpty(pMedicineIntervalName)) {
            return R.string.error__blank_medicine_interval_name;
        }
        if(TextUtils.isEmpty(pMedicineIntervalValue)) {
            return R.string.error__blank_medicine_interval_value;
        }
        return 0;
    }
}
