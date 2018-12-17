package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Carrier;
import com.oneshoppoint.yates.model.Order;
import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.repository.OrderDao;
import com.oneshoppoint.yates.repository.ProductDao;
import com.oneshoppoint.yates.repository.RetailerDao;
import com.oneshoppoint.yates.service.OrderService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.Invoice;
import com.oneshoppoint.yates.wrapper.InvoiceItem;
import com.oneshoppoint.yates.wrapper.OrderSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 4/27/16.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    RetailerDao retailerDao;
    @Autowired
    GenericDao<Carrier> carrierDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    CurrentUser currentUser;

    public List<OrderSummary> getOrderSummariesByRetailer(Integer id) {
        return orderDao.getOrderSummariesByRetailer(retailerDao.find(id).getAddress().getEmail());
    }

    public List<OrderSummary> getOrderSummariesByCarrier(Integer id) {
        return orderDao.getOrderSummariesByCarrier(carrierDao.find(id).getAddress().getEmail());
    }

    public List<OrderSummary> getOrderSummariesByStatus(String status) {
        if(status.equals("ordered") ||
           status.equals("received by retailer") ||
           status.equals("received by carrier") ||
           status.equals("transporting") ||
           status.equals("completed") ||
           status.equals("prepared")
           ){
            return orderDao.getOrderSummariesByStatus(status);
        }

        throw new RestException(Status.FAILED,"The status does not exist");
    }

    public List<OrderSummary> search (String pattern) {
        return orderDao.search(pattern);
    }

    public List<OrderSummary> getOrderSummaryByCustomer () {
        return orderDao.getOrderSummariesByCustomer(currentUser.getUser().getAddress().getEmail());
    }

    public List<OrderSummary> getAll () {
        return orderDao.getAllOrders();
    }

    public List<Integer> getYears() {
        return orderDao.getYears();
    }

    public Invoice getInvoice(String invoiceNo) {
        List<Order> orderItems = orderDao.getOrderByInvoiceNo(invoiceNo);
        if(orderItems.isEmpty()) {
           return null;
        }

        List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
        double total = 0.0;
        for(Order order : orderItems) {
            InvoiceItem item = new InvoiceItem();
            item.setProductName(order.getProductName());
            item.setProductPrice(order.getProductPrice());
            item.setProductQuantity(order.getProductQuantity());
            item.setProductTotalPrice(order.getProductTotalPrice());
            item.setProductUUID(order.getProductUUID());
            Product product = productDao.getByUUID(order.getProductUUID());
            if(product != null){
                item.setProductImage(product.getPrimaryImage());
            }
            invoiceItems.add(item);
            total+=item.getProductTotalPrice();
        }
        Order order = orderItems.get(0);
        Invoice invoice = new Invoice();
        invoice.setInvoiceNo(order.getInvoiceNo());
        invoice.setCarrierEmail(order.getCarrierEmail());
        invoice.setCarrierStreetAddress(order.getCarrierStreetAddress());
        invoice.setCarrierResidentialName(order.getCarrierResidentialName());
        invoice.setCarrierLocation(order.getCarrierLocation());
        invoice.setCarrierName(order.getCarrierName());
        invoice.setCarrierPayBillNo(order.getCarrierPayBillNo());
        invoice.setCarrierPrice(order.getCarrierPrice());
        invoice.setCarrierPhoneNumber(order.getCarrierPhoneNumber());
        invoice.setCustomerEmail(order.getCustomerEmail());
        invoice.setCustomerPhoneNumber(order.getCustomerPhoneNumber());
        invoice.setCustomerFirstname(order.getCustomerFirstname());
        invoice.setCustomerLastname(order.getCustomerLastname());
        invoice.setCustomerLocation(order.getCustomerLocation());
        invoice.setCustomerResidentialName(order.getCustomerResidentialName());
        invoice.setCustomerStreetAddress(order.getCustomerStreetAddress());
        invoice.setRetailerEmail(order.getRetailerEmail());
        invoice.setRetailerLocation(order.getRetailerLocation());
        invoice.setRetailerName(order.getRetailerName());
        invoice.setRetailerPayBillNo(order.getRetailerPayBillNo());
        invoice.setRetailerPhoneNumber(order.getRetailerPhoneNumber());
        invoice.setInvoiceItems(invoiceItems);
        invoice.setTotal(total);
        invoice.setStatus(order.getStatus());
        return invoice;
    }

    public String updateStatus (String invoiceNo) {
        List<Order> orders = orderDao.getOrderByInvoiceNo(invoiceNo);
        Carrier carrier = currentUser.getUser().getAffiliate().getCarrier();
        Retailer retailer = currentUser.getUser().getAffiliate().getRetailer();
        for(Order order : orders) {
            if(retailer != null) {
                if(order.getStatus().equalsIgnoreCase("ordered")) {
                    order.setStatus("received by retailer");
                } else if (order.getStatus().equalsIgnoreCase("received by retailer") && order.getCarrierEmail() != null) {
                    order.setStatus("prepared");
                } else if (order.getStatus().equalsIgnoreCase("received by retailer")) {
                    order.setStatus("completed");
                }
            } else if (carrier != null) {
                if(order.getStatus().equalsIgnoreCase("prepared")) {
                    order.setStatus("received by carrier");
                } else if (order.getStatus().equalsIgnoreCase("received by carrier")) {
                    order.setStatus("transporting");
                } else if (order.getStatus().equalsIgnoreCase("transporting")) {
                    order.setStatus("completed");
                }
            }

            orderDao.update(order);
        }

        return orders.get(0).getStatus();
    }
}
