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
                    <li>Comparison</li>
                </ul>

            </div>
        </div>
    </div>
</div>

<div id="content">
    <div class="container">

        <div class="row">
            <div class="col-sm-12">
                <div class="row products">
                    <table class="table table-responsive table-bordered">
                        <thead>
                            <th></th>
                            <c:forEach items="${products}" var="product">
                                <th><img style="width:150px;height:100px;" src='<c:url value="/images${product.primaryImage.path}"/>'></th>
                            </c:forEach>
                        </thead>
                        <tbody>
                            <tr>
                                <td></td>
                                <c:forEach items="${products}" var="product">
                                    <td><button type="button" onclick="addToCart('${product.UUID}')" class="form-control btn btn-info"><i class="fa fa-cart-plus" aria-hidden="true"></i> CART </button></td>
                                </c:forEach>
                            <tr>
                            <c:forEach items="${featureMaps}" var="featureMap">
                                <tr>
                                    <td>${featureMap.feature.name}</td>
                                    <c:forEach items="${products}" var="product">
                                        <td><c:forEach items="${product.featureValues}" var="featureValue"><c:if test="${featureValue.feature.id == featureMap.feature.id}">${featureValue.val}</c:if></c:forEach></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- /.products -->

                <div class="row">

                    <div class="col-md-12 banner">
                        <a href="#">
                            <img src='<c:url value="/resources/img/banner2.jpg"/>' alt="" class="img-responsive">
                        </a>
                    </div>

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