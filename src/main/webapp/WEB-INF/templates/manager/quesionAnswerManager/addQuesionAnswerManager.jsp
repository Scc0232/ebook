<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	$(function() {
		var saveBtn = $('#quesionAnswerManager-saveBtn');
		var form = $('#quesionAnswerManager-fm');

		$(saveBtn).bind('click', function() {

			var obj = toObject(form);
			if (!$(form).form('validate')) {
				closeLoading();
				return false;
			}
			console.log(obj);
			$.ajax({
				type : "POST",
				url : '${basePath}quesionAnswerManager/addQuesionAnswerManager.do',
				data : obj,
				dataType : "json",
				async : "false",
				success : function(data) {
					if (data.flag === "success") {
						layer.msg('保存成功！');
						parent.$('#quesionAnswerManager-add').window('close');
						$("#quesionAnswerManager-grid").datagrid('reload'); // 刷新数据网格
					}
				}
			});
		});
	});
</script>
<form id="quesionAnswerManager-fm" method="post" novalidate
	style="margin-top: 50px;">
	<div class="fitem" style="margin-top: 50px;">
		<div style="margin-bottom: 50px" align="center">
			<label align="right">问题名称 ：</label>
			 <input name="question"
				validtype="length[4,50]" missingMessage="不能为空"
				invalidMessage="有效长度4-50" class="easyui-textbox" required="true"
				style="width: 600px; height: 35px;">

		</div>
		<div  style="margin-bottom: 20px"  align="center" >
			<label align="right">问题答案 ：</label> 
			<input name="answer"   
			    data-options="required:true,validType:'ORG_TYPE',multiline:true"
				class="easyui-textbox"  required="true"
				style="width: 600px; height: 250px;"
				 ></input>
		</div>
	</div>

</form>
<p align="center" style="margin-top: 50px;">
	<a id="quesionAnswerManager-saveBtn" class="easyui-linkbutton"><font
		size="2">提&nbsp;&nbsp;交</font></a>
</p>