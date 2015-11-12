package com.dreamdigitizers.drugmanagement.data.dal.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableAlarm extends Table {
    public static final String TABLE_NAME = "alarm";

    public static final String COLUMN_NAME__SCHEDULE_ID = "schedule_id";
    public static final String COLUMN_NAME__ALARM_YEAR = "alarm_year";
    public static final String COLUMN_NAME__ALARM_MONTH = "alarm_month";
    public static final String COLUMN_NAME__ALARM_DATE = "alarm_date";
    public static final String COLUMN_NAME__ALARM_HOUR = "alarm_hour";
    public static final String COLUMN_NAME__ALARM_MINUTE = "alarm_minute";
    public static final String COLUMN_NAME__IS_DONE = "is_done";

    public static final int COLUMN_INDEX__SCHEDULE_ID = 1;
    public static final int COLUMN_INDEX__ALARM_YEAR = 2;
    public static final int COLUMN_INDEX__ALARM_MONTH = 3;
    public static final int COLUMN_INDEX__ALARM_DATE = 4;
    public static final int COLUMN_INDEX__ALARM_HOUR = 5;
    public static final int COLUMN_INDEX__ALARM_MINUTE = 6;
    public static final int COLUMN_INDEX__IS_DONE = 7;

    private static final String STATEMENT__CREATE = "CREATE TABLE `" + TableAlarm.TABLE_NAME + "` ("
        + "`" + Table.COLUMN_NAME__ID + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
        + "`" + TableAlarm.COLUMN_NAME__SCHEDULE_ID + "` INTEGER NOT NULL,"
        + "`" + TableAlarm.COLUMN_NAME__ALARM_YEAR + "` INTEGER NOT NULL,"
        + "`" + TableAlarm.COLUMN_NAME__ALARM_MONTH + "` INTEGER NOT NULL,"
        + "`" + TableAlarm.COLUMN_NAME__ALARM_DATE + "` INTEGER NOT NULL,"
        + "`" + TableAlarm.COLUMN_NAME__ALARM_HOUR + "` INTEGER NOT NULL,"
        + "`" + TableAlarm.COLUMN_NAME__ALARM_MINUTE + "` INTEGER NOT NULL,"
        + "`" + TableAlarm.COLUMN_NAME__IS_DONE + "` INTEGER NOT NULL DEFAULT '0'"
        + ");";

    private static final String STATEMENT__UPDATE = "";

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<String>();

        columns.add(Table.COLUMN_NAME__ID);
        columns.add(TableAlarm.COLUMN_NAME__SCHEDULE_ID);
        columns.add(TableAlarm.COLUMN_NAME__ALARM_YEAR);
        columns.add(TableAlarm.COLUMN_NAME__ALARM_MONTH);
        columns.add(TableAlarm.COLUMN_NAME__ALARM_DATE);
        columns.add(TableAlarm.COLUMN_NAME__ALARM_HOUR);
        columns.add(TableAlarm.COLUMN_NAME__ALARM_MINUTE);
        columns.add(TableAlarm.COLUMN_NAME__IS_DONE);

        return columns;
    }

    public static List<String> getColumnsForJoin() {
        List<String> columns = TableAlarm.getColumns();

        List<String> scheduleColumns = TableSchedule.getColumns();
        scheduleColumns.remove(Table.COLUMN_NAME__ID);
        columns.addAll(scheduleColumns);

        List<String> takenMedicineColumns = TableTakenMedicine.getColumns();
        takenMedicineColumns.remove(Table.COLUMN_NAME__ID);
        columns.add(TableTakenMedicine.COLUMN_NAME__ID);
        columns.addAll(takenMedicineColumns);

        List<String> medicineColumns = TableMedicine.getColumns();
        medicineColumns.remove(Table.COLUMN_NAME__ID);
        columns.addAll(medicineColumns);

        List<String> medicineCategoryColumns = TableMedicineCategory.getColumns();
        medicineCategoryColumns.remove(Table.COLUMN_NAME__ID);
        columns.addAll(medicineCategoryColumns);

        List<String> familyMemberColumns = TableFamilyMember.getColumns();
        familyMemberColumns.remove(Table.COLUMN_NAME__ID);
        columns.addAll(familyMemberColumns);

        List<String> medicineTimeColumns = TableMedicineTime.getColumns();
        medicineTimeColumns.remove(Table.COLUMN_NAME__ID);
        columns.addAll(medicineTimeColumns);

        List<String> medicineIntervalColumns = TableMedicineInterval.getColumns();
        medicineIntervalColumns.remove(Table.COLUMN_NAME__ID);
        columns.addAll(medicineIntervalColumns);

        return  columns;
    }

    public static void onCreate(Context pContext, SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableAlarm.STATEMENT__CREATE);
    }

    public static void onUpgrade(Context pContext, SQLiteDatabase pSQLiteDatabase, int pOldVersion, int pNewVersion) {

    }
}
