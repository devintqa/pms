<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: EMD Details</title>

<%@include file="Script.jsp" %>

<script>
$(document).ready(function () {
	  $("#showSubProject").hide();

	  $("#showCompetitorName").hide();

	  if($("#competitor").is(":checked"))
	  {
	  	 $("#showCompetitorName").show();
	  }

	  //called when key is pressed in textbox
	  $("#emdPeriod").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg").html("Numbers Only").show().fadeOut("slow");
	               return false;
	    }
	   });
	  
	  $('#subProjectEMD').change(function() {
	        if($(this).is(":checked")) {
			var aliasProjectName  = $('#projId').val();
			$.ajax({
				type : "GET",
				url : "getSubAliasProject.do",
				cache : false,
				data: "aliasProjectName="+aliasProjectName,
				success : function(response) {
					var options = '';
					if (response != null) {
						var obj = jQuery.parseJSON(response);
						var options = '';
						//options = '<option value=0>--Please Select--</option>';
						for ( var key in obj) {
							var attrName = key;
							var attrValue = obj[key];
							options = options + '<option value='+attrName+'>'
									+ attrValue + '</option>';
						}
						$('#aliasSubProjectName').html(options);
					}
				}
			});
				$("#showSubProject").show();
	        }else {
	        	$("#aliasSubProjectName").prop('selectedIndex',0);
	        	$("#showSubProject").hide();
	        }
		}); 
	  $('#projId').change(function() {
		  $("#showSubProject").hide();
		  $('#subProjectEMD').attr('checked', false);
	  });

	  if($('#subProjectEMD').is(':checked')) {
	  		$("#showSubProject").show();
	  };
	  $('#competitor').change(function() {
		 $("#emdSubmitter").val('');
		 $("#showCompetitorName").show();
	  });
	  $('#psk').change(function() {
      	$("#showCompetitorName").hide();
      	$("#emdSubmitter").val('');
      });

});
</script>
</head>

<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${emdUpdationMessage}</h2>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${emdCreationMessage}</h2>
		</div>
		<div>
			<form:form method="POST" commandName="emdForm"
				action="createEmd.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>EMD Details</legend>
						<table>
							<tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="aliasProjectName"
										cssClass="inputText" id="projId" items="${aliasProjectList}" >
									</form:select></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>EMD For Sub Project? :</td>
								<td><form:checkbox path="subProjectEMD" id="subProjectEMD"/></td>
								<td><form:errors path="subProjectEMD" cssClass="error" /></td>
							</tr>
							<tr id="showSubProject">
								<td>Sub Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="aliasSubProjectName"
										id="aliasSubProjectName" cssClass="inputText"  items="${subAliasProjectList}">
										<c:if test="${projDescForm.subProjId gt '0'}">
											<option value="${projDescForm.subProjId}" selected="selected">${projDescForm.aliasSubProjectName}</option>
										</c:if>
									</form:select></td>
								<td><form:errors path="aliasSubProjectName"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>EMD Type <span id="colon">:</span>
								</td>
								<td><form:select path="emdType" cssClass="inputText"
										items="${emdTypeList}" /></td>
								<td><form:errors path="emdType" cssClass="error" /></td>
							</tr>
							<tr>
								<td>EMD For <span id="colon">:</span></td>
								<td><form:radiobutton path="emdFor" id="psk" value="PSK" checked="true"/>PSK
                                <form:radiobutton  path="emdFor" id="competitor"  value="competitor"/>Competitor</td>
                                <td><form:errors path="emdFor" cssClass="error" /></td>
							</tr>
						    <tr id="showCompetitorName">
								<td>Competitor Name<span id="colon">:</span>
								</td>
								<td><form:input path="emdSubmitter"
										placeholder="Enter Competitor Name" cssClass="inputText" /></td>
								<td><form:errors path="emdSubmitter" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Bank Guarantee Number<span id="colon">:</span>
								</td>
								<td><form:input path="bgNumber"
										placeholder="Enter Bank Guarantee Number" cssClass="inputText" /></td>
								<td><form:errors path="bgNumber" cssClass="error" /></td>
							</tr>
							<tr>
								<td>EMD Start Date<span id="colon">:</span>
								</td>
								<td><form:input path="emdStartDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="emdStartDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>EMD End Date<span id="colon">:</span>
								</td>
								<td><form:input path="emdEndDate" placeholder="DD-MM-YYYY"
										cssClass="inputText" /></td>
								<td><form:errors path="emdEndDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>EMD Period (in months)<span id="colon">:</span>
								</td>
								<td><form:input path="emdPeriod"
										placeholder="Enter EMD Period (in months)" maxlength="4"
										cssClass="inputText" required="required" /></td>
								<td>&nbsp;<span id="errmsg"></span></td>
								<td><form:errors path="emdPeriod" cssClass="error" /></td>
							</tr>
							<tr>
								<td>EMD Extension Date<span id="colon">:</span>
								</td>
								<td><form:input path="emdExtensionDate" placeholder="DD-MM-YYYY"
										cssClass="inputText" /></td>
								<td><form:errors path="emdExtensionDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>EMD Amount<span id="colon">:</span>
								</td>
								<td><form:input path="emdAmount"
										placeholder="Enter EMD Amount" cssClass="inputText" /></td>
								<td><form:errors path="emdAmount" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Ledger Number<span id="colon">:</span>
								</td>
								<td><form:input path="emdLedgerNumber"
										placeholder="Enter Ledger Number" cssClass="inputText" /></td>
								<td><form:errors path="emdLedgerNumber" cssClass="error" /></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>

					<form:hidden path="employeeId" />
					<form:hidden path="projId" />
					<form:hidden path="subProjId" />
					<form:hidden path="isUpdate" />
					<form:hidden path="emdId" />
					
					<table>
						<tr>
							<td></td>
							<td><input type="submit" /></td>
							<td></td>
						</tr>
					</table>
				</center>
				<br>
			</form:form>

		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


