<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	$(function() {
		
		//机构名称下拉列表
		$('#orgIdCombobox').combobox({
			url:'${basePath}styleSetting/findOrgIdList.do',
			valueField:'id',
			textField:'orgName',
			panelHeight: "auto",
            editable: false,
			onLoadSuccess: function (data) {
                if (data) {
                    $('#orgIdCombobox').combobox('setValue',data[0].id);
                }
            }
		});
		$("styleSetting-fm").form('clear');
		
		$("#styleSetting-saveBtn").click(function(){
			 $("#styleSettingAdd-fm").form('submit', { 
				 url : '${basePath}styleSetting/addStyleSetting.do', 
				 onSubmit : function() {
	            loading();
	            if (!$(this).form('validate')) {
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
	            	parent.$('#styleSetting-add').window('close');
	                $("#styleSetting-grid").datagrid('reload'); // 刷新数据网格
	            }
	        } });
		});
	});
</script>
<form id="styleSettingAdd-fm" method="post" novalidate
	style="margin-top: 50px;">
	<div class="fitem" style="margin-top: 50px;">
		<div style="margin-bottom: 50px" align="center">
			<label align="right" style="width:100px">机构ID ：</label> <input id="orgIdCombobox" name="orgId"
				  editable="false" data-options="required:true,validType:'ORG_NAME'"
				style="width: 180px; height: 26px;">

		</div>
		<div style="margin-bottom: 20px" align="center">
			<label align="right" style="width:100px">CSS资源地址：</label> <input name="cssResourceUrl"
				data-options="required:true,validType:'CSS_RESOURCE_URL'"
				class="easyui-textbox" required="true"
				style="width: 180px; height: 26px;">
		</div>
	</div>

</form>
<p align="center" style="margin-top: 50px;">
	<a id="styleSetting-saveBtn" class="easyui-linkbutton"><font
		size="2">提&nbsp;&nbsp;交</font></a>
</p>