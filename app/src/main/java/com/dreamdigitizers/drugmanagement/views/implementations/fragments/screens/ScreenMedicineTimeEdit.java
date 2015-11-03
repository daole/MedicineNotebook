package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.MedicineTime;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeEdit;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeEdit;
import com.dreamdigitizers.drugmanagement.views.implementations.adapters.TimeValueArrayAdapter;

import java.util.List;

public class ScreenMedicineTimeEdit extends Screen implements IViewMedicineTimeEdit {
    private EditText mTxtMedicineTimeName;
    private ListView mListView;
    private Button mBtnAddTimeValue;
    private Button mBtnEdit;
    private Button mBtnBack;
    private TimeValueArrayAdapter mAdapter;

    private IPresenterMedicineTimeEdit mPresenter;

    private long mRowId;

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_time_edit, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineTimeName = (EditText)pView.findViewById(R.id.txtMedicineTimeName);
        this.mListView = (ListView)pView.findViewById(R.id.lstMedicineTimeValues);
        this.mBtnAddTimeValue = (Button)pView.findViewById(R.id.btnAddTimeValue);
        this.mBtnEdit = (Button)pView.findViewById(R.id.btnEdit);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);

        this.mRowId = this.getArguments().getLong(Screen.BUNDLE_KEY__ROW_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(Screen.BUNDLE_KEY__ROW_ID, this.mRowId);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(Screen.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mAdapter = new TimeValueArrayAdapter(this.getContext(), R.layout.part__medicine_time_value);
        this.mListView.setAdapter(this.mAdapter);

        this.mBtnAddTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineTimeEdit.this.buttonAddTimeValueClick();
            }
        });

        this.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineTimeEdit.this.buttonEditClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineTimeEdit.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterMedicineTimeEdit)PresenterFactory.createPresenter(IPresenterMedicineTimeEdit.class, this);
        this.mPresenter.select(this.mRowId);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_time_edit;
    }

    @Override
    public boolean onBackPressed() {
        this.mIScreenActionsListener.onBack();
        return true;
    }

    @Override
    public void bindData(MedicineTime pModel) {
        this.mTxtMedicineTimeName.setText(pModel.getMedicineTimeName());
        this.mAdapter.addItems(pModel.getMedicineTimeValues());
    }

    public void buttonAddTimeValueClick() {
        DialogUtils.displayTimePickerDialog(this.getActivity(), this.getString(R.string.btn__cancel), true, new DialogUtils.IOnTimePickerDialogEventListener() {
            @Override
            public void onTimeSet(int pHourOfDay, int pMinute, Activity pActivity, String pCancelButtonText, boolean pIs24HourView) {
                String timeValue = String.format(Constants.FORMAT__TIME_VALUE, pHourOfDay)
                        + Constants.DELIMITER__TIME
                        + String.format(Constants.FORMAT__TIME_VALUE, pMinute);
                ScreenMedicineTimeEdit.this.mAdapter.addItem(timeValue);
            }

            @Override
            public void onCancel(Activity pActivity, String pCancelButtonText, boolean pIs24HourView) {

            }
        });
    }

    public void buttonEditClick() {
        String medicineTimeName = this.mTxtMedicineTimeName.getText().toString().trim();
        List<String> medicineTimeValues = this.mAdapter.getData();
        this.mPresenter.edit(this.mRowId, medicineTimeName, medicineTimeValues);
    }

    public void buttonBackClick() {
        this.mIScreenActionsListener.onBack();
    }
}
