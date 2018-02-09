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
 * 为全部的ajax设置默认参数
 */
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	type: "POST",
	complete : function(XMLHttpRequest, textStatus) {
		var exceptionType = XMLHttpRequest.getResponseHeader("exceptionType");		
		if (exceptionType == "SessionTimeOutException") {
			$.messager.alert('提示', "登录超时间，3秒后跳转到登录页面!!!", 'error',function(){
				window.location=JS_CONTEXT+"/loginPage";
			});
			setInterval(() => {
				window.location=JS_CONTEXT+"/loginPage";
			}, 5000);
		}else if(exceptionType == "NoAccessException") {
			$.messager.alert('提示', "没有权限访问该请求", 'error');
		}else if(exceptionType == "Exception") {
			$.messager.alert('提示', "请求超时，网络异常，请与管理员联系!", 'error');
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
