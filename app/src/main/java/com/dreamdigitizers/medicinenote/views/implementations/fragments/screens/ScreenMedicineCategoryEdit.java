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
import com.dreamdigitizers.medicinenote.data.models.MedicineCategory;
import com.dreamdigitizers.medicinenote.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineCategoryEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineCategoryEdit;

public class ScreenMedicineCategoryEdit extends Screen implements IViewMedicineCategoryEdit {
    private EditText mTxtMedicineCategoryName;
    private EditText mTxtMedicineCategoryNote;
    private Button mBtnEdit;
    private Button mBtnBack;

    private IPresenterMedicineCategoryEdit mPresenter;

    private long mRowId;
    private MedicineCategory mModel;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
        return true;
    }

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPresenter = (IPresenterMedicineCategoryEdit)PresenterFactory.createPresenter(IPresenterMedicineCategoryEdit.class, this);
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
        View rootView = pInflater.inflate(R.layout.screen__medicine_category_edit, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineCategoryName = (EditText)pView.findViewById(R.id.txtMedicineCategoryName);
        this.mTxtMedicineCategoryNote = (EditText)pView.findViewById(R.id.txtMedicineCategoryNote);
        this.mBtnEdit = (Button)pView.findViewById(R.id.btnEdit);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.bindModelData(this.mModel);

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
    public void bindData(MedicineCategory pModel) {
        this.mModel = pModel;
    }

    private void buttonEditClick() {
        String medicineCategoryName = this.mTxtMedicineCategoryName.getText().toString().trim();
        String medicineCategoryNote = this.mTxtMedicineCategoryNote.getText().toString().trim();
        this.mPresenter.edit(this.mRowId, medicineCategoryName, medicineCategoryNote);
    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }

    private void bindModelData(MedicineCategory pModel) {
        this.mTxtMedicineCategoryName.setText(pModel.getMedicineCategoryName());
        this.mTxtMedicineCategoryNote.setText(pModel.getMedicineCategoryNote());
    }
}
