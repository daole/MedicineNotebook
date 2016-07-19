package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.data.ContentProviderMedicine;
import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineIntervalAdd;
import com.dreamdigitizers.medicinenote.utils.StringUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineIntervalAdd;

class PresenterMedicineIntervalAdd implements IPresenterMedicineIntervalAdd {
    private IViewMedicineIntervalAdd mView;

    public PresenterMedicineIntervalAdd(IViewMedicineIntervalAdd pView) {
        this.mView = pView;
    }

    @Override
    public void insert(String pMedicineIntervalName, String pMedicineIntervalValue) {
        int result = this.checkInputData(pMedicineIntervalName, pMedicineIntervalValue);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_NAME, pMedicineIntervalName);
        contentValues.put(TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_VALUE, pMedicineIntervalValue);

        Uri uri = this.mView.getViewContext().getContentResolver().insert(
                ContentProviderMedicine.CONTENT_URI__MEDICINE_INTERVAL, contentValues);
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

    private int checkInputData(String pMedicineIntervalName, String pMedicineIntervalValue) {
        if(TextUtils.isEmpty(pMedicineIntervalName)) {
            return R.string.error__blank_medicine_interval_name;
        }
        if(TextUtils.isEmpty(pMedicineIntervalValue)) {
            return R.string.error__blank_medicine_interval_value;
        }
        if(!StringUtils.isPositiveInteger(pMedicineIntervalValue)) {
            return R.string.error__invalid_medicine_interval_value;
        }
        return 0;
    }
}
