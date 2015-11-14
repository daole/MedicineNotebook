package com.dreamdigitizers.drugmanagement.views.implementations.adapters;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicine;

public class AdapterTakenMedicine extends AdapterBase {
    ArrayMap<Long, TakenMedicine> mData;

    public AdapterTakenMedicine(Context pContext) {
        super(pContext);

        this.mData = new ArrayMap<>();
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
            pConvertView = layoutInflater.inflate(R.layout.part__taken_medicine, pParent, false);

            viewHolder = new ViewHolder();
            viewHolder.mLblMedicineName = (TextView)pConvertView.findViewById(R.id.lblMedicineName);
            viewHolder.mLblDose = (TextView)pConvertView.findViewById(R.id.lblDose);
            viewHolder.mBtnDelete = (ImageButton)pConvertView.findViewById(R.id.btnDelete);
            pConvertView.setTag(viewHolder);
        }

        TakenMedicine takenMedicine = this.mData.valueAt(pPosition);
        viewHolder = (ViewHolder)pConvertView.getTag();
        viewHolder.mLblMedicineName.setText(takenMedicine.getFallbackMedicineName());
        viewHolder.mLblDose.setText(takenMedicine.getDose());
        viewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                AdapterTakenMedicine.this.onButtonDeleteClick(pPosition);
            }
        });

        return pConvertView;
    }

    public ArrayMap getData() {
        return this.mData;
    }

    public void addItem(TakenMedicine pItem) {
        this.mData.put(pItem.getMedicineId(), pItem);
        this.notifyDataSetChanged();
        this.setListViewHeightBasedOnItems();
    }

    public void addItems(TakenMedicine[] pItems) {
        if (pItems != null && pItems.length > 0) {
            for (TakenMedicine item : pItems) {
                this.addItem(item);
            }
            this.notifyDataSetChanged();
            this.setListViewHeightBasedOnItems();
        }
    }

    public void removeItem(int pPosition) {
        this.mData.removeAt(pPosition);
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
        public TextView mLblMedicineName;
        public TextView mLblDose;
        public ImageButton mBtnDelete;
    }
}
