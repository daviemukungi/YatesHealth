package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Carrier;
import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.model.Medic;
import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.repository.AnalyticDao;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import com.oneshoppoint.yates.repository.RetailerDao;
import com.oneshoppoint.yates.service.AnalyticService;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.Dataset;
import com.oneshoppoint.yates.wrapper.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by robinson on 5/10/16.
 */
@Service
@Transactional
public class AnalyticServiceImpl implements AnalyticService {
    @Autowired
    private AnalyticDao analyticDao;
    @Autowired
    private GenericRecursiveDao<Location> locationDao;
    @Autowired
    private RetailerDao retailerDao;
    @Autowired
    private GenericDao<Medic> medicDao;
    @Autowired
    private GenericDao<Carrier> carrierDao;

    private String[] months;

    public AnalyticServiceImpl () {
        months = new String[12];
        months[0] = "January";
        months[1] = "February";
        months[2] = "March";
        months[3] = "April";
        months[4] = "May";
        months[5] = "June";
        months[6] = "July";
        months[7] = "August";
        months[8] = "September";
        months[9] = "October";
        months[10] = "November";
        months[11] = "December";
    }

    public Graph salesByLocation (Integer year, Integer locationId) {
        Location location  = locationDao.find(locationId);
        if(location == null) {
            throw new RestException(Status.FAILED,"The location does not exist");
        }
        return build(analyticDao.sales(year,location),"sales in ksh ");
    }

    public Graph salesByRetailer (Integer year, Integer retailerId) {
        Retailer retailer  = retailerDao.find(retailerId);
        if(retailer == null) {
            throw new RestException(Status.FAILED,"The retailer does not exist");
        }
        return build(analyticDao.sales(year,retailer),"sales in ksh ");
    }

    public Graph salesByLocationAndRetailer (Integer year,Integer locationId,Integer retailerId) {
        Location location  = locationDao.find(locationId);
        if(location == null) {
            throw new RestException(Status.FAILED,"The location does not exist");
        }
        Retailer retailer  = retailerDao.find(retailerId);
        if(retailer == null) {
            throw new RestException(Status.FAILED,"The retailer does not exist");
        }
        return build(analyticDao.sales(year,location,retailer),"sales in ksh ");
    }

    public Graph salesByLocationAndCarrier (Integer year,Integer locationId,Integer carrierId) {
        Location location  = locationDao.find(locationId);
        if(location == null) {
            throw new RestException(Status.FAILED,"The location does not exist");
        }
        Carrier carrier  = carrierDao.find(carrierId);
        if(carrier == null) {
            throw new RestException(Status.FAILED,"The carrier does not exist");
        }
        return build(analyticDao.sales(year,location,carrier),"sales in ksh ");
    }


    public Graph salesByCarrier (Integer year,Integer carrierId) {
        Carrier carrier  = carrierDao.find(carrierId);
        if(carrier == null) {
            throw new RestException(Status.FAILED,"The carrier does not exist");
        }
        return build(analyticDao.sales(year,carrier),"sales in ksh ");
    }

    public Graph carrierSales (Integer year,Integer locationId) {
        Location location  = locationDao.find(locationId);
        if(location == null) {
            throw new RestException(Status.FAILED,"The location does not exist");
        }
        return build(analyticDao.salesByCarriers(year, location),"sales in ksh ");
    }

    public Graph popularProducts (Integer year) {
        return buildCount(analyticDao.popularProducts(year));
    }

    public Graph popularProductsByRetailer (Integer year,Integer retailerId) {
        Retailer retailer  = retailerDao.find(retailerId);
        if(retailer == null) {
            throw new RestException(Status.FAILED,"The retailer does not exist");
        }
        return buildCount(analyticDao.popularProducts(year, retailer));
    }

    public Graph orders (Integer year) {
        return buildCount(analyticDao.orders(year));
    }

    public Graph prescriptions (Integer year) {
        return buildPrescription(analyticDao.prescriptions(year));
    }

    public Graph prescriptionsByMedic (Integer year,Integer medicId) {
        Medic medic = medicDao.find(medicId);
        if(medic == null) {
            throw new RestException(Status.FAILED,"The medic does not exist");
        }
        return buildPrescription(analyticDao.prescriptions(year, medic));
    }

    public Graph prescriptionsPie (Integer year) {
        List<Object[]> results = analyticDao.prescriptionsPie(year);
        Graph graph = new Graph();
        List<Dataset> datasets = new ArrayList<Dataset>();
        if(results != null) {
            Double[] data = new Double[1];
            Arrays.fill(data,0.0);
            for (Object[] row : results) {
                Dataset dataset = new Dataset();
                boolean dispensed = (Boolean)row[1];
                if(dispensed) {
                    dataset.setLabel("dispensed");
                    data[0] = ((BigInteger)row[0]).doubleValue();
                } else {
                    dataset.setLabel("un dispensed");
                    data[0] = ((BigInteger)row[0]).doubleValue();
                }
                dataset.setData(Arrays.asList(data));
                datasets.add(dataset);
            }
        }
        String[] labels = {"prescriptions"};
        graph.setLabels(Arrays.asList(labels));
        graph.setDatasets(datasets);
        return graph;
    }

    private Graph buildPrescription (List<Object[]> results) {
        Graph graph = new Graph();
        List<Dataset> datasets = new ArrayList<Dataset>();
        if(results != null) {
            Double[] data = new Double[12];
            Arrays.fill(data,0.0);
            for (Object[] row : results) {
                Dataset dataset = new Dataset();
                boolean dispensed = (Boolean)row[2];
                if(dispensed) {
                    dataset.setLabel("dispensed");
                } else {
                    dataset.setLabel("un dispensed");
                }

                data[((Integer)row[1]-1)] = ((BigInteger)row[0]).doubleValue();
                dataset.generateBackground();
                dataset.setData(Arrays.asList(data));
                datasets.add(dataset);
            }
        }

        graph.setLabels(Arrays.asList(months));
        graph.setDatasets(datasets);
        return graph;
    }

    private Graph buildCount (List<Object[]> results) {
        Graph graph = new Graph();
        List<Dataset> datasets = new ArrayList<Dataset>();
        graph.setLabels(Arrays.asList(months));
        if(results != null) {
            for (Object[] row : results) {
                Double[] data = new Double[12];
                Arrays.fill(data,0.0);
                Dataset dataset = new Dataset();
                dataset.setLabel((String)row[2]);
                data[((Integer)row[1]-1)] = ((BigInteger)row[0]).doubleValue();
                dataset.setData(Arrays.asList(data));
                dataset.generateBackground();
                datasets.add(dataset);
            }
        }
        graph.setDatasets(datasets);

        return graph;
    }

    private Graph build (List<Object[]> results,String label) {
        Graph graph = new Graph();
        List<Dataset> datasets = new ArrayList<Dataset>();
        Double[] data = new Double[12];
        Arrays.fill(data,0.0);
        graph.setLabels(Arrays.asList(months));
        Dataset dataset = new Dataset();
        dataset.setLabel(label);
        if(results != null) {
            for (Object[] row : results) {
                data[((int)(Integer)row[1]-1)] = (Double)row[0];
            }
        }
        dataset.setData(Arrays.asList(data));
        datasets.add(dataset);
        graph.setDatasets(datasets);

        return graph;
    }
}
