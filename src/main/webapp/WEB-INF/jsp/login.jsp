<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@ include file="common/head.jsp" %>
<script type="text/javascript">
$(function(){
	$("#loginButton").click(function(){
		var userNameVal = $.trim($(".loginUsr").val());
		var passVal = $.trim($(".loginPw").val());
		if(userNameVal == ""){
			$(".loginTip").css("display","block").html("系统提示：请输入用户名!");
		  }else if(passVal == ""){
			$(".loginTip").css("display","block").html("系统提示：请输入密码!");
		  }else if(userNameVal != "" && passVal != ""){
			$.ajax({
				async : false,
				cache:false,
				type: 'post',
				dataType : "text",
				data : {
					"username" : userNameVal,
					"password" : passVal
				},
				url: JS_CONTEXT+"/loginAuth",
				success:function(data){					
					var data2=eval( "(" + data + ")" );				
					if(data2.success){		
						window.location=JS_CONTEXT+"/index";
			       }else{				    	 
			    	   $(".loginTip").css("display","block").html(data2.message);
			       }			
					
				}
			});			  
		 }
	});
	
	$(document).keyup(function(event){
	 	if(event.keyCode ==13){
	 		$("#loginButton").trigger("click");
	  	}
	});
});
</script>
</head>
<body>
	<div>
		<div class="login">
			<h2>用户登录</h2>
			<div class="loginTip" style="display:none;"></div>
			<form  action="/login" method="post">
				<div class="loginFilds">
					<div class="loginFildsMask"></div>
					<div class="loginNameArea">					  
					  username:<input type="text" placeholder="请输入您的帐号,如：zhangsan" class="homeImage loginField loginUsr" name="username" />
					  <span class="loginNameIcon"></span>
					</div>
					<div class="loginPassArea">
					 passowrd: <input type="password" placeholder="请输入您的密码" class="homeImage loginField loginPw" name="password" />
					  <span class="loginPassIcon"></span>
					</div>
				</div>
				<div style="margin: 30px 0 20px 0;" class="clr">
					<input type="button" class="loginPost fll" value="立即登录" id="loginButton" /> <a class="homeRegLink flr" href="javascript:void(0);">重置</a>
				</div>
			</form>
		</div>
	</div>
</body>

</html>
