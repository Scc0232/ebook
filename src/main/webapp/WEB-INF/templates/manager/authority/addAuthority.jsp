<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var saveBtn = $('#authority-saveBtn');
        var form = $('#authorityAdd-fm');

        $(saveBtn).bind('click', function () {
            var obj = toObject(form);
           /*  obj.isValid = $("#isvalid").combobox('getValue'); */
            console.log(obj);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            $.ajax({
                type: "POST",
                url: '${basePath}manager/authority/addAuthority.do',
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
			<tr>
				<th>权限名称 ：</th>
				<td><input name="name" class="easyui-textbox"
					data-options="required:true" validType="auth"
					style="width: 180px; height: 26px;"></td>
			</tr>
			<tr>
				<th>权限显示名称 ：</th>
				<td><input name="displayName" class="easyui-textbox"
					data-options="required:true" style="width: 180px; height: 26px;"></td>
			</tr>
		</table>
</form>
<p align="center">
	<a id="authority-saveBtn" class="easyui-linkbutton"><font size="3">提&nbsp;&nbsp;交</font></a>
</p>