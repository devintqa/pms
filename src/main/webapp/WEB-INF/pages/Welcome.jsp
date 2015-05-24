<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Project Home</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.dataTables.1.10.6.min.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/jquery.dataTables.1.10.6.css" />">

<script>
$(function() {
    var table = $("#projectDocumentList").dataTable( {"pageLength": 10});
    var tblNewSignUpRequest = $("#newSignUpRequests").dataTable();
	var tblEmdDocument = $("#emdDocumentList").dataTable();
	
     $("#columnNames").on('change', 'input:checkbox', function(e) {
        var columnName = $(this).attr('name');			  
        
		  var isColumnStatusChecked = $( this ).is( ":checked" ) 
		  if(!isColumnStatusChecked){
				$(this).attr('value', 'hidden');
		  } else{
				$(this).attr('value', '');
		  }
        $("table[id*=projectDocumentList] th").each(function(index, item) {			  
            if (columnName == item.innerHTML) {
                $('table[id*=projectDocumentList] tr').find("th:eq(" + index + "), td:eq(" + index + ")").toggle();
            }
        })
    });


    $("#projectDocumentList_paginate").on('click', function(e) {
        $("#columnNames").find('input:checkbox').each(function(index, item) {
            var columnStatus = $(this).attr('value');
            var columnName = $(this).attr('name');				  
			  
            if (columnStatus == 'hidden') {						
                $("table[id*=projectDocumentList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#projectDocumentList tr').find("td:eq(" + index + ")").css('display');
                        if (displayType != 'none') {
                            $('table[id*=projectDocumentList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
            }else{					
				 $("table[id*=projectDocumentList] th").each(function(index, item) {						
                    if (columnName == item.innerHTML) {
                        var displayType = $('#projectDocumentList tr').find("td:eq(" + index + ")").css('display');							  
                        if (displayType == 'none') {
                            $('table[id*=projectDocumentList] tr').find("td:eq(" + index + ")").toggle();
                        }

                    }
                })
			  }
        });
    });		  
});
</script>

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
</script>
</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<h3 id="welcomeMessage">Welcome, ${employeeObj.employeeId}&nbsp!</h3>
			<h2
				style="text-align: left; font-family: arial; color: #C6311D; font-size: 14px;">${noProjectCreated}</h2>
		</div>
		<c:if test="${employeeObj.employeeTeam eq 'Admin'}">
			<h1 style="text-align: center; color: #E08B14; font-size: 24px;">New
				Signup Requests</h1>
			<table id="newSignUpRequests" class="gridView">
				<thead>
					<tr>
						<th>User Name</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Gender</th>
						<th>Team</th>
						<th>Address</th>
						<th>Mobile</th>
						<th>Mail</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty newSignupRequestList}">
						<c:forEach var="signReq" items="${newSignupRequestList}">
							<tr>
								<td>${signReq.employeeId}</td>
								<td>${signReq.employeeFName}</td>
								<td>${signReq.employeeLName}</td>
								<td>${signReq.employeeGender}</td>
								<td>${signReq.employeeTeam}</td>
								<td>${signReq.employeeAddress}</td>
								<td>${signReq.employeeMobile}</td>
								<td>${signReq.employeeMail}</td>
								<td><c:if test="${signReq.enabled eq '0'}">
										<a
											href="javascript:manageUser('${signReq.employeeId}', 'enable');"
											class="userAction">Enable</a> &nbsp;&nbsp;
										<a
											href="javascript:manageUser('${signReq.employeeId}', 'deny');"
											class="userAction">Deny</a>
									</c:if> <c:if test="${listValue.enabled eq '1'}">
										<a
											href="javascript:manageUser('${signReq.employeeId}', 'disable');"
											class="userAction">Disable</a>
									</c:if></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</c:if>
		
		<c:if test="${employeeObj.employeeTeam eq 'Admin'}">
			<h1 style="text-align: center; color: #E08B14; font-size: 18px;">EMD end dates are nearing for following projects. Please take necessary action.</h1>
			<table id="emdDocumentList" class="gridView">
				<thead>
					<tr>
						<th>Project Alias Name</th>
						<th>EMD Start Date</th>
						<th>EMD End Date</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty emdDocumentList}">
						<c:forEach var="emdProject" items="${emdDocumentList}">
							<tr>
								<td>${emdProject.aliasName}</td>
								<td>${emdProject.emdStartDate}</td>
								<td>${emdProject.emdEndDate}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</c:if>

		<c:if test="${employeeObj.employeeTeam eq 'Technical'}">
			<h1 style="text-align: center; color: #E08B14; font-size: 24px;">Project
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
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty projectDocumentList}">
						<c:forEach var="projDoc" items="${projectDocumentList}">
							<tr>
								<td><a
									href="/pms/emp/myview/updateProject/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&project=${projDoc.projId}&action=${action}"
									class="userAction">${projDoc.projectName}</a></td>
								<td>${projDoc.aliasName}</td>
								<td>${projDoc.agreementNo}</td>
								<td>${projDoc.cerNo}</td>
								<td>${projDoc.amount}</td>
								<td>${projDoc.contractorName}</td>
								<td>${projDoc.projectStatus}</td>
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

