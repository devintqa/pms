<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Create Project</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="<c:url value="/resources/js/script.js" />"></script>
</head>


<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div>
		<div>
			<form:form method="POST" commandName="createProjectForm"
				action="createProject.do">
				<form:errors path="*" cssClass="errorblock" element="div" />
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Project Details</legend>
						<table>
							<tr>
								<td>Project <span id="colon">:</span>
								</td>
								<td><form:input path="project"
										placeholder="Enter Project Name" cssClass="inputText" /></td>
								<td><form:errors path="project" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Alias <span id="colon">:</span>
								</td>
								<td><form:input path="alias" placeholder="Enter Alias Name"
										cssClass="inputText" /></td>
								<td><form:errors path="alias" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement No <span id="colon">:</span>
								</td>
								<td><form:input path="agreementNo"
										placeholder="Enter Agreement No" cssClass="inputText" /></td>
								<td><form:errors path="agreementNo" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Date <span id="colon">:</span>
								</td>
								<td><form:input path="agreementDate"
										placeholder="DD-MM-YYYY" id="agreementDate"
										cssClass="inputText" /></td>
								<td><form:errors path="agreementDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Value <span id="colon">:</span>
								</td>
								<td><form:input path="agreementValue"
										placeholder="Enter Agreement Value" cssClass="inputText" /></td>
								<td><form:errors path="agreementValue" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Agreement Period <span id="colon">:</span>
								</td>
								<td><form:input path="agreementPeriod"
										placeholder="Enter Agremment Period" cssClass="inputText" /></td>
								<td><form:errors path="agreementPeriod" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Estimate No <span id="colon">:</span>
								</td>
								<td><form:input path="estimationNo"
										placeholder="Enter Estimation No" cssClass="inputText" /></td>
								<td><form:errors path="estimationNo" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Estimate Amount <span id="colon">:</span>
								</td>
								<td><form:input path="estimationAmount"
										placeholder="Enter Estimation Amount" cssClass="inputText" /></td>
								<td><form:errors path="estimationAmount" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Contractor Name <span id="colon">:</span>
								</td>
								<td><form:input path="contractorName"
										placeholder="Enter Contractor Name" cssClass="inputText" /></td>
								<td><form:errors path="contractorName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Contractor Address <span id="colon">:</span>
								</td>
								<td><form:textarea path="contractorAddress"
										placeholder="Enter Contractor Address" cssClass="inputText" /></td>
								<td><form:errors path="contractorAddress" cssClass="error" /></td>
							</tr>

							<tr>
								<td>Tender Date <span id="colon">:</span>
								</td>
								<td><form:input path="tenderDate" placeholder="DD-MM-YYYY"
										id="tenderDate" cssClass="inputText" /></td>
								<td><form:errors path="tenderDate" cssClass="error" /></td>
							</tr>

							<tr>
								<td>Commencement Date <span id="colon">:</span>
								</td>
								<td><form:input path="commencementDate"
										placeholder="DD-MM-YYYY" id="commencementDate"
										cssClass="inputText" /></td>
								<td><form:errors path="commencementDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Completion Date <span id="colon">:</span>
								</td>
								<td><form:input path="completionDate"
										placeholder="DD-MM-YYYY" id="completionDate"
										cssClass="inputText" /></td>
								<td><form:errors path="completionDate" cssClass="error" /></td>
							</tr>
						</table>
					</fieldset>

					<form:hidden path="empId" />
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


