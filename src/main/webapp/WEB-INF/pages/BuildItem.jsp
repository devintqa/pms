<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Create Item</title>
<%@include file="Script.jsp" %>

<script>
$(document).ready(function () {
	$("#itemName").autocomplete({
		source: function (request, response) {
			$.getJSON("/pms/emp/myview/buildItem/searchItem.do", {
            	                term: request.term
            	            }, response);
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
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${itemUpdationMessage}</h2>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${itemCreationMessage}</h2>
		</div>
		<div>
			<form:form method="POST" commandName="itemForm"
				action="createItem.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Item Details</legend>
						<table>
							<tr>
								<td>Item Name<span id="colon">:</span>
								</td>
								<td><form:input path="itemName"
										placeholder="Enter Item Name" cssClass="inputText" /></td>
								<td><form:errors path="itemName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Item Unit<span id="colon">:</span>
								</td>
								<td><form:input path="itemUnit"
										placeholder="Enter Item Unit" cssClass="inputText" /></td>
								<td><form:errors path="itemUnit" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Item Type<span id="colon">:</span>
								</td>
								<td><form:select path="itemType"
								cssClass="itemType" items="${itemTypes}"/></td>
								<td><form:errors path="itemType" cssClass="error" /></td>
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
			</form:form>

		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


