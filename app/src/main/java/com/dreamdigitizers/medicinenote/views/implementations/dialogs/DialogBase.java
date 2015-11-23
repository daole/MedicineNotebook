package com.dreamdigitizers.medicinenote.views.implementations.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.dreamdigitizers.medicinenote.R;
import com.dreamdigitizers.medicinenote.utils.DialogUtils;
import com.dreamdigitizers.medicinenote.views.abstracts.IView;
import com.dreamdigitizers.medicinenote.views.implementations.activities.ActivityBase;

public abstract class DialogBase extends Dialog implements IView {
    protected ActivityBase mActivity;

    public DialogBase(ActivityBase pActivity) {
        super(pActivity);

        this.mActivity = pActivity;
        this.setTitle(this.getTitle());
        this.setContentView(this.getContentView());

        this.retrieveScreenItems();
        this.mapInformationToScreenItems();
    }

    @Override
    public Context getViewContext() {
        return this.getContext();
    }

    @Override
    public void showMessage(int pStringResourceId) {
        Toast.makeText(this.getContext(), pStringResourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConfirmation(int pStringResourceId, DialogUtils.IOnDialogButtonClickListener pListener) {
        String title = this.getContext().getString(R.string.title__dialog_error);
        String positiveButtonText = this.getContext().getString(R.string.btn__ok);
        String negativeButtonText = this.getContext().getString(R.string.btn__no);
        String message = this.getContext().getString(pStringResourceId);
        DialogUtils.displayDialog(this.mActivity, title, message, true, positiveButtonText, negativeButtonText, pListener);
    }

    @Override
    public void showError(int pStringResourceId) {
        String title = this.getContext().getString(R.string.title__dialog_error);
        String buttonText = this.getContext().getString(R.string.btn__ok);
        String message = this.getContext().getString(pStringResourceId);
        DialogUtils.displayErrorDialog(this.mActivity, title, message, buttonText);
    }

    protected abstract int getTitle();
    protected abstract int getContentView();
    protected abstract void retrieveScreenItems();
    protected abstract void mapInformationToScreenItems();
}
