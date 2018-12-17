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
<c:if test="${type == 'affiliates'}">
    <div class="col-md-12 panel mb25 mt5">
</c:if>
<c:if test="${type != 'affiliates'}">
    <div class="col-md-6 panel mb25 mt5">
</c:if>
    <div class="panel-heading">
        <span class="panel-title hidden-xs"> Add New ${type}</span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="active">
                <button id="addBtn" onclick="getContent('/user/view/admin/${type}/table')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
            </li>
        </ul>
    </div>
    <div class="panel-body p20 pb10">
        <div class="tab-content pn br-n admin-form">
            <div id="tab1_1" class="tab-pane active">

                <div class="section row mbn">
                    <c:if test="${type == 'affiliates'}">
                        <div class="col-md-4">
                            <div class="fileupload fileupload-new admin-form" data-provides="fileupload">
                                <div class="fileupload-preview thumbnail mb20">
                                    <c:if test="${user.affiliate != null && user.affiliate.image != null}">
                                        <img src='<c:out value="images/${user.affiliate.image.path}"/>' alt="affiliate image">
                                    </c:if>
                                    <c:if test="${user.affiliate == null}">
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
                    </c:if>
                    <c:if test="${type != 'affiliates'}">
                        <div class=" pl15">
                    </c:if>
                        <div class="section mb10">
                            <label for="firstname" class="field prepend-icon">
                                <input id="firstname" class="form-control" value="${user.firstname}" type="text" placeholder="First name">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="lastname" class="field prepend-icon">
                                <input id="lastname" class="form-control" value="${user.lastname}" type="text" placeholder="Last name">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="phoneNumber" class="field prepend-icon">
                                <input id="phoneNumber" class="form-control" value="${user.address.phoneNumber}" type="text" placeholder="phone number">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="email" class="field prepend-icon">
                                <input id="email" class="form-control" value="${user.address.email}" type="text" placeholder="E-mail">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="streetAddress" class="field prepend-icon">
                                <input id="streetAddress" class="form-control" value="${user.address.streetAddress}" type="text" placeholder="Street Address">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="residentialName" class="field prepend-icon">
                                <input id="residentialName" class="form-control" value="${user.address.residentialName}" type="text" placeholder="Residential Name">
                            </label>
                        </div>
                        <div class="section mb10" id="location">
                            <c:if test="${user.address.location.name != null}">
                                 <label for="location" class="field prepend-icon">
                                     <input onclick="$('#location').html('');callGet('/location/tree',useLocations);" class="form-control " value="${user.address.location.name} (click to change)" type="submit">
                                 </label>
                            </c:if>
                        </div>
                        <c:if test="${type == 'affiliates'}">
                            <div class="section mb10">
                               <label for="employerType" class="field prepend-icon">
                                        <select id='employerType' class="form-control select2-single" onchange="getEmployers(this.value)">
                                            <option value="retailer" <c:if test="${user.affiliate.retailer != null}" >selected="selected"</c:if> >Retailer</option>
                                            <option value="carrier" <c:if test="${user.affiliate.carrier != null}" >selected="selected"</c:if>>Carrier</option>
                                        </select>
                               </label>
                            </div>
                            <div class="section mb10" id="employer">
                                <label for="employerid" class="field prepend-icon">
                                    <select id='employerId'  class="form-control select2-single">
                                <c:forEach items="${employers}" var="employer">
                                       <option value="${employer.id}" <c:if test="${user.affiliate.retailer != null && employer.id == user.affiliate.retailer.id}" >selected="selected"</c:if> <c:if test="${user.affiliate.carrier != null && employer.id == user.affiliate.carrier.id}" >selected="selected"</c:if>>${employer.name}</option>
                                </c:forEach>
                                    </select>
                                </label>
                            </div>
                        </c:if>
                        <div class="section mb10">
                            <label for="enabledChkBx" class="field prepend-icon">
                                <input id="enabledChkBx" type="checkbox" <c:if test="${user.enabled == true}">checked="checked" </c:if> >
                                <label for="enabledChkBx">Enabled</label>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="pharmacistChkBx" class="field prepend-icon">
                                <input id="pharmacistChkBx" type="checkbox" <c:if test="${user.affiliate.pharmacist == true}">checked="checked" </c:if> >
                                <label for="pharmacistChkBx">Pharmacist</label>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" <c:if test="${user != null}">onclick="submitUser(this,'PUT')" </c:if> <c:if test="${user == null}">onclick="submitUser(this,'POST')" </c:if> type="button">Save User</button>
                        </p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<c:if test="${type == 'staff'}" >
    <div class="col-md-5 panel mb25 mt5 pull-right">
        <div class="panel-body p25">
            <select class="dualMultiselect" id="permissionIds" multiple="multiple" size="15">
                <c:forEach items="${permissions}" var="permission">
                    <option value="${permission.id}"  <c:forEach items="${user.permissions}" var="perm"> <c:if test="${permission.id == perm.id}">selected="selected" </c:if> </c:forEach>  >${permission.description}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</c:if>
<!-- FileUpload Plugin -->
<script src='<c:url value="resources/vendor/plugins/select2/select2.min.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/duallistbox/jquery.bootstrap-duallistbox.min.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/fileupload/fileupload.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/holder/holder.min.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/select2/select2.min.js"/>'></script>
  <script>
      <c:if test="${user != null}">
      locationId = ${user.address.location.id};
      </c:if>
      var multiSelect = $('.dualMultiselect').bootstrapDualListbox({
          nonSelectedListLabel: 'Options',
          selectedListLabel: 'Selected',
          preserveSelectionOnMove: 'moved',
          moveOnSelect: true
      });

      function getEmployers(val) {
          if(val == 'retailer') {
              callGet('/retailer',buildStructure);
          } else if (val == 'carrier') {
              callGet('/carrier',buildStructure);
          }
      }

      function buildStructure () {
          var data = this.data;
          var html = '<select id="employerId"  class="form-control select2-single">';
          for (var idx = 0;idx < data.length;idx++) {
              html+='<option value="'+data[idx].id+'">'+data[idx].name+'</option>';
          }
          html+='</select>';
          $('#employer').html(html);
          $(".select2-single").select2();
      }

      var btn = '';

      function submitUser(elm,method) {
          btn = elm;
          var formContent = {};
          <c:if test="${user != null}">
          formContent.id = ${user.id};
          </c:if>
          formContent.firstname= $('#firstname').val();
          formContent.lastname= $('#lastname').val();
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

          if($("#pharmacistChkBx").is(':checked')) {
              formContent.pharmacist = true;
          } else {
              formContent.pharmacist = false;
          }

          <c:if test="${type == 'staff'}">
            formContent.customer = false;
            formContent.permissionIds = $('#permissionIds').val();
          </c:if>
          <c:if test="${type == 'customers'}">
            formContent.customer = true;
          </c:if>
          <c:if test="${type == 'affiliates'}">
              if($("#employerType").val() == 'carrier') {
                  formContent.carrier = true;
              } else {
                  formContent.carrier = false;
              }

            formContent.affiliate = true;
            formContent.image = {};
            formContent.customer = false;
            formContent.employerId = $('#employerId').val();
            imgSend('/user/${type}/upload','/user',method,formContent,refresh);
          </c:if>

          <c:if test="${type != 'affiliates'}">
            passData('/user',method,formContent,refresh);
          </c:if>
      }

      function refresh () {
          if(this.status == 'CREATED' || this.status == 'MODIFIED' || this.status == 'OK') {
              $(btn).prop('disabled',true);
              getContent('/user/view/admin/${type}/table');
          }
      }

      <c:if test="${user.address.location.name == null}">
        callGet('/location/tree',useLocations);
      </c:if>

      $(".select2-single").select2();
  </script>