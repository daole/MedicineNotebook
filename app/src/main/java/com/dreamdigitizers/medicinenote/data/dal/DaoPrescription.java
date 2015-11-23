package com.dreamdigitizers.medicinenote.data.dal;

import android.database.Cursor;

import com.dreamdigitizers.medicinenote.data.DatabaseHelper;
import com.dreamdigitizers.medicinenote.data.dal.tables.Table;
import com.dreamdigitizers.medicinenote.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.medicinenote.data.dal.tables.TablePrescription;

import java.util.Arrays;
import java.util.List;

public class DaoPrescription extends Dao {
    private static final String JOIN_CLAUSE;
    static {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(TablePrescription.TABLE_NAME);
        stringBuilder.append(" LEFT JOIN ");
        stringBuilder.append(TableFamilyMember.TABLE_NAME);
        stringBuilder.append(" ON ");
        stringBuilder.append(TablePrescription.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(TablePrescription.COLUMN_NAME__FAMILY_MEMBER_ID);
        stringBuilder.append(" = ");
        stringBuilder.append(TableFamilyMember.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(Table.COLUMN_NAME__ID);

        JOIN_CLAUSE = stringBuilder.toString();
    }

    public DaoPrescription(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    public Cursor select(String[] pProjection, String pWhereClause, String[] pWhereArgs, String pGroupBy, String pHaving, String pSortOrder, boolean pIsCloseOnEnd) {
        this.mSQLiteQueryBuilder.setTables(DaoPrescription.JOIN_CLAUSE);
        Cursor cursor = this.mDatabaseHelper.select(this.mSQLiteQueryBuilder, pProjection, pWhereClause, pWhereArgs, pGroupBy, pHaving, pSortOrder, null, false, pIsCloseOnEnd);
        return  cursor;
    }

    @Override
    public String getTableName() {
        return TablePrescription.TABLE_NAME;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TablePrescription.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
