<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: View Details</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
<script>
  $(document).ready(function () {
		$("#aliasProjectName").autocomplete({
			source: function (request, response) {
			if($("#viewUnderSubProject").is(':checked'))	{
				$.getJSON("/pms/emp/myview/viewDetails/searchSubProject.do", {
                	                term: request.term
                	            }, response);
			}else{
				$.getJSON("/pms/emp/myview/viewDetails/searchProject.do", {
                	                term: request.term
                	            }, response);
				}
	        }
		});
	});
 </script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
		</div>
		<div class="ui-widget">
			<form:form id="viewDetailsForm" method="POST"
				commandName="viewDetailsForm"
				action="viewDetails.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>View Details</legend>
						<table>
							<tr>
								<td>Alias Project Name<span id="colon">:</span>
								</td>
								<td><form:input path="aliasProjectName"
										id="aliasProjectName" placeholder="Enter Alias Project Name"
										cssClass="inputText" /></td>
								<td><form:errors path="aliasProjectName" cssClass="error" /></td>
							</tr>
							<tr id="viewProjectItemPrice">
								<td>View Project Item Price? :</td>
								<td><form:checkbox path="viewProjectItemPrice"
										id="viewProjectItemPrice" /></td>
								<td><form:errors path="viewProjectItemPrice"
										cssClass="error" /></td>
							</tr>
							<tr id="showSearchItemDesc">
								<td>View Project Item Rate With Quantity :</td>
								<td><form:checkbox path="searchAggregateItemDetails"
										id="searchAggregateItemDetails" /></td>
								<td><form:errors path="searchAggregateItemDetails"
										cssClass="error" /></td>
							</tr>
							<tr id="showComparativeAnalysis">
								<td>View Project Comparative Analysis :</td>
								<td><form:checkbox path="searchComparisonData"
										id="searchComparisonData" /></td>
								<td><form:errors path="searchComparisonData"
										cssClass="error" /></td>
							</tr>
							<tr>
								<td>Search under :</td>
								<td><form:radiobutton path="searchUnder" value="Project"
										id="searchUnderProject" checked="true" />Project</td>
								<td><form:radiobutton path="searchUnder" value="subProject"
										id="viewUnderSubProject" />Sub Project</td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>
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

			<c:if test="${itemPriceDetailsSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName}
					Item Price Configuration</h1>
				<table id="projectItemPriceList" class="gridView">
					<thead>
						<tr>
							<th>Item Type</th>
							<th>Item Name</th>
							<th>Unit</th>
							<th>Price</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty itemPriceDetails}">
							<c:forEach var="item" items="${itemPriceDetails}">
								<tr>
									<td>${item.itemType}</td>
									<td>${item.itemName}</td>
									<td>${item.itemUnit}</td>
									<td>${item.itemPrice}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
			
			<c:if test="${aggregateItemDetailsSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName}
					Aggregate Material Details</h1>
				<table id="itemDescList" class="gridView">
					<thead>
						<tr>
							<th>Item Type</th>
							<th>Item Name</th>
							<th>Item Unit</th>
							<th>Item Price</th>
							<th>Total Adequacy</th>
							<th>Total Item Cost</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty aggregateItemDetails}">
							<c:forEach var="item" items="${aggregateItemDetails}">
								<tr>
									<td>${item.itemType}</td>
									<td>${item.itemName}</td>
									<td>${item.itemUnit}</td>
									<td>${item.itemPrice}</td>								
									<td>${item.itemQty}</td>
									<td>${item.itemCost}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
			
			<c:if test="${projDescComparisonDetailsSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">${projectAliasName}
					Comparison Data</h1>
				<table id="compareDescList" class="gridView">
					<thead>
						<tr>
							<th>Serial Number</th>
							<th>Alias Description</th>
							<th>Quantity</th>
							<th>Metric</th>
							<th>Department Price Per Quantity</th>
							<th>Department Total Amount</th>
							<th>PSK Price Per Quantity</th>
							<th>PSK Total Amount</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty projDescComparisonDetails}">
							<c:forEach var="desc" items="${projDescComparisonDetails}">
								<tr>
									<td>${desc.serialNumber}</td>
									<td>${desc.aliasDescription}</td>
									<td>${desc.quantity}</td>
									<td>${desc.metric}</td>								
									<td>${desc.deptPricePerQuantity}</td>
									<td>${desc.deptTotalCost}</td>
									<td>${desc.pricePerQuantity}</td>
									<td>${desc.totalCost}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
			
		</div>
		<div id="dialog-confirm"></div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>