<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script>
	(function() {
		var dg = $('#styleSetting-grid');// 数据网格
		var search = $('#styleSetting-search');// 搜索div
		var searchBtn = $('#styleSetting-searchBtn');// 搜索按钮
		var saveBtn = $('#styleSetting-addBtn');//增加按钮
		var removeBtn = $('#styleSetting-removeBtn');//删除按钮
		var modifyBtn = $('#styleSetting-modifyBtn');//修改按钮
		var dlg = $('#styleSetting-dlg');//会话框x
		var form = $('#styleSetting-fm');//表单
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
		    collapsible: true,
            pagination: true,// 分页
            singleSelect: true,// 单选
			url : '${basePath}styleSetting/findStyleSettingPagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ { field : 'id', title : '选择', checkbox : true },
			        { field : 'orgName', title : '机构ID', width:8 },
			        { field : 'cssResourceUrl', title : 'css资源地址', width:20 },
			        
					
			         ] ],
			        
			// 工具栏
			toolbar : '#styleSetting-tool' ,fit:true,fitColumns : true};

		
		//搜索按钮
		$(searchBtn).bind('click', function() {
			var param = toObject(search);
			console.log(param);
			$(dg).datagrid('load', param);
		});
		
		
		//添加方法
        $(saveBtn).bind('click', function () {
            $('#styleSetting-add').dialog({
                title: '增加风格',
                width: 500,
                height: 350,
                closed: false,
                cache: false,
                href: '${basePath}styleSetting/addStyleSettingView.do',
                modal: true
            });
        });
		
        //删除按钮绑定事件
        $(removeBtn).bind('click', function() {
            var rows = $(dg).datagrid('getSelected');
            if (rows) {
                
                $.messager.confirm('温馨提示', '确认删除此风格?', function(r) {
                	if (!r) {
                        return;
                    }
                    $.ajax({
                        type : "POST",
                        url : '${basePath}styleSetting/removeStyleSetting.do?id=' + rows.id,
                        async : "false",
                        success : function(data) {
                            closeLoading();
                            if (data) {//保存成功
                              //  $(dlg).dialog('close');// 关闭回话框
                                $("#styleSetting-grid").datagrid('reload'); // 刷新数据网格
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
            
           // var selectedRows=$("#styleSetting-grid").datagrid("getSelections");
        	//$('#orgNameCombobox').combobox('setValue',selectedRows[0].id);
           
        	if (row) {
                $('#styleSetting-add').dialog({
                    title: '修改风格',
                    width: 500,
                    height: 350,
                    closed: false,
                    cache: false,
                    type : "GET",
                    href: '${basePath}styleSetting/modifyStyleSettingView.do?id=' + row.id,
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


<div id="styleSetting-tool">
	<div id="styleSetting-search" style="padding-top: 10px;">
	<label style="padding-left: 10px;">CSS资源地址:</label> <input name="cssResourceUrl"
			class="easyui-textbox" style="height: 26px;">
		<a
			id="styleSetting-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="styleSetting-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加风格</a> 
		<a id="styleSetting-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改风格</a>
		<a id="styleSetting-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除风格</a>
	</div>
</div>
<div id="styleSetting-grid"></div>
<div id="styleSetting-add" ></div>
