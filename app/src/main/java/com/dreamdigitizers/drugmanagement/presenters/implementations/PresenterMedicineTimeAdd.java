package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeAdd;

class PresenterMedicineTimeAdd implements IPresenterMedicineTimeAdd {
    private IViewMedicineTimeAdd mViewMedicineTimeAdd;

    public PresenterMedicineTimeAdd(IViewMedicineTimeAdd pViewMedicineTimeAdd) {
        this.mViewMedicineTimeAdd = pViewMedicineTimeAdd;
    }

    @Override
    public void insert(String pMedicineTimeName, String pMedicineTimeValue) {
        int result = this.checkInputData(pMedicineTimeName, pMedicineTimeValue);
        if(result != 0) {
            this.mViewMedicineTimeAdd.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME, pMedicineTimeName);
        contentValues.put(TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_VALUE, pMedicineTimeValue);

        Uri uri = this.mViewMedicineTimeAdd.getViewContext().getContentResolver().insert(
                MedicineContentProvider.CONTENT_URI__MEDICINE_TIME, contentValues);
        long newId = Long.parseLong(uri.getLastPathSegment());
        if(newId == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            this.mViewMedicineTimeAdd.showError(R.string.error__duplicated_data);
        } else if(newId == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            this.mViewMedicineTimeAdd.showError(R.string.error__unknown_error);
        } else {
            this.mViewMedicineTimeAdd.clearInput();
            this.mViewMedicineTimeAdd.showMessage(R.string.message__insert_successful);
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
