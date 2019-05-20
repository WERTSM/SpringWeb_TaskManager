<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
	    <title>Регистрация нового юзера</title>
    </head>
    <body>

    <h1 align = "center"> Registration: </h1>

    <form action = "${pageContext.servletContext.contextPath}/registry" method = "POST">
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
		    <td align = "left">
		        <input type = "submit" value = "SAVE"/>
		    </td>
		    <td align = "right">
		        <a href = "${pageContext.servletContext.contextPath}/login">
                    <input type = "button"  value = "Back to login">
                 </a>
            </td>
		</tr>
	</table>
    </form>
</body>