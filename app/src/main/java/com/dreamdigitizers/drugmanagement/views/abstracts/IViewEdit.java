package com.dreamdigitizers.drugmanagement.views.abstracts;

import com.dreamdigitizers.drugmanagement.data.models.Model;

public interface IViewEdit<T extends Model> extends IView {
    void bindData(T pModel);
}
