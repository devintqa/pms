<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Create PWD Description</title>
<%@include file="Script.jsp" %>
<script type="text/javascript">
$("#quantity").attr("readonly", "readonly");
</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${projDescCreationMessage}</h2>
		</div>
		<div>
			<form:form id="baseDescForm" method="POST" commandName="baseDescForm"
				action="createOrUpdate.do">
				<!--<form:errors path="*" cssClass="errorblock" element="div" />
				-->
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>PWD Project Description</legend>
						<table>
							<tr>
								<td>Work Type <span id="colon">:</span>
								</td>
								<td><form:select path="workType" cssClass="inputText"
										id="workType" items="${workTypeList}" /></td>
								<td><form:errors path="workType" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Description<span id="colon">:</span>
								</td>
								<td><form:textarea path="description"
										placeholder="Enter description" cssClass="inputText" /></td>
								<td><form:errors path="description" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Base Description<span id="colon">:</span>
								</td>
								<td><form:input path="aliasDescription"
										placeholder="Enter Base description" cssClass="inputText" /></td>
								<td><form:errors path="aliasDescription" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Quantity<span id="colon">:</span>
								</td>
								<td><form:input id="quantity" path="quantity"
										placeholder="Enter quantity for the selected work" cssClass="inputText" /></td>
								<td><form:errors path="quantity" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Metric<span id="colon">:</span>
								</td>
								<td><form:input id="metric" path="metric"
										placeholder="Enter quantity metric" cssClass="inputText" /></td>
								<td><form:errors path="metric" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Total Cost<span id="colon">:</span>
								</td>
								<td><form:input path="totalCost" cssClass="inputText" /></td>
								<td><form:errors path="totalCost" cssClass="error" /></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>
					<form:hidden path="employeeId" />
					<form:hidden path="isUpdate" />
					<form:hidden path="projDescId" />
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
