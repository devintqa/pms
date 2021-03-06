<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>PMS :: Search Description</title>
<%@include file="Script.jsp"%>
<%@include file="Utility.jsp"%>
<script>


function openBaseDescLoader (baseDescId, employeeId, descType){
	  windowObjectReference = window.open("/pms/emp/myview/buildBaseDesc/loadBaseDescItems.do?baseDescId="+baseDescId+"&employeeId="+employeeId+"&descType="+descType,'winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1200,height=700');
}

function deleteBaseDescription(deleteLink, aliasDescription, baseDescriptionId ) {
	console.log(deleteLink);
	var currentRow = $(deleteLink).closest("tr");
	console.log(currentRow);
	$("#dialog-confirm").html(aliasDescription	+ " : Deletion Operation!, Please confirm to proceed");
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
					url : 'deleteGlobalProjectDescription.do',
					data : "aliasDescription=" + aliasDescription,
					success : function(response) {
						console.log("Successfully deleted row ");
					},
					error : function(err) {
						console.log("Error deleting base description ");
					}
				});
				$(this).dialog('close');
				currentRow.remove();
			},
			"No" : function() {
				$(this).dialog('close');
			}
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
		<form:form id="baseDesc" method="POST"
			commandName="baseDesc" action="searchBaseDescDetails.do">
			<center>
				<fieldset style="margin: 1em; text-align: left;">
					<legend>Search Base Description</legend>
					<table>
						<tr>
							<td>Category <span id="colon">:</span></td>
							<td><form:radiobutton path="searchOn" value="psk"
									id="searchOnPsk" checked="true" />PSK</td>
							<td><form:radiobutton path="searchOn" value="government"
									id="searchOnGovernment" />GOVERNMENT</td>
						</tr>
					</table>
				</fieldset>
				<table>
					<tr>
						<td></td>
						<td><input class="button" id="submit" type="submit" /></td>
						<td></td>
					</tr>
				</table>
			</center>
		</form:form>
		<div>
			<h2 style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
		</div>
		<div class="ui-widget">
			<c:if test="${not empty baseDescriptionList}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">Base Description Documents</h1>
				<table id="baseDescriptionList" class="gridView">
					<thead>
						<tr>
							<th>Base Description</th>
							<th>Work Type</th>
							<th>Metric</th>
							<th>Quantity</th>
							<c:if test="${baseDesc.searchOn eq 'government'}">
							<th>Total Cost</th>
							</c:if>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="globalDescription" items="${baseDescriptionList}">
							<tr>
								<td><a	href="javascript:openBaseDescLoader('${globalDescription.baseDescId}','${employeeObj.employeeId}','${baseDesc.searchOn}')"
										class="userAction">${globalDescription.aliasDescription}</a>
										</td>
								<td>${globalDescription.workType}</td>
								<td>${globalDescription.metric}</td>
								<td>${globalDescription.quantity}</td>
								<c:if test="${baseDesc.searchOn eq 'government'}">
								<td>${globalDescription.pricePerQuantity}</td>
								</c:if>
								<td><a
									href="/pms/emp/myview/buildBaseDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&desc=${globalDescription.baseDescId}&action=edit"
									class="userAction">Update</a> <strong> / </strong> <a
									id="deleteRow"
									onclick="javascript:deleteBaseDescription(this,'${globalDescription.aliasDescription}','${globalDescription.baseDescId}');"
									style="color: red; cursor: pointer;"> Delete</a></td>
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