<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	$("shoppingCart-fm").form('clear');
	$("#shoppingCartModify-saveBtn").click(function(){
		 $("#shoppingCart-fm").form('submit', { 
			 url : '${basePath}manager/shoppingCart/modifyShoppingCart.do', 
			 onSubmit : function() {
            loading();
            if (!$('#shoppingCart-fm').form('validate')) {
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
            	parent.$('#shoppingCart-add').window('close');
            	layer.msg('修改成功！');
                $("#shoppingCart-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});



</script>

<form id="shoppingCart-fm" method="post" novalidate style="margin-top: 50px;">
    <div>
        <input id="id" name="id" type="hidden" value="${shoppingCart.id}"/>
    </div>
	<div class="fitem" style="margin-top: 20px; " align="center">
		<label align="right">商品数量：</label> 
		<input name="count" missingMessage="不能为空" value="${shoppingCart.count}"  class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="shoppingCartModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>