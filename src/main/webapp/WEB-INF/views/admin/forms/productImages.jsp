<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: robinson
  Date: 4/9/16
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href='<c:url value="resources/vendor/plugins/select2/css/core.css"/>'>
<div class="panel mb25 mt5">
    <div class="panel-heading">
        <span class="panel-title hidden-xs"> Product Images </span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="">
                <button id="addBtn" onclick="getContent('/product/view/admin/table')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
            </li>
        </ul>
    </div>
    <div class="panel-body p20 pb10">
        <div class="tab-content pn br-n admin-form">
            <div id="tab1_1" class="tab-pane active">
                <div class="section row mbn">
                <c:if test="${product.images != null}">
                    <c:forEach items="${product.images}" var="image" varStatus="status">
                            <div style="background-color: #324047;height:400px;padding:20px;" class="col-xs-12 col-md-3">
                                <div class="fileupload fileupload-new admin-form" data-provides="fileupload">
                                    <div class="fileupload-preview thumbnail mb20">
                                        <img src='<c:out value="images/${image.path}"/>' alt="category image">
                                    </div>
                                    <c:if test="${status.index == 0}">
                                        <div class="row">
                                            <div class="col-xs-12">
                                            <span class="button btn-system btn-file btn-block">
                                              <span class="fileupload-new">Select</span>
                                              <span class="fileupload-exists">Change</span>
                                              <input type="file">
                                            </span>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(product.images) == 0}">
                        <div style="background-color: #324047;height:400px;padding:20px;" class="col-xs-12 col-md-3">
                            <div class="fileupload fileupload-new admin-form" data-provides="fileupload">
                                 <div class="fileupload-preview thumbnail mb20">
                                      <img data-src="holder.js/100%x140" alt="holder">
                                 </div>
                                 <div class="row">
                                     <div class="col-xs-12">
                                         <span class="button btn-system btn-file btn-block">
                                          <span class="fileupload-new">Select</span>
                                          <span class="fileupload-exists">Change</span>
                                          <input type="file">
                                         </span>
                                    </div>
                                 </div>
                            </div>
                        </div>
                </c:if>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" onclick="productImageUpload()" type="button">Save Gallery</button>
                        </p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- FileUpload Plugin -->
<script src='<c:url value="resources/vendor/plugins/fileupload/fileupload.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/holder/holder.min.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/select2/select2.min.js"/>'></script>
<script>
    function productImageUpload() {
        var fl = $('input[type=file]')[0];

        for (var idx = 0;idx < fl.files.length; idx ++ ) {
            var formData = new FormData();
            formData.append('file',fl.files[idx]);
            $.ajax({
                url : '/api/product/${product.id}/gallery',
                data : formData,
                processData : false,
                contentType : false,
                type : 'POST',
                beforeSend: function () {
                    $('.loading').fadeIn('slow');
                },
                success : function(data) {
                    $('.loading').fadeOut('slow');
                    if(data.status == 'OK') {
                        showToast(data.message,'success');
                    } else {
                        showToast(data.message,'error');
                    }
                }
            });
        }
    }
</script>
<script type="text/javascript">
</script>