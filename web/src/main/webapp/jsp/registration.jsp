<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/jsp/error/error.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<html>
	<head>
		<title>Регистрация</title>
	</head>
	<body>
		<form name="registrationForm" method="POST" action="controller">
			<input type="hidden" name="command" value="registration" /> 
			Введите ваши данные:<br/>
			<table>
				<tr>
					<td>Имя:</td>
					<td><input type="text" name="firstname" value="" size="20"/></td>
				</tr>
				<tr>
					<td>Фамилия:</td>
					<td><input type="text" name="lastname" value="" size="20" /></td>
				</tr>
				<tr>
					<td>Логин:</td>
					<td><input type="text" name="login" value="" size="20"/></td>
				</tr>
				<tr>
					<td>Пароль:</td>
					<td><input type="password" name="password" value="" size="20" /></td>
				</tr>
				<tr>
					<td>№ Счета:</td>
					<td><input type="text" name="accountid" value="" size="20"/></td>
				</tr>
				<tr>
					<td>Валюта Счета:</td>
					<td>
						<input type="radio" name="currency" value="BYR" checked="checked"/>BYR
						<input type="radio" name="currency" value="USD"/>USD
						<input type="radio" name="currency" value="EUR"/>EUR<br/>
					</td>
				</tr>
			</table>			 
			${operationMessage} 
			${errorUserExsists} <br />
			<input type="submit" value="Зарегистрировать" />
			<a href="controller?command=back">Вернуться обратно</a>
		</form>
		
		
	</body>
</html>