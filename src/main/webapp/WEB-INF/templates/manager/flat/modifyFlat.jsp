<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	$("flat-fm").form('clear');
	$("#flatModify-saveBtn").click(function(){
		 $("#flat-fm").form('submit', { 
			 url : '${basePath}manager/flat/modifyFlat.do', 
			 onSubmit : function() {
            loading();
            if (!$('#flat-fm').form('validate')) {
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
            	parent.$('#flat-add').window('close');
            	layer.msg('修改成功！');
                $("#flat-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});



</script>

<form id="flat-fm" method="post" novalidate style="margin-top: 50px;">
    <div>
        <input id="id" name="id" type="hidden" value="${flat.id}"/>
    </div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">校区名称：</label> 
		<input name="collegeName" validtype="length[5,20]" missingMessage="不能为空" invalidMessage="有效长度5-20" value="${flat.collegeName}"  class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">宿舍楼名称：</label>
		<input name="flatName" class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${flat.flatName}">
	</div>
</form>
<p align="center">
	<a id="flatModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>