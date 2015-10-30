package com.dreamdigitizers.drugmanagement.views;

import com.dreamdigitizers.drugmanagement.data.models.FamilyMember;

public interface IViewFamilyMemberEdit extends IView {
    void bindData(FamilyMember pModel);
}
