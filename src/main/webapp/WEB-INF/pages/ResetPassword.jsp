<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Reset Password</title>

<%@include file="Script.jsp" %>

</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h1 style="text-align: center; color: #007399; font-size: 24px;">Reset
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
							<td><form:label path="employeeId" placeholder="Employee ID"
									cssClass="inputText">${ResetForm.employeeId}</form:label></td>

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
						<form:hidden path="employeeId" />
						<tr></tr>
						<tr>
							<td></td>
							<td><input class="button" type="submit" /></td>
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