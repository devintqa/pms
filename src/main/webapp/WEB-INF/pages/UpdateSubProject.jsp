<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Update Sub Project</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/script.js" />"></script>
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.dataTables.1.10.6.min.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.dataTables.1.10.6.css" />">

<script>
$(function() {
	var table = $("#subProjectDocumentList").dataTable();
})
</script>

</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		
		<c:if test="${subProjectDocumentSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName} Sub Project Details</h1>
				<table id="subProjectDocumentList" class="gridView">
					<thead>
						<tr>
							<th>Name</th>
							<th>Alias</th>
							<th>Agreement No</th>
							<th>CER No</th>
							<th>Amount</th>
							<th>Contractor Name</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty subProjectDocumentList}">
							<c:forEach var="subProjDoc" items="${subProjectDocumentList}">
								<tr>
									<td>${subProjDoc.subProjectName}</td>
									<td><a
										href="/pms/emp/myview/updateSubProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${subProjDoc.projId}&subproject=${subProjDoc.subProjId}&action=${action}"
										class="userAction">${subProjDoc.aliasSubProjName}</a></td>
									<td>${subProjDoc.subAgreementNo}</td>
									<td>${subProjDoc.subCerNo}</td>
									<td>${subProjDoc.subAmount}</td>
									<td>${subProjDoc.subContractorName}</td>
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

