<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    (function() {
        var dg = $('#menu-grid');// 数据网格
        var rdg = $('#menu-resourgrid');// 数据网格
        var search = $('#menu-search');// 搜索div
        var searchRD = $('#menu-searchRD');// 搜索资源div
        var searchBtn = $('#menu-searchBtn');// 搜索按钮
        var searchResource = $('#menu-searchResource');// 搜索资源信息按钮
        
     	// 增加按钮
        var addBtn = $('#menu-addBtn');			
        var removeBtn = $('#menu-removeBtn');// 删除按钮
        var modifyBtn = $('#menu-modifyBtn');// 修改按钮
        var dlg = $('#menu-dlg');// 回话框
        var form = $('#menu-fm');// 表单
        var url;// 提交url

        // 数据网格首选项
        var dgOption = {
            pagination : true,// 分页
            singleSelect : true,// 单选
            url : '${basePath}manager/menu/findMenuPagination.do',// 数据来源地址
            selectOnCheck : true,
            columns : [ [ { field : 'id', title : '选择', checkbox : true },
                          { field : 'name', title : '菜单名称', width : 200 }, 
                          { field : 'icon', title : '菜单图标', width : 200 },
                          { field : 'pname', title : '父菜单', width : 200 } ] ],
            // 工具栏
            toolbar : '#menu-tool',fit:true,fitColumns : true };
		
        //新增页面
        $(addBtn).bind('click',function() {
            $('#menu-dlg').dialog({
        	    title: '增加菜单',
        	    width: 500,
        	    height: 300,
        	    closed: false,
        	    cache: false,
        	    href: '${basePath}manager/menu/addMenuView.do',
        	    modal: true
        	});
        });
        
        $(searchBtn).bind('click', function() {
            var param = toObject(search);
            $(dg).datagrid('load', param);
        });
        
        $(searchResource).bind('click', function() {
            //console.log('search');
            var param = toObject(searchRD);
            var g = $(rdg).combogrid('grid');
            g.datagrid('load', param);
        });
        
        /* $(document).ready(function() {
           //查询父菜单ID为空的所有菜单信息
            $('#parentid').combobox({ 
                url : '${basePath}manager/menu/findMenuPagination.do', 
                valueField : 'id', textField : 'name' });
        }); */
        
        $(modifyBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                var id = row.id;
                isParent(id);
                renovation(id);
                $("#menu-fm").form('clear');
                $('#menu-dlg').dialog({
            	    title: '修改菜单',
            	    width: 500,
            	    height: 300,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}manager/menu/modifyMenuView.do?menuId=' + row.id,
            	    modal: true
            	});
            } else {
                layer.msg('必须选择一行!');
            }
        });

        function renovation(id) {
            $.ajax({ type : "post", async : false, //同步执行  
            	url : "${basePath}manager/menu/queryAllMenu.do", data : { id : id }, dataType : "json", //返回数据形式为json  
            	success : function(result) {
                //console.log(result);
                $("#parentid").combobox("loadData", result);
            } })
        }
        
        function isParent(id) {
            $.ajax({ type : "post", async : false, url : "${basePath}manager/menu/isParentMenu.do", data : { id : id },
                dataType : "json", success : function(Parent) {
                    //console.log(Parent);
                    if (Parent == true) {
                        $("#parentMenu").hide();
                    } else {
                        $("#parentMenu").show();
                    }
                } 
           	})
        }
        
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
                      $.post('${basePath}manager/menu/menuRemove.do', { id : id }, function(result) {
                          //console.log(result);
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


<!-- <div id="menu-searchRD">
	<label>名称:</label> 
	<input name="name" class="easyui-textbox" style="width: 50px; height: 26px;"> 
	<label>显示名称:</label>
	<input name="displayName" class="easyui-textbox" style="width: 80px; height: 26px;"> 
	<label>地址:</label> <input name="url" class="easyui-textbox" style="width: 100px; height: 26px;"> 
	<a id="menu-searchResource" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
</div> -->

<div id="menu-tool">
    <div id="menu-search" style="padding-top: 10px;">
        <label style="padding-left: 10px;">菜单名称:</label> 
        <input name="name" class="easyui-textbox" style="height: 26px;" /> 
		<label style="padding-left: 10px;">菜单图标:</label> 
		<input name="icon" class="easyui-textbox" style="height: 26px;" /> 
		<a id="menu-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	
	<div id="menu-search" style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="menu-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加菜单</a> 
		<a id="menu-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">修改菜单</a> 
		<a id="menu-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除菜单</a>
	</div>
</div>

<div id="menu-grid"></div>
<div id="menu-dlg"></div>