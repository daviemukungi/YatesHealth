<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <span class="panel-title hidden-xs"> Add New Category</span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="active">
                <button id="addBtn" onclick="getContent('/category/view/admin/table')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
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
                                <c:if test="${category != null && category.image != null}">
                                    <img src='<c:out value="images/${category.image.path}"/>' alt="category image">
                                </c:if>
                                <c:if test="${category == null}">
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
                            <label for="name" class="field prepend-icon">
                                <input id="name" class="form-control" value="${category.name}" type="text" placeholder="Name">
                                <label for="name" class="field-icon">
                                    <i class="fa fa-tag"></i>
                                </label>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label class="field prepend-icon">
                                <textarea style="height: 160px;" class="gui-textarea br-light bg-light" id="description" name="description" placeholder="Category Description">
                                    ${category.description}
                                </textarea>
                                <label for="description" class="field-icon">
                                    <i class="fa fa-comments"></i>
                                </label>
                          <span class="input-footer hidden">
                            <strong>Hint:</strong>What kinds of products does this category group </span>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="enabledChkBx" class="field prepend-icon">
                                <input id="enabledChkBx" type="checkbox" <c:if test="${category.enabled == true}">checked="checked" </c:if> >
                                <label for="enabledChkBx">Enabled</label>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="categories" class="field prepend-icon">Parent Category</label>
                            <label for="categories" class="field prepend-icon">
                                <select id="categories" class="select2-single form-control">
                                    <option value="">none</option>
                                    <c:forEach items="${categories}" var="cat">
                                        <option value="${cat.id}"  <c:forEach items="${cat.children}" var="child"> <c:if test="${category.id == child.id}">selected="selected" </c:if> </c:forEach>  >${cat.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" <c:if test="${category != null}">onclick="submitCategory(this,'PUT')" </c:if> <c:if test="${category == null}">onclick="submitCategory(this,'POST')" </c:if> type="button">Save Category</button>
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
      // Init Select2 - Basic Single
      $(".select2-single").select2();
      var btn = '';
      function submitCategory(elm,method) {
          btn = elm;
          var formContent = {};
          <c:if test="${category != null}">
          formContent.id = ${category.id};
          </c:if>
          formContent.name= $('#name').val();
          formContent.description = $('#description').val();
          if($("#enabledChkBx").is(':checked')) {
              formContent.enabled = true;
          } else {
              formContent.enabled = false;
          }
          var parentId = $('#categories').val();
          if(parentId != "") {
              formContent.parentId  = parentId;
          } else {
              formContent.parentId = null;
          }
          formContent.image = {};
          imgSend('/category/upload','/category',method,formContent,refresh);
      }

      function refresh () {
          if(this.status == 'CREATED' || this.status == 'MODIFIED' || this.status == 'OK') {
              $(btn).prop('disabled',true);
              getContent('/category/view/admin/table');
          }
      }
  </script>