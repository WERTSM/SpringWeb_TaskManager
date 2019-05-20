<html>
    <head>
	    <title>Login page</title>
    </head>
    <body>

    <h1 align = "center"> Login: </h1>

    <form action = "${pageContext.servletContext.contextPath}/login" method = "POST">
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
		        <input align = "left" type = "submit" value = "Login"/>
		    </td>
		    <td align = "right">
		        <a align = "right" href = "${pageContext.servletContext.contextPath}/registry">
                    <input type = "button"  value = "Registration"/>
                 </a>
            </td>
		</tr>
	</table>
    </form>
</body>