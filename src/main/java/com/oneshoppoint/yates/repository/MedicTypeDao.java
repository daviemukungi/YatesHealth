package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.MedicType;

/**
 * Created by robinson on 4/8/16.
 */
public interface MedicTypeDao extends GenericDao<MedicType> {
    MedicType getByToken(String token);
}
