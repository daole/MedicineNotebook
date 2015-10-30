package com.dreamdigitizers.drugmanagement.views;

import com.dreamdigitizers.drugmanagement.data.models.MedicineCategory;

public interface IViewMedicineCategoryEdit extends IView {
    void bindData(MedicineCategory pModel);
}
