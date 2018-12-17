package com.oneshoppoint.yates.repository.impl;


import com.oneshoppoint.yates.model.Order;
import com.oneshoppoint.yates.repository.OrderDao;
import com.oneshoppoint.yates.wrapper.OrderSummary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
@Repository
public class OrderDaoImpl extends  GenericDaoImpl<Order> implements OrderDao {
    @PersistenceContext
    EntityManager entityManager;

    public OrderDaoImpl() {
        super(Order.class);
    }

    public List<OrderSummary> getOrderSummariesByRetailer(String email) {
        TypedQuery<OrderSummary> query = entityManager.createQuery("SELECT DISTINCT new com.oneshoppoint.yates.wrapper.OrderSummary(o.invoiceNo,o.customerFirstname,o.customerLastname,o.status,o.createdOn) FROM Order o  WHERE o.retailerEmail =:email  AND o.deletedBy IS NULL AND o.deletedOn IS NULL ",OrderSummary.class);
        query.setParameter("email",email);
        List<OrderSummary> result = query.getResultList();
        Collections.sort(result);
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<OrderSummary> getOrderSummariesByCarrier(String email) {
        TypedQuery<OrderSummary> query = entityManager.createQuery("SELECT DISTINCT new com.oneshoppoint.yates.wrapper.OrderSummary(o.invoiceNo,o.customerFirstname,o.customerLastname,o.status,o.createdOn) FROM Order o  WHERE o.carrierEmail =:email AND (o.status = :statusOne OR o.status = :statusTwo OR o.status = :statusThree OR o.status = :statusFour) AND o.deletedBy IS NULL AND o.deletedOn IS NULL ",OrderSummary.class);
        query.setParameter("statusOne","received by carrier");
        query.setParameter("statusTwo","transporting");
        query.setParameter("statusThree","completed");
        query.setParameter("statusFour","prepared");
        query.setParameter("email",email);
        List<OrderSummary> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<OrderSummary> getOrderSummariesByStatus(String status) {
        TypedQuery<OrderSummary> query = entityManager.createQuery("SELECT DISTINCT new com.oneshoppoint.yates.wrapper.OrderSummary(o.invoiceNo,o.customerFirstname,o.customerLastname,o.status,o.createdOn) FROM Order o  WHERE  o.status = :status AND o.deletedBy IS NULL AND o.deletedOn IS NULL ",OrderSummary.class);
        query.setParameter("status",status);
        List<OrderSummary> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<OrderSummary> getOrderSummariesByCustomer(String email) {
        TypedQuery<OrderSummary> query = entityManager.createQuery("SELECT DISTINCT new com.oneshoppoint.yates.wrapper.OrderSummary(o.invoiceNo,o.customerFirstname,o.customerLastname,o.status,o.createdOn,sum(o.productTotalPrice)) FROM Order o  WHERE o.customerEmail =:email  AND o.deletedBy IS NULL AND o.deletedOn IS NULL GROUP BY o.invoiceNo",OrderSummary.class);
        query.setParameter("email",email);
        List<OrderSummary> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<OrderSummary> getAllOrders() {
        TypedQuery<OrderSummary> query = entityManager.createQuery("SELECT DISTINCT new com.oneshoppoint.yates.wrapper.OrderSummary(o.invoiceNo,o.customerFirstname,o.customerLastname,o.status,o.createdOn,sum(o.productTotalPrice)) FROM Order o  WHERE o.deletedBy IS NULL AND o.deletedOn IS NULL GROUP BY o.invoiceNo ",OrderSummary.class);
        List<OrderSummary> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<OrderSummary> search (String pattern) {
        TypedQuery<OrderSummary> query = entityManager.createQuery("SELECT DISTINCT new com.oneshoppoint.yates.wrapper.OrderSummary(o.invoiceNo,o.customerFirstname,o.customerLastname,o.status,o.createdOn,sum(o.productTotalPrice)) FROM Order o  WHERE (o.customerFirstname like :pattern OR o.customerLastname like :pattern OR o.invoiceNo like :pattern) AND  o.deletedBy IS NULL AND o.deletedOn IS NULL GROUP BY o.invoiceNo ",OrderSummary.class);
        pattern = "%"+pattern+"%";
        query.setParameter("pattern",pattern);
        List<OrderSummary> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Integer> getYears() {
        Query query = entityManager.createNativeQuery("SELECT DISTINCT YEAR(o.created_on) AS years FROM orders o  WHERE o.deleted_by IS NULL AND o.deleted_on IS NULL ORDER BY years");

        List<Integer> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Order> getOrderByInvoiceNo(String invoiceNo) {
        TypedQuery<Order> query = entityManager.createQuery("SELECT o FROM Order o WHERE o.invoiceNo =:invoiceNo  AND o.deletedBy IS NULL AND o.deletedOn IS NULL ",Order.class);
        query.setParameter("invoiceNo",invoiceNo);
        List<Order> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }
}
