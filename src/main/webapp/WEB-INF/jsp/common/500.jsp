<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String content=null;
	if(request.getContextPath().endsWith("/")){
		content=request.getContextPath();
	}else{
		content=request.getContextPath()+"/";
	}
	request.setAttribute("ctx", content);
%>
<!doctype html public "-//w3c//dtd html 4.01 transitional//en" "http://www.w3.org/tr/html4/loose.dtd">
<html>
<head>
<title>错误500,后台处理出现异常！</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<style type=text/css>
body {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}
table {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}
td {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}
body {
	scrollbar-highlight-color: buttonface;
	scrollbar-shadow-color: buttonface;
	scrollbar-3dlight-color: buttonhighlight;
	scrollbar-track-color: #eeeeee;
	background-color: #ffffff
}
a {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}
a:hover {
	font-size: 9pt;
	color: #0188d2;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: underline
}
h1 {
	font-size: 9pt;
	font-family: "tahoma", "宋体"
}
</style>
<body topmargin=20>
	<table cellspacing=0 width=600 align=center border=0>
		<tbody>
			<tr>
				<td valign=top align=center>
				   <img height=100 src="${ctx}commons/errors/500/500.jpg" width=100 border=0>
				</td>
				<td>
					<h1>抱歉</h1> http 错误 500：后台处理出现异常。
					<hr noshade size=0>
					<p>☉ 请尝试以下操作：</p>
					<ul>
						<li>确保浏览器的地址栏中显示的网站地址的拼写和格式正确无误。
						<li>如果通过单击链接而到达了该网页，请与网站管理员联系，通知他们该链接的格式不正确。
						<li>单击<a href="javascript:history.back(1)">
						   <font color=#ff0000>后退</font></a>按钮尝试另一个链接。
						</li>
					</ul>
					<hr noshade size=0>
					<p>
						☉ 如果您对本系统有任何疑问、意见、建议、咨询，请联系管理员。</a> <br>
						&nbsp;&nbsp;&nbsp;<br>
					</p>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>