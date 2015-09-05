<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Update Employee Details</title>
<%@include file="Script.jsp" %>
<script type="text/javascript">
$(document).ready(function () {
    $("#employeeFName").attr("readonly", "readonly");
    $("#employeeLName").attr("readonly", "readonly");
    $("#employeeTeam").attr("readonly", "readonly");
    $("#employeeAddress").attr("readonly", "readonly");
    $("#employeeMobile").attr("readonly", "readonly");
    $("#employeeGender").attr("readonly", "readonly");
    $("#employeeMail").attr("readonly", "readonly");
  });

</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
			<div>
            	<h2 style="text-align: cener; font-family: arial; color: #007399; font-size: 14px;">${message}</h2>
            </div>
			    <form:form method="POST" commandName="updateEmpform" action="updateEmployeeRole.do">
			        <center>
                        <fieldset style="margin: 1em; text-align: left;">
                        <legend>Update Employee</legend>
					<table>
						<tr>
							<td>First Name <span id="colon">:</span>
							</td>
							<td><form:input path="employeeFName" id="employeeFName"
									placeholder="Enter First Name" cssClass="inputText" /></td>
							<td><form:errors path="employeeFName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Last Name <span id="colon">:</span>
							</td>
							<td><form:input path="employeeLName" id="employeeLName"
									placeholder="Enter Last Name" cssClass="inputText" /></td>
							<td><form:errors path="employeeLName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Team <span id="colon">:</span> </td>
							<td><form:input path="employeeTeam" id="employeeTeam"
                            placeholder="Enter employee team" cssClass="inputText" /></td>
							<td><form:errors path="employeeTeam" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Address <span id="colon">:</span>
							</td>
							<td><form:textarea path="employeeAddress" id="employeeAddress"
									placeholder="Enter Complete address" cssClass="inputText" /></td>
							<td><form:errors path="employeeAddress" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mobile Number <span id="colon">:</span>
							</td>
							<td><form:input path="employeeMobile" id="employeeMobile"
									placeholder="Enter mobile number" cssClass="inputText" /></td>
							<td><form:errors path="employeeMobile" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Mail Id <span id="colon">:</span>
							</td>
							<td><form:input path="employeeMail" id="employeeMail"
									placeholder="Enter mailID" cssClass="inputText" /></td>
							<td><form:errors path="employeeMail" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Employee Role<span id="colon">:</span>
							</td>
							<td><form:select path="employeeRole" cssClass="inputText" items="${employeeRoleList}"/></td>
                            <td><form:errors path="employeeRole" cssClass="error" /></td>
						</tr>
						<tr></tr>
					</table>
			    </fieldset>
			    <form:hidden path="employeeId" />
			    <table>
			    <tr>
                	<td></td>
                	<td><input class="button" type="submit" value="update" /></td>
                	<td></td>
                </tr>
                <center>
                </table>
			</form:form>
		</div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>