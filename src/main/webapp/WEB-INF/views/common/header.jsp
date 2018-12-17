<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="robots" content="all,follow">
  <meta name="googlebot" content="index,follow,snippet,archive">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>One Shop Point</title>

  <meta name="keywords" content="">

  <link href='https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,500,700,800' rel='stylesheet' type='text/css'>

  <!-- Bootstrap and Font Awesome css -->
  <link rel="stylesheet" href='<c:url value="/resources/css/font-awesome.min.css"/>'>
  <link rel="stylesheet" href='<c:url value="/resources/css/bootstrap.min.css"/>'>
  <link rel="stylesheet" href='<c:url value="/resources/css/chosen.min.css"/>'>

  <!-- Css animations  -->
  <link href='<c:url value="/resources/css/animate.css"/>' rel="stylesheet">

  <!-- Theme stylesheet, if possible do not edit this stylesheet -->
  <link href='<c:url value="/resources/css/style.default.css"/>' rel="stylesheet" id="theme-stylesheet">
  <link rel="stylesheet" type="text/css" href='<c:url value="/resources/vendor/plugins/select2/css/core.css"/>'>

  <!-- Custom stylesheet - for your changes -->
  <link href='<c:url value="/resources/css/custom.css"/>' rel="stylesheet">
  <link href='<c:url value="/resources/vendor/plugins/toastr/toastr.min.css"/>' rel="stylesheet"/>
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/t/dt/dt-1.10.11/datatables.min.css"/>

  <!-- Responsivity for older IE -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Favicon and apple touch icons-->
  <link rel="shortcut icon" type="image/x-icon" href='<c:url value="/resources/assets/img/favicon2.ico"/>' />
</head>

<body>
<div id="all">

  <header>

    <!-- *** TOP ***
_________________________________________________________ -->
    <div id="top">
      <div class="container">
        <div class="row">
          <div class="col-xs-5 contact">
            <p class="hidden-sm hidden-xs">Contact us on ${settings.phone} or ${settings.email}.</p>
            <p class="hidden-md hidden-lg"><a href="#" data-animate-hover="pulse"><i class="fa fa-phone"></i></a>  <a href="#" data-animate-hover="pulse"><i class="fa fa-envelope"></i></a>
            </p>
          </div>
          <div class="col-xs-7">
            <sec:authorize access="hasAnyRole('ROLE_CUSTOMER','ROLE_MEDIC','ROLE_RETAILER','ROLE_CARRIER')">
              <sec:authentication var="user" property="principal" />
              <div class="login">
                <a href="#" ><i class="fa fa-user"></i> <span class="hidden-xs text-uppercase">${user.username}</span></a>
              </div>
            </sec:authorize>
            <c:if test="${medic == null && carrier == null && retailer == null && token == null}">
              <div class="social">
                <a href="${settings.facebook}" class="external facebook" data-animate-hover="pulse"><i class="fa fa-facebook"></i></a>
                <a href="${settings.googlePlus}" class="external gplus" data-animate-hover="pulse"><i class="fa fa-google-plus"></i></a>
                <a href="${settings.twitter}" class="external twitter" data-animate-hover="pulse"><i class="fa fa-twitter"></i></a>
                <a href="${settings.email}" class="email" data-animate-hover="pulse"><i class="fa fa-envelope"></i></a>
              </div>
              <div class="login">
                <a href="#" data-toggle="modal" data-target="#login-modal"><i class="fa fa-sign-in"></i> <span class="hidden-xs text-uppercase">Sign in</span></a>
                <a href="#" data-toggle="modal" data-target="#registration-modal"><i class="fa fa-user"></i> <span class="hidden-xs text-uppercase">Sign up</span></a>
              </div>
            </c:if>

          </div>
        </div>
      </div>
    </div>
    <sec:authentication var="user" property="principal" />
    <!-- *** TOP END *** -->

    <!-- *** NAVBAR ***
    _________________________________________________________ -->

    <div class="navbar-affixed-top" data-spy="affix" data-offset-top="200">

      <div class="navbar navbar-default yamm" role="navigation" id="navbar">

        <div class="container">
          <div class="navbar-header">

            <a class="navbar-brand home" href="${pageContext.request.contextPath}/">
              <img src='<c:url value="/resources/img/logo.png"/>' alt="one shop point logo"
                   class="hidden-xs hidden-sm" style="height: 50px;">
              <img src='<c:url value="/resources/assets/img/favicon2.ico"/>' alt="one shop point logo"
                   class="visible-xs visible-sm"><span class="sr-only">one shop point - go to homepage</span>
            </a>

            <div class="navbar-buttons">
              <button type="button" class="navbar-toggle btn-template-main" data-toggle="collapse"
                      data-target="#navigation">
                <span class="sr-only">Toggle navigation</span>
                <i class="fa fa-align-justify"></i>
              </button>
            </div>
          </div>
          <c:if test="${medic == null && carrier == null && retailer == null && token == null}">
            <!--/.navbar-header -->
            <div class="navbar-collapse collapse" id="navigation">

              <ul class="nav navbar-nav navbar-right">
                <li <c:if test="${page == null}">class="active"</c:if>>
                  <a href="javascript: void(0)" class="dropdown-toggle" data-toggle="dropdown">e-Shop</a>
                </li>
                <li <c:if test="${page == 'compare'}">class="active"</c:if>>
                  <a href="${pageContext.request.contextPath}/product/compare">Compare</a>
                </li>
                <li>
                  <a href="#" data-toggle="modal" data-target="#prescription-modal" class="dropdown-toggle">Get Prescription</a>
                </li>
                <!-- ========== FULL WIDTH MEGAMENU ================== -->
                <li class="dropdown use-yamm yamm-fw">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
                     data-delay="200">Cart <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li>
                      <div class="yamm-content">
                        <div class="row">
                          <div class="col-md-12 clearfix" id="basket">

                            <div class="box">
                              <div class="table-responsive">
                                <table class="table">
                                  <thead>
                                  <tr>
                                    <th colspan="2">Product</th>
                                    <th>Quantity</th>
                                    <th>Unit price</th>
                                    <th colspan="2">Total</th>
                                  </tr>
                                  </thead>
                                  <tbody id="cartTable">
                                  </tbody>
                                  <tfoot>
                                  <tr>
                                    <th colspan="3">Total</th>
                                    <th colspan="3" id="totalCost"></th>
                                  </tr>
                                  </tfoot>
                                </table>

                              </div>
                              <!-- /.table-responsive -->

                              <div class="box-footer">
                                <div class="pull-right">
                                  <button onclick="sendUpdatedCart()" class="btn btn-default"><i class="fa fa-refresh"></i> Update cart</button>
                                  <button data-toggle="modal" data-target="#checkout-modal" type="submit" class="btn btn-template-main">Proceed to checkout <i class="fa fa-chevron-right"></i>
                                  </button>
                                </div>
                              </div>

                              </form>

                            </div>
                          </div>
                        </div>
                      </div>
                      <!-- /.yamm-content -->
                    </li>
                  </ul>
                </li>
              </ul>

            </div>
            <!--/.nav-collapse -->

            <div  id="search">

              <form class="navbar-form" action="${pageContext.request.contextPath}/product/search" method='GET' role="search">
                <div class="input-group">
                  <input type="text" name="query" class="form-control" placeholder="Search">
                  <span class="input-group-btn">
                        <button type="submit" class="btn btn-template-main"><i class="fa fa-search"></i></button>
                  </span>
                </div>
              </form>
            </div>
            <!--/.nav-collapse -->
          </c:if>
        </div>


      </div>
      <!-- /#navbar -->

    </div>

    <!-- *** NAVBAR END *** -->

  </header>

  <!-- *** LOGIN MODAL ***
  _________________________________________________________ -->

  <div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
    <div class="modal-dialog modal-sm">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" id="Login">User login</h4>
        </div>
        <div class="modal-body">
          <form action="/j_spring_security_check" method="post">
            <div class="form-group">
              <input type="text" class="form-control" name="j_username" placeholder="email">
            </div>
            <div class="form-group">
              <input type="password" class="form-control" name="j_password" placeholder="password">
            </div>

            <p class="text-center">
              <button class="btn btn-template-main"><i class="fa fa-sign-in"></i> Log in</button>
            </p>

          </form>

          <p class="text-center text-muted">Not registered yet?</p>

          <p class="text-center text-muted"><a href="#" data-toggle="modal" data-target="#registration-modal"><strong>Register now</strong></a>
        </div>
      </div>
    </div>
  </div>
  <!-- *** LOGIN MODAL END *** -->

  <!-- *** LOGIN MODAL ***
 _________________________________________________________ -->

  <div class="modal fade" id="registration-modal" tabindex="-1" role="dialog" aria-labelledby="Registration" aria-hidden="true">
    <div class="modal-dialog modal-sm">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" >User Registration</h4>
        </div>
        <div class="modal-body row ">
          <a href="/customer/register" style="margin-bottom:5px;" class="col-sm-8 col-sm-offset-2 btn btn-success">Customer</a>
          <a href="/medic/register" style="margin-bottom:5px;" class="col-sm-8 col-sm-offset-2 btn btn-info">Medic</a>
        </div>
      </div>
    </div>
  </div>
  <!-- *** LOGIN MODAL END *** -->

  <div class="modal fade" id="prescription-modal" tabindex="-1" role="dialog" aria-labelledby="Prescription Decode" aria-hidden="true">
    <div class="modal-dialog modal-md">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" >Prescription</h4>
        </div>
        <div class="modal-body row ">
          <div class="col-md-11">
            <div id="prescriptionDiv" class="prescription form-group">
              <label for="prescription"> Prescription Code </label>
              <input id="prescription" type="text" class="form-control">
              <span style="display: none">Please enter your prescription code</span>
            </div>
            <div class="form-group">
              <button onclick="getPrescription();" class="btn btn-info">Get Prescription</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" id="checkout-modal" tabindex="-1" role="dialog" aria-labelledby="Checkout Choose Retailer" aria-hidden="true">
    <div class="modal-dialog modal-lg">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title" >Choose Retailer</h4>
        </div>
        <div class="modal-body row " style="padding: 0 15px;">
          <div class="col-md-3" style="background-color:#0094FF;background-image: url('/resources/img/subtle-grey.png');padding-top: 10px; min-height: 500px;">
            <div class="form-group" id="retailerLocation">
            </div>
          </div>
          <div class="col-md-9" id="retailers" style="height: 500px;overflow-y:scroll">
          </div>
        </div>
      </div>
    </div>
  </div>
