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
				<th width="150px">企业名称:</th>
				<th width="150px">企业法人：</th>
				<th width="150px">登记状态：</th>
				<th width="150px">父企业id：</th>
			</tr>
			<c:forEach items="${list}" var="son">
				<tr align="left" height="50px">
					<td width="150px">${son.companyName}</td>
					<td width="150px">${son.legalRepPersion}</td>
					<td width="150px">${son.recordStatus}</td>
					<td width="150px">${son.companyId}</td>
				</tr>
			</c:forEach>
</table>




			        

