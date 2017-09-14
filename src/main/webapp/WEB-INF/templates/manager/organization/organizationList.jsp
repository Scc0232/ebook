<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	(function() {
		var dg = $('#organization-grid');// 数据网格
		var search = $('#organization-search');// 搜索div
		var searchBtn = $('#organization-searchBtn');// 搜索按钮
		var saveBtn = $('#organization-addBtn');//增加按钮
		var removeBtn = $('#organization-removeBtn');//删除按钮
		var modifyBtn = $('#organization-modifyBtn');//修改按钮
		var dlg = $('#organization-dlg');//会话框
		var form = $('#organization-fm');//表单
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
		    collapsible: true,
            pagination: true,// 分页
            singleSelect: true,// 单选
			url : '${basePath}organization/findOrganizationPagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ { field : 'id', title : '选择', checkbox : true },
			        { field : 'orgName', title : '机构名称', width:20 },
			        { field : 'orgType', title : '机构类型', width:20 },
			        { field : 'createTime', title : '创建时间', width:20, formatter : function(val) {
						return formatDate(val);
					} },
			        { field : 'modifyTime', title : '修改时间', width:20, formatter : function(val) {
						return formatDate(val);
					} }
					
			         ] ],
			// 工具栏
			toolbar : '#organization-tool' ,fit:true,fitColumns : true};

		
		//搜索按钮
		$(searchBtn).bind('click', function() {
			var param = toObject(search);
			console.log(param);
			$(dg).datagrid('load', param);
		});
		
		
		//添加方法
        $(saveBtn).bind('click', function () {
            $('#organization-add').dialog({
                title: '增加机构',
                width: 500,
                height: 350,
                closed: false,
                cache: false,
                href: '${basePath}organization/addOrganizationView.do',
                modal: true
            });
        });
		
        //删除按钮绑定事件
        $(removeBtn).bind('click', function() {
            var rows = $(dg).datagrid('getSelected');
            if (rows) {
                
                $.messager.confirm('温馨提示', '确认删除此机构?', function(r) {
                	if (!r) {
                        return;
                    }
                    $.ajax({
                        type : "POST",
                        url : '${basePath}organization/removeOrganization.do?id=' + rows.id,
                        async : "false",
                        success : function(data) {
                            closeLoading();
                            if (data) {//保存成功
                              //  $(dlg).dialog('close');// 关闭回话框
                                $("#organization-grid").datagrid('reload'); // 刷新数据网格
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
                $('#organization-add').dialog({
                    title: '修改机构',
                    width: 500,
                    height: 350,
                    closed: false,
                    cache: false,
                    type : "GET",
                    href: '${basePath}organization/modifyOrganizationView.do?id=' + row.id,
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


<div id="organization-tool">
	<div id="organization-search" style="padding-top: 10px;">
	<label style="padding-left: 10px;">机构名称:</label> <input name="orgName"
			class="easyui-textbox" style="height: 26px;">
		<a
			id="organization-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<!-- <a
			id="organization-resetBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="organization-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加机构</a> 
		<a id="organization-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改机构</a>
		<a id="organization-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除机构</a>
	</div>
</div>
<div id="organization-grid"></div>
<div id="organization-add" ></div>
