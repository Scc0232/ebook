<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
/* .tablelist{
	border: 1px solid #D8D8D8;
} */
/* .tablelist th,td{
	border: 1px solid #D8D8D8;  
	}*/
.tablelist th,td{
	border-right:1px dashed #D8D8D8;
	border-bottom:4px solid #D8D8D8;
	}


</style>
<table align="center" width="99%"  cellspacing="0px">
			<tr align="center" height="50px" style="background-color:#D8D8D8">
				<th width="90px">股东名：</th>
				<th width="90px">股东类型:</th>
				<th width="90px">证件类型:</th>
				<th width="90px">证件号码:</th>
				<th width="90px">认缴出资:</th>
				<th width="90px">出资时间:</th>
				<th width="90px">认缴出资方式:</th>
				<th width="90px">实缴出资：</th>
				<th width="90px">实缴时间：</th>
				<th width="100px">实缴出资方式：</th>
				<th width="100px">法定代表人：</th>
				<th>登记状态：</th>
			</tr>
			<c:forEach items="${list}" var="stock">
				<tr align="left" height="50px">
					<td width="90px">${stock.name}</td>
					<td width="90px">${stock.stockType}</td>
					<td width="90px">${stock.cardType}</td>
					<td width="90px">${stock.cardNo}</td>
					<td width="90px">${stock.subcribe}</td>
					<td width="90px">${stock.subcribeTime}</td>
					<td width="90px">${stock.subcribeType}</td>
					<td width="90px">${stock.realSubcribe}</td>
					<td width="90px">${stock.realSubcribeTime}</td>
					<td width="100px">${stock.realSubcribeType}</td>
					<td width="100px">${stock.legalRepPersion}</td>
					<td>${stock.recordStatus}</td>
				</tr>
			</c:forEach>
</table>	




			        

