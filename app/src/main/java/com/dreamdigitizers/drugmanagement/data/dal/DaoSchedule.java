package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableSchedule;

import java.util.Arrays;
import java.util.List;

public class DaoSchedule extends Dao {
    public DaoSchedule(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return TableSchedule.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableSchedule.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
