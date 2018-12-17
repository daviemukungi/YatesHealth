package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Feature;
import com.oneshoppoint.yates.wrapper.FeatureList;

import java.util.List;

/**
 * Created by robinson on 5/4/16.
 */
public interface FeatureService {
    void save (FeatureList featureList);
    List<Feature> getAll ();
    Feature getById(Integer id);
    void update (FeatureList featureList);
    void delete (List<Integer> ids);
}
