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
	<div>
		<div class="login">
			<h2>用户登录</h2>
			<form  action="/login" method="post">
				<div class="loginFilds">
					<div class="loginFildsMask"></div>
					<div class="loginNameArea">					  
					  <input type="text" placeholder="请输入您的帐号,如：zhangsan" class="homeImage loginField loginUsr" name="userName" />
					  <span class="loginNameIcon"></span>
					</div>
					<div class="loginPassArea">
					  <input type="password" placeholder="请输入您的密码" class="homeImage loginField loginPw" name="passWord" />
					  <span class="loginPassIcon"></span>
					</div>
				</div>
				<div style="margin: 30px 0 20px 0;" class="clr">
					<input type="submit" class="loginPost fll" value="立即登录" id="loginButton" /> <a class="homeRegLink flr" href="javascript:void(0);">重置</a>
				</div>
			</form>
		</div>
	</div>
</body>

</html>
