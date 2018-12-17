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
                    <li>Retailer</li>
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
                <h3 class="panel-title">Retailer's section</h3>
            </div>

            <div class="panel-body">

                <ul class="nav nav-pills nav-stacked">
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/retailer"><i class="fa fa-building"></i>Outlets</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/retailer/stock"><i class="fa fa-briefcase"></i>Stocks</a>
                    </li>
                    <li>
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
            </div>

        </div>
        <!-- /.col-md-3 -->

        <!-- *** CUSTOMER MENU END *** -->
    </div>

    <!-- *** RIGHT COLUMN END *** -->

  <!-- *** LEFT COLUMN ***
_________________________________________________________ -->

  <div class="col-md-9">
    <div style="margin-bottom:30px;">
          <a href="${pageContext.request.contextPath}/retailer/outlet/add" class="btn btn-template-main btn-info">Add</a>
    </div>
    <div class="box">
      <div class="table-responsive" style="margin-bottom:25px;">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Pay Bill No</th>
            <th>Phone number</th>
            <th>Location</th>
            <th>Date</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
            <c:forEach items="${outlets}" var="outlet">
            <tr>
                <td><strong># ${outlet.id}</strong></td>
                <td>${outlet.name}</td>
                <td>${outlet.payBillNo}</td>
                <td>${outlet.address.phoneNumber}</td>
                <td>${outlet.address.location.name}</td>
                <td><jsp:setProperty name="dateValue" property="time" value="${outlet.createdOn.time}"/>
                    <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/retailer/outlet/${outlet.id}/edit" class="btn btn-template-main btn-sm">Edit</a>
                    <a href="" onclick="return deleteOutlet(${outlet.id})" class="btn btn-template-main btn-sm">Delete</a>
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
<script>
    function deleteOutlet (id) {
        passData('/api/retailer/?ids='+id,'DELETE',null,refresh);
        return false;
    }

    function refresh () {
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/retailer';
        }
    }
</script>
</body>
</html>