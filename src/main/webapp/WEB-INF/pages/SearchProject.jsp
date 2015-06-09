<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Search Project</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
  <script>
  $(function() {		
		$("#aliasProjectName").autocomplete({
			source: function (request, response) {
	            $.getJSON("/pms/emp/myview/searchProject/searchProject.do", {
	                term: request.term
	            }, response);
	        }
		});
	});
  </script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
 <div class="ui-widget">
			<form:form id="searchForm" method="POST" commandName="searchForm"
				action="searchDetails.do">
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
							<tr>
								<td>Search Project? :</td>
								<td><form:checkbox path="searchProject" id="searchProject"/></td>
								<td><form:errors path="searchProject" cssClass="error" /></td>
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
			
		<c:if test="${not empty projectDocumentList}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">Project
				Documents</h1>
			<table id="projectDocumentList" class="gridView">
				<thead>
					<tr>
						<th>Name</th>
						<th>Alias</th>
						<th>Agreement No</th>
						<th>CER No</th>
						<th>Amount</th>
						<th>Contractor Name</th>
						<th>Project Status</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
						<c:forEach var="projDoc" items="${projectDocumentList}">
							<tr>
								<td>${projDoc.projectName}</td>
								<td>${projDoc.aliasName}</td>
								<td>${projDoc.agreementNo}</td>
								<td>${projDoc.cerNo}</td>
								<td>${projDoc.amount}</td>
								<td>${projDoc.contractorName}</td>
								<td>${projDoc.projectStatus}</td>
								<td><a
									href="/pms/emp/myview/updateProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${projDoc.projId}&action=${action}"
									class="userAction">Update</a></td>
							</tr>
						</c:forEach>
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