<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:include page="../common/header.jsp"/>
<div id="heading-breadcrumbs">
    <div class="container">
        <div class="row">
            <div class="col-md-7">
                <h1>E-Shop</h1>
            </div>
            <div class="col-md-5">
                <ul class="breadcrumb">
                    <li><a href="/">Home</a>
                    </li>
                    <c:if test="${retailer == true}">
                        <li>Retailer</li>
                    </c:if>
                    <c:if test="${carrier == true}">
                        <li>Carrier</li>
                    </c:if>
                    <c:if test="${customer == true}">
                        <li>Customer</li>
                    </c:if>
                </ul>

            </div>
        </div>
    </div>
</div>

<div id="content">
    <div class="container">
<div class="row">
    <!-- *** RIGHT COLUMN ***
  _________________________________________________________ -->

    <div class="col-md-3">
        <!-- *** CUSTOMER MENU ***
    _________________________________________________________ -->
        <div class="panel panel-default sidebar-menu">

            <div class="panel-heading">
                <c:if test="${retailer == true}">
                    <h3 class="panel-title">Retailer's section</h3>
                </c:if>
                <c:if test="${carrier == true}">
                    <h3 class="panel-title">Carrier's section</h3>
                </c:if>
                <c:if test="${customer == true}">
                    <h3 class="panel-title">Customer's section</h3>
                </c:if>
            </div>

            <div class="panel-body">
                <c:if test="${retailer == true}">
                    <ul class="nav nav-pills nav-stacked">
                        <li>
                            <a href="${pageContext.request.contextPath}/retailer"><i class="fa fa-building"></i>Outlets</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/retailer/stock"><i class="fa fa-briefcase"></i>Stocks</a>
                        </li>
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/retailer/order"><i class="fa fa-shopping-cart"></i>Orders</a>
                        </li>
                        <c:if test="${pharmacy == true}">
                            <li>
                                <a href="${pageContext.request.contextPath}/retailer/prescriptions"><i class="fa fa-heartbeat"></i>Prescriptions</a>
                            </li>
                        </c:if>
                        <li>
                            <a href="${pageContext.request.contextPath}/retailer/account"><i class="fa fa-heart"></i> My account</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i class="fa fa-sign-out"></i> Logout</a>
                        </li>
                    </ul>
                </c:if>
                <c:if test="${carrier == true}">
                    <ul class="nav nav-pills nav-stacked">
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/carrier"><i class="fa fa-shopping-cart" aria-hidden="true"></i> Orders</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/carrier/plan"><i class="fa fa-list"></i> Plans</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/carrier/account"><i class="fa fa-heart"></i> My account</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i class="fa fa-sign-out"></i> Logout</a>
                        </li>
                    </ul>
                </c:if>
                <c:if test="${customer == true}">
                    <ul class="nav nav-pills nav-stacked">
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/customer"><i class="fa fa-bars"></i>My Orders</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/customer/wishlist"><i class="fa fa-heart"></i>My Wishlist</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/customer/account"><i class="fa fa-user"></i> My account</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/customer/logout"><i class="fa fa-sign-out"></i> Logout</a>
                        </li>
                    </ul>
                </c:if>
            </div>

        </div>
        <!-- /.col-md-3 -->

        <!-- *** CUSTOMER MENU END *** -->
    </div>

    <!-- *** RIGHT COLUMN END *** -->

  <!-- *** LEFT COLUMN ***
_________________________________________________________ -->

    <div class="col-md-9 clearfix" id="customer-order">

        <p class="lead"># ${invoice.invoiceNo}. <span id='statusBtn' onclick="passData('/api/order/${invoice.invoiceNo}/update_status','POST',null,refresh)" class="btn btn-info pull-right">${invoice.status}</span></p>
        <div class="box">
            <div class="table-responsive">
                <table class="table">
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
                                    <img src='<c:url value="/images/${item.productImage.path}"/>' alt="${item.productName}">
                                </a>
                            </td>
                            <td><a href="${pageContext.request.contextPath}/product?uuid=${item.productUUID}">${item.productName}</a>
                            </td>
                            <td>${item.productQuantity}</td>
                            <td>ksh. ${item.productPrice}</td>
                            <td>ksh. ${item.productTotalPrice}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th colspan="4" class="text-right">Order subtotal</th>
                        <th>ksh. ${invoice.total}</th>
                    </tr>
                    <c:if test="${invoice.carrierPrice != null}">
                    <tr>
                        <th colspan="4" class="text-right">Shipping and handling</th>
                        <th>ksh. ${invoice.carrierPrice}</th>
                    </tr>
                    </c:if>
                    <tr>
                        <th colspan="4" class="text-right">Total</th>
                        <th>ksh. ${invoice.total + invoice.carrierPrice}</th>
                    </tr>
                    </tfoot>
                </table>

            </div>
            <!-- /.table-responsive -->

            <div class="row addresses">
                <c:if test="${retailer == true || carrier == true}">
                    <div class="col-sm-6">
                        <h3 class="text-uppercase">Customer address</h3>
                        <p>${invoice.customerFirstname}&nbsp&nbsp${invoice.customerLastname}
                            <br>${invoice.customerPhoneNumber}
                            <br>${invoice.customerEmail}
                            <br>${invoice.customerStreetAddress}
                            <br>${invoice.customerResidentialName}
                            <br>${invoice.customerLocation}</p>
                    </div>
                </c:if>
                <c:if test="${retailer == true || customer == true && invoice.carrierPhoneNumber != null}">
                    <div class="col-sm-6">
                        <h3 class="text-uppercase">Carrier address</h3>
                        <p>${invoice.carrierName}
                            <br>${invoice.carrierPhoneNumber}
                            <br>${invoice.carrierEmail}
                            <br>${invoice.carrierStreetAddress}
                            <br>${invoice.carrierResidentialName}
                            <br>${invoice.carrierLocation}</p>
                    </div>
                </c:if>
                <c:if test="${carrier == true || customer == true}">
                    <div class="col-sm-6">
                        <h3 class="text-uppercase">Retailer address</h3>
                        <p>${invoice.retailerName}
                            <br>${invoice.retailerPhoneNumber}
                            <br>${invoice.retailerEmail}
                            <br>${invoice.retailerStreetAddress}
                            <br>${invoice.retailerResidentialName}
                            <br>${invoice.retailerLocation}</p>
                    </div>
                </c:if>
            </div>
            <!-- /.addresses -->

        </div>
        <!-- /.box -->

    </div>
  <!-- /.col-md-9 -->

  <!-- *** LEFT COLUMN END *** -->

</div>
</div>
 </div>
<jsp:include page="../common/footer.jsp"/>
<script>
    function refresh () {
        location.reload();
    }
</script>
</body>
</html>