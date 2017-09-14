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
				<th width="150px">被执行人姓名/名称:</th>
				<th width="150px">身份证/组织机构代码：</th>
				<th width="150px">执行法院：</th>
				<th width="150px">立案时间：</th>
				<th width="150px">案号：</th>
				<th>执行标的：</th>
			</tr>
			<c:forEach items="${list}" var="lawsuit">
				<tr align="left" height="50px">
					<td width="150px">${lawsuit.name}</td>
					<td width="150px">${lawsuit.cardNo}</td>
					<td width="150px">${lawsuit.court}</td>
					<td width="150px">${lawsuit.registrineTime}</td>
					<td width="150px">${lawsuit.caseNo}</td>
					<td>${lawsuit.zxbd}</td>
				</tr>
			</c:forEach>
</table>	




			        

