<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: robinson
  Date: 4/9/16
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-12 panel mb25 mt5">
    <div class="panel-heading">
        <span class="panel-title hidden-xs"> System Settings</span>
        <ul class="nav panel-tabs-border panel-tabs">
            <li class="active">
                <a aria-expanded="true" href="#tab1_1" data-toggle="tab">General</a>
            </li>
            <li class="">
                <a aria-expanded="false" href="#tab1_2" data-toggle="tab">T & C Public</a>
            </li>
            <li class="">
                <a aria-expanded="false" href="#tab1_3" data-toggle="tab">T & C Retailer</a>
            </li>
            <li class="">
                <a aria-expanded="false" href="#tab1_4" data-toggle="tab">T & C Medic</a>
            </li>
            <li class="">
                <a aria-expanded="false" href="#tab1_5" data-toggle="tab">T & C Carrier</a>
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
                                <c:if test="${settings.logo != null}">
                                    <img id="siteLogo"  src='<c:out value="images/${settings.logo}"/>' alt="site logo">
                                </c:if>
                                <c:if test="${settings.logo == null}">
                                    <img id="siteLogo" src='<c:url value="resources/img/logo.png"/>' alt="site logo">
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
                                <div class="col-xs-5">
                                    <button onclick="removeLogo()" class="button btn-danger btn-file btn-block"> REMOVE LOGO </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8 pl15">
                        <div class="section mb10">
                            <label for="branding" class="field prepend-icon">
                                <input id="branding" class="form-control" value="${settings.branding}" type="text" placeholder="Site Name (Branding)">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="aboutUs" class="field prepend-icon">
                                <textarea id="aboutUs" class="form-control"  placeholder="A small note about the business">${settings.aboutUs}</textarea>
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="company" class="field prepend-icon">
                                <input id="company" class="form-control" value="${settings.company}" type="text" placeholder="Company">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="phone" class="field prepend-icon">
                                <input id="phone" class="form-control" value="${settings.phone}" type="text" placeholder="Phone number">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="street" class="field prepend-icon">
                                <input id="street" class="form-control" value="${settings.street}" type="text" placeholder="Street">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="residence" class="field prepend-icon">
                                <input id="residence" class="form-control" value="${settings.residence}" type="text" placeholder="Residence">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="location" class="field prepend-icon">
                                <input id="location" class="form-control" value="${settings.location}" type="text" placeholder="Location">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="country" class="field prepend-icon">
                                <input id="country" class="form-control" value="${settings.country}" type="text" placeholder="Country">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="facebook" class="field prepend-icon">
                                <input id="facebook" class="form-control" value="${settings.facebook}" type="text" placeholder="facebook link">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="twitter" class="field prepend-icon">
                                <input id="twitter" class="form-control" value="${settings.twitter}" type="text" placeholder="twitter link">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="googlePlus" class="field prepend-icon">
                                <input id="googlePlus" class="form-control" value="${settings.googlePlus}" type="text" placeholder="google plus link">
                            </label>
                        </div>

                        <div class="section mb10">
                            <label for="email" class="field prepend-icon">
                                <input id="email" class="form-control" value="${settings.email}" type="text" placeholder="email link">
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" onclick="submitSettings(this)" type="button">Save Settings</button>
                        </p>
                    </div>
                </div>

            </div>
            <div id="tab1_2" class="tab-pane">
                <div class="section row mbn">
                    <textarea id="legalPublic" name="content" rows="10"></textarea>
                </div>
            </div>
            <div id="tab1_3" class="tab-pane">
                <div class="section row mbn">
                    <textarea id="legalRetailer" name="content" rows="10"></textarea>
                </div>
            </div>
            <div id="tab1_4" class="tab-pane">
                <div class="section row mbn">
                    <textarea id="legalMedic" name="content" rows="10"></textarea>
                </div>
            </div>
            <div id="tab1_5" class="tab-pane">
                <div class="section row mbn">
                    <textarea id="legalCarrier" name="content" rows="10"></textarea>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- FileUpload Plugin -->
<script src='<c:url value="resources/vendor/plugins/fileupload/fileupload.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/holder/holder.min.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/markdown/markdown.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/markdown/to-markdown.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/markdown/bootstrap-markdown.js"/>'></script>
  <script>
      var formContent = {};
      // Init MarkDown Editor
      formContent.legalPublic = '${settings.legalPublic}';
      formContent.legalRetailer = '${settings.legalRetailer}';
      formContent.legalMedic = '${settings.legalMedic}';
      formContent.legalCarrier = '${settings.legalCarrier}';
      formContent.logo = '${settings.logo}';

      function removeLogo () {
          formContent.logo = null;
          $("#siteLogo").prop("src","resources/img/logo.png");
      }

      $("#legalPublic").markdown({
          autofocus:true,
          savable:false,
          onShow: function(e) {
              e.setContent(toMarkdown('${settings.legalPublic}'));
          },
          onChange: function(e) {
              formContent.legalPublic = e.parseContent();
          }
      });
      $("#legalRetailer").markdown({
          autofocus:true,
          savable:false,
          onShow: function(e) {
              e.setContent(toMarkdown('${settings.legalRetailer}'));
          },
          onChange: function(e) {
              formContent.legalRetailer = e.parseContent();
          }
      });
      $("#legalMedic").markdown({
          autofocus:true,
          savable:false,
          onShow: function(e) {
              e.setContent(toMarkdown('${settings.legalMedic}'));
          },
          onChange: function(e) {
              formContent.legalMedic = e.parseContent();
          }
      });
      $("#legalCarrier").markdown({
          autofocus:true,
          savable:false,
          onShow: function(e) {
              e.setContent(toMarkdown('${settings.legalCarrier}'));
          },
          onChange: function(e) {
              formContent.legalCarrier = e.parseContent();
          }
      });

      function submitSettings(elm) {
          formContent.branding = $('#branding').val();
          formContent.aboutUs = $('#aboutUs').val();
          formContent.company = $('#company').val();
          formContent.phone = $('#phone').val();
          formContent.street = $('#street').val();
          formContent.residence = $('#residence').val();
          formContent.location =$('#location').val();
          formContent.country =$('#country').val();
          formContent.facebook =$('#facebook').val();
          formContent.twitter =$('#twitter').val();
          formContent.googlePlus =$('#googlePlus').val();
          formContent.email =$('#email').val();

          var formData = new FormData();
          if(isDefined($('input[type=file]')[0].files[0])) {
              formData.append('file', $('input[type=file]')[0].files[0]);
              $.ajax({
                  url : '/api/settings/upload',
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
                          formContent.logo = data.message;
                          passData ('/settings','POST',formContent,function () {if(this.status == 'CREATED'){$(elm).prop('disabled',true);}});
                      } else {
                          showToast(data.message,'error');
                      }
                  }
              });
          } else {
              passData ('/settings','POST',formContent,function () {if(this.status == 'CREATED'){$(elm).prop('disabled',true);}});
          }
      }
  </script>