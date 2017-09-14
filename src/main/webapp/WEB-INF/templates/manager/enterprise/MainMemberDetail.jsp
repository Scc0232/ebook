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
				<th width="150px">企业id:</th>
				<th width="150px">企业名称：</th>
				<th width="150px">职位名称：</th>
				<th width="150px">成员名称：</th>
				<th>所占公司股份份额：</th>
			</tr>
			<c:forEach items="${list}" var="main">
				<tr align="left" height="50px">
					<td width="150px">${main.companyId}</td>
					<td width="150px">${main.companyName}</td>
					<td width="150px">${main.jobName}</td>
					<td width="150px">${main.persionName}</td>
					<td>${main.share}</td>
				</tr>
			</c:forEach>
</table>




			        

