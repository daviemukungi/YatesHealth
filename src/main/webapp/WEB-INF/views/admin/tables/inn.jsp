<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-12 admin-grid">
    <div class="panel pb10 panel-widget" id="p05">
        <div class="panel-heading">
            <span class="panel-title"> Inns </span>
            <button id="deleteBtn" onclick="deleteInns()" class="btn btn-warning pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-trash"></i> Delete </button>
            <button id="addBtn" onclick="getContent('/inn/view/admin/form')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-plus"></i> Add </button>
            <button id="editBtn" onclick="getContent('/inn/view/admin/'+selectedIds[0]+'/form');" class="btn btn-info pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-plus"></i> Edit </button>
        </div>
        <div class="panel-body p15">
            <div class="table-responsive">
                <table class="table table-striped table-hover" id="innsTable" width="100%">
                    <thead>
                        <tr>
                            <th>Select</th>
                            <th>Name</th>
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
             $('#name'+id).prop('disabled',false);
             $('#enabled'+id).prop('disabled',false);
         } else {
             var idx = 0;
             while(idx < selectedIds.length ) {
                 $('#name'+id).prop('disabled',true);
                 $('#enabled'+id).prop('disabled',true);
                 if(selectedIds[idx] == id) {
                     selectedIds.splice(idx, 1);
                 }
                 idx++;
             }
         }

         if(selectedIds.length > 0) {
             $('#deleteBtn').prop('disabled', false);
             $('#editBtn').prop('disabled', false);
         }

         if(selectedIds.length == 0) {
             $('#deleteBtn').prop('disabled', true);
             $('#addBtn').prop('disabled', false);
         }

     }

    function innsTable() {
        $('#innsTable').DataTable({
            destroy: true,
            data: this.data,
            columns: [
                { data: 'id' },
                { data: 'name' },
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
                    "render": function (data,type,row) {
                        return row.name;
                    },
                    "targets": 1
                },
                {
                    "render":function(data,type,row){if(row.updatedOn != null){return new Date(row.updatedOn).toDateString()}else {return "N/A"}},
                    "targets": 2
                },
                {
                    "render":function(data,type,row){return new Date(row.createdOn).toDateString()},
                    "targets": 3
                },
                {
                    "render": function (data,type,row) {
                        return row.enabled;
                    },
                    "targets": 4
                }

            ]
        });

    }
    callGet('/inn',innsTable);

    function deleteInns() {
        var url = '/inn?ids='+selectedIds[0];

        for(var idx =1;idx < selectedIds.length;idx++) {
            url+='&ids='+selectedIds[idx];
        }
        execDelete(url,refresh);
    }

    function refresh () {
        callGet('/inn',innsTable);
    }
</script>