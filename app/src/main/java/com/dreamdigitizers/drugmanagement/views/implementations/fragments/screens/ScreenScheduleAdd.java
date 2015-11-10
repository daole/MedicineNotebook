package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.app.Activity;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.FamilyMember;
import com.dreamdigitizers.drugmanagement.data.models.Medicine;
import com.dreamdigitizers.drugmanagement.data.models.MedicineInterval;
import com.dreamdigitizers.drugmanagement.data.models.MedicineTime;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicine;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleAdd;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleAdd;
import com.dreamdigitizers.drugmanagement.views.implementations.adapters.TakenMedicineAdapter;
import com.dreamdigitizers.drugmanagement.views.implementations.dialogs.DialogMedicineSelect;

import java.text.DateFormat;
import java.util.GregorianCalendar;

public class ScreenScheduleAdd extends Screen implements IViewScheduleAdd {
    private Spinner mSelFamilyMembers;
    private Button mBtnSelectMedicine;
    private ListView mListView;
    private TextView mLblStartDateValue;
    private Button mBtnSelectStartDate;
    private Spinner mSelMedicineTimes;
    private Spinner mSelMedicineIntervals;
    private Switch mSwiAlarm;
    private EditText mTxtAlarmTimes;
    private EditText mTxtScheduleNote;
    private Button mBtnBack;
    private Button mBtnAdd;

    private IPresenterScheduleAdd mPresenter;
    //private SpinnerAdapter mMedicineAdapter;
    private TakenMedicineAdapter mAdapter;

    private FamilyMember mFamilyMember;
    private MedicineTime mMedicineTime;
    private MedicineInterval mMedicineInterval;
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
        this.mListView = (ListView)pView.findViewById(R.id.lstMedicines);
        this.mLblStartDateValue = (TextView)pView.findViewById(R.id.lblStartDateValue);
        this.mBtnSelectStartDate = (Button)pView.findViewById(R.id.btnSelectStartDate);
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
        this.mAdapter = new TakenMedicineAdapter(this.getContext());
        this.mListView.setAdapter(this.mAdapter);

        this.mSwiAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                ScreenScheduleAdd.this.changeAlarmSetting(pIsChecked);
            }
        });

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

        this.mBtnSelectMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenScheduleAdd.this.buttonSelectMedicineClick();
            }
        });

        this.mBtnSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenScheduleAdd.this.buttonSelectStartDateClick();
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

    /*
    @Override
    public void setMedicineAdapter(SpinnerAdapter pAdapter) {
        this.mMedicineAdapter = pAdapter;
    }
    */

    @Override
    public void setMedicineTimeAdapter(SpinnerAdapter pAdapter) {
        this.mSelMedicineTimes.setAdapter(pAdapter);
    }

    @Override
    public void setMedicineIntervalAdapter(SpinnerAdapter pAdapter) {
        this.mSelMedicineIntervals.setAdapter(pAdapter);
    }

    private void changeAlarmSetting(boolean pIsChecked) {
        this.mIsAlarm = pIsChecked;
        this.mTxtAlarmTimes.setEnabled(pIsChecked);
    }

    private void selectFamilyMember(int pPosition, long pRowId) {
        if(pRowId > 0) {
            Cursor cursor = (Cursor)this.mSelFamilyMembers.getItemAtPosition(pPosition);
            this.mFamilyMember = FamilyMember.fetchDataAtCurrentPosition(cursor);
        } else {
            this.mFamilyMember = null;
        }
    }

    private void selectMedicineTime(int pPosition, long pRowId) {
        if(pRowId > 0) {
            Cursor cursor = (Cursor)this.mSelMedicineTimes.getItemAtPosition(pPosition);
            this.mMedicineTime = MedicineTime.fetchDataAtCurrentPosition(cursor);
        } else {
            this.mMedicineTime = null;
        }
    }

    private void selectMedicineInterval(int pPosition, long pRowId) {
        if(pRowId > 0) {
            Cursor cursor = (Cursor)this.mSelMedicineIntervals.getItemAtPosition(pPosition);
            this.mMedicineInterval = MedicineInterval.fetchDataAtCurrentPosition(cursor);
        } else {
            this.mMedicineInterval = null;
        }
    }

    private void buttonSelectMedicineClick() {
        DialogMedicineSelect dialog = new DialogMedicineSelect((AppCompatActivity) this.getActivity(),
                new DialogMedicineSelect.IOnDialogButtonClickListener() {
                    @Override
                    public void onMedicineSelect(Medicine pMedicine, String pDose) {
                        TakenMedicine takenMedicine = new TakenMedicine();
                        takenMedicine.setMedicineId(pMedicine.getRowId());
                        takenMedicine.setMedicineName(pMedicine.getMedicineName());
                        takenMedicine.setDose(pDose);
                        ScreenScheduleAdd.this.mAdapter.addItem(takenMedicine);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
        dialog.show();
    }

    private void buttonSelectStartDateClick() {
        DialogUtils.displayDatePickerDialog(this.getActivity(), this.getString(R.string.btn__cancel), new DialogUtils.IOnDatePickerDialogEventListener() {
            @Override
            public void onDateSet(int pYear, int pMonthOfYear, int pDayOfMonth, Activity pActivity, String pCancelButtonText) {
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(ScreenScheduleAdd.this.getContext());
                GregorianCalendar gregorianCalendar = new GregorianCalendar(pYear, pMonthOfYear, pDayOfMonth);
                dateFormat.setCalendar(gregorianCalendar);
                String startDate = dateFormat.format(gregorianCalendar.getTime());
                ScreenScheduleAdd.this.mLblStartDateValue.setText(startDate);
            }

            @Override
            public void onCancel(Activity pActivity, String pCancelButtonText) {

            }
        });
    }

    private void buttonAddClick() {

    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }
}
