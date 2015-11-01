package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.views.implementations.adapters.TimeValueArrayAdapter;

import java.util.List;

public class ScreenMedicineTimeAdd extends Screen implements IViewMedicineTimeAdd {
    private IPresenterMedicineTimeAdd mPresenterMedicineTimeAdd;
    private EditText mTxtMedicineTimeName;
    private ListView mListView;
    private Button mBtnAddTimeValue;
    private Button mBtnAdd;
    private Button mBtnBack;
    private TimeValueArrayAdapter mTimeValueArrayAdapter;

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_time_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineTimeName = (EditText)pView.findViewById(R.id.txtMedicineTimeName);
        this.mListView = (ListView)pView.findViewById(R.id.lstMedicineTimeValues);
        this.mBtnAddTimeValue = (Button)pView.findViewById(R.id.btnAddTimeValue);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mTimeValueArrayAdapter = new TimeValueArrayAdapter(this.getContext(), R.layout.part__medicine_time_value);
        this.mListView.setAdapter(this.mTimeValueArrayAdapter);

        this.mBtnAddTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineTimeAdd.this.buttonAddTimeValueClick();
            }
        });

        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineTimeAdd.this.buttonAddClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineTimeAdd.this.buttonBackClick();
            }
        });

        this.mPresenterMedicineTimeAdd = (IPresenterMedicineTimeAdd)PresenterFactory.createPresenter(IPresenterMedicineTimeAdd.class, this);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_time_add;
    }

    public void buttonAddTimeValueClick() {
        DialogUtils.displayTimePickerDialog(this.getActivity(), true, new DialogUtils.IOnTimeSetListener() {
            @Override
            public void onTimeSet(int pHourOfDay, int pMinute, Activity pActivity, boolean pIs24HourView) {
                String timeValue = String.format(Constants.FORMAT__TIME_VALUE, pHourOfDay)
                        + Constants.DELIMITER__TIME
                        + String.format(Constants.FORMAT__TIME_VALUE, pMinute);
                ScreenMedicineTimeAdd.this.mTimeValueArrayAdapter.addItem(timeValue);
            }
        });
    }

    public void buttonAddClick() {
        String medicineTimeName = this.mTxtMedicineTimeName.getText().toString().trim();
        List<String> timeValues = this.mTimeValueArrayAdapter.getData();
        String medicineTimeValue = TextUtils.join(Constants.DELIMITER__DATA, timeValues);
        this.mPresenterMedicineTimeAdd.insert(medicineTimeName, medicineTimeValue);
    }

    public void buttonBackClick() {
        this.mIScreenActionsListener.onBack();
    }
}
