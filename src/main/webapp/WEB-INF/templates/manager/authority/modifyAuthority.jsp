<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var saveBtn = $('#authoritymodify-saveBtn');
        var form = $('#authorityAdd-fm');
        $(saveBtn).bind('click', function () {
        	
            var obj = toObject(form);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            console.log(obj);
            $.ajax({
                type: "POST",
                url: '${basePath}manager/authority/modifyAuthority.do',
                data: obj,
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('保存成功！');
                        parent.$('#authority-dlg').window('close');
                        $("#authority-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    });
    
</script>

<form id="authorityAdd-fm" method="post" novalidate style="margin-top: 20px;">
	<table class="am_table_1">
			<tr style="height: 15px;"></tr>
			  <input id="id" name="id" type="hidden" value="${authority.id}"/>
			<tr>
				<th>权限名称 ：</th>
				<td><input name="name" class="easyui-textbox"
					data-options="required:true" validType="auth"
					style="width: 180px; height: 26px;" value="${authority.name}"></td>
			</tr>
			<tr>
				<th>权限显示名称 ：</th>
				<td><input name="displayName" class="easyui-textbox"
					data-options="required:true" style="width: 180px; height: 26px;" value="${authority.displayName}"></td>
			</tr>
		</table>
</form>
<p align="center">
	<a id="authoritymodify-saveBtn" class="easyui-linkbutton"><font size="3">提&nbsp;&nbsp;交</font></a>
</p>