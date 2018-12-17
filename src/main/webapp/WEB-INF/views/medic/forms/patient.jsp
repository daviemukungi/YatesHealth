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
          <div id="firstnameDiv" class="firstname form-group">
            <label for="firstname">First name</label>
            <input type="text" value="${patient.firstname}" class="form-control" id="firstname">
            <span style="display: none">Please enter the patient's first name</span>
          </div>
          <div id="lastnameDiv" class="lastname form-group">
            <label for="lastname">Last name</label>
            <input type="text" value="${patient.lastname}" class="form-control" id="lastname">
            <span style="display: none">Please enter the patient's last name</span>
          </div>
          <div id="emailDiv" class="email form-group">
            <label for="email">E-mail</label>
            <input type="text" value="${patient.email}" class="form-control" id="email">
            <span style="display: none">Please enter the patient's Email</span>
          </div>
          <div id="phoneNumberDiv" class="phoneNumber form-group">
            <label for="phoneNumber">Phone number</label>
            <input type="text" value="${patient.phoneNumber}" class="form-control" id="phoneNumber">
            <span style="display: none">Please enter the patient's Phone number</span>
          </div>
          <div id="idNumberDiv" class="idNumber form-group">
            <label for="idNumber">ID number</label>
            <input type="text" value="${patient.idNumber}" class="form-control" id="idNumber">
            <span style="display: none">Please enter the patient's ID number</span>
          </div>
          <div class="form-group">
            <c:if test="${patient == null}">
              <input type="submit" onclick="submitPatient(this,'POST')" value="patient" class="btn btn-info form-control">
            </c:if>
            <c:if test="${patient != null}">
              <input type="submit" onclick="submitPatient(this,'PUT')" value="patient" class="btn btn-info form-control">
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
  function submitPatient(elm,method) {
    var formContent = {};
    <c:if test="${patient != null}">
    formContent.id = ${patient.id};
    </c:if>
    try {
      $(elm).prop('disabled',true);
      formContent.firstname= validateSpace("firstnameDiv",$('#firstname').val());
      formContent.lastname = validateSpace("lastnameDiv",$('#lastname').val());
      formContent.email = validateEmail("emailDiv",validateSpace("emailDiv",$('#email').val()));
      formContent.phoneNumber = validateSpace("phoneNumberDiv",$('#phoneNumber').val());
      formContent.idNumber = validateSpace("idNumberDiv",$('#idNumber').val());
      passData('/api/patient',method,formContent,refresh);
    } catch (e) {
      $(elm).prop('disabled',false);
      activateError(e);
    }
  }

  function refresh () {
    if(this.status != 'FAILED' || this.status != 'ERROR') {
      location.href='${pageContext.request.contextPath}/medic';
    }
  }

</script>
</body>
</html>