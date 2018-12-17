package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.Order;
import com.oneshoppoint.yates.wrapper.OrderSummary;

import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
public interface OrderDao extends GenericDao<Order> {
    List<OrderSummary> getOrderSummariesByRetailer(String email);
    List<OrderSummary> getOrderSummariesByCarrier(String email);
    List<OrderSummary> getOrderSummariesByStatus(String status);
    List<OrderSummary> getOrderSummariesByCustomer(String email);
    List<OrderSummary> getAllOrders();
    List<Integer> getYears();
    List<Order> getOrderByInvoiceNo(String invoiceNo);
    List<OrderSummary> search (String pattern);
}
