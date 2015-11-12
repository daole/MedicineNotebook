package com.dreamdigitizers.drugmanagement.data.dal;

import com.dreamdigitizers.drugmanagement.data.DatabaseHelper;
import com.dreamdigitizers.drugmanagement.data.dal.tables.Table;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableAlarm;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineCategory;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableSchedule;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;

import java.util.Arrays;
import java.util.List;

public class DaoAlarm extends Dao {
    private static final String JOIN_CLAUSE;
    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TableAlarm.TABLE_NAME);
        stringBuilder.append(" JOIN ");
        stringBuilder.append(TableSchedule.TABLE_NAME);
        stringBuilder.append(" ON ");
        stringBuilder.append(TableAlarm.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(TableAlarm.COLUMN_NAME__SCHEDULE_ID);
        stringBuilder.append(" = ");
        stringBuilder.append(TableSchedule.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(Table.COLUMN_NAME__ID);

        stringBuilder.append(" JOIN ");
        stringBuilder.append(TableTakenMedicine.TABLE_NAME);
        stringBuilder.append(" ON ");
        stringBuilder.append(TableTakenMedicine.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(TableTakenMedicine.COLUMN_NAME__SCHEDULE_ID);
        stringBuilder.append(" = ");
        stringBuilder.append(TableSchedule.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(Table.COLUMN_NAME__ID);

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

        stringBuilder.append(" LEFT JOIN ");
        stringBuilder.append(TableFamilyMember.TABLE_NAME);
        stringBuilder.append(" ON ");
        stringBuilder.append(TableSchedule.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(TableSchedule.COLUMN_NAME__FAMILY_MEMBER_ID);
        stringBuilder.append(" = ");
        stringBuilder.append(TableFamilyMember.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(Table.COLUMN_NAME__ID);

        stringBuilder.append(" LEFT JOIN ");
        stringBuilder.append(TableMedicineTime.TABLE_NAME);
        stringBuilder.append(" ON ");
        stringBuilder.append(TableSchedule.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(TableSchedule.COLUMN_NAME__MEDICINE_TIME_ID);
        stringBuilder.append(" = ");
        stringBuilder.append(TableMedicineTime.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(Table.COLUMN_NAME__ID);

        stringBuilder.append(" LEFT JOIN ");
        stringBuilder.append(TableMedicineInterval.TABLE_NAME);
        stringBuilder.append(" ON ");
        stringBuilder.append(TableSchedule.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(TableSchedule.COLUMN_NAME__MEDICINE_INTERVAL_ID);
        stringBuilder.append(" = ");
        stringBuilder.append(TableMedicineInterval.TABLE_NAME);
        stringBuilder.append(".");
        stringBuilder.append(Table.COLUMN_NAME__ID);

        JOIN_CLAUSE = stringBuilder.toString();
    }

    public DaoAlarm(DatabaseHelper pDatabaseHelper) {
        super(pDatabaseHelper);
    }

    @Override
    protected String getTableName() {
        return DaoAlarm.JOIN_CLAUSE;
    }

    @Override
    public boolean checkColumns(String[] pProjection) {
        if(pProjection == null) {
            return false;
        }
        List<String> columns = TableAlarm.getColumns();
        return columns.containsAll(Arrays.asList(pProjection));
    }
}
