package com.dreamdigitizers.drugmanagement.presenters.implementations;

import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenter;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterAlarm;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCamera;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterCapturedPicturePreview;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterFamilyMemberList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterInitialization;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineCategoryList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineInformation;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineIntervalList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineSelect;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterMedicineTimeList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterAvailableAlarm;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterPrescriptionAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterPrescriptionEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterPrescriptionList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleAdd;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleEdit;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterScheduleList;
import com.dreamdigitizers.drugmanagement.presenters.abstracts.IPresenterSettings;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewAlarm;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewInitialization;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineInformation;
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
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineSelect;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewMedicineTimeList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewAvailableAlarm;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewPrescriptionAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewPrescriptionEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewPrescriptionList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleAdd;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleEdit;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewScheduleList;
import com.dreamdigitizers.drugmanagement.views.abstracts.IViewSettings;

public class PresenterFactory {
    public static IPresenter createPresenter(Class pClass, IView pView) {
        if(pClass == IPresenterInitialization.class) {
            return new PresenterInitialization((IViewInitialization)pView);
        }

        if(pClass == IPresenterSettings.class) {
            return new PresenterSettings((IViewSettings)pView);
        }

        if(pClass == IPresenterAlarm.class) {
            return new PresenterAlarm((IViewAlarm)pView);
        }
        if(pClass == IPresenterAvailableAlarm.class) {
            return new PresenterAvailableAlarm((IViewAvailableAlarm)pView);
        }

        if(pClass == IPresenterCamera.class) {
            return new PresenterCamera((IViewCamera)pView);
        }
        if(pClass == IPresenterCapturedPicturePreview.class) {
            return new PresenterCapturedPicturePreview((IViewCapturedPicturePreview)pView);
        }

        if(pClass == IPresenterScheduleList.class) {
            return new PresenterScheduleList((IViewScheduleList)pView);
        }
        if(pClass == IPresenterScheduleAdd.class) {
            return new PresenterScheduleAdd((IViewScheduleAdd)pView);
        }
        if(pClass == IPresenterScheduleEdit.class) {
            return new PresenterScheduleEdit((IViewScheduleEdit)pView);
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

        if(pClass == IPresenterMedicineSelect.class) {
            return new PresenterMedicineSelect((IViewMedicineSelect)pView);
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
        if(pClass == IPresenterMedicineInformation.class) {
            return new PresenterMedicineInformation((IViewMedicineInformation)pView);
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

        if(pClass == IPresenterPrescriptionList.class) {
            return new PresenterPrescriptionList((IViewPrescriptionList)pView);
        }
        if(pClass == IPresenterPrescriptionAdd.class) {
            return new PresenterPrescriptionAdd((IViewPrescriptionAdd)pView);
        }
        if(pClass == IPresenterPrescriptionEdit.class) {
            return new PresenterPrescriptionEdit((IViewPrescriptionEdit)pView);
        }

        return null;
    }
}
