<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<%@include file="Script.jsp"%>
<script>

	$(document).ready(function () {
		$("#itemCode").autocomplete({
			source: function (request, response) {
				$.getJSON("/pms/emp/myview/searchProjectDescription/searchDescItems.do", {
							itemCode: request.term
	            	            }, response);
			}
		});
	});
	
	function deleteItemRow(row) {
		var noOfRow = document.getElementById('itemTable').rows.length;
		if (noOfRow > 2) {
			var i = row.parentNode.parentNode.rowIndex;
			document.getElementById('itemTable').deleteRow(i);
		}
	}

	function insertItemRow() {
		console.log('hi');
		var x = document.getElementById('itemTable');
		var new_row = x.rows[1].cloneNode(true);
		var len = x.rows.length;

		var inp0 = new_row.cells[0].getElementsByTagName('input')[0];
		inp0.id += len;
		inp0.value = '';

		var inp1 = new_row.cells[1].getElementsByTagName('input')[0];
		inp1.id += len;
		inp1.value = '';

		var inp2 = new_row.cells[2].getElementsByTagName('input')[0];
		inp2.id += len;
		inp2.value = '';

		x.appendChild(new_row);

	}

	function closeProjDescLoader() {
		$('#projDescLoader', window.parent.document).toggle();
	}

	function saveTable() {
		console.log('hi');
		var x = document.getElementById('itemTable');
		var len = x.rows.length;
		for (i = 1; i <= len - 1; i++) {
			alert(document.getElementById('itemTable').rows[i].cells[1]
					.getElementsByTagName('input')[0].value);
		}

	}
</script>
</head>
<body>
	<h1 style="text-align: center; color: #007399; font-size: 24px;">Item
		Breakdown Structure</h1>
	<form:form id="descItemForm" method="POST" commandName="descItemForm">
		<table id="itemTable" border="1" class="gridView">
			<tr>
				<th>Item</th>
				<th>Qty</th>
				<th>Amount</th>
				<th>Action</th>
			</tr>

			<tr>
				<td><form:input path="itemCode" id="itemCode"
						cssClass="inputText" /></td>
				<td><input size=25 type="text" id="lngbox" /></td>
				<td><input size=25 type="text" id="latbox1" /></td>
				<td><a id="deleteItem" onclick="deleteItemRow(this)">delete</a></td>
			</tr>

		</table>
		<br>
		<input type="button" id="addItem" value="Add"
			onclick="insertItemRow()" />
		<input type="button" id="savePOITable" value="Save"
			onclick="saveTable()" />

		<input type="button" id="savePOITable" value="Close"
			onclick="closeProjDescLoader()" />
	</form:form>
</body>
