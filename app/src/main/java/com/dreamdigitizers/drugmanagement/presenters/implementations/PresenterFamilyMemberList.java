package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.MedicineContentProvider;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableFamilyMember;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterFamilyMemberList;
import com.dreamdigitizers.drugmanagement.views.IViewFamilyMemberList;

public class PresenterFamilyMemberList implements IPresenterFamilyMemberList {
    private IViewFamilyMemberList mViewFamilyMemberList;
    private SimpleCursorAdapter mSimpleCursorAdapter;

    public PresenterFamilyMemberList(IViewFamilyMemberList pViewFamilyMemberList) {
        this.mViewFamilyMemberList = pViewFamilyMemberList;
        this.mViewFamilyMemberList.getViewLoaderManager().initLoader(0, null, this);
        this.createAdapder();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int pId, Bundle pArgs) {
        String[] projection = new String[0];
        projection = TableFamilyMember.getColumns().toArray(projection);
        CursorLoader cursorLoader = new CursorLoader(this.mViewFamilyMemberList.getViewContext(),
                MedicineContentProvider.CONTENT_URI__FAMILY_MEMBER, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> pLoader, Cursor pData) {
        this.mSimpleCursorAdapter.swapCursor(pData);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> pLoader) {
        this.mSimpleCursorAdapter.swapCursor(null);
    }

    public void createAdapder() {
        String[] from = new String[] {TableFamilyMember.COLUMN_NAME__FAMILY_MEMBER_NAME};
        int[] to = new int[] { R.id.lblFamilyMemberName };
        this.mSimpleCursorAdapter = new SimpleCursorAdapter(this.mViewFamilyMemberList.getViewContext(),
                R.layout.part__family_member, null, from, to, 0);
        this.mViewFamilyMemberList.setAdapter(this.mSimpleCursorAdapter);
    }
}
