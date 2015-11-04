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
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalAdd;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineIntervalAdd;

public class ScreenMedicineIntervalAdd extends Screen implements IViewMedicineIntervalAdd {
    private EditText mTxtMedicineIntervalName;
    private EditText mTxtMedicineIntervalValue;
    private Button mBtnAdd;
    private Button mBtnBack;

    private IPresenterMedicineIntervalAdd mPresenter;

    @Override
    public boolean onBackPressed() {
        this.mIScreenActionsListener.onBack();
        return true;
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_interval_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineIntervalName = (EditText)pView.findViewById(R.id.txtMedicineIntervalName);
        this.mTxtMedicineIntervalValue = (EditText)pView.findViewById(R.id.txtMedicineIntervalValue);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mTxtMedicineIntervalValue.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pTextView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {
                    ScreenMedicineIntervalAdd.this.buttonAddClick();
                }
                return false;
            }
        });

        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineIntervalAdd.this.buttonAddClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineIntervalAdd.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterMedicineIntervalAdd)PresenterFactory.createPresenter(IPresenterMedicineIntervalAdd.class, this);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_interval_add;
    }

    @Override
    public void clearInput() {
        this.mTxtMedicineIntervalName.setText("");
        this.mTxtMedicineIntervalValue.setText("");
    }

    public void buttonAddClick() {
        String medicineIntervalName = this.mTxtMedicineIntervalName.getText().toString().trim();
        String medicineIntervalValue = this.mTxtMedicineIntervalValue.getText().toString().trim();
        this.mPresenter.insert(medicineIntervalName, medicineIntervalValue);
    }

    public void buttonBackClick() {
        this.mIScreenActionsListener.onBack();
    }
}
