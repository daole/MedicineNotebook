package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.data.models.MedicineTime;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeEdit;

import java.util.List;

class PresenterMedicineTimeEdit implements IPresenterMedicineTimeEdit {
    private IViewMedicineTimeEdit mView;

    public PresenterMedicineTimeEdit(IViewMedicineTimeEdit pViewMedicineTimeEdit) {
        this.mView = pViewMedicineTimeEdit;
    }

    @Override
    public  void select(long pRowId) {
        String[] projection = new String[0];
        projection = TableMedicineTime.getColumns().toArray(projection);
        Uri uri = MedicineContentProvider.CONTENT_URI__MEDICINE_TIME;
        uri = ContentUris.withAppendedId(uri, pRowId);
        Cursor cursor = this.mView.getViewContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            List<MedicineTime> list = MedicineTime.fetchData(cursor);
            if(list.size() > 0) {
                MedicineTime model = list.get(0);
                this.mView.bindData(model);
            }
            cursor.close();
        }
    }

    @Override
    public void edit(long pRowId, String pMedicineTimeName, String pMedicineTimeValue) {
        int result = this.checkInputData(pMedicineTimeName, pMedicineTimeValue);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME, pMedicineTimeName);
        contentValues.put(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE, pMedicineTimeValue);

        Uri uri = MedicineContentProvider.CONTENT_URI__MEDICINE_TIME;
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

    private int checkInputData(String pMedicineTimeName, String pMedicineTimeValue) {
        if(TextUtils.isEmpty(pMedicineTimeName)) {
            return R.string.error__blank_medicine_time_name;
        }
        if(TextUtils.isEmpty(pMedicineTimeValue)) {
            return R.string.error__blank_medicine_time_value;
        }
        return 0;
    }
}
