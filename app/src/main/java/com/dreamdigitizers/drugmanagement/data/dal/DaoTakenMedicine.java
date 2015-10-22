package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;

import java.util.Arrays;
import java.util.List;

public class DaoTakenMedicine extends Dao {
    public DaoTakenMedicine(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return TableTakenMedicine.TABLE_NAME;
    }

    @Override
    protected boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableTakenMedicine.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
