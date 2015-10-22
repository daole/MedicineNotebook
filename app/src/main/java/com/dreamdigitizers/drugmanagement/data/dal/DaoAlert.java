package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlert;

import java.util.Arrays;
import java.util.List;

public class DaoAlert extends Dao {
    public DaoAlert(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return TableAlert.TABLE_NAME;
    }

    @Override
    protected boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableAlert.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
