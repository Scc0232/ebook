<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#major-grid');// 数据网格
		var search = $('#major-search');// 搜索div
		var searchBtn = $('#major-searchBtn');// 搜索按钮
		var saveBtn = $('#major-addBtn');//增加按钮
		var removeBtn = $('#major-removeBtn');//删除按钮
		var modifyBtn = $('#major-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/major/findMajorPagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ {
				field : 'id',
				title : '选择',
				checkbox : true
			}, {
				field : 'collegeName',
				title : '学校名称',
				width : '120px'
			}, {
				field : 'academyName',
				title : '学院名称',
				width : '160px'
			}, {
				field : 'professionName',
				title : '专业名称',
				width : '160px'
			}, {
				field : 'createTime',
				title : '创建时间',
				width : '150px',
				formatter : function(val) {
					return formatDate(val);
				}
			} ] ],
			// 工具栏
			toolbar : '#major-tool',
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
			$('#major-add').dialog({
				title : '增加专业',
				width : 600,
				height : 280,
				closed : false,
				cache : false,
				href : '${basePath}manager/major/addMajorView.do',
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
												'确认删除此专业?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/major/removeMajor.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#major-grid")
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
								$('#major-add')
										.dialog(
												{
													title : '修改专业',
													width : 600,
													height : 280,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/major/modifyMajorView.do?majorid='
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

<div id="major-tool">
	<div id="major-search" style="padding-top: 10px;">
			<label style="padding-left: 10px;">学校名称:</label> <input name="collegeName" class="easyui-textbox" style="height: 26px;"> 
			<label style="padding-left: 10px;">学院名称:</label> <input name="academyName" class="easyui-textbox" style="height: 26px;">
			<label style="padding-left: 10px;">专业名称:</label> <input name="professionName" class="easyui-textbox" style="height: 26px;">
			<a id="major-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="major-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加专业</a> <a id="major-modifyBtn"
			href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改专业</a> <a id="major-removeBtn" href="#"
			class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除专业</a>
	</div>
</div>
<div id="major-grid"></div>
<div id="major-add"></div>
