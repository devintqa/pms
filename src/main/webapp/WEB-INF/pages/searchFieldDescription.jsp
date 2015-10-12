<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Search Field Description</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
<script>
 
  $(document).ready(function () {
        $("#showSearchProjectDesc").hide();
		$("#aliasProjectName").autocomplete({
			source: function (request, response) {
			if($("#searchUnderSubProject").is(':checked'))	{
				$.getJSON("/pms/emp/myview/searchProjectDescription/searchSubProject.do", {
                	                term: request.term
                	            }, response);
			}else{
				$.getJSON("/pms/emp/myview/searchProjectDescription/searchProject.do", {
                	                term: request.term
                	            }, response);
				}
	        }
		});


	});

  function openProjDescLoader(projDescSerial, projId, subProjId, projDescId, descType, employeeId){
	  if(subProjId == ''){
		  subProjId = 0;
	  }
	  descType = $("#searchOn").val();
	  windowObjectReference = window.open("/pms/emp/myview/buildProjectDesc/loadProjDescItems.do?projDescSerial="+projDescSerial+"&projId="+projId+"&subProjId="+subProjId+"&projDescId="+projDescId+"&descType="+descType+"&employeeId="+employeeId,'winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1200,height=700');
  }

  function deleteProjectDescription(projectDescriptionAlias, projectDescriptionId) {

	  $("#dialog-confirm").html(projectDescriptionAlias + " : Deletion Operation!, Please confirm to proceed");

	    // Define the Dialog and its properties.
	    $("#dialog-confirm").dialog({
	        resizable: false,
	        modal: true,
	        title: "Warning!",
	        height: 200,
	        width: 400,
	        buttons: {
	            "Yes": function () {
	            	$.ajax({
	        			type : 'POST',
	        			url : 'deleteProjectDescription.do',
	        			data : "projectDescriptionId="+projectDescriptionId,
	        			success : function(response) {
	        				location.reload();
	    					console.log("Successfully deleted row ");
	        			},
	        			error : function(err) {
	        				console.log("Error deleting project description ");
	        			}
	        		});
	                $(this).dialog('close');
	                window.location.reload();

	            },
	                "No": function () {
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

		<div class="ui-widget">
			<form:form id="searchFieldDescForm" method="POST"
				commandName="searchFieldDescForm"
				action="searchFieldDescDetails.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Search Field Description</legend>
						<table>
							<tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td><form:input path="aliasProjectName"
										id="aliasProjectName" placeholder="Enter Alias Project Name"
										cssClass="inputText" /></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Search under <span id="colon">:</span></td>
								<td><form:radiobutton path="searchUnder" value="project"
										id="searchUnderProject" checked="true" />Project</td>
								<td><form:radiobutton path="searchUnder" value="subProject"
										id="searchUnderSubProject" />Sub Project</td>
							</tr>
							<tr></tr>
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
				<br>
			<div>
			<h2	style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
		</div>
			</form:form>
			<input id="searchOn" type="hidden" value="${searchProjDescForm.searchOn}"/>
			<c:if test="${projDescDocListSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName}
					Project Description Details</h1>
				<table id="projDescDocList" class="gridView">
					<thead>
						<tr>
							<th>Serial Number</th>
							<th>Alias</th>
							<th>Work Type</th>
							<th>No of Quantity</th>
							<th>Metric</th>
							<th>Price Per Quantity</th>
							<th>Total Cost</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty projDescDocList}">
							<c:forEach var="projDesc" items="${projDescDocList}">
								<tr>
									<td><a
										href="javascript:openProjDescLoader('${projDesc.serialNumber}','${projDesc.projId}','${projDesc.subProjId}','${projDesc.projDescId}','${searchProjDescForm.searchOn}','${employeeObj.employeeId}')"
										class="userAction">${projDesc.serialNumber}</a></td>
									<td>${projDesc.aliasDescription}</td>
									<td>${projDesc.workType}</td>
									<td>${projDesc.quantity}</td>
									<td>${projDesc.metric}</td>
									<td>${projDesc.pricePerQuantity}</td>
									<td>${projDesc.totalCost}</td>
									<td><a
										href="/pms/emp/myview/buildProjectDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${projDesc.projId}&subproject=${projDesc.subProjId}&desc=${projDesc.projDescId}&type=${searchProjDescForm.searchOn}&action=edit"
										class="userAction">Update</a> <strong> / </strong> <a
										id="deleteRow"
										href="javascript:deleteProjectDescription('${projDesc.aliasDescription}', '${projDesc.projDescId}');"
										style="color: red"> Delete</a></td>
								</tr>
							</c:forEach>
						</c:if>
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