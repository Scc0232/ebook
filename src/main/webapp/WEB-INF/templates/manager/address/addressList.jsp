<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#address-grid');// 数据网格
		var search = $('#address-search');// 搜索div
		var searchBtn = $('#address-searchBtn');// 搜索按钮
		var saveBtn = $('#address-addBtn');//增加按钮
		var removeBtn = $('#address-removeBtn');//删除按钮
		var modifyBtn = $('#address-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/address/findAddressPagination.do',// 数据来源地址
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
				field : 'phone',
				title : '手机号',
				width : '160px'
			}, {
				field : 'college',
				title : '校区',
				width : '160px'
			}, {
				field : 'flat',
				title : '宿舍楼',
				width : '160px'
			}, {
				field : 'roomNum',
				title : '房间号',
				width : '120px'
			}, {
				field : 'isDefault',
				title : '默认地址',
				width : '120px',
				formatter : function(val) {
					if (val == 1) {
						return '是';
					} else {
						return '';
					}
				}
			}, {
				field : 'createTime',
				title : '创建时间',
				width : '150px',
				formatter : function(val) {
					return formatDate(val);
				}
			} ] ],
			// 工具栏
			toolbar : '#address-tool',
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
			$('#address-add').dialog({
				title : '增加地址',
				width : 600,
				height : 520,
				closed : false,
				cache : false,
				href : '${basePath}manager/address/addAddressView.do',
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
												'确认删除此地址?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/address/removeAddress.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#address-grid")
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
								$('#address-add')
										.dialog(
												{
													title : '修改地址',
													width : 600,
													height : 520,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/address/modifyAddressView.do?addressid='
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

<div id="address-tool">
	<div id="address-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">姓名:</label> <input name="username"
			class="easyui-textbox" style="height: 26px;"> <label
			style="padding-left: 10px;">手机号:</label> <input name="phone"
			class="easyui-textbox" style="height: 26px;"> <label
			style="padding-left: 10px;">校区: </label><input name=college
			class="easyui-textbox" style="height: 26px;">
			<a id="address-searchBtn" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="address-modifyBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-user_gray'">修改地址</a> <a
			id="address-removeBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-edit_remove'">删除地址</a>
	</div>
</div>
<div id="address-grid"></div>
<div id="address-add"></div>
