<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
$(function () {
    var saveBtn = $('#dictAdd-saveBtn');
   
    $("#dict-fm").form({
                onSubmit: function(){
                        if(! $("#dict-fm").form('validate')){
                                layer.msg("请添加必填项!");
                                return false;
                        }
                        loading();
                        return true;
                },
                success: function(data){                                
                        var map = $.parseJSON(data);
                        if(map.flag == "success"){
                                parent.$('#dict-dlg').dialog('close');
                        }
                        closeLoading();
                        layer.msg(map.msg);
                        if(map.flag == "success"){
                                $("#dict-grid").datagrid('reload');
                        }
                }                       
        });
        
    $(saveBtn).bind('click', function () {
        $("#dict-fm").submit();
    }); 
    
    
    
});

</script>
<form id="dict-fm" method="post" enctype="multipart/form-data" action="${basePath}manager/dict/addDict.do">
   <table class="am_table_1">
       <tr style="height: 15px;"></tr>
        <tr>
           <th>字典名称 ：</th>
           <td><input name="dictName" maxlength='50'  class="easyui-textbox" style="width: 180px; height: 26px;" ></td>
       </tr>
   <tr>
       <th>字典类型 ：</th>
       <td><input name="dictType" maxlength='50' class="easyui-textbox" data-options="required:true" style="width: 180px; height: 26px;"></td>
   </tr>
   <tr>
       <th>字典编码：</th>
       <td><input name="dictCode"  maxlength='32' class="easyui-textbox" style="width: 180px; height: 26px;"></td>
   </tr>
   <tr>
       <th>字典备注 ：</th>
       <td><input name="dictDescribe"  maxlength='200' class="easyui-textbox" data-options="required:true" style="width: 180px; height: 26px;"></td>
   </tr>
   <tr>
       <th>上传图片：</th>
       <td><input id="icons" name="icons"  maxlength='200' class="easyui-filebox" style="width: 180px; height: 26px;"></td>
   </tr>
   </table>
</form>


<p align="center">
	<a id="dictAdd-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>