<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script>
    (function() {
        var dg = $('#correctionManage-grid');// 数据网格
        var search = $('#correctionManage-search');// 搜索div
        var searchBtn = $('#correctionManage-searchBtn');// 搜索按钮
        var spBtn= $('#correctionManage-spBtn');//审批按钮
        var url;// 提交url

        // 数据网格首选项
        var dgOption = {
            collapsible: true,
            pagination: true,// 分页
            singleSelect: true,// 单选
            url : '${basePath}correctionManage/findCorrectionManagePagination.do',// 数据来源地址
            selectOnCheck : true,
            columns : [ [ 
                        { field : 'id', title : '选择', checkbox : true },
                        { field : 'errorParts', title : '错误部分',width:20 },
                        { field : 'userName', title : '纠错用户',width:20 },
                        { field : 'mobileNo', title : '联系电话',width:20 },
                        { field : 'status', title : '审批状态',width:20, formatter : function(value, row, rowIndex) {
                               if(value=="0"){
                            	   return  "<a href='javascript:void(0)' onClick='approval(\""+ row.id +"\")'>"+'待审批'+"</a>";
                               }else if(value=="1"){
                            	   return   "已审批";
                               }else{
                            	   return "不处理";
                               }
                            
                        } },
                        
                    ] ],
                  
            // 工具栏
            toolbar : '#correctionManage-tool' ,fit:true,fitColumns : true};

        $(searchBtn).bind('click', function() {
            var param = toObject(search);
            console.log(param);
            $(dg).datagrid('load', param);
        });
        

        
        //审批按钮绑定事件
       $(spBtn).bind('click', function() {
           var rows = $('#correctionManage-grid').datagrid('getSelected');
           if (rows) {
               $.messager.confirm('温馨提示', '确认审批此课程?', function(r) {
                   if (!r) {
                       return;
                   }
                   if(rows.status!="0"){
                	   layer.msg('你已经审批过该条信息!');
                	   return false;
                   }
                   $.ajax({
                       type : "POST",
                       url : '${basePath}correctionManage/approvalCorrectionManage.do?id=' + rows.id,
                       async : "false",
                       success : function(data) {
                           console.log(data);
                           closeLoading();
                           if (data.flag=="success") {//审批除成功
                               layer.msg('审批成功!');
                               $(dg).datagrid('reload'); // 刷新数据网格
                           }
                       }
                   });
               });
           }else {
               layer.msg('必须选择一行!');
               return;
           }
       });
        
       $('#correctionManage-unspBtn').bind('click', function() {
           var rows = $('#correctionManage-grid').datagrid('getSelected');
           if (rows) {
               $.messager.confirm('温馨提示', '确认审批此课程?', function(r) {
                   if (!r) {
                       return;
                   }
                   if(rows.status!="0"){
                	   layer.msg('你已经审批过该条信息!');
                	   return false;
                   }
                   $.ajax({
                       type : "POST",
                       url : '${basePath}correctionManage/UnApprovalCorrectionManage.do?id=' + rows.id,
                       async : "false",
                       success : function(data) {
                           console.log(data);
                           closeLoading();
                           if (data.flag=="success") {//审批除成功
                               layer.msg('审批成功!');
                               $(dg).datagrid('reload'); // 刷新数据网格
                           }
                       }
                   });
               });
           }else {
               layer.msg('必须选择一行!');
               return;
           }
       });
        
        
        // 加载数据网格
        $(dg).datagrid(dgOption);
    })();
</script>

<script>
    function approval(id) {
        $('#correctionManage-add').dialog({
            title : '查看详情',
            width : 800,
            height : 600,
            closed : false,
            cache : false,
            href : '${basePath}correctionManage/correctionManageDetailed.do?id=' + id,
            modal : true
        });
    }
    

</script>



<div id="correctionManage-tool">
    <div id="correctionManage-search" style="padding-top: 10px;">
        <label style="padding-left: 10px;">纠错内容:</label> <input name="errorContent"
            class="easyui-textbox" style="height: 26px;"><a
            id="correctionManage-searchBtn" href="javascript:void(0)"
            class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
    <div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
<!--         <a id="correctionManage-spBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">审批</a>
         <a id="correctionManage-unspBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">不处理</a> -->
    </div>
</div>
<div id="correctionManage-grid"></div>
<div id="correctionManage-add" ></div>
