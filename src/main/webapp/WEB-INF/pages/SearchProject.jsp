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
  $(document).ready(function () {		
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
	<div>
	    <h2 style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
	</div>
 <div class="ui-widget">
     	<% String search = request.getParameter("search");%>
         <input type="hidden" id="search" value="<%=search%>"/>
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
							<c:if test="${searchForm.searchProject eq 'Y'}">
							<tr id="showEditProject">
								<td>Search All Projects? :</td>
								<td><form:checkbox path="editProject" id="editProject"/></td>
								<td><form:errors path="editProject" cssClass="error" /></td>
							</tr>
							</c:if>
							<c:if test="${searchForm.searchSubProject eq 'Y'}">
							<tr id="showEditSubProject">
								<td>Edit Sub Project? :</td>
								<td><form:checkbox path="editSubProject" id="editSubProject"/></td>
								<td><form:errors path="editSubProject" cssClass="error" /></td>
							</tr>
							</c:if>
							<c:if test="${searchForm.searchProjectDesc eq 'Y'}">
							<tr id="showSearchProjectDesc">
								<td>Search Project Description? :</td>
								<td><form:checkbox path="searchProjectDescription" id="searchProjectDescription"/></td>
								<td><form:errors path="searchProjectDescription" cssClass="error" /></td>
							</tr>	
							</c:if>		
							<tr></tr>
						</table>
							<form:hidden path="searchProject" id="searchProject"/>
							<form:hidden path="searchSubProject" id="searchSubProject"/>
							<form:hidden path="searchProjectDesc" id="searchProjectDesc"/>
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
		
		<c:if test="${projDescDocListSize gt 0}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName} Project Description Details</h1>
			<table id="projDescDocList" class="gridView">
				<thead>
					<tr>
						<th>Name</th>
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
								<td>${projDesc.description}</td>
								<td>${projDesc.aliasDescription}</td>
								<td>${projDesc.workType}</td>
								<td>${projDesc.quantityInFig}</td>
								<td>${projDesc.rateInFig}</td>
								<td>${projDesc.projDescAmount}</td>
								<td><a href="/pms/emp/myview/buildProjectDesc/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${projDesc.projId}&subproject=${projDesc.subProjId}&desc=${projDesc.projDescId}&action=edit"
										 class="userAction">Update</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</c:if>
		
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
										class="userAction">Update</a></td>
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