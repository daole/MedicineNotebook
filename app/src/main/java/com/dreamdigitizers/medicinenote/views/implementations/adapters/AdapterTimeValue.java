package com.dreamdigitizers.medicinenote.views.implementations.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dreamdigitizers.medicinenote.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterTimeValue extends AdapterBase {
    private List<String> mData;

    public AdapterTimeValue(Context pContext) {
        super(pContext);

        this.mData = new ArrayList<>();
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
            viewHolder.mBtnDelete = (ImageButton)pConvertView.findViewById(R.id.btnDelete);
            pConvertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder)pConvertView.getTag();
        viewHolder.mLblMedicineTimeValue.setText(this.mData.get(pPosition));
        viewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                AdapterTimeValue.this.onButtonDeleteClick(pPosition);
            }
        });

        return pConvertView;
    }

    public List<String> getData() {
        return this.mData;
    }

    public void addItem(String pItem) {
        if(!this.mData.contains(pItem)) {
            this.mData.add(pItem);
            Collections.sort(this.mData);
        }
        this.notifyDataSetChanged();
        this.setListViewHeightBasedOnItems();
    }

    public void addItems(String[] pItems) {
        if (pItems != null && pItems.length > 0) {
            for (String item : pItems) {
                if(!this.mData.contains(item)) {
                    this.mData.add(item);
                }
            }
            Collections.sort(this.mData);
            this.notifyDataSetChanged();
            this.setListViewHeightBasedOnItems();
        }
    }

    public void removeItem(int pPosition) {
        this.mData.remove(pPosition);
        this.notifyDataSetChanged();
        this.setListViewHeightBasedOnItems();
    }

    public void clearItem() {
        this.mData.clear();
        this.notifyDataSetChanged();
        this.setListViewHeightBasedOnItems();
    }

    private void onButtonDeleteClick(int pPosition) {
        this.removeItem(pPosition);
    }

    private static class ViewHolder {
        public TextView mLblMedicineTimeValue;
        public ImageButton mBtnDelete;
    }
}
