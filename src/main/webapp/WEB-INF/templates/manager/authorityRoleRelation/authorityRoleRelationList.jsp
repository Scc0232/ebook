<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	(function() {
		var selectdg = $('#authority-role-relation-selectdg');
		var unselectdgSearchBtn = $('#authority-role-relation-unselectdg-searchBtn');
		var searchFm = $('#authority-role-relation-select-search-fm');
		var selectdgSearchBtn = $('#authority-role-relation-selectdg-searchBtn');
		var unsearchFm = $('#authority-role-relation-unselect-search-fm');
		var unselectdg = $('#authority-role-relation-unselectdg');
		var form = $('#authority-role-relation-fm');
		var atsBtn = $('#authority-role-relation-atsBtn');
		var rfsBtn = $('#authority-role-relation-rfsBtn');
		var roleId = '${role.id}';

		var selectdgOption = {
			title : '已选择的权限',
			pagination : true,// 分页
			singleSelect : true,
			url : '${basePath}manager/authority/getThisRoleSelected.do?roleId=' + roleId,// 数据来源地址
			columns : [ [ { checkbox : true, field : 'id' }, { field : 'name', title : '名称', width : '45%' },
					{ field : 'displayName', title : '显示名称', width : '50%' } ] ],
			// 工具栏
			toolbar : '#authority-role-relation-select-search-fm' };

		var unselectdgOption = {
			title : '未选择的权限',
			pagination : true,singleSelect : true,
			url : '${basePath}manager/authority/getThisRoleUnselected.do?roleId=' + roleId,// 数据来源地址
			columns : [ [ { checkbox : true, field : 'id' }, { field : 'name', title : '名称', width : '45%' },
					{ field : 'displayName', title : '显示名称', width : '50%' } ] ],
			// 工具栏
			toolbar : '#authority-role-relation-unselect-search-fm' };

		$(selectdg).datagrid(selectdgOption);
		$(selectdgSearchBtn).bind('click', function() {
			var param = toObject(searchFm);
			$(selectdg).datagrid('load', param);
		});
		
		// 未选择的权限，搜索按钮绑定点击事件
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
						var jaAuthorityIds = getAuthorityIdAry(selections);
						$.post('${basePath}manager/authorityRoleRelation/addRelation.do', { roleId : roleId,
							jaAuthorityIds : $.toJSON(jaAuthorityIds) }, function(data) {
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
						var jaAuthorityIds = getAuthorityIdAry(selections);
						$.post('${basePath}manager/authorityRoleRelation/removeRelation.do', { roleId : roleId,
							jaAuthorityIds : $.toJSON(jaAuthorityIds) }, function(data) {
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

		// 获得权限ID数组
		function getAuthorityIdAry(selections) {
			var ary = [];
			for ( var i in selections) {
				ary[i] = selections[i].id;
			}
			return ary;
		}
	})();
</script>
<form id="authority-role-relation-fm" method="post" novalidate>
	<h3>角色:${role.name}</h3>
	<div id="authority-role-relation-selectdg" style="height: 300px;">已选择的权限</div>
	<div align="center" style="margin-top: 10px; margin-bottom: 10px;">
		<a id="authority-role-relation-atsBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="width: 140px">增加到已选择</a> <a id="authority-role-relation-rfsBtn"
			href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit_remove" plain="true" style="margin-left: 20px; width: 140px">从已选择中删除</a>
	</div>
	<div id="authority-role-relation-unselectdg" style="height: 300px;">未选择的权限</div>
</form>
<form id="authority-role-relation-select-search-fm">
	<label>权限名称:</label> <input name="name" class="easyui-textbox">&nbsp;
	<label>权限显示名称:</label> <input name="displayName" class="easyui-textbox">&nbsp;
	<a id="authority-role-relation-selectdg-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	<!-- <a href="javascript:$('#authority-role-relation-select-search-fm').form('clear')" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
</form>
<form id="authority-role-relation-unselect-search-fm">
	<label>权限名称:</label> <input name="name" class="easyui-textbox">&nbsp; 
	<label>权限显示名称:</label> <input name="displayName" class="easyui-textbox">&nbsp;
	<a id="authority-role-relation-unselectdg-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	<!-- <a href="javascript:$('#authority-role-relation-unselect-search-fm').form('clear')" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
</form>
