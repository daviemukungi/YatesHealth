package com.oneshoppoint.yates.wrapper;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 5/4/16.
 */
public class DosageList {
    @NotNull
    private List<Dosage> dosageFormList;

    public void setDosageFormList (List<Dosage> dosageFormList) {
        this.dosageFormList = new ArrayList<Dosage>();
        this.dosageFormList.addAll(dosageFormList);
    }

    public List<Dosage> getDosageFormList () {
        return dosageFormList;
    }
}
