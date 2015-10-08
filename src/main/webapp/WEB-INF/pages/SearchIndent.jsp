<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Search Indent</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
<script>

        $(document).ready(function () {
            $("#showSearchProjectDesc").hide();
            $("#aliasProjectName").autocomplete({
                source: function (request, response) {
                    var empId = $('#employeeId').val();
                        $.getJSON("/pms/emp/myview/searchProjectDescription/searchProject.do",{
                            term: request.term,
                            employeeId : empId
                        }, response);
                }
            });

        
        $("#viewIndent").click(function() {
            console.log($('input[name="indentDescription"]:checked'));
            var projDescs = [];
            var indentObjArray = [];
            var projId = '';
            var subProjId = '';
            var projDescId = '';
            var employeeId = '';
            
            $($('input[name="indentDescription"]:checked')).each(function(index) {

                console.log(index + ": " + $(this).attr('aria-proj-id'));
                console.log(index + ": " + $(this).attr('aria-subproj-id'));
                console.log(index + ": " + $(this).attr('aria-projdesc-id'));
                console.log(index + ": " + $(this).attr('aria-employee-id'));
                
                projId = $(this).attr('aria-proj-id');
                subProjId = $(this).attr('aria-subproj-id');
                projDescId = $(this).attr('aria-projdesc-id');
                employeeId = $(this).attr('aria-employee-id');

                projDescs.push(projDescId);
                
            });
            if($('input[name="indentDescription"]:checked').length > 0){
                	window.location = "/pms/emp/myview/indent/createIndent?employeeId="+employeeId+"&projectId="+projId+"&subProjectId="+subProjId+"&projDescs="+projDescs+"&action=view";
            }
        });

    });



    
</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">

		<div class="ui-widget">
			<form:form id="searchIndentForm" method="POST"
				commandName="searchIndentForm"
				action="searchIndentsOfProject.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Search Indent</legend>
						<table>
							<tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td colspan="2"><form:input path="aliasProjectName"
										id="aliasProjectName" placeholder="Enter Alias Project Name"
										cssClass="inputText" /></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>
					<table>
						<tr>
							<td></td>
							<td><input class="button" id="submit" type="submit" /></td>
						</tr>
					</table>
				</center>
				<br>
				<div>
					<h2
						style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
				</div>
                <form:hidden id="employeeId" path="employeeId"/>
			</form:form>
			<input id="searchOn" type="hidden"
				value="${searchProjDescForm.searchOn}" />
			<c:if test="${indentListSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName} Project Indent Details</h1>
				<table id="indentList" class="display">
					<thead>
						<tr>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Status</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty indentList}">
							<c:forEach var="indent" items="${indentList}">
								<tr>
									<td><a href="/pms/emp/myview/indent/itemToRequest?employeeId=${indent.employeeId}&indentId=${indent.indentId}&status=${indent.status}">${indent.startDate}</a>
									</td>
									<td>${indent.endDate}</td>
									<td>${indent.status}</td>
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