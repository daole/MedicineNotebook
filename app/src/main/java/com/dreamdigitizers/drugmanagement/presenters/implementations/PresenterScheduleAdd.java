package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import com.dreamdigitizers.drugmanagement.Constants;
import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineInterval;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableMedicineTime;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleAdd;

class PresenterScheduleAdd implements IPresenterScheduleAdd {
    private static final int LOADER_ID__FAMILY_MEMBER = 0;
    //private static final int LOADER_ID__MEDICINE = 1;
    private static final int LOADER_ID__MEDICINE_TIME = 1;
    private static final int LOADER_ID__MEDICINE_INTERVAL = 2;

    private IViewScheduleAdd mView;
    private SimpleCursorAdapter mFamilyMemberAdapter;
    private SimpleCursorAdapter mMedicineAdapter;
    private SimpleCursorAdapter mMedicineTimeAdapter;
    private SimpleCursorAdapter mMedicineIntervalAdapter;

    public PresenterScheduleAdd(IViewScheduleAdd pView) {
        this.mView = pView;
        this.mView.getViewLoaderManager().initLoader(PresenterScheduleAdd.LOADER_ID__FAMILY_MEMBER, null, this);
        //this.mView.getViewLoaderManager().initLoader(PresenterScheduleAdd.LOADER_ID__MEDICINE, null, this);
        this.mView.getViewLoaderManager().initLoader(PresenterScheduleAdd.LOADER_ID__MEDICINE_TIME, null, this);
        this.mView.getViewLoaderManager().initLoader(PresenterScheduleAdd.LOADER_ID__MEDICINE_INTERVAL, null, this);
        this.createFamilyMemberAdapter();
        //this.createMedicineAdapter();
        this.createMedicineTimeAdapter();
        this.createMedicineIntervalAdapter();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        CursorLoader cursorLoader;
        Uri contentUri;

        switch(pId) {
            case PresenterScheduleAdd.LOADER_ID__FAMILY_MEMBER:
                projection = TableFamilyMember.getColumns().toArray(projection);
                contentUri = MedicineContentProvider.CONTENT_URI__FAMILY_MEMBER;
                break;
            /*
            case PresenterScheduleAdd.LOADER_ID__MEDICINE:
                projection = TableMedicine.getColumns().toArray(projection);
                contentUri = MedicineContentProvider.CONTENT_URI__MEDICINE;
                break;
            */
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_TIME:
                projection = TableMedicineTime.getColumns().toArray(projection);
                contentUri = MedicineContentProvider.CONTENT_URI__MEDICINE_TIME;
                break;
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_INTERVAL:
                contentUri = MedicineContentProvider.CONTENT_URI__MEDICINE_INTERVAL;
                projection = TableMedicineInterval.getColumns().toArray(projection);
                break;
            default:
                return null;
        }

        cursorLoader = new CursorLoader(this.mView.getViewContext(), contentUri, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> pLoader, Cursor pData) {
        String[] projection = new String[0];
        MatrixCursor extras;
        Cursor cursor;
        SimpleCursorAdapter adapter;
        int id = pLoader.getId();

        switch(id) {
            case PresenterScheduleAdd.LOADER_ID__FAMILY_MEMBER:
                projection = TableFamilyMember.getColumns().toArray(projection);
                extras = new MatrixCursor(projection);
                extras.addRow(new Object[]{Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select)});
                adapter = this.mFamilyMemberAdapter;
                break;
            /*
            case PresenterScheduleAdd.LOADER_ID__MEDICINE:
                projection = TableMedicine.getColumns().toArray(projection);
                extras = new MatrixCursor(projection);
                extras.addRow(new Object[] {Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select), "", ""});
                adapter = this.mMedicineAdapter;
                break;
            */
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_TIME:
                projection = TableMedicineTime.getColumns().toArray(projection);
                extras = new MatrixCursor(projection);
                extras.addRow(new Object[] {Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select), ""});
                adapter = this.mMedicineTimeAdapter;
                break;
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_INTERVAL:
                projection = TableMedicineInterval.getColumns().toArray(projection);
                extras = new MatrixCursor(projection);
                extras.addRow(new Object[] {Constants.ROW_ID__NO_SELECT, this.mView.getViewContext().getString(R.string.lbl__select), ""});
                adapter = this.mMedicineIntervalAdapter;
                break;
            default:
                return;
        }

        cursor = new MergeCursor(new Cursor[]{extras, pData});
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> pLoader) {
        SimpleCursorAdapter adapter;
        int id = pLoader.getId();

        switch(id) {
            case PresenterScheduleAdd.LOADER_ID__FAMILY_MEMBER:
                adapter = this.mFamilyMemberAdapter;
                break;
            /*
            case PresenterScheduleAdd.LOADER_ID__MEDICINE:
                adapter = this.mMedicineAdapter;
                break;
            */
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_TIME:
                adapter = this.mMedicineTimeAdapter;
                break;
            case PresenterScheduleAdd.LOADER_ID__MEDICINE_INTERVAL:
                adapter = this.mMedicineIntervalAdapter;
                break;
            default:
                return;
        }

        adapter.swapCursor(null);
    }

    private void createFamilyMemberAdapter() {
        String[] from = new String[] {TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mFamilyMemberAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mFamilyMemberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setFamilyMemberAdapter(this.mFamilyMemberAdapter);
    }

    /*
    private void createMedicineAdapter() {
        String[] from = new String[] {TableMedicine.COLUMN_NAME__MEDICINE_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mMedicineAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mMedicineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setMedicineAdapter(this.mMedicineAdapter);
    }
    */

    private void createMedicineTimeAdapter() {
        String[] from = new String[] {TableMedicineTime.COLUMN_NAME__MEDICINE_TIME_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mMedicineTimeAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mMedicineTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setMedicineTimeAdapter(this.mMedicineTimeAdapter);
    }

    private void createMedicineIntervalAdapter() {
        String[] from = new String[] {TableMedicineInterval.COLUMN_NAME__MEDICINE_INTERVAL_NAME};
        int[] to = new int[] {android.R.id.text1};
        this.mMedicineIntervalAdapter = new SimpleCursorAdapter(this.mView.getViewContext(),
                android.R.layout.simple_spinner_item, null, from, to, 0);
        this.mMedicineIntervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mView.setMedicineIntervalAdapter(this.mMedicineIntervalAdapter);
    }

    private int checkInputData() {
        return 0;
    }
}
