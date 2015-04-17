<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Project Home</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/script.js" />"></script>
</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<h3 id="welcomeMessage">Welcome, ${employee.employeeId}&nbsp!
				${employee.employeeTeam}</h3>
		</div>

		<table id="newSignUpRequests">
			<thead>
				<tr>
					<th>Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Gender</th>
					<th>Team</th>
					<th>Address</th>
					<th>Mobile</th>
					<th>Mail</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty newSignupRequestList}">
					<c:forEach var="listValue" items="${newSignupRequestList}">
						<tr>
							<td>${listValue.employeeId}</td>
							<td>${listValue.employeeFName}</td>
							<td>${listValue.employeeLName}</td>
							<td>${listValue.employeeGender}</td>
							<td>${listValue.employeeTeam}</td>
							<td>${listValue.employeeAddress}</td>
							<td>${listValue.employeeMobile}</td>
							<td>${listValue.employeeMail}</td>
							<td><a>Enable</a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<br>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>



</body>
</html>

