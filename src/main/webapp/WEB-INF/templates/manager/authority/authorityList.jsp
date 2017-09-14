<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	(function() {
		var dg = $('#authority-grid');// 数据网格
		var search = $('#authority-search');// 搜索div
		var searchBtn = $('#authority-searchBtn');// 搜索按钮
		var resetBtn = $('#authority-resetBtn');// 重置按钮
		var addBtn = $('#authority-addBtn');// 增加按钮
		var modifyBtn = $('#authority-modifyBtn');// 修改按钮
		var removeBtn = $('#authority-removeBtn');// 删除按钮
		var allotResourceBtn = $('#authority-allotResourceBtn');// 分配资源按钮
		var form = $('#authority-fm');// 表单
		var dlg = $('#authority-dlg');// 回话框
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/authority/findAuthorityPagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [{ field : 'id', title : '选择', checkbox : true },
			        { field : 'name', title : '名称', width : 300 },
					{ field : 'displayName', title : '显示名称', width : 300 } ] ],
			// 工具栏
			toolbar : '#authority-tool' ,fit:true,fitColumns : true};
		
		$.extend($.fn.validatebox.defaults.rules, {
		    auth: {
		        validator: function(value,param){
		            return value.indexOf('AUTH_') == 0;
		        },
		        message: '权限名称必须以AUTH_开头 .'
		    }
		});

		// s:绑定点击事件

		$(searchBtn).bind('click', function() {
			var param = toObject('#authority-search');
			$('#authority-grid').datagrid('load', param);
		});

		/* 添加按钮绑定事件 */
		  $(addBtn).bind('click',function() {
	            $('#authority-dlg').dialog({
	        	    title: '增加权限',
	        	    width: 400,
	        	    height: 280,
	        	    closed: false,
	        	    cache: false,
	        	    href: '${basePath}manager/authority/addAuthorityView.do',
	        	    modal: true
	        	});
	        });

		  //修改方法
	        $(modifyBtn).bind('click', function () {
	            var row = $(dg).datagrid('getSelected');
	            var page =$(dg).datagrid('getChecked');
	            if(page.length>1){
					layer.msg('只能选择一行!');
					return;
				}
	            if (row) {
	            	console.log(row);
	                $('#authority-dlg').dialog({
	                    title: '修改权限',
	                    width: 400,
	                    height: 280,
	                    closed: false,
	                    cache: false,
	                    href: '${basePath}manager/authority/modifyAuthorityView.do?id=' + row.id,
	                    modal: true
	                });
	            } else {
	                layer.msg('必须选择一行!');
	            }
	        });
		  
		  	/* 分配资源 */
	        $(allotResourceBtn).bind(
					'click',
					function() {
						var row = $(dg).datagrid('getSelected');
						var rows = $(dg).datagrid('getChecked');
						console.log(rows);
			             if(rows.length >1){
			        	   layer.msg('只能选择一行');
			        	   return;
			             }
						if (row) {
							addEasyuiTab({ title : '分配资源',
								href : '${basePath}manager/authorityResourceRelation/index.do?authorityId=' + row.id,
								closable : true }, true);
						}else {
			                layer.msg('必须选择一行!');
			            }
					});
		
		/* 删除事件 */
		 $(removeBtn).bind('click', function() {
	            var row = $(dg).datagrid('getChecked');
	            if (row.length == 0) {
					layer.msg('必须选择一行!');
					return;
				} 
	            if (row) {
	            	var id = [];
	            	for ( var i in row) {
						id[i] = row[i].id;
					}
	            	id = $.toJSON(id);
	                $.messager.confirm('温馨提示', '确认删除此权限?', function(r) {
	                    if (r) {
	                        $.post('${basePath}manager/authority/removeAuthority.do', { id : id }, function(result) {
	                            if (result.flag == "success") {
	                                $(dg).datagrid('reload'); 
	                            } else {
	                                $.messager.show({ 
	                                title : 'Error', msg : result.errorMsg });
	                            }
	                        }, 'json');
	                    }
	                });
	            } else {
	                layer.msg('必须选择一行!');
	            }
	        });

		// 加载数据网格
		$(dg).datagrid(dgOption);
	})();
</script>
<div id="authority-tool">
	<div id="authority-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">名称:</label> <input name="name"
			class="easyui-textbox" style="height: 26px;">&nbsp;<label
			style="padding-left: 10px;">显示名称:</label> <input name="displayName"
			class="easyui-textbox" style="height: 26px;"><a
			id="authority-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<!-- <a id="authority-resetBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload"
			plain="true">重置</a> -->
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="authority-addBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-add'">增加权限</a> <a
			id="authority-modifyBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-key'">修改权限</a> <a
			id="authority-allotResourceBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-asterisk_yellow'">分配资源</a> <a
			id="authority-removeBtn" href="#" class="easyui-linkbutton"
			plain="true" data-options="iconCls:'icon-edit_remove'">删除权限</a>
	</div>
</div>
<div id="authority-grid"></div>

<div id="authority-dlg" ></div>