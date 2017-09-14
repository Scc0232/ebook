<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<table align="center" width="92%" height="92%">
	<tr height="15%">
		<th width="23%" align="center" >企业名称:</th>
		<td width="23%" align="left">${enterpriseInfo.companyName}</td>
		<th width="23%" align="center">注册号：</th>
		<td align="left">${enterpriseInfo.registNo}</td>
	</tr>
	<tr height="15%">
		<th width="23%" align="center">企业联系电话：</th>
		<td width="23%" align="left">${enterpriseInfo.companyPhoneNo}</td>
		<th width="23%" align="center">邮政编码：</th>
		<td align="left">${enterpriseInfo.postcode}</td>
	</tr>
	<tr height="15%">
		<th width="23%" align="center">通讯地址：</th>
		<td width="23%" align="left">${enterpriseInfo.address}</td>
		<th width="23%" align="center">电子邮箱：</th>
		<td align="left">${enterpriseInfo.email}</td>
	</tr>
	<tr height="15%">
		<th width="23%" align="center">本年度是否发生股权转让(0:否，1：是)：</th>
		<td width="23%" align="left">${enterpriseInfo.stockTransfer}</td>
		<th width="23%" align="center">企业经营状态：</th>
		<td align="left">${enterpriseInfo.managementStatus}</td>
	</tr>
	<tr height="15%">
		<th width="23%" align="center">是否有网站/网店(0:否，1：是)：</th>
		<td width="23%" align="left">${enterpriseInfo.isHaveWeb}</td>
		<th width="23%" align="center">是否有投资/购买其他公司股权(0:否，1：是)：</th>
		<td align="left">${enterpriseInfo.companyInverstment}</td>
	</tr>
	<tr height="15%">
		<th width="23%" align="center">从业人数：</th>
		<td width="23%" align="left">${enterpriseInfo.employeeCount}</td>
		<th width="23%" align="center">关注数：</th>
		<td align="left">${enterpriseInfo.focus}</td>
	</tr>
	<tr height="15%">
		<th width="23%" align="center">评级：</th>
		<td width="23%" align="left">${enterpriseInfo.level}</td>
		<th width="23%" align="center">坐标（经纬度）：</th>
		<td align="left">${enterpriseInfo.coords}</td>
	</tr>
	<tr>
		<th width="23%" align="center">浏览数：</th>
		<td width="23%" align="left">${enterpriseInfo.browseCount}</td>
		<th width="23%" align="center">所属行业：</th>
		<td align="left">${enterpriseInfo.industry}</td>
	</tr>

</table>	




			        

