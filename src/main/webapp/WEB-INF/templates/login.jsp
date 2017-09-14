<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>e书登录</title>
<script type="text/javascript" src="${basePath}library/jquery-easyui-1.4.3/jquery.min.js"></script>
<link rel="stylesheet" href="${basePath }css/login.css">
</head>
<body>
	<div class="wrapper">
		<div class="container">
			<h1>欢&nbsp;&nbsp;迎</h1>
			<span id="msg" style="display: none; color: red;">用户名或密码错误</span>
			<form class="form" method="post" action="${basePath}j_spring_security_check">
				<input type="text" name="username" placeholder="用户名"> <input name="password" type="password" placeholder="密码">
				<button type="submit" id="login-button">登录</button>
			</form>
		</div>
		<ul class="bg-bubbles">
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
			<li></li>
		</ul>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${nomatch}">
			// 显示用户名或密码错误
			$("#msg").show();
			</c:if>
		});

		$("#login-button").click(function(event) {
			$("#msg").hide();
			event.preventDefault();

			$('form').fadeOut(500);
			$('.wrapper').addClass('form-success');
			setTimeout(function() {
				$(".form").submit();
			}, 1000);
		});
	</script>
</body>
</html>

