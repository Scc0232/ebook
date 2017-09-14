/**
 * 任务页面相关js
 */
$(function(){
		var url;
		var submitType;
		// 数据网格首选项
		var dgOption = {
			collapsible : true,
			pagination : true,// 分页
			singleSelect : false,// 单选
			method: 'GET',
			url : 'assignment/taskManage/findTaskList.do',// 数据来源地址
			selectOnCheck : true,
			columns : [ [ 
			 		{ field : 'id', checkbox : true }, 
			 		{ field : 'taskName', title : '任务名' ,align:'center',width : '15%'},
					{ field : 'taskDesc', title : '任务描述',align:'center',width:'25%' },
				    { field : 'taskBigType', title : '任务类型',align:'center',width:'5%',formatter : function(value) {
						if (value == 0) {
							return "日常任务";
						 } else if(value == 1) {
							return "银行任务";
						 } else if(value == 2) {
							return "游戏任务";
						 }
					}},
					{ field : 'isUserCheck', title : '是否需要人工审核',align:'center',width:'6%',formatter : function(value){
						 if (value == 0) {
								return "不需要";
						 } else if (value == 1) {
							    return "需要";
						 }
					} },
					{ field : 'taskType', title : '任务级别',align:'center',width:'6%',formatter : function (value) {
						 if (value == 1) {
							 return "初级任务";
						 } else if (value == 2) {
							 return "中级任务";
						 } else if (value == 3) {
							 return "高级任务";
						 } else if (value == 4) {
							 return "信贷经理/服务者任务";
						 }
				    }},
					{ field : 'isTrgg', title : '是否循环',align:'center',width:'5%',formatter : function (value) {
						  if (value == 1) {
								return "循环";
						  } else if (value == 0) {
							  	return "不循环";
						  }
					}},
					{ field : 'trggCycle', title : '循环周期',align:'center',width:'5%'},
					{ field : 'trggCycleUnit', title : '循环周期单位',align:'center',width:'5%',formatter : function (value) {
						  if (value == "d") {
								return "天";
						  } else if (value == "m") {
							  	return "月";
						  } else if (value == "y") {
							  	return "年";
						  }
					}},
					{ field : 'taskSendDownType', title : '任务下发方式',align:'center',width:'7%',formatter : function (value) {
						  if (value) {
							  if (value == "SYSTEM") {
									return "系统自动下发";
							} else if (value == "PLAT_USER") {
									return "平台用户下发";
							}
						  }
					}},
					
					{ field : 'strategyConfig', title : '任务策略配置情况',align:'center',width:'8%',formatter : function (value) {
						  if (value == 0) {
								return "<font color=red>未配置</font>";
						} else if (value == 1) {
								return "<font color=green>已配置</font>";
						}
					  
					}},
					{ field : 'strategyAlgConfig', title : '任务策略算法配置情况',align:'center',width:'8%',formatter : function (value) {
						  if (value == 0) {
								return "<font color=red>未配置</font>";
						} else if (value == 1) {
								return "<font color=green>已配置</font>";
						}
					}}
					] ],
			// 工具栏
			toolbar : '#task_index_tool',fit:true,fitColumns : true};

			//查询
			$("#task-searchBtn").click(function(){
				var params = toObject($("#task-search"));
				$("#task-grid").datagrid("load",params);
			 });

			 //新增
			 $("#task-addBtn").click(function(){
				 $("#task_add_dialog").dialog('open').dialog('center').dialog('setTitle', '增加任务信息');
				 $("#task_add_form").form('clear');
				 url = "assignment/taskManage/addTask.do";
				 $("input[name='isBlankTask']").eq(0).prop("checked",true);
				 $("input[name='isUserCheck']").eq(0).prop("checked",true);
				 $("input[name='isTrgg']").eq(0).prop("checked",true);
				 $('#trggCycle').combobox('disable');
				 $('#taskType').combobox('setValue', '1');
				 $('#trggCycleUnit').combobox('setValue', 'd');
				 $('#taskSendDownType').combobox('setValue', 'SYSTEM');
				 $('#taskBigType').combobox('setValue','0');
			 });

			 $("input:radio[name='isTrgg']").change(function () {
				 var isTrgg = $('input[name="isTrgg"]:checked ').val();
				 if (isTrgg == 1) {
					 $('#trggCycle').combobox('enable');
				 } else if (isTrgg == 0) {
					 $("#trggCycle").numberbox('clear');
					 $('#trggCycle').combobox('disable');
				}
	          });
			 //保存
			 $("#task-saveBtn").click(function(){
				 var trgg = $('input[name="isTrgg"]:checked ').val();
				 if (trgg == 1) {
					 var trggCycle = $("#trggCycle").numberbox('getValue');
					 if (trggCycle <= 0) {
						 layer.msg('循环天数不能小于等于0');
						 return false;
					 }
				 }
				$("#task_add_form").form('submit', { url : url, onSubmit : function() {
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
						$("#task_add_dialog").dialog('close'); // 关闭回话框
						$("#task-grid").datagrid('reload'); // 刷新数据网格
					}
				} });
			 });

			 //修改
			 $("#task-modifyBtn").click(function(){
				 var row = $("#task-grid").datagrid('getSelected');
				 var rows = $("#task-grid").datagrid('getChecked');
				 if (row) {
					 if (rows.length > 1) {
						 layer.msg("最多只能选择一行！");
					 } else {
						 var id = row.id;
							$("#task_add_dialog").dialog('open').dialog('center').dialog('setTitle', '修改任务信息');
							$("#task_add_form").form('clear');
							$("#task_add_form").form('load', row);
							$("#taskId").val(id);
							url = 'assignment/taskManage/modifyTask.do';
							var isTrgg = row.isTrgg;
							if (isTrgg == 1) {
								 $('#trggCycle').combobox('enable');
							 } else if (isTrgg == 0) {
								 $('#trggCycle').combobox('disable');
							}
					 }
				 } else {
					layer.msg('请选择一行数据！');
				 }
			 });

			 //删除
			 $("#task-removeBtn").click(function(){
				 var row = $("#task-grid").datagrid('getChecked');
				 if (row.length != 0) {
					 $.messager.confirm('温馨提示', '确认删除此信息吗?',function(data){
						 if(data){
							 var taskIds = [];
							 for ( var i in row) {
								 taskIds[i] = row[i].id;
							 }
							 taskIds = $.toJSON(taskIds);
							 $.ajax({ type : "POST",
									url : 'assignment/taskManage/removeTask.do',
									data : {taskIdsJson:taskIds}, 
									async : "false",
									success : function(data) {
										closeLoading();
										if (data) {
											$("#task-grid").datagrid('reload'); // 刷新数据网格
										}
							} });
						 }
					 });
				 }
			 });

			//配置任务策略
			 $("#task-deploy").click(function(){
				 var row = $("#task-grid").datagrid('getSelected');
				 var rows = $("#task-grid").datagrid('getChecked');
				 if (row) {
					 if (rows.length > 1) {
						 layer.msg("最多只能选择一行！");
					 } else {
						 var id = row.id;
							//任务id
							$("#strategyOftaskId").val(id);
							//TODO 是否需要审核
							var isTrgg = row.isTrgg;
							$("#isTrggHidden").val(isTrgg);
							$("#TaskStrategy_dialog").dialog('open').dialog('center').dialog('setTitle', '配置任务策略信息');
				 			$("#TaskStrategy-grid").datagrid({
										collapsible : true,
										pagination : false,// 不分页
										singleSelect : false,// 单选
										method: 'GET',
										url : 'assignment/taskManage/findTaskStrategyList.do',// 数据来源地址
										queryParams : {taskId:id},
										selectOnCheck : true,
										columns : [ [ 
										 		{ field : 'id', checkbox : true }, 
										 		{ field : 'fieldKey', title : '字段key', align:'center',width:'18%'},
												{ field : 'fieldName', title : '字段名称' ,align:'center',width:'20%'},
											    { field : 'dataType', title : '数据类型',align:'center',width:'20%',formatter : function(value) {
											    	if (value == 'string') {
														return "字符串";
													 } else if (value == 'int') {
														return "数字";
													 }
												}},
												{ field : 'scoreType', title : '加分类型',align:'center',width:'18%',formatter : function(value){
													 if (value == 0) {
															return "信用分";
													 } else if (value == 1) {
														    return "授信分";
													 }
												} },
												{ field : 'modleType', title : '前台组件类型',align:'center',width:'20%',formatter : function (value) {
													 if (value == 1) {
														 return "输入框";
													 } else if (value == 2) {
														 return "下拉列表框";
													 } else if (value == 3) {
														 return "单选按钮";
													 } else if (value == 4) {
														 return "多选框";
													 } else if (value == 5) {
														 return "上传";
													}
											    }}
												]],
										// 工具栏
										toolbar : '#task_strategy_tool' });
							
					 }
				 } else {
					layer.msg('请选择一行数据！');
				 }
			 });
			 
			 $("#task-sendBtn").click(function(){
				 var row = $("#task-grid").datagrid('getSelected');
				 var rows = $("#task-grid").datagrid('getChecked');
				 if (row) {
					 if (rows.length > 1) {
						 layer.msg("最多只能选择一行！", {icon: 6});
					 } else {
						 $("#sendtask_to_credit_dialog").dialog('open').dialog('center').dialog('setTitle', '选择发送对象');
						 $('#roleType').combobox('setValue','ROLE_CREDIT_MANAGER');
						 var id = row.id;
						 $("#sendToTaskId").val(id);
						 var roleType = $("#roleType").combobox("getValue");
					 }
				 } else {
					 layer.msg('请选择一行！', {icon: 5});
				 }
			 });
			 
			 $("#task-send_tocreditBtn").click(function(){
				 var roleTypes = $("input[name='roleTypes']:checked").map(function () {
		               return $(this).val();
		           }).get().join('#');
				 if (roleTypes != null && roleTypes != '') {
					 var taskId = $("#sendToTaskId").val();
					 $.ajax({ type : "POST",
							url : 'assignment/taskManage/sendTaskToCredit.do',
							data : {roleNames:roleTypes,taskId:taskId}, 
							async : "false",
							success : function(data) {
								closeLoading();
								if (data) {
									alert(data.msg);
									$("#sendtask_to_credit_dialog").dialog('close');
									$("#task-grid").datagrid('reload'); // 刷新数据网格
								}
					} });
				 }
			 });
			 
			 //添加配置策略
			 $("#task_strategy-addBtn").click(function(){
				 $("#task_strategy_add_dialog").dialog('open').dialog('center').dialog('setTitle', '增加任务策略信息');
				 url = "assignment/taskManage/addTaskStrategy.do";
				 submitType = "add";
				 $("#fieldKey").textbox('clear');
				 $("#fieldName").textbox('clear');
			 });

			
			 //修改配置策略
			 $("#task_strategy-modifyBtn").click(function(){
				 var row = $("#TaskStrategy-grid").datagrid('getSelected');
				 var rows = $("#TaskStrategy-grid").datagrid('getChecked');
				 if (row) {
					 if (rows.length > 1) {
						 layer.msg("最多只能选择一行！");
					 } else {
						 var id = row.id;
							$("#task_strategy_add_dialog").dialog('open').dialog('center').dialog('setTitle', '修改任务策略信息');
							$("#task_strategy_add_form").form('clear');
							$("#task_strategy_add_form").form('load', row);
							$("#strategyOftaskId").val(row.taskId);
							$("#strategyId").val(id);
							url = 'assignment/taskManage/modifyTaskStrategy.do';
							submitType = "modify";
					 }
				 } else {
					layer.msg('请选择一行数据！');
				 }
			 });
			 

			//删除配置策略
			 $("#task_strategy-removeBtn").click(function(){
				 var row = $("#TaskStrategy-grid").datagrid('getChecked');
				 if (row.length != 0) {
					 $.messager.confirm('温馨提示', '确认删除此信息吗?',function(data){
						 if(data){
							 var taskStrategyIds = [];
							 var taskId = "";
							 for ( var i in row) {
								 taskStrategyIds[i] = row[i].id;
								 taskId = row[i].taskId;
							 }
							 taskStrategyIds = $.toJSON(taskStrategyIds);
							 $.ajax({ type : "POST",
									url : 'assignment/taskManage/romveTaskStrategy.do',
									data : {taskStrategyIdsJson:taskStrategyIds,taskId:taskId}, 
									async : "false",
									success : function(data) {
										closeLoading();
										if (data) {
											$("#TaskStrategy-grid").datagrid('reload'); // 刷新数据网格
										}
							} });
						 }
					 });
				 }
			 });
				
			//保存配置策略
			 $("#task-strategy-saveBtn").click(function(){
				 var fieldKey = $("#fieldKey").textbox("getValue");
				 //TODO
				 var isTrgg = $("#isTrggHidden").val();
				 var modleType = $("#modleType").combobox("getValue");
				 if (isTrgg == 0) {
					 if (modleType != 5) {
						 layer.msg("需要审核的任务策略组件类型只能为上传类型！");
						 return ;
					}
				 }
				 if (fieldKey != null && fieldKey != '') {
					 if (submitType == 'add') {
						 $.ajax({ type : "GET",
								url : 'assignment/taskManage/findTaskStrategyCountByfieldKey.do',
								data : {fieldKey:fieldKey}, 
								async : "false",
								success : function(result) {
									if (result == 1) {
										layer.msg("字段key已存在！");
									} else if (result == -1) {
										layer.msg("字段key参数为空!");
									} else {
										$("#task_strategy_add_form").form('submit', { url : url, onSubmit : function() {
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
												$("#task_strategy_add_dialog").dialog('close'); // 关闭回话框
												$("#TaskStrategy-grid").datagrid('reload'); // 刷新数据网格
											}
										} });
									}
						} });
					 } else if (submitType == 'modify') {
						 $("#task_strategy_add_form").form('submit', { url : url, onSubmit : function() {
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
									$("#task_strategy_add_dialog").dialog('close'); // 关闭回话框
									$("#TaskStrategy-grid").datagrid('reload'); // 刷新数据网格
								}
							} });
					 }
				 }
			 });

			 //配置任务策略分数算法 
			 $("#task_strategy-deploy").click(function(){
				 var row = $("#TaskStrategy-grid").datagrid('getSelected');
				 var rows = $("#TaskStrategy-grid").datagrid('getChecked');
				 if (row) {
					 if (rows.length > 1) {
						 layer.msg("最多只能选择一行！");
					 } else {
						 var id = row.id;
						 var modleType = row.modleType;
							//任务策略id
							$("#taskStrategyId").val(id);
							$("#modleType").val(modleType);
							$("#TaskCreditScoreAlg_dialog").dialog('open').dialog('center').dialog('setTitle', '配置任务策略算法信息');
				 			$("#TaskCreditScoreAlg-grid").datagrid({
										collapsible : true,
										pagination : false,// 不分页
										singleSelect : false,// 单选
										method: 'GET',
										url : 'assignment/taskManage/findTaskCreditScoreAlg.do',// 数据来源地址
										queryParams : {taskStrategyId:id},
										selectOnCheck : true,
										columns : [ [ 
										 		{ field : 'id', checkbox : true }, 
										 		{ field : 'initScore', title : '基础分数',align:'center',width:'23%' },
											    { field : 'percent', title : '折算系数',align:'center',width:'25%'},
												{ field : 'actualScore', title : '实际分数',align:'center',width:'25%',},
												{ field : 'optionValue', title : '下拉列表/单选/多选框的值',align:'center',width:'25%'},
												] ],
										// 工具栏
										toolbar : '#task_TaskCreditScoreAlg_tool' });
							
					 }
				 } else {
					layer.msg('请选择一行数据！');
				 }
			 });

			 $("#task_TaskCreditScoreAlg-addBtn").click(function(){
				 var total = $("#TaskCreditScoreAlg-grid").datagrid('getData').total;
				 var modleType = $("#modleType").val();
				 if(total && (modleType == 1 || modleType == 5)){
					 layer.msg("当前策略不能配置多种算法！");
					 return ;
				 }
				 $("#task_TaskCreditScoreAlg_add_dialog").dialog('open').dialog('center').dialog('setTitle', '增加任务策略算法信息');
				 url = "assignment/taskManage/addTaskCreditScoreAlg.do";
				 $("#actualScore").numberbox('clear');
				 $("#initScore").numberbox('clear');
				 $('#percent').numberbox('setValue',1);
				 $('#optionValue').textbox('clear');
				//检验
				$("#initScore").numberbox({
				    "onChange":function(){
				    	$("#actualScore").numberbox('setValue',($("#initScore").numberbox('getValue')*$("#percent").numberbox('getValue')));
				    }
				 });

				$("#percent").numberbox({
				    "onChange":function(){
				    	$("#actualScore").numberbox('setValue',($("#initScore").numberbox('getValue')*$("#percent").numberbox('getValue')));
				    }
				 });
			 });

			//修改配置策略算法
			 $("#task_TaskCreditScoreAlg-modifyBtn").click(function(){
				 var row = $("#TaskCreditScoreAlg-grid").datagrid('getSelected');
				 var rows = $("#TaskCreditScoreAlg-grid").datagrid('getChecked');
				 if (row) {
					 if (rows.length > 1) {
						 layer.msg("最多只能选择一行！");
					 } else {
						 var taskStrategyId = row.taskStrategyId;
							$("#task_TaskCreditScoreAlg_add_dialog").dialog('open').dialog('center').dialog('setTitle', '修改任务策略算法信息');
							$("#task_TaskCreditScoreAlg_add_form").form('clear');
							$("#task_TaskCreditScoreAlg_add_form").form('load', row);
							$("#taskStrategyId").val(taskStrategyId);
							//$("#strategyId").val(id);
							url = 'assignment/taskManage/modifyTaskCreditScoreAlg.do';
							//检验
							$("#initScore").numberbox({
							    "onChange":function(){
							    	$("#actualScore").numberbox('setValue',($("#initScore").numberbox('getValue')*$("#percent").numberbox('getValue')));
							    }
							 });

							$("#percent").numberbox({
							    "onChange":function(){
							    	$("#actualScore").numberbox('setValue',($("#initScore").numberbox('getValue')*$("#percent").numberbox('getValue')));
							    }
							 });
					 }
				 } else {
					layer.msg('请选择一行数据！');
				 }
			 });

			//删除配置策略
			 $("#task_TaskCreditScoreAlg-removeBtn").click(function(){
				 var row = $("#TaskCreditScoreAlg-grid").datagrid('getChecked');
				 if (row.length != 0) {
					 $.messager.confirm('温馨提示', '确认删除此信息吗?',function(data){
						 if(data){
							 var taskCreditScoreAlgIds = [];
							 var strategyId = "";
							 for ( var i in row) {
								 taskCreditScoreAlgIds[i] = row[i].id;
								 strategyId = row[i].taskStrategyId;
							 }
							 taskCreditScoreAlgIds = $.toJSON(taskCreditScoreAlgIds);
							 $.ajax({ type : "POST",
									url : 'assignment/taskManage/removeTaskCreditScoreAlg.do',
									data : {taskAlgIdsJson:taskCreditScoreAlgIds,strategyId:strategyId}, 
									async : "false",
									success : function(data) {
										closeLoading();
										if (data) {
											$("#TaskCreditScoreAlg-grid").datagrid('reload'); // 刷新数据网格
										}
							} });
						 }
					 });
				 }
			 });
			
			//保存配置策略算法信息
			 $("#task-TaskCreditScoreAlg-saveBtn").click(function(){
				$("#task_TaskCreditScoreAlg_add_form").form('submit', { url : url, onSubmit : function() {
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
						$("#task_TaskCreditScoreAlg_add_dialog").dialog('close'); // 关闭回话框
						$("#TaskCreditScoreAlg-grid").datagrid('reload'); // 刷新数据网格
					}
				} });
			 });
			 //加载数据 
			 $("#task-grid").datagrid(dgOption);

			 	/** 
				 * 扩展easyui的validator插件rules，支持更多类型验证 
				 */  
				$.extend($.fn.validatebox.defaults.rules, { 
					 english : {// 验证英语  
			                validator : function(value) {  
			                    return /^[A-Za-z]+$/i.test(value);  
			                },  
			                message : '请输入英文'  
			            }
				}); 
	});

