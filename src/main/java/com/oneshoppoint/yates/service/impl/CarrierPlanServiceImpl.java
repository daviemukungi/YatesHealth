package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import com.oneshoppoint.yates.service.CarrierPlanService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.CarrierForm;
import com.oneshoppoint.yates.wrapper.CarrierPlanForm;
import com.oneshoppoint.yates.wrapper.CarrierRateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by robinson on 4/8/16.
 */
@Service
@Transactional
public class CarrierPlanServiceImpl implements CarrierPlanService {
    @Autowired
    GenericDao<Carrier> carrierDao;
    @Autowired
    GenericRecursiveDao<Location> locationDao;
    @Autowired
    GenericDao<CarrierPlan> carrierPlanDao;
    @Autowired
    CurrentUser currentUser;

    public void save (CarrierPlanForm carrierPlanForm) {
        Carrier carrier = currentUser.getUser().getAffiliate().getCarrier();
        if(carrier == null) {
            throw new RestException(Status.ERROR,"The carrier owning the carrier plan was not found");
        }

        CarrierPlan carrierPlan = new CarrierPlan();
        carrierPlan.setName(carrierPlanForm.getName());
        carrierPlan.setBaseWeight(carrierPlanForm.getBaseWeight());
        carrierPlan.setMaximumPackSize(carrierPlanForm.getMaximumPackSize());
        carrierPlan.setMaximumWeight(carrierPlanForm.getMaximumWeight());
        carrierPlan.setExceedWeight(carrierPlanForm.getExceedWeight());
        carrierPlan.setExceedCharge(carrierPlanForm.getExceedCharge());
        carrierPlan.setShippingFactor(carrierPlanForm.getShippingFactor());
        List<CarrierRateForm> carrierRateForms = carrierPlanForm.getCarrierRates();
        Set<CarrierRate> carrierRates = new HashSet<CarrierRate>();

        for(CarrierRateForm carrierRateForm : carrierRateForms) {
            CarrierRate carrierRate = new CarrierRate();
            carrierRate.setOrigin(locationDao.find(carrierRateForm.getOriginId()));
            carrierRate.setDestination(locationDao.find(carrierRateForm.getDestinationId()));
            carrierRate.setPrice(carrierRateForm.getPrice());
            carrierRate.setExceedCharge(carrierRateForm.getExceedCharge());
            carrierRate.setExceedWeight(carrierRateForm.getExceedWeight());
            carrierRates.add(carrierRate);
        }
        carrierPlan.setCarrierRates(carrierRates);
        List<CarrierPlan> carrierPlans =  new ArrayList<CarrierPlan>();
        if(carrier.getCarrierPlans() != null) {
            carrierPlans.addAll(carrier.getCarrierPlans());
        }
        carrierPlans.add(carrierPlan);
        carrier.setCarrierPlans(carrierPlans);
        carrierDao.save(carrier);
    }

    public void update (CarrierPlanForm carrierPlanForm) {
        CarrierPlan carrierPlan = carrierPlanDao.find(carrierPlanForm.getId());

        if(carrierPlan == null) {
            throw new RestException(Status.FAILED,"Carrier Plan not found");
        }

        carrierPlan.setName(carrierPlanForm.getName());
        carrierPlan.setBaseWeight(carrierPlanForm.getBaseWeight());
        carrierPlan.setMaximumPackSize(carrierPlanForm.getMaximumPackSize());
        carrierPlan.setMaximumWeight(carrierPlanForm.getMaximumWeight());
        carrierPlan.setExceedWeight(carrierPlanForm.getExceedWeight());
        carrierPlan.setExceedCharge(carrierPlanForm.getExceedCharge());
        carrierPlan.setShippingFactor(carrierPlanForm.getShippingFactor());
        List<CarrierRateForm> carrierRateForms = carrierPlanForm.getCarrierRates();
        Set<CarrierRate> carrierRates = new HashSet<CarrierRate>();

        for(CarrierRateForm carrierRateForm : carrierRateForms) {
            CarrierRate carrierRate = new CarrierRate();
            carrierRate.setOrigin(locationDao.find(carrierRateForm.getOriginId()));
            carrierRate.setDestination(locationDao.find(carrierRateForm.getDestinationId()));
            carrierRate.setPrice(carrierRateForm.getPrice());
            carrierRate.setExceedCharge(carrierRateForm.getExceedCharge());
            carrierRate.setExceedWeight(carrierRateForm.getExceedWeight());
            carrierRates.add(carrierRate);
        }
        carrierPlan.setCarrierRates(carrierRates);
        carrierPlanDao.update(carrierPlan);
    }

    public void delete (Integer id) {
        Carrier carrier = currentUser.getUser().getAffiliate().getCarrier();

        if(carrier == null) {
            throw new RestException(Status.ERROR,"The carrier owning the carrier plan does not exist");
        }
        List<CarrierPlan> carrierPlans = carrier.getCarrierPlans();
        List<CarrierPlan> carrierPlanList = new ArrayList<CarrierPlan>();
        for (CarrierPlan carrierPlan : carrierPlans) {
            if(carrierPlan.getId() != id) {
                carrierPlanList.add(carrierPlan);
            }
        }
        carrier.setCarrierPlans(carrierPlanList);
        carrierDao.delete(carrier);
    }
}