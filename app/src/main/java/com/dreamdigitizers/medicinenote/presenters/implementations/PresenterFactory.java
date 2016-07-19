package com.dreamdigitizers.medicinenote.presenters.implementations;

import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenter;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterAlarm;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterAvailableAlarm;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterCamera;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterCapturedPicturePreview;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterFamilyMemberAdd;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterFamilyMemberEdit;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterFamilyMemberList;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterInitialization;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineAdd;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineCategoryAdd;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineCategoryEdit;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineCategoryList;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineEdit;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineInformation;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineIntervalAdd;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineIntervalEdit;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineIntervalList;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineList;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineSelect;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineTimeAdd;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineTimeEdit;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterMedicineTimeList;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterPrescriptionAdd;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterPrescriptionEdit;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterPrescriptionList;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterScheduleAdd;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterScheduleEdit;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterScheduleList;
import com.dreamdigitizers.medicinenote.presenters.abstracts.IPresenterSettings;
import com.dreamdigitizers.medicinenote.views.abstracts.IView;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewAlarm;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewAvailableAlarm;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewCamera;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewCapturedPicturePreview;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewFamilyMemberAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewFamilyMemberEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewFamilyMemberList;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewInitialization;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineCategoryAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineCategoryEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineCategoryList;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineInformation;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineIntervalAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineIntervalEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineIntervalList;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineList;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineSelect;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineTimeAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineTimeEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewMedicineTimeList;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewPrescriptionAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewPrescriptionEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewPrescriptionList;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewScheduleAdd;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewScheduleEdit;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewScheduleList;
import com.dreamdigitizers.medicinenote.views.abstracts.IViewSettings;

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
