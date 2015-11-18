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
    
    function openProjDescLoader(projDescSerial, projId, subProjId, projDescId, descType, employeeId){
  	  if(subProjId == ''){
  		  subProjId = 0;
  	  }
  	  descType = $("#searchOn").val();
  	  windowObjectReference = window.open("/pms/emp/myview/buildProjectDesc/loadProjDescItems.do?projDescSerial="+projDescSerial+"&projId="+projId+"&subProjId="+subProjId+"&projDescId="+projDescId+"&descType="+descType+"&employeeId="+employeeId,'winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1200,height=700');
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

          $('input[name="indentDescription"]').change(function(){
        	  $('#error').text('');
          });
            
		 $("#doIndent").click(function() {
		     var projDescs = [];
		     var projId = '';
		     var projDescId = '';
		     var employeeId = '';
		     
		      $($('input[name="indentDescription"]:checked')).each(function(index) {
		         
		          console.log(index + ": " + $(this).attr('aria-proj-id'));
		          console.log(index + ": " + $(this).attr('aria-projdesc-id'));
		          console.log(index + ": " + $(this).attr('aria-employee-id'));
		          projId = $(this).attr('aria-proj-id');
		          projDescId = $(this).attr('aria-projdesc-id');
		          employeeId = $(this).attr('aria-employee-id');
		
		          projDescs.push(projDescId);
		          
		      });
		         if($('input[name="indentDescription"]:checked').length > 0){
		         	window.location = "/pms/emp/myview/indent/createIndent?employeeId="+employeeId+"&projectId="+projId+"&projDescs="+projDescs;
		         }else{
		         	$('#error').text('Please select any description to build indent');
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
			<form:form id="searchProjDescForm" method="POST"
				commandName="searchProjDescForm"
				action="searchFieldDescDetail.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Search Field Description</legend>
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
								</c:if>
							</td>
						</tr>
					</table>
					 <form:hidden path="role"/>
					<span id="error" style="color:red;"></span>
				</center>
				<br>
				<div>
					<h2 style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
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
							<th>Total Quantity</th>
							<th>Available Quantity</th>
							<th>Metric</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty projDescDocList}">
							<c:forEach var="projDesc" items="${projDescDocList}">
								<tr>
										<td><input type="checkbox" name="indentDescription"
											aria-proj-id="${projDesc.projId}"
											aria-projdesc-id="${projDesc.projDescId}"
											aria-employee-id="${employeeObj.employeeId}" /></td>
									<td>
									<a href="javascript:openProjDescLoader('${projDesc.serialNumber}','${projDesc.projId}','${projDesc.subProjId}','${projDesc.projDescId}','FIELD','${employeeObj.employeeId}')"
										class="userAction">${projDesc.serialNumber}</a></td>
									<td>${projDesc.aliasDescription}</td>
									<td>${projDesc.workType}</td>
									<td>${projDesc.quantity}</td>
									<td>${projDesc.availableQty}</td>
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