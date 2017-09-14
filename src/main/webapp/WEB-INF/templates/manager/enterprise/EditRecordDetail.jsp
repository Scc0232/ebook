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
				<th width="150px">变更类型(法人变更、经营范围变更等):</th>
				<th width="150px">变更时间：</th>
				<th width="150px">修改前：</th>
				<th width="150px">修改后：</th>
				<th>企业ID：</th>
			</tr>
			<c:forEach items="${list}" var="edit">
				<tr align="left" height="50px">
					<td width="150px">${edit.editType}</td>
					<td width="150px">${edit.editTime}</td>
					<td width="150px">
						<textarea style="width:150px;height:50px;">${edit.beforeEdit}</textarea>
					</td>
					<td width="150px">
						<textarea style="width:150px;height:50px;">${edit.afterEdit}</textarea>
					</td>
					<td>${edit.companyId}</td>
				</tr>
			</c:forEach>
</table>




			        

