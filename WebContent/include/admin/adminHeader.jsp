<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>     <!-- 引入jstl -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %>   <!-- 引入jstl -->

<html>

<head>
	<%String path = request.getContextPath(); %>
	<script src="<%=path %>/js/jquery/2.0.0/jquery.min.js"></script>
	<link href="<%=path %>/css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<script src="<%=path %>/js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="<%=path %>/css/back/style.css" rel="stylesheet">
	
<!--  预先定义一些判断输入框的函数，方便后面使用 -->
	
<script>
function checkEmpty(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	return true;
}
function checkNumber(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(isNaN(value)){
		alert(name+ "必须是数字");
		$("#"+id)[0].focus();
		return false;
	}
	
	return true;
}
function checkInt(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(parseInt(value)!=value){
		alert(name+ "必须是整数");
		$("#"+id)[0].focus();
		return false;
	}
	
	return true;
}

/* 对于删除超链接，都需要进行确认操作 */
$(function(){
	$("a").click(function(){
		var deleteLink = $(this).attr("deleteLink");
		console.log(deleteLink);
		if("true"==deleteLink){
			var confirmDelete = confirm("确认要删除");
			if(confirmDelete)
				return true;
			return false;
			
		}
	});
})
</script>	
</head>
<body>

