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
import com.dreamdigitizers.drugmanagement.data.models.MedicineCategory;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterMedicineCategoryEdit;
import com.dreamdigitizers.drugmanagement.views.IViewMedicineCategoryEdit;

public class ScreenMedicineCategoryEdit extends Screen implements IViewMedicineCategoryEdit {
    private IPresenterMedicineCategoryEdit mPresenterMedicineCategoryEdit;
    private EditText mTxtMedicineCategoryName;
    private EditText mTxtMedicineCategoryNote;
    private Button mBtnEdit;
    private Button mBtnBack;

    private long mRowId;

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_category_edit, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineCategoryName = (EditText)pView.findViewById(R.id.txtMedicineCategoryName);
        this.mTxtMedicineCategoryNote = (EditText)pView.findViewById(R.id.txtMedicineCategoryNote);
        this.mBtnEdit = (Button)pView.findViewById(R.id.btnEdit);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);

        this.mRowId = this.getArguments().getLong(ScreenMedicineCategoryEdit.BUNDLE_KEY__ROW_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(ScreenMedicineCategoryEdit.BUNDLE_KEY__ROW_ID, this.mRowId);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(ScreenMedicineCategoryEdit.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mPresenterMedicineCategoryEdit = (IPresenterMedicineCategoryEdit)PresenterFactory.createPresenter(IPresenterMedicineCategoryEdit.class, this);
        this.mPresenterMedicineCategoryEdit.select(this.mRowId);

        this.mTxtMedicineCategoryNote.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pTextView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {
                    ScreenMedicineCategoryEdit.this.buttonEditClick();
                }
                return false;
            }
        });

        this.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineCategoryEdit.this.buttonEditClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineCategoryEdit.this.buttonBackClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_category_edit;
    }

    @Override
    public boolean onBackPressed() {
        this.mIScreenActionsListener.onBack();
        return true;
    }

    @Override
    public void bindData(MedicineCategory pModel) {
        this.mTxtMedicineCategoryName.setText(pModel.getMedicineCategoryName());
        this.mTxtMedicineCategoryNote.setText(pModel.getMedicineCategoryNote());
    }

    public void buttonEditClick() {
        String medicineCategoryName = this.mTxtMedicineCategoryName.getText().toString().trim();
        String medicineCategoryNote = this.mTxtMedicineCategoryNote.getText().toString().trim();
        this.mPresenterMedicineCategoryEdit.edit(this.mRowId, medicineCategoryName, medicineCategoryNote);
    }

    public void buttonBackClick() {
        this.mIScreenActionsListener.onBack();
    }
}
