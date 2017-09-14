<%--
  Created by IntelliJ IDEA.
  User: zhanghua
  Date: 11/30/15
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	 $('#parentid').combobox({ 
         url : '${basePath}manager/menu/findParentMenuList.do', 
         valueField : 'id',
         textField : 'name' });
	$("menu-fm").form('clear');
	$("#menuAdd-saveBtn").click(function(){
		 $("#menu-fm").form('submit', { 
			 url : '${basePath}manager/menu/addMenu.do', 
			 onSubmit : function() {
            loading();
            if (!$(this).form('validate')) {
                closeLoading();
                return false;
            }
            return true;
        }, success : function(result) {
            closeLoading();
            var result = eval('(' + result + ')');
            console.log(result);
            if (result.flag === 'fail') {
                $.messager.show({ title : 'Error', msg : result.msg });
            } else {
            	parent.$('#menu-dlg').window('close');
                $("#menu-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
	
});
</script>
<form id="menu-fm" method="post" novalidate>
	<table class="am_table_1">
		<tr style="height: 15px;"></tr>
		<tr>
			<th>菜单图标 ：</th>
			<td><input name="icon" class="easyui-textbox" required="true"
				style="width: 180px; height: 26px;"></td>
		</tr>
		<tr>
			<th>菜单名称 ：</th>
			<td><input name="name" class="easyui-textbox" required="true"
				style="width: 180px; height: 26px;"></td>
		</tr>
		<tr>
			<th>资源 ：</th>
			<td>
				<select id="menu-resourgrid" class="easyui-combogrid"
				name="resourceId" style="width: 180px; height: 26px;"
				data-options="
                    icons:[{}],
                    editable:false,
                    pagination: true,// 分页
                    panelWidth: 500,
                    idField: 'id',
                    textField: 'displayName',
                    url: '${basePath}manager/resource/findResourcePagination.do',
                    method: 'get',
                    columns: [[
                        {field:'name',title:'名称',width:100},
                        {field:'displayName',title:'显示名称',width:120},
                         {field:'url',title:'地址',width:120},
                       ]], toolbar : '#menu-searchRD',
                       fitColumns: true">
				</select>
			</td>
		</tr>
		<tr>
			<th>父菜单 ：</th>
			<td>
				<input id="parentid" name="parentId" class="easyui-textbox"
				editable="false" style="width: 180px; height: 26px;">
			</td>
		</tr>
	</table>
</form>

<p align="center">
	<a id="menuAdd-saveBtn" class="easyui-linkbutton"><font size="3">提&nbsp;&nbsp;交</font></a>
</p>