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

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.models.Medicine;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineEdit;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineEdit;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.ActivityCamera;

public class ScreenMedicineEdit extends Screen implements IViewMedicineEdit {
    private static final String BUNDLE_KEY__MEDICINE_CATEGORY_ID = "category_id";
    private static final String BUNDLE_KEY__MEDICINE_PICTURE_FILE_PATH = "medicine_picture";
    private static final String BUNDLE_KEY__OLD_MEDICINE_PICTURE_FILE_PATH = "old_medicine_picture";

    private EditText mTxtMedicineName;
    private Spinner mSelMedicineCategories;
    private ImageButton mBtnAddMedicineCategory;
    private ImageView mImgMedicinePicture;
    private EditText mTxtMedicineNote;
    private Button mBtnEdit;
    private Button mBtnBack;

    private IPresenterMedicineEdit mPresenter;

    private long mRowId;
    private long mMedicineCategoryId;
    private String mMedicinePictureFilePath;
    private String mOldMedicinePictureFilePath;

    @Override
    public boolean onBackPressed() {
        this.buttonBackClick();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putLong(Screen.BUNDLE_KEY__ROW_ID, this.mRowId);
        pOutState.putLong(ScreenMedicineEdit.BUNDLE_KEY__MEDICINE_CATEGORY_ID, this.mMedicineCategoryId);
        pOutState.putString(ScreenMedicineEdit.BUNDLE_KEY__MEDICINE_PICTURE_FILE_PATH, this.mMedicinePictureFilePath);
        pOutState.putString(ScreenMedicineEdit.BUNDLE_KEY__OLD_MEDICINE_PICTURE_FILE_PATH, this.mOldMedicinePictureFilePath);
    }

    @Override
    protected void retrieveArguments(Bundle pArguments) {
        this.mRowId = pArguments.getLong(Screen.BUNDLE_KEY__ROW_ID);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {
        this.mRowId = pSavedInstanceState.getLong(Screen.BUNDLE_KEY__ROW_ID);
        this.mMedicineCategoryId = pSavedInstanceState.getLong(ScreenMedicineEdit.BUNDLE_KEY__MEDICINE_CATEGORY_ID);
        this.mMedicinePictureFilePath = pSavedInstanceState.getString(ScreenMedicineEdit.BUNDLE_KEY__MEDICINE_PICTURE_FILE_PATH);
        this.mOldMedicinePictureFilePath = pSavedInstanceState.getString(ScreenMedicineEdit.BUNDLE_KEY__OLD_MEDICINE_PICTURE_FILE_PATH);
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
        View rootView = pInflater.inflate(R.layout.screen__medicine_edit, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mTxtMedicineName = (EditText)pView.findViewById(R.id.txtMedicineName);
        this.mSelMedicineCategories = (Spinner)pView.findViewById(R.id.selMedicineCategories);
        this.mBtnAddMedicineCategory = (ImageButton)pView.findViewById(R.id.btnAddMedicineCategory);
        this.mImgMedicinePicture = (ImageView)pView.findViewById(R.id.imgMedicinePicture);
        this.mTxtMedicineNote = (EditText)pView.findViewById(R.id.txtMedicineNote);
        this.mBtnEdit = (Button)pView.findViewById(R.id.btnEdit);
        this.mBtnBack = (Button)pView.findViewById(R.id.btnBack);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        pView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                ScreenMedicineEdit.this.loadMedicinePicture();
                ScreenMedicineEdit.this.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        this.mSelMedicineCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenMedicineEdit.this.selectMedicineCategory(pRowId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ScreenMedicineEdit.this.selectMedicineCategory(0);
            }
        });

        this.mBtnAddMedicineCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenMedicineEdit.this.buttonAddMedicineCategoryClick();
            }
        });

        this.mImgMedicinePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineEdit.this.medicinePictureClick();
            }
        });

        this.mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineEdit.this.buttonEditClick();
            }
        });

        this.mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ScreenMedicineEdit.this.buttonBackClick();
            }
        });

        this.mPresenter = (IPresenterMedicineEdit) PresenterFactory.createPresenter(IPresenterMedicineEdit.class, this);
        this.mPresenter.select(this.mRowId);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_edit;
    }

    @Override
    public void bindData(Medicine pModel) {
        this.mTxtMedicineName.setText(pModel.getMedicineName());
        this.bindMedicineCategoryId(pModel.getMedicineCategoryId());
        this.mOldMedicinePictureFilePath = pModel.getMedicineImagePath();
        this.mMedicinePictureFilePath = this.mOldMedicinePictureFilePath;
        this.loadMedicinePicture();
        this.mTxtMedicineNote.setText(pModel.getMedicineNote());
    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.getLoaderManager();
    }

    @Override
    public void setAdapter(SpinnerAdapter pAdapter) {
        this.mSelMedicineCategories.setAdapter(pAdapter);
    }

    @Override
    public void onDataEdited() {
        this.deleteOldMedicinePicture();
    }

    private void selectMedicineCategory(long pRowId) {
        this.mMedicineCategoryId = pRowId;
    }

    private void medicinePictureClick() {
        Intent intent = new Intent(ScreenMedicineEdit.this.getContext(), ActivityCamera.class);
        ScreenMedicineEdit.this.startActivityForResult(intent, Constants.REQUEST_CODE__CAMERA);
    }

    private void buttonAddMedicineCategoryClick() {
        this.goToMedicineCategoryAddScreen();
    }

    private void buttonEditClick() {
        String medicineName = this.mTxtMedicineName.getText().toString().trim();
        String medicineNote = this.mTxtMedicineNote.getText().toString().trim();
        this.mPresenter.edit(this.mRowId, medicineName, this.mMedicineCategoryId, this.mMedicinePictureFilePath, medicineNote);
    }

    private void buttonBackClick() {
        this.deleteMedicinePicture();
        this.mScreenActionsListener.onBack();
    }

    private void bindMedicineCategoryId(long pMedicineCategoryId) {
        for(int i = 0; i < this.mSelMedicineCategories.getCount(); i++) {
            Cursor cursor = (Cursor)this.mSelMedicineCategories.getItemAtPosition(i);
            long rowId = cursor.getLong(cursor.getColumnIndex(Table.COLUMN_NAME__ID));
            if(rowId == pMedicineCategoryId) {
                this.mSelMedicineCategories.setSelection(i);
                break;
            }
        }
    }

    private void deleteOldMedicinePicture() {
        if(!TextUtils.isEmpty(this.mMedicinePictureFilePath) && !this.mMedicinePictureFilePath.equals(this.mOldMedicinePictureFilePath)) {
            this.mPresenter.deleteImage(this.mOldMedicinePictureFilePath);
            this.mOldMedicinePictureFilePath = this.mMedicinePictureFilePath;
        }
    }

    private void deleteMedicinePicture() {
        if(!TextUtils.isEmpty(this.mMedicinePictureFilePath) && !this.mMedicinePictureFilePath.equals(this.mOldMedicinePictureFilePath)) {
            this.mPresenter.deleteImage(this.mMedicinePictureFilePath);
        }
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
