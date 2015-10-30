package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.models.MedicineCategory;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterMedicineCategoryEdit;
import com.dreamdigitizers.drugmanagement.views.IViewMedicineCategoryEdit;

import java.util.List;

class PresenterMedicineCategoryEdit implements IPresenterMedicineCategoryEdit {
    private IViewMedicineCategoryEdit mViewMedicineCategoryEdit;

    public PresenterMedicineCategoryEdit(IViewMedicineCategoryEdit pViewMedicineCategoryEdit) {
        this.mViewMedicineCategoryEdit = pViewMedicineCategoryEdit;
    }

    @Override
    public  void select(long pRowId) {
        String[] projection = new String[0];
        projection = TableMedicineCategory.getColumns().toArray(projection);
        Uri uri = MedicineContentProvider.CONTENT_URI__MEDICINE_CATEGORY;
        uri = ContentUris.withAppendedId(uri, pRowId);
        Cursor cursor = this.mViewMedicineCategoryEdit.getViewContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            List<MedicineCategory> list = MedicineCategory.fetchData(cursor);
            if(list.size() > 0) {
                MedicineCategory model = list.get(0);
                this.mViewMedicineCategoryEdit.bindData(model);
            }
            cursor.close();
        }
    }

    @Override
    public void edit(long pRowId, String pMedicineCategoryName, String pMedicineCategoryNote) {
        int result = this.checkInputData(pMedicineCategoryName);
        if(result != 0) {
            this.mViewMedicineCategoryEdit.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NAME, pMedicineCategoryName);
        contentValues.put(TableMedicineCategory.COLUMN_NAME__MEDICINE_CATEGORY_NOTE, pMedicineCategoryNote);

        Uri uri = MedicineContentProvider.CONTENT_URI__MEDICINE_CATEGORY;
        uri = ContentUris.withAppendedId(uri, pRowId);
        int affectedRows = this.mViewMedicineCategoryEdit.getViewContext().getContentResolver().update(
                uri, contentValues, null, null);
        if(affectedRows == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            this.mViewMedicineCategoryEdit.showError(R.string.error__duplicated_data);
        } else if(affectedRows == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            this.mViewMedicineCategoryEdit.showError(R.string.error__unknown_error);
        } else {
            this.mViewMedicineCategoryEdit.showMessage(R.string.message__insert_successful);
        }
    }

    private int checkInputData(String pMedicineCategoryName) {
        if(TextUtils.isEmpty(pMedicineCategoryName)) {
            return R.string.error__blank_medicine_category;
        }
        return 0;
    }
}
