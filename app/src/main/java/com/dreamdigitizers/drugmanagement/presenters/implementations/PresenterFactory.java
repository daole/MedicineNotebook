package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenter;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterFamilyMemberList;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterMedicineCategoryAdd;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.views.IView;
import com.dreamdigitizers.drugmanagement.views.IViewFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.views.IViewFamilyMemberList;
import com.dreamdigitizers.drugmanagement.views.IViewMedicineCategoryAdd;
import com.dreamdigitizers.drugmanagement.views.IViewMedicineCategoryList;

public class PresenterFactory {
    public static IPresenter createPresenter(Class pClass, IView pView) {
        if(pClass == IPresenterFamilyMemberList.class) {
            return new PresenterFamilyMemberList((IViewFamilyMemberList)pView);
        }
        if(pClass == IPresenterFamilyMemberAdd.class) {
            return new PresenterFamilyMemberAdd((IViewFamilyMemberAdd)pView);
        }
        if(pClass == IPresenterMedicineCategoryList.class) {
            return new PresenterMedicineCategoryList((IViewMedicineCategoryList)pView);
        }
        if(pClass == IPresenterMedicineCategoryAdd.class) {
            return new PresenterMedicineCategoryAdd((IViewMedicineCategoryAdd)pView);
        }
        return null;
    }
}
