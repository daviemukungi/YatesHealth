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
                    <li>Carrier</li>
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
                <h3 class="panel-title">Carrier section</h3>
            </div>

            <div class="panel-body">
                <ul class="nav nav-pills nav-stacked">
                    <li>
                        <a href="${pageContext.request.contextPath}/carrier"><i class="fa fa-shopping-cart" aria-hidden="true"></i> Orders</a>
                    </li>
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/carrier/plan"><i class="fa fa-list"></i> Plans</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/carrier/account"><i class="fa fa-heart"></i> My account</a>
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
          <a href="${pageContext.request.contextPath}/carrier/plan/add" class="btn btn-template-main btn-info">Add</a>
    </div>
    <div class="box">
      <div class="table-responsive" style="margin-bottom:25px;">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Base Weight</th>
            <th>Excess Weight</th>
            <th>Excess Charge</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
            <c:forEach items="${carrierPlans}" var="carrierPlan">
            <tr>
                <td><strong># ${carrierPlan.id}</strong></td>
                <td>${carrierPlan.name}</td>
                <td>${carrierPlan.baseWeight}</td>
                <td>${carrierPlan.exceedWeight}</td>
                <td>${carrierPlan.exceedCharge}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/carrier/plan/${carrierPlan.id}/edit" class="btn btn-template-main btn-sm">Edit</a>
                    <a href="" onclick="return deletePrescription(${carrierPlan.id})" class="btn btn-template-main btn-sm">Delete</a>
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
    function deletePrescription (id) {
        passData('/api/carrier/?plan&ids='+id,'DELETE',null,refresh);
        return false;
    }

    function refresh () {
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/carrier/plan';
        }
    }
</script>
</body>
</html>