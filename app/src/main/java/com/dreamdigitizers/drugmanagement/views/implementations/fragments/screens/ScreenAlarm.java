package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.AlarmExtended;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterAlarm;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.utils.AlarmUtils;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.utils.SoundUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewAlarm;
import com.dreamdigitizers.drugmanagement.views.implementations.adapters.AdapterTakenMedicineDetails;

import java.text.DateFormat;
import java.util.GregorianCalendar;

public class ScreenAlarm extends Screen implements IViewAlarm, AdapterTakenMedicineDetails.IBitmapLoader {
    private TextView mLblTime;
    private TextView mLblFamilyMemberName;
    private ListView mListView;
    private TextView mLblEmpty;
    private Button mBtnOK;

    private IPresenterAlarm mPresenter;

    private long mRowId;

    @Override
    public void onResume() {
        super.onResume();
        SoundUtils.playAlarmSound(this.getContext());
    }

    @Override
    protected void handleExtras(Bundle pExtras) {
        Bundle bundle = pExtras.getBundle(AlarmUtils.INTENT_EXTRA_KEY__ALARM_DATA);
        this.mRowId = bundle.getLong(Constants.BUNDLE_KEY__ROW_ID);
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
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__alarm, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mLblTime = (TextView)pView.findViewById(R.id.lblTime);
        this.mLblFamilyMemberName = (TextView)pView.findViewById(R.id.lblFamilyMemberName);
        this.mListView = (ListView)pView.findViewById(R.id.lstTakenMedicines);
        this.mLblEmpty = (TextView)pView.findViewById(R.id.lblEmpty);
        this.mBtnOK = (Button)pView.findViewById(R.id.btnOK);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mListView.setEmptyView(this.mLblEmpty);

        this.mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenAlarm.this.buttonOKClick();
            }
        });

        this.mPresenter = (IPresenterAlarm)PresenterFactory.createPresenter(IPresenterAlarm.class, this);
        this.mPresenter.select(this.mRowId);

        if(!this.mStateChecker.isRecreated()) {
            this.mPresenter.setAlarmDone(this.mRowId);
        }
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    private void buttonOKClick() {
        SoundUtils.stop();
        SoundUtils.reset();
        SoundUtils.release();
        this.getActivity().finish();
    }

    @Override
    public void bindData(AlarmExtended pModel) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.getContext());
        GregorianCalendar gregorianCalendar = new GregorianCalendar(pModel.getAlarmYear(),
                pModel.getAlarmMonth(),
                pModel.getAlarmDate(),
                pModel.getAlarmHour(),
                pModel.getAlarmMinute());
        dateFormat.setCalendar(gregorianCalendar);
        String startDate = dateFormat.format(gregorianCalendar.getTime());
        this.mLblTime.setText(startDate);

        String familyMemberName = pModel.getSchedule().getFamilyMember().getFamilyMemberName();
        if(TextUtils.isEmpty(familyMemberName)) {
            familyMemberName = pModel.getSchedule().getFallbackFamilyMemberName();
        }
        this.mLblFamilyMemberName.setText(familyMemberName);

        AdapterTakenMedicineDetails adapter = new AdapterTakenMedicineDetails(this.getContext(), pModel.getSchedule().getTakenMedicines(), this);
        adapter.setListView(this.mListView);
        this.mListView.setAdapter(adapter);
    }

    @Override
    public Bitmap loadBitmap(String pFilePath, int pWidth, int pHeight) {
        return this.mPresenter.loadImage(pFilePath, pWidth, pHeight);
    }
}
