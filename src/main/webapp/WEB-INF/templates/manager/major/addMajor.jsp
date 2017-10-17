<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
/*         var saveBtn = $('#major-saveBtn');
        var form = $('#majorAdd-fm');
        $(saveBtn).bind('click', function () {
          
            var obj = toObject(form);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            console.log(obj);
            $.ajax({
                type: "POST",
                url: '${basePath}manager/major/addMajor.do',
                data: obj,
                dataType: "json",
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('保存成功！');
                        parent.$('#major-add').window('close');
                        $("#major-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    }); */
    var saveBtn = $('#major-saveBtn');
    
    $("#majorAdd-fm").form({
                onSubmit: function(){
                        if(! $("#majorAdd-fm").form('validate')){
                                layer.msg("请添加必填项!");
                                return false;
                        }
                        loading();
                        return true;
                },
                success: function(data){                                
                        var map = $.parseJSON(data);
                        if(map.flag == "success"){
                                parent.$('#major-add').dialog('close');
                        }
                        closeLoading();
                        layer.msg(map.msg);
                        if(map.flag == "success"){
                                $("#major-grid").datagrid('reload');
                        }
                }                       
        });
        
    $(saveBtn).bind('click', function () {
        $("#majorAdd-fm").submit();
    }); 
    
    
    
});
    
    
</script>
<form id="majorAdd-fm" method="post" enctype="multipart/form-data"  action="${basePath}manager/major/addMajor.do">
		<div class="fitem" style="margin-top: 30px;" align="center">
		<label align="center">学校名称 :</label> <input name="collegeName"
			class="easyui-textbox" missingMessage="不能为空" style="width: 180px; height: 26px;"
			value="${major.collegeName}">
	</div>
	<div class="fitem" style="margin-top: 20px;" align="center">
		<label align="center">学院名称:</label> <input name="academyName"
			class="easyui-textbox" missingMessage="不能为空" style="width: 180px; height: 26px;"
			value="${major.academyName}">
	</div>
	<div class="fitem" style="margin-top: 20px;" align="center" align="center">
		<label align="center">专业名称 :</label> <input name="professionName"
			class="easyui-textbox" missingMessage="不能为空" style="width: 180px; height: 26px;"
			value="${major.professionName}">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="major-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>