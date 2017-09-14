<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
.tablelist th,td{
	border-right:1px dashed #D8D8D8;
	border-bottom:4px solid #D8D8D8;
	}

</style>
<table  align="center" width="99%" cellspacing="0px" class="tablelist">
			<tr align="center" height="50px" style="background-color:#D8D8D8">
				<th width="150px">资讯名称:</th>
				<th width="150px">来源：</th>
				<th width="150px">时间：</th>
				<th width="150px">内容：</th>
				<th>企业ID：</th>
			</tr>
			<c:forEach items="${list}" var="enterNews">
				<tr align="left" height="50px">
					<td width="150px">${enterNews.newsName}</td>
					<td width="150px">${enterNews.newsFrom}</td>
					<td width="150px">${enterNews.newsTime}</td>
					<td width="150px">
							<textarea style="width:150px;height:50px;">${enterNews.content}</textarea>
					</td>
					<td>${enterNews.companyId}</td>
				</tr>
			</c:forEach>
</table>




			        

