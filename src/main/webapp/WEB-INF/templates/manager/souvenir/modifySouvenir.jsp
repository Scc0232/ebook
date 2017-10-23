<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){

		$('#typeCombobox').combobox({
			valueField:'type',
			panelHeight: "auto",
            editable: false,
				
            onLoadSuccess: function (data) {
            	$('#typeCombobox').combobox('setValue','${souvenir.type}');
            }
		});
	
		
		
	$("souvenir-fm").form('clear');
	$("#souvenirModify-saveBtn").click(function(){
		 $("#souvenir-fm").form('submit', { 
			 url : '${basePath}manager/souvenir/modifySouvenir.do', 
			 onSubmit : function() {
            loading();
            if (!$('#souvenir-fm').form('validate')) {
                closeLoading();
                return false;
            }
            return true;
        }, success : function(result) {
            closeLoading();
            var result = eval('(' + result + ')');
            console.log(result);
            if (result.flag === 'fail') {
                $.messager.show({ title : 'Error', msg : result.msg });
            } else {
            	parent.$('#souvenir-add').window('close');
            	layer.msg('修改成功！');
                $("#souvenir-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});



</script>

<form id="souvenir-fm" method="post" enctype="multipart/form-data" action="${basePath}manager/souvenir/modifySouvenir.do">
    <div>
        <input id="id" name="id" type="hidden" value="${souvenir.id}"/>
    </div>
	<div class="fitem" style="margin-top: 20px;">
	   <label  align="right">类型 :</label>
    		<select  id="typeCombobox" class="easyui-combobox" name="type" style="width:180px;" editable="false" panelHeight="auto" value="${souvenir.type}">
			        	<option value="1">校园文创</option>
			        	<option value="2">文化</option>
     		</select> 
     	<label style="margin-left: 20px" align="right">名称：</label>
		<input name="name" class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${souvenir.name}">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">简介：</label> 
		<input name="introduce" class="easyui-textbox" style="width: 180px; height: 26px;" value="${souvenir.introduce}">
	   <label style="margin-left: 20px" align="right">价格 ：</label>
		<input name="price"  class="easyui-textbox" style="width: 180px; height: 26px;" required="true" value="${souvenir.price}">
	</div>
	<div class="fitem" style="margin-top: 20px;">
	   <label  align="right">图片URL ：</label>
		<input name="icons"  class="easyui-filebox" style="width: 180px; height: 26px;" value="${souvenir.icon}">
	</div>
</form>
<p align="center">
	<a id="souvenirModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>