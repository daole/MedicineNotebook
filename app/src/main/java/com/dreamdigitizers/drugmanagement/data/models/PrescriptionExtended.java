package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionExtended extends Prescription {
    private FamilyMember mFamilyMember;

    public PrescriptionExtended() {

    }

    public PrescriptionExtended(Prescription pPrescription) {
        this.setRowId(pPrescription.getRowId());
        this.setFamilyMemberId(pPrescription.getFamilyMemberId());
        this.setPrescriptionName(pPrescription.getPrescriptionName());
        this.setPrescriptionDate(pPrescription.getPrescriptionDate());
        this.setImagePath(pPrescription.getImagePath());
        this.setPrescriptionNote(pPrescription.getPrescriptionNote());
    }

    public FamilyMember getFamilyMember() {
        return this.mFamilyMember;
    }

    public void setFamilyMember(FamilyMember pFamilyMember) {
        this.mFamilyMember = pFamilyMember;
    }

    public static List<PrescriptionExtended> fetchExtendedData(Cursor pCursor) {
        List<PrescriptionExtended> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                PrescriptionExtended model = PrescriptionExtended.fetchExtendedDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static PrescriptionExtended fetchExtendedDataAtCurrentPosition(Cursor pCursor) {
        return PrescriptionExtended.fetchExtendedDataAtCurrentPosition(pCursor, 0);
    }

    public static PrescriptionExtended fetchExtendedDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        PrescriptionExtended model = null;

        if(pCursor != null) {
            Prescription prescription = Prescription.fetchDataAtCurrentPosition(pCursor, pRowId);
            FamilyMember familyMember = FamilyMember.fetchDataAtCurrentPosition(pCursor, prescription.getFamilyMemberId());

            model = new PrescriptionExtended(prescription);
            model.setFamilyMember(familyMember);
        }

        return  model;
    }
}
