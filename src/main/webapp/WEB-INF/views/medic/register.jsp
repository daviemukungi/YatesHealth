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
          <li>Medic Registration</li>
        </ul>

      </div>
    </div>
  </div>
</div>

<div id="content">
  <div class="container">

    <div class="row">
      <div class="alert alert-success" style="display:none;" id="register-success"role="alert">You have successfully registered to the medics program.We will send you an email once your registration has been verified by the appropriate regulatory body.</div>
      <div class="col-md-6">
        <div class="box">
          <h2 class="text-uppercase">New account</h2>

          <p class="lead">Medic Registration</p>
          <p>Registering with us allows you to prescribe medicine to your patients online.Please note that this account is not connected to your customer </p>
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
          <div id="nationalIdDiv" class="nationalId form-group">
            <label for="nationalId">National ID</label>
            <input type="text" class="form-control" id="nationalId">
            <span style="display: none">Please enter your National ID</span>
          </div>
          <div id="medicalIdDiv" class="medicalId form-group">
            <label for="medicalId">Medical ID</label>
            <input type="text" class="form-control" id="medicalId">
            <span style="display: none">Please enter your medical ID</span>
          </div>
          <div id="medicTypeIdDiv" class="medicType form-group">
            <label for="medicTypeId">Medic Types</label>
            <select id="medicTypeId" class="form-control select2-single">
              <c:forEach items="${medicTypes}" var="medicType">
                <option value="${medicType.id}">${medicType.name}</option>
              </c:forEach>
            </select>
            <span style="display: none">Please select a medic Type</span>
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
          <p class="lead">Enter your Professional address</p>
          <p class="text-muted">Please ensure that the information you enter corresponds to the information you supplied to yor regulatory body.</p>

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
            <button onclick="submitMedic(this)" class="btn btn-template-main">Register</button>
            <span>By clicking on Register you are agreeing with our <a href="/medic_t_and_c">terms and conditions</a></span>
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

  function submitMedic(elm) {
    try {
      var firstname = validateSpace("firstnameDiv",$('#firstname').val());
      var lastname = validateSpace("lastnameDiv",$('#lastname').val());
      var nationalId = validateSpace("nationalIdDiv",$('#nationalId').val());
      var medicalId = validateSpace("medicalIdDiv",$('#medicalId').val());
      var medicTypeId = validateSpace("medicTypeIdDiv",$('#medicTypeId').val());
      var password = validateSpace("passwordDiv",$('#password').val());
      var confirmPassword = validatePassword("password",password,validateSpace("confirmPasswordDiv",$('#confirmPassword').val()));
      var phoneNumber = validateSpace("phoneNumberDiv",$('#phoneNumber').val());
      var email = validateEmail("emailDiv",validateSpace("emailDiv",$('#email').val()));
      var streetAddress = validateSpace("streetAddressDiv",$('#streetAddress').val());
      var residentialName = validateSpace("residentialNameDiv",$('#residentialName').val());

      var formContent = {};
      formContent.firstname= firstname;
      formContent.lastname= lastname;
      formContent.medicalId= medicalId;
      formContent.nationalId= nationalId;
      formContent.medicTypeId= medicTypeId;
      formContent.password= password;
      formContent.address = {};
      formContent.address.phoneNumber = phoneNumber;
      formContent.address.email = email;
      formContent.address.streetAddress = streetAddress;
      formContent.address.residentialName = residentialName;
      formContent.address.locationId = validateSpace("location",locationId);
      formContent.customer = false;
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
       $("#register-success").fadeIn();
       $(clickedBtn).prop('disabled',true);
     }
  }
  callGet('/api/location/tree',useLocations);
</script>
</body>
</html>