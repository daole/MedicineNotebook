package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenter;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCamera;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCapturedPicturePreview;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IView;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewCamera;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewCapturedPicturePreview;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewFamilyMemberEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewFamilyMemberList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineCategoryAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineCategoryEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineIntervalAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineIntervalEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineIntervalList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeList;

public class PresenterFactory {
    public static IPresenter createPresenter(Class pClass, IView pView) {
        if(pClass == IPresenterCamera.class) {
            return new PresenterCamera((IViewCamera)pView);
        }
        if(pClass == IPresenterCapturedPicturePreview.class) {
            return new PresenterCapturedPicturePreview((IViewCapturedPicturePreview)pView);
        }

        if(pClass == IPresenterFamilyMemberList.class) {
            return new PresenterFamilyMemberList((IViewFamilyMemberList)pView);
        }
        if(pClass == IPresenterFamilyMemberAdd.class) {
            return new PresenterFamilyMemberAdd((IViewFamilyMemberAdd)pView);
        }
        if(pClass == IPresenterFamilyMemberEdit.class) {
            return new PresenterFamilyMemberEdit((IViewFamilyMemberEdit)pView);
        }

        if(pClass == IPresenterMedicineList.class) {
            return new PresenterMedicineList((IViewMedicineList)pView);
        }
        if(pClass == IPresenterMedicineAdd.class) {
            return new PresenterMedicineAdd((IViewMedicineAdd)pView);
        }
        if(pClass == IPresenterMedicineEdit.class) {
            return new PresenterMedicineEdit((IViewMedicineEdit)pView);
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
        if(pClass == IPresenterMedicineTimeEdit.class) {
            return new PresenterMedicineTimeEdit((IViewMedicineTimeEdit)pView);
        }

        if(pClass == IPresenterMedicineIntervalList.class) {
            return new PresenterMedicineIntervalList((IViewMedicineIntervalList)pView);
        }
        if(pClass == IPresenterMedicineIntervalAdd.class) {
            return new PresenterMedicineIntervalAdd((IViewMedicineIntervalAdd)pView);
        }
        if(pClass == IPresenterMedicineIntervalEdit.class) {
            return new PresenterMedicineIntervalEdit((IViewMedicineIntervalEdit)pView);
        }
        return null;
    }
}
