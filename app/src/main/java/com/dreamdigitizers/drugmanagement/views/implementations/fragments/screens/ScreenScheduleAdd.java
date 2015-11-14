package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityBase;
import com.dreamdigitizers.drugmanagement.views.implementations.adapters.TakenMedicineAdapter;
import com.dreamdigitizers.drugmanagement.views.implementations.dialogs.DialogMedicineSelect;

import java.text.DateFormat;
import java.util.GregorianCalendar;

public class ScreenScheduleAdd extends Screen implements IViewScheduleAdd {
    private Spinner mSelFamilyMembers;
    private ImageButton mBtnAddFamilyMember;
    private ListView mListView;
    private ImageButton mBtnSelectMedicine;
    private TextView mLblStartDateValue;
    private ImageButton mBtnSelectStartDate;
    private Spinner mSelMedicineTimes;
    private ImageButton mBtnAddMedicineTime;
    private Spinner mSelMedicineIntervals;
    private ImageButton mBtnAddMedicineInterval;
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

    private TakenMedicine[] mAdapterData;

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mAdapter = new TakenMedicineAdapter(this.getContext());
        if(this.mAdapterData != null) {
            this.mAdapter.addItems(this.mAdapterData);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        ArrayMap<Long, TakenMedicine> adapterData = this.mAdapter.getData();
        pOutState.putSerializable(Screen.BUNDLE_KEY__ADAPTER_DATA, adapterData.values().toArray());
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mAdapterData = (TakenMedicine[])pSavedInstanceState.getSerializable(Screen.BUNDLE_KEY__ADAPTER_DATA);
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__schedule_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mSelFamilyMembers = (Spinner)pView.findViewById(R.id.selFamilyMembers);
        this.mBtnAddFamilyMember = (ImageButton)pView.findViewById(R.id.btnAddFamilyMember);
        this.mBtnSelectMedicine = (ImageButton)pView.findViewById(R.id.btnSelectMedicine);
        this.mListView = (ListView)pView.findViewById(R.id.lstMedicines);
        this.mLblStartDateValue = (TextView)pView.findViewById(R.id.lblStartDateValue);
        this.mBtnSelectStartDate = (ImageButton)pView.findViewById(R.id.btnSelectStartDate);
        this.mSelMedicineTimes = (Spinner)pView.findViewById(R.id.selMedicineTimes);
        this.mBtnAddMedicineTime = (ImageButton)pView.findViewById(R.id.btnAddMedicineTime);
        this.mSelMedicineIntervals = (Spinner)pView.findViewById(R.id.selMedicineTimeIntervals);
        this.mBtnAddMedicineInterval = (ImageButton)pView.findViewById(R.id.btnAddMedicineInterval);
        this.mSwiAlarm = (Switch)pView.findViewById(R.id.swiAlarm);
        this.mTxtAlarmTimes = (EditText)pView.findViewById(R.id.txtTimes);
        this.mTxtScheduleNote = (EditText)pView.findViewById(R.id.txtScheduleNote);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mListView.setAdapter(this.mAdapter);

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

        this.mBtnAddFamilyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenScheduleAdd.this.buttonAddFamilyMemberClick();
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

        this.mBtnAddMedicineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenScheduleAdd.this.buttonAddMedicineTimeClick();
            }
        });

        this.mBtnAddMedicineInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenScheduleAdd.this.buttonAddMedicineIntervalClick();
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
        this.mSelFamilyMembers.setSelection(0);
        this.mSelMedicineTimes.setSelection(0);
        this.mSelMedicineIntervals.setSelection(0);
        this.mLblStartDateValue.setText("");
        this.mTxtAlarmTimes.setText("");
        this.mTxtScheduleNote.setText("");
        this.mAdapter.clearItem();
        this.mSwiAlarm.setChecked(true);
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

    private void buttonAddFamilyMemberClick() {
        this.goToFamilyMemberAddScreen();
    }

    private void buttonSelectMedicineClick() {
        DialogMedicineSelect dialog = new DialogMedicineSelect((ActivityBase)this.getActivity(),
                new DialogMedicineSelect.IOnDialogButtonClickListener() {
                    @Override
                    public void onMedicineSelect(Medicine pMedicine, String pDose) {
                        TakenMedicine takenMedicine = new TakenMedicine();
                        takenMedicine.setMedicineId(pMedicine.getRowId());
                        takenMedicine.setFallbackMedicineName(pMedicine.getMedicineName());
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

    private void buttonAddMedicineTimeClick() {
        this.goToMedicineTimeAddScreen();
    }

    private void buttonAddMedicineIntervalClick() {
        this.goToMedicineIntervalAddScreen();
    }

    private void buttonAddClick() {
        this.mPresenter.insert(this.mFamilyMember,
                this.mAdapter.getData(),
                this.mLblStartDateValue.getText().toString().trim(),
                this.mMedicineTime,
                this.mMedicineInterval,
                this.mSwiAlarm.isChecked(),
                this.mTxtAlarmTimes.getText().toString().trim(),
                this.mTxtScheduleNote.getText().toString().trim());
    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }

    private void goToFamilyMemberAddScreen() {
        Screen screen = new ScreenFamilyMemberAdd();
        this.mScreenActionsListener.onChangeScreen(screen);
    }

    private void goToMedicineTimeAddScreen() {
        Screen screen = new ScreenMedicineTimeAdd();
        this.mScreenActionsListener.onChangeScreen(screen);
    }

    private void goToMedicineIntervalAddScreen() {
        Screen screen = new ScreenMedicineIntervalAdd();
        this.mScreenActionsListener.onChangeScreen(screen);
    }
}
