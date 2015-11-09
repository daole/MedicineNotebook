package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleAdd;

class PresenterScheduleAdd implements IPresenterScheduleAdd {
    private IViewScheduleAdd mView;

    public PresenterScheduleAdd(IViewScheduleAdd pView) {
        this.mView = pView;
    }
}
