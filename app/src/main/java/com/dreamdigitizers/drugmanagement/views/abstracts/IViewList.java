package com.dreamdigitizers.drugmanagement.views.abstracts;

import android.support.v4.app.LoaderManager;
import android.widget.ListAdapter;

public interface IViewList extends IView {
    LoaderManager getViewLoaderManager();
    void setAdapter(ListAdapter pListAdapter);
}
