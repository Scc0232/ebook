<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	(function() {
		var dg = $('#quesionAnswerManager-grid');// 数据网格
		var search = $('#quesionAnswerManager-search');// 搜索div
		var searchBtn = $('#quesionAnswerManager-searchBtn');// 搜索按钮
		var saveBtn = $('#quesionAnswerManager-addBtn');//增加按钮
		var removeBtn = $('#quesionAnswerManager-removeBtn');//删除按钮
		var modifyBtn = $('#quesionAnswerManager-modifyBtn');//修改按钮
		var dlg = $('#quesionAnswerManager-dlg');//会话框
		var form = $('#quesionAnswerManager-fm');//表单
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
		    collapsible: true,
            pagination: true,// 分页
            singleSelect: true,// 单选
			url : '${basePath}quesionAnswerManager/findQuesionAnswerManager.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ { field : 'id', title : '选择', checkbox : true },
			        { field : 'question', title : '问题名称', width:20 },
			        { field : 'answer', title : '问题答案', width:20 },
			        { field : 'createTime', title : '创建时间', width:20, formatter : function(val) {
						return formatDate(val);
					} },
			        { field : 'updateTime', title : '修改时间', width:20, formatter : function(val) {
						return formatDate(val);
					} }
					
			         ] ],
			// 工具栏
			toolbar : '#quesionAnswerManager-tool' ,fit:true,fitColumns : true};

		
		//搜索按钮
		$(searchBtn).bind('click', function() {
			var param = toObject(search);
			console.log(param);
			$(dg).datagrid('load', param);
		});
		
		
		//添加方法
        $(saveBtn).bind('click', function () {
            $('#quesionAnswerManager-add').dialog({
                title: '增加问题',
                width: 800,
                height: 500,
                closed: false,
                cache: false,
                href: '${basePath}quesionAnswerManager/addQuesionAnswerManagerView.do',
                modal: true
            });
        });
		
        //删除按钮绑定事件
        $(removeBtn).bind('click', function() {
            var rows = $(dg).datagrid('getSelected');
            if (rows) {
                
                $.messager.confirm('温馨提示', '确认删除此问题?', function(r) {
                	if (!r) {
                        return;
                    }
                    $.ajax({
                        type : "POST",
                        url : '${basePath}quesionAnswerManager/removeQuesionAnswerManager.do?id=' + rows.id,
                        async : "false",
                        success : function(data) {
                            closeLoading();
                            if (data) {//保存成功
                              //  $(dlg).dialog('close');// 关闭回话框
                                $("#quesionAnswerManager-grid").datagrid('reload'); // 刷新数据网格
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
                $('#quesionAnswerManager-add').dialog({
                    title: '修改问题',
                    width: 800,
                    height: 500,
                    closed: false,
                    cache: false,
                    type : "GET",
                    href: '${basePath}quesionAnswerManager/modifyQuesionAnswerManagerView.do?id=' + row.id,
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


<div id="quesionAnswerManager-tool">
	<div id="quesionAnswerManager-search" style="padding-top: 10px;">
	<label style="padding-left: 10px;">问题名称:</label> <input name="question"
			class="easyui-textbox" style="height: 26px;">
		<a
			id="quesionAnswerManager-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<!-- <a
			id="organization-resetBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="quesionAnswerManager-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加问题</a> 
		<a id="quesionAnswerManager-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改问题</a>
		<a id="quesionAnswerManager-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除问题</a>
	</div>
</div>
<div id="quesionAnswerManager-grid"></div>
<div id="quesionAnswerManager-add" ></div>
