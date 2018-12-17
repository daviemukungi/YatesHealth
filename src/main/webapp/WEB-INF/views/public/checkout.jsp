<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../common/header.jsp"/>
<div id="heading-breadcrumbs">
  <div class="container">
    <div class="row">
      <div class="col-md-7">
        <h1>E-Shop</h1>
      </div>
      <div class="col-md-5">
        <ul class="breadcrumb">
          <li><a href="${pageContext.request.contextPath}/">Home</a>
          </li>
          <li>Checkout</li>
        </ul>

      </div>
    </div>
  </div>
</div>

<div id="content">
  <div class="container">

    <div class="row">

      <div class="col-md-9 clearfix" >

        <div class="box">
            <ul class="nav nav-pills nav-justified">
              <li id="addressNav" onclick="nav('address')" class="active"><a href="#"><i class="fa fa-map-marker"></i><br>Address </a></li>
              <li id="deliveryNav" onclick="nav('delivery')" class="disabled"><a href="#"><i class="fa fa-truck"></i><br>Delivery Method </a></li>
              <li id="retailerNav" onclick="nav('retailer')" class="disabled"><a href="#"><i class="fa fa-money"></i><br>Retailer Payment </a></li>
              <li id="orderNav" onclick="nav('order')" class="disabled"><a href="#"><i class="fa fa-eye"></i><br>Order Review </a></li>
            </ul>

            <div class="row" id="address" >
                <div class="content">
                  <div class="row">
                    <div class="col-sm-6">
                      <div id="firstnameDiv" class="firstname form-group">
                        <label for="firstname">Firstname</label>
                        <input type="text" class="form-control" value="${user.firstname}" id="firstname">
                        <span style="display: none">Please enter your first name</span>
                      </div>
                    </div>
                    <div class="col-sm-6">
                      <div id="lastnameDiv" class="form-group">
                        <label for="lastname">Lastname</label>
                        <input type="text" class="form-control" value="${user.lastname}" id="lastname">
                        <span style="display: none">Please enter your last name</span>
                      </div>
                    </div>
                  </div>
                  <!-- /.row -->

                  <div class="row">
                    <div class="col-sm-6">
                      <div id="phoneNumberDiv" class="form-group">
                        <label for="phoneNumber">Phone number</label>
                        <input type="text" class="form-control" value="${user.address.phoneNumber}" id="phoneNumber">
                        <span style="display: none">Please enter your phone number</span>
                      </div>
                    </div>
                    <div class="col-sm-6">
                      <div id="emailDiv" class="form-group">
                        <label for="email">E-mail</label>
                        <input type="text" class="form-control" value="${user.address.email}" id="email">
                        <span style="display: none">Please enter your email</span>
                      </div>
                    </div>
                  </div>
                  <!-- /.row -->

                  <div class="row">
                    <div class="col-sm-6">
                      <div id="streetAddressDiv" class="form-group">
                        <label for="streetAddress">Street address</label>
                        <input type="text" class="form-control" value="${user.address.streetAddress}" id="streetAddress">
                        <span style="display: none">Please enter your street address</span>
                      </div>
                    </div>
                    <div class="col-sm-6">
                      <div id="residentialNameDiv" class="form-group">
                        <label for="residentialName">Residential Name</label>
                        <input type="text" class="form-control" value="${user.address.residentialName}" id="residentialName">
                        <span style="display: none">Please enter your Residential Name</span>
                      </div>
                    </div>
                    <div class="col-sm-6">
                      <div class="form-group" id="location">
                          <c:if test="${user != null}">
                              <label for="location">Location</label>
                              <input onclick="$('#location').html('');callGet('/location/tree',useLocations);" class="col-sm-12 form-control " value="${user.address.location.name} (click to change)" type="submit">
                          </c:if>
                      </div>
                    </div>

                  </div>
              <!-- /.row -->
                    <div class="box-footer">
                        <div class="pull-left">
                            <a href="${pageContext.request.contextPath}/" class="btn btn-default"><i class="fa fa-chevron-left"></i>Continue Shopping</a>
                        </div>
                        <div class="pull-right">
                            <button onclick="saveAddress()" class="btn btn-template-main">Continue to Delivery<i class="fa fa-chevron-right"></i>
                            </button>
                        </div>
                    </div>
            </div>
            </div>
            <div class="row" id="delivery" style="display:none;">
                <div class="content">
                  <div class="row">
                    <table id="carriers" class="table table-responsive">
                      <thead>
                        <tr>
                          <th>Id</th>
                          <th>Carrier</th>
                          <th>Plan</th>
                          <th>PayBill No</th>
                          <th>Price</th>
                          <th>Transaction No.</th>
                        </tr>
                      </thead>
                      <tbody>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div class="box-footer">
                    <div class="pull-left">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-default"><i class="fa fa-chevron-left"></i>Continue Shopping</a>
                    </div>
                    <div class="pull-right">
                        <button onclick="skipCarrier()" class="btn btn-template-main">Skip Delivery<i class="fa fa-chevron-right"></i>
                        </button>
                        <button onclick="saveCarrier()" class="btn btn-template-main">Continue to Delivery<i class="fa fa-chevron-right"></i>
                        </button>
                    </div>
                </div>
            </div>
            <div class="row" style="display:none;" id="retailer">
                <div class="content">
                    <div class="row">
                        <table class="table table-responsive">
                            <thead>
                            <tr>
                                <th>Retailer</th>
                                <th>PayBill No</th>
                                <th>Price</th>
                                <th>Transaction ID.</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${retailerBasket.retailer.name}</td>
                                <td>${retailerBasket.retailer.payBillNo}</td>
                                <td>${retailerBasket.totalPrice}</td>
                                <td>
                                    <div id="retailerTransactionCodeDiv" >
                                        <input type="text" class="form-control" placeholder="transaction code" id="retailerTransactionCode">
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="box-footer">
                    <div class="pull-left">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-default"><i class="fa fa-chevron-left"></i>Continue Shopping</a>
                    </div>
                    <div class="pull-right">
                        <button onclick="saveRetailer(${retailerBasket.retailer.id})" class="btn btn-template-main">Continue to Delivery<i class="fa fa-chevron-right"></i>
                        </button>
                    </div>
                </div>
            </div>
            <div class="row" style="display:none;" id="order">
                <div class="content">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th colspan="2">Product</th>
                                <th>Quantity</th>
                                <th>Unit price</th>
                                <th>Total</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${retailerBasket.products}" var="product">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/product?uuid=${product.UUID}">
                                            <img src="${pageContext.request.contextPath}/images/${product.image.path}" style="width:100px;height:80px" alt="${product.name}">
                                        </a>
                                    </td>
                                    <td><a href="${pageContext.request.contextPath}/product?uuid=${product.UUID}">${product.name}</a>
                                    </td>
                                    <td>${product.quantity}</td>
                                    <td>${product.price}</td>
                                    <td>${product.price * product.quantity}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th colspan="5">Total</th>
                                <th>Ksh. ${retailerBasket.totalPrice}</th>
                            </tr>
                            </tfoot>
                        </table>

                    </div>
                </div>
                <div class="box-footer">
                    <div class="pull-left">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-default"><i class="fa fa-chevron-left"></i>Continue Shopping</a>
                    </div>
                    <div class="pull-right">
                        <button onclick="checkout()" class="btn btn-template-main">Place Order<i class="fa fa-chevron-right"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.box -->


      </div>
      <!-- /.col-md-9 -->

      <div class="col-md-3">

        <div class="box" id="order-summary">
          <div class="box-header">
            <h3>Order summary</h3>
          </div>
          <p class="text-muted">Shipping and additional costs are calculated based on the values you have entered.</p>

          <div class="table-responsive">
            <table class="table">
              <tbody>
              <tr>
                <td>Order subtotal</td>
                <th>${retailerBasket.totalPrice}</th>
              </tr>
              <tr>
                <td>Shipping and handling</td>
                <th id="shipping"></th>
              </tr>
              <tr class="total">
                <td>Total</td>
                <th id="total">${retailerBasket.totalPrice}</th>
              </tr>
              </tbody>
            </table>
          </div>

        </div>

      </div>
      <!-- /.col-md-3 -->

    </div>
    <!-- /.row -->

  </div>
  <!-- /.container -->
</div>
<!-- /#content -->

<!-- *** FOOTER ***
_________________________________________________________ -->
<jsp:include page="../common/footer.jsp"/>
<script src='<c:url value="resources/vendor/plugins/datatables/media/js/jquery.dataTables.js"/>'></script>
<script>
  var formContent = {};
  var $input = '';
  var carrierId = '';
  var current = 'address';
  var plan ='';
  var checkoutNav  = [];
  checkoutNav.push(current);

  function saveAddress () {
    try {
      var firstname = validateSpace("firstnameDiv",$('#firstname').val());
      var lastname = validateSpace("lastnameDiv",$('#lastname').val());
      var phoneNumber = validateSpace("phoneNumberDiv",$('#phoneNumber').val());
      var email = validateEmail("emailDiv",validateSpace("emailDiv",$('#email').val()));
      var streetAddress = validateSpace("streetAddressDiv",$('#streetAddress').val());
      var residentialName = validateSpace("residentialNameDiv",$('#residentialName').val());

      formContent.firstname = firstname;
      formContent.lastname = lastname;
      formContent.phoneNumber = phoneNumber;
      formContent.email = email;
      formContent.streetAddress = streetAddress;
      formContent.residentialName = residentialName;
      <c:if test="${user != null}">
        formContent.locationId  = ${user.address.location.id};
        cartContent.locationId = ${user.address.location.id };
      </c:if>
      <c:if test="${user == null}">
        formContent.locationId = locationId;
        cartContent.locationId = locationId;
      </c:if>
      passDataNoToast('/api/checkout/carrier?retailerId=${retailerBasket.retailer.id}','POST',cartContent,carriersTable);
    } catch (e) {
      activateError(e);
    }
  }

  function skipCarrier () {
     checkoutNav.push('retailer');
     nav('retailer');
  }

  function saveCarrier () {
    try {
      var carrierTransactionCode = validateSpace("carrier"+carrierId+"Div",$($input).val());
      formContent.carrierId = carrierId;
      formContent.carrierTransactionCode = carrierTransactionCode;
      formContent.carrierPlan = validateSpace("carrier"+carrierId+"Div",plan);
      checkoutNav.push('retailer');
      nav('retailer');
    } catch (e) {
      activateError(e);
    }
  }

  function saveRetailer (id) {
    try {
      var retailerTransactionCode = validateSpace("retailerTransactionCodeDiv",$('#retailerTransactionCode').val());

      formContent.retailerId = id;
      formContent.retailerTransactionCode = retailerTransactionCode;
      checkoutNav.push('order');
      nav('order');
    } catch (e) {
      activateError(e);
    }
  }

  function nav(next) {
      for(var idx = 0 ; idx < checkoutNav.length;idx++) {
          if(checkoutNav[idx] == next) {
              $("#"+current).css('display','none');
              $("#"+current+"Nav").removeClass('active');
              $("#"+next).css('display','');
              $("#"+next+"Nav").removeClass('disabled').addClass('active');
              current = next;
              var temp = [];
              for(var idx2 = 0;idx2 < (idx+1);idx2++) {
                  temp.push(checkoutNav[idx2]);
              }
              checkoutNav = temp;
          }
      }
  }

  function checkout () {
    formContent.cart = cartContent.cart;
    passData('/api/checkout/order','POST',formContent,refresh);
  }

  function refresh () {
      if(this.status == 'CREATED') {
          passData('/cart','DELETE',null,getCart);
          location.href='${pageContext.request.contextPath}/';
      }
  }

  function orderSummary(shippingPrice,carrierPlan){
      $('#shipping').html(shippingPrice);
      $('#total').html('ksh '+(${retailerBasket.totalPrice}+shippingPrice));
      plan = carrierPlan;
  }

  function selector (elm,id) {
    if($(elm).is(':checked')) {
      $('.transactionNo').prop({disabled: true});
      $('.chkBx').prop({checked:false});
      $(elm).prop({checked:true});
      carrierId  = id;
      $input = $('#carrier'+id);
      $($input).removeProp('disabled');
    }
  }

  function carriersTable() {
      $('#carriers').DataTable({
          destroy: true,
          data: this.data,
          columnDefs: [
              {
                  "render": function (data, type, row) {
                      return '<input type="checkbox" class="chkBx" onchange="orderSummary('+row.charge+',\''+row.planName+'\');selector(this,'+row.carrier.id+');">';
                  },
                  "targets": 0,
                  "searchable": false
              },
              {
                  "render": function (data, type, row) {
                      return row.carrier.name;
                  },
                  "targets": 1
              },
              {
                  "render": function (data, type, row) {
                      return row.planName;
                  },
                  "targets": 2
              },
              {
                  "render": function (data, type, row) {
                      return row.carrier.payBillNo;
                  },
                  "targets": 3
              },
              {
                  "render": function (data, type, row) {
                      return "ksh. "+row.charge;
                  },
                  "targets": 4
              },
              {
                  "render":function(data,type,row) {
                      return '<div id="carrier'+row.carrier.id+'Div" ><input type="text" class="form-control transactionNo" placeholder="transaction code" id="carrier'+row.carrier.id+'" disabled></div>';
                  },
                  "targets": 5
              }

          ]
      });

      checkoutNav.push('delivery');
      nav('delivery');
  }
<c:if test="${user == null}">
  callGet('/api/location/tree',useLocations);
</c:if>
</script>
</body>

</html>