<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Reset Password</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="<c:url value="/resources/js/script.js" />"></script>
</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<h1 style="text-align: center; color: #E08B14; font-size: 24px;">Reset
				Password</h1>
			<br>
			<h3 style="text-align: center; font-size: 18px;">Thank you for
				validating your Account. Please Reset Your Password.</h3>
			<br>

			<form:form method="POST" commandName="ResetForm"
				action="resetForm.do">
				<form:errors path="*" cssClass="errorblock" element="div" />
				<center>
					<table>
						<tr>
							<td>UserName <span id="colon">:</span>
							</td>
							<td><form:label path="empId" placeholder="Employee ID"
									cssClass="inputText">${ResetForm.empId}</form:label></td>

						</tr>
						<tr>
							<td>Password <span id="colon">:</span>
							</td>
							<td><form:password path="employeePwd"
									placeholder="Create new Password" cssClass="inputText" /></td>
							<td><form:errors path="employeePwd" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Confirm Password<span id="colon">:</span>
							</td>
							<td><form:password path="employeeConfirmPwd"
									placeholder="Confirm new Password" cssClass="inputText" /></td>
							<td><form:errors path="employeeConfirmPwd" cssClass="error" /></td>
						</tr>
						<form:hidden path="empId" />
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