package com.dreamdigitizers.drugmanagement.views;

import android.content.Context;

public interface IView {
    Context getViewContext();
    void showError(int pStringResourceId);
    void showMessage(int pStringResourceId);
}
