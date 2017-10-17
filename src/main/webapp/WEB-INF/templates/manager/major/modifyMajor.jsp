<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	$(function() {
		$("major-fm").form('clear');
		$("#majorModify-saveBtn").click(function() {
			$("#major-fm").form('submit', {
				url : '${basePath}manager/major/modifyMajor.do',
				onSubmit : function() {
					loading();
					if (!$('#major-fm').form('validate')) {
						closeLoading();
						return false;
					}
					return true;
				},
				success : function(result) {
					closeLoading();
					var result = eval('(' + result + ')');
					console.log(result);
					if (result.flag === 'fail') {
						$.messager.show({
							title : 'Error',
							msg : result.msg
						});
					} else {
						parent.$('#major-add').window('close');
						layer.msg('修改成功！');
						$("#major-grid").datagrid('reload'); // 刷新数据网格
					}
				}
			});
		});
	});
</script>

<form id="major-fm" method="post" enctype="multipart/form-data"
	action="${basePath}manager/major/modifyMajor.do">
	<div>
		<input id="id" name="id" type="hidden" value="${major.id}" />
	</div>
	<div class="fitem" style="margin-top: 30px;" align="center">
		<label align="center">学校名称 :</label> <input name="collegeName"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${major.collegeName}">
	</div>
	<div class="fitem" style="margin-top: 20px;" align="center">
		<label align="center">学院名称:</label> <input name="academyName"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${major.academyName}">
	</div>
	<div class="fitem" style="margin-top: 20px;" align="center">
		<label align="center">专业名称 :</label> <input name="professionName"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${major.professionName}">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="majorModify-saveBtn" class="easyui-linkbutton"><font
		size="2">提&nbsp;&nbsp;交</font></a>
</p>