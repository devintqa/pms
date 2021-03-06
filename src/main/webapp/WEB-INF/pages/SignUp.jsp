<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: SignUp</title>

<%@include file="Script.jsp" %>

</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h1 style="text-align: center; color: #007399; font-size: 24px;">New User SignUp</h1>
			<br>
			<form:form method="POST" commandName="signupForm" action="signup.do">

				<!--<form:errors path="*" cssClass="errorblock" element="div" />
				--><center>
					<table>
						<tr>
							<td>First Name <span id="colon">:</span>
							</td>
							<td><form:input path="employeeFName"
									placeholder="Enter First Name" cssClass="inputText" /></td>
							<td><form:errors path="employeeFName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Last Name <span id="colon">:</span>
							</td>
							<td><form:input path="employeeLName"
									placeholder="Enter Last Name" cssClass="inputText" /></td>
							<td><form:errors path="employeeLName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Password <span id="colon">:</span>
							</td>
							<td><form:password path="employeePwd"
									placeholder="Enter Password" cssClass="inputText" /></td>
							<td><form:errors path="employeePwd" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Confirm Password <span id="colon">:</span>
							</td>
							<td><form:password path="employeeConfirmPwd"
									placeholder="Confirm Password" cssClass="inputText" /></td>
							<td><form:errors path="employeeConfirmPwd" cssClass="error" /></td>
						</tr>
						<tr>
								<td>Team <span id="colon">:</span> </td>
								<td><form:select path="employeeTeam" cssClass="inputText" items="${employeeTeamList}"/></td>
								<td><form:errors path="employeeTeam" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Address <span id="colon">:</span>
							</td>
							<td><form:textarea path="employeeAddress"
									placeholder="Enter Complete address" cssClass="inputText" /></td>
							<td><form:errors path="employeeAddress" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Gender <span id="colon">:</span></td>
							<td><form:radiobutton path="employeeGender" value="M" />Male
								<form:radiobutton path="employeeGender" value="F" />Female</td>
							<td><form:errors path="employeeGender" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mobile Number <span id="colon">:</span>
							</td>
							<td><form:input path="employeeMobile"
									placeholder="Enter mobile number" cssClass="inputText" /></td>
							<td><form:errors path="employeeMobile" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mail Id <span id="colon">:</span>
							</td>
							<td><form:input path="employeeMail"
									placeholder="Enter mailID" cssClass="inputText" /></td>
							<td><form:errors path="employeeMail" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mother's Maiden Name<span id="colon">:</span>
							</td>
							<td><form:input path="employeeMotherMaidenName"
									placeholder="Mother's maiden name" cssClass="inputText" /></td>
							<td><form:errors path="employeeMotherMaidenName"
									cssClass="error" /></td>
						</tr>
						<tr></tr>
						<tr>
							<td></td>
							<td><input class="button" type="submit" /></td>
						</tr>
						<tr></tr>
						<tr>
							<td colspan="3" style="text-align: center">Already
								registered! <a href="/pms/emp/login">Login Here</a>
							</td>
						</tr>
						<tr></tr>
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
