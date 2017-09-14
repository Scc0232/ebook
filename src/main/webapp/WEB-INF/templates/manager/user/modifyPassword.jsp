<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var modifyBtn = $('#userModify-modifyBtn');
        var form = $('#passwordModify-fm');

        $(modifyBtn).bind('click', function () {
            var obj = toObject(form);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            $.ajax({
                type: "POST",
                url: '${basePath}manager/user/modifyPassword.do',
                data: obj,
                dataType: "json",
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('修改成功！');
                        parent.$('#user-add').window('close');
                        $("#user-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    });
</script>

<form id="passwordModify-fm" method="post" novalidate style="margin-top: 25px;">
<table class="am_table_1">
    <tr class="fitem" style="margin-top: 50px;">
        <input id="id" name="id" type="hidden" value="${userMap.id}"/>
        <th>重置密码:</th>
        <td style="margin-top: 50px;">
            <input type="password" name="password" data-options="required:true,validType:'safepass'" class="easyui-textbox" style="width: 180px; height: 26px;"></input>
        </td>
    </tr>
    </table>
</form>

<p align="center" style="margin-top: 20px;">
    <a id="userModify-modifyBtn" class="easyui-linkbutton"><font size="2">修&nbsp;&nbsp;改</font></a>
</p>