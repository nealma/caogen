$(document).ready(function(){
	$("#pt").panel();
	$("#lingyiTable").datagrid({
		url: '../../params',
        method: 'GET',
		toolbar: [
			{
				text:'新增',
				iconCls:'icon-add',
				handler:function(){
					updateFlag = 0;
                    //TODO: CLear Form
                    $(':input','#editFm').val('');
                    $("#dialog").dialog('open');
					$("#paraName").prop("disabled", false);
				}
			},'-',{
				text:'修改',
				iconCls:'icon-edit',
				handler:function(){
					updateFlag = 1;
					var rows = $('#lingyiTable').datagrid('getSelected');
					if(rows){
						bindForm("editFm", rows, "");
						$("#dialog").dialog('open');
						$("#paraName").prop("disabled", true);
					}else{
						$.messager.alert('提示', '请选择要修改的系统参数信息！');
					}
				}
			},'-',{
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var rows = $('#lingyiTable').datagrid('getSelected');
					if(rows){
						deleteData(rows.segmentId);
					}else{
						$.messager.alert('提示', '请选择要删除的系统参数信息！');
					}
				}
			}]
	});
	$("#dialog").dialog({
		modal:true,
		buttons: [
			{
				text:'保存',
				width:'80px',
				iconCls:'icon-save',
				handler:function(){
					if(updateFlag == 0){
                        createData();
					}else{
						updateData();
					}
				}
			}, {
				text:'关闭',
				width:'80px',
				handler:function(){
					$('#dialog').dialog('close')
				}
			}
		]
	});
});

function queryData(){
	loadDatagrid(formToJsonParms("queryFm", {}, ""));
}

function loadDatagrid(params){
	$("#lingyiTable").datagrid("load", params);
}

//=====CRUD=== start
var updateFlag = 0;
function updateData(){
	$("#paraName").prop("disabled", true);
	if($("#editFm").form('validate')){
		var params = formToJsonParms("editFm", "", "");
		$.ajax({
			"type": "PUT",
			"dataType": "json",
			"url": "../../params",
			"data": params,
			async: false,
			success: function(json){
				if (json.type == 'SUCCESS') {
					$.messager.alert(json.title, json.msg,'info');
					$('#dialog').dialog('close')
					loadDatagrid();
				}else{
					$.messager.alert(json.title, json.msg,'error');
					$('#dialog').dialog('close')
				}
			},
			error : function() {
				errorTimeoutPrompt();
				$('#dialog').dialog('close')
			}
		});
	}
}
function createData(){
	if($("#editFm").form('validate')){
		$("#paraName").prop("disabled", false);
		var params = formToJsonParms("editFm", "", "");
		$.ajax({
			"type": "POST",
			"dataType": "json",
			"url": "../../params",
			"data": params,
			async: false,
			success: function(json){
				if (json.type == 'SUCCESS') {
					$.messager.alert(json.title, json.msg,'info');
					$('#dialog').dialog('close')
					loadDatagrid();
				}else{
					$.messager.alert(json.title, json.msg,'error');
					$('#dialog').dialog('close')
				}
			},
			error : function() {
				errorTimeoutPrompt();
				$('#dialog').dialog('close')
			}
		});
	}
}
function deleteData() {
	$.messager.confirm('确认', '您确认想要删除记录吗？', function (r) {
		if (r) {
			var params = formToJsonParms("editFm", "", "");
			$.ajax({
				"type": "DELETE",
				"dataType": "json",
				"url": "../../params/"+ params.id,
				"data": {},
				async: false,
				success: function (json) {
					if (json.type == 'SUCCESS') {
						$.messager.alert(json.title, json.msg, 'info');
						$('#dialog').dialog('close')
						loadDatagrid();
					} else {
						$.messager.alert(json.title, json.msg, 'error');
						$('#dialog').dialog('close')
					}
				},
				error: function () {
					errorTimeoutPrompt();
					$('#dialog').dialog('close')
				}
			});
		}
	});
}
//=====CRUD=== end
