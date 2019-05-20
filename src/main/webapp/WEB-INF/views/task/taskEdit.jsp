<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.khmelev.tm.dto.ProjectDTO" %>
<%@ page import="java.util.Date" %>
<html>
    <head>
	    <title>Task edit</title>
    </head>
<body>
<div align = "center">
        <h1> Редактирование задачи </h1>
        <form action = "<%=request.getContextPath()%>/taskEdit" method = "post">
        <input type = "hidden" name = "idTask" value = ${task.id}>
            <p> Название: </p>
            <input type = "text" name = "nameTask" value = "" placeholder = "Название задачи"/>
            <p> Описание: </p>
            <input type = "text" name = "descTask" value = "" placeholder = "Описание задачи"/>
            <p> Дата начала: </p>
            <input type = "date" name = "dateStart" value = "<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd"/>"/>
            <p>Дата окончания: </p>
            <input type = "date" name = "dateFinish" value = "<fmt:formatDate value="<%=new Date()%>"  pattern="yyyy-MM-dd"/>"/>
            <p>

            <%List list = (List<ProjectDTO>) request.getAttribute("projects");%>
            <c:set var="list" value="<%=list%>"/>

            <select name="taskProjectId">
                <option selected value=""><c:out value="Без проекта"/></option>
                <option disabled> Выберите проект </option>
                <c:forEach var = "project" items="${list}">
                    <option value = "${project.id}"> ${project.name} </option>
                </c:forEach>
            </select>
            </p>
            <p> <button type="submit"> Сохранить изменения </button> </p>
        </form>
    </div>
    </body>
</html>