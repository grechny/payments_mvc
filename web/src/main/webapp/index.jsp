<%@ page contentType="text/html; charset=UTF-8" 
		 pageEncoding="UTF-8" errorPage="/assets/pages/error/error.jsp"%>
		 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<html>
	<head>
		<title>Авторизация</title>
	</head>
	<body>
		<form name="loginForm" method="POST" action="controller">
			<input type="hidden" name="command" value="login" />
			Введите ваш логин и пароль: <br/>
			<table>
				<tr>
					<td>Логин:</td>
					<td><input type="text" name="login" value="" size="20"/></td>
				</tr>
				<tr>
					<td>Пароль:</td>
					<td><input type="password" name="password" value="" size="20" /></td>
				</tr>
			</table>			 
			${errorLoginOrPassword} <br />
			<input type="submit" value="Войти" /> 
			<a href="controller?command=gotoregistration">Регистрация</a>
		</form>
	</body>
</html>