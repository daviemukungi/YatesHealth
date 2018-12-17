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
                    <li>Retailer</li>
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
                <h3 class="panel-title">Retailer's section</h3>
            </div>

            <div class="panel-body">

                <ul class="nav nav-pills nav-stacked">
                    <li>
                        <a href="${pageContext.request.contextPath}/retailer"><i class="fa fa-building"></i>Outlets</a>
                    </li>
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/retailer/stock"><i class="fa fa-briefcase"></i>Stocks</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/retailer/order"><i class="fa fa-shopping-cart"></i>Orders</a>
                    </li>
                    <c:if test="${pharmacy == true}">
                        <li>
                            <a href="${pageContext.request.contextPath}/retailer/prescriptions"><i class="fa fa-heartbeat"></i>Prescriptions</a>
                        </li>
                    </c:if>
                    <li>
                        <a href="${pageContext.request.contextPath}/retailer/account"><i class="fa fa-heart"></i> My account</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i class="fa fa-sign-out"></i> Logout</a>
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
    <div style="margin-bottom:30px;" class="row">
        <a style="margin-right:10px;" href="#" data-toggle="modal" data-target="#import-categories" class="btn btn-template-main btn-info pull-left"><i class="fa fa-download" aria-hidden="true"></i> Export Excel</a>
        <div class="btn-file pull-left"><input type="file" onchange="importExcel();" name="file" id="file" class="" /><label id="fileLbl" class="btn btn-info" for="file"><i class="fa fa-upload" aria-hidden="true"></i> Import Excel</label></div>
    </div>
    <div class="box">
      <div class="table-responsive" style="margin-bottom:25px;">
        <table class="table table-hover">
          <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Created On</th>
            <th>Updated On</th>
          </tr>
          </thead>
          <tbody>
            <c:forEach items="${stocks}" var="stock">
            <tr>
                <td><strong># ${stock.id}</strong></td>
                <td>${stock.product.name}</td>
                <td>${stock.price}</td>
                <td>${stock.quantity}</td>
                <td><jsp:setProperty name="dateValue" property="time" value="${stock.createdOn.time}"/>
                    <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm"/>
                </td>
                <c:if test="${stock.updatedOn != null}">
                <td><jsp:setProperty name="dateValue" property="time" value="${stock.updatedOn.time}"/>
                    <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm"/>
                </td>
                </c:if>
            </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <!-- /.table-responsive -->

    </div>
    <!-- /.box -->

  </div>
    <div class="modal fade" id="import-categories" tabindex="-1" role="dialog" aria-labelledby="Registration" aria-hidden="true">
        <div class="modal-dialog modal-lg">

            <div class="modal-content col-md-8 col-md-offset-2 ">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" >Import Categories</h4>
                </div>
                <div class="modal-body row ">
                    <c:if test="${categories != null}">
                        <div id="categoryIdDiv" class="form-group">
                            <select id="categoryId" style="width:568px;" class="select2-single form-control">
                                   <option value=""> Select a category</option>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                            <span style="display: none">Please select a category</span>
                        </div>
                    </c:if>
                    <c:if test="${inns != null}">
                        <div id="innIdDiv" class="form-group">
                            <select id="innId" style="width:568px;" class="select2-single form-control">
                                <option value=""> Select a inn</option>
                                <c:forEach items="${inns}" var="inn">
                                    <option value="${inn.id}">${inn.name}</option>
                                </c:forEach>
                            </select>
                            <span style="display: none">Please select an inn</span>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <input type="submit" onclick="getExcel('xls')" value="Old Excel (MS Excel 2003 and older)" class="btn btn-danger form-control">
                    </div>
                    <div class="form-group">
                        <input type="submit" onclick="getExcel('xlsx')" value="New Excel (MS Excel 2007 and newer)" class="btn btn-info form-control">
                    </div>
                </div>
            </div>
        </div>
    </div>
  <!-- /.col-md-9 -->

  <!-- *** LEFT COLUMN END *** -->

</div>
</div>
 </div>
<jsp:include page="../common/footer.jsp"/>
<script>
    $(".select2-single").select2();
    var importUrl = '';
    function getExcel (format) {
        try {
            <c:if test="${categories != null}">
                var id = validateSpace("categoryIdDiv",$('#categoryId').val());
            </c:if>
            <c:if test="${inns != null}">
            var id = validateSpace("innIdDiv",$('#innId').val());
            </c:if>
            location.href='${pageContext.request.contextPath}/api/stock?format='+format+"&id="+id;
        } catch (e) {
            activateError(e);
        }
    }

    function deleteStock (id) {
        passData('/api/stock/?ids='+id,'DELETE',null,refresh);
        return false;
    }

    function refresh () {
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/retailer/stock';
        }
    }

    function importExcel () {
        var formData = new FormData();
        if(isDefined($('input[type=file]')[0].files[0])) {
            formData.append('file', $('input[type=file]')[0].files[0]);
            $.ajax({
                url : '${pageContext.request.contextPath}/api/stock/import',
                data : formData,
                processData : false,
                contentType : false,
                type : 'POST',
                beforeSend: function () {
                    $("#fileLbl").html('<i class="fa fa-refresh fa-spin" aria-hidden="true"></i> &nbspimporting');
                },
                success : function(data) {
                    $("#fileLbl").html('<i class="fa fa-upload" aria-hidden="true"></i> Import Excel');
                    if(data.status == 'OK') {
                        showToast(data.message,'success');
                        refresh();
                    } else {
                        showToast(data.message,'error');
                    }
                }
            });
        }
    }
</script>
</body>
</html>