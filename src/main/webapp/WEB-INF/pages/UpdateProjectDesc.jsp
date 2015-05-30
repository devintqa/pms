<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Update Project Description</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/script.js" />"></script>
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.dataTables.1.10.6.min.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.dataTables.1.10.6.css" />">

<script>
	  $(function() {
			var table = $("#projDescDocListSize").dataTable();
	  });
</script>

</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		
				<c:if test="${projDescDocListSize gt 0}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">${subProjectAliasName} Project Description Details</h1>
			<table id="projDescDocListSize" class="gridView">
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
								<td><a href="/pms/emp/myview/buildProjectDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${projDesc.projId}&subproject=${projDesc.subProjId}&desc=${projDesc.projDescId}&action=edit"
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

