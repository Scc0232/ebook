<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	(function() {
		var dg = $('#role-grid');// 数据网格
		var search = $('#role-search');// 搜索div
		var searchBtn = $('#role-searchBtn');// 搜索按钮
		var resetBtn = $('#role-resetBtn');// 重置按钮
		var addBtn = $('#role-addBtn');// 增加按钮
		var modifyBtn = $('#role-modifyBtn');// 修改按钮
		var removeBtn = $('#role-removeBtn');// 删除按钮
		var saveBtn = $('#role-saveBtn');// 保存按钮
		var dlg = $('#role-dlg');// 回话框
		var form = $('#role-fm');// 表单
		var allotAuthBtn = $('#role-allotAuthBtn');// 分配权限按钮
		var allotUserBtn = $('#role-allotUserBtn');// 分配用户按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/role/findRolePagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ { field : 'id', title : '选择', checkbox : true },
			        { field : 'name', title : '角色名称', width : 200 },
					{ field : 'isValid', title : '有效值', width : 200 } ] ],
			// 工具栏
			toolbar : '#role-tool',fit:true,fitColumns : true };

		// s:绑定点击事件

		$(searchBtn).bind('click', function() {
			var param = toObject(search);
			$(dg).datagrid('load', param);
		});

		/* 添加按钮绑定事件 */
		  $(addBtn).bind('click',function() {
	            $('#role-dlg').dialog({
	        	    title: '增加角色',
	        	    width: 400,
	        	    height: 180,
	        	    closed: false,
	        	    cache: false,
	        	    href: '${basePath}manager/role/addRoleView.do',
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
                $('#role-dlg').dialog({
                    title: '修改角色',
                    width: 400,
                    height: 280,
                    closed: false,
                    cache: false,
                    href: '${basePath}manager/role/modifyRoleView.do?id=' + row.id,
                    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });


		/* 删除方法 */
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
	                $.messager.confirm('温馨提示', '确认删除此角色?', function(r) {
	                    if (r) {
	                        $.post('${basePath}manager/role/removeRole.do', { id : id }, function(result) {
	                            //console.log(result);
	                            if (result.flag == "success") {
	                                $(dg).datagrid('reload'); // reload the user data
	                            } else {
	                                $.messager.show({ // show error message
	                                title : 'Error', msg : result.errorMsg });
	                            }
	                        }, 'json');
	                    }
	                });
	            } else {
	                layer.msg('必须选择一行!');
	            }
	        });
		/* 分配权限 */
		 $(allotAuthBtn).bind(
				'click',
				function() {
					var row = $(dg).datagrid('getSelected');
					var rows = $(dg).datagrid('getChecked');
			           if(rows.length >1){
			        	   layer.msg('只能选择一行');
			        	   return;
			           }
					if (row) {
						addEasyuiTab({ title : '分配权限',
							href : '${basePath}manager/authorityRoleRelation/index.do?roleId=' + row.id,
							closable : true }, true);
					}else {
		                layer.msg('必须选择一行!');
		            }
				}); 
		
		/* 分配用户 */
		 $(allotUserBtn).bind(
				'click',
				function() {
					var row = $(dg).datagrid('getSelected');
					var rows = $(dg).datagrid('getChecked');
			           if(rows.length >1){
			        	   layer.msg('只能选择一行');
			        	   return;
			           }
					if (row) {
						addEasyuiTab({ title : '分配用户',
							href : '${basePath}manager/userRoleRelation/index.do?roleId=' + row.id, closable : true },
								true);
					}else {
		                layer.msg('必须选择一行!');
		            }
				}); 
		// 加载数据网格
		$(dg).datagrid(dgOption);
	})();
</script>
<div id="role-tool">
	<div id="role-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">角色名称:</label> 
		<input name="name" class="easyui-textbox" style="height: 26px;">
		<a id="role-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="role-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加角色</a> 
		<a id="role-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user'">修改角色</a> 
		<a id="role-allotAuthBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-key'">分配权限</a>
		<a id="role-allotUserBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">分配用户</a>
		<a id="role-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除角色</a>
	</div>
</div>
<div id="role-grid"></div>

<div id="role-dlg"></div>
