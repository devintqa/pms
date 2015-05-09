<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<style>
#errmsg {
	color: red;
}
</style>
<title>PMS :: Create Sub Project</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<script src="<c:url value="/resources/js/script.js" />"></script>
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script
	src="<c:url value="/resources/js/jquery.dataTables.1.10.6.min.js" />"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery.dataTables.1.10.6.css" />">
<script>
	$(function() {
		var table = $("#projDescDocListSize").dataTable();
	})
</script>
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
</script>
</head>

<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<form:form method="POST" commandName="subProjectForm"
				action="createSubProject.do">
				<!--<form:errors path="*" cssClass="errorblock" element="div" />
				-->
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
										placeholder="Enter Sub Project Name" cssClass="inputText" /></td>
								<td><form:errors path="subProjectName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Alias Sub Project Name<span id="colon">:</span>
								</td>
								<td><form:input path="aliasSubProjName"
										placeholder="Enter Alias Project Name" cssClass="inputText" /></td>
								<td><form:errors path="aliasSubProjName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Original Agreement Number<span id="colon">:</span>
								</td>
								<td><form:input path="subAgreementNo"
										placeholder="Enter Original Agreement Number"
										cssClass="inputText" /></td>
								<td><form:errors path="subAgreementNo" cssClass="error" /></td>
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
								<td><form:input path="subContractorName"
										placeholder="Enter Name of Contractor" cssClass="inputText" /></td>
								<td><form:errors path="subContractorName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Address of Contractor<span id="colon">:</span>
								</td>
								<td><form:textarea path="subContractorAddress"
										placeholder="Enter Address of Contractor" cssClass="inputText" /></td>
								<td><form:errors path="subContractorAddress"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Contractors value<span id="colon">:</span>
								</td>
								<td><form:input path="subContractValue"
										placeholder="Enter Contractors Value" cssClass="inputText" /></td>
								<td><form:errors path="subContractValue" cssClass="error" /></td>
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

		<c:if test="${projDescDocListSize gt 0}">
			<h1 style="text-align: center; color: #C6311D; font-size: 24px;">Project
				Description Details</h1>
			<table id="projDescDocListSize" class="gridView">
				<thead>
					<tr>
						<th>Name</th>
						<th>Alias</th>
						<th>Work Type</th>
						<th>Quantity</th>
						<th>Rate</th>
						<th>Project Description Amount</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty projDescDocList}">
						<c:forEach var="projDesc" items="${projDescDocList}">
							<tr>
								<td>${projDesc.description}</td>
								<td><a href="#" class="userAction">${projDesc.aliasDescription}</a></td>
								<td>${projDesc.workType}</td>
								<td>${projDesc.quantityInFig}</td>
								<td>${projDesc.rateInFig}</td>
								<td>${projDesc.projDescAmount}</td>
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


