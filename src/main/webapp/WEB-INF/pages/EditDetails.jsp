<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: My Profile</title>

<%@include file="Script.jsp"%>

</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2 style="text-align: center; color: #007399; font-size: 20px;">${updateSuccessMessage}</h2>
			<br>
			<h1 style="text-align: center; color: #007399; font-size: 24px;">Edit
				Profile</h1>
			<!-- <h3 style="text-align:center;font-size:18px;">Please update your details and click submit.</h3> -->
			<br>
			<form:form method="POST" commandName="employee"
				action="editDetails.do">

				<!--<form:errors path="*" cssClass="errorblock" element="div" />
				-->
				<center>
					<table>
						<tr>
							<td>UserName<span id="colon">:</span>
							</td>
							<td><form:input path="employeeId"
									placeholder="PSK User Name" cssClass="inputText"
									value="${employee.employeeId}" readonly="true" /></td>
							<td><form:errors path="employeeId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Team<span id="colon">:</span>
							</td>
							<td><form:input path="employeeTeam"
									placeholder="PSK User Name" cssClass="inputText"
									value="${employee.employeeTeam}" readonly="true" /></td>
							<td><form:errors path="employeeTeam" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Home Address<span id="colon">:</span>
							</td>
							<td><form:textarea path="employeeAddress"
									cssClass="inputText" value="${employee.employeeAddress}" /></td>
							<td><form:errors path="employeeAddress" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mobile Number<span id="colon">:</span>
							</td>
							<td><form:input path="employeeMobile" cssClass="inputText"
									value="${employee.employeeMobile}" /></td>
							<td><form:errors path="employeeMobile" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mail Id<span id="colon">:</span>
							</td>
							<td><form:input path="employeeMail" cssClass="inputText"
									value="${employee.employeeMail}" /></td>
							<td><form:errors path="employeeMail" cssClass="error" /></td>
						</tr>
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


