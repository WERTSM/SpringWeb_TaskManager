<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ru.khmelev.tm.dto.ProjectDTO" %>
<%@ page import="java.util.Date" %>
<html>
    <head>
	    <title>Project create</title>
    </head>
<body>
<div align = "center">
        <h1> Создание проекта </h1>
        <form action = "<%=request.getContextPath()%>/projectCreate" method = "post">
            <p> Название: </p>
            <input type = "text" name = "nameProject" value = "" placeholder = "Название проекта"/>
            <p> Описание: </p>
            <input type = "text" name = "descProject" value = "" placeholder = "Описание проекта"/>
            <p> Дата начала: </p>
            <input type = "date" name = "dateStart" value = "<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd"/>"/>
            <p>Дата окончания: </p>
            <input type = "date" name = "dateFinish" value = "<fmt:formatDate value="<%=new Date()%>"  pattern="yyyy-MM-dd"/>"/>
            <p> <button type="submit"> Сохранить проект </button> </p>
        </form>
    </div>
    </body>
</html>