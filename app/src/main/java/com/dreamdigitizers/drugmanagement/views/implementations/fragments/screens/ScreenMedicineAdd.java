package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineAdd;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineAdd;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityCamera;

public class ScreenMedicineAdd extends Screen implements IViewMedicineAdd {
    private EditText mTxtMedicineName;
    private Spinner mSelMedicineCategories;
    private Button mBtnCapture;
    private ImageView mImgMedicinePicture;
    private EditText mTxtMedicineNote;
    private Button mBtnAdd;
    private Button mBtnBack;

    private long mMedicineCategoryId;
    private String mMedicinePictureFilePath;

    private IPresenterMedicineAdd mPresenter;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
        return true;
    }

    @Override
    public void onActivityResult (int pRequestCode, int pResultCode, Intent pData) {
        if(pRequestCode == Constants.REQUEST_CODE__CAMERA) {
            if(pResultCode == Activity.RESULT_OK) {
                this.deleteMedicinePicture();
                this.mMedicinePictureFilePath = pData.getExtras().getString(Constants.BUNDLE_KEY__CAPTURED_PICTURE_FILE_PATH);
                this.loadMedicinePicture();
            }
        }
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineName = (EditText)pView.findViewById(R.id.txtMedicineName);
        this.mSelMedicineCategories = (Spinner)pView.findViewById(R.id.selMedicineCategories);
        this.mBtnCapture = (Button)pView.findViewById(R.id.btnCapture);
        this.mImgMedicinePicture = (ImageView)pView.findViewById(R.id.imgMedicinePicture);
        this.mTxtMedicineNote = (EditText)pView.findViewById(R.id.txtMedicineNote);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mSelMedicineCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenMedicineAdd.this.selectMedicineCategory(pRowId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ScreenMedicineAdd.this.selectMedicineCategory(0);
            }
        });

        this.mBtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineAdd.this.buttonCaptureClick();
            }
        });

        this.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineAdd.this.buttonAddClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineAdd.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterMedicineAdd) PresenterFactory.createPresenter(IPresenterMedicineAdd.class, this);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_add;
    }

    @Override
    public void clearInput() {

    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.getLoaderManager();
    }

    @Override
    public void setAdapter(SpinnerAdapter pAdapter) {
        this.mSelMedicineCategories.setAdapter(pAdapter);
    }

    private void buttonCaptureClick() {
        Intent intent = new Intent(ScreenMedicineAdd.this.getContext(), ActivityCamera.class);
        ScreenMedicineAdd.this.startActivityForResult(intent, Constants.REQUEST_CODE__CAMERA);
    }

    private void selectMedicineCategory(long pRowId) {
        this.mMedicineCategoryId = pRowId;
    }

    private void buttonAddClick() {

    }

    private void buttonBackClick() {
        this.deleteMedicinePicture();
        this.mScreenActionsListener.onBack();
    }

    private void deleteMedicinePicture() {
        this.mPresenter.deleteImage(this.mMedicinePictureFilePath);
    }

    private void loadMedicinePicture() {
        this.mImgMedicinePicture.setImageBitmap(
                this.mPresenter.loadImage(
                        this.mMedicinePictureFilePath,
                        this.mImgMedicinePicture.getWidth(),
                        this.mImgMedicinePicture.getHeight()));
    }
}
