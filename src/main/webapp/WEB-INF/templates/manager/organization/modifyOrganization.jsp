<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	$(function() {
		$("organization-fm").form('clear');
		$("#organizationModify-saveBtn").click(function() {
			$("#organization-fm").form('submit', {
				url : '${basePath}organization/modifyOrganization.do',
				onSubmit : function() {
					loading();
					if (!$('#organization-fm').form('validate')) {
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
						parent.$('#organization-add').window('close');
						layer.msg('修改成功！');
						$("#organization-grid").datagrid('reload'); // 刷新数据网格
					}
				}
			});
		});
	});
</script>

<form id="organization-fm" method="post" novalidate
	style="margin-top: 50px;">
	<div>
		<input id="id" name="id" type="hidden" value="${organization.id}" />
	</div>
	<div class="fitem" style="margin-top: 50px; margin-bottom: 50px"
		align="center">
		<label align="right">机构名称 ：</label> <input name="orgName"
			validtype="length[4,20]" missingMessage="不能为空"
			invalidMessage="有效长度4-20" class="easyui-textbox" required="true"
			style="width: 180px; height: 26px;" value="${organization.orgName}">
	</div>
	<div style="margin-bottom: 20px" align="center">
		<label style="margin-left: 20px">机构类型 ：</label> <input name="orgType"
			data-options="required:true,validType:'ORG_TYPE'"
			class="easyui-textbox" required="true"
			style="width: 180px; height: 26px;" value="${organization.orgType}">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="organizationModify-saveBtn" class="easyui-linkbutton"><font
		size="2">提&nbsp;&nbsp;交</font></a>
</p>