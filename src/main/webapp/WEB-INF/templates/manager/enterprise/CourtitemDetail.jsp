<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
.tablelist th,td{
	border-right:1px dashed #D8D8D8;
	border-bottom:4px solid #D8D8D8;
	}

</style>
<table align="center" width="99%"  cellspacing="0px">
			<tr align="left" height="50px" style="background-color:#D8D8D8">
				<th>生效法律文书确定的义务:</th>
				<th>同PARTYTYPENAME:</th>
				<th>做出执行依据单位:</th>
				<th>被执行人姓名/名称:</th>
				<th>URL:</th>
				<th>失信被执行人行为具体情形:</th>
				<th>年龄:</th>
				<th>案号:</th>
				<th>立案时间:</th>
				<th>发布时间:</th>
				<th>个人/对公客户，580表示自然人，581表示企业或其他组织:</th>
				<th>执行法院:</th>
				<th>身份证号/组织机构代码:</th>
				<th>源网站给该数据的唯一标识:</th>
				<th>性别:</th>
				<th>关注次数:</th>
				<th>被执行人的履行情况:</th>
				<th>:</th>
				<th>执行依据文号:</th>
				<th>省份:</th>
				<th>是否存在黑名单中:</th>
				<th>录入时间:</th>
			</tr>
			<c:forEach items="${list}" var="courtitem">
				<tr align="left" height="50px">
					<td>${courtitem.duty}</td>
					<td>${courtitem.sType}</td>
					<td>${courtitem.gistunit}</td>
					<td>${courtitem.iname}</td>
					<td>${courtitem.url}</td>
					<td>${courtitem.disrupttypename}</td>
					<td>${courtitem.age}</td>
					<td>${courtitem.casecode}</td>
					<td>${courtitem.regdate}</td>
					<td>${courtitem.publishdate}</td>
					<td>${courtitem.partytypename}</td>
					<td>${courtitem.courtname}</td>
					<td>${courtitem.cardnum}</td>
					<td>${courtitem.key}</td>
					<td>${courtitem.sexy}</td>
					<td>${courtitem.focusnumber}</td>
					<td>${courtitem.performance}</td>
					<td>${courtitem.sId}</td>
					<td>${courtitem.gistid}</td>
					<td>${courtitem.areaname}</td>
					<td>${courtitem.isBlacklist}</td>
					<td>${courtitem.buildTime}</td>
				</tr>
			</c:forEach>
</table>




			        

