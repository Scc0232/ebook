<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function () {
    var saveBtn = $('#areaAdd-saveBtn');
    var form = $('#area-fm');

    $(saveBtn).bind('click', function () {
        var obj = toObject(form);
        console.log(obj);
        if (!$(form).form('validate')) {
            closeLoading();
            return false;
        }
        $.ajax({
            type: "POST",
            url: '${basePath}manager/area/addArea.do',
            data: obj,
            async: "false",
            success: function (data) {
                if (data.flag === "success") {
                    layer.msg('保存成功！');
                    parent.$('#area-dlg').window('close');
                    $("#area-grid").datagrid('reload'); // 刷新数据网格
                }
            }
        });
    });
});
</script>
	<form id="area-fm" method="post"  novalidate >
	   <table class="am_table_1" text-align: center;">
	       <tr style="height: 15px;"></tr>
	       <tr>
	           <th>区域名称 ：</th>
	           <td><input name="name" maxlength='20' data-options="required:true,validType:'CHS'" class="easyui-textbox" style="width: 180px; height: 26px;" value="${area.areaName}"></td>
	       </tr>
	       <tr>
               <th>区域编码 ：</th>
               <td><input name="code" maxlength='6' class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${area.code}"></td>
           </tr>
           <tr>
               <th>城市编码：</th>
               <td><input name="cityCode" maxlength='6' class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value = "${area.cityCode}"></td>
           </tr>
           <tr>
               <th>简称 ：</th>
               <td><input name="abbr" maxlength='10' class="easyui-textbox"  style="width: 180px; height: 26px;" value = "${area.abbr}"></td>
           </tr>
	   </table>
	</form>

<p align="center">
	<a id="areaAdd-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>