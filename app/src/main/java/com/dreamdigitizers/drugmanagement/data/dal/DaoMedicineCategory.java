package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;

import java.util.Arrays;
import java.util.List;

public class DaoMedicineCategory extends Dao {
    public DaoMedicineCategory(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return TableMedicineCategory.TABLE_NAME;
    }

    @Override
    protected boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableMedicineCategory.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
