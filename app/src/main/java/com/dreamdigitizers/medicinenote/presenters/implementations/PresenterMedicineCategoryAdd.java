package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.data.ContentProviderMedicine;
import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineCategoryAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineCategoryAdd;

class PresenterMedicineCategoryAdd implements IPresenterMedicineCategoryAdd {
    private IViewMedicineCategoryAdd mView;

    public PresenterMedicineCategoryAdd(IViewMedicineCategoryAdd pView) {
        this.mView = pView;
    }

    @Override
    public void insert(String pMedicineCategoryName, String pMedicineCategoryNote) {
        int result = this.checkInputData(pMedicineCategoryName);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME, pMedicineCategoryName);
        if(!TextUtils.isEmpty(pMedicineCategoryNote)) {
            contentValues.put(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NOTE, pMedicineCategoryNote);
        }

        Uri uri = this.mView.getViewContext().getContentResolver().insert(
                ContentProviderMedicine.CONTENT_URI__MEDICINE_CATEGORY, contentValues);
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

    private int checkInputData(String pMedicineCategoryName) {
        if(TextUtils.isEmpty(pMedicineCategoryName)) {
            return R.string.error__blank_medicine_category_name;
        }
        return 0;
    }
}
