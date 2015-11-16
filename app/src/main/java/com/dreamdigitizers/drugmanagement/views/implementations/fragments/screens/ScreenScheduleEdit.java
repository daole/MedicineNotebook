package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.AlarmExtended;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleEdit;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleEdit;
import com.dreamdigitizers.drugmanagement.views.implementations.adapters.AdapterTakenMedicineDetails;

import java.text.DateFormat;
import java.util.GregorianCalendar;

public class ScreenScheduleEdit extends Screen implements IViewScheduleEdit, AdapterTakenMedicineDetails.IBitmapLoader {
    private TextView mLblFamilyMemberNameValue;
    private ListView mListView;
    private TextView mLblStartDateValue;
    private TextView mLblMedicineTimeValue;
    private TextView mLblMedicineIntervalValue;
    private ImageView mImageViewDone;
    private CheckBox mChkAlarm;
    private TextView mLblTimesValue;
    private TextView mLblScheduleNoteValue;
    private Button mBtnBack;

    private IPresenterScheduleEdit mPresenter;

    private long mRowId;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
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
        View rootView = pInflater.inflate(R.layout.screen__schedule_edit, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mLblFamilyMemberNameValue = (TextView)pView.findViewById(R.id.lblFamilyMemberNameValue);
        this.mListView = (ListView)pView.findViewById(R.id.lstMedicines);
        this.mLblStartDateValue = (TextView)pView.findViewById(R.id.lblStartDateValue);
        this.mLblMedicineTimeValue = (TextView)pView.findViewById(R.id.lblMedicineTimeValue);
        this.mLblMedicineIntervalValue = (TextView)pView.findViewById(R.id.lblMedicineIntervalValue);
        this.mImageViewDone = (ImageView)pView.findViewById(R.id.imgDone);
        this.mChkAlarm = (CheckBox)pView.findViewById(R.id.chkAlarm);
        this.mLblTimesValue = (TextView)pView.findViewById(R.id.lblTimesValue);
        this.mLblScheduleNoteValue = (TextView)pView.findViewById(R.id.lblScheduleNoteValue);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenScheduleEdit.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterScheduleEdit)PresenterFactory.createPresenter(IPresenterScheduleEdit.class, this);
        this.mPresenter.select(this.mRowId);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_schedule_edit;
    }

    @Override
    public void bindData(AlarmExtended pModel) {
        String familyMemberName = pModel.getSchedule().getFamilyMember().getFamilyMemberName();
        if(TextUtils.isEmpty(familyMemberName)) {
            familyMemberName = pModel.getSchedule().getFallbackFamilyMemberName();
        }
        this.mLblFamilyMemberNameValue.setText(familyMemberName);

        AdapterTakenMedicineDetails adapter = new AdapterTakenMedicineDetails(this.getContext(), pModel.getSchedule().getTakenMedicines(), this);
        adapter.setListView(this.mListView);
        this.mListView.setAdapter(adapter);

        this.mLblStartDateValue.setText(pModel.getSchedule().getStartDate());

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.getContext());
        GregorianCalendar gregorianCalendar = new GregorianCalendar(pModel.getAlarmYear(),
                pModel.getAlarmMonth(),
                pModel.getAlarmDate());
        dateFormat.setCalendar(gregorianCalendar);
        String dateValue = dateFormat.format(gregorianCalendar.getTime());
        String timeValue = String.format(Constants.FORMAT__TIME_VALUE, pModel.getAlarmHour())
                + Constants.DELIMITER__TIME
                + String.format(Constants.FORMAT__TIME_VALUE, pModel.getAlarmMinute());
        this.mLblMedicineTimeValue.setText(dateValue + " " + timeValue);

        int intervalValue = pModel.getSchedule().getMedicineInterval().getMedicineIntervalValue();
        String intervalValueWithUnit = this.getResources().getQuantityString(R.plurals.lbl__day, intervalValue, intervalValue);
        this.mLblMedicineIntervalValue.setText(intervalValueWithUnit);

        if(pModel.getIsDone()) {
            this.mImageViewDone.setVisibility(View.VISIBLE);
            this.mChkAlarm.setVisibility(View.GONE);
        } else {
            this.mImageViewDone.setVisibility(View.GONE);
            this.mChkAlarm.setVisibility(View.VISIBLE);
            this.mChkAlarm.setChecked(pModel.getIsAlarm());
            this.mChkAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton pButtonView, boolean pIsChecked) {
                    ScreenScheduleEdit.this.checkBoxAlarmCheckChanged(pIsChecked);
                }
            });
        }

        this.mLblTimesValue.setText(Integer.toString(pModel.getSchedule().getTimes()));

        this.mLblScheduleNoteValue.setText(pModel.getSchedule().getScheduleNote());
    }

    @Override
    public Bitmap loadBitmap(String pFilePath, int pWidth, int pHeight) {
        return this.mPresenter.loadImage(pFilePath, pWidth, pHeight);
    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }

    private void checkBoxAlarmCheckChanged(boolean pIsChecked) {
        this.mPresenter.changeAlarmStatus(pIsChecked);
    }
}
