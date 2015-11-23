package com.dreamdigitizers.medicinenote.data.dal;

import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicineCategory;

import java.util.Arrays;
import java.util.List;

public class DaoMedicineCategory extends Dao {
    public DaoMedicineCategory(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    public String getTableName() {
        return TableMedicineCategory.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableMedicineCategory.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
