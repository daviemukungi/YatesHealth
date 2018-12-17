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
<div class="col-md-5 panel mb25 mt5">
    <div class="panel-heading">
        <span class="panel-title hidden-xs"> Add New Medic Type</span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="active">
                <button id="addBtn" onclick="getContent('/medictype/view/admin/table')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-angle-left"></i> Back </button>
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
                                <input id="name" class="form-control" value="${medicType.name}" type="text" placeholder="Name">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="phoneNumber" class="field prepend-icon">
                                <input id="phoneNumber" class="form-control" value="${medicType.address.phoneNumber}" type="text" placeholder="phone number">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="email" class="field prepend-icon">
                                <input id="email" class="form-control" value="${medicType.address.email}" type="text" placeholder="E-mail">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="streetAddress" class="field prepend-icon">
                                <input id="streetAddress" class="form-control" value="${medicType.address.streetAddress}" type="text" placeholder="Street Address">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="residentialName" class="field prepend-icon">
                                <input id="residentialName" class="form-control" value="${medicType.address.residentialName}" type="text" placeholder="Residential Name">
                            </label>
                        </div>
                        <div class="section mb10" id="location">
                            <c:if test="${medicType.address.location.name != null}">
                                 <label for="location" class="field prepend-icon">
                                     <input onclick="$('#location').html('');callGet('/location/tree',useLocations);" class="form-control " value="${medicType.address.location.name} (click to change)" type="submit">
                                 </label>
                            </c:if>
                        </div>
                        <div class="section mb10">
                            <label for="enabledChkBx" class="field prepend-icon">
                                <input id="enabledChkBx" type="checkbox" <c:if test="${medicType.enabled == true}">checked="checked" </c:if> >
                                <label for="enabledChkBx">Enabled</label>
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" <c:if test="${medicType != null}">onclick="submitMedicType(this,'PUT')" </c:if> <c:if test="${medicType == null}">onclick="submitMedicType(this,'POST')" </c:if> type="button">Save MedicType</button>
                        </p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<div class="col-md-6 panel mb25 mt5 pull-right">
        <div class="panel-heading">
            <span class="panel-title"> Select Allowed Inns </span>
        </div>
        <div class="panel-body p25">
            <select class="dualMultiselect" id="innIds" multiple="multiple" size="15">
                <c:forEach items="${inns}" var="inn">
                    <option value="${inn.id}"  <c:forEach items="${medicType.allowedINNs}" var="allowedINN"> <c:if test="${allowedINN.id == inn.id}">selected="selected" </c:if> </c:forEach>  >${inn.name}</option>
                </c:forEach>
            </select>
        </div>
</div>


<script src='<c:url value="resources/vendor/plugins/select2/select2.min.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/duallistbox/jquery.bootstrap-duallistbox.min.js"/>'></script>
  <script>
      var btn ='';

      function submitMedicType(elm,method) {
          btn = elm;
          var formContent = {};
          <c:if test="${medicType != null}">
          formContent.id = ${medicType.id};
          </c:if>
          formContent.name= $('#name').val();
          formContent.address = {};
          formContent.address.phoneNumber = $('#phoneNumber').val();
          formContent.address.email = $('#email').val();
          formContent.address.streetAddress = $('#streetAddress').val();
          formContent.address.residentialName = $('#residentialName').val();
          <c:if test="${medicType != null}">
            if(locationId == '') {
                formContent.address.locationId = ${medicType.address.location.id};
            } else {
                formContent.address.locationId = locationId;
            }
          </c:if>
          <c:if test="${medicType == null}">
              formContent.address.locationId = locationId;
          </c:if>

          if($("#enabledChkBx").is(':checked')) {
              formContent.enabled = true;
          } else {
              formContent.enabled = false;
          }

          formContent.allowedINNs = $('#innIds' ).val();

          passData('/medictype',method,formContent,refresh);
      }

      var multiSelect = $('.dualMultiselect').bootstrapDualListbox({
          nonSelectedListLabel: 'Options',
          selectedListLabel: 'Selected',
          preserveSelectionOnMove: 'moved',
          moveOnSelect: true
      });

      function refresh () {
          if(this.status == 'CREATED' || this.status == 'MODIFIED' || this.status == 'OK') {
              $(btn).prop('disabled',true);
              getContent('/medictype/view/admin/table');
          }
      }

      <c:if test="${medicType.address.location.name == null}">
        callGet('/location/tree',useLocations);
      </c:if>
  </script>