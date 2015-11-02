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
                long rowId = pCursor.getLong(Table.COLUMN_INDEX__ID);
                String familyMemberName = pCursor.getString(TableFamilyMember.COLUMN_INDEX__FAMILY_MEMBER_NAME);

                FamilyMember model = new FamilyMember();
                model.setId(rowId);
                model.setFamilyMemberName(familyMemberName);

                list.add(model);
            } while (pCursor.moveToNext());
        }

        return list;
    }
}
