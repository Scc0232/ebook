<%--
  Created by IntelliJ IDEA.
  User: lidongwei
  Date: 16/1/21
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	$("area-fm").form('clear');
	$("#areaModify-saveBtn").click(function(){
		 $("#area-fm").form('submit', { 
			 url : '${basePath}manager/area/modifyArea.do', 
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
            	parent.$('#area-dlg').window('close');
            	layer.msg('修改成功！');
                $("#area-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});
</script>
	<form id="area-fm" method="post" novalidate>
	   <table class="am_table_1">
	       <tr style="height: 15px;"></tr>
	       <input id="id" name="id" type="hidden" value="${area.id}"/>
	       <tr>
	           <th>区域名称 ：</th>
	           <td><input name="name" class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${area.name}"></td>
	       </tr>
	       <tr>
               <th>区域编码 ：</th>
               <td><input name="code" class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${area.code}"></td>
           </tr>
           <tr>
               <th>城市编码：</th>
               <td><input name="cityCode" class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value = "${area.cityCode}"></td>
           </tr>
           <tr>
               <th>简称 ：</th>
               <td><input name="abbr" class="easyui-textbox"  style="width: 180px; height: 26px;" value = "${area.abbr}"></td>
           </tr>
	   </table>
	</form>

<p align="center">
	<a id="areaModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>