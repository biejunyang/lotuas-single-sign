<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@ include file="common/head.jsp" %>

</head>
<body>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="openPage('系统管理', '${ctx}/system/managePage');">系统管理</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="openPage('用户管理', '${ctx}/system/managePage');">用户管理</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="openPage('角色管理', '${ctx}/system/managePage');">角色管理</a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="openPage('权限管理', '${ctx}/system/managePage');">权限管理</a>
	
	<script type="text/javascript" src="${ctx }/resources/js/index.js"></script>
	
</body>

</html>
