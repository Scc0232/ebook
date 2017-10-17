<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
/*         var saveBtn = $('#bookShelf-saveBtn');
        var form = $('#bookShelfAdd-fm');
        $(saveBtn).bind('click', function () {
          
            var obj = toObject(form);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            console.log(obj);
            $.ajax({
                type: "POST",
                url: '${basePath}manager/bookShelf/addBookShelf.do',
                data: obj,
                dataType: "json",
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('保存成功！');
                        parent.$('#bookShelf-add').window('close');
                        $("#bookShelf-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    }); */
    var saveBtn = $('#bookShelf-saveBtn');
    
    $("#bookShelfAdd-fm").form({
                onSubmit: function(){
                        if(! $("#bookShelfAdd-fm").form('validate')){
                                layer.msg("请添加必填项!");
                                return false;
                        }
                        loading();
                        return true;
                },
                success: function(data){                                
                        var map = $.parseJSON(data);
                        if(map.flag == "success"){
                                parent.$('#bookShelf-add').dialog('close');
                        }
                        closeLoading();
                        layer.msg(map.msg);
                        if(map.flag == "success"){
                                $("#bookShelf-grid").datagrid('reload');
                        }
                }                       
        });
        
    $(saveBtn).bind('click', function () {
        $("#bookShelfAdd-fm").submit();
    }); 
    
    
    
});
    
    
</script>
<form id="bookShelfAdd-fm" method="post" enctype="multipart/form-data"  action="${basePath}manager/bookShelf/addBookShelf.do">
	<div class="fitem" style="margin-top: 20px;">
		<label align="right">ISBN：</label> 
		<input name="isbn" validtype="length[5,20]" missingMessage="不能为空" invalidMessage="有效长度5-20"   class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">书名：</label>
		<input name="title" class="easyui-textbox" required="true" style="width: 180px; height: 26px;">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">子标题：</label> 
		<input name="phone" class="easyui-textbox" style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">作者 ：</label>
		<input name="author"  class="easyui-textbox" style="width: 180px; height: 26px;">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">出版社 :</label> 
		<input name="publisher" class="easyui-textbox"  style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">页数 :</label>
		<input name="page"  class="easyui-textbox"   style="width: 180px; height: 26px;">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">折旧价格 :</label> 
		<input name="depPrice" class="easyui-textbox"  style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">押金 :</label>
		<input name="deposit" maxlength='100' class="easyui-textbox"  style="width: 180px; height: 26px;">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">e币价格 :</label> 
		<input name="ePrice" class="easyui-textbox"  style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">分类名称 :</label>
    		<select  class="easyui-combobox" name="classId" style="width:180px;" editable="false" panelHeight="auto">
			        	<option value="1">考研</option>
			        	<option value="2">公务员</option>
			        	<option value="3">四六级</option>
			        	<option value="4">文学类</option>
			        	<option value="5">理工类</option>
			        	<option value="6">政史类</option>
			        	<option value="7">计算机类</option>
			        	<option value="8">英语类</option>
			        	<option value="9">经管类机类</option>
			        	<option value="10">语言类</option>
			        	<option value="11">其他</option>
    		</select> 
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">学校 :</label> 
		<input name="college" class="easyui-textbox"  style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">年级 :</label>
	   <input name="grade" maxlength='100' class="easyui-textbox"  style="width: 180px; height: 26px;">
	</div>
	<div class="fitem" style="margin-top: 20px;">
	    <label  align="right">图片URL :</label>
		<input id="icons" name="icons"  maxlength='200' class="easyui-filebox" style="width: 180px; height: 26px;">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="bookShelf-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>