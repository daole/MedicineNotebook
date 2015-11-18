package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.models.PrescriptionExtended;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterPrescriptionEdit;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.utils.DialogUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewPrescriptionEdit;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityCamera;

import java.text.DateFormat;
import java.util.GregorianCalendar;

public class ScreenPrescriptionEdit extends Screen implements IViewPrescriptionEdit {
    private EditText mTxtPrescriptionName;
    private TextView mLblPrescriptionDateValue;
    private ImageButton mBtnSelectPrescriptionDate;
    private Spinner mSelFamilyMembers;
    private ImageButton mBtnAddFamilyMember;
    private ImageView mImgPrescriptionPicture;
    private ImageButton mBtnAddImage;
    private EditText mTxtPrescriptionNote;
    private Button mBtnEdit;
    private Button mBtnBack;

    private IPresenterPrescriptionEdit mPresenter;

    private long mRowId;
    private long mFamilyMemberId;
    private String mPrescriptionPictureFilePath;
    private String mOldPrescriptionPictureFilePath;
    private SpinnerAdapter mAdapter;
    private PrescriptionExtended mModel;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
        return true;
    }

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPresenter = (IPresenterPrescriptionEdit)PresenterFactory.createPresenter(IPresenterPrescriptionEdit.class, this);
        this.mPresenter.select(this.mRowId);
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(Screen.BUNDLE_KEY__ROW_ID, this.mRowId);
        pOutState.putString(Screen.BUNDLE_KEY__PRESCRIPTION_PICTURE_FILE_PATH, this.mPrescriptionPictureFilePath);
    }

    @Override
    protected void retrieveArguments(Bundle pArguments) {
        this.mRowId = pArguments.getLong(Screen.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(Screen.BUNDLE_KEY__ROW_ID);
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
        View rootView = pInflater.inflate(R.layout.screen__prescription_edit, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtPrescriptionName = (EditText)pView.findViewById(R.id.txtPrescriptionName);
        this.mLblPrescriptionDateValue = (TextView)pView.findViewById(R.id.lblPrescriptionDateValue);
        this.mBtnSelectPrescriptionDate = (ImageButton)pView.findViewById(R.id.btnSelectPrescriptionDate);
        this.mSelFamilyMembers = (Spinner)pView.findViewById(R.id.selFamilyMembers);
        this.mBtnAddFamilyMember = (ImageButton)pView.findViewById(R.id.btnAddFamilyMember);
        this.mImgPrescriptionPicture = (ImageView)pView.findViewById(R.id.imgPrescriptionPicture);
        this.mBtnAddImage = (ImageButton)pView.findViewById(R.id.btnAddImage);
        this.mTxtPrescriptionNote = (EditText)pView.findViewById(R.id.txtPrescriptionNote);
        this.mBtnEdit = (Button)pView.findViewById(R.id.btnEdit);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.bindModelData(this.mModel);

        if(!TextUtils.isEmpty(this.mPrescriptionPictureFilePath)) {
            pView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    ScreenPrescriptionEdit.this.loadPrescriptionPicture();
                    ScreenPrescriptionEdit.this.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }

        this.mSelFamilyMembers.setAdapter(this.mAdapter);
        this.mSelFamilyMembers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenPrescriptionEdit.this.selectFamilyMember(pRowId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ScreenPrescriptionEdit.this.selectFamilyMember(0);
            }
        });

        this.mImgPrescriptionPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionEdit.this.prescriptionPictureClick();
            }
        });

        this.mBtnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionEdit.this.buttonAddImageClick();
            }
        });

        this.mBtnSelectPrescriptionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionEdit.this.buttonSelectDateClick();
            }
        });

        this.mBtnAddFamilyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionEdit.this.buttonAddFamilyMemberClick();
            }
        });

        this.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionEdit.this.buttonEditClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenPrescriptionEdit.this.buttonBackClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_prescription_edit;
    }

    @Override
    public void bindData(PrescriptionExtended pModel) {
        this.mModel = pModel;
    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.getLoaderManager();
    }

    @Override
    public void setAdapter(SpinnerAdapter pAdapter) {
        this.mAdapter = pAdapter;
    }

    @Override
    public void onFamilyMemberDataLoaded() {
        if(this.mSelFamilyMembers != null) {
            this.bindFamilyMemberId(this.mModel.getFamilyMemberId());
        }
    }

    @Override
    public void onDataEdited() {
        this.deleteOldMedicinePicture();
    }

    private void selectFamilyMember(long pRowId) {
        this.mFamilyMemberId = pRowId;
    }

    private void prescriptionPictureClick() {
        //Intent intent = new Intent(this.getContext(), ActivityCamera.class);
        //this.startActivityForResult(intent, Constants.REQUEST_CODE__CAMERA);
    }

    private void buttonAddImageClick() {
        Bundle extras = new Bundle();
        extras.putInt(Constants.BUNDLE_KEY__IMAGE_TYPE, Constants.IMAGE_TYPE__PRESCRIPTION);
        extras.putBoolean(Constants.BUNDLE_KEY__IS_CROPPED, false);
        Intent intent = new Intent(this.getContext(), ActivityCamera.class);
        intent.putExtra(Constants.INTENT_EXTRA_KEY__DATA, extras);
        this.startActivityForResult(intent, Constants.REQUEST_CODE__CAMERA);
    }

    private void buttonSelectDateClick() {
        DialogUtils.displayDatePickerDialog(this.getActivity(), this.getString(R.string.btn__cancel), new DialogUtils.IOnDatePickerDialogEventListener() {
            @Override
            public void onDateSet(int pYear, int pMonthOfYear, int pDayOfMonth, Activity pActivity, String pCancelButtonText) {
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(ScreenPrescriptionEdit.this.getContext());
                GregorianCalendar gregorianCalendar = new GregorianCalendar(pYear, pMonthOfYear, pDayOfMonth);
                dateFormat.setCalendar(gregorianCalendar);
                String date = dateFormat.format(gregorianCalendar.getTime());
                ScreenPrescriptionEdit.this.mLblPrescriptionDateValue.setText(date);
            }

            @Override
            public void onCancel(Activity pActivity, String pCancelButtonText) {

            }
        });
    }

    private void buttonAddFamilyMemberClick() {
        this.goToFamilyMemberAddScreen();
    }

    private void buttonEditClick() {
        String prescriptionName = this.mTxtPrescriptionName.getText().toString().trim();
        String prescriptionDate = this.mLblPrescriptionDateValue.getText().toString().trim();
        String prescriptionNote = this.mTxtPrescriptionNote.getText().toString().trim();
        this.mPresenter.edit(this.mRowId, prescriptionName, prescriptionDate, this.mFamilyMemberId, this.mPrescriptionPictureFilePath, prescriptionNote);
    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }

    private void bindFamilyMemberId(long pFamilyMemberId) {
        for(int i = 0; i < this.mSelFamilyMembers.getCount(); i++) {
            Cursor cursor = (Cursor)this.mSelFamilyMembers.getItemAtPosition(i);
            long rowId = cursor.getLong(cursor.getColumnIndex(Table.COLUMN_NAME__ID));
            if(rowId == pFamilyMemberId) {
                this.mSelFamilyMembers.setSelection(i);
                break;
            }
        }
    }

    private void deletePrescriptionPicture() {
        if(!TextUtils.isEmpty(this.mPrescriptionPictureFilePath) && !this.mPrescriptionPictureFilePath.equals(this.mOldPrescriptionPictureFilePath)) {
            this.mPresenter.deleteImage(this.mPrescriptionPictureFilePath);
        }
    }

    private void deleteOldMedicinePicture() {
        if(!TextUtils.isEmpty(this.mPrescriptionPictureFilePath) && !this.mPrescriptionPictureFilePath.equals(this.mOldPrescriptionPictureFilePath)) {
            this.mPresenter.deleteImage(this.mOldPrescriptionPictureFilePath);
            this.mOldPrescriptionPictureFilePath = this.mPrescriptionPictureFilePath;
        }
    }

    private void loadPrescriptionPicture() {
        Bitmap bitmap = this.mPresenter.loadImage(this.mPrescriptionPictureFilePath,
                this.mImgPrescriptionPicture.getWidth(),
                this.mImgPrescriptionPicture.getHeight());
        if(bitmap != null) {
            this.mImgPrescriptionPicture.setImageBitmap(bitmap);
        }
    }

    private void goToFamilyMemberAddScreen() {
        Screen screen = new ScreenFamilyMemberAdd();
        this.mScreenActionsListener.onChangeScreen(screen);
    }

    public void bindModelData(PrescriptionExtended pModel) {
        this.mTxtPrescriptionName.setText(pModel.getPrescriptionName());
        this.mLblPrescriptionDateValue.setText(pModel.getPrescriptionDate());
        this.bindFamilyMemberId(pModel.getFamilyMemberId());
        this.mOldPrescriptionPictureFilePath = pModel.getImagePath();
        if(TextUtils.isEmpty(this.mPrescriptionPictureFilePath)) {
            this.mPrescriptionPictureFilePath = this.mOldPrescriptionPictureFilePath;
        }
        this.mTxtPrescriptionNote.setText(pModel.getPrescriptionNote());
    }
}
