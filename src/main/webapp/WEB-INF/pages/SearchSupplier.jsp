<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>PMS :: Search Supplier</title>
<%@include file="Script.jsp"%>
<%@include file="Utility.jsp"%>
<script>
	function deleteSupplier(supplierAlias, supplierId) {
		$("#dialog-confirm").html(supplierAlias + " : Deletion Operation!, Please confirm to proceed");
		$("#dialog-confirm").dialog({
			resizable : false,
			modal : true,
			title : "Warning!",
			height : 200,
			width : 400,
			buttons : {
				"Yes" : function() {
					$.ajax({
						type : 'POST',
						url : 'deleteSupplier.do',
						data : "supplierId=" + supplierId,
						success : function(response) {
							location.reload();
							console.log("Successfully deleted row ");
						},
						error : function(err) {
							console.log("Error deleting Project ");
						}
					});
					$(this).dialog('close');
					window.location.reload();

				},
				"No" : function() {
					$(this).dialog('close');

				}
			}
		});
	}
</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${actionMessage}</h2>
		</div>
		<div class="ui-widget">
			<c:if test="${not empty supplierList}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">Supplier Details</h1>
				<table id="supplierDetails" class="display" width="100%">
					<thead>
						<tr>
							<th>Alias Supplier Name</th>
							<th>Supplier Name</th>
							<th>TIN No</th>
							<th>Phone Number</th>
							<th>Supplier Address</th>
							<th>Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="supplier" items="${supplierList}">
							<tr>
								<td>${supplier.aliasName}</td>
								<td>${supplier.name}</td>
								<td>${supplier.tinNumber}</td>
								<td>${supplier.phoneNumber}</td>
								<td>${supplier.emailAddress}</td>
								<td><a
									href="/pms/emp/myview/updateSupplier/${employeeObj.employeeId}?team=${employeeObj.employeeTeam}&supplierId=${supplier.supplierId}"
									class="userAction">Update
									</a> <strong> / </strong>
									<a id="deleteRow"
									    href="javascript:deleteSupplier('${supplier.aliasName}','${supplier.supplierId}');"
									    style="color: red"> Delete</a>

								</td>
							</tr>
						</c:forEach>
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