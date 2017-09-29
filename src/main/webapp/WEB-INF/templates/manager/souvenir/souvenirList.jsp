<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#souvenir-grid');// 数据网格
		var search = $('#souvenir-search');// 搜索div
		var searchBtn = $('#souvenir-searchBtn');// 搜索按钮
		var saveBtn = $('#souvenir-addBtn');//增加按钮
		var removeBtn = $('#souvenir-removeBtn');//删除按钮
		var modifyBtn = $('#souvenir-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/souvenir/findSouvenirPagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ {
				field : 'id',
				title : '选择',
				checkbox : true
			}, {
				field : 'type',
				title : '类型',
				width : '120px',
				formatter : function(val) {
					if (val == '1') {
						return '校园文创';
					} else {
						return '文化';
					} 

				}
			}, {
				field : 'name',
				title : '名称',
				width : '160px'
			}, {
				field : 'introduce',
				title : '简介',
				width : '120px'
			}, {
				field : 'icon',
				title : '图片',
				width : '120px'
			}, {
				field : 'createTime',
				title : '创建时间',
				width : '120px',
				formatter : function(val) {
					return formatDate(val);
				}
			} ] ],
			// 工具栏
			toolbar : '#souvenir-tool',
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
			$('#souvenir-add').dialog({
				title : '增加纪念品',
				width : 600,
				height : 520,
				closed : false,
				cache : false,
				href : '${basePath}manager/souvenir/addSouvenirView.do',
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
												'确认删除此纪念品?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/souvenir/removeSouvenir.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#souvenir-grid")
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
								$('#souvenir-add')
										.dialog(
												{
													title : '修改纪念品',
													width : 600,
													height : 520,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/souvenir/modifySouvenirView.do?souvenirid='
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

<div id="souvenir-tool">
	<div id="souvenir-search" style="padding-top: 10px;">
			<label style="padding-left: 10px;">名称:</label> <input name="name" class="easyui-textbox" style="height: 26px;"> 
			<label style="padding-left: 10px;">类型:
			<select  class="easyui-combobox" name="type" style="width:74px;" editable="false" panelHeight="auto">
			        	<option value=" " > </option>
			        	<option value=" " > </option>
			        	<option value="1">校园文创</option>
			        	<option value="2">文化</option>
    		</select> 
    		
    		
    		
			<a id="souvenir-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="souvenir-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加纪念品</a> <a id="souvenir-modifyBtn"
			href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改纪念品</a> <a id="souvenir-removeBtn" href="#"
			class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除纪念品</a>
	</div>
</div>
<div id="souvenir-grid"></div>
<div id="souvenir-add"></div>
