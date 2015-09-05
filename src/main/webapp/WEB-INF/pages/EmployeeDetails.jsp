<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Employee Details</title>

<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>

<script>
	function manageUser(userId, action) {
		var entity = {
			"user" : userId,
			"action" : action
		};
		$.ajax({
			type : 'POST',
			url : 'manageAccess.do',
			contentType : 'application/json',
			data : JSON.stringify(entity),
			dataType : "json",
			success : function(response) {
				if (response == '1') {
					location.reload();
				}
			},
			error : function(err) {
				alert("error - " + err);
			}
		});
	}
	function deleteEmployee(employeeId, employeeName) {

    	  $("#dialog-confirm").html(employeeName + " : Delete this employee permanently ??, Please confirm to proceed");
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
    	        			url : 'deleteEmployee.do',
    	        			data : "employeeId="+employeeId,
    	        			success : function(response) {
    	        				location.reload();
    	    					console.log("Successfully deleted employee ");
    	        			},
    	        			error : function(err) {
    	        				console.log("Error deleting employee ");
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
			<h1 style="text-align: center; color: #007399; font-size: 24px;">Employee Details</h1>
			<table id="employeeDetails" class="gridView">
				<thead>
					<tr>
						<th>User Name</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Team</th>
						<th>Mobile</th>
						<th>Mail</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty employees}">
						<c:forEach var="employee" items="${employees}">
							<tr>
								<td>${employee.employeeId}</td>
								<td>${employee.employeeFName}</td>
								<td>${employee.employeeLName}</td>
								<td>${employee.employeeTeam}</td>
								<td>${employee.employeeMobile}</td>
								<td>${employee.employeeMail}</td>
								<td>
                                    <a
										href="javascript:deleteEmployee('${employee.employeeId}', '${employee.employeeFName}');"
										class="userAction">Delete</a>
                                    <a
										href="/pms/emp/myview/updateEmployee/${employee.employeeId}?team=${employee.employeeTeam}"
										class="userAction">Update</a>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</div>
	</div>
	<div id="dialog-confirm"></div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>