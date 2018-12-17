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
                    <li>
                        <a href="${pageContext.request.contextPath}/retailer"><i class="fa fa-building"></i>Outlets</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/retailer/stock"><i class="fa fa-briefcase"></i>Stocks</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/retailer/order"><i class="fa fa-shopping-cart"></i>Orders</a>
                    </li>
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/retailer/prescriptions"><i class="fa fa-heartbeat"></i>Prescriptions</a>
                    </li>
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
    <c:if test="${expired == true}">
        <div style="margin-top:10px;margin-bottom:20px;" class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
            </button>
            This Prescription has expired or has already been dispensed from another pharmacy.
        </div>
    </c:if>
    <div style="margin-bottom:30px;" class="row">
        <form action="/retailer/prescriptions" onsubmit="return function() {if($('#code').val() != ''){return false;} else {return true;}}" method="post" >
            <div class="col-md-7 form-group">
                <input name="code" type="text" class="form-control" id="code" placeholder="Prescription Code">
            </div>
            <div class="col-md-5 form-group">
                <input type="submit" name="submitCode" value="Get Prescription" class="btn btn-info">
            </div>
        </form>
    </div>
    <c:if test="${prescription != null}">
        <div class="col-md-12"><hr></div>
        <div class="row">
            <div class="box">
                <div style="border: dotted 1px;padding: 5px;">
                    <h6>NAME :- ${prescription.patient.firstname} ${prescription.patient.lastname}</h6>
                    <h6>Email :- ${prescription.patient.email}</h6>
                    <h6>Phone number :- ${prescription.patient.phoneNumber}</h6>
                    <h6>ID number :- ${prescription.patient.idNumber}</h6>
                    <hr>
                    <button onclick="dispense()" class="btn btn-info btn-sm"><i class="fa fa-shopping-basket" aria-hidden="true"></i>Dispense</button>
                </div>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>INN</th>
                        <th>Dosage Form</th>
                        <th style="text-align: center" colspan="3">Quantity X Per Day</th>
                        <th colspan="2">Duration</th>
                    </tr>
                    </thead>
                    <tbody id="prescribe">
                    <c:forEach items="${prescription.prescriptionItems}" var="item">
                        <tr>
                            <td>${item.inn.name}</td>
                            <td>${item.dosageForm}</td>
                            <td align="center">${item.frequencyQuantity}</td>
                            <td align="center">X</td>
                            <td align="center">${item.frequencyPerDay}</td>
                            <td>${item.duration}</td>
                            <td>${item.unit}</td>
                        </tr>
                        <tr>
                            <td colspan="7">${item.note}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
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
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/medic/prescription/${prescription.id}/info';
        }
    }

    function dispense () {
        passData('/api/prescription/dispense?id=${prescription.id}','PATCH',null,refresh);
    }

</script>
</body>
</html>