package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.Dao;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;

import java.util.Arrays;
import java.util.List;

public class DaoFamilyMember extends Dao {
    public DaoFamilyMember(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return TableFamilyMember.TABLE_NAME;
    }

    @Override
    protected boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableFamilyMember.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
