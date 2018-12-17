<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
          <li>Customer Registration</li>
        </ul>

      </div>
    </div>
  </div>
</div>

<div id="content">
  <div class="container">

    <div class="row">
      <div class="col-md-6">
        <div class="box">
          <h2 class="text-uppercase">New account</h2>

          <p class="lead">Not our registered customer yet?</p>
          <p>With registration with us new world of fashion, fantastic discounts and much more opens to you! The whole process will not take you more than a minute!</p>
          <hr>
          <div id="firstnameDiv" class="firstname form-group">
            <label for="firstname">First name</label>
            <input type="text" class="form-control" id="firstname">
            <span style="display: none">Please enter your first name</span>
          </div>
          <div id="lastnameDiv" class="lastname form-group">
            <label for="lastname">Last name</label>
            <input type="text" class="form-control" id="lastname">
            <span style="display: none">Please enter your last name</span>
          </div>
          <div id="passwordDiv" class="password form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password">
            <span style="display: none">Please enter a password</span>
          </div>
          <div id="confirmPasswordDiv" class="password form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" class="form-control" id="confirmPassword">
            <span style="display: none" >This field must be same with the password field</span>
          </div>
        </div>
      </div>

      <div class="col-md-6">
        <div class="box">
          <h2 class="text-uppercase">Address</h2>
          <p class="lead">Enter your address Info For Convenience</p>
          <p class="text-muted">We use This Address information to automatically fill in your billing and delivery information when you order from our store</p>

          <hr>
          <div id="phoneNumberDiv" class="form-group">
            <label for="phoneNumber">Phone number</label>
            <input type="text" class="form-control" id="phoneNumber">
            <span style="display: none">Please enter your phonenumber</span>
          </div>
          <div id="emailDiv" class="form-group">
            <label for="email">Email</label>
            <input type="text" class="form-control" id="email">
            <span style="display: none">Please enter your email</span>
          </div>
          <div id="streetAddressDiv" class="form-group">
            <label for="streetAddress">Nearest Street Name</label>
            <input type="text" class="form-control" id="streetAddress">
            <span style="display: none">Please enter the nearest street or road to where you live</span>
          </div>
          <div id="residentialNameDiv" class="form-group">
            <label for="residentialName">Estate Name/ residential Name</label>
            <input type="text" class="form-control" id="residentialName">
            <span style="display: none">Please enter the name of your estate or residence</span>
          </div>
          <div class="form-group" id="location">
          </div>
          <div class="text-center">
            <button onclick="submitCustomer(this)" class="btn btn-template-main">Register</button>
            <span>By clicking on Register you are agreeing with our <a href="/customer_t_and_c">terms and conditions</a></span>
          </div>
        </div>
      </div>
    </div>

  </div>
  <!-- /.container -->
</div>
<!-- /#content -->


<!-- *** FOOTER ***
_________________________________________________________ -->
<jsp:include page="../common/footer.jsp"/>
<script>
  var clickedBtn = '';

  function submitCustomer(elm) {
    try {
      var firstname = validateSpace("firstnameDiv",$('#firstname').val());
      var lastname = validateSpace("lastnameDiv",$('#lastname').val());
      var password = validateSpace("passwordDiv",$('#password').val());
      var confirmPassword = validatePassword("password",password,validateSpace("confirmPasswordDiv",$('#confirmPassword').val()));
      var phoneNumber = validateSpace("phoneNumberDiv",$('#phoneNumber').val());
      var email = validateEmail("emailDiv",validateSpace("emailDiv",$('#email').val()));
      var streetAddress = validateSpace("streetAddressDiv",$('#streetAddress').val());
      var residentialName = validateSpace("residentialNameDiv",$('#residentialName').val());

      var formContent = {};
      formContent.firstname= firstname;
      formContent.lastname= lastname;
      formContent.password= password;
      formContent.address = {};
      formContent.address.phoneNumber = phoneNumber;
      formContent.address.email = email;
      formContent.address.streetAddress = streetAddress;
      formContent.address.residentialName = residentialName;
      formContent.address.locationId = validateSpace("location",locationId);
      formContent.customer = true;
      formContent.registration = true;
      formContent.enabled = true;
      passData('/api/user','POST',formContent,openLogin);
      clickedBtn = elm;
    } catch (e) {
      activateError(e);
    }

  }



  function openLogin () {
     if(this.status != 'ERROR' && this.status != 'FAILED') {
       $("#login-modal").modal();
       $(clickedBtn).prop('disabled',true);
     }
  }
  callGet('/api/location/tree',useLocations);
</script>
</body>
</html>