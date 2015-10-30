package com.dreamdigitizers.drugmanagement.views;

import android.support.v4.app.LoaderManager;
import android.widget.ListAdapter;

public interface IListView extends IView {
    LoaderManager getViewLoaderManager();
    void setAdapter(ListAdapter pListAdapter);
}
