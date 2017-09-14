<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
.tablelist th,td{
	border-right:1px dashed #D8D8D8;
	border-bottom:4px solid #D8D8D8;
	}

</style>

<table align="center" width="99%"  cellspacing="0px">
			<tr align="center" height="50px" style="background-color:#D8D8D8">
				<th width="150px">企业ID:</th>
				<th width="150px">被投资企业ID：</th>
				<th width="150px">企业名称：</th>
				<th width="150px">法定代表人：</th>
				<th>登记状态：</th>
			</tr>
			<c:forEach items="${list}" var="Abroad">
				<tr align="left" height="50px">
					<td width="150px">${Abroad.companyId}</td>
					<td width="150px">${Abroad.toCompanyId}</td>
					<td width="150px">${Abroad.companyName}</td>
					<td width="150px">${Abroad.legalRepPersion}</td>
					<td>${Abroad.recordStatus}</td>
				</tr>
			</c:forEach>
</table>




			        

