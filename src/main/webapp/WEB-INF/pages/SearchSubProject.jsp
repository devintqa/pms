<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>PMS :: Search Sub Project</title>
<%@include file="Script.jsp"%>
<%@include file="Utility.jsp"%>
<script>
	$(document)
			.ready(
					function() {
						$("#aliasProjectName")
								.autocomplete(
										{
											source : function(request, response) {
												$
														.getJSON(
																"/pms/emp/myview/searchSubProject/searchProject.do",
																{
																	term : request.term
																}, response);
											}
										});
					});

	function deleteSubProject(subprojectAlias, subProjectId) {
		$("#dialog-confirm").html(
				subprojectAlias
						+ " : Deletion Operation!, Please confirm to proceed");
		// Define the Dialog and its properties.
		$("#dialog-confirm").dialog({
			resizable : false,
			modal : true,
			title : "Warning!",
			height : 200,
			width : 400,
			buttons : {
				"Yes" : function() {
					$.ajax({
						type : 'POST',
						url : 'deleteSubProject.do',
						data : "subProjectId=" + subProjectId,
						success : function(response) {
							location.reload();
							console.log("Successfully deleted row ");
						},
						error : function(err) {
							console.log("Error deleting sub project  ");
						}
					});
					$(this).dialog('close');
					window.location.reload();

				},
				"No" : function() {
					$(this).dialog('close');

				}
			}
		});
	}
</script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
		</div>
		<div class="ui-widget">
			<form:form id="searchSubForm" method="POST"
				commandName="searchSubForm" action="searchSubProjectDetails.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Search Details</legend>
						<table>
							<tr>
								<td>Alias Project Name<span id="colon">:</span>
								</td>
								<td><form:input path="aliasProjectName"
										id="aliasProjectName" placeholder="Enter Alias Project Name"
										cssClass="inputText" /></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr id="showEditSubProject">
								<td>Edit Sub Project? :</td>
								<td><form:checkbox path="editSubProject"
										id="editSubProject" /></td>
								<td><form:errors path="editSubProject" cssClass="error" /></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>
					<table>
						<tr>
							<td></td>
							<td><input type="submit" /></td>
							<td></td>
						</tr>
					</table>
				</center>
				<br>
				<br>

			</form:form>

			<c:if test="${subProjectDocumentSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName}
					Sub Project Details</h1>
				<table id="subProjectDocumentList" class="gridView">
					<thead>
						<tr>
							<th>Sub Project Name</th>
							<th>Alias</th>
							<th>Agreement No</th>
							<th>CER No</th>
							<th>Amount</th>
							<th>Contractor Name</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty subProjectDocumentList}">
							<c:forEach var="subProjDoc" items="${subProjectDocumentList}">
								<tr>
									<td>${subProjDoc.subProjectName}</td>
									<td>${subProjDoc.aliasSubProjName}</td>
									<td>${subProjDoc.subAgreementNo}</td>
									<td>${subProjDoc.subCerNo}</td>
									<td>${subProjDoc.subAmount}</td>
									<td>${subProjDoc.subContractorName}</td>
									<td><a
										href="/pms/emp/myview/updateSubProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${subProjDoc.projId}&subproject=${subProjDoc.subProjId}&action=${action}"
										class="userAction">Update</a>
										<strong> / </strong>
										<a href="/pms/emp/myview/configureItems/${employeeObj.employeeId}?project=${subProjDoc.projId}&subProject=${subProjDoc.subProjId}"
                                        class="userAction"> Configure</a>
                                        <strong> / </strong>
										<a id="deleteRow"
										href="javascript:deleteSubProject('${subProjDoc.aliasSubProjName}','${subProjDoc.subProjId}');"
										style="color: red"> Delete</a>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
		<div id="dialog-confirm"></div>
		</div>
		</div>
		<footer>
			<jsp:include page="Footer.jsp" />
		</footer>
</body>
</html>