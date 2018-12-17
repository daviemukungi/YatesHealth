package com.oneshoppoint.yates.model;

import javax.persistence.*;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="orders")
public class Order extends Model {
    private String invoiceNo;
    private String customerFirstname;
    private String customerLastname;
    private String customerPhoneNumber;
    private String customerEmail;
    private String customerStreetAddress;
    private String customerResidentialName;
    private String customerLocation;
    private String productUUID;
    private String productName;
    private Double productPrice;
    private Integer productQuantity;
    private Double productTotalPrice;
    private String retailerName;
    private String retailerPhoneNumber;
    private String retailerEmail;
    private String retailerStreetAddress;
    private String retailerResidentialName;
    private String retailerLocation;
    private String retailerPayBillNo;
    private String retailerTransactionCode;
    private String carrierPlan;
    private String carrierName;
    private String carrierPhoneNumber;
    private String carrierEmail;
    private String carrierStreetAddress;
    private String carrierResidentialName;
    private String carrierLocation;
    private String carrierPayBillNo;
    private Double carrierPrice;
    private String carrierTransactionCode;
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

    @Column(name="customer_firstname",nullable = false)
    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerLastname (String customerLastname) {
        this.customerLastname = customerLastname;
    }

    @Column(name="customer_lastname",nullable = false)
    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerPhoneNumber (String customerPhoneNumber) {
        this.customerPhoneNumber  = customerPhoneNumber;
    }

    @Column(name="customer_phonenumber",nullable = false)
    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerEmail (String customerEmail) {
        this.customerEmail  = customerEmail;
    }

    @Column(name="customer_email",nullable = false)
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerStreetAddress (String customerStreetAddress) {
        this.customerStreetAddress  = customerStreetAddress;
    }

    @Column(name="customer_street_address",nullable = false)
    public String getCustomerStreetAddress() {
        return customerStreetAddress;
    }

    public void setCustomerResidentialName (String customerResidentialName) {
        this.customerResidentialName  = customerResidentialName;
    }

    @Column(name="customer_residential_name",nullable = false)
    public String getCustomerResidentialName() {
        return customerResidentialName;
    }

    public void setCustomerLocation (String customerLocation) {
        this.customerLocation  = customerLocation;
    }

    @Column(name="customer_location",nullable = false)
    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setProductUUID (String productUUID) {
        this.productUUID = productUUID;
    }

    @Column(name="product_UUID",nullable = false)
    public String getProductUUID() {
        return productUUID;
    }

    public void setProductName (String productName) {
        this.productName  = productName;
    }

    @Column(name="product_name",nullable = false)
    public String getProductName() {
        return productName;
    }

    public void setProductPrice (Double productPrice) {
        this.productPrice  = productPrice;
    }

    @Column(name="product_price",nullable = false)
    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductQuantity (Integer productQuantity) {
        this.productQuantity  = productQuantity;
    }

    @Column(name="product_quantity",nullable = false)
    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductTotalPrice (Double productTotalPrice) {
        this.productTotalPrice  = productTotalPrice;
    }

    @Column(name="product_total_price",nullable = false)
    public Double getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setRetailerName (String retailerName) {
        this.retailerName  = retailerName;
    }

    @Column(name="retailer_name",nullable = false)
    public String getRetailerName(){
        return retailerName;
    }

    public void setRetailerPhoneNumber (String retailerPhoneNumber) {
        this.retailerPhoneNumber  = retailerPhoneNumber;
    }

    @Column(name="retailer_phonenumber",nullable = false)
    public String getRetailerPhoneNumber(){
        return retailerPhoneNumber;
    }

    public void setRetailerEmail (String retailerEmail) {
        this.retailerEmail  = retailerEmail;
    }

    @Column(name="retailer_email",nullable = false)
    public String getRetailerEmail(){
        return retailerEmail;
    }

    public void setRetailerStreetAddress (String retailerStreetAddress) {
        this.retailerStreetAddress  = retailerStreetAddress;
    }

    @Column(name="retailer_street_address",nullable = false)
    public String getRetailerStreetAddress(){
        return retailerStreetAddress;
    }

    public void setRetailerResidentialName (String retailerResidentialName) {
        this.retailerResidentialName  = retailerResidentialName;
    }

    @Column(name="retailer_residential_name",nullable = false)
    public String getRetailerResidentialName(){
        return retailerResidentialName;
    }

    public void setRetailerLocation (String retailerLocation) {
        this.retailerLocation  = retailerLocation;
    }

    @Column(name="retailer_location",nullable = false)
    public String getRetailerLocation(){
        return retailerLocation;
    }

    public void setRetailerPayBillNo (String retailerPayBillNo) {
        this.retailerPayBillNo  = retailerPayBillNo;
    }

    @Column(name="retailer_pay_bill_no",nullable = false)
    public String getRetailerPayBillNo(){
        return retailerPayBillNo;
    }

    public void setRetailerTransactionCode (String retailerTransactionCode) {
        this.retailerTransactionCode  = retailerTransactionCode;
    }

    @Column(name="retailer_transaction_code",nullable = false)
    public String getRetailerTransactionCode(){
        return retailerTransactionCode;
    }

    public void setCarrierPlan (String carrierPlan) {
        this.carrierPlan  = carrierPlan;
    }

    @Column(name="carrier_plan")
    public String getCarrierPlan() {
        return carrierPlan;
    }

    public void setCarrierName (String carrierName) {
        this.carrierName  = carrierName;
    }

    @Column(name="carrier_name")
    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierPhoneNumber (String carrierPhoneNumber) {
        this.carrierPhoneNumber  = carrierPhoneNumber;
    }

    @Column(name="carrier_phonenumber")
    public String getCarrierPhoneNumber() {
        return carrierPhoneNumber;
    }

    public void setCarrierEmail (String carrierEmail) {
        this.carrierEmail  = carrierEmail;
    }

    @Column(name="carrier_email")
    public String getCarrierEmail() {
        return carrierEmail;
    }

    public void setCarrierStreetAddress (String carrierStreetAddress) {
        this.carrierStreetAddress  = carrierStreetAddress;
    }

    @Column(name="carrier_street_address")
    public String getCarrierStreetAddress() {
        return carrierStreetAddress;
    }

    public void setCarrierResidentialName (String carrierResidentialName) {
        this.carrierResidentialName  = carrierResidentialName;
    }

    @Column(name="carrier_residential_name")
    public String getCarrierResidentialName() {
        return carrierResidentialName;
    }

    public void setCarrierLocation (String carrierLocation) {
        this.carrierLocation  = carrierLocation;
    }

    @Column(name="carrier_location")
    public String getCarrierLocation() {
        return carrierLocation;
    }

    public void setCarrierPayBillNo (String carrierPayBillNo) {
        this.carrierPayBillNo  = carrierPayBillNo;
    }

    @Column(name="carrier_pay_bill_no")
    public String getCarrierPayBillNo() {
        return carrierPayBillNo;
    }

    public void setCarrierTransactionCode (String carrierTransactionCode) {
        this.carrierTransactionCode  = carrierTransactionCode;
    }

    @Column(name="carrier_transaction_code")
    public String getCarrierTransactionCode(){
        return carrierTransactionCode;
    }

    public void setCarrierPrice (Double carrierPrice) {
        this.carrierPrice  = carrierPrice;
    }

    @Column(name="carrier_price")
    public Double getCarrierPrice() {
        return carrierPrice;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    @Column(name="order_status",nullable = false)
    public String getStatus() {
        return status;
    }
}
