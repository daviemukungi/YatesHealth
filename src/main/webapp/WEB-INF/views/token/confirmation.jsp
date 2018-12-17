<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../common/header.jsp"/>
<div id="heading-breadcrumbs">
    <div class="container">
        <div class="row">
            <div class="col-md-7">
                <h1>Y.A.T.E.S</h1>
            </div>
            <div class="col-md-5">
                <ul class="breadcrumb">
                    <li><a href="/">Home</a>
                    </li>
                    <li>Confirmation</li>
                </ul>

            </div>
        </div>
    </div>
</div>

<div id="content">
    <div class="container">
        <div class="row">
                <c:if test="${users == null}">
                    <div class="col-md-8 col-md-offset-2" style="min-height: 400px;">

                        <h4 style="margin:10px;">There are No Pending Confirmations right now. We will notify you via E-mail if new Medical Practitioners submit their information</h4>
                        <p class="buttons"><a href="${pageContext.request.contextPath}/" class="btn btn-template-main"><i class="fa fa-home"></i> Go to Homepage</a>
                        </p>
                    </div>
                </c:if>
                <div class="table-responsive" style="margin-bottom:25px;text-align: left;">
                    <c:forEach items="${users}" var="user">
                    <table class="table table-hover table-bordered">
                            <tr>
                                <td>NAME</td><td>${user.firstname} ${user.lastname}</td>
                                <td>MEDICAL ID</td><td>${user.medic.medicalId}</td>
                            </tr>
                            <tr>
                                <td>NATIONAL ID</td><td>${user.medic.nationalId}</td>
                                <td>POSITION</td><td>${user.medic.medicType.name}</td>
                            </tr>
                            <tr>
                                <td>PHONE NUMBER</td><td>${user.address.phoneNumber}</td>
                                <td>E-MAIL</td><td>${user.address.email}</td>
                            </tr>
                            <tr>
                                <td>FACILITY STREET </td><td>${user.address.streetAddress}</td>
                                <td>FACILITY BUILDING NAME</td><td>${user.address.residentialName}</td>
                            </tr>
                            <tr>

                            </tr>
                            <tr>
                                <td>FACILITY LOCATION </td><td>${user.address.location.name} (${user.address.location.label})</td>
                                <td colspan="2"><a href="${pageContext.request.contextPath}/regulator/medics?token=${token}&status=approved&userId=${user.id}" class="btn btn-success">APPROVE</a>
                                    <a href="${pageContext.request.contextPath}/regulator/medics?token=${token}&status=rejected&userId=${user.id}" class="btn btn-danger">REJECT</a></td>
                            </tr>
                    </table>
                    </c:forEach>
                </div>
        </div>
    </div>
    <!-- /.container -->
</div>
<!-- /#content -->
<jsp:include page="../common/footer.jsp"/>
</body>

</html>