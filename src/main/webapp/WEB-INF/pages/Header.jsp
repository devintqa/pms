<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PMS</title>
</head>
<body>
	<nav id="desktop"> <c:choose>
		<c:when test="${employee.empId > '0'}">
			<ul>
				<li><a id="emphome"
					href="/pms/emp/myview/myhome/${employee.empId}" class="active">Home</a>
				</li>
				<li><a id="about"
					href="/pms/emp/myview/buildProject/${employee.empId}">Build
						Project</a></li>
				<li><a id="profile"
					href="/pms/emp/myview/details/edit/${employee.empId}">Update Profile</a></li>
				<li><a id="logout"
					href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</c:when>
		<c:otherwise>
			<ul>
				<li><a id="home" href="/pms/emp/login">Login</a></li>
				<li><a id="register" href="/pms/emp/signup">Register</a></li>
				<li><a id="help" href="/pms/emp/forgotpassword">Forgot
						Password</a></li>
			</ul>
		</c:otherwise>
	</c:choose>
</body>
</html>