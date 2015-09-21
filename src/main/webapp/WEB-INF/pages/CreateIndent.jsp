<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Create Project</title>
<%@include file="Script.jsp"%>
<style>
.dateField {
    border: 1px solid black;
    border-radius: 5px;
    padding: 5px;
}
</style>
<script>
	$(document).ready(function() {
		$(".collapse").accordion({
			collapsible : true,
			active : false
		});
		$(".dateField").datepicker(
				   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
		);
	});
	
	
</script>
</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">

		<div>
			<form:form id="indentForm" commandName="indentForm">

				<c:forEach var="indent" items="${indentList}">
					<div class="collapse">
						<h3>${indent.aliasProjDesc}</h3>
						<fieldset style="margin: 1em; text-align: left;">
							<legend> </legend>
							<table>
								<tr>
									<td>Start date<span id="colon">:</span>
									</td>
									<td><form:input  path="startDate" class="dateField" value="${indent.startDate}"  placeholder="DD-MM-YYYY"  /></td>
								</tr>
								<tr>
									<td>End date<span id="colon">:</span>
									</td>
									<td><form:input  path="endDate" class="dateField" value="${indent.endDate}" placeholder="DD-MM-YYYY"  /></td>
								</tr>
								<tr>
									<td>Quantity<span id="colon">:</span>
									</td>
									<td><form:input id="quantity" path="area" value="${indent.area}" cssClass="inputText" /></td>
								</tr>
								<tr>
									<td>Metric<span id="colon">:</span>
									</td>
									<td><form:input id="metric" path="metric" value="${indent.metric}" readOnly="true" cssClass="inputText" /></td>
								</tr>
								<tr></tr>
							</table>
							
							<table id="itemTable" border="1" class="gridView">
								<tr>
									<th>Type</th>
									<th>Item</th>
									<th>Unit</th>
									<th>Adequacy</th>
									<th>Action</th>
								</tr>
									<c:forEach var="row" items="${indent.itemDetails}">
									<tr>
									<td>${row.itemType}</td>
									<td>${row.itemName}</td>
									<td>${row.itemUnit}</td>
									<td>${row.itemQty}</td>
									<td><a id="deleteItem" onclick="deleteItemRow(this)">
									<img src="<c:url value="/resources/images/delete.png" />" /></a></td>
								</tr>
									</c:forEach>
							</table>
						</fieldset>
					</div>
				</c:forEach>
			</form:form>

		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


