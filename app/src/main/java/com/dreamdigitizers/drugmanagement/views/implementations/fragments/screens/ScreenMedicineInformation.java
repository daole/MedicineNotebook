package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicineExtended;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineInformation;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineInformation;
import com.dreamdigitizers.drugmanagement.views.implementations.customviews.ZoomableImageView;

public class ScreenMedicineInformation extends Screen implements IViewMedicineInformation {
    private TextView mLblMedicineNameValue;
    private TextView mLblMedicineNoteValue;
    private TextView mLblMedicineCategoryNameValue;
    private TextView mLblMedicineCategoryNoteValue;
    private ImageView mImgMedicinePicture;
    private Button mBtnBack;
    private ZoomableImageView mImgZoomableMedicineImage;

    private IPresenterMedicineInformation mPresenter;

    private long mRowId;
    private String mMedicinePictureFilePath;
    private Bitmap mFullMedicinePicture;
    private TakenMedicineExtended mModel;

    @Override
    public boolean onBackPressed() {
        if (this.mImgZoomableMedicineImage.isOpen()) {
            this.mImgZoomableMedicineImage.zoomImageToThumbnail(this.mImgMedicinePicture);
        } else {
            this.buttonBackClick();
        }
        return true;
    }

    @Override
    public void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        this.mPresenter = (IPresenterMedicineInformation)PresenterFactory.createPresenter(IPresenterMedicineInformation.class, this);
        this.mPresenter.select(this.mRowId);
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(Constants.BUNDLE_KEY__ROW_ID, this.mRowId);
    }

    @Override
    protected void retrieveArguments(Bundle pArguments) {
        this.mRowId = pArguments.getLong(Constants.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(Constants.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_information, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mLblMedicineNameValue = (TextView)pView.findViewById(R.id.lblMedicineNameValue);
        this.mLblMedicineNoteValue = (TextView)pView.findViewById(R.id.lblMedicineNoteValue);
        this.mLblMedicineCategoryNameValue = (TextView)pView.findViewById(R.id.lblMedicineCategoryName);
        this.mLblMedicineCategoryNoteValue = (TextView)pView.findViewById(R.id.lblMedicineCategoryNoteValue);
        this.mImgMedicinePicture = (ImageView)pView.findViewById(R.id.imgMedicinePicture);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
        this.mImgZoomableMedicineImage = (ZoomableImageView)pView.findViewById(R.id.imgZoomableMedicineImage);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.bindModelData(this.mModel);

        if(!TextUtils.isEmpty(this.mMedicinePictureFilePath)) {
            pView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    ScreenMedicineInformation.this.loadMedicinePicture();
                    ScreenMedicineInformation.this.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }

        this.mImgMedicinePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineInformation.this.medicinePictureClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineInformation.this.buttonBackClick();
            }
        });
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_information;
    }

    @Override
    public void bindData(TakenMedicineExtended pModel) {
        this.mModel = pModel;
    }

    private void medicinePictureClick() {
        if(this.mFullMedicinePicture == null && !TextUtils.isEmpty(this.mMedicinePictureFilePath)) {
            this.mFullMedicinePicture = this.mPresenter.loadImage(this.mMedicinePictureFilePath);
        }
        if(this.mFullMedicinePicture != null) {
            this.mImgZoomableMedicineImage.zoomImageFromThumb(this.mImgMedicinePicture, this.mFullMedicinePicture);
            /*
            this.mImgZoomableMedicineImage.zoomImageFromThumb(this.mZoomContainer,
                    this.mImgMedicinePicture,
                    this.mFullMedicinePicture,
                    this.getResources().getInteger(android.R.integer.config_shortAnimTime));
            */
        }
    }

    private void buttonBackClick() {
        this.mScreenActionsListener.onBack();
    }

    private void loadMedicinePicture() {
        Bitmap bitmap = this.mPresenter.loadImage(this.mMedicinePictureFilePath,
                this.mImgMedicinePicture.getWidth(),
                this.mImgMedicinePicture.getHeight());
        if(bitmap != null) {
            this.mImgMedicinePicture.setImageBitmap(bitmap);
        }
    }

    private void bindModelData(TakenMedicineExtended pModel) {
        String medicineName = pModel.getMedicine().getMedicineName();
        if(TextUtils.isEmpty(medicineName)) {
            medicineName = pModel.getFallbackMedicineName();
        }
        String medicineNote = pModel.getMedicine().getMedicineNote();
        String medicineCategoryName = pModel.getMedicine().getMedicineCategory().getMedicineCategoryName();
        String medicineCategoryNote = pModel.getMedicine().getMedicineCategory().getMedicineCategoryNote();

        this.mLblMedicineNameValue.setText(medicineName);
        this.mLblMedicineNoteValue.setText(medicineNote);
        this.mLblMedicineCategoryNameValue.setText(medicineCategoryName);
        this.mLblMedicineCategoryNoteValue.setText(medicineCategoryNote);
        this.mMedicinePictureFilePath = pModel.getMedicine().getMedicineImagePath();
    }
}
