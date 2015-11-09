package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleAdd;

public class ScreenScheduleAdd extends Screen implements IViewScheduleAdd {
    @Override
    protected View loadView(LayoutInflater pInflater, ViewGroup pContainer) {
        View rootView = pInflater.inflate(R.layout.screen__schedule_add, pContainer, false);
        return rootView;
    }

    @Override
    protected void retrieveScreenItems(View pView) {

    }

    @Override
    protected void mapInformationToScreenItems(View pView) {

    }

    @Override
    protected int getTitle() {
        return R.string.title__screen_schedule_add;
    }

    @Override
    public void clearInput() {

    }
}
