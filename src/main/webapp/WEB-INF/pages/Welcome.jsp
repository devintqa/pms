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
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

<script>
	function manageUser(userId, action) {
		var entity = {
			"user" : userId,
			"action" : action
		};
		$.ajax({
			type : 'POST',
			url : 'manageAccess.do',
			contentType : 'application/json',
			data : JSON.stringify(entity),
			success : function(response) {
				if(response == '1'){
					location.reload();	
				}
			},
			error : function(err) {
				alert("error - " + err);
			}
		});
	}
</script>
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

		<c:if test="${employee.employeeTeam eq 'Admin'}">
			<h1 style="text-align: center; color: #E08B14; font-size: 24px;">New
				Signup Requests</h1>
			<table id="newSignUpRequests">
				<thead>
					<tr>
						<th>User Name</th>
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
								<td><c:if test="${listValue.enabled eq '0'}">
										<a
											href="javascript:manageUser('${listValue.employeeId}', 'enable');"
											class="userAction">Enable</a>
									</c:if> <c:if test="${listValue.enabled eq '1'}">
										<a
											href="javascript:manageUser('${listValue.employeeId}', 'disable');"
											class="userAction">Disable</a>
									</c:if></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
		</c:if>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>



</body>
</html>

