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
            <li>Customer</li>
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
              <h3 class="panel-title">Customer's section</h3>
          </div>

          <div class="panel-body">
              <ul class="nav nav-pills nav-stacked">
                <li>
                  <a href="${pageContext.request.contextPath}/customer"><i class="fa fa-bars"></i>My Orders</a>
                </li>
                <li  class="active">
                  <a href="${pageContext.request.contextPath}/customer/wishlist"><i class="fa fa-heart"></i>My Wishlist</a>
                </li>
                <li>
                  <a href="${pageContext.request.contextPath}/customer/account"><i class="fa fa-user"></i> My account</a>
                </li>
                <li>
                  <a href="${pageContext.request.contextPath}/customer/logout"><i class="fa fa-sign-out"></i> Logout</a>
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
        <div class="box row products">
          <c:forEach items="${wishlist}" var="product">
            <div class="col-md-4 col-sm-6">
              <div class="product">
                <div class="image">
                  <a href="/product?id=${product.id}">
                    <c:if test="${product.primaryImage.path != null}">
                      <img src='<c:url value="/images${product.primaryImage.path}"/>'
                           alt="" class="img-responsive image1" style="height:138px;">
                    </c:if>
                    <c:if test="${product.primaryImage == null || product.primaryImage.path == null}">
                      <img src='<c:url value="/resources/assets/img/avatars/no_img.png"/>'
                           alt="" class="img-responsive image1">
                    </c:if>
                  </a>
                </div>
                <!-- /.image -->
                <div class="text">
                  <h3>
                    <a href="/product?id=${product.id}">${product.name}</a>
                  </h3>

                  <p class="price">ksh. ${product.price}</p>

                  <p>

                  <div class="btn-group" role="group" aria-label="Basic example">
                    <button type="button" onclick="addToCart('${product.UUID}')" class="btn btn-info"><i class="fa fa-cart-plus"
                                                                                                         aria-hidden="true"></i> CART
                    </button>
                    <button type="button" onclick="removeFromWishlist('${product.UUID}')" class="btn btn-danger"><i class="fa fa-times"
                                                                                                             aria-hidden="true"></i></button>
                    <button type="button" onclick="addToComparison('${product.UUID}')" class="btn btn-info"><i class="fa fa-exchange"
                                                                                                               aria-hidden="true"></i></button>
                  </div>
                  </p>

                </div>
                <!-- /.text -->
              </div>
            </div>
            <!-- /.product -->
          </c:forEach>
        </div>
        <!-- /.box -->

      </div>
      <!-- /.col-md-9 -->

      <!-- *** LEFT COLUMN END *** -->

    </div>
  </div>
</div>
<jsp:include page="../common/footer.jsp"/>
</body>
</html>