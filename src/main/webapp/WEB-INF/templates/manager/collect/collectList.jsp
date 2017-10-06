<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#collect-grid');// 数据网格
		var search = $('#collect-search');// 搜索div
		var searchBtn = $('#collect-searchBtn');// 搜索按钮
		var saveBtn = $('#collect-addBtn');//增加按钮
		var removeBtn = $('#collect-removeBtn');//删除按钮
		var modifyBtn = $('#collect-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/collect/findCollectPagination.do',// 数据来源收藏
			selectOnCheck : true,
			columns : [ [ {
				field : 'id',
				title : '选择',
				checkbox : true
			}, {
				field : 'username',
				title : '姓名',
				width : '160px'
			}, {
				field : 'bookName',
				title : '图书名',
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
			toolbar : '#collect-tool',
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
			$('#collect-add').dialog({
				title : '增加收藏',
				width : 600,
				height : 520,
				closed : false,
				cache : false,
				href : '${basePath}manager/collect/addCollectView.do',
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
												'确认删除此收藏?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/collect/removeCollect.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#collect-grid")
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
								$('#collect-add')
										.dialog(
												{
													title : '修改收藏',
													width : 600,
													height : 520,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/collect/modifyCollectView.do?collectid='
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

<div id="collect-tool">
	<div id="collect-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">图书名:</label> <input name="bookName"
			class="easyui-textbox" style="height: 26px;">  <a
			id="collect-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="collect-removeBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-edit_remove'">删除收藏</a>
	</div>
</div>
<div id="collect-grid"></div>
<div id="collect-add"></div>
