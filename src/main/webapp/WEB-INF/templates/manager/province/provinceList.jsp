<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    (function() {
        var dg = $('#province-grid');// 数据网格
        var rdg = $('#province-resourgrid');// 数据网格
        var search = $('#province-search');// 搜索div
        var searchBtn = $('#province-searchBtn');// 搜索按钮
        var resetBtn = $('#province-resetBtn');// 重置按钮
        var addBtn = $('#province-addBtn');// 增加按钮
        var modifyBtn = $('#province-modifyBtn');// 修改按钮
        var removeBtn = $('#province-removeBtn');// 删除按钮
        var saveBtn = $('#province-saveBtn');// 保存按钮
        var dlg = $('#province-dlg');// 回话框
        var form = $('#province-fm');// 表单
        var url;// 提交url

        // 数据网格首选项
        var dgOption = {
            pagination : true,// 分页
            singleSelect : true,// 单选
            url : '${basePath}manager/province/findProvincePagination.do',// 数据来源地址
            selectOnCheck : true,
            columns : [ [ { field : 'id', title : '选择', checkbox : true },
                          { field : 'name', title : '省份名称', width : 200 }, 
                          { field : 'code', title : '省份编码', width : 200 },
                          { field : 'abbr', title : '简称', width : 200 } ] ],
            // 工具栏
            toolbar : '#province-tool',fit:true,fitColumns : true };

        $(searchBtn).bind('click', function() {
            var param = toObject(search);
            $(dg).datagrid('load', param);
        });

        $(resetBtn).bind('click', function() {
            $(search).form('clear');
        });
        
        $(addBtn).bind('click',function() {
            $('#province-dlg').dialog({
        	    title: '增加省份',
        	    width: 500,
        	    height: 300,
        	    closed: false,
        	    cache: false,
        	    href: '${basePath}manager/province/addProvinceView.do',
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
                $('#province-dlg').dialog({
            	    title: '修改省份',
            	    width: 500,
            	    height: 300,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}manager/province/modifyProvinceView.do?provinceId=' + row.id,
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
	       $.messager.confirm('温馨提示', '确认删除此省份?', function(r) {
                  if (r) {
                      $.post('${basePath}manager/province/provinceRemove.do', { id : id }, function(result) {
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


<div id="province-tool">
    <div id="province-search" style="padding-top: 10px;">
        <label style="padding-left: 10px;">省份名称:</label> 
        <input name="name" class="easyui-textbox" style="height: 26px;" /> 
		<label style="padding-left: 10px;">省份编码:</label> 
		<input name="code" class="easyui-textbox" style="height: 26px;" /> 
		<a id="province-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	
	<div id="province-search" style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="province-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加省份</a> 
		<a id="province-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">修改省份</a>
		<a id="province-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除省份</a>
	</div>
</div>
<div id="province-grid"></div>

<div id="province-dlg"></div>