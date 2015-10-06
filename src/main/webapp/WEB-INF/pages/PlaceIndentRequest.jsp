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
			<form:form id="indentDesc" method="POST" commandName="indentDesc" action="placeIndentRequest.do">
	
			<c:if test="${indentItemSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">Indent Item Details</h1>
				<table id="indentList" class="display">
					<thead>
						<tr>
							<th>Item Name</th>
							<th>Item Type</th>
							<th>Item Qty</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty indentItems}">
							<c:forEach var="indent" items="${indentItems}">
								<tr>
									<td>${indent.itemName}</td>
									<td>${indent.itemType}</td>
									<td>${indent.itemQty}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<input type="hidden" id="indentId" value="${indentId}"/>
				<br>
			</c:if>
			</form:form>
		</div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>