<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	$(function() {
		$('#orgNamecombobox').combobox({
			url:'${basePath}styleSetting/findOrgIdList.do',
			valueField:'id',
			textField:'orgName',
			panelHeight: "auto",
            editable: false,
				
            onLoadSuccess: function (data) {
            	$('#orgNamecombobox').combobox('setValue','${styleSetting.orgId}');
            }
		});
		$("styleSetting-fm").form('clear');
		$("#styleSettingModify-saveBtn").click(function() {
			$("#styleSetting-fm").form('submit', {
				url : '${basePath}styleSetting/modifyStyleSetting.do',
				onSubmit : function() {
					loading();
					if (!$('#styleSetting-fm').form('validate')) {
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
						parent.$('#styleSetting-add').window('close');
						layer.msg('修改成功！');
						$("#styleSetting-grid").datagrid('reload'); // 刷新数据网格
					}
				}
			});
		});
	});
</script>

<form id="styleSetting-fm" method="post" novalidate
	style="margin-top: 50px;">
	<div>
		<input id="id" name="id" type="hidden" value="${styleSetting.id}" />
	</div>
	<div class="fitem" style="margin-top: 50px; margin-bottom: 50px"
		align="center">
		<label align="right" style="width:100px">机构ID ：</label> <input id="orgNamecombobox" name="orgId"
			 
			style="width: 180px; height: 26px;" >
	</div>
	<div style="margin-bottom: 20px" align="center">
		<label style="margin-left: 20px">CSS资源地址 ：</label> <input name="cssResourceUrl"
			data-options="required:true,validType:'CSS_RESOURCE_URL'"
			class="easyui-textbox" required="true"
			style="width: 180px; height: 26px;" value="${styleSetting.cssResourceUrl}">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="styleSettingModify-saveBtn" class="easyui-linkbutton"><font
		size="2">提&nbsp;&nbsp;交</font></a>
</p>