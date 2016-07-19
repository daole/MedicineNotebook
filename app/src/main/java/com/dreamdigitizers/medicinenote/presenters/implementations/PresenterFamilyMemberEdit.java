package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.data.ContentProviderMedicine;
import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.medicinenote.data.models.FamilyMember;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewFamilyMemberEdit;

import java.util.List;

class PresenterFamilyMemberEdit implements IPresenterFamilyMemberEdit {
    private IViewFamilyMemberEdit mView;

    public PresenterFamilyMemberEdit(IViewFamilyMemberEdit pView) {
        this.mView = pView;
    }

    @Override
    public  void select(long pRowId) {
        String[] projection = new String[0];
        projection = TableFamilyMember.getColumns().toArray(projection);
        Uri uri = ContentProviderMedicine.CONTENT_URI__FAMILY_MEMBER;
        uri = ContentUris.withAppendedId(uri, pRowId);
        Cursor cursor = this.mView.getViewContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            List<FamilyMember> list = FamilyMember.fetchData(cursor);
            if(list.size() > 0) {
                FamilyMember model = list.get(0);
                this.mView.bindData(model);
            }
            cursor.close();
        }
    }

    @Override
    public void edit(long pRowId, String pFamilyMemberName) {
        int result = this.checkInputData(pFamilyMemberName);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME, pFamilyMemberName);

        Uri uri = ContentProviderMedicine.CONTENT_URI__FAMILY_MEMBER;
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

    private int checkInputData(String pFamilyMemberName) {
        if(TextUtils.isEmpty(pFamilyMemberName)) {
            return R.string.error__blank_family_member_name;
        }
        return 0;
    }
}
