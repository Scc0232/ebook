<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#bookShelf-grid');// 数据网格
		var search = $('#bookShelf-search');// 搜索div
		var searchBtn = $('#bookShelf-searchBtn');// 搜索按钮
		var saveBtn = $('#bookShelf-addBtn');//增加按钮
		var removeBtn = $('#bookShelf-removeBtn');//删除按钮
		var modifyBtn = $('#bookShelf-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/bookShelf/findBookShelfPagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ {
				field : 'id',
				title : '选择',
				checkbox : true
			}, {
				field : 'isbn',
				title : 'ISBN',
				width : '120px'
			}, {
				field : 'title',
				title : '书名',
				width : '160px'
			}, {
				field : 'author',
				title : '作者',
				width : '120px'
			}, {
				field : 'page',
				title : '页数',
				width : '120px'
			}, {
				field : 'ePrice',
				title : 'e币价格',
				width : '120px'
			}, {
				field : 'className',
				title : '分类',
				width : '120px'
			}, {
				field : 'grade',
				title : '年级',
				width : '120px'
			}, {
				field : 'pubdate',
				title : '出版时间',
				width : '150px',
				formatter : function(val) {
					return formatDate(val);
				}
			}, {
				field : 'icon',
				title : '图片URL',
				width : '220px'
			} ] ],
			// 工具栏
			toolbar : '#bookShelf-tool',
			fit : true,
			fitColumns : true
		};

		$(searchBtn).bind('click', function() {
			var param = toObject(search);
			console.log(param);
			$(dg).datagrid('load', param);
		});

		//添加方法
		$(saveBtn).bind('click', function() {
			$('#bookShelf-add').dialog({
				title : '增加图书',
				width : 600,
				height : 520,
				closed : false,
				cache : false,
				href : '${basePath}manager/bookShelf/addBookShelfView.do',
				modal : true
			});
		});

		//删除按钮绑定事件
		$(removeBtn)
				.bind(
						'click',
						function() {
							var rows = $(dg).datagrid('getSelected');
							if (rows) {

								$.messager
										.confirm(
												'温馨提示',
												'确认删除此图书?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/bookShelf/removeBookShelf.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#bookShelf-grid")
																				.datagrid(
																						'reload'); // 刷新数据网格
																	}
																}
															});
												});
							} else {
								layer.msg('必须选择一行!');
								return;
							}
						});

		//修改方法
		$(modifyBtn)
				.bind(
						'click',
						function() {
							var row = $(dg).datagrid('getSelected');
							if (row) {
								$('#bookShelf-add')
										.dialog(
												{
													title : '修改图书',
													width : 600,
													height : 520,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/bookShelf/modifyBookShelfView.do?bookShelfid='
															+ row.id,
													modal : true
												});
							} else {
								layer.msg('必须选择一行!');
							}
						});

		// 加载数据网格
		$(dg).datagrid(dgOption);
	})();
</script>

<div id="bookShelf-tool">
	<div id="bookShelf-search" style="padding-top: 10px;">
			<label style="padding-left: 10px;">书名:</label> <input name="title" class="easyui-textbox" style="height: 26px;"> 
			<label style="padding-left: 10px;">作者:</label> <input name="author" class="easyui-textbox" style="height: 26px;">
			<label style="padding-left: 10px;">年级:</label><input name="grade" class="easyui-textbox" style="height: 26px;">
    		<label style="padding-left: 10px;">分类:
    		<select  class="easyui-combobox" name="classId" style="width:74px;" editable="false" panelHeight="auto">
			        	<option value=" " ></option>
			        	<option value=" " ></option>
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
    		
    		
			<a id="bookShelf-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="bookShelf-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加图书</a> <a id="bookShelf-modifyBtn"
			href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改图书</a> <a id="bookShelf-removeBtn" href="#"
			class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除图书</a>
	</div>
</div>
<div id="bookShelf-grid"></div>
<div id="bookShelf-add"></div>
