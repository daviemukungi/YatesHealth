package com.oneshoppoint.yates.service;


import com.oneshoppoint.yates.wrapper.CarrierPlanForm;


/**
 * Created by robinson on 4/9/16.
 */
public interface CarrierPlanService {
    void save(CarrierPlanForm carrierPlanForm);
    void update (CarrierPlanForm carrierPlanForm);
    void delete(Integer id);
}
