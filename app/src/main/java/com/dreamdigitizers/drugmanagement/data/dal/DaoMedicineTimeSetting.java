package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTimeSetting;

import java.util.Arrays;
import java.util.List;

public class DaoMedicineTimeSetting extends Dao {
    public DaoMedicineTimeSetting(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return TableMedicineTimeSetting.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableMedicineTimeSetting.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
