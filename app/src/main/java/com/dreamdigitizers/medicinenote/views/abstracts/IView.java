package com.dreamdigitizers.medicinenote.views.abstracts;

import android.content.Context;

import com.dreamdigitizers.medicinenote.utils.DialogUtils;

public interface IView {
    Context getViewContext();
    void showMessage(int pStringResourceId);
    void showConfirmation(int pStringResourceId, DialogUtils.IOnDialogButtonClickListener pListener);
    void showError(int pStringResourceId);
}
