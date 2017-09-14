<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
<meta charset="UTF-8">
<style>
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}

.fitem input {
	width: 160px;
}
</style>
<%@ include file="../includes/easyui.jsp"%>
<link href="${basePath }css/common.css" rel="stylesheet" />
<link href="${basePath }css/jquery.Jcrop.css" rel="stylesheet" />
<script src="${basePath }js/easyui.tree.extension.js"></script>
<script src="${basePath }js/manager.index.js"></script>
<script src="${basePath }js/location.js"></script>
<script src="${basePath }js/area.js"></script>
<script src="${basePath }js/common/script.js"></script>
<script src="${basePath }js/jquery.imgareaselect.min.js"></script>
<script src="${basePath }js/jquery.imgareaselect.pack.js"></script>
<script src="${basePath }js/jquery.Jcrop.js"></script>
<script src="${basePath }js/jQueryRotate.js"></script>
<script src="${basePath }js/jquery.form.min.js"></script>
<script src="${basePath }js/easyui_validate.js"></script>
<script type="text/javascript">
    window.ctx="${basePath}"
</script>
<title>e书管理系统</title>
<style type="text/css">
	#top_img{ border:1px solid #000;overflow:hidden} 
	#top_img {width:100%;} 
</style>
</head>
<body class="easyui-layout">
	<div id="top_img" data-options="region:'north',split:true" style="height: 80px; padding:33px 1% 35px 1%;font-size: 14px; background: url('${basePath }img/top.png') top center no-repeat;" >
		<h1 style="float: left;font-size:20px; font-family:'微软雅黑';"></h1>
		<p align="right" style="margin-right: 20px; float: right; font-family:'微软雅黑';">
			<b>欢迎: ${username } &nbsp;&nbsp;<a href="${basePath }j_spring_security_logout">退出登录</a></b>
		</p>
	</div>
	<div data-options="region:'west',title:'菜单',split:true" style="width: 15%;">
		<ul id="menu"></ul>
	</div>
	<div data-options="region:'center'" style="background: #eee;">
		<div id="workspace" class="easyui-tabs" data-options="fit:true,border:true" style="width: 100%; height: 100%;">
		</div>
	</div>
	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div name="closeCurrent">关闭</div>
		<div name="closeAll">关闭全部</div>
		<div name="closeOther">关闭其他</div>
		<div class="menu-sep"></div>
		<div name="closeRight">关闭右侧</div>
		<div name="closeLeft">关闭左侧</div>
	</div>

</body>
</html>
