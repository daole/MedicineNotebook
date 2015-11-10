package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleAdd;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleAdd;

public class ScreenScheduleAdd extends Screen implements IViewScheduleAdd {
    private Spinner mSelFamilyMembers;
    private Button mBtnSelectMedicine;
    private ListView mLstMedicines;
    private Spinner mSelMedicineTimes;
    private Spinner mSelMedicineIntervals;
    private Switch mSwiAlarm;
    private EditText mTxtAlarmTimes;
    private EditText mTxtScheduleNote;
    private Button mBtnBack;
    private Button mBtnAdd;

    private IPresenterScheduleAdd mPresenter;
    private SpinnerAdapter mMedicineAdapter;

    private long mFamilyMemberId;
    private long mMedicineTimeId;
    private long mMedicineIntervalId;
    private boolean mIsAlarm;

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__schedule_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mSelFamilyMembers = (Spinner)pView.findViewById(R.id.selFamilyMembers);
        this.mBtnSelectMedicine = (Button)pView.findViewById(R.id.btnSelectMedicine);
        this.mLstMedicines = (ListView)pView.findViewById(R.id.lstMedicines);
        this.mSelMedicineTimes = (Spinner)pView.findViewById(R.id.selMedicineTimes);
        this.mSelMedicineIntervals = (Spinner)pView.findViewById(R.id.selMedicineTimeIntervals);
        this.mSwiAlarm = (Switch)pView.findViewById(R.id.swiAlarm);
        this.mTxtAlarmTimes = (EditText)pView.findViewById(R.id.txtAlarmTimes);
        this.mTxtScheduleNote = (EditText)pView.findViewById(R.id.txtScheduleNote);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mSelFamilyMembers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenScheduleAdd.this.selectFamilyMember(pPosition, pRowId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ScreenScheduleAdd.this.selectFamilyMember(-1, 0);
            }
        });

        this.mBtnSelectMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenScheduleAdd.this.buttonSelectMedicineClick();
            }
        });

        this.mSelMedicineTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenScheduleAdd.this.selectMedicineTime(pPosition, pRowId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ScreenScheduleAdd.this.selectMedicineTime(-1, 0);
            }
        });

        this.mSelMedicineIntervals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenScheduleAdd.this.selectMedicineInterval(pPosition, pRowId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ScreenScheduleAdd.this.selectMedicineInterval(-1, 0);
            }
        });

        this.mSwiAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                ScreenScheduleAdd.this.changeAlarmSetting(pIsChecked);
            }
        });

        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenScheduleAdd.this.buttonAddClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenScheduleAdd.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterScheduleAdd)PresenterFactory.createPresenter(IPresenterScheduleAdd.class, this);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_schedule_add;
    }

    @Override
    public void clearInput() {

    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.getLoaderManager();
    }

    @Override
    public void setFamilyMemberAdapter(SpinnerAdapter pAdapter) {
        this.mSelFamilyMembers.setAdapter(pAdapter);
    }

    @Override
    public void setMedicineAdapter(SpinnerAdapter pAdapter) {
        this.mMedicineAdapter = pAdapter;
    }

    @Override
    public void setMedicineTimeAdapter(SpinnerAdapter pAdapter) {
        this.mSelMedicineTimes.setAdapter(pAdapter);
    }

    @Override
    public void setMedicineIntervalAdapter(SpinnerAdapter pAdapter) {
        this.mSelMedicineIntervals.setAdapter(pAdapter);
    }

    private void selectFamilyMember(int pPosition, long pRowId) {
        this.mFamilyMemberId = pRowId;
    }

    private void selectMedicineTime(int pPosition, long pRowId) {
        this.mMedicineTimeId = pRowId;
    }

    private void selectMedicineInterval(int pPosition, long pRowId) {
        this.mMedicineIntervalId = pRowId;
    }

    private void buttonSelectMedicineClick() {

    }

    private void changeAlarmSetting(boolean pIsChecked) {
        this.mIsAlarm = pIsChecked;
        this.mTxtAlarmTimes.setEnabled(pIsChecked);
    }

    private void buttonAddClick() {

    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }
}
