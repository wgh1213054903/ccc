<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>左侧菜单展示</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			}
		}
	};

	$(document).ready(function(){
		$.ajax({
			type : "post",
			url: "${pageContext.request.contextPath}/powerController/powerList.do",
			data : "",
			dataType : "json",
			async : true,
			success : function(result) {
				if (result.success) {
					$.fn.zTree.init($("#treeDemo"), setting, result.object);
				} else {
					alert(result.msg);
				}
			},
			error : function() {
				alert("系统错误,请联系管理员！");
			}
		})
	});
</script>
</head>
<body>
	<ul id="treeDemo" class="ztree"></ul>
</body>
</html>