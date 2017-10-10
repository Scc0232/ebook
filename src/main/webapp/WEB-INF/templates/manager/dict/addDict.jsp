<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function () {
    var saveBtn = $('#dictAdd-saveBtn');
    var form = $('#dict-fm');

    $(saveBtn).bind('click', function () {
        var obj = toObject(form);
        console.log(obj);
        if (!$(form).form('validate')) {
            closeLoading();
            return false;
        }
        $.ajax({
            type: "POST",
            url: '${basePath}manager/dict/addDict.do',
            data: obj,
            async: "false",
            success: function (data) {
                if (data.flag === "success") {
                    layer.msg('保存成功！');
                    parent.$('#dict-dlg').window('close');
                    $("#dict-grid").datagrid('reload'); // 刷新数据网格
                }
            }
        });
    });
});
</script>
	<form id="dict-fm" method="post" enctype="multipart/form-data" action="${basePath}manager/dict/addDict.do">
	   <table class="am_table_1">
	       <tr style="height: 15px;"></tr>
	        <tr>
	           <th>字典名称 ：</th>
	           <td><input name="dictName" maxlength='50' data-options="required:true,validType:'CHS'"  class="easyui-textbox" style="width: 180px; height: 26px;" ></td>
	       </tr>
           <tr>
               <th>字典类型 ：</th>
               <td><input name="dictType" maxlength='50' class="easyui-textbox" data-options="required:true" style="width: 180px; height: 26px;"></td>
           </tr>
           <tr>
               <th>字典编码：</th>
               <td><input name="dictCode"  maxlength='32' class="easyui-textbox" style="width: 180px; height: 26px;"></td>
           </tr>
           <tr>
               <th>字典备注 ：</th>
               <td><input name="dictDescribe"  maxlength='200' class="easyui-textbox" data-options="required:true" style="width: 180px; height: 26px;"></td>
           </tr>
           <tr>
               <th>上传图片：</th>
               <td><input id="icons" name="icons"  maxlength='200' class="easyui-filebox" style="width: 180px; height: 26px;"></td>
           </tr>
	   </table>
	</form>

<p align="center">
	<a id="dictAdd-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>