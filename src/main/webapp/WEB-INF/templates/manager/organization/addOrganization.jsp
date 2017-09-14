<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	$(function() {
		var saveBtn = $('#organization-saveBtn');
		var form = $('#organizationAdd-fm');

		$(saveBtn).bind('click', function() {

			var obj = toObject(form);
			if (!$(form).form('validate')) {
				closeLoading();
				return false;
			}
			console.log(obj);
			$.ajax({
				type : "POST",
				url : '${basePath}organization/addOrganization.do',
				data : obj,
				dataType : "json",
				async : "false",
				success : function(data) {
					if (data.flag === "success") {
						layer.msg('保存成功！');
						parent.$('#organization-add').window('close');
						$("#organization-grid").datagrid('reload'); // 刷新数据网格
					}
				}
			});
		});
	});
</script>
<form id="organizationAdd-fm" method="post" novalidate
	style="margin-top: 50px;">
	<div class="fitem" style="margin-top: 50px;">
		<div style="margin-bottom: 50px" align="center">
			<label align="right">机构名称 ：</label> <input name="orgName"
				validtype="length[4,20]" missingMessage="不能为空"
				invalidMessage="有效长度4-20" class="easyui-textbox" required="true"
				style="width: 180px; height: 26px;">

		</div>
		<div style="margin-bottom: 20px" align="center">
			<label align="right">机构类型 ：</label> <input name="orgType"
				data-options="required:true,validType:'ORG_TYPE'"
				class="easyui-textbox" required="true"
				style="width: 180px; height: 26px;">
		</div>
	</div>

</form>
<p align="center" style="margin-top: 50px;">
	<a id="organization-saveBtn" class="easyui-linkbutton"><font
		size="2">提&nbsp;&nbsp;交</font></a>
</p>