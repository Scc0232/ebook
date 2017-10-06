<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#flat-grid');// 数据网格
		var search = $('#flat-search');// 搜索div
		var searchBtn = $('#flat-searchBtn');// 搜索按钮
		var saveBtn = $('#flat-addBtn');//增加按钮
		var removeBtn = $('#flat-removeBtn');//删除按钮
		var modifyBtn = $('#flat-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/flat/findFlatPagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ {
				field : 'id',
				title : '选择',
				checkbox : true
			}, {
				field : 'collegeName',
				title : '校区',
				width : '160px'
			}, {
				field : 'flatName',
				title : '宿舍楼名称',
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
			toolbar : '#flat-tool',
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
			$('#flat-add').dialog({
				title : '增加宿舍',
				width : 600,
				height : 220,
				closed : false,
				cache : false,
				href : '${basePath}manager/flat/addFlatView.do',
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
												'确认删除此宿舍?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/flat/removeFlat.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#flat-grid")
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
								$('#flat-add')
										.dialog(
												{
													title : '修改宿舍',
													width : 600,
													height : 220,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/flat/modifyFlatView.do?flatid='
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

<div id="flat-tool">
	<div id="flat-search" style="padding-top: 10px;">
			<label style="padding-left: 10px;">学校:</label> <input name="collegeName" class="easyui-textbox" style="height: 26px;"> 
			<a id="flat-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="flat-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加宿舍</a> <a id="flat-modifyBtn"
			href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改宿舍</a> <a id="flat-removeBtn" href="#"
			class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除宿舍</a>
	</div>
</div>
<div id="flat-grid"></div>
<div id="flat-add"></div>
