package com.oneshoppoint.yates.service;


import com.oneshoppoint.yates.wrapper.Graph;

/**
 * Created by robinson on 5/10/16.
 */
public interface AnalyticService {
    Graph salesByLocation (Integer year,Integer id);
    Graph salesByRetailer (Integer year,Integer id);
    Graph salesByLocationAndRetailer (Integer year,Integer locationId,Integer retailerId);
    Graph salesByCarrier (Integer year,Integer id);
    Graph carrierSales (Integer year,Integer locationId);
    Graph salesByLocationAndCarrier (Integer year,Integer locationId,Integer carrierId);
    Graph popularProducts (Integer year);
    Graph popularProductsByRetailer (Integer year,Integer id);
    Graph orders (Integer year);
    Graph prescriptions (Integer year);
    Graph prescriptionsByMedic (Integer year,Integer id);
    Graph prescriptionsPie (Integer year);
}
