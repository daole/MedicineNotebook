package com.dreamdigitizers.medicinenote.data.dal;

import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicine;

import java.util.Arrays;
import java.util.List;

public class DaoMedicine extends Dao {
    public DaoMedicine(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    public String getTableName() {
        return TableMedicine.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableMedicine.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
