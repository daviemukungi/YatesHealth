<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-md-12 admin-grid">
    <div class="panel pb10 panel-widget" id="p05">
        <div class="panel-heading">
            <span class="panel-title"> Features </span>
            <button id="deleteBtn" onclick="deleteFeatures()" class="btn btn-warning pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-trash"></i> Delete </button>
            <button id="addBtn" onclick="addForm()" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;"><i class="fa fa-bars"></i> Add </button>
            <button id="saveBtn" onclick="saveFeatures()" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;" disabled><i class="fa fa-plus"></i> Save </button>
            <button id="editBtn" onclick="updateFeatures()" class="btn btn-default pull-right" style="margin-top:15px;margin-right: 10px;" disabled><i class="fa fa-pencil"></i> Update </button>
        </div>
        <div class="panel-body p15">
            <div class="table-responsive">
                <table class="table table-striped table-hover" id="featuresTable" width="100%">
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
    $('#deleteBtn').prop('disabled', true);
    $('#addBtn').prop('disabled', false);
    var count = 0;

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

    function addForm () {
        var newDate = new Date();
        var html = '<tr><td></td><td><input type="text" id="addName'+count+'" value=""></td><td>N/A</td><td>'+newDate.toDateString()+'</td><td><input type="checkbox" id="addEnabled'+count+'"></td></tr>';
       $('#featuresTable tbody').prepend(html);
        $('#saveBtn').prop('disabled', false);
        count++;
    }

    function saveFeatures () {
        var formContent = {};
        formContent.featureFormList=[];
        for(var idx = 0;idx < count;idx++) {
            if($('#addName'+idx).length != 0 && $('#addEnabled'+idx).length != 0) {
                var enabled = '';
                if($('#addName'+idx).val() != '') {
                    if($("#addEnabled"+idx).is(':checked')) {enabled = true;} else {enabled = false;}
                    formContent.featureFormList.push({feature:$('#addName'+idx).val(),enabled:enabled});
                }
            }
        }

        if(formContent.featureFormList.length > 0 ) {
            passData('/feature','POST',formContent,refresh);
        }
    }

    function featuresTable() {
        $('#featuresTable').DataTable({
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
                        return "<input type='text' id='name"+row.id+"' value='"+row.name+"' disabled >";
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
                        if(row.enabled) {
                            return "<input type='checkbox' checked = 'checked' id='enabled'"+row.id+" disabled >";
                        }
                        return "<input type='checkbox' id='enabled"+row.id+"' disabled >";
                    },
                    "targets": 4
                }

            ]
        });

    }
    callGet('/feature',featuresTable);

    function deleteFeatures() {
        var url = '/feature?ids='+selectedIds[0];

        for(var idx =1;idx < selectedIds.length;idx++) {
            url+='&ids='+selectedIds[idx];
        }
        execDelete(url);
    }

    function updateFeatures() {
        var formContent = {};
        formContent.featureFormList= [];
        for(var idx =0;idx < selectedIds.length;idx++) {
            var enabled = '';
            if($('#name'+idx).val() != '') {
                if($("#enabled"+selectedIds[idx]).is(':checked')) {enabled = true;} else {enabled = false;}
                formContent.featureFormList.push({id:selectedIds[idx],feature:$('#name'+selectedIds[idx]).val(),enabled:enabled});
            }
        }

        if(formContent.featureFormList.length > 0 ) {
            passData('/feature','PUT',formContent,refresh);
        }
    }

    function refresh () {
        callGet('/feature',featuresTable);
    }
</script>