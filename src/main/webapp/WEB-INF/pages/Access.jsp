<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Enable Access</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="<c:url value="/resources/js/script.js" />"></script>
</head>


<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<h1 style="text-align: center; color: #E08B14; font-size: 24px;">&nbsp;&nbsp;&nbsp;&nbsp;Enable Access</h1>
			<br>
			<form:form method="POST" commandName="access"
				action="enableAccessRequest.do">

				<form:errors path="*" cssClass="errorblock" element="div" />
				<center>
					<table>
						<tr>
								<td>Team <span id="colon">:</span> </td>
								<td><form:select path="employeeTeam" cssClass="inputText" items="${employeeTeamList}"/></td>
								<td><form:errors path="employeeTeam" cssClass="error" /></td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr>
							<td></td>
							<td><input type="submit" /></td>
							<td></td>
						</tr>
					</table>
				</center>


			</form:form>
		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>



</body>
</html>


