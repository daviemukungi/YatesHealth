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
            </div>

        </div>
        <!-- /.col-md-3 -->

        <!-- *** CUSTOMER MENU END *** -->
    </div>

    <!-- *** RIGHT COLUMN END *** -->

  <!-- *** LEFT COLUMN ***
_________________________________________________________ -->

  <div class="col-md-9">
    <div class="box">
      <div class="table-responsive" style="margin-bottom:25px;">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>INV-NO</th>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>Status</th>
            <th>Ordered On</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
            <c:forEach items="${orders}" var="order">
            <tr>
                <td><strong># ${order.invoiceNo}</strong></td>
                <td>${order.firstname}</td>
                <td>${order.lastname}</td>
                <td>${order.status}</td>
                <td>${order.dateOrdered}</td>
                <td>
                    <c:if test="${retailer == true}">
                        <a href="${pageContext.request.contextPath}/retailer/order/${order.invoiceNo}/view" class="btn btn-template-main btn-sm">View</a>
                    </c:if>
                    <c:if test="${carrier == true}">
                        <a href="${pageContext.request.contextPath}/carrier/order/${order.invoiceNo}/view" class="btn btn-template-main btn-sm">View</a>
                    </c:if>
                </td>
            </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <!-- /.table-responsive -->

    </div>
    <!-- /.box -->

  </div>
  <!-- /.col-md-9 -->

  <!-- *** LEFT COLUMN END *** -->

</div>
</div>
 </div>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>