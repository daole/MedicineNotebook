package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineAdd;

public class ScreenMedicineAdd extends Screen implements IViewMedicineAdd {
    private EditText mTxtMedicineName;
    private Spinner mSelMedicineCategories;
    private Button mBtnCapture;
    private ImageView mImgMedicinePicture;
    private EditText mTxtMedicineNote;
    private Button mBtnAdd;
    private Button mBtnBack;

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
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_add;
    }

    @Override
    public void clearInput() {

    }

    public void buttonCaptureClick() {

    }

    public void buttonAddClick() {

    }

    public void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }
}
