<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: My Profile</title>
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
			<h2 style="text-align: center; color: #E08B14; font-size: 20px;">${updateSuccessMessage}</h2>
			<br>
			<h1 style="text-align: center; color: #E08B14; font-size: 24px;">Edit
				Profile</h1>
			<!-- <h3 style="text-align:center;font-size:18px;">Please update your details and click submit.</h3> -->
			<br>
			<form:form method="POST" commandName="employee"
				action="editDetails.do">

				<form:errors path="*" cssClass="errorblock" element="div" />
				<center>
					<table>
						<tr>
							<td>UserName<span id="colon">:</span>
							</td>
							<td><form:input path="empId" placeholder="TCS ID"
									cssClass="inputText" value="${employee.empId}" readonly="true" /></td>
							<td><form:errors path="empId" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Password<span id="colon">:</span>
							</td>
							<td><form:password path="empPassword" cssClass="inputText"
									value="${employee.empPassword}" /></td>
							<td><form:errors path="empPassword" cssClass="error" /></td>
						</tr>
						<tr>
							<td>First Name<span id="colon">:</span>
							</td>
							<td><form:input path="employeeFName" cssClass="inputText"
									value="${employee.employeeFName}" /></td>
							<td><form:errors path="employeeFName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Last Name<span id="colon">:</span>
							</td>
							<td><form:input path="employeeLName" cssClass="inputText"
									value="${employee.employeeLName}" /></td>
							<td><form:errors path="employeeLName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Home Address<span id="colon">:</span>
							</td>
							<td><form:textarea path="employeeAddress"
									cssClass="inputText" value="${employee.employeeAddress}" /></td>
							<td><form:errors path="employeeAddress" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Date Of Joining<span id="colon">:</span>
							</td>
							<td><form:input path="employeeDOJ" placeholder="DD-MM-YYYY"
									id="doj" cssClass="inputText" value="${employee.employeeDOJ}" /></td>
							<td><form:errors path="employeeDOJ" cssClass="error" /></td>
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
						<tr>
							<td>Team<span id="colon">:</span>
							</td>
							<td><form:select path="employeeTeam" cssClass="inputText">
									<form:options items="${teamList}" />
								</form:select></td>
							<td><form:errors path="employeeTeam" cssClass="error" /></td>
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


