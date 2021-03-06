<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var saveBtn = $('#souvenir-saveBtn');
        /* var form = $('#souvenirAdd-fm');
        $(saveBtn).bind('click', function () {
          
            var obj = toObject(form);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            console.log(obj);
            $.ajax({
                type: "POST",
                url: '${basePath}manager/souvenir/addSouvenir.do',
                data: obj,
                dataType: "json",
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('保存成功！');
                        parent.$('#souvenir-add').window('close');
                        $("#souvenir-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        }); */
        
        $("#souvenirAdd-fm").form({
            onSubmit: function(){
                    if(! $("#souvenirAdd-fm").form('validate')){
                            layer.msg("请添加必填项!");
                            return false;
                    }
                    loading();
                    return true;
            },
            success: function(data){                                
                    var map = $.parseJSON(data);
                    if(map.flag == "success"){
                            parent.$('#souvenir-add').dialog('close');
                    }
                    closeLoading();
                    layer.msg(map.msg);
                    if(map.flag == "success"){
                            $("#souvenir-grid").datagrid('reload');
                    }
            }                       
    });
    
$(saveBtn).bind('click', function () {
    $("#souvenirAdd-fm").submit();
}); 
        
    });
    
</script>
<form id="souvenirAdd-fm" method="post" enctype="multipart/form-data" action="${basePath}manager/souvenir/addSouvenir.do">
		<div class="fitem" style="margin-top: 20px;">
	   <label  align="right">类型 :</label>
    		<select  class="easyui-combobox" name="type" style="width:180px;" editable="false" panelHeight="auto">
			        	<option value="1">校园文创</option>
			        	<option value="2">文化</option>
     		</select> 
     	<label style="margin-left: 20px" align="right">名称：</label>
		<input name="name" class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">简介：</label> 
		<input name="introduce" class="easyui-textbox" style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">价格 ：</label>
		<input name="price"  class="easyui-textbox" style="width: 180px; height: 26px;" required="true">
	</div>
	<div class="fitem" style="margin-top: 20px;">
	   <label  align="right">图片URL ：</label>
		<input  name="icons"  class="easyui-filebox" style="width: 180px; height: 26px;">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="souvenir-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>