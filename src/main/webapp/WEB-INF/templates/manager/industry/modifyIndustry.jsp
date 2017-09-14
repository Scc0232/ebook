<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function(){
	$("industry-fm").form('clear');
	
	$("#industryModify-saveBtn").click(function(){
		 $("#industry-fm").form('submit', { 
			 url : '${basePath}industry/modifyIndustry.do', 
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
            	parent.$('#industry-add').window('close');
                $("#industry-grid").datagrid('reload'); // 刷新数据网格
            }
        } });
	});
	
	$('#fatherSectorid').combobox({ 
        url : '${basePath}industry/findParentIndustryList.do', 
        valueField : 'id',
        textField : 'sectorName' ,
       	onLoadSuccess:function(){
            $('#fatherSectorid').combobox('setValue','${industry.fatherSectorId}');   
        }
            	
	});
	
	
});
</script>
	<form id="industry-fm" method="post" novalidate>
	   <table class="am_table_1">
	       <tr style="height: 15px;"></tr>
	       <input id="id" name="id" type="hidden" value="${industry.id}"/>
	       <tr>
               <th>行业名称 ：</th>
               <td><input name="sectorName" class="easyui-textbox" required="true" style="width: 180px; height: 26px;" value="${industry.sectorName}"></td>
           </tr>
           <tr>
               <th>父行业 ：</th>
               <td><input id="fatherSectorid" name="fatherSectorId" class="easyui-textbox" editable="false" style="width: 180px; height: 26px;" value = "${industry.fatherSectorId}"></td>
           </tr>
	   </table>
	</form>

<p align="center">
	<a id="industryModify-saveBtn" class="easyui-linkbutton"><font size="3">提&nbsp;&nbsp;交</font></a>
</p>