$(function(){
	//系统显示列表
	$('#system_grid').datagrid({
	    url:JS_CONTEXT+'/system/listSystems?pagable=true',
	    fit:true,
	    border:false,
	    toolbar: "#system_grid_tb",
	    singleSelect : true,
	    fitColumns : false,
		idField:'systemId',
		nowrap:false,
		pagination:true, pageSize:20,pageList:[20, 30, 40, 50],
	    columns:[[
            {field:'sel',title:'选择',width:50,checkbox:true},
            {field:'systemName',title:'系统名称',width:100,align:'center'},
	        {field:'systemUrl',title:'系统地址',width:100,align:'center'},
	        {field:'systemIcon',title:'系统图标',width:100,align:'center'}
	    ]]
	});
	
	//添加按钮
	$("#add_system_btn").click(function(){
		$("#save_system_dialog").dialog({title:'添加系统信息'}).dialog("open");
	});
});



/**
 * 保存系统信息
 * @returns
 */
function saveSystem(){
	$("#save_system_form").form("submit", {
		url:JS_CONTEXT+"/system/saveSystem",
		onSubmit:function(param){
			var flag=$(this).form("validate");
			if(flag){
				$.messager.progress(50000);				
			}
			return flag;
		},
		success:function(data){
			$.messager.progress('close');
			data=eval("("+data+")");
			if(data.success){
				$("#save_system_dialog").dialog("close");
				$('#system_grid').datagrid("reload");
				$('#system_grid').datagrid("clearChecked");
			}else{
				$.messager.alert("提示", data.message, "error");
			}
		}
	});
}

function closeSaveSystemDialog(){
	$("#save_system_form").form("clear");
}