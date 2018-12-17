<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<footer id="footer">
  <div class="container">
    <div class="col-md-3 col-sm-6">
      <h4>About us</h4>

      <p>${settings.aboutUs}</p>
      <hr class="hidden-md hidden-lg hidden-sm">

    </div>
    <!-- /.col-md-3 -->

    <div class="col-md-3 col-sm-6">

      <h4>Contact</h4>

      <p><strong>${settings.company}.</strong>
        <br>${settings.street}
        <br>${settings.residence}
        <br>${settings.location}
        <br>
        <strong>${settings.country}</strong>
      </p>

      <hr class="hidden-md hidden-lg hidden-sm">

    </div>
    <div class="col-md-3 col-sm-6">

      <h4>Quick Links</h4>

      <p><strong><a href="/login">Retailer Login</a></strong><br>
        <strong><a href="/login">Customer Login.</a></strong><br>
        <strong><a href="/login">Medic Login.</a></strong><br>
      </p>

      <hr class="hidden-md hidden-lg hidden-sm">

    </div>
    <div class="col-md-3 col-sm-6">

      <h4>Legal</h4>

      <p><strong><a href="/legal/public/">Terms & conditions public.</a></strong><br>
        <strong><a href="/legal/retailer/">Terms & conditions retailers.</a></strong><br>
        <strong><a href="/legal/carrier/">Terms & conditions carriers.</a></strong><br>
        <strong><a href="/legal/medic/">Terms & conditions medics.</a></strong><br>
      </p>

      <hr class="hidden-md hidden-lg hidden-sm">

    </div>
  </div>
  <!-- /.container -->
</footer>
<!-- /#footer -->

<!-- *** FOOTER END *** -->

<!-- *** COPYRIGHT ***
_________________________________________________________ -->

<div id="copyright">
  <div class="container">
    <div class="col-md-12">
      <p id="copy" class="pull-left"></p>

    </div>
  </div>
</div>
<!-- /#copyright -->

<!-- *** COPYRIGHT END *** -->



</div>
<!-- /#all -->


<!-- #### JAVASCRIPT FILES ### -->

<script src='<c:url value="/resources/js/jquery-1.11.0.min.js"/>'></script>
<%--<script>--%>
  <%--window.jQuery || document.write('<script src="js/jquery-1.11.0.min.js"><\/script>')--%>
<%--</script>--%>
<script src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>

<script src='<c:url value="/resources/js/jquery.cookie.js"/>'></script>
<script src='<c:url value="/resources/js/waypoints.min.js"/>'></script>
<script src='<c:url value="/resources/js/jquery.counterup.min.js"/>'></script>
<script src='<c:url value="/resources/js/jquery.hovercard.min.js"/>'></script>
<script src='<c:url value="/resources/js/jquery.parallax-1.1.3.js"/>'></script>
<script src='<c:url value="/resources/js/front.js"/>'></script>
<script src='<c:url value="/resources/vendor/plugins/toastr/toastr.min.js"/>'></script>
<script src='<c:url value="/resources/js/custom.js"/>'></script>
<script src='<c:url value="/resources/js/chosen.jquery.min.js"/>'></script>
<script src='<c:url value="/resources/vendor/plugins/select2/select2.min.js"/>'></script>
<script>var d = new Date(); $("#copy").html("&copy; "+d.getFullYear()+". One shop point");</script>
<script>

  <sec:authentication var="user" property="principal" />
  <sec:authorize access="isAuthenticated()" var="isAuthenticated" />

  function passData(loc,method,formContent,handler) {
    $.ajax({
      url: '${pageContext.request.contextPath}' + loc,
      type: method,
      data: JSON.stringify(formContent),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      beforeSend: function (xhr) {
        <c:if test="${not isAuthenticated}">
        xhr.setRequestHeader ("Authorization", "Basic " + btoa("web@oneshoppoint.com:spr0iPpQAiwS8u"));
        </c:if>
        $('.loading').fadeIn('slow');
      },
      success: function (data, status) {
        if (data.status == 'CREATED' || data.status == 'MODIFIED' || data.status == 'DELETED' || data.status == 'OK') {
          showToast(data.message, 'success');
        }

        if (data.status == 'ERROR') {
          showToast(data.message, 'error');
        }

        if (data.status == 'FAILED') {
          showToast(data.message, 'warning');
        }
        handler.call(data);
        $('.loading').fadeOut('slow');
      }
    });
  }

  function passDataNoToast(loc,method,formContent,handler) {
    $.ajax({
      url: '${pageContext.request.contextPath}' + loc,
      type: method,
      data: JSON.stringify(formContent),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      beforeSend: function (xhr) {
        <c:if test="${not isAuthenticated}">
        xhr.setRequestHeader ("Authorization", "Basic " + btoa("web@oneshoppoint.com:spr0iPpQAiwS8u"));
        </c:if>
        $('.loading').fadeIn('slow');
      },
      success: function (data, status) {
        handler.call(data);
        $('.loading').fadeOut('slow');
      }
    });
  }

  function callGet(getLoc, handler) {
      $.ajax({
          url: '${pageContext.request.contextPath}' + getLoc,
          type: "GET",
          beforeSend: function (xhr) {
            <c:if test="${not isAuthenticated}">
            xhr.setRequestHeader ("Authorization", "Basic " + btoa("web@oneshoppoint.com:spr0iPpQAiwS8u"));
            </c:if>
            $('.loading').fadeIn('slow');
          },
          success: function (data, status) {
              handler.call(data);
              $('.loading').fadeOut('slow');
          }
      });
  }

  function showToast(message, type) {
    if (type.toLowerCase() == 'success') {
      toastr.success(message);
    } else if (type.toLowerCase() == 'info') {
      toastr.info(message);
    } else if (type.toLowerCase() == 'error') {
      toastr.error(message);
    } else if (type.toLowerCase() == 'warning') {
      toastr.warning(message);
    }

    toastr.options.extendedTimeOut = 0;
    toastr.options.timeOut = 3000;
    toastr.options.fadeOut = 250;
    toastr.options.fadeIn = 250;
    toastr.options.progressBar = true;
  }

  function imgSend(uploadLoc,sendLoc,method,formContent,handler) {
    var formData = new FormData();
    if(isDefined($('input[type=file]')[0].files[0])) {
      formData.append('file', $('input[type=file]')[0].files[0]);
      $.ajax({
        url : '${pageContext.request.contextPath}' + uploadLoc,
        data : formData,
        processData : false,
        contentType : false,
        type : 'POST',
        beforeSend: function (xhr) {
          <c:if test="${not isAuthenticated}">
          xhr.setRequestHeader ("Authorization", "Basic " + btoa("web@oneshoppoint.com:spr0iPpQAiwS8u"));
          </c:if>
          $('.loading').fadeIn('slow');
        },
        success : function(data) {
          $('.loading').fadeOut('slow');
          if(data.status == 'OK') {
            formContent.image.path = data.message;
            passData (sendLoc,method,formContent,handler);
          } else {
            showToast(data.message,'error');
          }
        }
      });
    } else {
      if(!isDefined(formContent.image.path)) {
        formContent.image = null;
      }

      passData (sendLoc,method,formContent,handler);
    }

  }

  function getPrescription() {
    try {
      var code = validateSpace("prescriptionDiv",$('#prescription').val());
      passData('/prescription?code='+code,'POST',null,closePrescriptionModal);

    } catch (e) {
      activateError(e);
    }
  }

  function closePrescriptionModal () {
    $('#prescription-modal').modal('toggle');
    $('#prescription').val('');
    getCart();
  }

  function createCartStructure () {
    var data = this;
    if(isDefined(data[0])) {
      var html = "";
      var total = 0;
      var prescriptionIncluded = false;

      for(var idx = 0; idx < data.length; idx ++) {
        if(data[idx].product != null) {
          html+='<tr><td><a href="/product?uuid='+data[idx].product.uuid+'"><img src="images/'+data[idx].product.primaryImage.path+'" alt="'+data[idx].product.name+'">'+
                  '</a></td><td><a href="/product?uuid='+data[idx].product.uuid+'">'+data[idx].product.name+'</a></td><td><input min="1" onchange="updateQuantity(this.value,\''+data[idx].product.uuid+'\')" value="'+data[idx].quantity+'" class="form-control" type="number">'+
                  '</td><td>'+data[idx].product.price+'</td><td>'+data[idx].totalPrice+'</td><td><a href="" onclick="return removeFromCart(\''+data[idx].product.uuid+'\',\'product\')"><i class="fa fa-trash-o"></i></a></td></tr>';
          total+=data[idx].totalPrice;
        } else {
          html+='<tr><td><img src="${pageContext.request.contextPath}/resources/img/package.png" alt="medical package"></td><td>medical prescription </td><td> 1 </td><td> N/A </td><td> N/A </td><td><a href="" onclick="return removePackageFromCart(\''+data[idx].code+'\',\'medical\')"><i class="fa fa-trash-o"></i></a></td></tr>';
          total+=data[idx].totalPrice;
          prescriptionIncluded = true;
        }
      }
      $('#cartTable').html(html);
      if(prescriptionIncluded) {
        $('#totalCost').html('ksh. '+total+' + prescription');
      } else {
        $('#totalCost').html('ksh. '+total);
      }

      cartContent.cart = [];
      for(idx = 0; idx < data.length; idx ++) {
        if(data[idx].product != null) {
          cartContent.cart.push({uuid:data[idx].product.uuid,quantity:data[idx].quantity,medical:false});
        } else {
          cartContent.cart.push({uuid:data[idx].code,quantity:null,medical:true});
        }
      }
      cartContent.locationId = locId;
      if(cartContent.locationId != ''){
        passData('/api/checkout','POST',cartContent,createRetailerStructure);
      }
    } else {
      $('#cartTable').html('<tr><td colspan="4" style="text-align: center;">Cart is Empty</td></tr>');
      $('#totalCost').html('N/A');
    }
  }

  function createRetailerStructure() {
    var data = this.data;
    var html = '';
    for(var idx = 0; idx < data.length ; idx++) {
      html+='<a href="${pageContext.request.contextPath}/checkout?retailerId='+data[idx].retailer.id+'"><div style="white-space: normal;margin:5px;" class="btn btn-default col-md-5">'+
              '<span>'+data[idx].retailer.name+'</span><span style="display:inline-block;margin:0 5px;color:green"> ksh. '+data[idx].totalPrice+'</span>';
      if(data[idx].products.length > 0 ) {
        html+='<span class="badge" id="retailer'+data[idx].retailer.id+'">'+data[idx].products.length+'</span></div></a>';
      } else {
        html+='</div></a>';
      }

    }
    $('#retailers').html(html);

    for(idx = 0; idx < data.length; idx++) {
      var html2 = '<div style="white-space: normal;max-height:300px;overflow-y: scroll;">';
      for(var idx2 = 0;idx2 < data[idx].products.length;idx2++) {
        html2 += '<div class="col-md-8 col-md-offset-2 btn btn-default" style="margin:5px;width:260px;"><img class="pull-left" src="/images/'+data[idx].products[idx2].image.path+'" style="width:50px;height:50px;"><span>'+ data[idx].products[idx2].name+'</span></div>';
      }

      html2+='</div>';
      $("#retailer"+data[idx].retailer.id).hovercard({
        detailsHTML: html2,
        openOnLeft:true,
        width: 300
      });
    }

  }

  function deliveryMethod () {
    try {
      var firstname = validateSpace("firstnameDiv",$('#firstname').val());
      var lastname = validateSpace("lastnameDiv",$('#lastname').val());
      var password = validateSpace("passwordDiv",$('#password').val());
      var confirmPassword = validatePassword("password",password,validateSpace("confirmPasswordDiv",$('#confirmPassword').val()));
      var phoneNumber = validateSpace("phoneNumberDiv",$('#phoneNumber').val());
      var email = validateEmail("emailDiv",validateSpace("emailDiv",$('#email').val()));
      var streetAddress = validateSpace("streetAddressDiv",$('#streetAddress').val());
      var residentialName = validateSpace("residentialNameDiv",$('#residentialName').val());
    } catch (e) {
      activateError(e);
    }
  }

  getCart();
  callGet('/api/location/tree',useRetailerLocations);

</script>