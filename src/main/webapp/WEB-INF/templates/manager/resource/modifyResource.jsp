<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var modifyBtn = $('#resourceModify-modifyBtn');
        var form = $('#resourceModify-fm');

        $(modifyBtn).bind('click', function () {
            var obj = toObject(form);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            $.ajax({
                type: "POST",
                url: '${basePath}manager/resource/modifyResource.do',
                data: obj,
                dataType: "json",
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('修改成功！');
                        parent.$('#resource-add').window('close');
                        $("#resource-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    });
</script>

<form id="resourceModify-fm" method="post" novalidate style="margin-top: 40px;">

    <div class="fitem" style="margin-top: 20px;">
        <input id="id" name="id" type="hidden" value="${resourceMap.id}"/>
        <div class="fitem" style="margin-top: 30px;">
            <label align="right">资源简称:</label>
            <input name="name" value="${resourceMap.name}" class="easyui-textbox" required="true" style="width: 200px; height: 26px;">
        </div>
       
         <div class="fitem" style="margin-top: 30px;">
            <label align="right">资源名:</label>
            <input name="displayName" value="${resourceMap.displayName}" class="easyui-textbox" required="true" style="width: 200px; height: 26px;">
        </div>
        
        <div class="fitem" style="margin-top: 30px;">
            <label align="right">URL:</label>
            <input name="url" value="${resourceMap.url}" class="easyui-textbox" required="true" style="width: 200px; height: 26px;">
        </div>
        
        <div class="fitem" style="margin-top: 30px;">
				<label align="right">备注:</label>
				<input name="remark" value="${resourceMap.remark}" class="easyui-textbox" required="true" style="width: 200px; height: 26px;">
			</div>
    </div>
</form>


<div id="resource-modify-buttons" style="text-align: center; padding-top: 20px;">
	<a id="resourceModify-modifyBtn" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" style="width: 90px">保存</a> 
	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:$('#resource-dlg').dialog('close')" style="width: 90px">返回</a> -->
</div>
