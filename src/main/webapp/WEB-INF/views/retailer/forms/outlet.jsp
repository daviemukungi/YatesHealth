<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:include page="../../common/header.jsp"/>
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
                        <h3 class="panel-title">Retailer section</h3>
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
                <div class="box">
                    <c:if test="${outlet == null}">
                        <h2 class="text-uppercase">Add Outlet</h2>
                    </c:if>
                    <c:if test="${outlet != null}">
                        <h2 class="text-uppercase">Modify Outlet</h2>
                    </c:if>
                    <div id="nameDiv" class="name form-group">
                        <label for="name">Name</label>
                        <input type="text" value="${outlet.name}" class="form-control" id="name">
                        <span style="display: none">Please enter the outlet's name</span>
                    </div>
                    <div id="descriptionDiv" class="description form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description">${outlet.description}</textarea>
                        <span style="display: none">Please enter the outlet's description</span>
                    </div>
                    <div id="payBillNoDiv" class="payBillNo form-group">
                        <label for="payBillNo">Pay Bill No</label>
                        <input type="text" value="${outlet.payBillNo}" class="form-control" id="payBillNo">
                        <span style="display: none">Please enter the outlet's Pay Bill No</span>
                    </div>
                    <div id="phoneNumberDiv" class="form-group">
                        <label for="phoneNumber">Phone number</label>
                        <input type="text" value="${outlet.address.phoneNumber}" class="form-control" id="phoneNumber">
                        <span style="display: none">Please enter your phonenumber</span>
                    </div>
                    <div id="emailDiv" class="form-group">
                        <label for="email">Email</label>
                        <input type="text" value="${outlet.address.email}" class="form-control" id="email">
                        <span style="display: none">Please enter your email</span>
                    </div>
                    <div id="streetAddressDiv" class="form-group">
                        <label for="streetAddress">Nearest Street Name</label>
                        <input type="text" value="${outlet.address.streetAddress}" class="form-control" id="streetAddress">
                        <span style="display: none">Please enter the nearest street or road to where you live</span>
                    </div>
                    <div id="residentialNameDiv" class="form-group">
                        <label for="residentialName">Estate Name/ residential Name</label>
                        <input type="text" value="${outlet.address.streetAddress}" class="form-control" id="residentialName">
                        <span style="display: none">Please enter the name of your estate or residence</span>
                    </div>
                    <div class="form-group" id="location">
                        <c:if test="${outlet.address.location.name != null}">
                            <label for="residentialName">${outlet.address.location.label}</label>
                            <input onclick="$('#location').html('');callGet('/location/tree',useLocations);" class="form-control " value="${outlet.address.location.name} (click to change)" type="submit">
                        </c:if>
                    </div>
                    <div class="form-group">
                        <c:if test="${outlet == null}">
                            <input type="submit" onclick="submitOutlet(this,'POST')" value="Update" class="btn btn-info form-control">
                        </c:if>
                        <c:if test="${outlet != null}">
                            <input type="submit" onclick="submitOutlet(this,'PUT')" value="Save" class="btn btn-info form-control">
                        </c:if>
                    </div>
                </div>
                <!-- /.box -->

            </div>
            <!-- /.col-md-9 -->

            <!-- *** LEFT COLUMN END *** -->

        </div>
    </div>
</div>
<jsp:include page="../../common/footer.jsp"/>
<script>
    locationId = '${outlet.address.location.id}';
    function submitOutlet(elm,method) {
        try {
            var name = validateSpace("nameDiv",$('#name').val());
            var description = validateSpace("descriptionDiv",$('#description').val());
            var payBillNo = validateSpace("payBillNoDiv",$('#payBillNo').val());
            var phoneNumber = validateSpace("phoneNumberDiv",$('#phoneNumber').val());
            var email = validateEmail("emailDiv",validateSpace("emailDiv",$('#email').val()));
            var streetAddress = validateSpace("streetAddressDiv",$('#streetAddress').val());
            var residentialName = validateSpace("residentialNameDiv",$('#residentialName').val());

            var formContent = {};
            <c:if test="${outlet != null}">
            formContent.id = ${outlet.id};
            </c:if>
            formContent.name= name;
            formContent.description = description;
            formContent.payBillNo = payBillNo;
            formContent.address = {};
            formContent.address.phoneNumber = phoneNumber;
            formContent.address.email = email;
            formContent.address.streetAddress = streetAddress;
            formContent.address.residentialName = residentialName;
            formContent.address.locationId = validateSpace("location",locationId);
            formContent.enabled = true;

            passData('/api/retailer',method,formContent,refresh);
            clickedBtn = elm;
        } catch (e) {
            activateError(e);
        }
    }

    function refresh () {
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/retailer';
        }
    }
<c:if test="${outlet.address.location.name == null}">
    callGet('/api/location/tree',useLocations);
</c:if>
</script>
</body>
</html>