package com.dreamdigitizers.medicinenote.views.implementations.fragments.screens;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dreamdigitizers.medicinenote.Constants;
import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.data.models.AlarmExtended;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterAlarm;
import com.dreamdigitizers.medicinenote.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.medicinenote.utils.AlarmUtils;
import com.dreamdigitizers.medicinenote.utils.SoundUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewAlarm;
import com.dreamdigitizers.medicinenote.views.implementations.adapters.AdapterTakenMedicineDetails;

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
    private AlarmExtended mModel;
    private Handler mHandler;

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPresenter = (IPresenterAlarm)PresenterFactory.createPresenter(IPresenterAlarm.class, this);
        this.mPresenter.select(this.mRowId);
        if(!this.mStateChecker.isRecreated()) {
            this.mPresenter.setAlarmDone(this.mRowId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!SoundUtils.isPlaying()) {
            SoundUtils.playAlarmSound(this.getContext());
        }

        this.mHandler = new Handler();
        this.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SoundUtils.isPlaying()) {
                    ScreenAlarm.this.buttonOKClick();
                }
            }
        }, Constants.ALARM_TIME);
    }

    @Override
    protected void handleExtras(Bundle pExtras) {
        Bundle bundle = pExtras.getBundle(AlarmUtils.INTENT_EXTRA_KEY__DATA);
        this.mRowId = bundle.getLong(Constants.BUNDLE_KEY__ROW_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(Constants.BUNDLE_KEY__ROW_ID, this.mRowId);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(Constants.BUNDLE_KEY__ROW_ID);
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
        this.bindModelData(this.mModel);

        this.mListView.setEmptyView(this.mLblEmpty);

        this.mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenAlarm.this.buttonOKClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    public void bindData(AlarmExtended pModel) {
        this.mModel = pModel;
    }

    @Override
    public Bitmap loadBitmap(String pFilePath, int pWidth, int pHeight) {
        return this.mPresenter.loadImage(pFilePath, pWidth, pHeight);
    }

    private void listItemClick(long pRowId) {
        this.goToMedicineInformationScreen(pRowId);
    }

    private void buttonOKClick() {
        if(SoundUtils.isPlaying()) {
            SoundUtils.stop();
            SoundUtils.reset();
            SoundUtils.release();
        }
        this.getActivity().finish();
    }

    private void bindModelData(AlarmExtended pModel) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.getContext());
        GregorianCalendar gregorianCalendar = new GregorianCalendar(pModel.getAlarmYear(),
                pModel.getAlarmMonth(),
                pModel.getAlarmDate());
        dateFormat.setCalendar(gregorianCalendar);
        String dateValue = dateFormat.format(gregorianCalendar.getTime());
        String timeValue = String.format(Constants.FORMAT__TIME_VALUE, pModel.getAlarmHour())
                + Constants.DELIMITER__TIME
                + String.format(Constants.FORMAT__TIME_VALUE, pModel.getAlarmMinute());
        this.mLblTime.setText(dateValue + " " + timeValue);

        String familyMemberName = pModel.getSchedule().getFamilyMember().getFamilyMemberName();
        if(TextUtils.isEmpty(familyMemberName)) {
            familyMemberName = pModel.getSchedule().getFallbackFamilyMemberName();
        }
        this.mLblFamilyMemberName.setText(familyMemberName);

        AdapterTakenMedicineDetails adapter = new AdapterTakenMedicineDetails(this.getContext(), pModel.getSchedule().getTakenMedicines(), this);
        adapter.setListView(this.mListView);
        this.mListView.setAdapter(adapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenAlarm.this.listItemClick(pRowId);
            }
        });
    }

    private void goToMedicineInformationScreen(long pRowId) {
        Bundle arguments = new Bundle();
        arguments.putLong(Constants.BUNDLE_KEY__ROW_ID, pRowId);
        Screen screen = new ScreenMedicineInformation();
        screen.setArguments(arguments);
        this.mScreenActionsListener.onChangeScreen(screen);
    }
}
