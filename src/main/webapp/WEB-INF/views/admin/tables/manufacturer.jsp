<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-12 admin-grid">
    <div class="panel pb10 panel-widget" id="p05">
        <div class="panel-heading">
            <span class="panel-title"> Manufacturers</span>
            <button id="deleteBtn" onclick="deleteManufacturers()" class="btn btn-warning pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-trash"></i> Delete </button>
            <button id="addBtn" onclick="getContent('/manufacturer/view/admin/form')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-plus"></i> Add </button>
            <button id="editBtn" onclick="getContent('/manufacturer/view/admin/'+selectedIds[0]+'/form');" class="btn btn-info pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-plus"></i> Edit </button>
        </div>
        <div class="panel-body p15">
            <div class="table-responsive">
                <table class="table table-striped table-hover" id="manufacturersTable" width="100%">
                    <thead>
                    <tr>
                        <th>Select</th>
                        <th>Thumbnail</th>
                        <th>Name</th>
                        <th>Phone number</th>
                        <th>E-mail</th>
                        <th>Last Updated</th>
                        <th>Date Created</th>
                        <th>Enabled</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script>
    var selectedIds = [];
    $('#editBtn').prop('disabled', true);
    $('#deleteBtn').prop('disabled', true);
    $('#addBtn').prop('disabled', false);

     function selectId (elm,id) {
         if($(elm).is(':checked')) {
             selectedIds.push(id);
         } else {
             var idx = 0;
             while(idx < selectedIds.length ) {
                 if(selectedIds[idx] == id) {
                     selectedIds.splice(idx, 1);
                 }
                 idx++;
             }
         }

         if(selectedIds.length > 1) {
             $('#editBtn').prop('disabled', true);
         }

         if(selectedIds.length == 1) {
             $('#editBtn').prop('disabled', false);
         }

         if(selectedIds.length > 0) {
             $('#deleteBtn').prop('disabled', false);
         }

         if(selectedIds.length == 0) {
             $('#editBtn').prop('disabled', true);
             $('#deleteBtn').prop('disabled', true);
             $('#addBtn').prop('disabled', false);
         }

     }

    function manufacturersTable() {
        $('#manufacturersTable').DataTable({
            destroy: true,
            data: this.data,
            columns: [
                { data: 'id' },
                { data: 'image' },
                { data: 'name' },
                { data: 'address'},
                { data: 'address'},
                { data: 'updatedOn' },
                { data: 'createdOn'},
                { data: 'enabled' }
            ],
            columnDefs: [
                {
                    "render": function (data, type, row) {
                        return "<input type='checkbox' onclick='selectId(this,"+row.id+")'>";
                    },
                    "targets": 0,
                    "searchable": false
                },
                {
                    "render": function (data, type, row) {
                        if(row.image == null) {
                            return "<img src='<c:url value="resources/assets/img/avatars/no_img.png"/>' alt='avatar' class='mw45 br64'>";
                        }

                        return "<img src='<c:url value="images"/>"+row.image.path+"' alt='avatar' class='mw45 br64'>";
                    },
                    "targets": 1
                },
                {
                    "render": function (data, type, row) {
                        return row.address.phoneNumber;
                    },
                    "targets": 3
                },
                {
                    "render": function (data, type, row) {
                        return row.address.email;
                    },
                    "targets": 4
                },
                {
                    "render":function(data,type,row){if(row.updatedOn != null){return new Date(row.updatedOn).toDateString()}else {return "N/A"}},
                    "targets": 5
                },
                {
                    "render":function(data,type,row){return new Date(row.createdOn).toDateString()},
                    "targets": 6
                }

            ]
        });

    }
    callGet('/manufacturer',manufacturersTable);

    function deleteManufacturers() {
        var url = '/manufacturer?ids='+selectedIds[0];

        for(var idx =1;idx < selectedIds.length;idx++) {
            url+='&ids='+selectedIds[1];
        }
        execDelete(url)
    }
</script>