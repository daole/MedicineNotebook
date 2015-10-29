package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenter;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.presenters.interfaces.IPresenterFamilyMemberList;
import com.dreamdigitizers.drugmanagement.views.IView;
import com.dreamdigitizers.drugmanagement.views.IViewFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.views.IViewFamilyMemberList;

public class PresenterFactory {
    public static IPresenter createPresenter(Class pClass, IView pView) {
        if(pClass == IPresenterFamilyMemberAdd.class) {
            return new PresenterFamilyMemberAdd((IViewFamilyMemberAdd)pView);
        }
        if(pClass == IPresenterFamilyMemberList.class) {
            return new PresenterFamilyMemberList((IViewFamilyMemberList)pView);
        }

        return null;
    }
}
