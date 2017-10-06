<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	$("diary-fm").form('clear');
	$("#diaryModify-saveBtn").click(function(){
		 $("#diary-fm").form('submit', { 
			 url : '${basePath}manager/diary/modifyDiary.do', 
			 onSubmit : function() {
            loading();
            if (!$('#diary-fm').form('validate')) {
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
            	parent.$('#diary-add').window('close');
            	layer.msg('修改成功！');
                $("#diary-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});



</script>

<form id="diary-fm" method="post" novalidate style="margin-top: 50px;">
    <div>
        <input id="id" name="id" type="hidden" value="${diary.id}"/>
    </div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">主题：</label> 
		<input name="title" missingMessage="不能为空"  value="${diary.title}"  class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">图片：</label>
		<input name=icon class="easyui-textbox"     style="width: 180px; height: 26px;" value="${diary.icon}">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">内容：</label> 
		<input name="content" class="easyui-textbox" style="width: 380px; height: 26px;" data-options="multiline:true"  value="${diary.content}">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="diaryModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>