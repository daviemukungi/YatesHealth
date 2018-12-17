package com.oneshoppoint.yates.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 4/27/16.
 */
public class Invoice {
    private String invoiceNo;
    private String customerFirstname;
    private String customerLastname;
    private String customerPhoneNumber;
    private String customerEmail;
    private String customerStreetAddress;
    private String customerResidentialName;
    private String customerLocation;
    private List<InvoiceItem> invoiceItems;
    private String retailerName;
    private String retailerPhoneNumber;
    private String retailerEmail;
    private String retailerStreetAddress;
    private String retailerResidentialName;
    private String retailerLocation;
    private String retailerPayBillNo;
    private String retailerTransactionCode;
    private String carrierName;
    private String carrierPhoneNumber;
    private String carrierEmail;
    private String carrierStreetAddress;
    private String carrierResidentialName;
    private String carrierLocation;
    private String carrierPayBillNo;
    private Double carrierPrice;
    private String carrierTransactionCode;
    private Double total;
    private String status;

    public void setInvoiceNo (String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo () {
        return this.invoiceNo;
    }

    public void setCustomerFirstname (String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerLastname (String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerPhoneNumber (String customerPhoneNumber) {
        this.customerPhoneNumber  = customerPhoneNumber;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerEmail (String customerEmail) {
        this.customerEmail  = customerEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerStreetAddress (String customerStreetAddress) {
        this.customerStreetAddress  = customerStreetAddress;
    }

    public String getCustomerStreetAddress() {
        return customerStreetAddress;
    }

    public void setCustomerResidentialName (String customerResidentialName) {
        this.customerResidentialName  = customerResidentialName;
    }

    public String getCustomerResidentialName() {
        return customerResidentialName;
    }

    public void setCustomerLocation (String customerLocation) {
        this.customerLocation  = customerLocation;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setRetailerName (String retailerName) {
        this.retailerName  = retailerName;
    }

    public String getRetailerName(){
        return retailerName;
    }

    public void setRetailerPhoneNumber (String retailerPhoneNumber) {
        this.retailerPhoneNumber  = retailerPhoneNumber;
    }

    public String getRetailerPhoneNumber(){
        return retailerPhoneNumber;
    }

    public void setRetailerEmail (String retailerEmail) {
        this.retailerEmail  = retailerEmail;
    }

    public String getRetailerEmail(){
        return retailerEmail;
    }

    public void setRetailerStreetAddress (String retailerStreetAddress) {
        this.retailerStreetAddress  = retailerStreetAddress;
    }

    public String getRetailerStreetAddress(){
        return retailerStreetAddress;
    }

    public void setRetailerResidentialName (String retailerResidentialName) {
        this.retailerResidentialName  = retailerResidentialName;
    }

    public String getRetailerResidentialName(){
        return retailerResidentialName;
    }

    public void setRetailerLocation (String retailerLocation) {
        this.retailerLocation  = retailerLocation;
    }

    public String getRetailerLocation(){
        return retailerLocation;
    }

    public void setRetailerPayBillNo (String retailerPayBillNo) {
        this.retailerPayBillNo  = retailerPayBillNo;
    }

    public String getRetailerPayBillNo(){
        return retailerPayBillNo;
    }

    public void setRetailerTransactionCode (String retailerTransactionCode) {
        this.retailerTransactionCode  = retailerTransactionCode;
    }

    public String getRetailerTransactionCode(){
        return retailerTransactionCode;
    }

    public void setCarrierName (String carrierName) {
        this.carrierName  = carrierName;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierPhoneNumber (String carrierPhoneNumber) {
        this.carrierPhoneNumber  = carrierPhoneNumber;
    }

    public String getCarrierPhoneNumber() {
        return carrierPhoneNumber;
    }

    public void setCarrierEmail (String carrierEmail) {
        this.carrierEmail  = carrierEmail;
    }

    public String getCarrierEmail() {
        return carrierEmail;
    }

    public void setCarrierStreetAddress (String carrierStreetAddress) {
        this.carrierStreetAddress  = carrierStreetAddress;
    }

    public String getCarrierStreetAddress() {
        return carrierStreetAddress;
    }

    public void setCarrierResidentialName (String carrierResidentialName) {
        this.carrierResidentialName  = carrierResidentialName;
    }

    public String getCarrierResidentialName() {
        return carrierResidentialName;
    }

    public void setCarrierLocation (String carrierLocation) {
        this.carrierLocation  = carrierLocation;
    }

    public String getCarrierLocation() {
        return carrierLocation;
    }

    public void setCarrierPayBillNo (String carrierPayBillNo) {
        this.carrierPayBillNo  = carrierPayBillNo;
    }

    public String getCarrierPayBillNo() {
        return carrierPayBillNo;
    }

    public void setCarrierTransactionCode (String carrierTransactionCode) {
        this.carrierTransactionCode  = carrierTransactionCode;
    }

    public String getCarrierTransactionCode(){
        return carrierTransactionCode;
    }

    public void setCarrierPrice (Double carrierPrice) {
        this.carrierPrice  = carrierPrice;
    }

    public Double getCarrierPrice() {
        return carrierPrice;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setTotal (Double total) {
        this.total = total;
    }

    public Double getTotal() {
        return total;
    }

    public void setInvoiceItems (List<InvoiceItem> invoiceItems) {
        this.invoiceItems = new ArrayList<InvoiceItem>();
        this.invoiceItems.addAll(invoiceItems);
    }

    public List<InvoiceItem> getInvoiceItems () {
        return this.invoiceItems;
    }
}
