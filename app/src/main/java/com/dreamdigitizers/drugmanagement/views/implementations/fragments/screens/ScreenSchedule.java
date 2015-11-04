package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.implementations.activities.CameraActivity;

public class ScreenSchedule extends Screen {
    private ListView mListView;

    @Override
    public void createOptionsMenu(Menu pMenu, MenuInflater pInflater) {
        pInflater.inflate(R.menu.menu__add_delete, pMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch(pItem.getItemId()) {
            case R.id.menu_item__add:
                return true;
            case R.id.menu_item__delete:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(pItem);
    }

    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__schedule, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {
        this.mListView = (ListView)pView.findViewById(R.id.schedule_list);

        Button btnTakeCamera = (Button)pView.findViewById(R.id.btnTakePicture);
        btnTakeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenSchedule.this.getContext(), CameraActivity.class);
                ScreenSchedule.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void recoverInstanceState(Bundle pSavedInstanceState) {

    }

    @Override
    protected void mapInformationToScreenItems() {

    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_schedule;
    }
}
