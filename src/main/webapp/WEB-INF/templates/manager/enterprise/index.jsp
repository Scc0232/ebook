<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    (function() {
        var dg = $('#enterpriseInfo-grid');// 数据网格
        var rdg = $('#enterpriseInfo-resourgrid');// 数据网格
        var search = $('#enterpriseInfo-search');// 搜索div
        var searchBtn = $('#enterpriseInfo-searchBtn');// 搜索按钮
        var resetBtn = $('#enterpriseInfo-resetBtn');// 重置按钮
        var vietinbanhBtn = $('#enterpriseInfo-vietinbanhBtn');// 工商信息按钮
        var enterpriseBtn = $('#enterpriseInfo-enterpriseBtn');//企业信息按钮
        var lawsuitBtn=('#enterpriseInfo-lawsuitBtn');//诉讼信息按钮
        var stockBtn=('#enterpriseInfo-stockBtn');//股东信息按钮
        var enterpriseNewsBtn=('#enterpriseInfo-enterpriseNewsBtn');//企业咨询
        var sonEnterpriseBtn=('#enterpriseInfo-sonEnterpriseBtn');//分支机构
        var mainMemberBtn=('#enterpriseInfo-mainMemberBtn');//主要成员
        var editRecordBtn=('#enterpriseInfo-editRecordBtn');//修改记录
   		var abroadBtn=('#enterpriseInfo-abroadBtn');//对外投资findCourtitemName
   		var CourtitemBtn=('#enterpriseInfo-CourtitemBtn');//失信信息
   		var dlg = $('#enterpriseInfo-dlg');// 回话框
        var form = $('#enterpriseInfo-fm');// 表单
        var url;// 提交url

        // 数据网格首选项
        var dgOption = {
            pagination : true,// 分页
            singleSelect : true,// 单选
            url : '${basePath}companyInfo/findCompanyInfoPagination.do',// 数据来源地址
            selectOnCheck : true,
            columns : [ [ { field : 'id', title : '选择', checkbox : true },
                          { field : 'companyName', title : '企业名称', width : 200 }, 
                          { field : 'companyPhoneNo', title : '企业联系电话', width : 200 },
                          { field : 'address', title : '通讯地址', width : 200 },
                          { field : 'email', title : '电子邮箱', width : 200 },
                          { field : 'managementStatus', title : '企业经营状态', width : 200 },
                          { field : 'employeeCount', title : '从业人数', width : 200 },
                          { field : 'focus', title : '关注数', width : 200 },
                          { field : 'browseCount', title : '浏览数', width : 200 },
                          { field : 'industry', title : '所属行业', width : 200 }
                          ] ],
            // 工具栏
            toolbar : '#enterpriseInfo-tool',fit:true,fitColumns : true };

        $(searchBtn).bind('click', function() {
            var param = toObject(search);
            $(dg).datagrid('load', param);
        });

        $(resetBtn).bind('click', function() {
            $(search).form('clear');
        });
        //企业信息查询 
        $(enterpriseBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '企业信息详情',
            	    width: 800,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findenterpriseInfobyId.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
      //工商信息查询 
        $(vietinbanhBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '工商信息详情',
            	    width: 800,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findVietinbanhInfoById.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
        //诉讼信息查询 
        $(lawsuitBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '诉讼信息详情',
            	    width: 1000,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findLawsuitbyId.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
        //股东信息查询 
        $(stockBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '股东信息查询 ',
            	    width: 1200,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findStockbyId.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
        //企业咨询
        $(enterpriseNewsBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '企业咨询 ',
            	    width: 800,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findEnterpriseNewsId.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
      //分支机构
        $(sonEnterpriseBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '分支机构 ',
            	    width: 800,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findSonEnterpriseId.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
        //主要成员
        $(mainMemberBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '主要成员 ',
            	    width: 800,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findMainMemberId.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
        //修改记录
        $(editRecordBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '修改记录 ',
            	    width: 800,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findEditRecordId.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
        //对外投资
        $(abroadBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '对外投资 ',
            	    width: 800,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findAbroadInvestmentId.do?companyId=' + row.id,
            	    modal: true
                });
            } else {
                layer.msg('必须选择一行!');
            }
        });
        //失信信息
        $(CourtitemBtn).bind('click', function() {
            var row = $(dg).datagrid('getSelected');
            var rows = $(dg).datagrid('getChecked');
           if(rows.length >1){
        	   layer.msg('只能选择一行');
        	   return;
           }
            if (row) {
                $('#enterpriseInfo-dlg').dialog({
            	    title: '失信信息 ',
            	    width: 1200,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href: '${basePath}companyInfo/findCourtitemName.do?companyName=' + row.companyName,
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


<div id="enterpriseInfo-tool">
    <div id="enterpriseInfo-search" style="padding-top: 10px;">
        <label style="padding-left: 10px;">企业名称:</label> 
        <input name="companyName" class="easyui-textbox" style="height: 26px;" /> 
		<a id="enterpriseInfo-searchBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
	</div>
	
	<div id="enterpriseInfo-search" style="padding-top: 5px; padding-bottom: 5px; padding-left: 5px;">
		<a id="enterpriseInfo-enterpriseBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">企业信息</a>
		<a id="enterpriseInfo-vietinbanhBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">工商信息</a>
		<a id="enterpriseInfo-lawsuitBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">诉讼信息</a>
		<a id="enterpriseInfo-stockBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">股东信息</a>
		<a id="enterpriseInfo-enterpriseNewsBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">企业咨询</a>
		<a id="enterpriseInfo-sonEnterpriseBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">分支机构</a>
		<a id="enterpriseInfo-mainMemberBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">主要成员</a>
		<a id="enterpriseInfo-editRecordBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">修改记录</a>
		<a id="enterpriseInfo-abroadBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">对外投资</a>
		<a id="enterpriseInfo-CourtitemBtn" href="#" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-edit'">失信信息</a>
	
	
	</div>
</div>
<div id="enterpriseInfo-grid"></div>

<div id="enterpriseInfo-dlg"></div>