<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#shoppingCart-grid');// 数据网格
		var search = $('#shoppingCart-search');// 搜索div
		var searchBtn = $('#shoppingCart-searchBtn');// 搜索按钮
		var saveBtn = $('#shoppingCart-addBtn');//增加按钮
		var removeBtn = $('#shoppingCart-removeBtn');//删除按钮
		var modifyBtn = $('#shoppingCart-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/shoppingCart/findShoppingCartPagination.do',// 数据来源购物车
			selectOnCheck : true,
			columns : [ [ {
				field : 'id',
				title : '选择',
				checkbox : true
			}, {
				field : 'username',
				title : '姓名',
				width : '160px'
			},{
				field : 'productType',
				title : '商品类型',
				width : '120px',
				formatter : function(val) {
					if (val == 0) {
						return '纪念品';
					} else {
						return '图书';
					}
				}
			}, {
				field : 'productName',
				title : '商品名称',
				width : '160px'
			}, {
				field : 'productPrice',
				title : '商品价格',
				width : '160px'
			}, {
				field : 'count',
				title : '数量',
				width : '160px'
			},  {
				field : 'createTime',
				title : '创建时间',
				width : '150px',
				formatter : function(val) {
					return formatDate(val);
				}
			} ] ],
			// 工具栏
			toolbar : '#shoppingCart-tool',
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
			$('#shoppingCart-add').dialog({
				title : '增加购物车',
				width : 600,
				height : 520,
				closed : false,
				cache : false,
				href : '${basePath}manager/shoppingCart/addShoppingCartView.do',
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
												'确认删除此购物车?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/shoppingCart/removeShoppingCart.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#shoppingCart-grid")
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
								$('#shoppingCart-add')
										.dialog(
												{
													title : '修改购物车',
													width : 600,
													height : 220,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/shoppingCart/modifyShoppingCartView.do?shoppingCartid='
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

<div id="shoppingCart-tool">
	<div id="shoppingCart-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">商品名称:</label> <input name="productName"
			class="easyui-textbox" style="height: 26px;"> <label style="padding-left: 10px;">商品类型:
			<select  class="easyui-combobox" name="productType" style="width:74px;" editable="false" panelHeight="auto">
			        	<option value=></option>
			        	<option value=></option>
			        	<option value=" 0" >纪念品</option>
			        	<option value=" 1" >图书</option>
		   </select>
			<a id="shoppingCart-searchBtn" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="shoppingCart-modifyBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-user_gray'">修改商品数量</a> <a
			id="shoppingCart-removeBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-edit_remove'">删除购物车</a>
	</div>
</div>
<div id="shoppingCart-grid"></div>
<div id="shoppingCart-add"></div>
