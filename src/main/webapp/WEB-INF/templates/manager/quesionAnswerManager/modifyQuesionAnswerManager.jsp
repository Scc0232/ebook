<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	$(function() {
		$("quesionAnswerManager-fm").form('clear');
		$("#quesionAnswerManagerModify-saveBtn").click(function() {
			$("#quesionAnswerManager-fm").form('submit', {
				url : '${basePath}quesionAnswerManager/modifyQuesionAnswerManager.do',
				onSubmit : function() {
					loading();
					if (!$('#quesionAnswerManager-fm').form('validate')) {
						closeLoading();
						return false;
					}
					return true;
				},
				success : function(result) {
					closeLoading();
					var result = eval('(' + result + ')');
					console.log(result);
					if (result.flag === 'fail') {
						$.messager.show({
							title : 'Error',
							msg : result.msg
						});
					} else {
						parent.$('#quesionAnswerManager-add').window('close');
						layer.msg('修改成功！');
						$("#quesionAnswerManager-grid").datagrid('reload'); // 刷新数据网格
					}
				}
			});
		});
	});
</script>

<form id="quesionAnswerManager-fm" method="post" novalidate
	style="margin-top: 50px;">
	<div>
		<input id="id" name="id" type="hidden" value="${quesionAnswerManager.id}" />
	</div>
	<div class="fitem" style="margin-top: 50px; margin-bottom: 50px"
		align="center">
		<label align="right">问题名称 ：</label> <input name="question"
			validtype="length[4,50]" missingMessage="不能为空"
			invalidMessage="有效长度4-50" class="easyui-textbox" required="true"
			style="width: 600px; height: 35px;" value="${quesionAnswerManager.question}">
	</div>
	<div style="margin-bottom: 20px" align="center">
		<label style="margin-left: 20px">问题答案 ：</label> 
		<input name="answer"   
			    data-options="required:true,validType:'ORG_TYPE',multiline:true"
				class="easyui-textbox"  required="true"
				style="width: 600px; height: 250px;" value="${quesionAnswerManager.answer}"
				 ></input>
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="quesionAnswerManagerModify-saveBtn" class="easyui-linkbutton"><font
		size="2">提&nbsp;&nbsp;交</font></a>
</p>