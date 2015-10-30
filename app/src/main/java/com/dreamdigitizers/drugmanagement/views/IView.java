package com.dreamdigitizers.drugmanagement.views;

import android.content.Context;

import com.dreamdigitizers.drugmanagement.utils.DialogUtils;

public interface IView {
    Context getViewContext();
    void showMessage(int pStringResourceId);
    void showConfirmation(int pStringResourceId, DialogUtils.IOnDialogButtonClickListener pListener);
    void showError(int pStringResourceId);
}
