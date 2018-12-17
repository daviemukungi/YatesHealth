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
                    <li>ERROR</li>
                </ul>

            </div>
        </div>
    </div>
</div>

<div id="content">
    <div class="container" style="min-height: 300px;">
        <div class="row">
            <div class="col-md-12" >
                    <c:if test="${type == 'public'}">
                        ${settings.legalPublic}
                    </c:if>

                    <c:if test="${type == 'retailer'}">
                        ${settings.legalRetailer}
                    </c:if>

                    <c:if test="${type == 'carrier'}">
                        ${settings.legalCarrier}
                    </c:if>

                    <c:if test="${type == 'medic'}">
                        ${settings.legalMedic}
                    </c:if>

            </div>
        </div>
    </div>
    <!-- /.container -->
</div>
<jsp:include page="../common/footer.jsp"/>
</body>

</html>