<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script>
    (function() {
        var dg = $('#ideaTicking-grid');// 数据网格
        var search = $('#ideaTicking-search');// 搜索div
        var searchBtn = $('#ideaTicking-searchBtn');// 搜索按钮
        var url;// 提交url

        // 数据网格首选项
        var dgOption = {
            collapsible: true,
            pagination: true,// 分页
            singleSelect: true,// 单选
            url : '${basePath}ideaTicking/ideaTickingPagination.do',// 数据来源地址
            selectOnCheck : true,
            columns : [ [ 
                        { field : 'id', title : '选择', checkbox : true },
                        { field : 'userName', title : '反馈用户',width:20 },
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
            toolbar : '#ideaTicking-tool' ,fit:true,fitColumns : true};

        $(searchBtn).bind('click', function() {
            var param = toObject(search);
            console.log(param);
            $(dg).datagrid('load', param);
        });
        
        // 加载数据网格
        $(dg).datagrid(dgOption);
    })();
</script>

<script>
    function approval(id) {
        $('#ideaTicking-add').dialog({
            title : '查看详情',
            width : 800,
            height : 600,
            closed : false,
            cache : false,
            href : '${basePath}ideaTicking/ideaTickingDetailed.do?id=' + id,
            modal : true
        });
    }
    

</script>

<div id="ideaTicking-tool">
    <div id="ideaTicking-search" style="padding-top: 10px;">
        <label style="padding-left: 10px;">反馈内容:</label> <input name="content"
            class="easyui-textbox" style="height: 26px;"><a
            id="ideaTicking-searchBtn" href="javascript:void(0)"
            class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>
<div id="ideaTicking-grid"></div>
<div id="ideaTicking-add" ></div>
