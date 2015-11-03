package com.dreamdigitizers.drugmanagement.presenters.abstracts;

import java.util.List;

public interface IPresenterMedicineTimeAdd extends IPresenter {
    void insert(String pMedicineTimeName, List<String> pMedicineTimeValues);
}
