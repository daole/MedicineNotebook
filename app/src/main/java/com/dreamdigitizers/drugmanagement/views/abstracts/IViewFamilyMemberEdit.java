package com.dreamdigitizers.drugmanagement.views.abstracts;

import com.dreamdigitizers.drugmanagement.data.models.FamilyMember;

public interface IViewFamilyMemberEdit extends IView {
    void bindData(FamilyMember pModel);
}
