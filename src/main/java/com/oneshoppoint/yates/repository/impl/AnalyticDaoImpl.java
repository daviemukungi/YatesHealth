package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.Carrier;
import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.model.Medic;
import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.repository.AnalyticDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by robinson on 5/10/16.
 */

@Repository
public class AnalyticDaoImpl implements AnalyticDao {
    @PersistenceContext
    EntityManager entityManager;

    public List<Object[]> sales (Integer year,Location location) {
        Queue<Location> locationQueue = new LinkedList<Location>();
        locationQueue.add(location);
        String locations="";
        while(!locationQueue.isEmpty()) {
            Location node = locationQueue.remove();
            Set<Location> children = node.getChildren();
            if (children != null && !children.isEmpty()) {
                locationQueue.addAll(children);
            }

            locations+="'"+node.getName()+"("+node.getLabel()+")"+"'";
            if(!locationQueue.isEmpty()) {
                locations+=",";
            }
        }
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT SUM(o.product_total_price) AS sale ,MONTH(o.created_on) AS order_month,retailer_location FROM orders o WHERE YEAR(o.created_on) =:orderYear AND o.order_status ='completed' AND o.deleted_by IS NULL AND o.deleted_on IS NULL GROUP BY order_month) o WHERE retailer_location IN ("+locations+") ORDER BY o.order_month ASC");
        query.setParameter("orderYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> sales (Integer year,Location location,Retailer retailer) {
        Queue<Location> locationQueue = new LinkedList<Location>();
        locationQueue.add(location);
        String locations="";
        while(!locationQueue.isEmpty()) {
            Location node = locationQueue.remove();
            Set<Location> children = node.getChildren();
            if (children != null && !children.isEmpty()) {
                locationQueue.addAll(children);
            }

            locations+="'"+node.getName()+"("+node.getLabel()+")"+"'";
            if(!locationQueue.isEmpty()) {
                locations+=",";
            }
        }
        Queue<Retailer> retailerQueue = new LinkedList<Retailer>();
        retailerQueue.add(retailer);
        String retailers="";
        while(!retailerQueue.isEmpty()) {
            Retailer node = retailerQueue.remove();
            Set<Retailer> outlet = node.getOutlets();
            if(outlet != null && !outlet.isEmpty()) {
                retailerQueue.addAll(outlet);
            }

            retailers+="'"+retailer.getName()+"'";
            if(!retailerQueue.isEmpty()) {
                retailers+=",";
            }
        }

        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT SUM(o.product_total_price) AS sale ,MONTH(o.created_on) AS order_month,retailer_location,retailer_name FROM orders o WHERE YEAR(o.created_on) =:orderYear AND o.deleted_by IS NULL AND o.order_status ='completed' AND o.deleted_on IS NULL GROUP BY order_month) o WHERE retailer_location IN ("+locations+") AND retailer_name IN ("+retailers+") ORDER BY o.order_month ASC");
        query.setParameter("orderYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> sales (Integer year,Retailer retailer) {
        Queue<Retailer> retailerQueue = new LinkedList<Retailer>();
        retailerQueue.add(retailer);
        String retailers="";
        while(!retailerQueue.isEmpty()) {
            Retailer node = retailerQueue.remove();
            Set<Retailer> outlet = node.getOutlets();
            if (outlet != null && !outlet.isEmpty()) {
                retailerQueue.addAll(outlet);
            }

            retailers+="'"+retailer.getName()+"'";
            if(!retailerQueue.isEmpty()) {
                retailers+=",";
            }
        }
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT SUM(o.product_total_price) AS sale ,MONTH(o.created_on) AS order_month,retailer_name FROM orders o WHERE YEAR(o.created_on) =:orderYear AND o.order_status ='completed' AND o.deleted_by IS NULL AND o.deleted_on IS NULL GROUP BY order_month) o WHERE retailer_name IN ("+retailers+") ORDER BY o.order_month ASC");
        query.setParameter("orderYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> salesByCarriers (Integer year,Location location) {
        Queue<Location> locationQueue = new LinkedList<Location>();
        locationQueue.add(location);
        String locations="";
        while(!locationQueue.isEmpty()) {
            Location node = locationQueue.remove();
            Set<Location> children = node.getChildren();
            if (children != null && !children.isEmpty()) {
                locationQueue.addAll(children);
            }

            locations+="'"+node.getName()+"("+node.getLabel()+")"+"'";
            if(!locationQueue.isEmpty()) {
                locations+=",";
            }
        }
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT SUM(o.carrier_price) AS sale ,MONTH(o.created_on) AS order_month,carrier_location FROM (SELECT DISTINCT o.carrier_price,o.created_on,o.carrier_location,o.carrier_name,o.order_status FROM orders o WHERE o.deleted_by IS NULL AND o.deleted_on IS NULL ) o WHERE YEAR(o.created_on) =:orderYear AND o.order_status ='completed' GROUP BY order_month) o WHERE carrier_location IN ("+locations+") ORDER BY o.order_month ASC");
        query.setParameter("orderYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }


    public List<Object[]> sales (Integer year,Location location,Carrier carrier) {
        Queue<Location> locationQueue = new LinkedList<Location>();
        locationQueue.add(location);
        String locations="";
        while(!locationQueue.isEmpty()) {
            Location node = locationQueue.remove();
            Set<Location> children = node.getChildren();
            if (children != null && !children.isEmpty()) {
                locationQueue.addAll(children);
            }

            locations+="'"+node.getName()+"("+node.getLabel()+")"+"'";
            if(!locationQueue.isEmpty()) {
                locations+=",";
            }
        }
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT SUM(o.carrier_price) AS sale,MONTH(o.created_on) AS order_month,carrier_location,carrier_name FROM (SELECT DISTINCT o.carrier_price,o.created_on,o.carrier_location,o.carrier_name,o.order_status FROM orders o WHERE o.deleted_by IS NULL AND o.deleted_on IS NULL ) o WHERE YEAR(o.created_on) =:orderYear AND o.order_status ='completed'  GROUP BY order_month) o WHERE carrier_location IN ("+locations+") AND carrier_name =:carrier_name ORDER BY o.order_month ASC");
        query.setParameter("orderYear",year);
        query.setParameter("carrier_name",carrier.getName());
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> sales (Integer year,Carrier carrier) {
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT SUM(o.carrier_price) AS sale,MONTH(o.created_on) AS order_month,carrier_name FROM (SELECT DISTINCT o.carrier_price,o.created_on,o.carrier_location,o.carrier_name,o.order_status FROM orders o WHERE o.deleted_by IS NULL AND o.deleted_on IS NULL ) o WHERE YEAR(o.created_on) =:orderYear AND o.order_status ='completed'  GROUP BY order_month) o WHERE  carrier_name =:carrier_name ORDER BY o.order_month ASC");
        query.setParameter("orderYear",year);
        query.setParameter("carrier_name",carrier.getName());
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> popularProducts (Integer year) {
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT count(o.product_name) AS products,MONTH(o.created_on) as order_month,o.product_name FROM orders o WHERE YEAR(o.created_on) =:orderYear AND o.deleted_by IS NULL AND o.deleted_on IS NULL GROUP BY o.product_name) o ORDER BY products ASC");
        query.setParameter("orderYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> popularProducts (Integer year,Retailer retailer) {
        Queue<Retailer> retailerQueue = new LinkedList<Retailer>();
        retailerQueue.add(retailer);
        String retailers="";
        while(!retailerQueue.isEmpty()) {
            Retailer node = retailerQueue.remove();
            Set<Retailer> outlet = node.getOutlets();
            if (outlet != null && !outlet.isEmpty()) {
                retailerQueue.addAll(outlet);
            }

            retailers+="'"+node.getName()+"'";
            if(!retailerQueue.isEmpty()) {
                retailers+=",";
            }
        }
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT count(o.product_name) AS products,MONTH(o.created_on) as order_month,o.product_name,retailer_name FROM orders o WHERE YEAR(o.created_on) =:orderYear AND o.deleted_by IS NULL AND o.deleted_on IS NULL GROUP BY o.retailer_name,o.product_name) o WHERE retailer_name IN ("+retailers+") ORDER BY products ASC");
        query.setParameter("orderYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> orders (Integer year) {
        Query query = entityManager.createNativeQuery("SELECT DISTINCT count(o.invoiceNo),MONTH(o.created_on) as order_month,COUNT(o.order_status) AS order_status FROM orders o WHERE YEAR(o.created_on) =:orderYear AND o.deleted_by IS NULL AND o.deleted_on IS NULL GROUP BY o.order_status");
        query.setParameter("orderYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> prescriptions (Integer year) {
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT COUNT(p.code) AS codes ,MONTH(p.created_on) AS prescription_month,p.dispensed FROM prescriptions p WHERE YEAR(p.created_on) =:prescriptionYear AND p.deleted_by IS NULL AND p.deleted_on IS NULL GROUP BY p.dispensed) o ORDER BY  o.prescription_month ASC");
        query.setParameter("prescriptionYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> prescriptions (Integer year,Medic medic) {
        Query query = entityManager.createNativeQuery("SELECT * FROM (SELECT COUNT(p.code) AS codes,MONTH(p.created_on) AS prescription_month,p.dispensed FROM prescriptions p INNER JOIN medics m ON (p.medic_id = m.id) WHERE m.id = :medicId AND YEAR(p.created_on) =:prescriptionYear AND p.deleted_by IS NULL AND p.deleted_on IS NULL GROUP BY p.dispensed) o ORDER BY  o.prescription_month ASC");
        query.setParameter("prescriptionYear",year);
        query.setParameter("medicId",medic.getId());
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

    public List<Object[]> prescriptionsPie (Integer year) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(p.code) AS codes,p.dispensed FROM prescriptions p WHERE YEAR(p.created_on) =:prescriptionYear AND p.deleted_by IS NULL AND p.deleted_on IS NULL GROUP BY p.dispensed");
        query.setParameter("prescriptionYear",year);
        List<Object[]> results  = query.getResultList();
        if(results.isEmpty()) {
            return null;
        }

        return results;
    }

}
