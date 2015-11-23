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

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterFamilyMemberAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewFamilyMemberAdd;

public class ScreenFamilyMemberAdd extends Screen implements IViewFamilyMemberAdd {
    private EditText mTxtFamilyMemberName;
    private Button mBtnAdd;
    private Button mBtnBack;

    private IPresenterFamilyMemberAdd mPresenter;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
        return true;
    }

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPresenter = (IPresenterFamilyMemberAdd)PresenterFactory.createPresenter(IPresenterFamilyMemberAdd.class, this);
    }

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
    protected void mapInformationToScreenItems(View pView) {
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
    public void clearInput() {
        this.mTxtFamilyMemberName.setText("");
    }

    private void buttonAddClick() {
        String familyMemberName = this.mTxtFamilyMemberName.getText().toString().trim();
        this.mPresenter.insert(familyMemberName);
    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }
}
