<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	$(function() {
		/*         var saveBtn = $('#book-saveBtn');
		 var form = $('#bookAdd-fm');
		 $(saveBtn).bind('click', function () {
		
		 var obj = toObject(form);
		 if (!$(form).form('validate')) {
		 closeLoading();
		 return false;
		 }
		 console.log(obj);
		 $.ajax({
		 type: "POST",
		 url: '${basePath}manager/book/addBook.do',
		 data: obj,
		 dataType: "json",
		 async: "false",
		 success: function (data) {
		 if (data.flag === "success") {
		 layer.msg('保存成功！');
		 parent.$('#book-add').window('close');
		 $("#book-grid").datagrid('reload'); // 刷新数据网格
		 }
		 }
		 });
		 });
		 }); */

		/*     $('#collegeCombobox').combobox({
		 url:'${basePath}manager/book/findCollegeList.do',
		 valueField:'collegeName',
		 textField:'collegeName',
		 panelHeight: "auto",
		 editable: false,
		 onLoadSuccess: function (data) {
		 if (data) {
		 $('#collegeCombobox').combobox('setValue',data[0].collegeName);
		 }
		 } ,
		 onSelect: function(record){
		 academy.combobox({
		 url:'${basePath}manager/book/findAcademy.do?collegeName='+record.collegeName,
		 valueField:'academyName',
		 textField:'academyName',
		 panelHeight: "auto",
		 editable: false
		 }).combobox('clear');
		
		 } 
		 });

		 var academy = $('#academyCombobox').combobox({
		 url:'${basePath}manager/book/findAcademy.do?collegeName='+$("#collegeCombobox").combobox("getText"),
		 valueField:'academyName',
		 textField:'academyName',
		 panelHeight: "auto",
		 editable: false,
		 onSelect: function(record){ 
		 var url ='${basePath}manager/book/findAcademy.do?collegeName=' + record.Value;
		 $("#combobox_one").combobox('reload', url);
		 }
		
		 });
		 */
		$('#college')
				.combobox(
						{
							valueField : 'collegeName', //值字段
							textField : 'collegeName', //显示的字段
							url : '${basePath}manager/book/findCollegeList.do',//url为java后台查询省级列表的方法地址
							panelHeight : 'auto',
							editable : true,
							//模糊查询
							filter : function(q, row) {
								var opts = $(this).combobox('options');
								return row[opts.textField].indexOf(q) >= 0; //从头匹配,改成>=即可在任意地方匹配
							},
							onSelect : function(rec) {
								$('#academy').combobox('setValue', "");
								$('#profession').combobox('setValue', "");
								var url = '${basePath}manager/book/findAcademy.do?collegeName='
										+ rec.collegeName;//url为java后台查询事级列表的方法地址
								$('#academy').combobox('reload', url);
							}
						});
		//市区 
		$('#academy')
				.combobox(
						{
							valueField : 'collegeName', //值字段
							textField : 'academyName', //显示的字段              
							panelHeight : 'auto',
							editable : false, //不可编辑，只能选择
							value : '',
							onSelect : function(rec) {
								$('#profession').combobox('setValue', "");
								var url = '${basePath}manager/book/findProfession.do?collegeName='
										+ rec.collegeName
										+ '&academyName='
										+ rec.academyName;//url为java后台查询区县级列表的方法地址
								$('#profession').combobox('reload', url);
							}
						});
		//区 县
		$('#profession').combobox({
			valueField : 'professionName',
			textField : 'professionName',
			panelHeight : 'auto',
			editable : false,
		});

		var saveBtn = $('#book-saveBtn');

		$("#bookAdd-fm").form({
			onSubmit : function() {
				if (!$("#bookAdd-fm").form('validate')) {
					layer.msg("请添加必填项!");
					return false;
				}
				loading();
				return true;
			},
			success : function(data) {
				var map = $.parseJSON(data);
				if (map.flag == "success") {
					parent.$('#book-add').dialog('close');
				}
				closeLoading();
				layer.msg(map.msg);
				if (map.flag == "success") {
					$("#book-grid").datagrid('reload');
				}
			}
		});

		$(saveBtn).bind('click', function() {
			$("#bookAdd-fm").submit();
		});

	});
</script>
<form id="bookAdd-fm" method="post" enctype="multipart/form-data" action="${basePath}manager/book/addBook.do">
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">ISBN：</label> <input name="isbn" validtype="length[5,20]" missingMessage="不能为空" invalidMessage="有效长度5-20"
			class="easyui-textbox" required="true" style="width: 180px; height: 26px;"> <label style="margin-left: 20px" align="right">书名：</label>
		<input name="title" class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">子标题：</label> <input name="phone" class="easyui-textbox" style="width: 180px; height: 26px;"> <label
			style="margin-left: 20px" align="right">作者 ：</label> <input name="author" class="easyui-textbox" style="width: 180px; height: 26px;">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">出版社 :</label> <input name="publisher" class="easyui-textbox" style="width: 180px; height: 26px;"> <label
			style="margin-left: 20px" align="right">页数 :</label> <input name="page" class="easyui-textbox" style="width: 180px; height: 26px;">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">折旧价格 :</label> <input name="depPrice" class="easyui-textbox" style="width: 180px; height: 26px;"> <label
			style="margin-left: 20px" align="right">押金 :</label> <input name="deposit" maxlength='100' class="easyui-textbox"
			style="width: 180px; height: 26px;">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">e币价格 :</label> <input name="ePrice" class="easyui-textbox" style="width: 180px; height: 26px;"> <label
			style="margin-left: 20px" align="right">分类名称 :</label> <select class="easyui-combobox" name="classId" style="width: 180px;"
			editable="false" panelHeight="auto">
			<option value="1">考研</option>
			<option value="2">公务员</option>
			<option value="3">四六级</option>
			<option value="4">文学类</option>
			<option value="5">理工类</option>
			<option value="6">政史类</option>
			<option value="7">计算机类</option>
			<option value="8">英语类</option>
			<option value="9">经管类</option>
			<option value="10">语言类</option>
			<option value="11">其他</option>
		</select>
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">学校 :</label> <input id="college" name="college"  style="width: 180px; height: 26px;">
		<label style="margin-left: 20px" align="right">学院 :</label> <input id="academy" name="academy" maxlength='100' class="easyui-combobox"
			style="width: 180px; height: 26px;">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">专业 :</label> <input id="profession" name="profession" maxlength='100' class="easyui-combobox"
			style="width: 180px; height: 26px;"> <label style="margin-left: 20px" align="right">年级 :</label> <input name="grade"
			class="easyui-textbox" style="width: 180px; height: 26px;">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">原价 :</label> <input name="initCost" class="easyui-textbox" style="width: 180px; height: 26px;"> <label
			style="margin-left: 20px" align="right">其他平台 :</label> <input name="otherCost" maxlength='100' class="easyui-textbox"
			style="width: 180px; height: 26px;">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">节省 :</label> <input name="saveCose" maxlength='100' class="easyui-textbox" style="width: 180px; height: 26px;">
		<label style="margin-left: 20px" align="right">图片URL : </label> <input id="icons" name="icons" maxlength='200' class="easyui-filebox"
			style="width: 180px; height: 26px;">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="book-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>