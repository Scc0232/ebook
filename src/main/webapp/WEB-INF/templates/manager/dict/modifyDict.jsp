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
	$("dict-fm").form('clear');
	$("#dictModify-saveBtn").click(function(){
		 $("#dict-fm").form('submit', { 
			 url : '${basePath}manager/dict/modifyDict.do', 
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
            	parent.$('#dict-dlg').window('close');
            	layer.msg('修改成功！');
                $("#dict-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});
</script>
	<form id="dict-fm" method="post"  enctype="multipart/form-data" action="${basePath}manager/dict/modifyDict.do">
	   <table class="am_table_1">
	       <tr style="height: 15px;"></tr>
	       <input id="id" name="id" type="hidden" value="${dict.id}"/>
	       <tr>
	           <th>字典名称 ：</th>
	           <td><input name="dictName" maxlength='50'   class="easyui-textbox" style="width: 180px; height: 26px;" value="${dict.dictName}"></td>
	       </tr>
	       <tr>
               <th>字典类型 ：</th>
               <td><input name="dictType" maxlength='50' class="easyui-textbox"   style="width: 180px; height: 26px;" value="${dict.dictType}"></td>
           </tr>
           <tr>
               <th>字典编码：</th>
               <td><input name="dictCode" maxlength='32' class="easyui-textbox" style="width: 180px; height: 26px;" value = "${dict.dictCode}"></td>
           </tr>
           <tr>
               <th>字典备注 ：</th>
               <td><input name="dictDescribe" maxlength='200' class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value = "${dict.dictDescribe}"></td>
           </tr>
           <tr>
               <th>菜单图片URL ：</th>
               <td><input id="icons" name="icons"  maxlength='200' class="easyui-filebox" style="width: 180px; height: 26px;" value = "${dict.icon}"></td>
           </tr>
	   </table>
	</form>

<p align="center">
	<a id="dictModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>