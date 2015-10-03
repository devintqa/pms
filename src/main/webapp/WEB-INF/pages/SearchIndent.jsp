<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Build Indent</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
<script>
    var windowObjectReference = null;

    function popUpClosed() {
        if (windowObjectReference) {
            window.location.reload();
        }
    }

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

        $("#doIndent").click(function() {
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
                	window.location = "/pms/emp/myview/indent/createIndent?employeeId="+employeeId+"&projectId="+projId+"&subProjectId="+subProjId+"&projDescs="+projDescs+"&action=edit";
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

    function Indent(projId, subProjId, projDescId, employeeId) {
        this.projId = projId;
        this.subProjId = subProjId;
        this.projDescId = projDescId;
        this.employeeId = employeeId;
    }



    function openProjDescLoader(projDescSerial, projId, subProjId, projDescId,
        descType, employeeId) {
        if (subProjId == '') {
            subProjId = 0;
        }
        descType = $("#searchOn").val();
        windowObjectReference = window
            .open(
                "/pms/emp/myview/buildProjectDesc/loadProjDescItems.do?projDescSerial="
                		+ projDescSerial + "&projId=" + projId + "&subProjId=" + subProjId 
                		+ "&projDescId=" + projDescId + "&descType=" + descType 
                		+ "&employeeId=" + employeeId,
                'winname',
                'directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1200,height=700');

    }

    function deleteProjectDescription(projectDescriptionAlias,
        projectDescriptionId, projectDescriptionType) {

        $("#dialog-confirm").html(
            projectDescriptionAlias + " : Deletion Operation!, Please confirm to proceed");

        // Define the Dialog and its properties.
        $("#dialog-confirm")
            .dialog({
                resizable: false,
                modal: true,
                title: "Warning!",
                height: 200,
                width: 400,
                buttons: {
                    "Yes": function() {
                        $
                            .ajax({
                                url: 'deleteProjectDescription.do',
                                data: "projectDescriptionId=" + projectDescriptionId + "&projectDescriptionType=" + projectDescriptionType,
                                success: function(response) {
                                    location.reload();
                                    console.log("Successfully deleted row ");
                                },
                                error: function(err) {
                                    console.log("Error deleting project description ");
                                }
                            });
                        $(this).dialog('close');
                    },
                    "No": function() {
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
			<form:form id="searchProjDescForm" method="POST"
				commandName="searchProjDescForm"
				action="searchFieldDescDetail.do">
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
							<td> <c:if test="${projDescDocListSize gt 0}">
								<input class="button" id="doIndent" value="Build Indent" type="button" />
								&nbsp;
								<input class="button" id="viewIndent" value="View Indent" type="button" />
								</c:if>
							</td>
						</tr>
					</table>
				</center>
				<br>
				<div>
					<h2
						style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
				</div>
                <form:hidden path="employeeId"/>
			</form:form>
			<input id="searchOn" type="hidden"
				value="${searchProjDescForm.searchOn}" />
			<c:if test="${projDescDocListSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName} Project Description Details</h1>
				<table id="projDescDocList" class="display">
					<thead>
						<tr>
							<th>Select</th>
							<th>Serial Number</th>
							<th>Alias</th>
							<th>Work Type</th>
							<th>No of Quantity</th>
							<th>Metric</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty projDescDocList}">
							<c:forEach var="projDesc" items="${projDescDocList}">
								<tr>
										<td><input type="checkbox" name="indentDescription"
											aria-proj-id="${projDesc.projId}"
											aria-subproj-id="${projDesc.subProjId}"
											aria-projdesc-id="${projDesc.projDescId}"
											aria-employee-id="${employeeObj.employeeId}" /></td>
									<td>${projDesc.serialNumber}</td>
									<td>${projDesc.aliasDescription}</td>
									<td>${projDesc.workType}</td>
									<td>${projDesc.quantity}</td>
									<td>${projDesc.metric}</td>
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