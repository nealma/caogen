//###### role tree start
var zTree, rMenu;
//全局参数
var config = {
	initMenuUrl: "../systemManage/listMenu.action",
	initDepartmentRoleUrl: "../organization/listDepartmentRolesTree.action",
	addRoleUrl: "../systemManage/createRole.action",
	updateRoleUrl: "../systemManage/updateRole.action",
	delRoleUrl: "../systemManage/deleteRole.action"
}

$(document).ready(function () {
	$("#pt").panel();
	//构造菜单树
	getTree();
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	zTree = $.fn.zTree.getZTreeObj("treeDemo");
	rMenu = $("#rMenu");

	//新增
	$("#r_addNode").on("click", function () {
		addTreeNode();
	});
	//重命名
	$("#r_updateNode").on("click", function () {
		renameTreeNode();
	});
	//删除
	$("#r_deleteNode").on("click", function () {
		removeTreeNode();
	});

});

function getTree() {
	$.ajax({
		url: config.initDepartmentRoleUrl,
		dataType: "json",
		success: function (result) {
			zNodes = result;
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
	});
}

var setting = {
	view: {
		fontCss: getFontCss
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onRightClick: OnRightClick,
		beforeRename: beforeRename,
		onClick: itemClick
	}
};

var zNodes = [
];


function OnRightClick(event, treeId, treeNode) {
	if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
		zTree.cancelSelectedNode();
		showRMenu("root", event.clientX, event.clientY);
	} else if (treeNode && !treeNode.noR) {
		zTree.selectNode(treeNode);
		showRMenu(treeNode.level, event.clientX, event.clientY);
	}
}

function itemClick(event, treeId, treeNode){
	//TODO: 展开对应的菜单项
	listFunctionRole();

}


function showRMenu(type, x, y) {

	if (type == "root") {
		$("#rMenu").hide();
	} else if (type <= 1) {
		$("#rMenu").show();
		$("#r_deleteNode").hide();
		$("#r_addNode").show();
		$("#r_updateNode").show();
	} else {
		$("#rMenu").show();
		$("#r_addNode").hide();
		$("#r_deleteNode").show();
		$("#r_updateNode").show();
	}
	rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});

	$("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
	$("#rMenu").hide();
	$("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event) {
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
		rMenu.css({"visibility": "hidden"});
	}
}
var addCount = 1;
var addStatus = false;
function addTreeNode() {
	hideRMenu();
	treeNode = zTree.getSelectedNodes()[0];
	var newNode = {name: "增加" + (addCount++), pId: treeNode.pId};
	if (treeNode) {
		$.messager.confirm('确认', "确认增加吗？", function (r) {
			if (r) {
				newNode.checked = treeNode.checked;
				treeNode = zTree.addNodes(treeNode, newNode);
				zTree.editName(treeNode[0]);
				addStatus = true;
			}
		});
	}
}

function saveMenu(){
	if($("#editFm").form('validate')){
		var params = formToJsonParms("editFm", "", "");
		$.ajax({
			"type": "POST",
			"dataType": "json",
			"url": config.updateRoleUrl,
			"data": params,
			async: false,
			success: function(json){
				if (json.type == 'SUCCESS') {
					$.messager.alert(json.title, json.text,'info');
					var treeNode = zTree.getSelectedNodes()[0];
					treeNode.name = params.departmentRoleName;
					zTree.updateNode(treeNode);
				}else{
					$.messager.alert(json.title, json.text,'error');
				}
			},
			error : function() {
				errorTimeoutPrompt();
			}
		});
	}
}
function beforeRename(treeId, treeNode, newName){
	if(treeNode){
		var param = {
			"departmentRoleId": treeNode.id,
			"departmentRoleName": newName,
			"parentDepartmentRoleId": treeNode.pId
		};
		if(addStatus == true){
			$.ajax({
				"type": "POST",
				"dataType": "json",
				"url": config.addRoleUrl,
				"data": param,
				async: false,
				success: function (json) {
					if (json.type == 'SUCCESS') {
						treeNode.id = json.result[0].departmentRoleId;
						zTree.updateNode(treeNode)
						addStatus = false;
						$.messager.alert(json.title, json.text,'info');
					}
				},
				error: function () {
					addStatus = false;
					errorTimeoutPrompt();
				}
			});
		}else{
			$.ajax({
				"type": "POST",
				"dataType": "json",
				"url": config.updateRoleUrl,
				"data": param,
				async: false,
				success: function (json) {
					if (json.type == 'SUCCESS') {
						$.messager.alert(json.title, json.text,'info');
					}
				},
				error: function () {
					errorTimeoutPrompt();
				}
			});
		}
	}
}

function renameTreeNode() {
	hideRMenu();
	treeNode = zTree.getSelectedNodes()[0];
	if (treeNode) {
		$.messager.confirm('确认', "确认修改吗？", function (r) {
			if(r) {
				zTree.editName(treeNode);
			}
		});
	}
}
function removeTreeNode() {
	hideRMenu();
	var nodes = zTree.getSelectedNodes();
	if (nodes && nodes.length > 0) {
		if (nodes && nodes.length > 0) {
			if (nodes[0].child && nodes[0].child.length > 0) {//父部门的情况
				msg = "还有子节点，确认删除吗?";
			} else {//子部门的情况
				msg = "确认删除?";
			}
			$.messager.confirm('确认', msg, function (r) {
				if (r) {
					$.ajax({
						"type": "POST",
						"dataType": "json",
						"url": config.delRoleUrl,
						"data": {departmentRoleId: nodes[0].id},
						async: false,
						success: function (json) {
							if (json.type == 'SUCCESS') {
								zTree.removeNode(nodes[0]);
								$.messager.alert(json.title, json.text,'info');
							}
						},
						error: function () {
							errorTimeoutPrompt();
						}
					});
				}
			});
		}
	}
}

function checkTreeNode(checked) {
	var nodes = zTree.getSelectedNodes();
	if (nodes && nodes.length > 0) {
		zTree.checkNode(nodes[0], checked, true);
	}
	hideRMenu();
}
function resetTree() {
	hideRMenu();
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
}

//使用搜索数据 加高亮显示功能，需要2步
//1.在tree的setting 的view 设置里面加上 fontCss: getFontCss 设置
//2.在ztree容器上方，添加一个文本框，并添加onkeyup事件，该事件调用固定方法  changeColor(id,key,value）
//	id指ztree容器的id，一般为ul，key是指按ztree节点的数据的哪个属性为条件来过滤,value是指过滤条件，该过滤为模糊过滤
var nodeList;
function changeColor(id,key,value){
	treeId = id;
	updateNodes(false);
	if(value != ""){
		nodeList = zTree.getNodesByParamFuzzy(key, value);
		if(nodeList && nodeList.length>0){
			updateNodes(true);
		}
	}
}
function updateNodes(highlight) {
	if(nodeList && nodeList.length>0){
		for( var i=0; i<nodeList.length;  i++) {
			nodeList[i].highlight = highlight;
			zTree.updateNode(nodeList[i]);
			if(highlight){
				zTree.expandNode(nodeList[i], true, true, true);
				//zTree.selectNode(nodeList[i]);
			}
		}
	}
}
function getFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

//assign role
function assignFunctionRole() {
	var nodes = zTreeMenu.getCheckedNodes();
	if(!nodes || nodes.length == 0){
		return;
	}

	var roles = zTree.getSelectedNodes();
	if(!roles || roles.length == 0){
		return;
	}

	var functionIds = [];
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].isParent == false){
			functionIds.push(nodes[i].id);
		}
	}
	if(functionIds.length==0){
		return;
	}
	var departmentRoleId = roles[0].id;
	$.ajax({
		url: '../systemManage/assignFunctionRole.action',
		dataType: "json",
		data: {departmentRoleId: departmentRoleId, functionIds: functionIds.join(',')},
		success: function (json) {
			if (json.type == 'SUCCESS') {
				$.messager.alert(json.title, json.text,'info');
			}
		}
	});
}

function listFunctionRole() {
	zTreeMenu.checkAllNodes(false);
	zTreeMenu.expandAll(true);
	var roles = zTree.getSelectedNodes();
	if(!roles || roles.length == 0){
		return;
	}
	var departmentRoleId = roles[0].id;
	$.ajax({
		url: '../systemManage/listFunctionRole.action',
		dataType: "json",
		data: {departmentRoleId: departmentRoleId},
		success: function (json) {
			if(json && json.length > 0){
				for( var i =0; i < json.length; i ++ ) {
					var n1= zTreeMenu.getNodeByParam("id", json[i].functionId);
					zTreeMenu.checkNode(n1, true, true);
				}
			}
		}
	});
}
//###### role tree end


//###### menu tree start
var zTreeMenu, zNodesMenu;

$(document).ready(function () {
	$("#pt").panel();
	//构造菜单树
	getMenuTree();

	$.fn.zTree.init($("#menuTree"), settingMenu, zNodesMenu);
	zTreeMenu = $.fn.zTree.getZTreeObj("menuTree");
});

function getMenuTree() {
	$.ajax({
		url: config.initMenuUrl,
		dataType: "json",
		success: function (result) {
			zNodesMenu = result;
			$.fn.zTree.init($("#menuTree"), settingMenu, zNodesMenu);
		}
	});
}

var settingMenu = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: itemClickMenu
	}
};

function itemClickMenu(event, treeId, treeNode){
	//$("#functionId").val(treeNode.id);
	//$("#functionName").val(treeNode.name);
	//$("#parentFunctionId").val(treeNode.pId);
	//$("#functionUrl").val(treeNode.functionUrl||'');
	//$("#functionClass").val(treeNode.functionClass||'1');

}

//###### menu tree end
