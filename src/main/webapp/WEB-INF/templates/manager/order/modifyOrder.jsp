<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	$("order-fm").form('clear');
	
	$('#statusCombobox').combobox({
		valueField:'orderStatus',
		panelHeight: "auto",
        editable: false,
			
        onLoadSuccess: function (data) {
        	$('#statusCombobox').combobox('setValue','${order.orderStatus}');
        }
	});
	
	$("#orderModify-saveBtn").click(function(){
		 $("#order-fm").form('submit', { 
			 url : '${basePath}manager/order/modifyOrder.do', 
			 onSubmit : function() {
            loading();
            if (!$('#order-fm').form('validate')) {
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
            	parent.$('#order-add').window('close');
            	layer.msg('修改成功！');
                $("#order-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});



</script>

<form id="order-fm" method="post" novalidate style="margin-top: 50px;">
    <div>
        <input id="id" name="id" type="hidden" value="${order.id}"/>
    </div>
	<div class="fitem" style="margin-top: 20px; " align="center">
		<label align="right">订单状态：</label> <select id="statusCombobox"  class="easyui-combobox" name="orderStatus" style="width:180px;" editable="false" panelHeight="auto" value="${order.orderStatus}">
			        	<option value="0">未支付</option>
			        	<option value="1">已支付</option>
			        	<option value="2">取消</option>
			        	<option value="3">已预订</option>
			        	<option value="4">测试</option>
    		</select> 
</form>
<p align="center" style="margin-top: 50px;">
	<a id="orderModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>