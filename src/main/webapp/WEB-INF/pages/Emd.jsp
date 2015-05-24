<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: EMD Details</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-ui-1.10.3.css" />">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui-1.10.3.js" />"></script>
<script src="<c:url value="/resources/js/script.js" />"></script>
<script
	src="<c:url value="/resources/js/jquery.dataTables.1.10.6.min.js" />"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/jquery.dataTables.1.10.6.css" />">
<style>
#errmsg {
	color: red;
}
</style>

<script>
$(document).ready(function () {
	  //called when key is pressed in textbox
	  $("#emdPeriod").keypress(function (e) {
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
			<h2
				style="text-align: left; font-family: arial; color: #C6311D; font-size: 14px;">${emdUpdationMessage}</h2>
			<h2
				style="text-align: left; font-family: arial; color: #C6311D; font-size: 14px;">${emdCreationMessage}</h2>
		</div>
		<div>
			<form:form method="POST" commandName="emdForm"
				action="createEmd.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>EMD Details</legend>
						<table>
							<tr>
								<td>EMD Type <span id="colon">:</span>
								</td>
								<td><form:select path="emdType" cssClass="inputText"
										items="${emdTypeList}" /></td>
								<td><form:errors path="emdType" cssClass="error" /></td>
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
								<td>EMD Ledger Number<span id="colon">:</span>
								</td>
								<td><form:input path="emdLedgerNumber"
										placeholder="Enter EMD Ledger Number" cssClass="inputText" /></td>
								<td><form:errors path="emdLedgerNumber" cssClass="error" /></td>
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
			</form:form>

		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


