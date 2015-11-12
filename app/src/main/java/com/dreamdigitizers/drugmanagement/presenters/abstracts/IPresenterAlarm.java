package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import com.dreamdigitizers.drugmanagement.data.models.AlarmExtended;

public interface IPresenterAlarm extends IPresenter {
    void select(long pRowId);
    boolean setAlarmDone(long pRowId);
}
