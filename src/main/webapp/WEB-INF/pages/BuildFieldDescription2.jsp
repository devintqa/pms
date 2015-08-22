<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Create Field Description</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
<script>
  var windowObjectReference = null;
  $(document).ready(function () {
        $("#showSearchProjectDesc").hide();
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
		
		  $("#submit").click(function(){
			  
			 
		  });
		  
	});
  
  function openProjDescLoader(projDescSerial, projId, subProjId, projDescId, descType, employeeId){
	  if(subProjId == ''){
		  subProjId = 0;
	  }
	  if($("#searchOnPsk").is(':checked')){
		  descType = "psk";
	  }else{
		  descType = "government";
	  }
	  windowObjectReference = window.open("/pms/emp/myview/buildProjectDesc/loadProjDescItems.do?projDescSerial="+projDescSerial+"&projId="+projId+"&subProjId="+subProjId+"&projDescId="+projDescId+"&type="+descType+"&employeeId="+employeeId,'winname','directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1200,height=700');
	  
  }

  function confirmOverwriteOfFieldData(aliasName,searchUnder){
        if(aliasName=="") {
            $("#dialog-confirm").html("Please Select Alias Project Name");
            $("#dialog-confirm").dialog({
                  modal: true,
                  title: "Warning!",
                  height: 180,
                  width: 300,
                  buttons: {
                    Ok: function() {
                      $( this ).dialog( "close" );
                    }
                  }
                });
            return;
        }

        $.ajax({
	        	    type : 'GET',
	        		url : 'FieldDescriptionPresent.do',
	        		data : "aliasName="+aliasName+'&searchUnder='+searchUnder,
	        		success : function(response) {
	    					    console.log("Successfully deleted row ");
	        			    },
	        	    error : function(err) {
	        				  console.log("Error deleting project description ");
	        			    }
	        	});
  }
  
  function deleteProjectDescription(projectDescriptionAlias, projectDescriptionId) {
	  
	  $("#dialog-confirm").html(projectDescriptionAlias + " : Deletion Operation!, Please confirm to proceed");

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
	        			url : 'FieldDescriptionPresent.do',
	        			data : "aliasName="+aliasProjectName&"isSubProject="+searchUnder,
	        			success : function(response) {
	        				location.reload();
	    					console.log("Successfully deleted row ");
	        			},
	        			error : function(err) {
	        				console.log("Error deleting project description ");
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
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
		</div>
		<div class="ui-widget">
			<form:form id="fieldDescriptionForm" method="POST"
				commandName="fieldDescriptionForm"
				action="createFieldDescriptions.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Create Field Description</legend>
						<table>
							<tr>
								<td>Alias Project Name <span id="colon">:</span>
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
							</tr>
							<tr></tr>
						</table>
					</fieldset>
					<table>
						<tr>
							<td></td>
							<td><input class="button" id="createFieldDescription" type="button" value="Create"
							onclick="confirmOverwriteOfFieldData($('#aliasProjectName').val(),$('#searchUnderProject').is(':checked'))"/></td>
							<td></td>
						</tr>
					</table>
				</center>
				<br>
				<br>
			<input id="searchOn" type="hidden">
			</form:form>
			<br>
			<br>
		</div>
		<div id="dialog-confirm"></div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>