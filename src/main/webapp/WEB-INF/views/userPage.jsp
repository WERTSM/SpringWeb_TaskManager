<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.khmelev.tm.dto.UserDTO" %>
<html>
    <head>
	    <titleСтраничка юзера</title>
    </head>
    <body>

        <div align = "center">
        <%UserDTO userDTO = (UserDTO) request.getAttribute("userDTO");%>
		<c:set var = "user" value = "<%=userDTO%>"/>


		<h1> User page: </h1>
        <p>
            <b> Your login : ${user.login} </b>
        </p>
        <p>
            <a href = "${pageContext.servletContext.contextPath}/projects"> <input type="button" value="Проекты"/> </a>
        </p>
        <p>
            <a href = "${pageContext.servletContext.contextPath}/tasks"> <input type="button" value="Задачи"/> </a>
        </p>
        <p>
            <a href="${pageContext.servletContext.contextPath}/userEdit"> <input type="button" value="Изменить данные пользователя"/> </a>
        </p>
        <p>
            <a href="${pageContext.servletContext.contextPath}/logout"> <input type="button" value="Exit"/> </a>
        </p>
        </div>
    </body>
</html>