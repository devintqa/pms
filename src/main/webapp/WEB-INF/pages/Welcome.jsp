<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Project Home</title>

<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>

<script>
	function manageUser(userId, action) {
		var entity = {
			"user" : userId,
			"action" : action
		};
		$.ajax({
			type : 'POST',
			url : 'manageAccess.do',
			contentType : 'application/json',
			data : JSON.stringify(entity),
			dataType : "json",
			success : function(response) {
				if (response == '1') {
					location.reload();
				}
			},
			error : function(err) {
				alert("error - " + err);
			}
		});
	}
</script>
</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h3 id="welcomeMessage">Welcome, ${employeeObj.employeeId}&nbsp!</h3>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noProjectCreated}</h2>
		</div>
		<c:if test="${employeeObj.employeeTeam eq 'Admin'}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">New
				Signup Requests</h1>
			<table id="newSignUpRequests" class="gridView">
				<thead>
					<tr>
						<th>User Name</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Gender</th>
						<th>Team</th>
						<th>Address</th>
						<th>Mobile</th>
						<th>Mail</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty newSignupRequestList}">
						<c:forEach var="signReq" items="${newSignupRequestList}">
							<tr>
								<td>${signReq.employeeId}</td>
								<td>${signReq.employeeFName}</td>
								<td>${signReq.employeeLName}</td>
								<td>${signReq.employeeGender}</td>
								<td>${signReq.employeeTeam}</td>
								<td>${signReq.employeeAddress}</td>
								<td>${signReq.employeeMobile}</td>
								<td>${signReq.employeeMail}</td>
								<td><c:if test="${signReq.enabled eq '0'}">
										<a
											href="javascript:manageUser('${signReq.employeeId}', 'enable');"
											class="userAction">Enable</a> &nbsp;&nbsp;
										<a
											href="javascript:manageUser('${signReq.employeeId}', 'deny');"
											class="userAction">Deny</a>
									</c:if> <c:if test="${listValue.enabled eq '1'}">
										<a
											href="javascript:manageUser('${signReq.employeeId}', 'disable');"
											class="userAction">Disable</a>
									</c:if></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</c:if>

		<c:if test="${employeeObj.employeeTeam eq 'Admin'}">
			<h1 style="text-align: center; color: #007399; font-size: 18px;">EMD
				end dates are nearing for following projects. Please take necessary
				action.</h1>
			<table id="emdDocumentList" class="gridView">
				<thead>
					<tr>
						<th>EMD Amount</th>
						<th>EMD Start Date</th>
						<th>EMD End Date</th>
						<th>EMD Type</th>
						<th>EMD Extension Date</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty emdDocumentList}">
						<c:forEach var="emdProject" items="${emdDocumentList}">
							<tr>
								<td>${emdProject.emdAmount}</td>
								<td>${emdProject.emdStartDate}</td>
								<td>${emdProject.emdEndDate}</td>
								<td>${emdProject.emdType}</td>
								<td>${emdProject.emdExtensionDate}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</c:if>

		<c:if test="${employeeObj.employeeTeam eq 'Technical'}">
			
				Click here to <a href="downloadTemplate.do" class="userAction">download
				template</a>
			<br>
			<br>
            <br>
            Click here to <a href="downloadItemDescTemplate.do" class="userAction">download Item Description Template</a>
		</c:if>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>

