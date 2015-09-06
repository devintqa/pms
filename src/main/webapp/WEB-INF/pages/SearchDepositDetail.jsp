<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>PMS :: Search Deposit Detail</title>
<%@include file="Script.jsp"%>
<%@include file="Utility.jsp"%>
<script>
  var windowObjectReference = null;
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

  function deleteDeposit(aliasProjectName, depositId) {
	  
	  $("#dialog-confirm").html(aliasProjectName + " : Deletion Operation!, Please confirm to proceed");

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
	        			url : 'deleteDeposit.do',
	        			data : "depositId="+depositId,
	        			success : function(response) {
	        				location.reload();
	    					console.log("Successfully deleted row ");
	        			},
	        			error : function(err) {
	        				console.log("Error deleting Deposit Detail  ");
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
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<h2
			style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
	<div class="ui-widget">
		<form:form id="searchDepositForm" method="POST"
			commandName="searchDepositForm" action="searchDepositDetails.do">
			<center>
				<fieldset style="margin: 1em; text-align: left;">
					<legend>Search Deposit Details</legend>
					<table>
						<tr>
							<td>Alias Project Name<span id="colon">:</span>
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
                            <td><form:radiobutton path="searchUnder" value="Global"
                                                  id="searchUnderProject"/>Global</td>
						</tr>
						<tr></tr>
					</table>
				</fieldset>
				<table>
					<tr>
						<td></td>
						<td><input class="button" type="submit" /></td>
						<td></td>
					</tr>
				</table>
			</center>
			<br>
			<br>

		</form:form>

		<c:if test="${depositDetailsSize gt 0}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName}
				Deposit Detail Details</h1>
			<table id="depositDetails" class="gridView">
				<thead>
					<tr>
						<th>Project Name</th>
						<th>Sub Project Name</th>
						<th>Deposit Type</th>
						<th>Deposit Amount</th>
						<th>Deposit Start Date</th>
						<th>Deposit End Date</th>
                        <th>Deposit Status</th>
                        <th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty depositDetails}">
						<c:forEach var="detail" items="${depositDetails}">
							<tr>
								<td>${detail.aliasProjectName}</td>
								<td>${detail.aliasSubProjectName}</td>
								<td>${detail.depositType}</td>
								<td>${detail.depositAmount}</td>
								<td>${detail.depositStartDate}</td>
                                <td>${detail.depositEndDate}</td>
                                <td>${detail.depositStatus}</td>
                                <td><a
									href="/pms/emp/myview/buildDepositDetail/${employeeObj.employeeId}?depositId=${detail.depositId}&action=updateDepositDetail&aliasProjectName=${detail.aliasProjectName}&aliasSubProjectName=${detail.aliasSubProjectName}"
									class="userAction">Update</a> <strong> / </strong> <a
									id="deleteRow"
									href="javascript:deleteDeposit('${detail.aliasProjectName}','${detail.depositId}');"
									style="color: red"> Delete</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</c:if>
	</div>
	<div id="dialog-confirm"></div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>