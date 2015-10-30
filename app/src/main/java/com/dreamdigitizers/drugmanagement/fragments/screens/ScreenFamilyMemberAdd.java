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
import com.dreamdigitizers.drugmanagement.views.IViewFamilyMemberAdd;

public class ScreenFamilyMemberAdd extends Screen implements IViewFamilyMemberAdd {
    private IPresenterFamilyMemberAdd mPresenterFamilyMemberAdd;
    private EditText mTxtFamilyMemberName;
    private Button mBtnAdd;
    private Button mBtnBack;

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__family_member_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtFamilyMemberName = (EditText)pView.findViewById(R.id.txtFamilyMemberName);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mPresenterFamilyMemberAdd = (IPresenterFamilyMemberAdd)PresenterFactory.createPresenter(IPresenterFamilyMemberAdd.class, this);

        this.mTxtFamilyMemberName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pTextView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {
                    ScreenFamilyMemberAdd.this.buttonAddClick();
                }
                return false;
            }
        });

        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenFamilyMemberAdd.this.buttonAddClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenFamilyMemberAdd.this.buttonBackClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_family_member_add;
    }

    @Override
    public boolean onBackPressed() {
        this.mIScreenActionsListener.onBack();
        return true;
    }

    public void buttonAddClick() {
        String familyMemberName = this.mTxtFamilyMemberName.getText().toString().trim();
        this.mPresenterFamilyMemberAdd.insert(familyMemberName);
    }

    public void buttonBackClick() {
        this.mIScreenActionsListener.onBack();
    }
}
