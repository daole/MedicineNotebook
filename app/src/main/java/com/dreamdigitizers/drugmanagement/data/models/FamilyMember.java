package com.dreamdigitizers.drugmanagement.data.models;

import android.database.Cursor;

import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;

import java.util.ArrayList;
import java.util.List;

public class FamilyMember extends Model {
    private String mFamilyMemberName;

    public String getFamilyMemberName() {
        return this.mFamilyMemberName;
    }

    public void setFamilyMemberName(String pFamilyMemberName) {
        this.mFamilyMemberName = pFamilyMemberName;
    }

    public static List<FamilyMember> fetchData(Cursor pCursor) {
        List<FamilyMember> list = new ArrayList<>();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                FamilyMember model = FamilyMember.fetchDataAtCurrentPosition(pCursor);
                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }

    public static FamilyMember fetchDataAtCurrentPosition(Cursor pCursor) {
        return FamilyMember.fetchDataAtCurrentPosition(pCursor, 0);
    }

    public static FamilyMember fetchDataAtCurrentPosition(Cursor pCursor, long pRowId) {
        FamilyMember model = null;

        if(pCursor != null) {
            long rowId = pRowId;
            if(rowId <= 0) {
                rowId = pCursor.getLong(pCursor.getColumnIndex(Table.COLUMN_NAME__ID));
            }
            String familyMemberName = pCursor.getString(pCursor.getColumnIndex(TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME));

            model = new FamilyMember();
            model.setRowId(rowId);
            model.setFamilyMemberName(familyMemberName);
        }

        return  model;
    }
}
