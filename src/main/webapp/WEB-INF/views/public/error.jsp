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
    <div class="container">
        <div class="row">
            <div class="col-sm-6 col-sm-offset-3" id="error-page">

                <div class="box">

                    <h3>${message}</h3>
                    <h4 class="text-muted">${errorCode}</h4>

                    <p class="buttons"><a href="/" class="btn btn-template-main"><i class="fa fa-home"></i> Go to Homepage</a>
                    </p>
                </div>


            </div>
        </div>
    </div>
    <!-- /.container -->
</div>
<!-- /#content -->
_________________________________________________________ -->
<jsp:include page="../common/footer.jsp"/>
</body>

</html>