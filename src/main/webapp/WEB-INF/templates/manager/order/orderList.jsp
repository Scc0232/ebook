<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	(function() {
		var dg = $('#order-grid');// 数据网格
		var search = $('#order-search');// 搜索div
		var searchBtn = $('#order-searchBtn');// 搜索按钮
		var saveBtn = $('#order-addBtn');//增加按钮
		var removeBtn = $('#order-removeBtn');//删除按钮
		var modifyBtn = $('#order-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/order/findOrderPagination.do',// 数据来源订单
			selectOnCheck : true,
			columns : [ [ {
				field : 'id',
				title : '选择',
				checkbox : true
			}, {
				field : 'orderNo',
				title : '订单号',
				width : '150px'
			}, {
				field : 'username',
				title : '姓名',
				width : '120px'
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
				field : 'orderStatus',
				title : '订单状态',
				width : '60px',
				formatter : function(val) {
					if (val == 0) {
						return '未支付';
					} else if (val == 1) {
						return '已支付';
					}else if (val == 2) {
						return '已取消';
					}else if (val == 3) {
						return '已预订';
					}else if (val == 4) {
						return '测试';
					}
				}
			}, {
				field : 'productType',
				title : '商品类型',
				width : '60px',
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
				width : '60px'
			},   {
				field : 'value',
				title : '订单总价格',
				width : '70px'
			}, {
				field : 'preValue',
				title : '订单预订价格',
				width : '70px'
			},  {
				field : 'payEvalue',
				title : '订单支付E币数量',
				width : '100px'
			}, {
				field : 'count',
				title : '商品数量',
				width : '100px'
			}, {
				field : 'createTime',
				title : '创建时间',
				width : '150px',
				formatter : function(val) {
					return formatDate(val);
				}
			} ] ],
			// 工具栏
			toolbar : '#order-tool',
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
			$('#order-add').dialog({
				title : '增加订单',
				width : 600,
				height : 520,
				closed : false,
				cache : false,
				href : '${basePath}manager/order/addOrderView.do',
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
												'确认删除此订单?',
												function(r) {
													if (!r) {
														return;
													}
													$
															.ajax({
																type : "POST",
																url : '${basePath}manager/order/removeOrder.do?id='
																		+ rows.id,
																async : "false",
																success : function(
																		data) {
																	closeLoading();
																	if (data) {//保存成功
																		//  $(dlg).dialog('close');// 关闭回话框
																		$(
																				"#order-grid")
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
								$('#order-add')
										.dialog(
												{
													title : '修改订单',
													width : 600,
													height : 220,
													closed : false,
													cache : false,
													type : "GET",
													href : '${basePath}manager/order/modifyOrderView.do?orderid='
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

<div id="order-tool">
	<div id="order-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">订单号:</label> <input
			name="orderNo" class="easyui-textbox" style="height: 26px;">
		<label style="padding-left: 10px;">商品名称:</label> <input
			name="productName" class="easyui-textbox" style="height: 26px;">
		<label style="padding-left: 10px;">订单状态: <select
			class="easyui-combobox" name="orderStatus" style="width: 74px;"
			editable="false" panelHeight="auto">
				<option value=></option>
				<option value=></option>
				<option value=" 0">未支付</option>
				<option value=" 1">已支付</option>
				<option value=" 2">取消</option>
				<option value=" 3">已预订</option>
<!-- 				<option value=" 4">测试</option>
 -->		</select> <a id="order-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="order-modifyBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-user_gray'">修改订单状态</a> <a
			id="order-removeBtn" href="#" class="easyui-linkbutton" plain="true"
			data-options="iconCls:'icon-edit_remove'">删除订单</a>
	</div>
</div>
<div id="order-grid"></div>
<div id="order-add"></div>
