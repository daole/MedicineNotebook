package com.dreamdigitizers.medicinenote.data.dal;

import android.database.Cursor;

import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.Table;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicine;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableTakenMedicine;

import java.util.Arrays;
import java.util.List;

public class DaoTakenMedicine extends Dao {
    private static final String JOIN_CLAUSE;
    static {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(TableTakenMedicine.TABLE_NAME);
        stringBuilder.append(" LEFT JOIN ");
        stringBuilder.append(TableMedicine.TABLE_NAME);
        stringBuilder.append(" ON ");
        stringBuilder.append(TableTakenMedicine.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(TableTakenMedicine.COLUMN_NAME__MEDICINE_ID);
        stringBuilder.append(" = ");
        stringBuilder.append(TableMedicine.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(Table.COLUMN_NAME__ID);

        stringBuilder.append(" LEFT JOIN ");
        stringBuilder.append(TableMedicineCategory.TABLE_NAME);
        stringBuilder.append(" ON ");
        stringBuilder.append(TableMedicine.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(TableMedicine.COLUMN_NAME__MEDICINE_CATEGORY_ID);
        stringBuilder.append(" = ");
        stringBuilder.append(TableMedicineCategory.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(Table.COLUMN_NAME__ID);

        JOIN_CLAUSE = stringBuilder.toString();
    }

    public DaoTakenMedicine(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    public Cursor select(String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, boolean pIsCloseOnEnd) {
        this.mSQLiteQueryBuilder.setTables(DaoTakenMedicine.JOIN_CLAUSE);
        Cursor cursor = this.mDatabaseHelper.select(this.mSQLiteQueryBuilder, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, null, false, pIsCloseOnEnd);
        return  cursor;
    }

    @Override
    public String getTableName() {
        return TableTakenMedicine.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableTakenMedicine.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
