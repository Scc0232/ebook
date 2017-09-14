<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	(function() {
		var selectdg = $('#authority-resource-relation-selectdg');
		var unselectdgSearchBtn = $('#authority-resource-relation-unselectdg-searchBtn');
		var searchFm = $('#authority-resource-relation-select-search-fm');
		var selectdgSearchBtn = $('#authority-resource-relation-selectdg-searchBtn');
		var unsearchFm = $('#authority-resource-relation-unselect-search-fm');
		var unselectdg = $('#authority-resource-relation-unselectdg');
		var form = $('#authority-resource-relation-fm');
		var atsBtn = $('#authority-resource-relation-atsBtn');
		var rfsBtn = $('#authority-resource-relation-rfsBtn');
		var authorityId = '${authority.id}';

		var selectdgOption = {
			title : '已选择的资源',
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/resource/getThisAuthoritySelected.do?authorityId=' + authorityId,// 数据来源地址
			columns : [ [ { checkbox : true, field : 'id' }, { field : 'name', title : '名称', width : '25%' },
					{ field : 'displayName', title : '显示名称', width : '25%' },
					{ field : 'url', title : 'Url', width : '45%' } ] ],
			// 工具栏
			toolbar : '#authority-resource-relation-select-search-fm' };

		var unselectdgOption = {
			title : '未选择的资源',
			pagination : true,// 分页
			singleSelect : true,// 单选
			url : '${basePath}manager/resource/getThisAuthorityUnselected.do?authorityId=' + authorityId,// 数据来源地址
			columns : [ [ { checkbox : true, field : 'id' }, { field : 'name', title : '名称', width : '25%' },
					{ field : 'displayName', title : '显示名称', width : '25%' },
					{ field : 'url', title : 'Url', width : '45%' } ] ],
			// 工具栏
			toolbar : '#authority-resource-relation-unselect-search-fm' };

		$(selectdg).datagrid(selectdgOption);
		$(selectdgSearchBtn).bind('click', function() {
			var param = toObject(searchFm);
			console.log(param);
			$(selectdg).datagrid('load', param);
		});

		// 未选择的资源，搜索按钮绑定点击事件
		$(unselectdgSearchBtn).bind('click', function() {
			var param = toObject(unsearchFm);
			$(unselectdg).datagrid('load', param);
		});
		$(unselectdg).datagrid(unselectdgOption);
	    
		//增加
		$(atsBtn).bind(
				'click',
				function() {
					var selectrow = $(unselectdg).datagrid('getSelections');
					if (selectrow.length != 0) {
						loading();
						var jaResourceIds = getResourceIdAry(selectrow);
						$.post('${basePath}manager/authorityResourceRelation/addRelation.do', {
							authorityId : authorityId, jaResourceIds : $.toJSON(jaResourceIds) }, function(data) {
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
	    //删除
		$(rfsBtn).bind(
				'click',
				function() {
					var selections = $(selectdg).datagrid('getSelections');
					if (selections.length != 0) {
						loading();
						var jaResourceIds = getResourceIdAry(selections);
						$.post('${basePath}manager/authorityResourceRelation/removeRelation.do', {
							authorityId : authorityId, jaResourceIds : $.toJSON(jaResourceIds) }, function(data) {
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

		// 获得资源ID数组
		function getResourceIdAry(selections) {
			var ary = [];
			for ( var i in selections) {
				ary[i] = selections[i].id;
			}
			return ary;
		}
	})();
</script>
<form id="authority-resource-relation-fm" method="post" novalidate>
	<h3>权限:${authority.displayName}</h3>
	<div id="authority-resource-relation-selectdg" style="height: 300px;">已选择的资源</div>
	<div align="center" style="margin-top: 10px; margin-bottom: 10px;">
		<a id="authority-resource-relation-atsBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" style="width: 140px">增加到已选择</a> <a
			id="authority-resource-relation-rfsBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit_remove" plain="true" style="margin-left: 20px; width: 140px">从已选择中删除</a>
	</div>
	<div id="authority-resource-relation-unselectdg" style="height: 300px;">未选择的资源</div>
</form>

<form id="authority-resource-relation-select-search-fm">
	<label>资源名称:</label> <input name="name" class="easyui-textbox">&nbsp;
	<label>资源显示名称:</label> <input name="displayName" class="easyui-textbox">&nbsp;
	<label>Url:</label> <input name="url" class="easyui-textbox">&nbsp;
	<a id="authority-resource-relation-selectdg-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a> 
	<!-- <a href="javascript:$('#authority-resource-relation-select-search-fm').form('clear')" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
</form>

<form id="authority-resource-relation-unselect-search-fm">
	<label>资源名称:</label> <input name="name" class="easyui-textbox">&nbsp;
	<label>资源显示名称:</label> <input name="displayName" class="easyui-textbox">&nbsp;
	<label>Url:</label> <input name="url" class="easyui-textbox">&nbsp;
	<a id="authority-resource-relation-unselectdg-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	<!-- <a href="javascript:$('#authority-resource-relation-unselect-search-fm').form('clear')" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
</form>
