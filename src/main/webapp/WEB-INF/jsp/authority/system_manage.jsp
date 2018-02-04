<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统管理</title>
</head>
<body>
	<div id="system_grid_tb" style="height: 25px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add_system_btn">新增</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="eidt_system_btn">修改</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="disable_system_btn">禁用</a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="enable_system_btn">启用</a>
	</div>
	<table id="system_grid"></table>
	
	<div id="save_system_dialog" class="easyui-dialog"  data-options="closed:true,modal:true,onClose:closeSaveSystemDialog"  style="width: 400px; overflow: hidden;">
		<form id="save_system_form" method="post">
		<input type="hidden" name="systemId">
		<table class="form_tbl_cls">
			<tr>
				<td style="text-align: right; width: 32%;">系统名称</td><td><input class="easyui-textbox" name="systemName" data-options="required:true"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">系统地址</td><td><input class="easyui-textbox" name="systemUrl" data-options="required:true"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">系统图标</td><td><input class="easyui-textbox" name="systemIcon" data-options=""/></td>
			</tr>
		</table>
		<div style="text-align: center; padding: 5px 0;">
    		<a onclick="saveSystem();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
    		<a onclick="closeDialog('save_system_dialog')" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="margin-left: 15px;" onclick="closeDialog('save_system_dialog')">取消</a>
    	</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx }/resources/js/authority/system_manage.js"></script>
</body>
</html>
