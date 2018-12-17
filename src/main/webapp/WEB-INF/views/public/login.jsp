<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../common/header.jsp"/>
<div id="heading-breadcrumbs">
  <div class="container">
    <div class="row">
      <div class="col-md-7">
        <h1>Accounts</h1>
      </div>
      <div class="col-md-5">
        <ul class="breadcrumb">
          <li><a href="/">Home</a>
          </li>
          <li>User Login</li>
        </ul>

      </div>
    </div>
  </div>
</div>

<div id="content">
  <div class="container">

    <div class="row text-center ">
      <div class="col-md-12">
        <div class="heading">
          <h2>Login</h2>
        </div>
      </div>
      <div class="col-md-6 col-md-offset-3" style="margin-top:10px;margin-bottom:200px;border: dashed 1px;padding: 10px;">
        <c:if test="${loginFailed == true}">
          <div style="margin-top:10px;margin-bottom:20px;" class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
            </button>
            Failed to login. Please check your email and password.<br>
            If you have forgotten your password click <a href="/login/recover">here</a>
          </div>
        </c:if>

        <form action="/j_spring_security_check" onsubmit="return login();" method="post">
          <div class="row">
            <div class="col-sm-12">
              <div id="emailDiv" class="email form-group">
                <label for="email">Email</label>
                <input type="text" name="j_username" class="form-control" id="email">
                <span style="display: none">Please enter a valid email </span>
              </div>
            </div>
            <div class="col-sm-12">
              <div id="passwordDiv" class="password form-group">
                <label for="password">Password</label>
                <input type="password" name="j_password"  class="form-control" id="password">
                <span style="display: none">Please enter a password</span>
              </div>
            </div>
            <div class="col-sm-12 text-center">
              <button type="submit" class="btn btn-template-main"><i class="fa fa-sign-in"></i> Login</button>
            </div>
          </div>
          <!-- /.row -->
        </form>
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

  function login() {
    try {
      validateEmail("emailDiv",validateSpace("emailDiv",$('#email').val()));
      validateSpace("passwordDiv",$('#password').val());
      return true;
    } catch (e) {
      activateError(e);
      return false;
    }

  }
</script>
</body>
</html>