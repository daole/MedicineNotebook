package com.dreamdigitizers.medicinenote.views.implementations.fragments.screens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.dreamdigitizers.medicinenote.Constants;
import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineAdd;
import com.dreamdigitizers.medicinenote.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineAdd;
import com.dreamdigitizers.medicinenote.views.implementations.activities.ActivityCamera;
import com.dreamdigitizers.medicinenote.views.implementations.customviews.ZoomableImageView;

public class ScreenMedicineAdd extends Screen implements IViewMedicineAdd {
    private View mZoomContainer;
    private EditText mTxtMedicineName;
    private Spinner mSelMedicineCategories;
    private ImageButton mBtnAddMedicineCategory;
    private ImageView mImgMedicinePicture;
    private ImageButton mBtnAddImage;
    private EditText mTxtMedicineNote;
    private Button mBtnAdd;
    private Button mBtnBack;
    private ZoomableImageView mImgZoomableMedicineImage;

    private IPresenterMedicineAdd mPresenter;

    private long mMedicineCategoryId;
    private Bitmap mFullMedicinePicture;
    private String mMedicinePictureFilePath;
    private SpinnerAdapter mAdapter;

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
        this.mPresenter = (IPresenterMedicineAdd)PresenterFactory.createPresenter(IPresenterMedicineAdd.class, this);
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putString(Constants.BUNDLE_KEY__MEDICINE_PICTURE_FILE_PATH, this.mMedicinePictureFilePath);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mMedicinePictureFilePath = pSavedInstanceState.getString(Constants.BUNDLE_KEY__MEDICINE_PICTURE_FILE_PATH);
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
    public void onDestroy() {
        super.onDestroy();
        if(!this.mIsRecoverable) {
            this.deleteMedicinePicture();
        }
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mZoomContainer = pView.findViewById(R.id.zoomContainer);
        this.mTxtMedicineName = (EditText)pView.findViewById(R.id.txtMedicineName);
        this.mSelMedicineCategories = (Spinner)pView.findViewById(R.id.selMedicineCategories);
        this.mBtnAddMedicineCategory = (ImageButton)pView.findViewById(R.id.btnAddMedicineCategory);
        this.mImgMedicinePicture = (ImageView)pView.findViewById(R.id.imgMedicinePicture);
        this.mBtnAddImage = (ImageButton)pView.findViewById(R.id.btnAddImage);
        this.mTxtMedicineNote = (EditText)pView.findViewById(R.id.txtMedicineNote);
        this.mBtnAdd = (Button)pView.findViewById(R.id.btnAdd);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
        this.mImgZoomableMedicineImage = (ZoomableImageView)pView.findViewById(R.id.imgZoomableMedicineImage);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        if(!TextUtils.isEmpty(this.mMedicinePictureFilePath)) {
            pView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    ScreenMedicineAdd.this.loadMedicinePicture();
                    ScreenMedicineAdd.this.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }

        this.mSelMedicineCategories.setAdapter(this.mAdapter);
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

        this.mImgMedicinePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineAdd.this.medicinePictureClick();
            }
        });

        this.mBtnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineAdd.this.buttonAddImageClick();
            }
        });

        this.mBtnAddMedicineCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenMedicineAdd.this.buttonAddMedicineCategoryClick();
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
        this.mTxtMedicineName.setText("");
        this.mImgMedicinePicture.setImageBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.icon__no_photo));
        this.mSelMedicineCategories.setSelection(0);
        this.mTxtMedicineNote.setText("");
        this.mMedicinePictureFilePath = null;
    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.getLoaderManager();
    }

    @Override
    public void setAdapter(SpinnerAdapter pAdapter) {
        this.mAdapter = pAdapter;
    }

    private void selectMedicineCategory(long pRowId) {
        this.mMedicineCategoryId = pRowId;
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

    private void buttonAddImageClick() {
        Bundle extras = new Bundle();
        extras.putInt(Constants.BUNDLE_KEY__IMAGE_TYPE, Constants.IMAGE_TYPE__MEDICINE);
        extras.putBoolean(Constants.BUNDLE_KEY__IS_CROPPED, true);
        Intent intent = new Intent(this.getContext(), ActivityCamera.class);
        intent.putExtra(Constants.INTENT_EXTRA_KEY__DATA, extras);
        this.startActivityForResult(intent, Constants.REQUEST_CODE__CAMERA);
    }

    private void buttonAddMedicineCategoryClick() {
        this.goToMedicineCategoryAddScreen();
    }

    private void buttonAddClick() {
        String medicineName = this.mTxtMedicineName.getText().toString().trim();
        String medicineNote = this.mTxtMedicineNote.getText().toString().trim();
        this.mPresenter.insert(medicineName, this.mMedicineCategoryId, this.mMedicinePictureFilePath, medicineNote);
    }

    private void buttonBackClick() {
        this.deleteMedicinePicture();
        this.mScreenActionsListener.onBack();
    }

    private void deleteMedicinePicture() {
        this.mPresenter.deleteImage(this.mMedicinePictureFilePath);
    }

    private void loadMedicinePicture() {
        Bitmap bitmap = this.mPresenter.loadImage(this.mMedicinePictureFilePath,
                this.mImgMedicinePicture.getWidth(),
                this.mImgMedicinePicture.getHeight());
        if(bitmap != null) {
            this.mImgMedicinePicture.setImageBitmap(bitmap);
        }
    }

    private void goToMedicineCategoryAddScreen() {
        Screen screen = new ScreenMedicineCategoryAdd();
        this.mScreenActionsListener.onChangeScreen(screen);
    }
}
