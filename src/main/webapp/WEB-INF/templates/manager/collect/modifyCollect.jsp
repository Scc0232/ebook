<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	$("collect-fm").form('clear');
	$("#collectModify-saveBtn").click(function(){
		 $("#collect-fm").form('submit', { 
			 url : '${basePath}manager/collect/modifyCollect.do', 
			 onSubmit : function() {
            loading();
            if (!$('#collect-fm').form('validate')) {
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
            	parent.$('#collect-add').window('close');
            	layer.msg('修改成功！');
                $("#collect-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});



</script>

<form id="collect-fm" method="post" novalidate style="margin-top: 50px;">
    <div>
        <input id="id" name="id" type="hidden" value="${collect.id}"/>
    </div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">姓名：</label> 
		<input name="username" missingMessage="不能为空" invalidMessage="有效长度5-20" value="${collect.username}"  class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">手机号：</label>
		<input name="phone" class="easyui-textbox"   required="true" style="width: 180px; height: 26px;" value="${collect.phone}">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">校区：</label> 
		<input name="college" class="easyui-textbox" style="width: 180px; height: 26px;" value="${collect.college}">
	   <label style="margin-left: 20px" align="right">宿舍楼 ：</label>
		<input name="flat"  class="easyui-textbox" style="width: 180px; height: 26px;" value="${collect.flat}">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="collectModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>