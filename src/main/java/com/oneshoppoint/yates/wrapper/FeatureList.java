package com.oneshoppoint.yates.wrapper;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 5/4/16.
 */
public class FeatureList {
    @NotNull
    private List<FeatureForm> featureFormList;

    public void setFeatureFormList (List<FeatureForm> featureFormList) {
        this.featureFormList = new ArrayList<FeatureForm>();
        this.featureFormList.addAll(featureFormList);
    }

    public List<FeatureForm> getFeatureFormList () {
        return featureFormList;
    }
}
