package com.dreamdigitizers.drugmanagement.views.abstracts;

import com.dreamdigitizers.drugmanagement.data.models.MedicineCategory;

public interface IViewMedicineCategoryEdit extends IView {
    void bindData(MedicineCategory pModel);
}