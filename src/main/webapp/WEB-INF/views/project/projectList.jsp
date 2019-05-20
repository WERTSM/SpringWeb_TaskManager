<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.khmelev.tm.dto.ProjectDTO" %>
<%@ page import="ru.khmelev.tm.util.ConverterUtil" %>
<html>
    <head>
	    <title>Project list</title>
    </head>
<body>
	<p  align = "center">
    <b> Projects :</b>
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
    		<th>Edit</th>
    		<th>Delete</th>
		</tr>
		<%List list = (List<ProjectDTO>) request.getAttribute("projects");%>
		<c:set var="list" value="<%=list%>"/>
		<c:forEach items="${list}"  var="project" varStatus="status">
			<tr>
				<td>${project.id}</td>
				<td>${project.name}</td>
				<td>${project.description}</td>
				<td>${ConverterUtil.convertDateFormat(project.dateStart)}</td>
				<td>${ConverterUtil.convertDateFormat(project.dateStart)}</td>
				<td>${ConverterUtil.convertDateFormat(project.dateFinish)}</td>
				<td>${project.status.displayName}</td>
				<td>
				    <form method = "GET"  action = "${pageContext.servletContext.contextPath}/projectEdit">
                    <input type = "hidden" name = "PrId" value = ${project.id}>
                    <input type = "submit" value = "edit"/>
                    </form>
                </td>
                <td>
                    <form method = "POST" action = "${pageContext.servletContext.contextPath}/projectDelete">
                    <input type = "hidden" name = "PrId" value=${project.id}>
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
            <a href = "${pageContext.servletContext.contextPath}/projectCreate">
                <input type = "button" value = "Add Project"/>
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