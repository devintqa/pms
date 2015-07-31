<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Create Project</title>
<%@include file="Script.jsp"%>

<script>
$(document).ready(function () {
	  $("#agreementPeriod").keypress(function (e) {
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        $("#errmsg").html("Numbers Only").show().fadeOut("slow");
	               return false;
	    }
	   });
});
$(function(){
    if($('#isUpdate').val()=='Y') {
          	  	$("#aliasName").attr("readonly", "readonly");
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
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${projectUpdationMessage}</h2>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${projectCreationMessage}</h2>
		</div>
		<div>
			<form:form method="POST" commandName="projectForm"
				action="createProject.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Project Details</legend>
						<table>
							<tr>
								<td>Project Name<span id="colon">:</span>
								</td>
								<td><form:textarea path="projectName"
										placeholder="Enter Project Name" cssClass="inputText"
										maxlength="2000" rows="5" cols="40" /></td>
								<td><form:errors path="projectName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Alias Project Name<span id="colon">:</span>
								</td>
								<td><form:input path="aliasName"
										placeholder="Enter Alias Project Name" cssClass="inputText" /></td>
								<td><form:errors path="aliasName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Estimate C.E.R. Number<span id="colon">:</span>
								</td>
								<td><form:input path="cerNo"
										placeholder="Enter C.E.R. Number" cssClass="inputText" /></td>
								<td><form:errors path="cerNo" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Estimated Amount<span id="colon">:</span>
								</td>
								<td><form:input path="amount"
										placeholder="Enter Estimated Amount" cssClass="inputText" /></td>
								<td><form:errors path="amount" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Name of Contractor<span id="colon">:</span>
								</td>
								<td><form:textarea path="contractorName"
										placeholder="Enter Name of Contractor" cssClass="inputText"
										rows="5" cols="40" maxlength="2000" /></td>
								<td><form:errors path="contractorName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Alias Name of Contractor<span id="colon">:</span>
								</td>
								<td><form:input path="aliasContractorName"
										placeholder="Enter Alias Contractor Name" cssClass="inputText" /></td>
								<td><form:errors path="aliasContractorName"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Address of Contractor<span id="colon">:</span>
								</td>
								<td><form:textarea path="contractorAddress"
										placeholder="Enter Address of Contractor" cssClass="inputText"
										rows="5" cols="40" maxlength="2000" /></td>
								<td><form:errors path="contractorAddress" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Contractors value<span id="colon">:</span>
								</td>
								<td><form:input path="contractValue"
										placeholder="Enter Contractors Value" cssClass="inputText" /></td>
								<td><form:errors path="contractValue" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Value<span id="colon">:</span>
								</td>
								<td><form:input path="agreementValue"
										placeholder="Enter Agreement Value" cssClass="inputText" /></td>
								<td><form:errors path="agreementValue" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Value put to Tender<span id="colon">:</span>
								</td>
								<td><form:input path="tenderValue"
										placeholder="Enter Tender Value" cssClass="inputText" /></td>
								<td><form:errors path="tenderValue" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Excess in Amount<span id="colon">:</span>
								</td>
								<td><form:input path="exAmount"
										placeholder="Enter Excess in Amount" cssClass="inputText" /></td>
								<td><form:errors path="exAmount" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Excess in Percentage<span id="colon">:</span>
								</td>
								<td><form:input path="exPercentage"
										placeholder="Enter Excess in Percentage" cssClass="inputText" /></td>
								<td><form:errors path="exPercentage" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Less in Percentage<span id="colon">:</span>
								</td>
								<td><form:input path="lessPercentage"
										placeholder="Enter Less in Percentage" cssClass="inputText" /></td>
								<td><form:errors path="lessPercentage" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Additional Security Deposit<span id="colon">:</span>
								</td>
								<td><form:input path="addSecurityDeposit"
										placeholder="Enter security deposit amount"
										cssClass="inputText" /></td>
								<td><form:errors path="addSecurityDeposit" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Performance Guarantee <span id="colon">:</span>
								</td>
								<td><form:input path="performanceGuarantee"
										placeholder="Enter performance Guarantee" cssClass="inputText" /></td>
								<td><form:errors path="performanceGuarantee"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Date of Tender<span id="colon">:</span>
								</td>
								<td><form:input path="tenderDate" placeholder="DD-MM-YYYY"
										cssClass="inputText" /></td>
								<td><form:errors path="tenderDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Number<span id="colon">:</span>
								</td>
								<td><form:input path="agreementNo"
										placeholder="Enter Agreement Number" cssClass="inputText" /></td>
								<td><form:errors path="agreementNo" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Date of Agreement<span id="colon">:</span>
								</td>
								<td><form:input path="agreementDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="agreementDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Date of Commencement<span id="colon">:</span>
								</td>
								<td><form:input path="commencementDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="commencementDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Date of Completion<span id="colon">:</span>
								</td>
								<td><form:input path="completionDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="completionDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Period (in months)<span id="colon">:</span>
								</td>
								<td><form:input path="agreementPeriod"
										placeholder="Enter Agreement Period (in months)" maxlength="4"
										cssClass="inputText" required="required" /></td>
								<td>&nbsp;<span id="errmsg"></span></td>
								<td><form:errors path="agreementPeriod" cssClass="error" /></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>

					<form:hidden path="employeeId" />
					<form:hidden path="projId" />
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
			</form:form>

		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


