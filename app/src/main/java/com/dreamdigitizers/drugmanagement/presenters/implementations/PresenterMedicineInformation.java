package com.dreamdigitizers.drugmanagement.presenters.implementations;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;

import com.dreamdigitizers.drugmanagement.data.ContentProviderMedicine;
import com.dreamdigitizers.drugmanagement.data.dal.tables.TableTakenMedicine;
import com.dreamdigitizers.drugmanagement.data.models.TakenMedicineExtended;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineInformation;
import com.dreamdigitizers.drugmanagement.utils.FileUtils;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineInformation;

import java.util.List;

class PresenterMedicineInformation implements IPresenterMedicineInformation {
    private IViewMedicineInformation mView;

    public PresenterMedicineInformation(IViewMedicineInformation pView) {
        this.mView = pView;
    }

    @Override
    public Bitmap loadImage(String pFilePath) {
        return FileUtils.decodeBitmapFromFile(pFilePath);
    }

    @Override
    public Bitmap loadImage(String pFilePath, int pWidth, int pHeight) {
        return FileUtils.decodeSampledBitmapFromFile(pFilePath, pWidth, pHeight);
    }

    @Override
    public  void select(long pRowId) {
        String[] projection = new String[0];
        projection = TableTakenMedicine.getColumnsForJoin().toArray(projection);
        Uri uri = ContentProviderMedicine.CONTENT_URI__TAKEN_MEDICINE;
        uri = ContentUris.withAppendedId(uri, pRowId);
        Cursor cursor = this.mView.getViewContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            List<TakenMedicineExtended> list = TakenMedicineExtended.fetchExtendedData(cursor);
            if(list.size() > 0) {
                TakenMedicineExtended model = list.get(0);
                this.mView.bindData(model);
            }
            cursor.close();
        }
    }
}
