package com.dreamdigitizers.drugmanagement.fragments.screens;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dreamdigitizers.drugmanagement.R;

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
        this.mIScreenActionsListener.onBackAction();
        return true;
    }

    public void buttonAddClick(View pView) {
        StringBuilder message = new StringBuilder();
        boolean result = this.checkInputData(message);
        if(!result) {
            return;
        }
    }

    public void buttonCancelClick(View pView) {
        this.mIScreenActionsListener.onBackAction();
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
