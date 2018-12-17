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
            <li>Customer</li>
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
              <h3 class="panel-title">Customer's section</h3>
          </div>

          <div class="panel-body">
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
                <th>Ordered On</th>
                <th>Total</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${orders}" var="order">
                <tr>
                  <td><strong># ${order.invoiceNo}</strong></td>
                  <td>${order.dateOrdered}</td>
                  <td>${order.total}</td>
                  <td>${order.status}</td>
                  <td>
                      <a href="${pageContext.request.contextPath}/customer/order/${order.invoiceNo}/view" class="btn btn-template-main btn-sm">View</a>
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