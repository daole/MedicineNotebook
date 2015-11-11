package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;

import java.util.Arrays;
import java.util.List;

public class DaoAlarm extends Dao {
    public DaoAlarm(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return TableAlarm.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableAlarm.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
