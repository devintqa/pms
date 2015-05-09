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
		<c:when test="${employeeObj.employeeTeam eq 'Technical'}">
			<ul>
				<li><a id="emphome"
					href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
				</li>
				<li><a id="project"
					href="/pms/emp/myview/buildProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Build Project</a></li>
				<li><a id="subProject"
					href="/pms/emp/myview/buildSubProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Build Sub Project</a></li>
				<li><a id="description"
					href="/pms/emp/myview/buildProjectDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}">Build Description</a></li>
				<li><a id="profile"
					href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update Profile</a></li>
				<li><a id="logout"
					href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</c:when>
		
		<c:when test="${employeeObj.employeeTeam eq 'Account'}">
			<ul>
				<li><a id="emphome"
					href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
				</li>
				<li><a id="profile"
					href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update Profile</a></li>
				<li><a id="logout"
					href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</c:when>
		
		<c:when test="${employeeObj.employeeTeam eq 'Admin'}">
			<ul>
				<li><a id="emphome"
					href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
				</li>
				<li><a id="profile"
					href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update Profile</a></li>
				<li><a id="logout"
					href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</c:when>
		
		<c:when test="${employeeObj.employeeTeam eq 'Management'}">
			<ul>
				<li><a id="emphome"
					href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
				</li>
				<li><a id="profile"
					href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update Profile</a></li>
				<li><a id="logout"
					href="${pageContext.request.contextPath}/logout">Logout</a></li>
			</ul>
		</c:when>
		
		<c:when test="${employeeObj.employeeTeam eq 'Purchase'}">
			<ul>
				<li><a id="emphome"
					href="/pms/emp/myview/${employeeObj.employeeId}" class="active">Home</a>
				</li>
				<li><a id="profile"
					href="/pms/emp/myview/details/edit/${employeeObj.employeeId}">Update Profile</a></li>
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