package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineList;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.views.IViewMedicineList;

public class ScreenMedicineList extends Screen implements IViewMedicineList {
    private ListView mListView;
    private TextView mLblEmpty;

    private IPresenterMedicineList mPresenter;

    @Override
    public void createOptionsMenu(Menu pMenu, MenuInflater pInflater) {
        pInflater.inflate(R.menu.menu__add_delete, pMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch(pItem.getItemId()) {
            case R.id.menu_item__add:
                this.optionAddSelected();
                return true;
            case R.id.menu_item__delete:
                this.optionDeleteSelected();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(pItem);
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__medicine_list, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mListView = (ListView)pView.findViewById(R.id.lstMedicines);
        this.mLblEmpty = (TextView)pView.findViewById(R.id.lblEmpty);
    }

    @Override
    protected void mapInformationToScreenItems(View pView) {
        this.mListView.setEmptyView(this.mLblEmpty);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long pRowId) {
                ScreenMedicineList.this.listItemClick(pRowId);
            }
        });

        this.mPresenter = (IPresenterMedicineList) PresenterFactory.createPresenter(IPresenterMedicineList.class, this);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_list;
    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.getLoaderManager();
    }

    @Override
    public void setAdapter(ListAdapter pAdapter) {
        this.mListView.setAdapter(pAdapter);
    }

    private void optionAddSelected() {
        this.goToAddScreen();
    }

    private void optionDeleteSelected() {
        this.mPresenter.delete();
    }

    private void listItemClick(long pRowId) {
        this.goToEditScreen(pRowId);
    }

    private void goToAddScreen() {
        Screen screen = new ScreenMedicineAdd();
        this.mScreenActionsListener.onChangeScreen(screen);
    }

    private void goToEditScreen(long pRowId) {
        Bundle arguments = new Bundle();
        arguments.putLong(Screen.BUNDLE_KEY__ROW_ID, pRowId);
        //Screen screen = new ScreenMedicineCategoryEdit();
        //screen.setArguments(arguments);
        //this.mScreenActionsListener.onChangeScreen(screen);
    }
}
