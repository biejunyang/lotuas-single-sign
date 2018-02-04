//打开页面
function openPage(title, url){
	var dialogId=sy.UUID();
	$("body").append('<div id="'+dialogId+'"></div>');
	$('#'+dialogId).dialog({
	    title: title,
	    width: 950,
	    height: 650,
	    closed: false,
	    cache: false,
	    href: url,
	    modal: false,
	    onClose:function(){
	    	$(this).dialog("destroy");
	    }
	});
}




/**
 * 为全部的ajax
 */
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		var sessionstatus = XMLHttpRequest.getResponseHeader("session_timeout_status");		
		if (sessionstatus == "timeout") {
			
		}else if(sessionstatus == "noClientInfo"){
			
		}		
	},error:function(XMLHttpRequest, textStatus){
		var sessionstatus = XMLHttpRequest.getResponseHeader("session_timeout_status");
		if (sessionstatus != "timeout") {
			$.messager.alert('提示', "请求超时，网络异常，请与管理员联系!", 'info');
		}
	}
});




/**
 * 通过id关闭dialog
 * @param id
 * @returns
 */
function closeDialog(id){
	$("#"+id).dialog("close");
}
