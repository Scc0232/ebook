<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var saveBtn = $('#role-saveBtn');
        var form = $('#roleAdd-fm');

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
                url: '${basePath}manager/role/addRole.do',
                data: obj,
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('保存成功！');
                        parent.$('#role-dlg').window('close');
                        $("#role-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    });
    
</script>

<form id="roleAdd-fm" method="post" novalidate style="margin-top: 20px;">
	<table class="am_table_1">
	       <tr style="height: 15px;"></tr>
	        <tr>
               <th>角色名称 ：</th>
               <td><input name="name" class="easyui-textbox" data-options="required:true" style="width: 180px; height: 26px;"></td>
           </tr>
			<!-- <tr>
				<th>是否有效：</th>
				<td><select id="isvalid" class="easyui-combobox" name="isValid" data-options="required:true" editable="false"
					style="width: 180px; height: 26px;">
						<option value="1">是</option>
						<option value="0">否</option>
				</select></td>
			</tr> -->
	   </table>

</form>
<p align="center">
	<a id="role-saveBtn" class="easyui-linkbutton"><font size="3">提&nbsp;&nbsp;交</font></a>
</p>