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
                    <li>${cat.name}</li>
                </ul>

            </div>
        </div>
    </div>
</div>

<div id="content">
    <div class="container">

        <div class="row">


            <!-- *** LEFT COLUMN ***
    _________________________________________________________ -->

            <div class="col-sm-3">

                <!-- *** MENUS AND FILTERS ***
_________________________________________________________ -->
                <div class="panel panel-default sidebar-menu">

                    <div class="panel-heading">
                        <h3 class="panel-title">Brands</h3>
                        <a class="btn btn-xs btn-danger pull-right"  href="/product"><i class="fa fa-times-circle"></i> <span class="hidden-sm">Clear</span></a>
                    </div>

                    <div class="panel-body">
                        <div class="form-group">
                            <c:forEach items="${manufacturers}" var="manufacturer" varStatus="status">
                               <div class="checkbox">
                                  <label>
                                      <input type="checkbox" <c:forEach items="${mans}" var="man"> <c:if test="${man == manufacturer.id}">checked="checked"</c:if></c:forEach> class="manufacturers" onchange="if($(this).is(':checked')){paramMans.push(${manufacturer.id});getProductsByFilter()}else{removeManufacturer(${manufacturer.id})}" value="${manufacturer.id}">${manufacturer.name}
                                  </label>
                               </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <c:forEach items="${featureMaps}" var="featureMap" varStatus="statusOuter">
                    <div class="panel panel-default sidebar-menu">

                        <div class="panel-heading">
                            <h3 class="panel-title">${featureMap.feature.name}</h3>
                            <a class="btn btn-xs btn-danger pull-right" href="/product"><i class="fa fa-times-circle"></i> <span class="hidden-sm">Clear</span></a>
                        </div>

                        <div class="panel-body">
                            <div class="form-group">
                                <c:forEach items="${featureMap.featureValues}" var="featureValue" varStatus="status">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" <c:forEach items="${feats}" var="feat"><c:if test="${feat == featureValue.id}">checked="checked"</c:if></c:forEach> onchange="if($(this).is(':checked')){paramFeats.push(${featureValue.id});getProductsByFilter()}else{removeFeature(${featureValue.id})}" class="${featureMap.feature.name}}"  value="${featureValue.id}">${featureValue.val}
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <!-- *** MENUS AND FILTERS END *** -->

                <div class="banner">
                    <a href="#">
                        <img src='<c:url value="/resources/img/banner.jpg"/>' alt="sales 2014" class="img-responsive">
                    </a>
                </div>
                <!-- /.banner -->

            </div>
            <!-- /.col-md-3 -->

            <!-- *** LEFT COLUMN END *** -->

            <!-- *** RIGHT COLUMN ***
    _________________________________________________________ -->

            <div class="col-sm-9">
                <div class="row products">
                    <h3>Search Results: </h3>
                    <c:forEach items="${products}" var="product">
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
                                        <button type="button" onclick="addToWishlist('${product.UUID}')" class="btn btn-danger"><i class="fa fa-heart"
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
                <!-- /.products -->

                <div class="row">

                    <div class="col-md-12 banner">
                        <a href="#">
                            <img src='<c:url value="/resources/img/banner2.jpg"/>' alt="" class="img-responsive">
                        </a>
                    </div>

                </div>


                <div class="pages">

                    <p class="loadMore">
                        <a href="#" class="btn btn-template-main"><i class="fa fa-chevron-down"></i> Load more</a>
                    </p>

                    <ul class="pagination">
                        <li><a href="#">&laquo;</a>
                        </li>
                        <li class="active"><a href="#">1</a>
                        </li>
                        <li><a href="#">2</a>
                        </li>
                        <li><a href="#">3</a>
                        </li>
                        <li><a href="#">4</a>
                        </li>
                        <li><a href="#">5</a>
                        </li>
                        <li><a href="#">&raquo;</a>
                        </li>
                    </ul>
                </div>


            </div>
            <!-- /.col-md-9 -->

            <!-- *** RIGHT COLUMN END *** -->

        </div>

    </div>
    <!-- /.container -->
</div>
<!-- /#content -->



<!-- *** FOOTER ***
_________________________________________________________ -->
<jsp:include page="../common/footer.jsp"/>
<script>
    var manufacturerList = ${fn:length(manufacturers)};
    var featureList = ${fn:length(features)};
    var paramMans = [];
    var paramFeats = [];
    <c:forEach items="${mans}" var="man">
        paramMans.push(${man});
    </c:forEach>
    <c:forEach items="${feats}" var="feat">
    paramFeats.push(${feat});
    </c:forEach>

    function removeFeature (id) {
       var idx = 0;
       while(idx < paramFeats.length ) {
            if(paramFeats[idx] == id) {
               paramFeats.splice(idx, 1);
           }
           idx++;
       }
        getProductsByFilter();
    }

    function removeManufacturer (id) {
        var idx = 0;
        while(idx < paramMans.length ) {
            if(paramMans[idx] == id) {
                paramMans.splice(idx, 1);
            }
            idx++;
        }
        getProductsByFilter();
    }

    function getProductsByFilter() {
        var url = '/product?';
        for(var idx = 0; idx < paramMans.length;idx++) {
            if(idx == 0) {
                url+="manIds="+paramMans[idx];
            } else {
                url+="&manIds="+paramMans[idx];
            }
        }

        for(idx = 0; idx < paramFeats.length;idx++) {
            if(idx == 0 && paramMans.length == 0) {
                url+="featIds="+paramFeats[idx];
            } else {
                url+="&featIds="+paramFeats[idx];
            }
        }

        location.href = url;
    }
</script>
</body>

</html>