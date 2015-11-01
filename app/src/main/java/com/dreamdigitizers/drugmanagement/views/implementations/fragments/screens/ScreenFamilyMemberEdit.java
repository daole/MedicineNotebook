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
    private IPresenterFamilyMemberEdit mPresenterFamilyMemberEdit;
    private EditText mTxtFamilyMemberName;
    private Button mBtnEdit;
    private Button mBtnBack;

    private long mRowId;

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

        this.mRowId = this.getArguments().getLong(ScreenFamilyMemberEdit.BUNDLE_KEY__ROW_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(ScreenFamilyMemberEdit.BUNDLE_KEY__ROW_ID, this.mRowId);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(ScreenFamilyMemberEdit.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected void mapInformationToScreenItems() {
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

        this.mPresenterFamilyMemberEdit = (IPresenterFamilyMemberEdit)PresenterFactory.createPresenter(IPresenterFamilyMemberEdit.class, this);
        this.mPresenterFamilyMemberEdit.select(this.mRowId);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_family_member_edit;
    }

    @Override
    public boolean onBackPressed() {
        this.mIScreenActionsListener.onBack();
        return true;
    }

    @Override
    public void bindData(FamilyMember pModel) {
        this.mTxtFamilyMemberName.setText(pModel.getFamilyMemberName());
    }

    public void buttonEditClick() {
        String familyMemberName = this.mTxtFamilyMemberName.getText().toString().trim();
        this.mPresenterFamilyMemberEdit.edit(this.mRowId, familyMemberName);
    }

    public void buttonBackClick() {
        this.mIScreenActionsListener.onBack();
    }
}
