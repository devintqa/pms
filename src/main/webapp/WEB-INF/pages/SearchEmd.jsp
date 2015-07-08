<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Search EMD</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
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

  function deleteEmd(aliasProjectName, emdId) {
	  
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
	        			url : 'deleteEmd.do',
	        			data : "emdId="+emdId,
	        			success : function(response) {
	        				location.reload();
	    					console.log("Successfully deleted row ");
	        			},
	        			error : function(err) {
	        				console.log("Error deleting Emd  ");
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
	<div>
	    <h2 style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
	</div>
 <div class="ui-widget">
			<form:form id="searchEmdForm" method="POST" commandName="searchEmdForm"
				action="searchEmdDetails.do">
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
								<td>Search EMD? :</td>
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
		
		<c:if test="${emdDetailsSize gt 0}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName} EMD Details</h1>
			<table id="emdList" class="gridView">
		<thead>
        								<tr>
                            				<th>Project Name</th>
                            				<th>Sub Project Name</th>
                            				<th>EMD Type</th>
                            		    	<th>EMD Amount</th>
                            				<th>EMD Start Date</th>
                            				<th>EMD End Date</th>
                            				<th>Action</th>
                            			</tr>
                            		</thead>
				<tbody>
						<c:if test="${not empty emdList}">
							<c:forEach var="emd" items="${emdList}">
								<tr>
								    <td>${emd.aliasProjectName}</td>
								    <td>${emd.aliasSubProjectName}</td>
									<td>${emd.emdType}</td>
									<td>${emd.emdAmount}</td>
									<td>${emd.emdStartDate}</td>
									<td>${emd.emdEndDate}</td>
									<td><a href="/pms/emp/myview/buildEmd/${employeeObj.employeeId}?emdId=${emd.emdId}&action=updateEmd&aliasProjectName=${emd.aliasProjectName}&aliasSubProjectName=${emd.aliasSubProjectName}"
                                        class="userAction">Update</a>
									<strong> / </strong>
									<a id ="deleteRow" href ="javascript:deleteEmd('${emd.aliasProjectName}','${emd.emdId}');" style="color:red"> Delete</a>
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
		<div id="dialog-confirm"></div>
 	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>