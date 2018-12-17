<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:include page="../common/header.jsp"/>
<div id="heading-breadcrumbs">
    <div class="container">
        <div class="row">
            <div class="col-md-7">
                <h1>E-Shop</h1>
            </div>
            <div class="col-md-5">
                <ul class="breadcrumb">
                    <li><a href="/">Home</a>
                    </li>
                    <li>Carrier</li>
                </ul>

            </div>
        </div>
    </div>
</div>

<div id="content">
    <div class="container">
        <div class="row">
            <!-- *** RIGHT COLUMN ***
          _________________________________________________________ -->

            <div class="col-md-3">
                <!-- *** CUSTOMER MENU ***
            _________________________________________________________ -->
                <div class="panel panel-default sidebar-menu">

                    <div class="panel-heading">
                        <h3 class="panel-title">Carrier section</h3>
                    </div>

                    <div class="panel-body">

                        <ul class="nav nav-pills nav-stacked">
                            <li>
                                <a href="${pageContext.request.contextPath}/carrier"><i class="fa fa-shopping-cart" aria-hidden="true"></i> Orders</a>
                            </li>
                            <li class="active">
                                <a href="${pageContext.request.contextPath}/carrier/plan"><i class="fa fa-list"></i> Plans</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/carrier/account"><i class="fa fa-heart"></i> My account</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i class="fa fa-sign-out"></i> Logout</a>
                            </li>
                        </ul>
                    </div>

                </div>
                <!-- /.col-md-3 -->

                <!-- *** CUSTOMER MENU END *** -->
            </div>

            <!-- *** RIGHT COLUMN END *** -->

            <!-- *** LEFT COLUMN ***
          _________________________________________________________ -->

            <div class="col-md-9">
                <div class="box row">
                    <div id="nameDiv" class="name form-group col-md-12">
                        <label for="name">Name</label>
                        <input type="text" value="${plan.name}" class="form-control" id="name">
                        <span style="display: none">Please enter the plan's  name</span>
                    </div>
                    <div id="shippingFactorDiv" class="shippingFactor form-group col-md-6">
                        <label for="shippingFactor">shipping factor (cm<sup>3</sup>)</label>
                        <input type="text" value="${plan.shippingFactor}" class="form-control" id="shippingFactor">
                        <span style="display: none">Please enter the plan's maximum shipping factor</span>
                    </div>
                    <div id="maximumPackSizeDiv" class="maximumPackSize form-group col-md-6">
                        <label for="maximumPackSize">maximum package size (cm)</label>
                        <input type="text" value="${plan.maximumPackSize}" class="form-control" id="maximumPackSize">
                        <span style="display: none">Please enter the plan's maximum package size</span>
                    </div>
                    <div id="baseWeightDiv" class="baseWeight form-group col-md-6">
                        <label for="baseWeight">Base weight(kg)</label>
                        <input type="text" value="${plan.baseWeight}" class="form-control" id="baseWeight">
                        <span style="display: none">Please enter the plan's base weight</span>
                    </div>
                    <div id="maximumWeightDiv" class="maximumWeight form-group col-md-6">
                        <label for="maximumWeight">maximum weight(kg)</label>
                        <input type="text" value="${plan.maximumWeight}" class="form-control" id="maximumWeight">
                        <span style="display: none">Please enter the plan's maximum weight</span>
                    </div>
                    <div id="exceedWeightDiv" class="exceedWeight form-group col-md-6">
                        <label for="exceedWeight">Unit Excess weight(kg)</label>
                        <input type="text" value="${plan.exceedWeight}" class="form-control" id="exceedWeight">
                        <span style="display: none">Please enter the plan's unit excess weight</span>
                    </div>
                    <div id="exceedChargeDiv" class="exceedCharge form-group col-md-6">
                        <label for="exceedCharge">Unit Excess charge(ksh)</label>
                        <input type="text" value="${plan.exceedCharge}" class="form-control" id="exceedCharge">
                        <span style="display: none">Please enter the plan's unit excess charge</span>
                    </div>
                    <table class="table table-responsive table-striped">
                        <thead>
                        <tr>
                            <th>Origin</th>
                            <th>Destination</th>
                            <th>Price(ksh)</th>
                            <th>Excess Weight(kg)</th>
                            <th>Excess Charge(ksh)</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody id="rates">
                        <c:forEach items="${plan.carrierRates}" var="rate" varStatus="status">
                            <tr id="rate${status.index}">
                                <td style="min-width:150px;">
                                    <select class="form-control select2-single" id="originId${status.index}">
                                        <c:forEach items="${locations}" var="location">
                                            <option value="${location.id}" <c:if test="${rate.origin.id == location.id}">selected="selected"</c:if> >${location.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td style="min-width:150px;">
                                    <select class="form-control select2-single" id="destinationId${status.index}">
                                        <c:forEach items="${locations}" var="location">
                                            <option value="${location.id}" <c:if test="${rate.destination.id == location.id}">selected="selected"</c:if> >${location.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input class="form-control" value="${rate.price}" id="price${status.index}" type="text"></td>
                                <td><input class="form-control" value="${rate.exceedWeight}" id="exceedWeight${status.index}" type="text"></td>
                                <td><input class="form-control" value="${rate.exceedCharge}" id="exceedCharge${status.index}" type="text"></td>
                                <td>
                                    <button onclick="generateRateStructure('rate${status.index}')"
                                            class="btn btn-info"><i class="fa fa-plus" aria-hidden="true"></i></button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="form-group">
                        <c:if test="${plan == null}">
                            <input type="submit" onclick="submitPlan(this,'POST')" value="Save Plan" class="btn btn-info form-control">
                        </c:if>
                        <c:if test="${plan != null}">
                            <input type="submit" onclick="submitPlan(this,'PUT')" value="Save Plan" class="btn btn-info form-control">
                        </c:if>
                    </div>
                </div>
                <!-- /.box -->

            </div>
            <!-- /.col-md-9 -->

            <!-- *** LEFT COLUMN END *** -->

        </div>
    </div>
</div>
<jsp:include page="../common/footer.jsp"/>
<script>
    <c:if test="${plan != null}">
    var idx = ${fn:length(plan.carrierRates)};
    </c:if>
    <c:if test="${plan == null}">
    var idx = 0;
    </c:if>
    <c:if test="${plan == null || fn:length(plan.carrierRates) == 0}">
    generateRateStructure();
    </c:if>
    function generateRateStructure() {
        var origin = '<select class="form-control select2-single" id="originId' + idx + '" ><c:forEach items="${locations}" var="location"><option value="${location.id}">${location.name}</option></c:forEach></select>';
        var destination = '<select class="form-control select2-single" id="destinationId' + idx + '" ><c:forEach items="${locations}" var="location"><option value="${location.id}">${location.name}</option></c:forEach></select>';
        var html = '<tr id=rate' + idx + '><td style="min-width:150px;">' + origin + '<td style="min-width:150px;">' + destination+'</td><td><input type="text" class="form-control" id="price' + idx + '"></td>' +
                '<td><input type="text" class="form-control" id="exceedWeight' + idx + '"></td><td><input type="text" class="form-control" id="exceedCharge' + idx + '"></td>' +
                '<td><button onclick="generateRateStructure(\'rate' + idx + '\')" class="btn btn-info"><i class="fa fa-plus" aria-hidden="true"></i></button>';
        if (idx > 0) {
            html += '<button onclick="removeRateStructure(\'rate' + idx + '\')" class="btn btn-warning"><i class="fa fa-minus" aria-hidden="true"></i></button></td>';
        } else {
            html += '</td><tr>';
        }

        $('#rates').append(html);
        $(".select2-single").select2();
        idx++;
    }

    function removeRateStructure(id) {
        $('#' + id).remove();
        idx--;
    }

    function submitPlan(elm,method) {
        var formContent = {};
        <c:if test="${plan != null}">
        formContent.id = ${plan.id};
        </c:if>
        try {
            $(elm).prop('disabled',true);
            formContent.carrierRates = [];
            for(var count = 0;count <idx; count++) {
                console.log('here');
                if($('#originId'+count).length != 0 &&
                        $('#destinationId'+count).length != 0 &&
                        $('#price'+count).length != 0 &&
                        $('#exceedWeight'+count).length != 0 &&
                        $('#exceedCharge'+count).length != 0
                ) {
                    formContent.carrierRates.push({originId : validateSpace('rate'+count,$('#originId'+count).val()),
                        destinationId:validateSpace('rate'+count,$('#destinationId'+count).val()),
                        price:validateSpace('rate'+count,$('#price'+count).val()),
                        exceedWeight:$('#exceedWeight'+count).val(),
                        exceedCharge:$('#exceedCharge'+count).val()
                    });
                }
            }

            formContent.name= validateSpace("nameDiv",$('#name').val());
            formContent.shippingFactor = validateSpace("shippingFactorDiv",$('#shippingFactor').val());
            formContent.baseWeight = validateSpace("baseWeightDiv",$('#baseWeight').val());
            formContent.maximumWeight = validateSpace("maximumWeightDiv",$('#maximumWeight').val());
            formContent.maximumPackSize = validateSpace("maximumPackSizeDiv",$('#maximumPackSize').val());
            formContent.exceedWeight = validateSpace("exceedWeightDiv",$('#exceedWeight').val());
            formContent.exceedCharge = validateSpace("exceedChargeDiv",$('#exceedCharge').val());
            passData('/api/carrier?plan',method,formContent,refresh);
        } catch (e) {
            $(elm).prop('disabled',false);
            activateError(e);
        }
    }

    function refresh () {
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/carrier/plan';
        }
    }
    $(".select2-single").select2();

</script>
</body>
</html>