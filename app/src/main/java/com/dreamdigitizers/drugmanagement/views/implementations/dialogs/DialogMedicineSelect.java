package com.dreamdigitizers.drugmanagement.views.implementations.dialogs;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.models.Medicine;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineSelect;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineSelect;

public class DialogMedicineSelect extends DialogBase implements IViewMedicineSelect, DialogInterface.OnDismissListener {
    private Spinner mSelMedicines;
    private EditText mTxtDose;
    private Button mBtnSelect;
    private Button mBtnCancel;

    private IPresenterMedicineSelect mPresenter;
    private IOnDialogButtonClickListener mListener;

    private Medicine mMedicine;

    public DialogMedicineSelect(AppCompatActivity pActivity, IOnDialogButtonClickListener pListener) {
        super(pActivity);
        this.mListener = pListener;
        this.setOnDismissListener(this);
    }

    @Override
    protected int getTitle() {
        return R.string.title__dialog_medicine_select;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog__medicine_select;
    }

    @Override
    protected void retrieveScreenItems() {
        this.mSelMedicines = (Spinner)this.findViewById(R.id.selMedicines);
        this.mTxtDose = (EditText)this.findViewById(R.id.txtDose);
        this.mBtnSelect = (Button)this.findViewById(R.id.btnSelect);
        this.mBtnCancel = (Button)this.findViewById(R.id.btnCancel);
    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mSelMedicines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                DialogMedicineSelect.this.selectMedicine(pPosition, pRowId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                DialogMedicineSelect.this.selectMedicine(0, 0);
            }
        });

        this.mBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMedicineSelect.this.buttonSelectClick();
            }
        });

        this.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMedicineSelect.this.buttonCancelClick();
            }
        });

        this.mPresenter = (IPresenterMedicineSelect)PresenterFactory.createPresenter(IPresenterMedicineSelect.class, this);
    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.mActivity.getSupportLoaderManager();
    }

    @Override
    public void setAdapter(SpinnerAdapter pAdapter) {
        this.mSelMedicines.setAdapter(pAdapter);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        this.mPresenter.close();
    }

    private void selectMedicine(int pPosition, long pRowId) {
        if(pRowId > 0) {
            Cursor cursor = (Cursor)this.mSelMedicines.getItemAtPosition(pPosition);

            this.mMedicine = Medicine.fetchDataAtCurrentPosition(cursor);
        } else {
            this.mMedicine = null;
        }
    }

    private void buttonSelectClick() {
        String dose = this.mTxtDose.getText().toString().trim();
        boolean result = this.mPresenter.checkInputData(this.mMedicine, dose);
        if(result) {
            this.dismiss();
            if(DialogMedicineSelect.this.mListener != null) {
                DialogMedicineSelect.this.mListener.onMedicineSelect(this.mMedicine, dose);
            }
        }
    }

    private void buttonCancelClick() {
        this.dismiss();
        if(this.mListener != null) {
            this.mListener.onCancel();
        }
    }

    public interface IOnDialogButtonClickListener {
        void onMedicineSelect(Medicine pMedicine, String pDose);
        void onCancel();
    }
}
