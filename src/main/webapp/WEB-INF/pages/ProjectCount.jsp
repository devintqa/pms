<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Project Home</title>

<%@include file="Script.jsp" %>

<script>

	$(function() {
		var table = $("#newSignUpRequests")
				.dataTable();
		})
	
</script>

</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h3 id="welcomeMessage">Welcome, ${employeeObj.employeeId}&nbsp!</h3>
		</div>
		<c:if test="${employeeObj.employeeTeam eq 'Admin'}">
		<div>
			<h1 style="text-align: center; color: #007399; font-size: 24px;">Project Counts</h1>
			<table id="newSignUpRequests">
				<thead>
					<tr>
						<th>Tender Project</th>
						<th>Commencement Project</th>
						<th>Completed Project</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="text-align: center;">${tenderProjCount}</td>
						<td style="text-align: center;">${commencementProjCount}</td>
						<td style="text-align: center;">${completedProjCount}</td>
					</tr>
				</tbody>
			</table>
			</div>
			<br>
			<br>
		</c:if>
		</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>



</body>
</html>

