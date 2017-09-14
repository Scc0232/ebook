<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<script>
$(function () {
	 var createTime = Formatter.formatDate('${view.createTime}');
     $("#createTime").html(createTime);
     console.log(createTime);
     var modifyTime = Formatter.formatDate('${view.checkTime}');
     $("#modifyTime").html(modifyTime);
    
});


</script>
<script>
		
		//审批按钮绑定事件
		$('#correctionManage-saveBtn').bind('click', function() {
			var id = ${view.id};
			var status =${view.status};
		       $.messager.confirm('温馨提示', '确认审批此课程?', function(r) {
		           if (!r) {
		               return;
		           }
		           if(status!="0"){
		        	   layer.msg('你已经审批过该条信息!');
		        	   return false;
		           }
		           $.ajax({
		               type : "POST",
		               url : '${basePath}correctionManage/approvalCorrectionManage.do?id=' + id,
		               async : "false",
		               success : function(data) {
		                   console.log(data);
		                   closeLoading();
		                   if (data.flag=="success") {//审批除成功
		                       layer.msg('审批成功!');
		                   	parent.$('#correctionManage-add').dialog('close');//关闭菜单
		                   	$("#correctionManage-grid").datagrid('reload'); // 刷新数据网格
		                   }
		               }
		           });
		       });
		});
		
		//审批不处理按钮
		$('#correctionManage-modifyBtn').bind('click', function() {
			var id = ${view.id};
			var status =${view.status};
		       $.messager.confirm('温馨提示', '确认审批此课程?', function(r) {
		           if (!r) {
		               return;
		           }
		           if(status!="0"){
		        	   layer.msg('你已经审批过该条信息!');
		        	   return false;
		           }
		           $.ajax({
		               type : "POST",
		               url : '${basePath}correctionManage/UnApprovalCorrectionManage.do?id=' + id,
		               async : "false",
		               success : function(data) {
		                   console.log(data);
		                   closeLoading();
		                   if (data.flag=="success") {//审批除成功
		                       layer.msg('审批成功!');
		                       parent.$('#correctionManage-add').dialog('close');//关闭菜单
			                   	$("#correctionManage-grid").datagrid('reload'); // 刷新数据网格
		                   }
		               }
		           });
		       });
		});

</script>
	
<table id="billDetail-tab" method="post" class="am_table_detail_2"
	border="0">
	<tr style="height: 15px;">
		<th colspan="4"></th>
	</tr>
	<tr>
		<th>错误部分:</th>
		<td id="title">${view.errorParts}</td>
		<th>联系电话:</th>
		<td>${view.mobileNo}</td>
	</tr>
	<tr>
		<th>创建时间:</th>
		<td id="createTime"></td>
		<th>审批时间:</th>
		<td id="modifyTime"></td>
	</tr>
	<tr>
		<th>内容:</th>
		<td colspan="3"><textarea id="content"   disabled="disabled"  class="textarea easyui-validatebox"   style="height: 80px;width:98%;background-attachment: fixed; background-repeat: no-repeat; border-style: solid; border-color: #FFFFFF;"> ${view.errorContent}</textarea></td>
	</tr>
</table>
<div id="correctionManage-grid"></div>
 <div id="correctionManage-dlg-buttons" style="text-align: center; padding-top: -300px;">
     <a id="correctionManage-saveBtn" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" style="width: 90px">审批</a>
    <a id="correctionManage-modifyBtn" href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" style="width: 90px">不处理</a>  
</div> 
			        

