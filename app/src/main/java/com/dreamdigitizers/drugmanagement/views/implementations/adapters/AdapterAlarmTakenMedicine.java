package com.dreamdigitizers.drugmanagement.views.implementations.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicineExtended;

import java.util.List;

public class AdapterAlarmTakenMedicine extends AdapterBase {
    private List<TakenMedicineExtended> mData;

    public AdapterAlarmTakenMedicine(Context pContext, List<TakenMedicineExtended> pData) {
        super(pContext);

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
        return this.mData.get(pPosition).getRowId();
    }

    @Override
    public View getView(final int pPosition, View pConvertView, ViewGroup pParent) {
        ViewHolder viewHolder;
        if(pConvertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            pConvertView = layoutInflater.inflate(R.layout.part__alarm_taken_medicine, pParent, false);

            viewHolder = new ViewHolder();
            viewHolder.mImgMedicinePicture = (ImageView)pConvertView.findViewById(R.id.imgMedicinePicture);
            viewHolder.mLblMedicineName = (TextView)pConvertView.findViewById(R.id.lblMedicineName);
            viewHolder.mLblDose = (TextView)pConvertView.findViewById(R.id.lblDose);

            pConvertView.setTag(viewHolder);
        }

        TakenMedicineExtended takenMedicine = this.mData.get(pPosition);
        viewHolder = (ViewHolder)pConvertView.getTag();

        String medicineImagePath = takenMedicine.getMedicine().getMedicineImagePath();
        if(!TextUtils.isEmpty(medicineImagePath)) {

        }

        String medicineName = takenMedicine.getMedicine().getMedicineName();
        if(TextUtils.isEmpty(medicineName)) {
            medicineName = takenMedicine.getFallbackMedicineName();
        }
        viewHolder.mLblMedicineName.setText(medicineName);

        String dose = takenMedicine.getDose();
        viewHolder.mLblDose.setText(dose);

        return pConvertView;
    }

    @Override
    public void setListView(ListView pListView) {
        super.setListView(pListView);
        this.setListViewHeightBasedOnItems();
    }

    private static class ViewHolder {
        public ImageView mImgMedicinePicture;
        public TextView mLblMedicineName;
        public TextView mLblDose;
    }
}
