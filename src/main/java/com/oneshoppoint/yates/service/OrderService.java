package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.wrapper.Invoice;
import com.oneshoppoint.yates.wrapper.OrderSummary;

import java.util.List;

/**
 * Created by robinson on 4/27/16.
 */
public interface OrderService {
    List<OrderSummary> getOrderSummariesByRetailer(Integer id);
    List<OrderSummary> getOrderSummariesByCarrier(Integer id);
    List<OrderSummary> getOrderSummaryByCustomer();
    List<OrderSummary> getOrderSummariesByStatus(String status);
    List<OrderSummary> search (String pattern);
    List<OrderSummary> getAll();
    List<Integer> getYears();
    Invoice getInvoice(String invoiceNo);
    String updateStatus (String invoiceNo);
}
