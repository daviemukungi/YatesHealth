<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>One Shop Point</title>
    <meta name="keywords" content="One shop point admin"/>
    <meta name="description" content="One shop point  admin module">
    <meta name="author" content="One shop point">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Font CSS (Via CDN) -->
    <link href='https://fonts.googleapis.com/css?family=Roboto+Condensed' rel='stylesheet' type='text/css'>
    <!-- FullCalendar Plugin CSS -->
    <link rel="stylesheet" type="text/css" href='<c:url value="resources/vendor/plugins/fullcalendar/fullcalendar.min.css"/>'>
    <link rel="stylesheet" type="text/css" href='<c:url value="resources/vendor/plugins/select2/css/core.css"/>'>

    <!-- Theme CSS -->
    <link rel="stylesheet" type="text/css" href='<c:url value="resources/assets/skin/default_skin/css/theme.css"/>'>

    <!-- Admin Forms CSS -->
    <link rel="stylesheet" type="text/css"
          href='<c:url value="resources/assets/admin-tools/admin-forms/css/admin-forms.min.css"/>'>

    <!-- Favicon -->
    <link rel="shortcut icon" href='<c:url value="resources/assets/img/favicon2.ico"/>'>
    <link href='<c:url value="resources/vendor/plugins/toastr/toastr.min.css"/>' rel="stylesheet"/>
    <style>
        .navbar[class*="bg-"] .nav > li > a,.navbar[class*="bg-"] .nav > li.open > a {
            color:#f67b7b;
        }
        .navbar[class*="bg-"] .nav > li > a:hover {
            color : #4c6069;
        }
    </style>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

</head>

<body class="dashboard-page">
<div id="main">
    <header class="navbar navbar-fixed-top bg-default">

        <div class="navbar-branding dark bg-default">
            <a class="navbar-brand" href="/admin">
                <c:if test="${settings.logo == null}">
                    <img src='<c:url value="resources/img/logo.png"/>' style="width:160px;height:60px;"alt="one shop point logo">
                </c:if>
                <c:if test="${settings.logo != null}">
                    <img src='<c:url value="/images/${settings.logo}"/>' style="width:160px;height:60px;"alt="${settings.branding}">
                </c:if>
            </a>
            <span id="toggle_sidemenu_l" class="fa fa-align-left"></span>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <sec:authentication var="user" property="principal" />
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                    <span class="pl15">${user.username}</span>
                    <span class="fa fa-caret-down p5 hidden-xs bold color-1"></span>
                </a>
                <ul class="dropdown-menu" role="menu">
                    <li></li>
                    <li><a href="" onclick="return getContent('/admin/password')"><span class="fa fa-key"></span>Change Password</a></li>
                    <li class="divider"></li>
                    <li><a href="/j_spring_security_logout"><span class="fa fa-power-off"></span> Logout</a></li>
                </ul>
            </li>
        </ul>

        <div class="loading col-md-4 col-md-offset-4" style="color:#FFF;padding-top:20px;display: none;"><i
                class="fa fa-spinner fa-spin fa-2x "></i><span style="padding:5px;">loading</span></div>
    </header>
    <aside id="sidebar_left" class="nano nano-light affix sidebar-light has-scrollbar">
        <div class="sidebar-left-content nano-content">
            <ul class="nav sidebar-menu">
                <!-- sidebar bullets -->
                <li class="sidebar-label pt30">Menu</li>
                <li>
                    <a href="/admin">
                        <span class="fa fa-dashboard"></span>
                        <span class="sidebar-title">Dashboard</span>
                    </a>
                </li>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_CATEGORIES')">
                <li>
                    <a href="" onclick="return getContent('/category/view/admin/table')">
                        <span class="fa fa-codepen"></span>
                        <span class="sidebar-title">Categories</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_LOCATIONS')">
                <li>
                    <a href="" onclick="return getContent('/location/view/admin/table')">
                        <span class="fa fa-globe"></span>
                        <span class="sidebar-title">Locations</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_MANUFACTURERS')">
                <li>
                    <a href="" onclick="return getContent('/manufacturer/view/admin/table')">
                        <span class="fa fa-bank"></span>
                        <span class="sidebar-title">Manufacturers</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_FEATURES')">
                <li>
                    <a href="" onclick="return getContent('/feature/view/admin/table')">
                        <span class="fa fa-bars"></span>
                        <span class="sidebar-title">Features</span>
                    </a>
                </li>
                </sec:authorize>
                <%--<sec:authorize access="hasAnyRole('ROLE_MANAGE_DOSAGE_FORMS')">--%>
                    <li>
                        <a href="" onclick="return getContent('/dosage_form/view/admin/table')">
                            <span class="fa fa-bars"></span>
                            <span class="sidebar-title">Dosage Forms</span>
                        </a>
                    </li>
                <%--</sec:authorize>--%>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_INNS')">
                <li>
                    <a href="" onclick="return getContent('/inn/view/admin/table')">
                        <span class="fa fa-heart"></span>
                        <span class="sidebar-title">INNs</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_MEDIC_TYPES')">
                <li>
                    <a href="" onclick="return getContent('/medictype/view/admin/table')">
                        <span class="fa fa-heartbeat"></span>
                        <span class="sidebar-title">MedicTypes</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_CARRIERS')">
                <li>
                    <a href="" onclick="return getContent('/carrier/view/admin/table')">
                        <span class="fa fa-truck"></span>
                        <span class="sidebar-title">Carriers</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_RETAILERS')">
                <li>
                    <a href="" onclick="return getContent('/retailer/view/admin/table')">
                        <span class="fa fa-sitemap"></span>
                        <span class="sidebar-title">Retailers</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_PRODUCTS')">
                <li>
                    <a href="" onclick="return getContent('/product/view/admin/table')">
                        <span class="fa fa-tag"></span>
                        <span class="sidebar-title">Products</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_VIEW_ORDERS')">
                <li>
                    <a href="" onclick="return getContent('/order/view/admin/table?page=1&parts=20')">
                        <span class="fa fa-shopping-cart"></span>
                        <span class="sidebar-title">Orders</span>
                    </a>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_USERS')">
                <li>
                    <a class="accordion-toggle" href="#">
                        <span class="fa fa-users"></span>
                        <span class="sidebar-title">Users</span>
                        <span class="h-plus"></span>
                    </a>
                    <ul class="nav sub-nav">
                        <li>
                            <a href="" onclick="return getContent('/user/view/admin/staff/table')">
                                <span class="glyphicon glyphicon-user"></span> Staff </a>
                        </li>
                        <li>
                            <a href="" onclick="return getContent('/user/view/admin/customers/table')">
                                <span class="glyphicon glyphicon-tag"></span> Customers </a>
                        </li>
                        <li>
                            <a href="" onclick="return getContent('/user/view/admin/affiliates/table')">
                                <span class="glyphicon glyphicon-shopping-cart"></span> Retailers </a>
                        </li>
                        <li>
                            <a href="" onclick="return getContent('/user/view/admin/medics/table')">
                                <span class="glyphicon glyphicon-bishop"></span> Medics </a>
                        </li>
                    </ul>
                </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_MANAGE_SETTINGS')">
                <li>
                    <a href="" onclick="return getContent('/admin/settings')">
                        <span class="fa fa-cogs"></span>
                        <span class="sidebar-title">Settings</span>
                    </a>
                </li>
                </sec:authorize>
            </ul>
            <div class="sidebar-toggle-mini">
                <a href="#">
                    <span class="fa fa-sign-out"></span>
                </a>
            </div>
        </div>
    </aside>
    <section id="content_wrapper">
        <section id="content" class="table-layout">
            <div style="height: 984px;" class="tray tray-center">
                <div class="admin-panels ui-sortable animated fadeIn">
                    <div class="row">
                        <div id="grid-0" class="col-md-12 admin-grid">
                            <div class="panel mb25 mt5">
                                <div class="panel-heading">
                                    <span class="panel-title hidden-xs"> Sales</span>
                                    <ul class="nav panel-tabs-border panel-tabs">
                                        <li class="active">
                                            <div class="form-group" style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="yearSales" onchange="getSalesGraph()" class="select2-single form-control">
                                                    <jsp:useBean id="date" class="java.util.Date" />
                                                    <fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
                                                    <c:forEach items="${years}" var="year">
                                                        <option value="${year}" <c:if test="${year == currentYear}">selected="selected"</c:if>>${year}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group"  style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="retailerSales" onchange="getSalesGraph()" class="select2-single form-control">
                                                    <c:forEach items="${retailers}" var="retailer">
                                                        <option value="${retailer.id}">${retailer.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group" style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="locationSales" onchange="getSalesGraph()" class="select2-single form-control">
                                                    <c:forEach items="${locations}" var="location">
                                                        <option value="${location.id}" <c:if test="${location.id == root.id}">selected="selected"</c:if>>${location.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="panel-body p20 pb10">
                                    <canvas id="sales" width="500px" height="100px"></canvas>
                                </div>
                            </div>
                            <div class="panel mb25 mt5">
                                <div class="panel-heading">
                                    <span class="panel-title hidden-xs">Carrier Sales</span>
                                    <ul class="nav panel-tabs-border panel-tabs">
                                        <li class="active">
                                            <div class="form-group" style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="yearCarrierSales" onchange="getCarrierSalesGraph()" class="select2-single form-control">
                                                    <fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
                                                    <c:forEach items="${years}" var="year">
                                                        <option value="${year}" <c:if test="${year == currentYear}">selected="selected"</c:if>>${year}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group"  style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="carrierCarrierSales" onchange="getCarrierSalesGraph()" class="select2-single form-control">
                                                    <c:forEach items="${carriers}" var="carrier">
                                                        <option value="${carrier.id}">${carrier.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group" style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="locationCarrierSales" onchange="getCarrierSalesGraph()" class="select2-single form-control">
                                                    <c:forEach items="${locations}" var="location">
                                                        <option value="${location.id}" <c:if test="${location.id == root.id}">selected="selected"</c:if>>${location.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="panel-body p20 pb10">
                                    <canvas id="carrierSales" width="500px" height="100px"></canvas>
                                </div>
                            </div>
                            <div class="panel mb25 mt5">
                                <div class="panel-heading">
                                    <span class="panel-title hidden-xs">Top 3 Popular products</span>
                                    <ul class="nav panel-tabs-border panel-tabs">
                                        <li class="active">
                                            <div class="form-group" style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="yearPopularProducts" onchange="getPopularProductGraph()" class="select2-single form-control">
                                                    <fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
                                                    <c:forEach items="${years}" var="year">
                                                        <option value="${year}" <c:if test="${year == currentYear}">selected="selected"</c:if>>${year}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group"  style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="retailerPopularProducts" onchange="getPopularProductGraph()" class="select2-single form-control">
                                                    <c:forEach items="${retailers}" var="retailer">
                                                        <option value="${retailer.id}">${retailer.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="panel-body p20 pb10">
                                    <canvas id="popularProducts" width="500px" height="100px"></canvas>
                                </div>
                            </div>
                            <div class="panel mb25 mt5">
                                <div class="panel-heading">
                                    <span class="panel-title hidden-xs">Prescriptions</span>
                                    <ul class="nav panel-tabs-border panel-tabs">
                                        <li class="active">
                                            <div class="form-group" style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="yearPrescription" onchange="getPrescriptionGraph()" class="select2-single form-control">
                                                    <fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
                                                    <c:forEach items="${years}" var="year">
                                                        <option value="${year}" <c:if test="${year == currentYear}">selected="selected"</c:if>>${year}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group"  style="margin:10px;display: inline-block;">
                                                <select style="width:200px;" id="medicPrescription" onchange="getPrescriptionGraph()" class="select2-single form-control">
                                                        <option value=''>select a medic </option>
                                                    <c:forEach items="${medicUsers}" var="medicUser">
                                                        <option value="${medicUser.medic.id}">${medicUser.firstname} ${medicUser.lastname}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="panel-body p20 pb10">
                                    <canvas id="prescriptions" width="500px" height="100px"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </section>
    </section>
</div>
<!-- BEGIN: PAGE SCRIPTS -->

<!-- jQuery -->
<script src='<c:url value="resources/vendor/jquery/jquery-1.11.1.min.js"/>'></script>
<script src='<c:url value="resources/vendor/jquery/jquery_ui/jquery-ui.min.js"/>'></script>

<!-- Bootstrap Tabdrop Plugin -->
<script src='<c:url value="resources/vendor/plugins/tabdrop/bootstrap-tabdrop.js"/>'></script>

<!-- FullCalendar Plugin + moment Dependency -->
<script src='<c:url value="resources/vendor/plugins/fullcalendar/lib/moment.min.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/select2/select2.min.js"/>'></script>

<!-- Theme Javascript -->
<script src='<c:url value="resources/assets/js/utility/utility.js"/>'></script>
<script src='<c:url value="resources/assets/js/main.js"/>'></script>
<script src='<c:url value="resources/js/Chart.js"/>'></script>
<!-- Datatables -->
<script src='<c:url value="resources/vendor/plugins/datatables/media/js/jquery.dataTables.js"/>'></script>

<!-- Datatables Bootstrap Modifications  -->
<script src='<c:url value="resources/vendor/plugins/datatables/media/js/dataTables.bootstrap.js"/>'></script>
<script src='<c:url value="resources/vendor/plugins/toastr/toastr.min.js"/>'></script>
<script type="text/javascript">
    jQuery(document).ready(function () {

        "use strict";

        // Init Theme Core
        Core.init();
    });

    function isDefined(x) {
        var undefined;
        return x !== undefined;
    }

    //docking the main content
    var dockC = '';
    function getContent(contentLoc) {
        $.ajax({
            url: '' + contentLoc,
            type: "GET",
            beforeSend: function () {
                $('.loading').fadeIn('slow');
            },
            complete: function (xhr, status) {
                $('#grid-0').html(xhr.responseText);
                $('.loading').fadeOut('slow');
            }
        });

        return false;
    }

    //delete a record from the database
    function execDelete(dockOf,handler) {
        if(confirm("Are you sure you want to delete this item(s)")) {
            $.ajax({
                url: '/api' + dockOf,
                type: "DELETE",
                beforeSend: function () {
                    $('.loading').fadeIn('slow');
                },
                complete: function (xhr, status) {
                    data = JSON.parse(xhr.responseText);
                    if (data.status == 'CREATED' || data.status == 'MODIFIED' || data.status == 'DELETED') {
                        showToast(data.message, 'success');
                    }

                    if (data.status == 'ERROR') {
                        showToast(data.message, 'error');
                    }

                    if (data.status == 'FAILED') {
                        showToast(data.message, 'warning');
                    }
                    handler.call();
                    $('.loading').fadeOut('slow');
                }
            });
        }

    }

    var postLoc, formContent;
    function passData(loc,method,formContent,handler) {
        $.ajax({
            url: '/api' + loc,
            type: method,
            data: JSON.stringify(formContent),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            beforeSend: function () {
                $('.loading').fadeIn('slow');
            },
            success: function (data, status) {
                if (data.status == 'CREATED' || data.status == 'MODIFIED' || data.status == 'DELETED') {
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


    function callGet(getLoc, handler) {
        $.ajax({
            url: '/api' + getLoc,
            type: "GET",
            beforeSend: function () {
                $('.loading').fadeIn('slow');
            },
            success: function (data, status) {
                handler.call(data);
                $('.loading').fadeOut('slow');
            }
        });
    }


    function callPost(postLoc, param, handler) {

        $.ajax({
            url: '/api' + postLoc,
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(param),
            beforeSend: function () {
                $('.loading').fadeIn('slow');
            },
            complete: function (data, status) {
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
                url : '/api' + uploadLoc,
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
    var locationTree = '';
    var locationId = '';

    function useLocations ()  {
        locationTree = this.data;
        generateStructure(locationTree.children);
    }

    function searchTree(id) {
        var queue = [];
        queue.push(locationTree);
        while(queue.length != 0) {
            var nd = queue.shift();
            if (nd.children.length > 0) {
                queue = queue.concat(nd.children);
            }

            if(id == nd.id) {
                if(nd.children.length == 0) {
                    locationId = id;
                    return;
                }
                return nd.children;
            }
        }
    }

    function generateStructure (data) {
        var idx =0;
        if(isDefined(data) && data.length > 0) {
            var html ="";
            html+="<label for='locations' style='margin-bottom:10px;' id='"+data[0].label+"' class='field'><select id='locations' onchange=\"$('"+'#'+data[0].label+"').nextAll().remove();generateStructure(searchTree(this.value))\" class='select2-single form-control'><option>"+data[0].label+"</option>";
            while(idx < data.length) {
                html+="<option value='"+data[idx].id+"'>"+data[idx].name+"</option>";
                idx++;
            }
            html+="</select></label>";
            $("#location").append(html).show();
            $(".select2-single").select2();
        }
    }
    var tempDay = new Date();
    callGet("/analytics/sales?year="+tempDay.getFullYear()+"&locationId=${root.id}",drawSalesGraph);
    var firstTime = true;

    function drawSalesGraph () {
        var data = this.data;
        var salesChart = new Chart($('#sales'), {
                type: 'line',
                data: data
        });
        $(".select2-single").select2();
        if(firstTime) {
            callGet("/analytics/sales?year="+tempDay.getFullYear()+"&locationId=${root.id}&carrier",drawCarrierSalesGraph);
        }
    }

    function getSalesGraph() {
        var year = $('#yearSales').val();
        var location = $('#locationSales').val();
        var retailer = $('#retailerSales').val();
        if(year != '' && location != '' && retailer != '') {
            callGet("/analytics/sales?year="+year+"&locationId="+location+"&retailerId="+retailer,drawSalesGraph);
        } else if (year != '' && location != '') {
            callGet("/analytics/sales?year="+year+"&locationId="+location,drawSalesGraph);
        }
    }


    function drawCarrierSalesGraph () {
        var data = this.data;
        var CarrierSalesChart = new Chart($('#carrierSales'), {
            type: 'line',
            data: data
        });
        $(".select2-single").select2();
        if(firstTime) {
            callGet("/analytics/products?year="+tempDay.getFullYear(),drawPopularProductGraph);
        }
    }

    function getCarrierSalesGraph() {
        var year = $('#yearCarrierSales').val();
        var location = $('#locationCarrierSales').val();
        var carrier = $('#carrierCarrierSales').val();
        if(year != '' && location != '' && carrier != '') {
            callGet("/analytics/sales?year="+year+"&locationId="+location+"&carrierId="+carrier,drawCarrierSalesGraph);
        } else if (year != '' && carrier != '') {
            callGet("/analytics/sales?year="+year+"&carrierId="+carrierId,drawCarrierSalesGraph);
        }
    }

    function getPopularProductGraph() {
        var year = $('#yearPopularProducts').val();
        var retailer = $('#retailerPopularProducts').val();
        if(year != '' && retailer != '') {
            callGet("/analytics/products?year="+year+"&retailerId="+retailer,drawPopularProductGraph);
        }
    }

    function drawPopularProductGraph () {
        var data = this.data;
        var popularProductsChart = new Chart($('#popularProducts'), {
            type: 'bar',
            data: data
        });
        $(".select2-single").select2();
        if(firstTime) {
            callGet("/analytics/prescriptions?year="+tempDay.getFullYear(),drawPrescriptionGraph);
        }
    }

    function getPrescriptionGraph() {
        var year = $('#yearPrescription').val();
        var medic = $('#medicPrescription').val();
        if(year != '' && medic != '') {
            callGet("/analytics/prescriptions?year="+year+"&medicId="+medic,drawPrescriptionGraph);
        }
    }

    function drawPrescriptionGraph () {
        var data = this.data;
        var prescriptionsChart = new Chart($('#prescriptions'), {
            type: 'line',
            data: data
        });
        $(".select2-single").select2();
        if(firstTime) {
            firstTime = false;
        }
    }

</script>
</body>

</html>