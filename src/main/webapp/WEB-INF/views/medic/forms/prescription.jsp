<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:include page="../../common/header.jsp"/>
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
                    <li>Medic</li>
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
                        <h3 class="panel-title">Medic section</h3>
                    </div>

                    <div class="panel-body">

                        <ul class="nav nav-pills nav-stacked">
                            <li class="active">
                                <a href="${pageContext.request.contextPath}/medic"><i class="fa fa-users"></i>My Patients</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/medic/account"><i class="fa fa-heart"></i>
                                    My account</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/j_spring_security_logout"><i
                                        class="fa fa-sign-out"></i> Logout</a>
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
                <div class="box" style="margin-bottom:25px;min-height:200px;">
                    <h6>${patient.firstname} ${patient.lastname}</h6>
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th>Dosage Form</th>
                            <th style="text-align: center" colspan="3">Quantity X Per Day</th>
                            <th style="text-align: center" colspan="2">Duration</th>
                            <th style="text-align: center">+/-</th>
                        </tr>
                        </thead>
                        <tbody id="prescribe">
                        <c:forEach items="${prescription.prescriptionItems}" var="item" varStatus="status">
                            <tr class="prescription${status.index}">
                                <td>
                                    <select style="min-width:200px;" class="form-control select2-single" id="dosageForm${status.index}" disabled="disabled">
                                            <option value="${item.dosageForm}"  >${item.dosageForm}</option>
                                    </select>
                                </td>
                                <td><input class="form-control" value="${item.frequencyQuantity}" id="quantity${status.index}" type="text"></td>
                                <td>X</td>
                                <td><input class="form-control" value="${item.frequencyPerDay}" id="perDay${status.index}" type="text"></td>
                                <td><input class="form-control" value="${item.duration}" id="duration${status.index}" type="text"></td>
                                <td>
                                    <select class="form-control" id="unit${status.index}">
                                        <option value="days" <c:if test="${item.unit == 'days'}">selected="selected"</c:if> >day(s)</option>
                                        <option value="weeks" <c:if test="${item.unit == 'weeks'}">selected="selected"</c:if> >week(s)</option>
                                    </select>
                                </td>
                                <td>
                                    <button onclick="generatePrescriptionStructure('prescription${status.index}')"
                                            class="btn btn-info"><i class="fa fa-plus" aria-hidden="true"></i></button>
                                </td>
                            </tr>
                            <c:if test="${item.type == 'inn'}">
                                <tr class="prescription${status.index}">
                                    <td>
                                        <div class="btn-group">
                                            <button onclick="getPrescriptionData('inn',this,${status.index})" class="btn btn-info active" >INN</button>
                                            <button onclick="getPrescriptionData('product',this,${status.index})" class="btn btn-danger">Brand name</button>
                                        </div>
                                    </td>
                                    <td colspan="7" style="min-width:200px;"><select onchange="getDosageForm(this.value,'dosageForm${status.index}');" class="form-control select2-single" id="innId${status.index}">
                                        <c:forEach items="${inns}" var="inn"><option value="${inn.id}" <c:if test="${item.inn.id == inn.id}">selected="selected"</c:if> >${inn.name}</option></c:forEach>
                                    </select></td>
                                </tr>
                            </c:if>
                            <c:if test="${item.type == 'product'}">
                                <tr class="prescription${status.index}">
                                    <td>
                                        <div class="btn-group">
                                            <button onclick="getPrescriptionData('inn',this,${status.index})" class="btn btn-info" >INN</button>
                                            <button onclick="getPrescriptionData('product',this,${status.index})" class="btn btn-danger active">Brand name</button>
                                        </div>
                                    </td>
                                    <td colspan="7" style="min-width:200px;" class="required prescriptionType"><select onchange="getDosageForm(this.value,'dosageForm${status.index}');" class="form-control select2-single" id="innId${status.index}">
                                        <c:forEach items="${products}" var="product"><option value="${product.id}" <c:if test="${item.product.id == product.id}">selected="selected"</c:if> >${product.name}</option></c:forEach>
                                    </select></td>
                                </tr>
                            </c:if>
                            <tr class="prescription${status.index}">
                                <td colspan="8"><textarea class="form-control" id="note${status.index}" placeholder="ADDITIONAL INFORMATION OR YOU CAN REPEAT THE PRESCRIPTION IN YOUR OWN WORDS">${item.note}</textarea></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="form-group">
                        <c:if test="${prescription == null}">
                            <input type="submit" onclick="submitPrescription(this,'POST')" value="prescribe" class="btn btn-info form-control">
                        </c:if>
                        <c:if test="${prescription != null}">
                            <input type="submit" onclick="submitPrescription(this,'PUT')" value="prescribe" class="btn btn-info form-control">
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
<jsp:include page="../../common/footer.jsp"/>
<script>
    $(".select2-single").select2();
    <c:if test="${prescription != null}">
    var idx = ${fn:length(prescription.prescriptionItems)};
    </c:if>
    <c:if test="${prescription == null}">
    var idx = 0;
    </c:if>
    <c:if test="${prescription == null || fn:length(prescription.prescriptionItems) == 0}">
    generatePrescriptionStructure();
    </c:if>
    function generatePrescriptionStructure() {
        var select = '<select onchange="getDosageForm(this.value,\'dosageForm'+idx+'\');" class="form-control select2-single" id="innId' + idx + '" ><c:forEach items="${inns}" var="inn"><option value="${inn.id}">${inn.name}</option></c:forEach></select>';
        var html = '<tr class="prescription' + idx + '">'+
                '<td class="required" ><select type="text" style="min-width:200px;" class="form-control select2-single" id="dosageForm' + idx + '"></select></td><td><input type="text" class="form-control" id="quantity' + idx + '"></td><td>X</td>' +
                '<td class="required" ><input type="text" class="form-control" id="perDay' + idx + '"></td><td class="required" ><input type="text" class="form-control" id="duration' + idx + '"></td>' +
                '<td class="required" ><select style="min-width:100px;"class="form-control" id="unit'+idx+'"><option value="days" >day(s)</option><option value="weeks" >week(s)</option></select></td>'+
                '<td><button onclick="generatePrescriptionStructure(\'prescription' + idx + '\')" class="btn btn-info"><i class="fa fa-plus" aria-hidden="true"></i></button>';
        if (idx > 0) {
            html += '<button onclick="removePrescriptionStructure(\'prescription' + idx + '\')" class="btn btn-warning"><i class="fa fa-minus" aria-hidden="true"></i></button></td>';
        } else {
            html += '</td><tr>';
        }
        html+='<tr class="prescription'+ idx +'"><td><div class="btn-group"><button onclick="getPrescriptionData(\'inn\',this,' + idx + ')" class="btn btn-info active" >INN</button><button onclick="getPrescriptionData(\'product\',this,' + idx + ')" class="btn btn-danger">Brand name</button></div></td><td colspan="6" class="required prescriptionType" style="min-width:200px;">' + select + '</td></tr>';
        html+='<tr class="prescription' + idx + '"><td colspan="7"><textarea class="form-control" id="note'+idx+'" placeholder="ADDITIONAL INFORMATION OR YOU CAN REPEAT THE PRESCRIPTION IN YOUR OWN WORDS"></textarea></td></tr>';

        $('#prescribe').append(html);
        $(".select2-single").select2();
        idx++;
    }

    var tp = '';
    var secId = '';
    function insertPrescriptionData() {
        var data = this.data;
        var select = '';
        if(tp == "inn") {
            select += '<select onchange="getDosageForm(this.value,\'dosageForm'+(idx-1)+'\');" class="form-control select2-single" id="innId' + (idx-1) + '" >';
        }

        if(tp == "product"){
            select = '<select onchange="getDosageFormByProduct(this.value,\'dosageForm'+(idx-1)+'\');" class="form-control select2-single" id="productId' + (idx-1) + '" >';
        }

        for (j = 0; j < data.length; j++) {
            select+='<option value="'+data[j].id+'">'+data[j].name+'</option>';
        }

        select+='<select>';
        $('.prescription'+secId).find('.prescriptionType').html(select);
        $(".select2-single").select2();
    }

    function getPrescriptionData(type,btn,id) {
        if(type === "inn") {
            callGet('/api/inn?medic=allowed',insertPrescriptionData);
        }

        if(type === "product") {
            callGet('/api/product?medic=allowed',insertPrescriptionData);
        }
        tp = type;
        secId  = id;
        $('.prescription'+id).find("button").removeClass('active');
        $(btn).addClass("active");
    }

    function removePrescriptionStructure(id) {
        $('.' + id).remove();
        idx--;
    }

    function submitPrescription(elm,method) {
        var formContent = {};
        <c:if test="${prescription != null}">
        formContent.id = ${prescription.id};
        </c:if>
        try {
            formContent.prescriptionItems = [];
            for(var count = 0;count <idx; count++) {
                if($('.prescription'+count).length != 0 &&
                        $('#dosageForm'+count).length != 0 &&
                        $('#quantity'+count).length != 0 &&
                        $('#perDay'+count).length != 0 &&
                        $('#duration'+count).length != 0 &&
                        $('#unit'+count).length != 0 &&
                        $('#note'+count).length != 0
                ) {
                    var note = null;
                    if ($('#note'+count).val() != '') {
                        note = $('#note'+count).val();
                    }

                    if($('#innId'+count).length != 0) {
                        formContent.prescriptionItems.push({innId : validateSpace('prescription'+count,$('#innId'+count).val()),
                            dosageForm:validateSpace('prescription'+count,$('#dosageForm'+count).val()),
                            frequencyQuantity:validateSpace('prescription'+count,$('#quantity'+count).val()),
                            frequencyPerDay:validateSpace('prescription'+count,$('#perDay'+count).val()),
                            duration:validateSpace('prescription'+count,$('#duration'+count).val()),
                            unit:validateSpace('prescription'+count,$('#unit'+count).val()),
                            note:note,
                            type:"inn"
                        });
                    }

                    if($('#productId'+count).length != 0) {
                        formContent.prescriptionItems.push({productId : validateSpace('prescription'+count,$('#productId'+count).val()),
                            dosageForm:validateSpace('prescription'+count,$('#dosageForm'+count).val()),
                            frequencyQuantity:validateSpace('prescription'+count,$('#quantity'+count).val()),
                            frequencyPerDay:validateSpace('prescription'+count,$('#perDay'+count).val()),
                            duration:validateSpace('prescription'+count,$('#duration'+count).val()),
                            unit:validateSpace('prescription'+count,$('#unit'+count).val()),
                            note:note,
                            type:"product"
                        });
                    }
                }
            }

            formContent.patientId = ${patient.id};
            passData('/api/prescription',method,formContent,refresh);
        } catch (e) {
            $(elm).prop('disabled',false);
            $('.'+e).find( ".required" ).fadeIn().css('border-style','solid').css('border-width','2px').css('border-color','red');
        }
    }

    var selectElm = '';

    function insertDosageForm () {
        if (this.status == 'OK') {
            var data = this.data;
            var html='';
            for (idx = 0; idx < data.length;idx++) {
                html+= "<option value='"+data[idx].name+"'>"+data[idx].name+"</option>";
            }
            $("#"+selectElm).html(html);
            $(".select2-single").select2();
        }
    }

    function getDosageForm (innId,elm) {
        selectElm = elm;
        $("#"+selectElm).prop('disabled',false);
        callGet('/api/dosage_form?innId='+innId,insertDosageForm);
    }

    function getDosageFormByProduct (productId,elm) {
        selectElm = elm;
        $("#"+selectElm).prop('disabled',false);
        callGet('/api/dosage_form?productId='+productId,insertDosageForm);
    }

    function refresh () {
        if(this.status != 'FAILED' || this.status != 'ERROR') {
            location.href='${pageContext.request.contextPath}/medic/prescription?patientId=${patient.id}';
        }
    }

</script>
</body>
</html>