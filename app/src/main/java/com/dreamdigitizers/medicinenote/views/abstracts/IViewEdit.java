package com.dreamdigitizers.medicinenote.views.abstracts;

import com.dreamdigitizers.medicinenote.data.models.Model;

public interface IViewEdit<T extends Model> extends IView {
    void bindData(T pModel);
}
