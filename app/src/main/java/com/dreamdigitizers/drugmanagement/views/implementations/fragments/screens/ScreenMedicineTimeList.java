package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dreamdigitizers.drugmanagement.R;

public class ScreenMedicineTimeList extends Screen {
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
        View rootView = pInflater.inflate(R.layout.screen__medicine_time_list, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mListView = (ListView)pView.findViewById(R.id.lstMedicines);
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {

    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_medicine_time_list;
    }

    private void optionAddSelected() {
        this.goToAddScreen();
    }

    private void optionDeleteSelected() {

    }

    private void goToAddScreen() {
        Screen screen = new ScreenMedicineTimeAdd();
        this.mIScreenActionsListener.onChangeScreen(screen);
    }

    private void goToEditScreen(long pRowId) {

    }
}
