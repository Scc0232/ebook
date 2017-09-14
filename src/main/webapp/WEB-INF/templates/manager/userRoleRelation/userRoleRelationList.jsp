<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	(function() {
		var selectdg = $('#user-role-relation-selectdg');
		var unselectdgSearchBtn = $('#user-role-relation-unselectdg-searchBtn');
		var searchFm = $('#user-role-relation-select-search-fm');
		var selectdgSearchBtn = $('#user-role-relation-selectdg-searchBtn');
		var unsearchFm = $('#user-role-relation-unselect-search-fm');
		var unselectdg = $('#user-role-relation-unselectdg');
		var form = $('#user-role-relation-fm');
		var atsBtn = $('#user-role-relation-atsBtn');
		var rfsBtn = $('#user-role-relation-rfsBtn');
		var roleId = '${role.id}';

		var selectdgOption = { title : '已选择的用户', pagination : true,singleSelect : true,
		url : '${basePath}manager/user/getThisRoleSelected.do?roleId=' + roleId,// 数据来源地址
		columns : [ [ { checkbox : true, field : 'id' }, { field : 'username', title : '用户名', width : '45%' } ] ],
		// 工具栏
		toolbar : '#user-role-relation-select-search-fm' };

		var unselectdgOption = { title : '未选择的用户', pagination : true,singleSelect : true,
		url : '${basePath}manager/user/getThisRoleUnselected.do?roleId=' + roleId,// 数据来源地址
		columns : [ [ { checkbox : true, field : 'id' }, { field : 'username', title : '用户名', width : '45%' } ] ],
		// 工具栏
		toolbar : '#user-role-relation-unselect-search-fm' };

		$(selectdg).datagrid(selectdgOption);
		$(selectdgSearchBtn).bind('click', function() {
			var param = toObject(searchFm);
			$(selectdg).datagrid('load', param);
		});

		// 未选择的用户，搜索按钮绑定点击事件
		$(unselectdgSearchBtn).bind('click', function() {
			var param = toObject(unsearchFm);
			$(unselectdg).datagrid('load', param);
		});
		$(unselectdg).datagrid(unselectdgOption);

		$(atsBtn).bind(
				'click',
				function() {
					var selections = $(unselectdg).datagrid('getSelections');
					if (selections.length != 0) {
						loading();
						var jaUserIds = getUserIdAry(selections);
						$.post('${basePath}manager/userRoleRelation/addRelation.do', { roleId : roleId,
							jaUserIds : $.toJSON(jaUserIds) }, function(data) {
							closeLoading();
							if (data.flag === 'fail') {
								systemErrorMsg();
							} else {
								$(selectdg).datagrid('reload');
								$(unselectdg).datagrid('reload');
							}
						});
					}else{
						layer.msg("至少选择一行");
					}
				});

		$(rfsBtn).bind(
				'click',
				function() {
					var selections = $(selectdg).datagrid('getSelections');
					if (selections.length != 0) {
						loading();
						var jaUserIds = getUserIdAry(selections);
						$.post('${basePath}manager/userRoleRelation/removeRelation.do', { roleId : roleId,
							jaUserIds : $.toJSON(jaUserIds) }, function(data) {
							closeLoading();
							if (data.flag === 'fail') {
								systemErrorMsg();
							} else {
								$(selectdg).datagrid('reload');
								$(unselectdg).datagrid('reload');
							}
						});
					}else{
						layer.msg("至少选择一行");
					}
				});

		// 获得用户ID数组
		function getUserIdAry(selections) {
			var ary = [];
			for ( var i in selections) {
				ary[i] = selections[i].id;
			}
			return ary;
		}
	})();
</script>
<form id="user-role-relation-fm" method="post" novalidate>
	<h3>角色:${role.name}</h3>
	<div id="user-role-relation-selectdg" style="height: 300px;">已选择的用户</div>
	<div align="center" style="margin-top: 10px; margin-bottom: 10px;">
		<a id="user-role-relation-atsBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="width: 140px">增加到已选择</a> <a id="user-role-relation-rfsBtn"
			href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit_remove" plain="true" style="margin-left: 20px; width: 140px">从已选择中删除</a>
	</div>
	<div id="user-role-relation-unselectdg" style="height: 300px;">未选择的用户</div>
</form>
<form id="user-role-relation-select-search-fm">
	<label>用户名:</label> <input name="username" class="easyui-textbox">&nbsp;<a id="user-role-relation-selectdg-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search"
		plain="true">搜索</a> <!-- <a href="javascript:$('#user-role-relation-select-search-fm').form('clear')" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
</form>
<form id="user-role-relation-unselect-search-fm">
	<label>用户名:</label> <input name="username" class="easyui-textbox">&nbsp;<a id="user-role-relation-unselectdg-searchBtn" href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-search" plain="true">搜索</a> <!-- <a href="javascript:$('#user-role-relation-unselect-search-fm').form('clear')" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
</form>
