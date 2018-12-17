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
<div class="panel mb25 mt5 col-md-8">
    <div class="panel-heading">
        <span class="panel-title hidden-xs"> Add New Inn</span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="active">
                <button id="addBtn" onclick="getContent('/inn/view/admin/table')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
            </li>
        </ul>
    </div>
    <div class="panel-body p20 pb10">
        <div class="tab-content pn br-n admin-form">
            <div id="tab1_1" class="tab-pane active">

                <div class="section row mbn">
                    <div class="pl15">
                        <div class="section mb10">
                            <label for="name" class="field prepend-icon">
                                <input id="name" class="form-control" value="${inn.name}" type="text" placeholder="Name">
                            </label>
                        </div>
                        <div class="section mb10">
                            <select class="dualMultiselect" id="dosageFormIds" multiple="multiple" size="15">
                                <c:forEach items="${dosageForms}" var="dosageForm">
                                    <option value="${dosageForm.id}"  <c:forEach items="${inn.dosageForms}" var="innDosageForm"> <c:if test="${dosageForm.id == innDosageForm.id}">selected="selected" </c:if> </c:forEach>  >${dosageForm.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="section mb10">
                            <label for="enabledChkBx" class="field prepend-icon">
                                <input id="enabledChkBx" type="checkbox" <c:if test="${inn.enabled == true}">checked="checked" </c:if> >
                                <label for="enabledChkBx">Enabled</label>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" <c:if test="${inn != null}">onclick="submitInn(this,'PUT')" </c:if> <c:if test="${inn == null}">onclick="submitInn(this,'POST')" </c:if> type="button">Save Inn</button>
                        </p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- FileUpload Plugin -->
<script src='<c:url value="resources/vendor/plugins/duallistbox/jquery.bootstrap-duallistbox.min.js"/>'></script>
  <script>
      var multiSelect = $('.dualMultiselect').bootstrapDualListbox({
          nonSelectedListLabel: 'Options',
          selectedListLabel: 'Selected',
          preserveSelectionOnMove: 'moved',
          moveOnSelect: true
      });

      function submitInn(elm,method) {
          $(elm).prop('disabled',true);
          var formContent = {};
          <c:if test="${inn != null}">
          formContent.id = ${inn.id};
          </c:if>
          formContent.name= $('#name').val();
          formContent.dosageFormIds = $('#dosageFormIds').val();


          if($("#enabledChkBx").is(':checked')) {
              formContent.enabled = true;
          } else {
              formContent.enabled = false;
          }

          passData('/inn',method,formContent,refresh);
      }

      function refresh () {
          getContent('/inn/view/admin/table')
      }
  </script>