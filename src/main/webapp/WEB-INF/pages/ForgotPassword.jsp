<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Forgot Password</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="<c:url value="/resources/js/script.js" />"></script>
</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<h3 style="text-align: center; font-size: 18px;">Please provide
				the following details to reset your password.</h3>
			<br>
			<form:form method="POST" commandName="forgotPasswordForm"
				action="forgotpassword.do">

				<form:errors path="*" cssClass="errorblock" element="div" />
				<center>
					<table>
						<tr>
							<td>UserName <span id="colon">:</span>
							</td>
							<td><form:input path="empId" placeholder="Employee ID"
									cssClass="inputText" /></td>
							<td><form:errors path="empId" cssClass="error" /></td>
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