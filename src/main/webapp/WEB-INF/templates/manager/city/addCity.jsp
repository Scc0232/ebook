<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function () {
    var saveBtn = $('#cityAdd-saveBtn');
    var form = $('#city-fm');

    $(saveBtn).bind('click', function () {
        var obj = toObject(form);
        console.log(obj);
        if (!$(form).form('validate')) {
            closeLoading();
            return false;
        }
        $.ajax({
            type: "POST",
            url: '${basePath}manager/city/addCity.do',
            data: obj,
            async: "false",
            success: function (data) {
                if (data.flag === "success") {
                    layer.msg('保存成功！');
                    parent.$('#city-dlg').window('close');
                    $("#city-grid").datagrid('reload'); // 刷新数据网格
                }
            }
        });
    });
});
</script>
	<form id="city-fm" method="post" novalidate>
	   <table class="am_table_1">
	       <tr style="height: 15px;"></tr>
	       <tr>
	           <th>名称 ：</th>
	           <td><input name="name"  maxlength='20' data-options="required:true,validType:'CHS'" class="easyui-textbox" style="width: 180px; height: 26px;" value="${city.name}"></td>
	       </tr>
	       <tr>
               <th>城市编码 ：</th>
               <td><input name="code"  maxlength='6' class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${city.code}"></td>
           </tr>
           <tr>
               <th>省份编码：</th>
               <td><input name="provinceCode" maxlength='6' class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value = "${city.provinceCode}"></td>
           </tr>
           <tr>
               <th>简称 ：</th>
               <td><input name="abbr" maxlength='10' class="easyui-textbox"  style="width: 180px; height: 26px;" value = "${city.abbr}"></td>
           </tr>
	   </table>
	</form>

<p align="center">
	<a id="cityAdd-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>