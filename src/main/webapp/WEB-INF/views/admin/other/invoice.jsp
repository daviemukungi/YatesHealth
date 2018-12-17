<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: robinson
  Date: 4/9/16
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href='<c:url value="resources/vendor/plugins/select2/css/core.css"/>'>
<div class="col-md-12 panel mb25 mt5">
  <div class="panel-heading">
    <span class="panel-title hidden-xs"> INVOICE </span>
    <ul class="nav panel-tabs-border panel-tabs">
      <li class="active">
        <button onclick="getContent('/order/view/admin/table?page=1&parts=20')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
      </li>
    </ul>
  </div>
  <div class="panel-body p20 pb10">
    <div class="tab-content pn br-n admin-form">
      <div id="tab1_1" class="tab-pane active">

        <div class="section row mbn">
          <div class="table-responsive">
            <table class="table table-bordered">
              <thead>
              <tr>
                <th colspan="2">Product</th>
                <th>Quantity</th>
                <th>Unit price</th>
                <th>Total</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${invoice.invoiceItems}" var="item">
                <tr>
                  <td>
                    <a href="${pageContext.request.contextPath}/product?uuid=${item.productUUID}">
                      <img src='<c:url value="/images/${item.productImage.path}"/>' style="width: 150px; height: 100px;" alt="${item.productName}">
                    </a>
                  </td>
                  <td><a href="${pageContext.request.contextPath}/product?uuid=${item.productUUID}">${item.productName}</a>
                  </td>
                  <td>${item.productQuantity}</td>
                  <td>${item.productPrice}</td>
                  <td>${item.productTotalPrice}</td>
                </tr>
              </c:forEach>
              </tbody>
              <tfoot>
              <tr>
                <th colspan="4" class="text-right">Order subtotal</th>
                <th>ksh. ${invoice.total}</th>
              </tr>
              <tr>
                <th colspan="4" class="text-right">Shipping and handling</th>
                <th>ksh. ${invoice.carrierPrice}</th>
              </tr>
              <tr>
                <th colspan="4" class="text-right">Total</th>
                <th>${invoice.total + invoice.carrierPrice}</th>
              </tr>
              </tfoot>
            </table>

          </div>
          <!-- /.table-responsive -->

          <div class="row addresses">
              <div class="col-sm-4">
                <h3 class="text-uppercase">Customer address</h3>
                <p>${invoice.customerFirstname}&nbsp&nbsp${invoice.customerLastname}
                  <br>${invoice.customerPhoneNumber}
                  <br>${invoice.customerEmail}
                  <br>${invoice.customerStreetAddress}
                  <br>${invoice.customerResidentialName}
                  <br>${invoice.customerLocation}</p>
              </div>
              <div class="col-sm-4">
                <h3 class="text-uppercase">Carrier address</h3>
                <p>${invoice.carrierName}
                  <br>${invoice.carrierPhoneNumber}
                  <br>${invoice.carrierEmail}
                  <br>${invoice.carrierStreetAddress}
                  <br>${invoice.carrierResidentialName}
                  <br>${invoice.carrierLocation}</p>
              </div>
              <div class="col-sm-4">
                <h3 class="text-uppercase">Retailer address</h3>
                <p>${invoice.retailerName}
                  <br>${invoice.retailerPhoneNumber}
                  <br>${invoice.retailerEmail}
                  <br>${invoice.retailerStreetAddress}
                  <br>${invoice.retailerResidentialName}
                  <br>${invoice.retailerLocation}</p>
              </div>
          </div>
          <!-- /.addresses -->
        </div>
      </div>
    </div>
  </div>
</div>