<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#diary-grid');// 数据网格
		var search = $('#diary-search');// 搜索div
		var searchBtn = $('#diary-searchBtn');// 搜索按钮
		var saveBtn = $('#diary-addBtn');//增加按钮
		var removeBtn = $('#diary-removeBtn');//删除按钮
		var modifyBtn = $('#diary-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/diary/findDiaryPagination.do',// 数据来源日记
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
				field : 'title',
				title : '日记标题',
				width : '160px'
			}, {
				field : 'content',
				title : '日记内容',
				width : '160px'
			}, {
				field : 'icon',
				title : '图片',
				width : '160px'
			}, {
				field : 'commentTimes',
				title : '被评论次数',
				width : '120px'
			}, {
				field : 'likedTimes',
				title : '点赞次数',
				width : '120px'
			}, {
				field : 'createTime',
				title : '创建时间',
				width : '150px',
				formatter : function(val) {
					return formatDate(val);
				}
			} ] ],
			// 工具栏
			toolbar : '#diary-tool',
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
			$('#diary-add').dialog({
				title : '增加日记',
				width : 600,
				height : 520,
				closed : false,
				cache : false,
				href : '${basePath}manager/diary/addDiaryView.do',
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
												'确认删除此日记?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/diary/removeDiary.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#diary-grid")
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
								$('#diary-add')
										.dialog(
												{
													title : '修改日记',
													width : 600,
													height : 520,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/diary/modifyDiaryView.do?diaryid='
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

<div id="diary-tool">
	<div id="diary-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">主题:</label> <input name="title"
			class="easyui-textbox" style="height: 26px;"> <a
			id="diary-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="diary-modifyBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-user_gray'">修改日记</a> <a
			id="diary-removeBtn" href="#" class="easyui-linkbutton" plain="true"
			data-options="iconCls:'icon-edit_remove'">删除日记</a>
	</div>
</div>
<div id="diary-grid"></div>
<div id="diary-add"></div>
