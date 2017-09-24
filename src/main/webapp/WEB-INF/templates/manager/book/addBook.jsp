<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script>
    $(function () {
        var saveBtn = $('#book-saveBtn');
        var form = $('#bookAdd-fm');
        
      //分类名称下拉列表
		$('#classNameCombobox').combobox({
			url:'${basePath}book/findClassNameList.do',
			valueField:'id',
			textField:'name',
			panelHeight: "auto",
            editable: false,
			onLoadSuccess: function (data) {
                if (data) {
                    $('#classNameCombobox').combobox('setValue',data[0].id);
                }
            }
		});

        $(saveBtn).bind('click', function () {
          
            var obj = toObject(form);
            if (!$(form).form('validate')) {
                closeLoading();
                return false;
            }
            console.log(obj);
            $.ajax({
                type: "POST",
                url: '${basePath}manager/book/addBook.do',
                data: obj,
                dataType: "json",
                async: "false",
                success: function (data) {
                    if (data.flag === "success") {
                        layer.msg('保存成功！');
                        parent.$('#book-add').window('close');
                        $("#book-grid").datagrid('reload'); // 刷新数据网格
                    }
                }
            });
        });
    });
    
</script>
<form id="bookAdd-fm" method="post" novalidate style="margin-top: 50px;">
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
		<input id="classNameCombobox"  class="easyui-combobox" name="className"  editable="false" data-options="required:true,validType:'class_name'"  style="width: 180px; height: 26px;">
	</div>
		<div class="fitem" style="margin-top: 20px;">
		<label align="right">学校 :</label> 
		<input name="college" class="easyui-textbox"  style="width: 180px; height: 26px;">
	   <label style="margin-left: 20px" align="right">年级 :</label>
		<input name="grade"  class="easyui-textbox"  style="width: 180px; height: 26px;">
	</div>
</form>
<p align="center" style="margin-top: 50px;">
	<a id="book-saveBtn" class="easyui-linkbutton"><font size="2">提&nbsp;&nbsp;交</font></a>
</p>