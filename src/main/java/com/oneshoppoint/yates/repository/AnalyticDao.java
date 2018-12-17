package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.Carrier;
import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.model.Medic;


import java.util.List;

/**
 * Created by robinson on 5/10/16.
 */
public interface AnalyticDao {
    List<Object[]> sales (Integer year,Location location);
    List<Object[]> salesByCarriers (Integer year,Location location);
    List<Object[]> sales (Integer year,Location location,Retailer retailer);
    List<Object[]> sales (Integer year,Retailer retailer);
    List<Object[]> sales (Integer year,Location location,Carrier carrier);
    List<Object[]> sales (Integer year,Carrier carrier);
    List<Object[]> popularProducts (Integer year);
    List<Object[]> popularProducts (Integer year,Retailer retailer);
    List<Object[]> orders (Integer year);
    List<Object[]> prescriptions (Integer year);
    List<Object[]> prescriptions (Integer year,Medic medic);
    List<Object[]> prescriptionsPie (Integer year);
}
