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
                        <li>
                            <a href="${pageContext.request.contextPath}/retailer/order"><i class="fa fa-shopping-cart"></i>Orders</a>
                        </li>
                        <c:if test="${pharmacy == true}">
                            <li>
                                <a href="${pageContext.request.contextPath}/retailer/prescriptions"><i class="fa fa-heartbeat"></i>Prescriptions</a>
                            </li>
                        </c:if>
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/retailer/account"><i class="fa fa-heart"></i> My account</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i class="fa fa-sign-out"></i> Logout</a>
                        </li>
                    </ul>
                </c:if>
                <c:if test="${carrier == true}">
                    <ul class="nav nav-pills nav-stacked">
                        <li>
                            <a href="${pageContext.request.contextPath}/carrier"><i class="fa fa-shopping-cart" aria-hidden="true"></i> Orders</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/carrier/plan"><i class="fa fa-list"></i> Plans</a>
                        </li>
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/carrier/account"><i class="fa fa-heart"></i> My account</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i class="fa fa-sign-out"></i> Logout</a>
                        </li>
                    </ul>
                </c:if>
                <c:if test="${customer == true}">
                    <ul class="nav nav-pills nav-stacked">
                        <li>
                            <a href="${pageContext.request.contextPath}/customer"><i class="fa fa-bars"></i>My Orders</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/customer/wishlist"><i class="fa fa-heart"></i>My Wishlist</a>
                        </li>
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/customer/account"><i class="fa fa-user"></i> My account</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/customer/logout"><i class="fa fa-sign-out"></i> Logout</a>
                        </li>
                    </ul>
                </c:if>
                <c:if test="${medic == true}">
                    <ul class="nav nav-pills nav-stacked">
                        <li>
                            <a href="${pageContext.request.contextPath}/medic"><i class="fa fa-users"></i>My Patients</a>
                        </li>
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/medic/account"><i class="fa fa-heart"></i> My account</a>
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

    <div class="col-md-9 clearfix" id="customer-account">
        <c:if test="${medic == null}">
            <p class="lead">Change your personal details or your password here.</p>
        </c:if>
        <c:if test="${medic != null}">
            <p class="lead">Change your password here.</p>
        </c:if>

        <div class="box">

            <div class="heading">
                <h3 class="text-uppercase">Change password</h3>
            </div>

                <div class="row">
                    <div id="oldPasswordDiv" class="col-sm-6 password form-group">
                        <label for="oldPassword">Current Password</label>
                        <input type="password" class="form-control" id="oldPassword">
                        <span style="display: none">Please enter your current password</span>
                    </div>
                </div>
                <div class="row">
                    <div id="newPasswordDiv" class="col-sm-6 password form-group">
                        <label for="newPassword">New Password</label>
                        <input type="password" class="form-control" id="newPassword">
                        <span style="display: none">Please enter your New password</span>
                    </div>
                    <div id="confirmPasswordDiv" class="col-sm-6 password form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" class="form-control" id="confirmPassword">
                        <span style="display: none">Please confirm your new password</span>
                    </div>
                </div>
                <!-- /.row -->

                <div class="text-center">
                    <button type="submit" onclick="submitPassword(this)" class="btn btn-template-main"><i class="fa fa-save"></i> Save new password</button>
                </div>

        </div>
        <!-- /.box -->

        <c:if test="${medic == null}">
            <div class="box clearfix">
                <div class="heading">
                    <h3 class="text-uppercase">Personal details</h3>
                </div>
                <div class="row">
                    <div id="firstnameDiv" class="col-sm-6 firstname form-group">
                        <label for="firstname">First name</label>
                        <input type="text" class="form-control" value="${user.firstname}" id="firstname">
                        <span style="display: none">Please enter your first name</span>
                    </div>
                    <div id="lastnameDiv" class="col-sm-6 lastname form-group">
                        <label for="lastname">Last name</label>
                        <input type="text" class="form-control" value="${user.lastname}" id="lastname">
                        <span style="display: none">Please enter your last name</span>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div id="phoneNumberDiv" class="col-sm-6 form-group">
                        <label for="phoneNumber">Phone number</label>
                        <input type="text" class="form-control" value="${user.address.phoneNumber}" id="phoneNumber">
                        <span style="display: none">Please enter your phonenumber</span>
                    </div>
                    <div id="emailDiv" class="col-sm-6 form-group">
                        <label for="email">Email</label>
                        <input type="text" class="form-control" value="${user.address.email}" id="email">
                        <span style="display: none">Please enter your email</span>
                    </div>
                </div>
                <!-- /.row -->

                <div class="row">
                    <div id="streetAddressDiv" class="col-sm-6 form-group">
                        <label for="streetAddress">Nearest Street Name</label>
                        <input type="text" class="form-control" value="${user.address.streetAddress}" id="streetAddress">
                        <span style="display: none">Please enter the nearest street or road to where you live</span>
                    </div>
                    <div id="residentialNameDiv" class="col-sm-6 form-group">
                        <label for="residentialName">Estate Name/ residential Name</label>
                        <input type="text" class="form-control" value="${user.address.residentialName}" id="residentialName">
                        <span style="display: none">Please enter the name of your estate or residence</span>
                    </div>
                    <div class="col-sm-12 form-group" id="location">
                        <c:if test="${user.address.location.name != null}">
                            <label for="location">Location</label>
                            <input onclick="$('#location').html('');callGet('/location/tree',useLocations);" class="col-sm-12 form-control " value="${user.address.location.name} (click to change)" type="submit">
                        </c:if>
                    </div>
                    <div class="col-sm-12 text-center">
                        <button type="submit" onclick="updateUser(this)" class="btn btn-template-main"><i class="fa fa-save"></i> Save changes</button>

                    </div>

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
    function submitPassword(elm) {
        try {
            var oldPassword = validateSpace("oldPasswordDiv",$('#oldPassword').val());
            var newPassword = validateSpace("newPasswordDiv",$('#newPassword').val());
            var confirmPassword = validatePassword("newPassword",newPassword,validateSpace("confirmPasswordDiv",$('#confirmPassword').val()));

            var formContent = {};
            formContent.oldPassword= oldPassword;
            formContent.newPassword= newPassword;
            passData('/api/user/password','PATCH',formContent,refresh);
            clickedBtn = elm;
        } catch (e) {
            activateError(e);
        }

    }

    function updateUser(elm) {
        try {
            var firstname = validateSpace("firstnameDiv",$('#firstname').val());
            var lastname = validateSpace("lastnameDiv",$('#lastname').val());
            var phoneNumber = validateSpace("phoneNumberDiv",$('#phoneNumber').val());
            var email = validateEmail("emailDiv",validateSpace("emailDiv",$('#email').val()));
            var streetAddress = validateSpace("streetAddressDiv",$('#streetAddress').val());
            var residentialName = validateSpace("residentialNameDiv",$('#residentialName').val());

            var formContent = {};
            formContent.id = ${user.id};
            formContent.firstname= firstname;
            formContent.lastname= lastname;
            formContent.address = {};
            formContent.address.phoneNumber = phoneNumber;
            formContent.address.email = email;
            formContent.address.streetAddress = streetAddress;
            formContent.address.residentialName = residentialName;
            if(locationId == '') {
                formContent.address.locationId = ${user.address.location.id};
            } else {
                formContent.address.locationId = validateSpace("location",locationId);
            }
            formContent.customer = true;
            formContent.registration = true;
            formContent.enabled = true;
            passData('/api/user','PUT',formContent,refresh);
            clickedBtn = elm;
        } catch (e) {
            activateError(e);
        }

    }

    function refresh () {
        if(this.status == 'MODIFIED') {
            location.reload();
        }
    }
</script>
</body>
</html>