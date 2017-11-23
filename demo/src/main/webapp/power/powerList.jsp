2<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限展示页面</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
var setting = {
		async: {
			enable: true,
			url:"${pageContext.request.contextPath}/powerController/getPowerRootNode.do",
			autoParam:["id"]
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			},
			key: {
				url: "xUrl"
			}
		},
		callback: {
			onClick: zTreeOnClick
		}
	};
//-------------------------------------回调点击方法-------------------------------
	//回调点击事件
	function zTreeOnClick(event, treeId, treeNode) {
// 	    alert(treeNode.tId + ", " + treeNode.name);
// 	    console.info(treeNode.getParentNode());
		var parentNode = treeNode.getParentNode();
		//给父节点id和父节点名称赋值
		if (null != parentNode) {
			$("#parentId").text(parentNode.id);
			$("#parentName").text(parentNode.name);
		} else {
			$("#parentId").text("");
			$("#parentName").text("");
		}
		//给当前节点信息赋值
		$("#name").val(treeNode.name);
		$("#icon").val(treeNode.icon);
		$("#url").val(treeNode.url);
		$("#target").val(treeNode.target);
		$("#type").val(treeNode.type);
		$("#id").val(treeNode.id);
	};

	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting);
		$("#addSaveBtn").hide();
	});

	
	
	//-------------------------------------添加节点方法-------------------------------
	
	
	
	//添加菜单
	function addPower() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		//获取当前选中节点数据的集合，因为只选中了一个节点，所以根据数组下标取值，只用取第一个，即我们修改的节点
		var selectedNode = treeObj.getSelectedNodes()[0];
		console.info(selectedNode);
		if (undefined == selectedNode) {
			alert("请选择一个菜单节点");
			return false;
		}
		$("#pid").val(selectedNode.id);
		$("#parentId").text(selectedNode.id);
		$("#parentName").text(selectedNode.name);
		$("#id").val(""); //问题一：id未清空
		$("#name").val("");
		$("#target").val("");
		$("#url").val("");
		$("#updateSaveBtn").hide();
		$("#addBtn").hide();
		$("#deleteBtn").hide();
		//显示保存按钮
		$("#addSaveBtn").show();
	}
	
	//保存数据信息 ，问题二：保存成功之后，未获得数据库中自动生成的id值
	function savePowerNode() {
		var data = $("#power_powerList_form").serialize();
		alert(data);
		$.ajax({
		   type: "POST",
		   url: "${pageContext.request.contextPath}/powerController/addPowerNode.do",
		   data: data,
		   dataType: "json",
		   success: function(data){
		     if (data.success) {
		    	 alert(data.msg);
		    	 //获得整个树信息
		    	 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		    	 var newNode = {"pid":data.object.pid, "id":data.object.id, "name":data.object.name, "target":data.object.target, "url":data.object.url, "type": data.object.type};
		    	 var currentSelectedNode = treeObj.getNodeByParam("id", data.object.pid, null);
		    	 console.info(currentSelectedNode.name + "--------------");
		    	 //添加节点信息
		    	 treeObj.addNodes(currentSelectedNode, newNode);
		    	 $("#updateSaveBtn").show();
		 		 $("#addBtn").show();
		 		 $("#deleteBtn").show();
		 		 //显示保存按钮
		 		 $("#addSaveBtn").hide();
		     } else {
		    	 alert(data.msg);
		     }
		   },
		   error: function(msg) {
			   alert( "error Saved: " + msg );
		   }
		});
	}
	
	
	//-------------------------------------删除方法-------------------------------
	
	//删除菜单
	function deletePower() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		//获取当前选中节点数据的集合，因为只选中了一个节点，所以根据数组下标取值，只用取第一个，即我们修改的节点
		var selectedNode = treeObj.getSelectedNodes()[0];
		if (undefined == selectedNode) {
			alert("请选择一个需要删除的节点！");
			return false;
		}
		console.info(selectedNode);
		var id = selectedNode.id;
		if (confirm("确认要删除吗?")) {
			$.ajax({
			   type: "POST",
			   url: "${pageContext.request.contextPath}/powerController/deletePower.do",
			   data:{"id" : id},
			   dataType: "json",
			   success: function(data){
			     if (data.success) {
			    	 alert(data.msg);
			    	 $("#parentId").text("");
						$("#parentName").text("");
						$("#name").val("");
						$("#icon").val("");
						$("#url").val("");
						$("#target").val("_blank");
						$("#type").val("0");
						$("#id").val("");
			    	//删除选中的节点
			    	 treeObj.removeNode(selectedNode);
			     } else {
			    	 alert(data.msg);
			     }
			   },
			   error: function(msg) {
				   alert( "error Saved: " + msg );
			   }
			});
		} 
	}
	
	//-------------------------------------修改后保存方法-------------------------------
	//修改后保存
	function updateSave() {
		//更新树形节点
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getSelectedNodes();
		if (nodes.length > 0) {
			var data = $("#power_powerList_form").serialize();
			$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/powerController/updatePower.do",
				data : data,
				dataType : "json",
				async : true,
				success : function(result) {
					if (result.success) {
						alert(result.msg);
						$("#parentId").text("");
						$("#parentName").text("");
						$("#name").val("");
						$("#icon").val("");
						$("#url").val("");
						$("#target").val("_blank");
						$("#type").val("0");
						$("#id").val("");
						nodes[0].name = result.object.name;
						nodes[0].id = result.object.id;
						nodes[0].icon = result.object.icon;
						nodes[0].url = result.object.url;
						nodes[0].target = result.object.target;
						nodes[0].type = result.object.type;
						nodes[0].pid = result.object.pid;
						treeObj.updateNode(nodes[0]);
					} else {
						alert(result.msg);
					}
				},
				error : function() {
					alert("系统错误，请联系管理员！");
				}
			}) 
		} else {
			alert("请选择需要修改的一个节点信息");
		}
	}
</script>
</head>
<body>
<div style="width: 270px; height: 600px; border: 1px solid red; margin-left: 10px; margin-top: 10px; padding: 10px;float: left;">
	<ul id="treeDemo" class="ztree"></ul>
</div>
<div style="width: 600px; height: 600px; border: 1px solid red; margin-left: 10px; margin-top: 10px; padding: 10px; float: left;">
	<div style="">
		<input type="button" id="updateSaveBtn" value="修改后保存" onclick="updateSave();" />
		<input type="button" id="addBtn" value="新增菜单" onclick="addPower()" />
		<input type="button" id="deleteBtn" value="删除菜单" onclick="deletePower()" />
		<input type="button" id="addSaveBtn" value="新增保存" onclick="savePowerNode()" />
	</div>
	<div style="margin-top: 20px; margin-left: 20px;">
		<form id="power_powerList_form">
		父节点ID：<span id="parentId"></span><br><br>
		父节点名称：<span id="parentName"></span><br><br>
		节点名称：<input type="text" name="name" id="name" width="182px;"/><br><br>
		<input type="hidden" id="pid" name="pid">
		节点图标：<input type="text" name="icon" id="icon" width="182px;"><br><br>
		URL：<input type="text" name="url" id="url" width="182px;"><br><br>
		TARGET：<select id="target" name="target" style="width: 187px;">
					<option value="_blank">_blank</option>
					<option value="_self">_self</option>
					<option value="_top">_top</option>
					<option value="_parent">_parent</option>
					<option value="mainFrame">mainFrame</option>
			   </select><br><br>
	         节点类型：<select id="type" name="type" style="width: 187px;">
					<option value="0">菜单</option>
					<option value="1">按钮</option>
			   </select><br><br>
		</form>
	</div>
</div>
</body>
</html>