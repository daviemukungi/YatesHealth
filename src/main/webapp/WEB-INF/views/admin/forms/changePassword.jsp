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
<div class="panel mb25 mt5 col-md-6">
    <div class="panel-heading">
        <span class="panel-title hidden-xs"> Change Password</span>
    </div>
    <div class="panel-body p20 pb10">
        <div class="tab-content pn br-n admin-form">
            <div id="tab1_1" class="tab-pane active">

                <div class="section row mbn">
                    <div class="pl15">
                        <div class="section mb10">
                            <label for="oldPassword" class="field prepend-icon">
                                <input id="oldPassword" class="form-control" type="password" placeholder="Current Password">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="newPassword" class="field prepend-icon">
                                <input id="newPassword" class="form-control" type="password" placeholder="New Password">
                            </label>
                        </div>
                        <div class="section mb10">
                            <label for="confirmPassword" class="field prepend-icon">
                                <input id="confirmPassword" class="form-control" type="password" placeholder="Confirm Password">
                            </label>
                        </div>
                    </div>
                </div>

                <div class="section row mbn">
                    <div class="col-sm-12">
                        <p class="text-right">
                            <button class="btn btn-primary" onclick="submitPassword(this)" type="button">Save New Password</button>
                        </p>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
  <script>
      var btn = '';
      function submitPassword(elm) {
         var oldPassword = $('#oldPassword').val();
         var newPassword = $('#newPassword').val();
         var confirmPassword = $('#confirmPassword').val();

        if (newPassword == '' || confirmPassword == '' || oldPassword == '') {
            showToast("Please fill in all the required input",'warning');
        } else if (newPassword === confirmPassword) {
             var formContent = {};
             formContent.oldPassword= oldPassword;
             formContent.newPassword= newPassword;
             passData('/user/password','PATCH',formContent,refresh);
             btn = elm;
         } else {
             showToast("New password and Confirm passwords do not match",'error');
         }
      }

      function refresh () {
          if(this.status == 'CREATED' || this.status == 'MODIFIED' || this.status == 'OK') {
              $(btn).prop('disabled',true);
              getContent('/admin/password');
          }
      }
  </script>