<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#donation-grid');// 数据网格
		var search = $('#donation-search');// 搜索div
		var searchBtn = $('#donation-searchBtn');// 搜索按钮
		var saveBtn = $('#donation-addBtn');//增加按钮
		var removeBtn = $('#donation-removeBtn');//删除按钮
		var modifyBtn = $('#donation-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/donation/findDonationPagination.do',// 数据来源捐赠
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
				field : 'cnname',
				title : '联系人',
				width : '80px'
			}, {
				field : 'phoneNo',
				title : '联系电话',
				width : '100px'
			},{
				field : 'address',
				title : '收货地址',
				width : '200px'
			}, {
				field : 'status',
				title : '捐赠状态',
				width : '60px',
				formatter : function(val) {
					if (val == '0') {
						return '待确认';
					} else if (val == '1') {
						return '已确认';
					}
				}
			}, {
				field : 'isbn',
				title : 'ISBN',
				width : '120px'
			}, {
				field : 'bookName',
				title : '书名',
				width : '160px'
			}, {
				field : 'author',
				title : '作者',
				width : '120px'
			}, {
				field : 'eValue',
				title : 'e币价格',
				width : '80px'
			}, {
				field : 'isNote',
				title : '是否有笔记',
				width : '80px',
				formatter : function(val) {
					if (val == '1') {
						return '有';
					} else if (val == '0') {
						return '无';
					}
				}
			}, {
				field : 'degree',
				title : '新旧程度',
				width : '80px'
			}, {
				field : 'createTime',
				title : '创建时间',
				width : '120px',
				formatter : function(val) {
					return formatDate(val);
				}
			} ] ],
			// 工具栏
			toolbar : '#donation-tool',
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
			$('#donation-add').dialog({
				title : '增加捐赠',
				width : 600,
				height : 520,
				closed : false,
				cache : false,
				href : '${basePath}manager/donation/addDonationView.do',
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
												'确认删除此捐赠?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/donation/removeDonation.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#donation-grid")
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
								$('#donation-add')
										.dialog(
												{
													title : '修改捐赠',
													width : 600,
													height : 220,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/donation/modifyDonationView.do?donationid='
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

<div id="donation-tool">
	<div id="donation-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">图书名称:</label> <input
			name="bookName" class="easyui-textbox" style="height: 26px;">
		<label style="padding-left: 10px;">捐赠状态: <select
			class="easyui-combobox" name="status" style="width: 74px;"
			editable="false" panelHeight="auto">
				<option value=></option>
				<option value=></option>
				<option value=" 0">待确认</option>
				<option value=" 1">已确认</option>
 -->
		</select> <a id="donation-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="donation-modifyBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-user_gray'">修改捐赠状态</a> <a
			id="donation-removeBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-edit_remove'">删除捐赠</a>
	</div>
</div>
<div id="donation-grid"></div>
<div id="donation-add"></div>
