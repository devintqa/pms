<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Create Sub Project</title>
<%@include file="Script.jsp"%>

<script>
$(document).ready(function () {
	  //called when key is pressed in textbox
	  $("#subAgreementPeriod").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg").html("Numbers Only").show().fadeOut("slow");
	               return false;
	    }
	   });
});

$(function(){
    if($('#isUpdate').val()=='Y') {
          	  	$("#aliasSubProjName").attr("readonly", "readonly");
     }
});

</script>
</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${subProjectCreationMessage}</h2>
		</div>
		<div>
			<form:form method="POST" commandName="subProjectForm"
				action="createSubProject.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Sub Project Details</legend>
						<table>
							<tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="projId" cssClass="inputText"
										items="${aliasProjectList}" /></td>
								<td><form:errors path="projId" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Sub Project Name<span id="colon">:</span>
								</td>
								<td><form:textarea path="subProjectName"
										placeholder="Enter Sub Project Name" cssClass="inputText"
										maxlength="2000" rows="5" cols="40" /></td>
								<td><form:errors path="subProjectName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Alias Sub Project Name<span id="colon">:</span>
								</td>
								<td><form:input path="aliasSubProjName"
										id="aliasSubProjName" placeholder="Enter Alias Project Name"
										cssClass="inputText" /></td>
								<td><form:errors path="aliasSubProjName" cssClass="error" /></td>
							</tr>
                            <tr>
								<td>Sub Project Type<span id="colon">:</span>
								</td>
								<td><form:select path="subProjectType" cssClass="inputText"
										items="${projectTypeList}" /></td>
								<td><form:errors path="subProjectType" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Estimate C.E.R. Number<span id="colon">:</span>
								</td>
								<td><form:input path="subCerNo"
										placeholder="Enter C.E.R. Number" cssClass="inputText" /></td>
								<td><form:errors path="subCerNo" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Estimated Amount<span id="colon">:</span>
								</td>
								<td><form:input path="subAmount"
										placeholder="Enter Estimated Amount" cssClass="inputText" /></td>
								<td><form:errors path="subAmount" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Name of Contractor<span id="colon">:</span>
								</td>
								<td><form:textarea path="subContractorName"
										placeholder="Enter Name of Contractor" cssClass="inputText"
										maxlength="2000" rows="5" cols="40" /></td>
								<td><form:errors path="subContractorName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Alias Name of Contractor<span id="colon">:</span>
								</td>
								<td><form:input path="subAliasContractorName"
										placeholder="Enter Alias Name of Contractor"
										cssClass="inputText" /></td>
								<td><form:errors path="subAliasContractorName"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Address of Contractor<span id="colon">:</span>
								</td>
								<td><form:textarea path="subContractorAddress"
										placeholder="Enter Address of Contractor" cssClass="inputText"
										maxlength="2000" rows="5" cols="40" /></td>
								<td><form:errors path="subContractorAddress"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Value<span id="colon">:</span>
								</td>
								<td><form:input path="subAgreementValue"
										placeholder="Enter Agreement Value" cssClass="inputText" /></td>
								<td><form:errors path="subAgreementValue" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Value put to Tender<span id="colon">:</span>
								</td>
								<td><form:input path="subTenderValue"
										placeholder="Enter Tender Value" cssClass="inputText" /></td>
								<td><form:errors path="subTenderValue" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Excess in Amount<span id="colon">:</span>
								</td>
								<td><form:input path="subExAmount"
										placeholder="Enter Excess in Amount" cssClass="inputText" /></td>
								<td><form:errors path="subExAmount" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Excess in Percentage<span id="colon">:</span>
								</td>
								<td><form:input path="subExPercentage"
										placeholder="Enter Excess in Percentage" cssClass="inputText" /></td>
								<td><form:errors path="subExPercentage" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Less in Percentage<span id="colon">:</span>
								</td>
								<td><form:input path="subLessPercentage"
										placeholder="Enter Less in Percentage" cssClass="inputText" /></td>
								<td><form:errors path="subLessPercentage" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Additional Security Deposit<span id="colon">:</span>
								</td>
								<td><form:input path="subAddSecurityDeposit"
										placeholder="Enter additional security deposit"
										cssClass="inputText" /></td>
								<td><form:errors path="subAddSecurityDeposit"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Performance Guarantee <span id="colon">:</span>
								</td>
								<td><form:input path="subPerformanceGuarantee"
										placeholder="Enter performance Guarantee" cssClass="inputText" /></td>
								<td><form:errors path="subPerformanceGuarantee"
										cssClass="error" /></td>
							</tr>
                            <tr>
								<td>Completion Date for Bonus<span id="colon">:</span>
								</td>
								<td><form:input path="subCompletionDateForBonus"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="subCompletionDateForBonus" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Date of Tender<span id="colon">:</span>
								</td>
								<td><form:input path="subTenderDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="subTenderDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Date of Agreement<span id="colon">:</span>
								</td>
								<td><form:input path="subAgreementDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="subAgreementDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Number<span id="colon">:</span>
								</td>
								<td><form:input path="subAgreementNo"
										placeholder="Enter Agreement Number" cssClass="inputText" /></td>
								<td><form:errors path="subAgreementNo" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Date of Commencement<span id="colon">:</span>
								</td>
								<td><form:input path="subCommencementDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="subCommencementDate"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Date of Completion<span id="colon">:</span>
								</td>
								<td><form:input path="subCompletionDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="subCompletionDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Period (in months)<span id="colon">:</span>
								</td>
								<td><form:input path="subAgreementPeriod"
										placeholder="Enter Sub Agreement Period (in months)"
										maxlength="4" cssClass="inputText" required="required" /></td>
								<td>&nbsp;<span id="errmsg"></span></td>
								<td><form:errors path="subAgreementPeriod" cssClass="error" /></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>

					<form:hidden path="employeeId" />
					<form:hidden path="projId" />
					<form:hidden path="subProjId" />
					<form:hidden path="isUpdate" />
					<table>
						<tr>
							<td></td>
							<td><input type="submit" /></td>
							<td></td>
						</tr>
					</table>
				</center>
				<br>
				<br>

			</form:form>
		</div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


