<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-12 admin-grid">
    <div class="panel pb10 panel-widget" id="p05">
        <div class="panel-heading">
            <span class="panel-title"> Locations</span>
            <button id="deleteBtn" onclick="deleteLocations()" class="btn btn-warning pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-trash"></i> Delete </button>
            <button id="addBtn" onclick="getContent('/location/view/admin/form')" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-plus"></i> Add </button>
            <button id="editBtn" onclick="getContent('/location/view/admin/'+selectedIds[0]+'/form');" class="btn btn-info pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-plus"></i> Edit </button>
        </div>
        <div class="panel-body p15">
            <div class="table-responsive">
                <table class="table table-striped table-hover" id="locationsTable" width="100%">
                    <thead>
                    <tr>
                        <th>Select</th>
                        <th>Name</th>
                        <th>Label</th>
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

    function locationsTable() {
        $('#locationsTable').DataTable({
            destroy: true,
            data: this.data,
            columns: [
                { data: 'id' },
                { data: 'name' },
                { data: 'label' },
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
                    "render":function(data,type,row){if(row.updatedOn != null){return new Date(row.updatedOn).toDateString()}else {return "N/A"}},
                    "targets": 3
                },
                {
                    "render":function(data,type,row){return new Date(row.createdOn).toDateString()},
                    "targets": 4
                }

            ]
        });

    }
    callGet('/location',locationsTable);

    function deleteLocations() {
        var url = '/location?ids='+selectedIds[0];

        for(var idx =1;idx < selectedIds.length;idx++) {
            url+='&ids='+selectedIds[1];
        }
        execDelete(url)
    }
</script>