package com.dreamdigitizers.medicinenote.presenters.implementations;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.data.ContentProviderMedicine;
import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterFamilyMemberAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewFamilyMemberAdd;

class PresenterFamilyMemberAdd implements IPresenterFamilyMemberAdd {
    private IViewFamilyMemberAdd mView;

    public PresenterFamilyMemberAdd(IViewFamilyMemberAdd pView) {
        this.mView = pView;
    }

    @Override
    public void insert(String pFamilyMemberName) {
        int result = this.checkInputData(pFamilyMemberName);
        if(result != 0) {
            this.mView.showError(result);
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME, pFamilyMemberName);

        Uri uri = this.mView.getViewContext().getContentResolver().insert(
                ContentProviderMedicine.CONTENT_URI__FAMILY_MEMBER, contentValues);
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

    private int checkInputData(String pFamilyMemberName) {
        if(TextUtils.isEmpty(pFamilyMemberName)) {
            return R.string.error__blank_family_member_name;
        }
        return 0;
    }
}
