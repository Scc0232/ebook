<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	$("donation-fm").form('clear');
	
	$('#statusCombobox').combobox({
		valueField:'donationStatus',
		panelHeight: "auto",
        editable: false,
			
        onLoadSuccess: function (data) {
        	$('#statusCombobox').combobox('setValue','${donation.status}');
        }
	});
	
	$("#donationModify-saveBtn").click(function(){
		 $("#donation-fm").form('submit', { 
			 url : '${basePath}manager/donation/modifyDonation.do', 
			 onSubmit : function() {
            loading();
            if (!$('#donation-fm').form('validate')) {
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
            	parent.$('#donation-add').window('close');
            	layer.msg('修改成功！');
                $("#donation-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
});



</script>

<form id="donation-fm" method="post" novalidate style="margin-top: 50px;">
    <div>
        <input id="id" name="id" type="hidden" value="${donation.id}"/>
    </div>
	<div class="fitem" style="margin-top: 20px; " align="center">
		<label align="right">捐赠状态：</label> <select id="statusCombobox"  class="easyui-combobox" name="status" style="width:180px;" editable="false" panelHeight="auto" value="${donation.status}">
			        	<option value="0">待确认</option>
			        	<option value="1">已确认</option>
    		</select> 
</form>
<p align="center" style="margin-top: 50px;">
	<a id="donationModify-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>