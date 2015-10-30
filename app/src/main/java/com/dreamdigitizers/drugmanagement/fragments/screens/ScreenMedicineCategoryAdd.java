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
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterMedicineCategoryAdd;
import com.dreamdigitizers.drugmanagement.views.IViewMedicineCategoryAdd;

public class ScreenMedicineCategoryAdd extends Screen implements IViewMedicineCategoryAdd {
    private IPresenterMedicineCategoryAdd mPresenterMedicineCategoryAdd;
    private EditText mTxtMedicineCategoryName;
    private EditText mTxtMedicineCategoryNote;
    private Button mBtnAdd;
    private Button mBtnBack;

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_category_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineCategoryName = (EditText)pView.findViewById(R.id.txtMedicineCategoryName);
        this.mTxtMedicineCategoryNote = (EditText)pView.findViewById(R.id.txtMedicineCategoryNote);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mPresenterMedicineCategoryAdd = (IPresenterMedicineCategoryAdd)PresenterFactory.createPresenter(IPresenterMedicineCategoryAdd.class, this);

        this.mTxtMedicineCategoryNote.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pTextView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {
                    ScreenMedicineCategoryAdd.this.buttonAddClick();
                }
                return false;
            }
        });

        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineCategoryAdd.this.buttonAddClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineCategoryAdd.this.buttonBackClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_category_add;
    }

    @Override
    public boolean onBackPressed() {
        this.mIScreenActionsListener.onBack();
        return true;
    }

    public void buttonAddClick() {
        String medicineCategoryName = this.mTxtMedicineCategoryName.getText().toString().trim();
        String medicineCategoryNote = this.mTxtMedicineCategoryNote.getText().toString().trim();
        this.mPresenterMedicineCategoryAdd.insert(medicineCategoryName, medicineCategoryNote);
    }

    public void buttonBackClick() {
        this.mIScreenActionsListener.onBack();
    }
}
