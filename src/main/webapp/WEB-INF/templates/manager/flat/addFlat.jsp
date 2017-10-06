<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var saveBtn = $('#flat-saveBtn');
        var form = $('#flatAdd-fm');
        $(saveBtn).bind('click', function () {
          
            var obj = toObject(form);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            console.log(obj);
            $.ajax({
                type: "POST",
                url: '${basePath}manager/flat/addFlat.do',
                data: obj,
                dataType: "json",
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('保存成功！');
                        parent.$('#flat-add').window('close');
                        $("#flat-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    });
    
</script>
<form id="flatAdd-fm" method="post" novalidate style="margin-top: 50px;">
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">校区：</label> 
		<input name="collegeName"  missingMessage="不能为空" invalidMessage="有效长度5-20"   class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">宿舍楼名称：</label>
		<input name="flatName" class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	</div>
		
</form>
<p align="center" style="margin-top: 50px;">
	<a id="flat-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>