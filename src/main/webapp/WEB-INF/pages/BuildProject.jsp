<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Create Project</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet" type="text/css" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
<script	src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="<c:url value="/resources/js/script.js" />"></script>
</head>


<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<form:form method="POST" commandName="projectForm"
				action="createProject.do">
				<!--<form:errors path="*" cssClass="errorblock" element="div" />
				--><center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Project Details</legend>
						<table>
						<tr>
							<td>Project Name<span id="colon">:</span>
							</td>
							<td><form:textarea path="projectName"
									placeholder="Enter Project Name" cssClass="inputText" /></td>
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
							<td>Original Agreement Number<span id="colon">:</span>
							</td>
							<td><form:input path="agreementNo"
									placeholder="Enter Original Agreement Number" cssClass="inputText" /></td>
							<td><form:errors path="agreementNo" cssClass="error" /></td>
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
							<td><form:input path="Amount"
									placeholder="Enter Estimated Amount" cssClass="inputText" /></td>
							<td><form:errors path="Amount" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Name of Contractor<span id="colon">:</span>
							</td>
							<td><form:input path="contractorName"
									placeholder="Enter Name of Contractor" cssClass="inputText" /></td>
							<td><form:errors path="contractorName" cssClass="error" /></td>
						</tr>
						<tr>
							<td>Address of Contractor<span id="colon">:</span>
							</td>
							<td><form:input path="contractorAddress"
									placeholder="Enter Address of Contractor" cssClass="inputText" /></td>
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
							<td><form:input path="agreementValue" placeholder="Enter Agreement Value"
									 cssClass="inputText" /></td>
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
							<td><form:errors path="exAmount"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Excess in Percentage<span id="colon">:</span>
							</td>
							<td><form:input path="exPercentage"
									placeholder="Enter Excess in Percentage" cssClass="inputText" /></td>
							<td><form:errors path="exPercentage"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>EMD Amount<span id="colon">:</span>
							</td>
							<td><form:input path="emdAmount"
									placeholder="Enter EMD Amount" cssClass="inputText" /></td>
							<td><form:errors path="emdAmount"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>EMD Start Date<span id="colon">:</span>
							</td>
							<td><form:input path="emdStartDate"
									placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
							<td><form:errors path="emdStartDate"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>EMD End Date<span id="colon">:</span>
							</td>
							<td><form:input path="emdEndDate"
									placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
							<td><form:errors path="emdEndDate"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Date of Tender<span id="colon">:</span>
							</td>
							<td><form:input path="tenderDate"
									placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
							<td><form:errors path="tenderDate"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Date of Agreement<span id="colon">:</span>
							</td>
							<td><form:input path="agreementDate"
									placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
							<td><form:errors path="agreementDate"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Date of Commencement<span id="colon">:</span>
							</td>
							<td><form:input path="commencementDate"
									placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
							<td><form:errors path="commencementDate"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Date of Completion<span id="colon">:</span>
							</td>
							<td><form:input path="completionDate"
									placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
							<td><form:errors path="completionDate"
									cssClass="error" /></td>
						</tr>
						<tr>
							<td>Agreement Period (in months)<span id="colon">:</span>
							</td>
							<td><form:input path="agreementPeriod"
									placeholder="Enter Agreement Period (in months)" cssClass="inputText" /></td>
							<td><form:errors path="agreementPeriod"
									cssClass="error" /></td>
						</tr>
						<tr></tr>
					</table>
					</fieldset>

					<form:hidden path="employeeId" />
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


