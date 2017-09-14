<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    (function() {
        var dg = $('#area-grid');// 数据网格
        var rdg = $('#area-resourgrid');// 数据网格
        var search = $('#area-search');// 搜索div
        var searchBtn = $('#area-searchBtn');// 搜索按钮
        var resetBtn = $('#area-resetBtn');// 重置按钮
        var addBtn = $('#area-addBtn');// 增加按钮
        var modifyBtn = $('#area-modifyBtn');// 修改按钮
        var removeBtn = $('#area-removeBtn');// 删除按钮
        var saveBtn = $('#area-saveBtn');// 保存按钮
        var dlg = $('#area-dlg');// 回话框
        var form = $('#area-fm');// 表单
        var url;// 提交url

        // 数据网格首选项
        var dgOption = {
            pagination : true,// 分页
            singleSelect : true,// 单选
            url : '${basePath}manager/area/findAreaPagination.do',// 数据来源地址
            selectOnCheck : true,
            columns : [ [ { field : 'id', title : '选择', checkbox : true },
                          { field : 'name', title : '区域名称', width : 200 }, 
                          { field : 'code', title : '区域编码', width : 200 },
                          { field : 'cityCode', title : '城市编码', width : 200 },
                          { field : 'abbr', title : '简称', width : 200 } ] ],
            // 工具栏
            toolbar : '#area-tool',fit:true,fitColumns : true };

        $(searchBtn).bind('click', function() {
            var param = toObject(search);
            $(dg).datagrid('load', param);
        });

        $(resetBtn).bind('click', function() {
            $(search).form('clear');
        });
        
        $(addBtn).bind('click',function() {
            $('#area-dlg').dialog({
        	    title: '增加区域',
        	    width: 500,
        	    height: 300,
        	    closed: false,
        	    cache: false,
        	    href: '${basePath}manager/area/addAreaView.do',
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
                $('#area-dlg').dialog({
            	    title: '修改区域',
            	    width: 500,
            	    height: 300,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}manager/area/modifyAreaView.do?areaId=' + row.id,
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
	       $.messager.confirm('温馨提示', '确认删除此区域?', function(r) {
                  if (r) {
                      $.post('${basePath}manager/area/areaRemove.do', { id : id }, function(result) {
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


<div id="area-tool">
    <div id="area-search" style="padding-top: 10px;">
        <label style="padding-left: 10px;">区域名称:</label> 
        <input name="name" class="easyui-textbox" style="height: 26px;" /> 
		<label style="padding-left: 10px;">区域编码:</label> 
		<input name="code" class="easyui-textbox" style="height: 26px;" /> 
		<a id="area-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	
	<div id="area-search" style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="area-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加区域</a> 
		<a id="area-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">修改区域</a>
		<a id="area-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除区域</a>
	</div>
</div>
<div id="area-grid"></div>

<div id="area-dlg"></div>