<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Операции</title>
	</head>
	<body>
		<table border="1">
			<tr bgcolor="#CCCCCC">
				<td align="center"><strong>Дата</strong></td>
				<td align="center"><strong>Описание</strong></td>
				<td align="center"><strong>Сумма</strong></td>
				<td align="center"><strong>№ Клиента</strong></td>
				<td align="center"><strong>№ Счета</strong></td>
			</tr>
			<c:forEach var="opeartion" items="${operationsList}">
				<tr>
					<td><c:out value="${ opeartion.date }" /></td>
					<td><c:out value="${ opeartion.description }" /></td>
					<td align="right">
						<fmt:formatNumber value="${ opeartion.amount }" type="currency" minFractionDigits="2"  currencySymbol=""/>
					</td>
					<td align="center"><c:out value="${ opeartion.userId }" /></td>
					<td align="center"><c:out value="${ opeartion.accountId }" /></td>
				</tr>
			</c:forEach>
		</table>
		<a href="controller?command=backadmin">Вернуться обратно</a>
		<a href="controller?command=logout">Выйти из системы</a>
	</body>
</html>