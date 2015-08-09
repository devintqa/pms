<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>PMS :: Search Project</title>
<%@include file="Script.jsp"%>
<%@include file="Utility.jsp"%>
<script>
	function deleteProject(projectAlias, projectId) {
		$("#dialog-confirm").html(
				projectAlias
						+ " : Deletion Operation!, Please confirm to proceed");
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
						url : 'deleteProject.do',
						data : "projectId=" + projectId,
						success : function(response) {
							location.reload();
							console.log("Successfully deleted row ");
						},
						error : function(err) {
							console.log("Error deleting pwd description ");
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
			<c:if test="${not empty baseDescriptionList}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">Project
					Documents</h1>
				<table id="pwdDescriptionList" class="gridView">
					<thead>
						<tr>
							<th>BaseDescription</th>
							<th>WorkType</th>
							<th>Metric</th>
							<th>Quantity</th>
							<th>Total Cost</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="pwdDoc" items="${baseDescriptionList}">
							<tr>
								<td>${pwdDoc.aliasDescription}</td>
								<td>${pwdDoc.workType}</td>
								<td>${pwdDoc.metric}</td>
								<td>${pwdDoc.quantity}</td>
								<td>${pwdDoc.totalCost}</td>
								<td><a
									href="/pms/emp/myview/updatePwdDescription/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${pwdDoc.projId}"
									class="userAction">Update</a> <strong> / </strong> <a
									id="deleteRow"
									href="javascript:deleteProject('${pwdDoc.serialNumber}');"
									style="color: red"> Delete</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
		</div>
		<div id="dialog-confirm"></div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>