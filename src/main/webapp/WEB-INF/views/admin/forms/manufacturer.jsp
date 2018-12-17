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
        <span class="panel-title hidden-xs"> Add New Manufacturer</span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="active">
                <button id="addBtn" onclick="getContent('/manufacturer/view/admin/table')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
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
                                <c:if test="${manufacturer != null && manufacturer.image != null}">
                                    <img src='<c:out value="images/${manufacturer.image.path}"/>' alt="manufacturer image">
                                </c:if>
                                <c:if test="${manufacturer == null}">
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
                                <input id="name" class="form-control" value="${manufacturer.name}" type="text" placeholder="Name">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="phoneNumber" class="field prepend-icon">
                                <input id="phoneNumber" class="form-control" value="${manufacturer.address.phoneNumber}" type="text" placeholder="phone number">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="email" class="field prepend-icon">
                                <input id="email" class="form-control" value="${manufacturer.address.email}" type="text" placeholder="E-mail">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="streetAddress" class="field prepend-icon">
                                <input id="streetAddress" class="form-control" value="${manufacturer.address.streetAddress}" type="text" placeholder="Street Address">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="residentialName" class="field prepend-icon">
                                <input id="residentialName" class="form-control" value="${manufacturer.address.residentialName}" type="text" placeholder="Residential Name">
                            </label>
                        </div>
                        <div class="section mb10" id="location">
                            <c:if test="${manufacturer.address.location.name != null}">
                                 <label for="location" class="field prepend-icon">
                                     <input onclick="$('#location').html('');callGet('/location/tree',useLocations);" class="form-control " value="${manufacturer.address.location.name} (click to change)" type="submit">
                                 </label>
                            </c:if>
                        </div>
                        <div class="section mb10">
                            <label for="enabledChkBx" class="field prepend-icon">
                                <input id="enabledChkBx" type="checkbox" <c:if test="${manufacturer.enabled == true}">checked="checked" </c:if> >
                                <label for="enabledChkBx">Enabled</label>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" <c:if test="${manufacturer != null}">onclick="submitManufacturer(this,'PUT')" </c:if> <c:if test="${manufacturer == null}">onclick="submitManufacturer(this,'POST')" </c:if> type="button">Save Manufacturer</button>
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
      var btn = '';
      function submitManufacturer(elm,method) {
          btn = elm;
          var formContent = {};
          <c:if test="${manufacturer != null}">
          formContent.id = ${manufacturer.id};
          </c:if>
          formContent.name= $('#name').val();
          formContent.address = {};
          formContent.address.phoneNumber = $('#phoneNumber').val();
          formContent.address.email = $('#email').val();
          formContent.address.streetAddress = $('#streetAddress').val();
          formContent.address.residentialName = $('#residentialName').val();
          formContent.address.locationId = locationId;

          if($("#enabledChkBx").is(':checked')) {
              formContent.enabled = true;
          } else {
              formContent.enabled = false;
          }

          formContent.image = {};
          imgSend('/manufacturer/upload','/manufacturer',method,formContent,refresh);
      }

      function refresh () {
          if(this.status == 'CREATED' || this.status == 'MODIFIED' || this.status == 'OK') {
              $(btn).prop('disabled',true);
              getContent('/manufacturer/view/admin/table');
          }
      }

      <c:if test="${manufacturer.address.location.name == null}">
        callGet('/location/tree',useLocations);
      </c:if>
  </script>