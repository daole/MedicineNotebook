package com.dreamdigitizers.drugmanagement.fragments.screens;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;

public class ScreenFamilyMemberAdd extends Screen {
    private EditText mTxtFamilyMember;
    private Button mBtnAdd;
    private Button mBtnCancel;

    @Override
    public void onActivityCreated(Bundle pSavedInstanceState) {
        super.onActivityCreated(pSavedInstanceState);
        this.setHasOptionsMenu(false);
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__family_member_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtFamilyMember = (EditText)pView.findViewById(R.id.txtFamilyMember);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnCancel = (Button)pView.findViewById(R.id.btnCancel);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenFamilyMemberAdd.this.buttonAddClick(pView);
            }
        });

        this.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenFamilyMemberAdd.this.buttonCancelClick(pView);
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        this.mIScreenActionsListener.onBack();
        return true;
    }

    public void buttonAddClick(View pView) {
        StringBuilder message = new StringBuilder();
        Uri uri = this.insert(message);
        if(uri == null) {
            String title = this.getString(R.string.title__error_dialog);
            String buttonText = this.getString(R.string.btn__ok);
            DialogUtils.displayErrorDialog(this.getActivity(), title, message.toString(), buttonText);
        } else {
            Toast.makeText(this.getActivity(), R.string.message__insert_successful, Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonCancelClick(View pView) {
        this.mIScreenActionsListener.onBack();
    }

    private Uri insert(StringBuilder pMessage) {
        boolean result = this.checkInputData(pMessage);
        if(!result) {
            return null;
        }

        String familyMember = this.mTxtFamilyMember.getText().toString().trim();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME, familyMember);
        Uri uri = this.getContext().getContentResolver().insert(MedicineContentProvider.CONTENT_URI__FAMILY_MEMBER, contentValues);
        long newId = Long.parseLong(uri.getLastPathSegment());
        if(newId == DatabaseHelper.DB_ERROR_CODE__CONSTRAINT) {
            pMessage.append(this.getString(R.string.error__duplicated_data));
            return null;
        } else if(newId == DatabaseHelper.DB_ERROR_CODE__OTHER) {
            pMessage.append(this.getString(R.string.error__unknown_error));
            return null;
        }

        return uri;
    }

    private boolean checkInputData(StringBuilder pMessage) {
        String familyMember = this.mTxtFamilyMember.getText().toString().trim();
        if(TextUtils.isEmpty(familyMember)) {
            pMessage.append(this.getString(R.string.error__blank_family_member));
            return false;
        }
        return true;
    }
}
