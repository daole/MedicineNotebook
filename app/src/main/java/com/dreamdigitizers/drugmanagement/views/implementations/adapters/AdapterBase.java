package com.dreamdigitizers.drugmanagement.views.implementations.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class AdapterBase extends BaseAdapter {
    protected Context mContext;
    protected ListView mListView;

    public AdapterBase(Context pContext) {
        this.mContext = pContext;
    }

    public void setListView(ListView pListView) {
        this.mListView = pListView;
        this.setListViewHeightBasedOnItems();
    }

    protected void setListViewHeightBasedOnItems() {
        if(this.mListView == null) {
            return;
        }

        int count = this.getCount();

        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View item = this.getView(i, null, this.mListView);
            item.measure(0, 0);
            totalHeight += item.getMeasuredHeight();
        }

        int totalDividersHeight = this.mListView.getDividerHeight() * (count - 1);

        ViewGroup.LayoutParams params = this.mListView.getLayoutParams();
        params.height = totalHeight + totalDividersHeight;
        this.mListView.setLayoutParams(params);
        this.mListView.requestLayout();
    }
}
