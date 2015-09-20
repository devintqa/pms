<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Lead Details</title>
<%@include file="Script.jsp" %>

<script>
		function deleteLeadDetailRow(row) {
			var leadDetailTable = document.getElementById('leadDetailTable');
			var noOfRow = document.getElementById('leadDetailTable').rows.length;
			if (noOfRow > 2) {
				var i = row.parentNode.parentNode.rowIndex;
				document.getElementById('leadDetailTable').deleteRow(i);
			} else{
				document.getElementById('leadDetailTable').rows[1].cells[0].getElementsByTagName('input')[0].value = '';
				document.getElementById('leadDetailTable').rows[1].cells[1].getElementsByTagName('input')[0].value = '';
				document.getElementById('leadDetailTable').rows[1].cells[2].getElementsByTagName('input')[0].value = '';
				document.getElementById('leadDetailTable').rows[1].cells[3].getElementsByTagName('input')[0].value = '';
				document.getElementById('leadDetailTable').rows[1].cells[4].getElementsByTagName('input')[0].value = '';
				document.getElementById('leadDetailTable').rows[1].cells[5].getElementsByTagName('input')[0].value = '';
				document.getElementById('leadDetailTable').rows[1].cells[6].getElementsByTagName('input')[0].value = '';
				document.getElementById('leadDetailTable').rows[1].cells[7].getElementsByTagName('input')[0].value = '';
			}
		}

		function insertLeadDetailRow() {
			var leadDetailTable = document.getElementById('leadDetailTable');
			var new_row = leadDetailTable.rows[1].cloneNode(true);
			var len = leadDetailTable.rows.length;

			var material = new_row.cells[0].getElementsByTagName('input')[0];
			material.id += len;
			material.value = '';

			var sourceOfSupply = new_row.cells[1].getElementsByTagName('input')[0];
			sourceOfSupply.id += len;
			sourceOfSupply.value = '';

			var distance = new_row.cells[2].getElementsByTagName('input')[0];
			distance.id += len;
			distance.value = '';

			var cost = new_row.cells[3].getElementsByTagName('input')[0];
			cost.id += len;
			cost.value = '';

            var ic = new_row.cells[4].getElementsByTagName('input')[0];
			ic.id += len;
			ic.value = '';

            var leadCharges = new_row.cells[5].getElementsByTagName('input')[0];
            leadCharges.id += len;
            leadCharges.value = '';

            var loadingUnloading = new_row.cells[6].getElementsByTagName('input')[0];
			loadingUnloading.id += len;
			loadingUnloading.value = '';

            var total = new_row.cells[7].getElementsByTagName('input')[0];
			total.id += len;
			total.value = '';
			leadDetailTable.appendChild(new_row);
		}

		function LeadDetail(material, sourceOfSupply, distance, cost, ic, leadCharges, loadingUnloading, total) {
		    this.leadDetailId = '';
			this.material = material;
			this.sourceOfSupply = sourceOfSupply;
			this.distance = distance;
			this.cost = cost;
            this.ic = ic;
			this.leadCharges = leadCharges;
			this.loadingUnloading = loadingUnloading;
			this.total = total;
			}

		function saveLeadDetails() {
			var leadDetailTable = document.getElementById('leadDetailTable');
			var leadDetailArray = [];
			var leadDetailForm = {};
			var len = leadDetailTable.rows.length;
			var err = null;
			for (i = 1; i <= len - 1; i++) {
				var material = leadDetailTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
				var sourceOfSupply = leadDetailTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
				var distance = leadDetailTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
				var cost = leadDetailTable.rows[i].cells[3].getElementsByTagName('input')[0].value;
				var ic = leadDetailTable.rows[i].cells[4].getElementsByTagName('input')[0].value;
				var leadCharges = leadDetailTable.rows[i].cells[5].getElementsByTagName('input')[0].value;
				var loadingUnloading = leadDetailTable.rows[i].cells[6].getElementsByTagName('input')[0].value;
				var total = leadDetailTable.rows[i].cells[7].getElementsByTagName('input')[0].value;

				var obj = new LeadDetail(material, sourceOfSupply, distance, cost, ic, leadCharges, loadingUnloading, total);
					leadDetailArray.push(obj);
			}
			leadDetailForm["employeeId"] = document.getElementById('employeeId').value;
			leadDetailForm["projectId"] = document.getElementById('projectId').value;
			leadDetailForm["subProjectId"] = document.getElementById('subProjectId').value;
			leadDetailForm["leadConfiguration"] = JSON.stringify(leadDetailArray);

			console.log("data = " + JSON.stringify(leadDetailForm));
			if(err){
				alert("Please make sure that all the required fields are entered.");
			}else{
				$.ajax({
					type : "POST",
					url : "createLead.do",
					contentType: "application/json",
					cache : false,
					data: JSON.stringify(leadDetailForm),
					success : function(data) {
						$('#result').html("LeadDetail Saved successfully . ");
					}
				});
			}
		}

		$(document).ready(function () {
		     populateLeadDetailTable();
		});

        function populateLeadDetailTable() {
			var len = document.getElementById('leadDetailTable').rows.length;
			var obj = JSON.parse(document.getElementById('leadConfiguration').value);
			console.log(document.getElementById('leadConfiguration').value);
			for (i = 0; i <= obj.length - 1; i++) {
				fillData(obj[i]);
			}
			if(obj!= ''){
				document.getElementById('leadDetailTable').rows[1].remove();
			}
		}

		function fillData(leadDetail) {
			var row = document.getElementById('leadDetailTable').rows[1].cloneNode(true);
			var len = document.getElementById('leadDetailTable').rows.length;
            var new_row = leadDetailTable.rows[1].cloneNode(true);

            var material = new_row.cells[0].getElementsByTagName('input')[0];
			material.id += len;
			material.value = leadDetail.material;

			var sourceOfSupply = new_row.cells[1].getElementsByTagName('input')[0];
			sourceOfSupply.id += len;
			sourceOfSupply.value = leadDetail.sourceOfSupply;

			var distance = new_row.cells[2].getElementsByTagName('input')[0];
			distance.id += len;
			distance.value = leadDetail.distance;

			var cost = new_row.cells[3].getElementsByTagName('input')[0];
			cost.id += len;
			cost.value = leadDetail.cost;

            var ic = new_row.cells[4].getElementsByTagName('input')[0];
			ic.id += len;
			ic.value = leadDetail.ic;

            var leadCharges = new_row.cells[5].getElementsByTagName('input')[0];
            leadCharges.id += len;
            leadCharges.value = leadDetail.leadCharges;

            var loadingUnloading = new_row.cells[6].getElementsByTagName('input')[0];
			loadingUnloading.id += len;
			loadingUnloading.value = leadDetail.loadingUnloading;

            var total = new_row.cells[7].getElementsByTagName('input')[0];
			total.id += len;
			total.value = leadDetail.total;
			leadDetailTable.appendChild(new_row);

	}
</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
	<form:form id="leadDetailForm" method="POST" commandName="leadDetailForm">

		<h1 style="text-align: center; color: #007399; font-size: 24px;">Configure Lead Details</h1>
		<br>
		<table id="leadDetailTable" border="1" class="gridView">
			<tr>
				<th>Material*</th>
				<th>Source Of Supply*</th>
				<th>Distance*</th>
				<th>Cost</th>
				<th>ic</th>
				<th>Lead Charges</th>
				<th>Loading Unloading Charges</th>
				<th>Total</th>
				<th>Action</th>
			</tr>
			<tr>
				<td><input name="material" id="material" type="text" /></td>
				<td><input name="sourceOfSupply" id="sourceOfSupply" type="text" /></td>
				<td><input name="distance" id="distance" type="text" /></td>
                <td><input name="cost" id="cost" type="text" /></td>
				<td><input name="ic" id="ic" type="text" /></td>
				<td><input name="leadCharges" id="leadCharges" type="text" /></td>
                <td><input name="loadingUnloading" id="loadingUnloading" type="text" /></td>
				<td><input name="total" id="total" type="text" /></td>
				<td><a id="deleteLeadDetail" onclick="deleteLeadDetailRow(this)">
				<img src="<c:url value="/resources/images/delete.png" />" /></a></td>
			</tr>
		</table>
		<br>
		<br>
		<p><font size="3" color="red">* - Required field to be filled.</font></p>
		<br>
		<div id="result" style="text-align: left; font-family: arial; color: #007399; font-size: 16px;"></div>
		<br>
		<input class="button" type="button" id="addLeadDetail" value="Add" onclick="insertLeadDetailRow()" />
		<input class="button" type="button" id="saveDesc" value="Save" onclick="saveLeadDetails()" />
		<form:hidden path="leadConfiguration" id="leadConfiguration"/>
		<form:hidden path="projectId" id="projectId"/>
		<form:hidden path="subProjectId" id="subProjectId"/>
		<form:hidden path="employeeId" id="employeeId"/>
	</form:form>
	</div>
</body>