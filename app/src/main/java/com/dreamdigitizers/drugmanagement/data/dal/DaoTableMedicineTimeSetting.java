package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTimeSetting;

import java.util.Arrays;
import java.util.List;

public class DaoTableMedicineTimeSetting extends Dao {
    public DaoTableMedicineTimeSetting(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return TableMedicineTimeSetting.TABLE_NAME;
    }

    @Override
    protected boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableMedicineTimeSetting.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
