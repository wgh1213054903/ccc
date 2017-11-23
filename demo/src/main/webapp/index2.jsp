<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<jsp:include page="inc.jsp"></jsp:include>
<script type="text/javascript">
  $(function(){
	  powerList();
  })

  function powerList(){
	 $.ajax({
		  url:"<%=request.getContextPath()%>/powerController/powerList.do",
		  dataType:"json",
		  data:{"name":$("#name").val()},
		  type:"post",
		  async:true,
		  success:function(result){
			   console.info(result);
			  var str = " <tr><td>序号</td><td>父节点id</td><td>名称</td><td>图标</td><td>类型</td><td>跳转方式</td><td>路径</td></tr>";
			  if(result.success){
				  for (var i = 0; i < result.object.length; i++) {
					     if(result.object[i].id!=null && result.object[i].id!=""){
						  str+="<tr><td>"+ result.object[i].id+"</td>";
					  }else{
						  str+="<tr><td></td>"; 
					  }
					  if(result.object[i].pid!=null && result.object[i].pid!=""){
						  str+="<td>"+ result.object[i].pid+"</td>";
					  }else{
						  str+="<td></td>";
					  }
					  if(result.object[i].name!=null && result.object[i].name!=""){
						  str+="<td>"+ result.object[i].name+"</td>";
					  }else{
						  str+="<td></td>";
					  }
					  if(result.object[i].icon!=null && result.object[i].icon!=""){
						  str+="<td>"+ result.object[i].icon+"</td>";
					  }else{
						  str+="<td></td>";
					  }
					  if(result.object[i].type!=null && result.object[i].type!=""){
						  str+="<td>"+ result.object[i].type+"</td>";
					  }else{
						  str+="<td></td>";
					  }
					  if(result.object[i].target!=null && result.object[i].target!=""){
						  str+="<td>"+ result.object[i].target+"</td>";
					  }else{
						  str+="<td></td>";
					  }
					  if(result.object[i].url!=null && result.object[i].url!=""){
						  str+="<td>"+ result.object[i].url+"</td>";
					  } else{
						  str+="<td></td>";
					  }
					
					str+="</tr>";
					
					
				 } 
				 // alert(str);
				  $("#index_table").html(str);
			  }else{
				  alert(result.msg);
				  
			  }
		  },
		  error:function(){
			  alert("查询报错");
		  }
		})
  }
  
  function exportExcel(){
	  var data=$("#index_form1").serialize();
	  alert(data);
	  window.location.href="<%=request.getContextPath()%>/powerController/exportExcels.do?"+data;
  }
  
  
  function importExcel(){
	  alert("开始新增.........");
	
 	var login_form=dialog({
		  
		  title:"新增",
		  content:$("#index_dialog").html($("#index_dialog").html()),
		 
		  
	  })
	  login_form.showModal();
 	 
  }  
</script>
<body>
<div>
  
</div>
<div>
<form id="index_form1">
名称：<input type="text" name  ="name" id="name">
  <input type="button" onclick="powerList()" value="查询">
    <input type="checkbox" name="checks"  value="序号">序号
    <input type="checkbox" name="checks"  value="父节点id">父节点id
    <input type="checkbox" name="checks"  value="名称">名称
    <input type="checkbox" name="checks"  value="图标">图标
    <input type="checkbox" name="checks"  value="类型">类型
    <input type="checkbox" name="checks"  value="跳转方式">跳转方式
    <input type="checkbox" name="checks"  value="路径">路径
    <input type="button" value="导出excel" onclick="exportExcel()">
      <input type="button" value="导入excel" onclick="importExcel()">
 </form>   
 
</div>
<div>
    <table id="index_table" border="1"></table>
</div>
<div id="index_dialog" style="display: none">
    <form id="index_form" action="<%=request.getContextPath() %>/powerController/importExcel.do" 
    method="post"  enctype="multipart/form-data">
       <input type="file" name="file">
       <input type="submit" value="提交">
    </form>
</div>
</body>
</html>