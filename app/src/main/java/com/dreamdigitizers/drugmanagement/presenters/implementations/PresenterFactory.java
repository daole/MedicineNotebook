package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenter;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IView;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewFamilyMemberEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewFamilyMemberList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineCategoryAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineCategoryEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeList;

public class PresenterFactory {
    public static IPresenter createPresenter(Class pClass, IView pView) {
        if(pClass == IPresenterFamilyMemberList.class) {
            return new PresenterFamilyMemberList((IViewFamilyMemberList)pView);
        }
        if(pClass == IPresenterFamilyMemberAdd.class) {
            return new PresenterFamilyMemberAdd((IViewFamilyMemberAdd)pView);
        }
        if(pClass == IPresenterFamilyMemberEdit.class) {
            return new PresenterFamilyMemberEdit((IViewFamilyMemberEdit)pView);
        }
        if(pClass == IPresenterMedicineCategoryList.class) {
            return new PresenterMedicineCategoryList((IViewMedicineCategoryList)pView);
        }
        if(pClass == IPresenterMedicineCategoryAdd.class) {
            return new PresenterMedicineCategoryAdd((IViewMedicineCategoryAdd)pView);
        }
        if(pClass == IPresenterMedicineCategoryEdit.class) {
            return new PresenterMedicineCategoryEdit((IViewMedicineCategoryEdit)pView);
        }
        if(pClass == IPresenterMedicineTimeList.class) {
            return new PresenterMedicineTimeList((IViewMedicineTimeList)pView);
        }
        if(pClass == IPresenterMedicineTimeAdd.class) {
            return new PresenterMedicineTimeAdd((IViewMedicineTimeAdd)pView);
        }
        return null;
    }
}
