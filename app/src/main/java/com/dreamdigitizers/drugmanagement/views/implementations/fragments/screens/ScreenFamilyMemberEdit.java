package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

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
import com.dreamdigitizers.drugmanagement.data.models.FamilyMember;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewFamilyMemberEdit;

public class ScreenFamilyMemberEdit extends Screen implements IViewFamilyMemberEdit {
    private EditText mTxtFamilyMemberName;
    private Button mBtnEdit;
    private Button mBtnBack;

    private IPresenterFamilyMemberEdit mPresenter;

    private long mRowId;

    @Override
    public boolean onBackPressed() {
        this.mScreenActionsListener.onBack();
        return true;
    }


    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(Screen.BUNDLE_KEY__ROW_ID, this.mRowId);
    }

    @Override
    protected void retrieveArguments(Bundle pArguments) {
        this.mRowId = pArguments.getLong(Screen.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(Screen.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__family_member_edit, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtFamilyMemberName = (EditText)pView.findViewById(R.id.txtFamilyMemberName);
        this.mBtnEdit = (Button)pView.findViewById(R.id.btnEdit);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mTxtFamilyMemberName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pTextView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {
                    ScreenFamilyMemberEdit.this.buttonEditClick();
                }
                return false;
            }
        });

        this.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenFamilyMemberEdit.this.buttonEditClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenFamilyMemberEdit.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterFamilyMemberEdit)PresenterFactory.createPresenter(IPresenterFamilyMemberEdit.class, this);
        this.mPresenter.select(this.mRowId);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_family_member_edit;
    }

    @Override
    public void bindData(FamilyMember pModel) {
        this.mTxtFamilyMemberName.setText(pModel.getFamilyMemberName());
    }

    public void buttonEditClick() {
        String familyMemberName = this.mTxtFamilyMemberName.getText().toString().trim();
        this.mPresenter.edit(this.mRowId, familyMemberName);
    }

    public void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }
}
