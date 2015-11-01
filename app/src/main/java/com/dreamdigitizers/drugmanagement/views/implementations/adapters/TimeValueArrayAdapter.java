package com.dreamdigitizers.drugmanagement.views.implementations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeValueArrayAdapter extends BaseAdapter {
    private Context mContext;
    private int mLayoutResourceId;
    private List<String> mData;

    public TimeValueArrayAdapter(Context pContext, int pLayoutResourceId) {
        this(pContext, pLayoutResourceId, new ArrayList<String>());
    }

    public TimeValueArrayAdapter(Context pContext, int pLayoutResourceId, String[] pData) {
        this(pContext, pLayoutResourceId, Arrays.asList(pData));
    }

    public TimeValueArrayAdapter(Context pContext, int pLayoutResourceId, List<String> pData) {
        this.mContext = pContext;
        this.mLayoutResourceId = pLayoutResourceId;
        this.mData = pData;
    }

    @Override
    public int getCount() {
        return this.mData.size();
    }

    @Override
    public Object getItem(int pPosition) {
        return this.mData.get(pPosition);
    }

    @Override
    public long getItemId(int pPosition) {
        return pPosition;
    }

    @Override
    public View getView(final int pPosition, View pConvertView, ViewGroup pParent) {
        ViewHolder viewHolder;
        if(pConvertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            pConvertView = layoutInflater.inflate(R.layout.part__medicine_time_value, pParent, false);

            viewHolder = new ViewHolder();
            viewHolder.mLblMedicineTimeValue = (TextView)pConvertView.findViewById(R.id.lblMedicineTimeValue);
            viewHolder.mBtnDelete = (Button)pConvertView.findViewById(R.id.btnDelete);
            pConvertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder)pConvertView.getTag();
        viewHolder.mLblMedicineTimeValue.setText(this.mData.get(pPosition));
        viewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                TimeValueArrayAdapter.this.onButtonDeleteClick(pPosition);
            }
        });

        return pConvertView;
    }

    public List<String> getData() {
        return this.mData;
    }

    public void addItem(String pObject) {
        if(!this.mData.contains(pObject)) {
            this.mData.add(pObject);
            Collections.sort(this.mData);
        }
        this.notifyDataSetChanged();
    }

    public void removeItem(int pPosition) {
        this.mData.remove(pPosition);
        this.notifyDataSetChanged();
    }

    private void onButtonDeleteClick(int pPosition) {
        TimeValueArrayAdapter.this.removeItem(pPosition);
    }

    private static class ViewHolder {
        public TextView mLblMedicineTimeValue;
        public Button mBtnDelete;
    }
}
