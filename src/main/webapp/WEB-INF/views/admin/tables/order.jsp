<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-12 admin-grid">
    <div class="panel">
        <div class="panel-menu admin-form theme-primary">
            <div class="row">
                <div class="col-md-4">
                    <label class="field select">
                        <select id="filter-status" onclick="if(this.value=='all') {return getContent('/order/view/admin/table?page=1&parts=20');} else if(this.value!=''){return getContent('/order/view/admin/table?page=1&parts=20&status='+this.value);}" name="filter-status">
                            <option value=''>Filter by status</option>
                            <option value='all'>All</option>
                            <option <c:if test="${status =='ordered'}">selected="selected"</c:if>value='ordered'>Ordered</option>
                            <option <c:if test="${status =='received by retailer'}">selected="selected"</c:if>value='received by retailer'>Received by retailer</option>
                            <option <c:if test="${status =='received by carrier'}">selected="selected"</c:if>value='received by carrier'>Received by carrier</option>
                            <option <c:if test="${status =='transporting'}">selected="selected"</c:if>value='transporting'>Transporting</option>
                            <option <c:if test="${status =='completed'}">selected="selected"</c:if>value='completed'>Completed</option>
                            <option <c:if test="${status =='prepared'}">selected="selected"</c:if>value='prepared'>Prepared</option>
                        </select>
                        <i class="arrow double"></i>
                    </label>
                </div>
                <div class="col-md-6">
                    <label class="field">
                        <input type="text" class="form-control" value="" id="search" placeholder="search customer or invoice">
                    </label>
                </div>
                <div class="col-md-2">
                    <label class="field">
                        <button onclick="if($('#search').val() !='') {return getContent('/order/view/admin/table?page=1&parts=20&search='+$('#search').val());}" class="btn btn-info"><i class="fa fa-search" aria-hidden="true"></i> search</button>
                    </label>
                </div>
            </div>
        </div>
        <div class="panel-body pn">
            <div class="table-responsive">
                <table class="table table-bordered admin-form theme-warning tc-checkbox-1 fs13">
                    <thead>
                    <tr class="bg-light">
                        <th class="text-center">Order Date</th>
                        <th class="text-center">Order ID</th>
                        <th class="text-center" colspan="2">Customer</th>
                        <th class="text-center">Order Total</th>
                        <th class="text-center">Order Status</th>
                        <th class="text-center">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <td>${order.dateOrdered}</td>
                                <td class="text-center" >${order.invoiceNo}</td>
                                <td>${order.firstname}</td>
                                <td>${order.lastname}</td>
                                <td class="text-center" >ksh. ${order.total}</td>
                                <td class="text-center">
                                    <span class="badge <c:if test="${order.status == 'completed'}">badge-success</c:if><c:if test="${order.status != 'completed'}">badge-alert</c:if> " style="display: inline-block; font-size: 15px;">
                                        ${order.status}
                                    </span>
                                </td>
                                <td>
                                    <button onclick="getContent('/order/view/admin?invoice=${order.invoiceNo}');" class="btn btn-info"><i class="fa fa-eye" aria-hidden="true"></i></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="panel-footer">
            <c:if test="${prev != null}">
                <button style="margin-left:5px;width:80px" onclick="getContent('${prev}')" class="btn btn-info">previous</button>
            </c:if>
            <c:if test="${next != null}">
                <button style="margin-left:5px;width:80px" onclick="getContent('${next}')" class="btn btn-info">next</button>
            </c:if>
        </div>
    </div>
</div>
