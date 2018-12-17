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
                    <li><a href="/product">products</a>
                    </li>
                    <li>${product.name}</li>
                </ul>

            </div>
        </div>
    </div>
</div>

<div id="content">
    <div class="container">

        <div class="row">
            <div class="col-md-12">
                <div class="row" id="productMain">
                    <div class="col-sm-6">
                        <div id="mainImage">
                            <img style="height: 500px;" src="${pageContext.request.contextPath}/images/${product.primaryImage.path}" alt="${product.name}" class="img-responsive">
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="box">
                                <div class="sizes">

                                    <h3>Package size</h3>

                                    <label for="size_s">
                                        <a href="#">${product.length}</a>
                                        <input type="radio" id="size_s" name="size" value="${product.length}" class="size-input">
                                    </label> <span>x</span>
                                    <label for="size_m">
                                        <a href="#">${product.width}</a>
                                        <input type="radio" id="size_m" name="size" value="${product.width}" class="size-input">
                                    </label> <span>x</span>
                                    <label for="size_l">
                                        <a href="#">${product.height}</a>
                                        <input type="radio" id="size_l" name="size" value="${product.height}" class="size-input">
                                    </label>

                                </div>

                                <p class="price">ksh. ${product.price}</p>

                                <p class="text-center">
                                    <button type="submit" onclick="addToCart('${product.UUID}')" class="btn btn-template-main"><i class="fa fa-shopping-cart"></i> Add to cart</button>
                                    <button type="submit" onclick="addToWishlist('${product.UUID}')" class="btn btn-danger" data-toggle="tooltip" data-placement="top" title="Add to wishlist"><i class="fa fa-heart-o"></i>
                                    </button>
                                    <button type="submit" onclick="addToComparison('${product.UUID}')" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Add to compare"><i class="fa fa-exchange"></i>
                                    </button>
                                </p>
                        </div>

                        <div class="row" id="thumbs">
                            <c:forEach items="${product.images}" var="image">
                                <div class="col-xs-3">
                                    <a href="${pageContext.request.contextPath}/images/${image.path}" class="thumb">
                                        <img style="height:100px;" src="${pageContext.request.contextPath}/images/${image.path}" alt="" class="img-responsive">
                                    </a>
                                </div>
                            </c:forEach>
                            <div class="col-xs-3">
                                <a href="${pageContext.request.contextPath}/images/${product.primaryImage.path}" class="thumb">
                                    <img style="height:100px;" src="${pageContext.request.contextPath}/images/${product.primaryImage.path}" alt="" class="img-responsive">
                                </a>
                            </div>
                        </div>
                    </div>

                </div>


                <div class="box" id="details">
                    <p>
                    <h4>Product details</h4>
                    <p>${product.description}</p>
                    <c:forEach items="${product.featureValues}" var="featureValue">
                        <h4>${featureValue.feature.name}</h4>
                        <ul>
                            <li>${featureValue.val}</li>
                        </ul>
                    </c:forEach>
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
</script>
</body>

</html>