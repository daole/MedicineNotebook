package com.dreamdigitizers.medicinenote.data.dal;

import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicineTime;

import java.util.Arrays;
import java.util.List;

public class DaoMedicineTime extends Dao {
    public DaoMedicineTime(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    public String getTableName() {
        return TableMedicineTime.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableMedicineTime.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
