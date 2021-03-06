<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Update Project Description</title>

<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>

</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">

		<c:if test="${projDescDocListSize gt 0}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">${subProjectAliasName}
				Project Description Details</h1>
			<table id="projDescDocList" class="gridView">
				<thead>
					<tr>
						<th>Name</th>
						<th>Alias</th>
						<th>Work Type</th>
						<th>Quantity</th>
						<th>Rate</th>
						<th>Project Description Amount</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty projDescDocList}">
						<c:forEach var="projDesc" items="${projDescDocList}">
							<tr>
								<td>${projDesc.description}</td>
								<td><a
									href="/pms/emp/myview/buildProjectDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${projDesc.projId}&subproject=${projDesc.subProjId}&desc=${projDesc.projDescId}&action=edit"
									class="userAction">${projDesc.aliasDescription}</a></td>
								<td>${projDesc.workType}</td>
								<td>${projDesc.quantityInFig}</td>
								<td>${projDesc.rateInFig}</td>
								<td>${projDesc.projDescAmount}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</c:if>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>

</body>
</html>

