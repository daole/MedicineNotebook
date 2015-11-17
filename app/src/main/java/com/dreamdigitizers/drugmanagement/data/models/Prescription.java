package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TablePrescription;

import java.util.ArrayList;
import java.util.List;

public class Prescription extends Model {
    private long mFamilyMemberId;
    private String mPrescriptionName;
    private String mPrescriptionDate;
    private String mImagePath;
    private String mPrescriptionNote;

    public long getFamilyMemberId() {
        return this.mFamilyMemberId;
    }

    public void setFamilyMemberId(long pFamilyMemberId) {
        this.mFamilyMemberId = pFamilyMemberId;
    }

    public String getPrescriptionName() {
        return this.mPrescriptionName;
    }

    public void setPrescriptionName(String pPrescriptionName) {
        this.mPrescriptionName = pPrescriptionName;
    }

    public String getPrescriptionDate() {
        return this.mPrescriptionDate;
    }

    public void setPrescriptionDate(String pPrescriptionDate) {
        this.mPrescriptionDate = pPrescriptionDate;
    }

    public String getImagePath() {
        return this.mImagePath;
    }

    public void setImagePath(String pImagePath) {
        this.mImagePath = pImagePath;
    }

    public String getPrescriptionNote() {
        return this.mPrescriptionNote;
    }

    public void setPrescriptionNote(String pPrescriptionNote) {
        this.mPrescriptionNote = pPrescriptionNote;
    }

    public static List<Prescription> fetchData(Cursor pCursor) {
        List<Prescription> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                Prescription model = Prescription.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static Prescription fetchDataAtCurrentPosition(Cursor pCursor) {
        return Prescription.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static Prescription fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        Prescription model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
            }
            long familyMemberId = pCursor.getLong(pCursor.getColumnIndex(TablePrescription.COLUMN_NAME__FAMILY_MEMBER_ID));
            String prescriptionName = pCursor.getString(pCursor.getColumnIndex(TablePrescription.COLUMN_NAME__PRESCRIPTION_NAME));
            String prescriptionDate = pCursor.getString(pCursor.getColumnIndex(TablePrescription.COLUMN_NAME__PRESCRIPTION_DATE));
            String imagePath = pCursor.getString(pCursor.getColumnIndex(TablePrescription.COLUMN_NAME__IMAGE_PATH));
            String prescriptionNote = pCursor.getString(pCursor.getColumnIndex(TablePrescription.COLUMN_NAME__PRESCRIPTION_NOTE));

            model = new Prescription();
            model.setRowId(rowId);
            model.setFamilyMemberId(familyMemberId);
            model.setPrescriptionName(prescriptionName);
            model.setPrescriptionDate(prescriptionDate);
            model.setPrescriptionNote(prescriptionNote);
            model.setImagePath(imagePath);
        }

        return  model;
    }
}
