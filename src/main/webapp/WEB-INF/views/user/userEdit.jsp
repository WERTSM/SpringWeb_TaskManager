<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<html>
    <head>
	    <title>User edit page</title>
    </head>

    <body>
    <h1 align = "center"> Меняем персональные данные?: </h1>

    <form action = "${pageContext.servletContext.contextPath}/userEdit" method = "POST">
	<table align = "center">
		<tr>
			<td> Login: </td>
			<td>
				<input type = "text" name = "login" required>
			</td>
		</tr>
		<tr>
			<td> Password: </td>
			<td>
				<input type = "password" name = "password" required>
			</td>
		</tr>
		<tr> </tr>
		<tr>
		    <td>
		        <input align = "left" type = "submit" value = "Да. Меняем!"/>
		    </td>
		    <td align = "right">
		        <a align = "right" href = "${pageContext.servletContext.contextPath}/login">
                    <input type = "button"  value = "Нет"/>
                 </a>
            </td>
		</tr>
	</table>
    </form>
</body>