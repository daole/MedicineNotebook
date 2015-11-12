package com.dreamdigitizers.drugmanagement.views.implementations.adapters;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicine;

public class TakenMedicineAdapter extends BaseAdapter {
    private Context mContext;
    ArrayMap<Long, TakenMedicine> mData;

    public TakenMedicineAdapter(Context pContext) {
        this.mContext = pContext;
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
            viewHolder.mBtnDelete = (Button)pConvertView.findViewById(R.id.btnDelete);
            pConvertView.setTag(viewHolder);
        }

        TakenMedicine takenMedicine = this.mData.valueAt(pPosition);
        viewHolder = (ViewHolder)pConvertView.getTag();
        viewHolder.mLblMedicineName.setText(takenMedicine.getFallbackMedicineName());
        viewHolder.mLblDose.setText(takenMedicine.getDose());
        viewHolder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                TakenMedicineAdapter.this.onButtonDeleteClick(pPosition);
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
    }

    public void addItems(TakenMedicine[] pItems) {
        if (pItems != null && pItems.length > 0) {
            for (TakenMedicine item : pItems) {
                this.addItem(item);
            }
            this.notifyDataSetChanged();
        }
    }

    public void removeItem(int pPosition) {
        this.mData.removeAt(pPosition);
        this.notifyDataSetChanged();
    }

    public void clearItem() {
        this.mData.clear();
        this.notifyDataSetChanged();
    }

    private void onButtonDeleteClick(int pPosition) {
        this.removeItem(pPosition);
    }

    private static class ViewHolder {
        public TextView mLblMedicineName;
        public TextView mLblDose;
        public Button mBtnDelete;
    }
}
