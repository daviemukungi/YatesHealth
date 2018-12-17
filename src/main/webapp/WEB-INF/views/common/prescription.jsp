<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:include page="header.jsp"/>
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
                                <a href="${pageContext.request.contextPath}/medic/account"><i class="fa fa-heart"></i>
                                    My account</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i
                                        class="fa fa-sign-out"></i> Logout</a>
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
                    <div style="border: dotted 1px;padding: 5px;">
                        <h6>NAME :- ${prescription.patient.firstname} ${prescription.patient.lastname}</h6>
                        <h6>Email :- ${prescription.patient.email}</h6>
                        <h6>Phone number :- ${prescription.patient.phoneNumber}</h6>
                        <hr>
                        <c:if test="${prescription.sent == false}">
                            <button onclick="send(this)" class="btn btn-info btn-sm"><i class="fa fa-envelope" aria-hidden="true"></i>Send</button>
                        </c:if>
                    </div>
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th>Dosage Form</th>
                            <th style="text-align: center" colspan="3">Quantity X Per Day</th>
                            <th colspan="2">Duration</th>
                        </tr>
                        </thead>
                        <tbody id="prescribe">
                        <c:forEach items="${prescription.prescriptionItems}" var="item">
                            <tr>
                                <td>${item.dosageForm}</td>
                                <td align="center">${item.frequencyQuantity}</td>
                                <td align="center">X</td>
                                <td align="center">${item.frequencyPerDay}</td>
                                <td>${item.duration}</td>
                                <td>${item.unit}</td>
                            </tr>
                            <tr>
                                <c:if test="${item.type == 'inn'}">
                                    <td> INN </td>
                                    <td colspan="5">${item.inn.name}</td>
                                </c:if>
                                <c:if test="${item.type == 'product'}">
                                    <td> Brand name  </td>
                                    <td colspan="5">${item.product.name}</td>
                                </c:if>
                            </tr>
                            <tr>
                                <td colspan="7">${item.note}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.box -->

            </div>
            <!-- /.col-md-9 -->

            <!-- *** LEFT COLUMN END *** -->

        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
<script>
    function refresh () {
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/medic/prescription/${prescription.id}/info';
        }
    }

    function send (elm) {
        $(elm).prop('disabled',true);
        passData('/api/prescription?id=${prescription.id}','PATCH',null,refresh);
    }

</script>
</body>
</html>