package com.dreamdigitizers.medicinenote.views.implementations.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterAvailableAlarm;
import com.dreamdigitizers.medicinenote.presenters.implementations.PresenterFactory;
import com.dreamdigitizers.medicinenote.utils.DialogUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewAvailableAlarm;

public class ReceiverBoot extends BroadcastReceiver implements IViewAvailableAlarm {
    private Context mContext;

    private IPresenterAvailableAlarm mPresenter;

    @Override
    public void onReceive(Context pContext, Intent pIntent) {
        this.mContext = pContext;
        this.mPresenter = (IPresenterAvailableAlarm)PresenterFactory.createPresenter(IPresenterAvailableAlarm.class, this);
    }

    @Override
    public Context getViewContext() {
        return this.mContext;
    }

    @Override
    public void showMessage(int pStringResourceId) {

    }

    @Override
    public void showConfirmation(int pStringResourceId, DialogUtils.IOnDialogButtonClickListener pListener) {

    }

    @Override
    public void showError(int pStringResourceId) {

    }
}
