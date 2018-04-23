<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user list</title>

<style type="text/css">
	.tb {
		border: 1px solid #333;
	}
	.tb td {
		border: 1px solid #333;
		width: 200px;
	}
</style>
</head>
<body>
	<table class="tb">
	<tr>
		<td>id</td>
		<td>username</td>
		<td>sex</td>
		<td>age</td>
	</tr>
	<c:forEach items="${userList }" var="user">
		<tr>
			<td>${user.id }</td>
			<td>${user.username }</td>
			<td>${user.sex }</td>
			<td>${user.age }</td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>