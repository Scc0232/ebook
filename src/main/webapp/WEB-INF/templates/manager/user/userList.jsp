<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script>
	(function() {
		var dg = $('#user-grid');// 数据网格
		var search = $('#user-search');// 搜索div
		var searchBtn = $('#user-searchBtn');// 搜索按钮
		var saveBtn = $('#user-addBtn');//增加按钮
		var removeBtn = $('#user-removeBtn');//删除按钮
		var modifyBtn = $('#user-modifyBtn');//修改按钮
		var url;// 提交url

		// 数据网格首选项
		var dgOption = {
		    collapsible: true,
            pagination: true,// 分页
            singleSelect: true,// 单选
			url : '${basePath}manager/user/findUserPagination.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ { field : 'id', title : '选择', checkbox : true },
			        { field : 'petName', title : '用户名', width:'120px' },
			        { field : 'name', title : '姓名', width:'90px' },
			        { field : 'phone', title : '电话', width:'120px' },
			        { field : 'email', title : '邮箱', width:'120px' },
			        { field : 'qq', title : 'QQ', width:'90px' },
			        { field : 'companyName', title : '公司名称', width:'120px' },
			        { field : 'companyPhone', title : '公司电话', width:'120px' },
			        { field : 'companyAddr', title : '公司地址', width:'120px' },
			        //{ field : 'myReferralCode', title : '邀请码', width:'90px' },
			        { field : 'accredit', title : 'APP登录授权', width:'100px',formatter : function (val) {
							if (val == 0) {
								return "<font color=red>未授权</font>";
							} else {
								return "<font color=green>已授权</font>";
							}
				     } },
					{ field : 'createTime', title : '创建时间', width:'150px', formatter : function(val) {
						return formatDate(val);
					} },
			        {field : 'password',title : '操作', width:'120px', formatter : function(value, rowData, rowIndex) {
						console.log(value);
							var id = rowData.id;
							var optionBtn = " <input id='auditStatus-Btn' onClick=approval('"
								+ id + "') type='button' value='重置密码' />";
							var accredit = rowData.accredit;
							if (accredit == 0) {
								optionBtn +=" <input id='accredit_ok_btn' onClick=accredit('" + id + "',1) type='button' value='授权登录' style='color:green' />";
							} else if (accredit == 1) {
								optionBtn +=" <input id='accredit_ok_btn' onClick=accredit('" + id + "',0) type='button' value='取消授权' style='color:red' />";
							}
							return optionBtn;
						
					} }  ] ],
			// 工具栏
			toolbar : '#user-tool' ,fit:true,fitColumns : true};

		$.extend($.fn.validatebox.defaults.rules, { equals : { validator : function(value, param) {
			return value == $(param[0]).val();
		}, message : '密码不一致.' } });

		$(searchBtn).bind('click', function() {
			var param = toObject(search);
			console.log(param);
			$(dg).datagrid('load', param);
		});
		
		
		//添加方法
        $(saveBtn).bind('click', function () {
            $('#user-add').dialog({
                title: '增加用户',
                width: 600,
                height: 420,
                closed: false,
                cache: false,
                href: '${basePath}manager/user/addUserView.do',
                modal: true
            });
        });
		
        //删除按钮绑定事件
        $(removeBtn).bind('click', function() {
            var rows = $(dg).datagrid('getSelected');
            if (rows) {
                
                $.messager.confirm('温馨提示', '确认删除此用户?', function(r) {
                	if (!r) {
                        return;
                    }
                    $.ajax({
                        type : "POST",
                        url : '${basePath}manager/user/removeUser.do?id=' + rows.id,
                        async : "false",
                        success : function(data) {
                            closeLoading();
                            if (data) {//保存成功
                              //  $(dlg).dialog('close');// 关闭回话框
                                $("#user-grid").datagrid('reload'); // 刷新数据网格
                            }
                        }
                    });
                });
            }else {
	        	layer.msg('必须选择一行!');
	            return;
            }
        });
		
		
        //修改方法
        $(modifyBtn).bind('click', function () {
            var row = $(dg).datagrid('getSelected');
            if (row) {
                $('#user-add').dialog({
                    title: '修改用户',
                    width: 600,
                    height: 420,
                    closed: false,
                    cache: false,
                    type : "GET",
                    href: '${basePath}manager/user/modifyUserView.do?userId=' + row.id,
                    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
		
		// 加载数据网格
		$(dg).datagrid(dgOption);
	})();
</script>

<script>
	function approval(id) {
		$('#user-add').dialog({
			title : '重置密码',
			width : 400,
			height : 200,
			closed : false,
			cache : false,
			href : '${basePath}manager/user/modifyPasswordView.do?userId=' + id,
			modal : true
		});
	}

	//授权/取消登录授权 
	function accredit (id,accreditStatus) {
		var message = "";
		if (accreditStatus == 0) {
			message = "确认要取消此用户登录授权么？";
		} else if (accreditStatus == 1) {
			message = "确认要给予此用户登录授权么？";
		}
		$.messager.confirm('温馨提示', message, function(r) {
        	if (!r) {
                return;
            }
            $.ajax({
                type : "POST",
                url : '${basePath}manager/user/accredit.do?id=' + id + '&accreditStatus=' + accreditStatus,
                async : "false",
                success : function(data) {
                    closeLoading();
                    if (data.flag == 'success') {//保存成功
                    	layer.msg(data.msg);
                      //  $(dlg).dialog('close');// 关闭回话框
                    	$("#user-grid").datagrid('reload');
                    } else {
                    	layer.msg(data.msg);
                    }
                }
            });
        });
	}
</script>
<div id="user-tool">
	<div id="user-search" style="padding-top: 10px;">
	<label style="padding-left: 10px;">用户名:</label> <input name="username"
			class="easyui-textbox" style="height: 26px;">
		<label style="padding-left: 10px;">姓名:</label> <input name="name"
			class="easyui-textbox" style="height: 26px;"><a
			id="user-searchBtn" href="javascript:void(0)"
			class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<!-- <a
			id="user-resetBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true">重置</a> -->
	</div>
	<div style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="user-addBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">增加用户</a> 
		<a id="user-modifyBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-user_gray'">修改用户</a>
		<a id="user-removeBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit_remove'">删除用户</a>
	</div>
</div>
<div id="user-grid"></div>
<div id="user-add" ></div>
