<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script>
	(function() {
		var dg = $('#resource-grid');// 数据网格
		var search = $('#resource-search');// 搜索div
		var searchBtn = $('#resource-searchBtn');// 搜索按钮
		var saveBtn = $('#resource-addBtn');//增加按钮
		var removeBtn = $('#resource-removeBtn');//删除按钮
		var modifyBtn = $('#resource-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
		    collapsible: true,
            pagination: true,// 分页
            singleSelect: true,// 单选
			url : '${basePath}manager/resource/findResourcePagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ 
			            { field : 'id', title : '选择', checkbox : true },
				        { field : 'name', title : '资源简称',width:'20%' },
				        { field : 'displayName', title : '资源名',width:'20%' },
				        { field : 'url', title : 'URL',width:'20%' },
				        { field : 'createTime', title : '创建时间', formatter : function(val) {
	                        return formatDate(val);
	                    } },
	                    { field : 'remark', title : '备注',width:'20%' }
			        ] ],
			// 工具栏
			toolbar : '#resource-tool' ,fit:true,fitColumns : true};

		$(searchBtn).bind('click', function() {
			var param = toObject(search);
			console.log(param);
			$(dg).datagrid('load', param);
		});
		
		//添加方法
        $(saveBtn).bind('click', function () {
            $('#resource-add').dialog({
                title: '增加资源',
                width: 400,
                height: 350,
                closed: false,
                cache: false,
                href: '${basePath}manager/resource/addResourceView.do',
                modal: true
            });
        });
		
        //删除按钮绑定事件
        $(removeBtn).bind('click', function() {
            var rows = $(dg).datagrid('getSelected');
            if (rows) {
                
                $.messager.confirm('温馨提示', '确认删除此资源?', function(r) {
                    if (!r) {
                        return;
                    }
                    $.ajax({
                        type : "POST",
                        url : '${basePath}manager/resource/removeResource.do?id=' + rows.id,
                        async : "false",
                        success : function(data) {
                            closeLoading();
                            if (data) {//删除成功
                                $(dg).datagrid('reload'); // 刷新数据网格
                            }
                        }
                    });
                });
            }else {
                layer.msg('必须选择一行!');
                return;
            }
        });
		
        //修改方法
        $(modifyBtn).bind('click', function () {
            var row = $(dg).datagrid('getSelected');
            if (row) {
                $('#resource-add').dialog({
                    title: '修改资源',
                    width: 400,
                    height: 350,
                    closed: false,
                    cache: false,
                    type : "POST",
                    href: '${basePath}manager/resource/modifyResourceView.do?id=' + row.id,
                    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
		
		// 加载数据网格
		$(dg).datagrid(dgOption);
	})();
</script>

<div id="resource-tool">
	<div id="resource-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">资源名:</label> <input name="displayName"
			class="easyui-textbox" style="height: 26px;"><a
			id="resource-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="resource-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加资源</a> 
		<a id="resource-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改资源</a>
		<a id="resource-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除资源</a>
	</div>
</div>
<div id="resource-grid"></div>
<div id="resource-add" ></div>
