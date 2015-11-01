package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.models.FamilyMember;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewFamilyMemberEdit;

import java.util.List;

class PresenterFamilyMemberEdit implements IPresenterFamilyMemberEdit {
    private IViewFamilyMemberEdit mViewFamilyMemberEdit;

    public PresenterFamilyMemberEdit(IViewFamilyMemberEdit pViewFamilyMemberEdit) {
        this.mViewFamilyMemberEdit = pViewFamilyMemberEdit;
    }

    @Override
    public  void select(long pRowId) {
        String[] projection = new String[0];
        projection = TableFamilyMember.getColumns().toArray(projection);
        Uri uri = MedicineContentProvider.CONTENT_URI__FAMILY_MEMBER;
        uri = ContentUris.withAppendedId(uri, pRowId);
        Cursor cursor = this.mViewFamilyMemberEdit.getViewContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            List<FamilyMember> list = FamilyMember.fetchData(cursor);
            if(list.size() > 0) {
                FamilyMember model = list.get(0);
                this.mViewFamilyMemberEdit.bindData(model);
            }
            cursor.close();
        }
    }

    @Override
    public void edit(long pRowId, String pFamilyMemberName) {
        int result = this.checkInputData(pFamilyMemberName);
        if(result != 0) {
            this.mViewFamilyMemberEdit.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME, pFamilyMemberName);

        Uri uri = MedicineContentProvider.CONTENT_URI__FAMILY_MEMBER;
        uri = ContentUris.withAppendedId(uri, pRowId);
        int affectedRows = this.mViewFamilyMemberEdit.getViewContext().getContentResolver().update(
                uri, contentValues, null, null);
        if(affectedRows == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            this.mViewFamilyMemberEdit.showError(R.string.error__duplicated_data);
        } else if(affectedRows == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            this.mViewFamilyMemberEdit.showError(R.string.error__unknown_error);
        } else {
            this.mViewFamilyMemberEdit.showMessage(R.string.message__edit_successful);
        }
    }

    private int checkInputData(String pFamilyMemberName) {
        if(TextUtils.isEmpty(pFamilyMemberName)) {
            return R.string.error__blank_family_member_name;
        }
        return 0;
    }
}
