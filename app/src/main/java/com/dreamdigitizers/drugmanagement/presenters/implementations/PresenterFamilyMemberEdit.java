package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.drugmanagement.views.IViewFamilyMemberEdit;

class PresenterFamilyMemberEdit implements IPresenterFamilyMemberEdit {
    private IViewFamilyMemberEdit mViewFamilyMemberEdit;

    public PresenterFamilyMemberEdit(IViewFamilyMemberEdit pViewFamilyMemberEdit) {
        this.mViewFamilyMemberEdit = pViewFamilyMemberEdit;
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
            return R.string.error__blank_family_member;
        }
        return 0;
    }
}
