package com.dreamdigitizers.drugmanagement.data.models;

public class FamilyMember extends Model {
    private String mFamilyMemberName;

    public String getFamilyMemberName() {
        return this.mFamilyMemberName;
    }

    public void setFamilyMemberName(String pFamilyMemberName) {
        this.mFamilyMemberName = pFamilyMemberName;
    }
}
