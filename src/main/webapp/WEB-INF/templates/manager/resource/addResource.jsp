<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var saveBtn = $('#resource-saveBtn');
        var form = $('#resourceAdd-fm');

        $(saveBtn).bind('click', function () {
        	 if (!$(form).form('validate')) {
                 closeLoading();
                 return false;
             }
            var obj = toObject(form);
            $.ajax({
                type: "POST",
                url: '${basePath}manager/resource/addResource.do',
                data: obj,
                dataType: "json",
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('保存成功！');
                        parent.$('#resource-add').window('close');
                        $("#resource-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    });
    
</script>
	<form id="resourceAdd-fm" method="post" novalidate style="margin-top: 40px;" >
		<div class="fitem" style="margin-top: 20px;" buttons="#resource-dlg-buttons">
			<div class="fitem" style="margin-top: 30px;">
				<label align="right">资源名称:</label>
				<input name="name" class="easyui-textbox" required="true" style="width: 200px; height: 26px;">
			</div>
			<div class="fitem" style="margin-top: 30px;">
	            <label align="right">资源名:</label>
	            <input name="displayName" class="easyui-textbox" required="true" style="width: 200px; height: 26px;">
	        </div>
			<div class="fitem" style="margin-top: 30px;">
				<label align="right">URL:</label>
				<input name="url" class="easyui-textbox" required="true" style="width: 200px; height: 26px;">
			</div>
			<div class="fitem" style="margin-top: 30px;">
				<label align="right">备注:</label>
				<input name="remark" class="easyui-textbox" required="true" style="width: 200px; height: 26px;">
			</div>
		</div>
	</form>


<!-- <p align="center">
	<a id="resource-saveBtn" class="easyui-linkbutton"><font size="3">提&nbsp;&nbsp;交</font></a>
</p> -->

<div id="resource-dlg-buttons" style="text-align: center; padding-top: 20px;">
	<a id="resource-saveBtn" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" style="width: 90px">保存</a> 
	<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:$('#resource-dlg').dialog('close')" style="width: 90px">返回</a> -->
</div>