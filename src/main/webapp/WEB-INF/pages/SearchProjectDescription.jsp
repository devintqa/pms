<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Search Project Description</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
  <script>
  $(document).ready(function () {
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
  function deleteProjectDescription(projectDescriptionId) {
    		$.ajax({
    			type : 'POST',
    			url : 'deleteProjectDescription.do',
    			data : "projectDescriptionId="+projectDescriptionId,
    			success : function(response) {
    				location.reload();
					console.log("Successfully deleted row ");
    			},
    			error : function(err) {
    				alert("error - " + err);
    			}
    		});
    	}
  </script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
	    <h2 style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
	</div>
 <div class="ui-widget">
			<form:form id="searchProjDescForm" method="POST" commandName="searchProjDescForm"
				action="searchProjectDescDetails.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Search Details</legend>
						<table>
							<tr>
								<td>Alias Project Name<span id="colon">:</span>
								</td>
								<td><form:input path="aliasProjectName" id="aliasProjectName"
										placeholder="Enter Alias Project Name" cssClass="inputText" /></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr id="showSearchProjectDesc">
								<td>Search Project Description? :</td>
								<td><form:checkbox path="searchProjectDescription" id="searchProjectDescription"/></td>
								<td><form:errors path="searchProjectDescription" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Search under :</td>
								<td><form:radiobutton path="searchUnder" value = "Project" id="searchUnderProject" checked="true"/>Project</td>
								<td><form:radiobutton path="searchUnder" value = "subProject" id="searchUnderSubProject"/>Sub Project</td>
							</tr>		
							<tr></tr>
						</table>
					</fieldset>
					<table>
						<tr>
							<td></td>
							<td><input type="submit"/></td>
							<td></td>
						</tr>
					</table>
				</center>
				<br>
				<br>

			</form:form>
		
		<c:if test="${projDescDocListSize gt 0}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName} Project Description Details</h1>
			<table id="projDescDocList" class="gridView">
				<thead>
					<tr>
						<th>Serial Number</th>
						<th>Alias</th>
						<th>Work Type</th>
						<th>Quantity</th>
						<th>Rate</th>
						<th>Project Description Amount</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty projDescDocList}">
						<c:forEach var="projDesc" items="${projDescDocList}">
							<tr>
								<td>${projDesc.serialNumber}</td>
								<td>${projDesc.aliasDescription}</td>
								<td>${projDesc.workType}</td>
								<td>${projDesc.quantityInFig}</td>
								<td>${projDesc.rateInFig}</td>
								<td>${projDesc.projDescAmount}</td>
								<td><a href="/pms/emp/myview/buildProjectDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${projDesc.projId}&subproject=${projDesc.subProjId}&desc=${projDesc.projDescId}&action=edit"
										 class="userAction">Update</a>
									<strong> / </strong>
									<p id ="deleteRow" onclick ="javascript:deleteProjectDescription('${projDesc.projDescId}');" style="color:red"> Delete</p>
                                </td>
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