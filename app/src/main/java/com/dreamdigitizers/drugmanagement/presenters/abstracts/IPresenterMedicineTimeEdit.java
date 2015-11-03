package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import java.util.List;

public interface IPresenterMedicineTimeEdit extends IPresenter {
    void select(long pRowId);
    void edit(long pRowId, String pMedicineTimeName, List<String> pMedicineTimeValues);
}
