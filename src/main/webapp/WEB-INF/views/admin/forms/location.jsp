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
<div class="col-sm-12 col-md-6 panel mb25 mt5">
    <div class="panel-heading">
        <span class="panel-title hidden-xs"> Add New Location</span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="active">
                <button id="addBtn" onclick="getContent('/location/view/admin/table')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
            </li>
        </ul>
    </div>
    <div class="panel-body p20 pb10">
        <div class="tab-content pn br-n admin-form">
            <div id="tab1_1" class="tab-pane active">
                    <div class="pl15">
                        <div class="section mb10">
                            <label for="name" class="field prepend-icon">
                                <input id="name" class="form-control" value="${location.name}" type="text" placeholder="Name">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="name" class="field prepend-icon">
                                <input id="label" class="form-control" value="${location.label}" type="text" placeholder="Label">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="locations" class="field prepend-icon">
                                <select id="locations" class="select2-single form-control">
                                    <option value="">select a location </option>
                                    <c:forEach items="${locations}" var="loc">
                                        <option value="${loc.id}"  <c:forEach items="${loc.children}" var="child"> <c:if test="${child.id == location.id}">selected="selected" </c:if> </c:forEach> >${loc.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="enabledChkBx" class="field prepend-icon">
                                <input id="enabledChkBx" type="checkbox" <c:if test="${location.enabled == true}">checked="checked" </c:if> >
                                <label for="enabledChkBx">Enabled</label>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" <c:if test="${location != null}">onclick="submitLocation(this,'PUT')" </c:if> <c:if test="${location == null}">onclick="submitLocation(this,'POST')" </c:if> type="button">Save Location</button>
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
      function submitLocation(elm,method) {
          btn = elm;
          var formContent = {};
          <c:if test="${location != null}">
          formContent.id = ${location.id};
          </c:if>
          formContent.name= $('#name').val();
          formContent.label = $('#label').val();
          if($("#enabledChkBx").is(':checked')) {
              formContent.enabled = true;
          } else {
              formContent.enabled = false;
          }
          var parentId = $('#locations').val();
          if(parentId != "") {
              formContent.parentId  = parentId;
          } else {
              formContent.parentId = null;
          }
          passData('/location',method,formContent,refresh);
      }

      function refresh () {
          if(this.status == 'CREATED' || this.status == 'MODIFIED' || this.status == 'OK') {
              $(btn).prop('disabled',true);
              getContent('/location/view/admin/table');
          }
      }
  </script>