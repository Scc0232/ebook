<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    (function() {
        var dg = $('#industry-grid');// 数据网格
        var rdg = $('#industry-resourgrid');// 数据网格
        var search = $('#industry-search');// 搜索div
        var searchBtn = $('#industry-searchBtn');// 搜索按钮
     	// 增加按钮
        var removeBtn = $('#industry-removeBtn');// 删除按钮
        var modifyBtn = $('#industry-modifyBtn');// 修改按钮
        var dlg = $('#industry-dlg');// 回话框
        var form = $('#industry-fm');// 表单
        var url;// 提交url

        // 数据网格首选项
        var dgOption = {
            pagination : true,// 分页
            singleSelect : true,// 单选
            url : '${basePath}industry/findIndustryPagination.do',// 数据来源地址
            selectOnCheck : true,
            columns : [ [ { field : 'id', title : '选择', checkbox : true },
                          { field : 'sectorName', title : '行业名称', width : 200 }, 
                          { field : 'pname', title : '父行业名称', width:200 }, 
                          { field : 'createTime', title : '创建时间', width:200,formatter : function(val) {
      						return formatDate(val);} } ,
      						{ field : 'modifyTime', title : '修改时间',width:200, formatter : function(val) {
      							return formatDate(val);} } ,
                           ] ],
            // 工具栏
            toolbar : '#industry-tool',fit:true,fitColumns : true };
		
        //收索按钮
        $(searchBtn).bind('click', function() {
            var param = toObject(search);
            $(dg).datagrid('load', param);
        });
        
      //增加按钮
		$('#industry-addBtn').bind('click',function() {
            $('#industry-add').dialog({
        	    title: '添加行业',
        	    width: 500,
        	    height: 300,
        	    closed: false,
        	    cache: false,
        	    href: '${basePath}industry/addIndustryView.do',
        	    modal: true
        	});
        });
        
		//详情按钮
		$("#industry-detailBtn").bind('click',function() {
			
			var row = $(dg).datagrid('getChecked');
            if (row.length == 0) {
				layer.msg('必须选择一行!');
				return;
			} else if (row.length > 1) {
				layer.msg('最多只能选择一行!');
				return;
			}
			var id = row[0].id;
			
            $('#industry-add').dialog({
        	    title: '行业详情',
        	    width: 1050,
        	    height: 640,
        	    closed: false,
        	    cache: false,
        	    href: '${basePath}industry/industryDetail.do?id='+id,
        	    modal: true
        	});
        });
      
		//修改按钮
		$("#industry-modifyBtn").bind('click',function() {
			
			var row = $(dg).datagrid('getChecked');
            if (row.length == 0) {
				layer.msg('必须选择一行!');
				return;
			} else if (row.length > 1) {
				layer.msg('最多只能选择一行!');
				return;
			}
			var id = row[0].id;
			
			$('#industry-add').dialog({
				title: '修改行业',
        	    width: 500,
        	    height: 300,
        	    closed: false,
        	    cache: false,
        	    href: '${basePath}industry/modifyIndustryView.do?id='+id,
        	    modal: true
        	});
        });
		
		
        
        //删除按钮
        $('#industry-removeBtn').bind('click', function() {
            var row = $(dg).datagrid('getChecked');
            if (row.length == 0) {
				layer.msg('必须选择一行!');
				return;
			} else if (row.length > 1) {
				layer.msg('最多只能选择一行!');
				return;
			}
			var id = row[0].id;
	       $.messager.confirm('温馨提示', '确认删除此行业?', function(r) {
                  if (r) {
                      $.post('${basePath}industry/removeIndustry.do', { id : id }, function(result) {
                          if (result.flag == "success") {
                              $(dg).datagrid('reload'); // reload the user data
                          } else {
                              $.messager.show({ // show error message
                              title : 'Error', msg : result.errorMsg });
                          }
                      }, 'json');
                  }
              });
        });
        
        // 加载数据网格
        $(dg).datagrid(dgOption);
    })();
</script>


<div id="industry-tool">
    <div id="industry-search" style="padding-top: 10px;">
		<label style="padding-left: 10px;">行业名称:</label> 
		<input name="sectorName" class="easyui-textbox" style="height: 26px;" /> 
		<a id="industry-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	
	<div id="industry-search" style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="industry-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加行业</a> 
		<a id="industry-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">修改行业</a> 
		<a id="industry-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除行业</a>
		<!-- <a id="industry-detailBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-application_view_detail'">行业详细</a> -->
	</div>
</div>

<div id="industry-grid"></div>
<div id="industry-add"></div>