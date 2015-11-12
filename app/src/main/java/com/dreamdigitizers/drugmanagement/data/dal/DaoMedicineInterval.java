package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;

import java.util.Arrays;
import java.util.List;

public class DaoMedicineInterval extends Dao {
    public DaoMedicineInterval(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    public String getTableName() {
        return TableMedicineInterval.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableMedicineInterval.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
