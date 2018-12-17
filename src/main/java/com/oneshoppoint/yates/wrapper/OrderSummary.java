package com.oneshoppoint.yates.wrapper;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * Created by robinson on 4/27/16.
 */
public class OrderSummary implements Comparable<OrderSummary> {
    private String invoiceNo;
    private String firstname;
    private String lastname;
    private String status;
    private Date dateOrdered;
    private Double total;

    public OrderSummary (String invoiceNo,String firstname,String lastname,String status,Date dateOrdered) {
        this.invoiceNo = invoiceNo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
        this.dateOrdered = dateOrdered;
    }

    public OrderSummary (String invoiceNo,String firstname,String lastname,String status,Date dateOrdered,Double total) {
        this.invoiceNo = invoiceNo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.status = status;
        this.dateOrdered = dateOrdered;
        this.total  = total;
    }

    public void setInvoiceNo (String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setFirstname (String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname () {
        return firstname;
    }

    public void setLastname (String lastname) {
        this.lastname = lastname;
    }

    public String getLastname () {
        return lastname;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public String getStatus () {
        return this.status;
    }

    public void setDateOrdered (Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public Date getDateOrdered () {
        return dateOrdered;
    }

    public void setTotal (Double total) {
        this.total = total;
    }

    public Double getTotal () {
        return total;
    }

    public int compareTo (@Nonnull OrderSummary other) {
        return  this.dateOrdered.compareTo(other.getDateOrdered());
    }
}
