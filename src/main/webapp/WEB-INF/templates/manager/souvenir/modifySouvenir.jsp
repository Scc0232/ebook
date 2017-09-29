<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){

		$('#gradeCombobox').combobox({
			valueField:'grade',
			panelHeight: "auto",
            editable: false,
				
            onLoadSuccess: function (data) {
            	$('#gradeCombobox').combobox('setValue','${book.grade}');
            }
		});
	
		$('#classidCombobox').combobox({
			valueField:'classId',
			panelHeight: "auto",
            editable: false,
				
            onLoadSuccess: function (data) {
            	$('#classidCombobox').combobox('setValue','${souvenir.classId}');
            }
		});
		
	$("book-fm").form('clear');
	$("#bookModify-saveBtn").click(function(){
		 $("#book-fm").form('submit', { 
			 url : '${basePath}manager/book/modifySouvenir.do', 
			 onSubmit : function() {
            loading();
            if (!$('#book-fm').form('validate')) {
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
            	parent.$('#book-add').window('close');
            	layer.msg('修改成功！');
                $("#book-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});



</script>

<form id="book-fm" method="post" novalidate style="margin-top: 50px;">
    <div>
        <input id="id" name="id" type="hidden" value="${book.id}"/>
    </div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">ISBN：</label> 
		<input name="isbn" validtype="length[5,20]" missingMessage="不能为空" invalidMessage="有效长度5-20" value="${book.isbn}"  class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">书名：</label>
		<input name="title" class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${book.title}">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">子标题：</label> 
		<input name="subtitle" class="easyui-textbox" style="width: 180px; height: 26px;" value="${book.subtitle}">
	   <label style="margin-left: 20px" align="right">作者 ：</label>
		<input name="author"  class="easyui-textbox" style="width: 180px; height: 26px;" value="${book.author}">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">出版社 :</label> 
		<input name="publisher" class="easyui-textbox"  style="width: 180px; height: 26px;" value="${book.publisher}">
	   <label style="margin-left: 20px" align="right">页数 :</label>
		<input name="page"  class="easyui-textbox"   style="width: 180px; height: 26px;" value="${book.page}">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">折旧价格 :</label> 
		<input name="depPrice" class="easyui-textbox"  style="width: 180px; height: 26px;" value="${book.depPrice}">
	   <label style="margin-left: 20px" align="right">押金 :</label>
		<input name="deposit" maxlength='100' class="easyui-textbox"  style="width: 180px; height: 26px;" value="${book.deposit}">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">e币价格 :</label> 
		<input name="ePrice" class="easyui-textbox"  style="width: 180px; height: 26px;" value="${book.ePrice}">
	   <label style="margin-left: 20px" align="right">分类名称 :</label>
    		<select id="classidCombobox"  class="easyui-combobox" name="classId" style="width:180px;" editable="false" panelHeight="auto" value="${book.classId}">
			        	<option value="1">考研</option>
			        	<option value="2">公务员</option>
			        	<option value="3">四六级</option>
			        	<option value="4">文学类</option>
			        	<option value="5">理工类</option>
			        	<option value="6">政史类</option>
			        	<option value="7">计算机类</option>
			        	<option value="8">英语类</option>
			        	<option value="9">经管类机类</option>
			        	<option value="10">语言类</option>
			        	<option value="11">其他</option>
    		</select> 
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">学校 :</label> 
		<input name="college" class="easyui-textbox"  style="width: 180px; height: 26px;" value="${book.college}">
	   <label style="margin-left: 20px" align="right">年级 :</label>
	   <select id="gradeCombobox" class="easyui-combobox" name="grade" style="width:180px;" editable="false" panelHeight="auto" value="${book.grade}" selected="true">
			        	<option value="one">大一</option>
			        	<option value="two">大二</option>
			        	<option value="three">大三</option>
			        	<option value="four">大四</option>
    		</select> 
	</div>
	<div>
		<label align="left">图片URL :</label><input name="icon" class="easyui-textbox"  style="width: 380px; height: 26px;" value="${book.icon}">
	</div>
</form>
<p align="center">
	<a id="bookModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>