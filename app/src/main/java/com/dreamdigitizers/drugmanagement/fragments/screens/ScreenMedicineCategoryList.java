package com.dreamdigitizers.drugmanagement.fragments.screens;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.views.IViewMedicineCategoryList;

public class ScreenMedicineCategoryList extends Screen implements IViewMedicineCategoryList {
    private IPresenterMedicineCategoryList mPresenterMedicineCategoryList;
    private ListView mListView;

    @Override
    public void onCreateOptionsMenu(Menu pMenu, MenuInflater pInflater) {
        super.onCreateOptionsMenu(pMenu, pInflater);
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
        View rootView = pInflater.inflate(R.layout.screen__medicine_category_list, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mListView = (ListView)pView.findViewById(R.id.lstMedicineCategories);
        View lblEmpty = pView.findViewById(R.id.lblEmpty);
        this.mListView.setEmptyView(lblEmpty);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {
        this.mPresenterMedicineCategoryList = (IPresenterMedicineCategoryList) PresenterFactory.createPresenter(IPresenterMedicineCategoryList.class, this);
    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_category_list;
    }

    @Override
    public LoaderManager getViewLoaderManager() {
        return this.getLoaderManager();
    }

    @Override
    public void setAdapter(ListAdapter pListAdapter) {
        this.mListView.setAdapter(pListAdapter);
    }

    private void optionAddSelected() {
        this.goToAddScreen();
    }

    private void optionDeleteSelected() {
        this.mPresenterMedicineCategoryList.delete();
    }

    private void goToAddScreen() {
        Screen screen = new ScreenMedicineCategoryAdd();
        this.mIScreenActionsListener.onChangeScreen(screen);
    }
}
