<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    (function() {
        var dg = $('#dict-grid');// 数据网格
        var rdg = $('#dict-resourgrid');// 数据网格
        var search = $('#dict-search');// 搜索div
        var searchBtn = $('#dict-searchBtn');// 搜索按钮
        var resetBtn = $('#dict-resetBtn');// 重置按钮
        var addBtn = $('#dict-addBtn');// 增加按钮
        var modifyBtn = $('#dict-modifyBtn');// 修改按钮
        var removeBtn = $('#dict-removeBtn');// 删除按钮
        var saveBtn = $('#dict-saveBtn');// 保存按钮
        var dlg = $('#dict-dlg');// 回话框
        var form = $('#dict-fm');// 表单
        var url;// 提交url

        // 数据网格首选项
        var dgOption = {
            pagination : true,// 分页
            singleSelect : true,// 单选
            url : '${basePath}manager/dict/findDictPagination.do',// 数据来源地址
            selectOnCheck : true,
            columns : [ [ { field : 'id', title : '选择', checkbox : true },
                          { field : 'dictName', title : '字典名称', width : 200 }, 
                          { field : 'dictCode', title : '字典编码', width : 200 },
                          { field : 'dictType', title : '字典类型', width : 200 },
                          { field : 'dictDescribe', title : '字典备注', width : 200 },
                          { field : 'icon', title : '菜单图片URL', width : 200 } ] ],
            // 工具栏
            toolbar : '#dict-tool',fit:true,fitColumns : true };

        $(searchBtn).bind('click', function() {
            var param = toObject(search);
            $(dg).datagrid('load', param);
        });

        $(resetBtn).bind('click', function() {
            $(search).form('clear');
        });
        
        $(addBtn).bind('click',function() {
            $('#dict-dlg').dialog({
        	    title: '增加字典',
        	    width: 500,
        	    height: 300,
        	    closed: false,
        	    cache: false,
        	    href: '${basePath}manager/dict/addDictView.do',
        	    modal: true
        	});
        });

        $(modifyBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#dict-dlg').dialog({
            	    title: '修改字典',
            	    width: 500,
            	    height: 300,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}manager/dict/modifyDictView.do?dictId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
      
        $(removeBtn).bind('click', function() {
            var row = $(dg).datagrid('getChecked');
            if (row.length == 0) {
				layer.msg('必须选择一行!');
				return;
			} else if (row.length > 1) {
				layer.msg('最多只能选择一行!');
				return;
			}
			var id = row[0].id;
	       $.messager.confirm('温馨提示', '确认删除此菜单?', function(r) {
                  if (r) {
                      $.post('${basePath}manager/dict/dictRemove.do', { id : id }, function(result) {
                          if (result.flag == "success") {
                              $(dg).datagrid('reload'); 
                              layer.msg('删除成功！');
                          } else {
                              $.messager.show({ 
                              title : 'Error', msg : result.errorMsg });
                          }
                      }, 'json');
                  }
              });
        });

        $(saveBtn).bind('click', function() {
           
        });
        
        // 加载数据网格
        $(dg).datagrid(dgOption);
    })();
</script>


<div id="dict-tool">
    <div id="dict-search" style="padding-top: 10px;">
        <label style="padding-left: 10px;">字典名称:</label> 
        <input name="dictName" class="easyui-textbox" style="height: 26px;" /> 
		<label style="padding-left: 10px;">字典类型:</label> 
		<input name="dictType" class="easyui-textbox" style="height: 26px;" /> 
		<a id="dict-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	
	<div id="dict-search" style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="dict-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加字典</a> 
		<a id="dict-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">修改字典</a>
		<a id="dict-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除字典</a>
	</div>
</div>
<div id="dict-grid"></div>

<div id="dict-dlg"></div>