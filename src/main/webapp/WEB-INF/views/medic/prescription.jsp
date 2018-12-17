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
                    <li>Medic</li>
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
                <h3 class="panel-title">Medic section</h3>
            </div>

            <div class="panel-body">

                <ul class="nav nav-pills nav-stacked">
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/medic"><i class="fa fa-users"></i>My Patients</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/medic/account"><i class="fa fa-heart"></i> My account</a>
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
          <a href="${pageContext.request.contextPath}/medic/prescription/add?patientId=${patient.id}" class="btn btn-template-main btn-info">Add</a>
    </div>
    <div class="box">
      <div class="table-responsive" style="margin-bottom:25px;min-height:200px;">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>Id</th>
            <th>Date</th>
            <th>Prescribed Items</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
            <c:forEach items="${prescriptions}" var="prescription">
            <tr>
                <td><strong># ${prescription.id}</strong></td>
                <td><jsp:setProperty name="dateValue" property="time" value="${prescription.createdOn.time}"/>
                    <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm"/>
                </td>
                <td>${fn:length(prescription.prescriptionItems)}</td>
                <td>
                    <c:if test="${prescription.dispensed == true}">
                        <span class="label label-info">Dispensed</span>
                    </c:if>
                    <c:if test="${prescription.dispensed == false}">
                        <c:if test="${prescription.sent == false}">
                            <span class="label label-info">Not Sent</span>
                        </c:if>
                        <c:if test="${prescription.sent == true}">
                            <span class="label label-info">Not Dispensed</span>
                        </c:if>
                    </c:if>
                </td>
                <td>
                    <c:if test="${prescription.dispensed == false}">
                        <a href="${pageContext.request.contextPath}/medic/prescription/${prescription.id}/edit?patientId=${patient.id}" class="btn btn-template-main btn-sm">Edit</a>
                        <c:if test="${prescription.sent == false}">
                            <button onclick="passData('/api/prescription?id=${prescription.id}','PATCH',null,refresh)" class="btn btn-template-main btn-sm">Send</button>
                        </c:if>
                        <a href="" onclick="return deletePrescription(${prescription.id})" class="btn btn-danger btn-sm">Delete</a>
                    </c:if>
                        <a href="${pageContext.request.contextPath}/medic/prescription/${prescription.id}/info" class="btn btn-template-main btn-sm">Info</a>
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
        passData('/api/prescription/?ids='+id,'DELETE',null,refresh);
        return false;
    }

    function refresh () {
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/medic/prescription?patientId=${patient.id}';
        }
    }
</script>
</body>
</html>