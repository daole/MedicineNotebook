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
import com.dreamdigitizers.drugmanagement.data.models.MedicineInterval;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalEdit;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineIntervalEdit;

public class ScreenMedicineIntervalEdit extends Screen implements IViewMedicineIntervalEdit {
    private EditText mTxtMedicineIntervalName;
    private EditText mTxtMedicineIntervalValue;
    private Button mBtnEdit;
    private Button mBtnBack;

    private IPresenterMedicineIntervalEdit mPresenter;

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
        View rootView = pInflater.inflate(R.layout.screen__medicine_interval_edit, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineIntervalName = (EditText)pView.findViewById(R.id.txtMedicineIntervalName);
        this.mTxtMedicineIntervalValue = (EditText)pView.findViewById(R.id.txtMedicineIntervalValue);
        this.mBtnEdit = (Button)pView.findViewById(R.id.btnEdit);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mTxtMedicineIntervalValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pTextView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {
                    ScreenMedicineIntervalEdit.this.buttonEditClick();
                }
                return false;
            }
        });

        this.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineIntervalEdit.this.buttonEditClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineIntervalEdit.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterMedicineIntervalEdit)PresenterFactory.createPresenter(IPresenterMedicineIntervalEdit.class, this);
        this.mPresenter.select(this.mRowId);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_category_edit;
    }

    @Override
    public void bindData(MedicineInterval pModel) {
        this.mTxtMedicineIntervalName.setText(pModel.getMedicineIntervalName());
        this.mTxtMedicineIntervalValue.setText(Integer.toString(pModel.getMedicineIntervalValue()));
    }

    public void buttonEditClick() {
        String medicineCategoryName = this.mTxtMedicineIntervalName.getText().toString().trim();
        String medicineCategoryNote = this.mTxtMedicineIntervalValue.getText().toString().trim();
        this.mPresenter.edit(this.mRowId, medicineCategoryName, medicineCategoryNote);
    }

    public void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }
}
