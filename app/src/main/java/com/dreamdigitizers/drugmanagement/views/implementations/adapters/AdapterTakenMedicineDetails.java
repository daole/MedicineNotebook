package com.dreamdigitizers.drugmanagement.views.implementations.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dreamdigitizers.drugmanagement.R;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicineExtended;

import java.util.List;

public class AdapterTakenMedicineDetails extends AdapterBase {
    private List<TakenMedicineExtended> mData;
    private IBitmapLoader mBitmapLoader;
    private ArrayMap<Long, Bitmap> mBitmaps;

    public AdapterTakenMedicineDetails(Context pContext, List<TakenMedicineExtended> pData, IBitmapLoader pPictureLoader) {
        super(pContext);

        this.mData = pData;
        this.mBitmapLoader = pPictureLoader;
        this.mBitmaps = new ArrayMap<>();
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
            pConvertView = layoutInflater.inflate(R.layout.part__taken_medicine_alarm, pParent, false);

            viewHolder = new ViewHolder();
            viewHolder.mImgMedicinePicture = (ImageView)pConvertView.findViewById(R.id.imgMedicinePicture);
            viewHolder.mLblMedicineName = (TextView)pConvertView.findViewById(R.id.lblMedicineName);
            viewHolder.mLblDose = (TextView)pConvertView.findViewById(R.id.lblDose);

            pConvertView.setTag(viewHolder);
        }

        TakenMedicineExtended takenMedicine = this.mData.get(pPosition);
        viewHolder = (ViewHolder)pConvertView.getTag();

        final String medicineImagePath = takenMedicine.getMedicine().getMedicineImagePath();
        if(!TextUtils.isEmpty(medicineImagePath)) {
            Bitmap bitmap = this.mBitmaps.get(takenMedicine.getRowId());
            if(bitmap != null) {
                viewHolder.mImgMedicinePicture.setImageBitmap(bitmap);
            } else if(this.mBitmapLoader != null) {
                final ImageView medicinePicture = viewHolder.mImgMedicinePicture;
                medicinePicture.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        Bitmap bitmap = AdapterTakenMedicineDetails.this.mBitmapLoader.loadBitmap(medicineImagePath, medicinePicture.getWidth(), medicinePicture.getHeight());
                        if(bitmap != null) {
                            medicinePicture.setImageBitmap(bitmap);
                        }
                        medicinePicture.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
            }
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

    public interface IBitmapLoader {
        Bitmap loadBitmap(String pFilePath, int pWidth, int pHeight);
    }

    private static class ViewHolder {
        public ImageView mImgMedicinePicture;
        public TextView mLblMedicineName;
        public TextView mLblDose;
    }
}
