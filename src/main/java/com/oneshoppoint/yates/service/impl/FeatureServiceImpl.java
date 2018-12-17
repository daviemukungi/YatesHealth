package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Feature;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.service.FeatureService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.wrapper.FeatureForm;
import com.oneshoppoint.yates.wrapper.FeatureList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Created by robinson on 5/4/16.
 */
@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {
    @Autowired
    private GenericDao<Feature> featureDao;
    @Autowired
    private CurrentUser currentUser;

    public void save (FeatureList featureList) {
        List<FeatureForm> featureForms = featureList.getFeatureFormList();
        for(FeatureForm featureForm : featureForms) {
            String name = featureForm.getFeature().toLowerCase();
            Feature feature = featureDao.getByName(name);
            if(feature != null) {
                continue;
            }

            feature = new Feature();
            feature.setName(name);
            feature.setEnabled(featureForm.getEnabled());
            feature.setCreatedBy(currentUser.getUser().getId());
            feature.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            featureDao.save(feature);
        }
    }

    public void update (FeatureList featureList) {
        List<FeatureForm> featureForms = featureList.getFeatureFormList();
        for(FeatureForm featureForm : featureForms) {
            Feature feature = featureDao.find(featureForm.getId());
            if(feature == null) {
                continue;
            }

            feature.setName(featureForm.getFeature().toLowerCase());
            feature.setEnabled(featureForm.getEnabled());
            feature.setCreatedBy(currentUser.getUser().getId());
            feature.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            featureDao.save(feature);
        }
    }

    public List<Feature> getAll () {
        return featureDao.getAll();
    }

    public Feature getById (Integer id) {
        return featureDao.find(id);
    }

    public void delete (List<Integer> ids) {
        for(Integer id : ids) {
            Feature feature = featureDao.find(id);
            if(feature == null) {
                continue;
            }
            feature.setDeletedBy(currentUser.getUser().getId());
            feature.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            featureDao.save(feature);
        }
    }
}
