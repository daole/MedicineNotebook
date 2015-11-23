package com.dreamdigitizers.medicinenote.views.implementations.fragments.screens;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dreamdigitizers.medicinenote.Constants;
import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.data.models.FamilyMember;
import com.dreamdigitizers.medicinenote.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewFamilyMemberEdit;

public class ScreenFamilyMemberEdit extends Screen implements IViewFamilyMemberEdit {
    private EditText mTxtFamilyMemberName;
    private Button mBtnEdit;
    private Button mBtnBack;

    private IPresenterFamilyMemberEdit mPresenter;

    private long mRowId;
    private FamilyMember mModel;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
        return true;
    }

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPresenter = (IPresenterFamilyMemberEdit)PresenterFactory.createPresenter(IPresenterFamilyMemberEdit.class, this);
        this.mPresenter.select(this.mRowId);
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(Constants.BUNDLE_KEY__ROW_ID, this.mRowId);
    }

    @Override
    protected void retrieveArguments(Bundle pArguments) {
        this.mRowId = pArguments.getLong(Constants.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(Constants.BUNDLE_KEY__ROW_ID);
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
        this.bindModelData(this.mModel);

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
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_family_member_edit;
    }

    @Override
    public void bindData(FamilyMember pModel) {
        this.mModel = pModel;
    }

    private void buttonEditClick() {
        String familyMemberName = this.mTxtFamilyMemberName.getText().toString().trim();
        this.mPresenter.edit(this.mRowId, familyMemberName);
    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }

    private void bindModelData(FamilyMember pModel) {
        this.mTxtFamilyMemberName.setText(pModel.getFamilyMemberName());
    }
}
