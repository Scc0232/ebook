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
	$("city-fm").form('clear');
	$("#cityModify-saveBtn").click(function(){
		 $("#city-fm").form('submit', { 
			 url : '${basePath}manager/city/modifyCity.do', 
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
            	parent.$('#city-dlg').window('close');
            	layer.msg('修改成功！');
                $("#city-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});
</script>
	<form id="city-fm" method="post" novalidate>
	   <table class="am_table_1">
	       <tr style="height: 15px;"></tr>
	       <input id="id" name="id" type="hidden" value="${city.id}"/>
	       <tr>
	           <th>城市名称 ：</th>
	           <td><input name="name" maxlength='20' data-options="required:true,validType:'CHS'" class="easyui-textbox" style="width: 180px; height: 26px;" value="${city.name}"></td>
	       </tr>
	       <tr>
               <th>城市编码 ：</th>
               <td><input name="code" maxlength='6' class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${city.code}"></td>
           </tr>
           <tr>
               <th>省份编码：</th>
               <td><input name="provinceCode" maxlength='6' class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value = "${city.provinceCode}"></td>
           </tr>
           <tr>
               <th>简称 ：</th>
               <td><input name="abbr" maxlength='10' class="easyui-textbox"  style="width: 180px; height: 26px;" value = "${city.abbr}"></td>
           </tr>
	   </table>
	</form>

<p align="center">
	<a id="cityModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>