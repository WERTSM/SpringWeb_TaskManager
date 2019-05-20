<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.khmelev.tm.dto.TaskDTO" %>
<%@ page import="ru.khmelev.tm.util.ConverterUtil" %>
<html>
    <head>
	    <title>Task list</title>
    </head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<body>
	<p  align = "center">
    <b> Tasks :</b>
	</p>

	<table border="5" align = "center">
		<tr>
		    <th>Id</th>
			<th>Name</th>
			<th>Description</th>
			<th>Start date</th>
			<th>Finish date</th>
    		<th>Create date</th>
    		<th>Status</th>
    		<th>Project ID</th>
    		<th>Edit</th>
    		<th>Delete</th>
		</tr>
		<%List list = (List<TaskDTO>) request.getAttribute("tasks");%>
		<c:set var="list" value="<%=list%>"/>
		<c:forEach items="${list}"  var="task" varStatus="status">
			<tr>
				<td>${task.id}</td>
				<td>${task.name}</td>
				<td>${task.description}</td>
				<td>${ConverterUtil.convertDateFormat(task.dateStart)}</td>
				<td>${ConverterUtil.convertDateFormat(task.dateStart)}</td>
				<td>${ConverterUtil.convertDateFormat(task.dateFinish)}</td>
				<td>${task.status.displayName}</td>
				<td>${task.projectId}</td>
				<td>
				    <form method = "GET"  action = "${pageContext.servletContext.contextPath}/taskEdit">
                    <input type = "hidden" name = "TskId" value = ${task.id}>
                    <input type = "submit" value = "edit"/>
                    </form>
                </td>
                <td>
                    <form method = "POST" action = "${pageContext.servletContext.contextPath}/taskDelete">
                    <input type = "hidden" name = "TskId" value=${task.id}>
                    <input type = "submit" value = "delete" />
                    </form>
                </td>
	        </tr>
		</c:forEach>
	</table>
	<tr> </tr>
	<table align = "center">
    <tr>
        <td align = "left">
            <a href = "${pageContext.servletContext.contextPath}/taskCreate">
                <input type = "button" value = "Add Task"/>
            </a>
        </td>
        <td align = "right">
            <a href="${pageContext.servletContext.contextPath}/user">
                <input type="button" value="Main"/>
            </a>
        </td>
    </tr>
    </table>
    </body>
</html>