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
        <span class="panel-title hidden-xs"> Add New Product</span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="active">
                <a aria-expanded="true" href="#tab1_1" data-toggle="tab">General</a>
            </li>
            <li class="">
                <a aria-expanded="false" href="#tab1_2" data-toggle="tab">Features</a>
            </li>
            <li class="">
                <button id="addBtn" onclick="getContent('/product/view/admin/table')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
            </li>
        </ul>
    </div>
    <div class="panel-body p20 pb10">
        <div class="tab-content pn br-n admin-form">
            <div id="tab1_1" class="tab-pane active">

                <div class="section row mbn">
                    <div class="col-md-4">
                        <div class="fileupload fileupload-new admin-form" data-provides="fileupload">
                            <div class="fileupload-preview thumbnail mb20">
                                <c:if test="${product != null && product.primaryImage != null}">
                                    <img src='<c:out value="images/${product.primaryImage.path}"/>' alt="product image">
                                </c:if>
                                <c:if test="${product == null}">
                                    <img data-src="holder.js/100%x140" alt="holder">
                                </c:if>
                            </div>
                            <div class="row">
                                <div class="col-xs-5">
                                    <span class="button btn-system btn-file btn-block">
                                      <span class="fileupload-new">Select</span>
                                      <span class="fileupload-exists">Change</span>
                                      <input id="uploadImg" type="file">
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8 pl15">
                        <div class="section mb10">
                            <label for="name" class="field">
                                <input id="name" class="form-control" value="${product.name}" type="text" placeholder="Name">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="weight" class="field">
                                <input id="weight" class="form-control" value="${product.weight}" type="text" placeholder="Weight">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="width" class="field">
                                <input id="width" class="form-control" value="${product.width}" type="text" placeholder="Width">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="height" class="field">
                                <input id="height" class="form-control" value="${product.height}" type="text" placeholder="Height">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="length" class="field">
                                <input id="length" class="form-control" value="${product.length}" type="text" placeholder="Length">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="price" class="field">
                                <input id="price" class="form-control" value="${product.price}" type="text" placeholder="price">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="description" class="field">
                                <textarea id="description" class="form-control"  placeholder="Description">${product.description}</textarea>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="categoryId" class="field prepend-icon">
                                <select id="categoryId" onchange="if(this.value == ''){$('#innId').prop('disabled',false);} else {$('#innId').prop('disabled',true);}" class="select2-single form-control">
                                    <option value="">Category</option>
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.id}" <c:if test="${category.id == product.category.id}">selected="selected"</c:if>>${category.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="innId" class="field prepend-icon">
                                <select id="innId" onchange="if(this.value == ''){$('#categoryId').prop('disabled',false);} else {$('#categoryId').prop('disabled',true);}" class="select2-single form-control">
                                    <option value="">Inn</option>
                                    <c:forEach items="${inns}" var="inn">
                                        <option value="${inn.id}" <c:if test="${inn.id == product.inn.id}">selected="selected"</c:if>>${inn.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="manufacturerId" class="field prepend-icon">
                                <option value="">Manufacturer</option>
                                <select id="manufacturerId"class="select2-single form-control">
                                    <c:forEach items="${manufacturers}" var="manufacturer">
                                        <option value="${manufacturer.id}" <c:if test="${manufacturer.id == product.manufacturer.id}">selected="selected"</c:if>>${manufacturer.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="enabledChkBx" class="field prepend-icon">
                                <input id="enabledChkBx" type="checkbox" <c:if test="${product.enabled == true}">checked="checked" </c:if> >
                                <label for="enabledChkBx">Enabled</label>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" <c:if test="${product != null}">onclick="submitProduct(this,'PUT')" </c:if> <c:if test="${product == null}">onclick="submitProduct(this,'POST')" </c:if> type="button">Save Product</button>
                        </p>
                    </div>
                </div>

            </div>
            <div id="tab1_2" class="tab-pane">
                <c:forEach items="${product.featureValues}" var="featureValue" varStatus="status">
                    <div id="feature${status.index}">
                        <div class="section mb10 col-sm-5">
                            <label for="name${status.index}" class="field prepend-icon">
                                <select id="name${status.index}" class="select2-single form-control" >
                                    <c:forEach items="${features}" var="feature">
                                        <option value="${feature.id}" <c:if test="${featureValue.feature.id == feature.id}">selected="selected"</c:if> >${feature.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                        <div class="section mb10 col-sm-5">
                            <label for="val${status.index}" class="field prepend-icon">
                                <input id="val${status.index}" class="form-control" value="${featureValue.val}" type="text" placeholder="Value">
                            </label>
                        </div>
                        <div class="section mb10 col-sm-2">
                            <label class="field prepend-icon">
                                <button onclick="generateFeatureStructure()" class="btn btn-info"><i class="fa fa-plus" aria-hidden="true"></i></button>
                                <c:if test="${status.index > 0}">
                                    <button onclick="removeFeatureStructure('feature${status.index}')" class="btn btn-warning"><i class="fa fa-minus" aria-hidden="true"></i></button>
                                </c:if>
                            </label>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<!-- FileUpload Plugin -->
<script src='<c:url value="resources/vendor/plugins/fileupload/fileupload.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/holder/holder.min.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/select2/select2.min.js"/>'></script>
  <script>
      <c:if test="${product != null}">
          var idx = ${fn:length(product.featureValues)};
      </c:if>
      <c:if test="${product == null}">
          var idx = 0;
      </c:if>
      <c:if test="${product == null || fn:length(product.featureValues) == 0}">
        generateFeatureStructure();
      </c:if>
      function generateFeatureStructure () {
          var options='';
          <c:forEach items="${features}" var="feature">
            options+='<option value="${feature.id}">${feature.name}</option>';
          </c:forEach>
          var html = '<div id="feature'+idx+'"><div class="section mb10 col-sm-5"><label for="name'+idx+'" class="field prepend-icon">'+
                     '<select id="name'+idx+'" class="select2-single form-control">'+options+'</select></label></div>'+
                     '<div class="section mb10 col-sm-5"><label for="val'+idx+'" class="field prepend-icon">'+
                     '<input id="val'+idx+'" class="form-control" value="" type="text" placeholder="Value"></label></div>'+
                     '<div class="section mb10 col-sm-2"><label class="field prepend-icon">'+
                     '<button onclick="generateFeatureStructure()" class="btn btn-info"><i class="fa fa-plus" aria-hidden="true"></i></button> ';
          if(idx > 0) {
              html+= '<button onclick="removeFeatureStructure(\'feature'+idx+'\')" class="btn btn-warning"><i class="fa fa-minus" aria-hidden="true"></i></button></label></div></div>';
          }

          $('#tab1_2').append(html);
          $(".select2-single").select2();
          idx++;
      }

      function removeFeatureStructure(id) {
          $('#'+id).remove();
          idx--;
      }

      var btn = '';

      function submitProduct(elm,method) {
          btn = elm;
          var formContent = {};
          <c:if test="${product != null}">
          formContent.id = ${product.id};
          </c:if>
          formContent.name= $('#name').val();
          formContent.price = $('#price').val();
          formContent.weight = $('#weight').val();
          formContent.width = $('#width').val();
          formContent.height = $('#height').val();
          formContent.length = $('#length').val();
          formContent.description = $('#description').val();
          if($('#categoryId').val() != '') {
              formContent.categoryId = $('#categoryId').val();
          } else {
              formContent.categoryId = null;
          }

          if($('#innId').val() != '') {
              formContent.innId = $('#innId').val();
          } else {
              formContent.innId = null;
          }

          formContent.manufacturerId = $('#manufacturerId').val();
          formContent.featureValues = [];
          for(var count = 0;count <idx; count++) {
              if($('#name'+count).length != 0 && $('#val'+count).length != 0 && $('#name'+count).val() != "" && $('#val'+count).val() != "") {
                  formContent.featureValues.push({featureId : $('#name'+count).val(),val:$('#val'+count).val()});
              }
          }

          if($("#enabledChkBx").is(':checked')) {
              formContent.enabled = true;
          } else {
              formContent.enabled = false;
          }

          formContent.image = {};
          <c:if test="${product.primaryImage != null}">
            formContent.image.path = '${product.primaryImage.path}';
          </c:if>
          imgSend('/product/upload','/product',method,formContent,refresh);
      }

      function refresh () {
          if(this.status == 'CREATED' || this.status == 'MODIFIED' || this.status == 'OK') {
              $(btn).prop('disabled',true);
              getContent('/product/view/admin/table');
          }
      }

      $(".select2-single").select2();
  </script>