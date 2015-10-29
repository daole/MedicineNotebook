package com.dreamdigitizers.drugmanagement.fragments.screens;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterFamilyMemberAdd;

public class ScreenFamilyMemberAdd extends Screen {
    private IPresenterFamilyMemberAdd mPresenterFamilyMemberAdd;
    private EditText mTxtFamilyMember;
    private Button mBtnAdd;
    private Button mBtnBack;

    @Override
    public void onActivityCreated(Bundle pSavedInstanceState) {
        super.onActivityCreated(pSavedInstanceState);
        this.mPresenterFamilyMemberAdd = (IPresenterFamilyMemberAdd)PresenterFactory.createPresenter(IPresenterFamilyMemberAdd.class, this);
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
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mTxtFamilyMember.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pTextView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {
                    ScreenFamilyMemberAdd.this.buttonAddClick(pTextView);
                }
                return false;
            }
        });

        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenFamilyMemberAdd.this.buttonAddClick(pView);
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenFamilyMemberAdd.this.buttonBackClick(pView);
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        this.mIScreenActionsListener.onBack();
        return true;
    }

    public void buttonAddClick(View pView) {
        String familyMember = this.mTxtFamilyMember.getText().toString();
        this.mPresenterFamilyMemberAdd.insert(familyMember);
    }

    public void buttonBackClick(View pView) {
        this.mIScreenActionsListener.onBack();
    }
}
