<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
	/* function onCollegeChanged(e) {
		var collegeCombo = document.getElementById("college");
		var academyCombo = document.getElementById("academy");
		var coll = collegeCombo.getValue();
		academyCombo.load("${basePath}manager/book/findAcademy.do?collegeName="
						+ coll);
		academyCombo.setValue("");
		alert('${book.college}');
	}
	
	function onacademyChanged(e) {
		var collegeCombo = document.getElementById("college");
		var academyCombo = document.getElementById("academy");
		var professionCombo = document.getElementById("profession");
		var coll = collegeCombo.getValue();
		var acad = academyCombo.getValue();
		professionCombo.load('${basePath}manager/book/findProfession.do?collegeName='
						+ coll
						+ '&academyName='
						+ acad);
		professionCombo.setValue("");
	} */

	$(function() {
		/* mini.parse();

		var form = new mini.Form("book-fm"); */

		$('#gradeCombobox').combobox({
			valueField : 'grade',
			panelHeight : "auto",
			editable : false,
			onLoadSuccess : function(data) {
				$('#gradeCombobox').combobox('setValue', '${book.grade}');
			}
		});

		$('#classidCombobox').combobox({
			valueField : 'classId',
			panelHeight : "auto",
			editable : false,
			onLoadSuccess : function(data) {
				$('#classidCombobox').combobox('setValue', '${book.classId}');
			}
		});

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
							onLoadSuccess : function(data) {
								
								$('#academy').combobox('setValue',
										'${book.academy}');
								var url = '${basePath}manager/book/findAcademy.do?collegeName='
										+ '${book.college}';//url为java后台查询事级列表的方法地址
								$('#academy').combobox('reload', url);

								$('#profession').combobox('setValue',
										'${book.profession}');
								var url = '${basePath}manager/book/findProfession.do?collegeName='
										+ '${book.college}'
										+ '&academyName='
										+ '${book.academy}';//url为java后台查询事级列表的方法地址
								$('#profession').combobox('reload', url);
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
							onLoadSuccess : function(data) {

							},
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

		/* $("book-fm").form('clear'); */
		$("#bookModify-saveBtn").click(function() {
			$("#book-fm").form('submit', {
				url : '${basePath}manager/book/modifyBook.do',
				onSubmit : function() {
					loading();
					if (!$('#book-fm').form('validate')) {
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
						parent.$('#book-add').window('close');
						layer.msg('修改成功！');
						$("#book-grid").datagrid('reload'); // 刷新数据网格
					}
				}
			});
		});
	});
</script>

<form id="book-fm" method="post" enctype="multipart/form-data"
	action="${basePath}manager/book/modifyBook.do">
	<div>
		<input id="id" name="id" type="hidden" value="${book.id}" />
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">ISBN：</label> <input name="isbn"
			validtype="length[10,13]" missingMessage="不能为空"
			invalidMessage="有效长度10-13" value="${book.isbn}" class="easyui-textbox"
			required="true" style="width: 180px; height: 26px;"> <label
			style="margin-left: 20px" align="right">书名：</label> <input
			name="title" class="easyui-textbox" required="true"
			style="width: 180px; height: 26px;" value="${book.title}">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">子标题：</label> <input name="subtitle"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${book.subtitle}"> <label style="margin-left: 20px"
			align="right">作者 ：</label> <input name="author"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${book.author}">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">出版社 :</label> <input name="publisher"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${book.publisher}"> <label style="margin-left: 20px"
			align="right">页数 :</label> <input name="page" class="easyui-textbox"
			style="width: 180px; height: 26px;" value="${book.page}">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">折旧价格 :</label> <input name="depPrice"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${book.depPrice}"> <label style="margin-left: 20px"
			align="right">押金 :</label> <input name="deposit" maxlength='100'
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${book.deposit}">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">e币价格 :</label> <input name="ePrice"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${book.ePrice}"> <label style="margin-left: 20px"
			align="right">分类名称 :</label> <select id="classidCombobox"
			class="easyui-combobox" name="classId" style="width: 180px;"
			editable="false" panelHeight="auto" value="${book.classId}">
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
		<label align="right">学校 :</label> <input id="college" name="college"
			 style="width: 180px; height: 26px;"
			value="${book.college}"> <label style="margin-left: 20px"
			align="right">学院 :</label> <input id="academy" name="academy"
			maxlength='100' class="easyui-textbox"
			style="width: 180px; height: 26px;" value="${book.academy}">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">专业 :</label> <input id="profession"
			name="profession" maxlength='100' class="easyui-combobox"
			style="width: 180px; height: 26px;" value="${book.profession}">
		<label style="margin-left: 20px" align="right">年级 :</label> <input
			name="grade" maxlength='100' class="easyui-textbox"
			style="width: 180px; height: 26px;" value="${book.grade}">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">原价 :</label> <input name="initCost"
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${book.initCost}"> <label style="margin-left: 20px"
			align="right">其他平台 :</label> <input name="otherCost" maxlength='100'
			class="easyui-textbox" style="width: 180px; height: 26px;"
			value="${book.otherCost}">
	</div>
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">节省 :</label> <input name="saveCose"
			maxlength='100' class="easyui-textbox"
			style="width: 180px; height: 26px;" value="${book.saveCose}">
		<label style="margin-left: 20px" align="right">图片URL : </label> <input
			id="icons" name="icons" maxlength='200' class="easyui-filebox"
			style="width: 180px; height: 26px;" value="${book.icon}">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="bookModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>