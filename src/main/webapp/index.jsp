<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
	    <title>ПРИВЕТСТВУЮ</title>
    </head>
    <body>
    <p align = "center"> <b> Привет ДРУГ: </b> </p>
    <p align = "center">
        <a href="${pageContext.servletContext.contextPath}/login"> <input type = "button"  value = "Login" align = "centre"/> </a>
    </p>
    </body>
</html>