<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Forgot Password</title>

<%@include file="Script.jsp"%>

</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h3 style="text-align: center; font-size: 18px;">Please provide
				the following details to reset your password.</h3>
			<br>
			<form:form method="POST" commandName="forgotPasswordForm"
				action="forgotpassword.do">

				<!--<form:errors path="*" cssClass="errorblock" element="div" />
				-->
				<center>
					<table>
						<tr>
							<td>UserName <span id="colon">:</span>
							</td>
							<td><form:input path="employeeId"
									placeholder="Enter User Name" cssClass="inputText" /></td>
							<td><form:errors path="employeeId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mother's Maiden Name<span id="colon">:</span></td>
							<td><form:input path="employeeMotherMaidenName"
									placeholder="Mother's maiden name" cssClass="inputText" /></td>
							<td><form:errors path="employeeMotherMaidenName"
									cssClass="error" /></td>
						</tr>
						<tr></tr>
						<tr>
							<td></td>
							<td><input type="submit" /></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="3" style="text-align: center">Go To Home Page!
								<a href="/pms/emp/login">Home Page</a>
							</td>
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