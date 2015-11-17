package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterPrescriptionAdd;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewPrescriptionAdd;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityCamera;

import java.text.DateFormat;
import java.util.GregorianCalendar;

public class ScreenPrescriptionAdd extends Screen implements IViewPrescriptionAdd {
    private EditText mTxtPrescriptionName;
    private TextView mLblPrescriptionDateValue;
    private ImageButton mBtnSelectPrescriptionDate;
    private Spinner mSelFamilyMembers;
    private ImageButton mBtnAddFamilyMember;
    private ImageView mImgPrescriptionPicture;
    private EditText mTxtPrescriptionNote;
    private Button mBtnAdd;
    private Button mBtnBack;

    private IPresenterPrescriptionAdd mPresenter;

    private long mFamilyMemberId;
    private String mPrescriptionPictureFilePath;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(Screen.BUNDLE_KEY__FAMILY_MEMBER_ID, this.mFamilyMemberId);
        pOutState.putString(Screen.BUNDLE_KEY__PRESCRIPTION_PICTURE_FILE_PATH, this.mPrescriptionPictureFilePath);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mFamilyMemberId = pSavedInstanceState.getLong(Screen.BUNDLE_KEY__FAMILY_MEMBER_ID);
        this.mPrescriptionPictureFilePath = pSavedInstanceState.getString(Screen.BUNDLE_KEY__PRESCRIPTION_PICTURE_FILE_PATH);
    }

    @Override
    public void onActivityResult (int pRequestCode, int pResultCode, Intent pData) {
        if(pRequestCode == Constants.REQUEST_CODE__CAMERA) {
            if(pResultCode == Activity.RESULT_OK) {
                this.deletePrescriptionPicture();
                this.mPrescriptionPictureFilePath = pData.getExtras().getString(Constants.BUNDLE_KEY__CAPTURED_PICTURE_FILE_PATH);
                this.loadPrescriptionPicture();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!this.mIsRecoverable) {
            this.deletePrescriptionPicture();
        }
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__prescription_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtPrescriptionName = (EditText)pView.findViewById(R.id.txtFamilyMemberName);
        this.mLblPrescriptionDateValue = (TextView)pView.findViewById(R.id.lblPrescriptionDateValue);
        this.mBtnSelectPrescriptionDate = (ImageButton)pView.findViewById(R.id.btnSelectPrescriptionDate);
        this.mSelFamilyMembers = (Spinner)pView.findViewById(R.id.selFamilyMembers);
        this.mBtnAddFamilyMember = (ImageButton)pView.findViewById(R.id.btnAddFamilyMember);
        this.mImgPrescriptionPicture = (ImageView)pView.findViewById(R.id.imgPrescriptionPicture);
        this.mTxtPrescriptionNote = (EditText)pView.findViewById(R.id.txtPrescriptionNote);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mSelFamilyMembers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenPrescriptionAdd.this.selectFamilyMember(pRowId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ScreenPrescriptionAdd.this.selectFamilyMember(0);
            }
        });

        this.mImgPrescriptionPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionAdd.this.prescriptionPictureClick();
            }
        });

        this.mBtnSelectPrescriptionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionAdd.this.buttonSelectDateClick();
            }
        });

        this.mBtnAddFamilyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionAdd.this.buttonAddFamilyMemberClick();
            }
        });

        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionAdd.this.buttonAddClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionAdd.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterPrescriptionAdd)PresenterFactory.createPresenter(IPresenterPrescriptionAdd.class, this);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_family_member_add;
    }

    @Override
    public void clearInput() {
        this.mTxtPrescriptionName.setText("");
        this.mLblPrescriptionDateValue.setText("");
        this.mSelFamilyMembers.setSelection(0);
        this.mImgPrescriptionPicture.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.icon__no_photo));
        this.mTxtPrescriptionNote.setText("");
        this.mPrescriptionPictureFilePath = null;
    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.getLoaderManager();
    }

    @Override
    public void setAdapter(SpinnerAdapter pAdapter) {
        this.mSelFamilyMembers.setAdapter(pAdapter);
    }

    private void selectFamilyMember(long pRowId) {
        this.mFamilyMemberId = pRowId;
    }

    private void prescriptionPictureClick() {
        Intent intent = new Intent(ScreenPrescriptionAdd.this.getContext(), ActivityCamera.class);
        ScreenPrescriptionAdd.this.startActivityForResult(intent, Constants.REQUEST_CODE__CAMERA);
    }

    private void buttonSelectDateClick() {
        DialogUtils.displayDatePickerDialog(this.getActivity(), this.getString(R.string.btn__cancel), new DialogUtils.IOnDatePickerDialogEventListener() {
            @Override
            public void onDateSet(int pYear, int pMonthOfYear, int pDayOfMonth, Activity pActivity, String pCancelButtonText) {
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(ScreenPrescriptionAdd.this.getContext());
                GregorianCalendar gregorianCalendar = new GregorianCalendar(pYear, pMonthOfYear, pDayOfMonth);
                dateFormat.setCalendar(gregorianCalendar);
                String date = dateFormat.format(gregorianCalendar.getTime());
                ScreenPrescriptionAdd.this.mLblPrescriptionDateValue.setText(date);
            }

            @Override
            public void onCancel(Activity pActivity, String pCancelButtonText) {

            }
        });
    }

    private void buttonAddFamilyMemberClick() {
        this.goToFamilyMemberAddScreen();
    }

    private void buttonAddClick() {
        String prescriptionName = this.mTxtPrescriptionName.getText().toString().trim();
        String prescriptionDate = this.mLblPrescriptionDateValue.getText().toString().trim();
        String prescriptionNote = this.mTxtPrescriptionNote.getText().toString().trim();
        this.mPresenter.insert(prescriptionName, prescriptionDate, this.mFamilyMemberId, this.mPrescriptionPictureFilePath, prescriptionNote);
    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }

    private void deletePrescriptionPicture() {
        this.mPresenter.deleteImage(this.mPrescriptionPictureFilePath);
    }

    private void loadPrescriptionPicture() {
        this.mImgPrescriptionPicture.setImageBitmap(
                this.mPresenter.loadImage(
                        this.mPrescriptionPictureFilePath,
                        this.mImgPrescriptionPicture.getWidth(),
                        this.mImgPrescriptionPicture.getHeight()));
    }

    private void goToFamilyMemberAddScreen() {
        Screen screen = new ScreenFamilyMemberAdd();
        this.mScreenActionsListener.onChangeScreen(screen);
    }
}
