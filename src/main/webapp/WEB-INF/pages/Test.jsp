<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<%@include file="Script.jsp"%>
<script>

	var testData = "[{\"itemCode\":\"CEMENT\",\"itemUnit\":\"bag\",\"itemQty\":\"3\",\"itemPrice\":\"43\",\"itemCost\":\"129\"},{\"itemCode\":\"RIVER SAND\",\"itemUnit\":\"cft\",\"itemQty\":\"45\",\"itemPrice\":\"42\",\"itemCost\":\"1890\"}]";
	
	$(document).ready(function () {
		var selector = "input[name = 'itemCode']";
		$(document).on('keydown.autocomplete', selector, function() {
			$(this).autocomplete({
				source: function (request, response) {
					$.getJSON("/pms/emp/myview/searchProjectDescription/searchDescItems.do", {
								itemCode: request.term
		            	            }, response);
				}
			});
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
		
		var inp3 = new_row.cells[3].getElementsByTagName('input')[0];
		inp3.id += len;
		inp3.value = '';
		
		var inp4 = new_row.cells[4].getElementsByTagName('input')[0];
		inp4.id += len;
		inp4.value = '';

		x.appendChild(new_row);

	}

	function closeDescWin() {
		$('#projDescLoader', window.parent.document).toggle();
	}
	
	$(document).on("focusout","input[name = 'itemQty']",function(){
		var qty = $(this).val()
		var amnt = $(this).parents('tr:first').find('td:nth-child(4) input').val();
		var cost = qty*amnt;
		$(this).parents('tr:first').find('td:nth-child(5) input').val(cost);
	});
	
	$(document).on("focusout","input[name = 'itemPrice']",function(){
		var qty = $(this).val()
		var amnt = $(this).parents('tr:first').find('td:nth-child(3) input').val();
		var cost = qty*amnt;
		$(this).parents('tr:first').find('td:nth-child(5) input').val(cost);
	});
	
	function ItemDetail(itemCode, itemUnit, itemQty, itemPrice, itemCost) {
		this.itemCode = itemCode;
		this.itemUnit = itemUnit;
		this.itemQty = itemQty;
		this.itemPrice = itemPrice;
		this.itemCost = itemCost;
		} 
	
	function saveItemDesc() {
		var x = document.getElementById('itemTable');
		var itemObjArray = [];
		
		var len = x.rows.length;
		for (i = 1; i <= len - 1; i++) {
			var itemCode = document.getElementById('itemTable').rows[i].cells[0]
			.getElementsByTagName('input')[0].value;
			var itemUnit = document.getElementById('itemTable').rows[i].cells[1]
			.getElementsByTagName('input')[0].value;
			var itemQty = document.getElementById('itemTable').rows[i].cells[2]
			.getElementsByTagName('input')[0].value;
			var itemPrice = document.getElementById('itemTable').rows[i].cells[3]
			.getElementsByTagName('input')[0].value;
			var itemCost = document.getElementById('itemTable').rows[i].cells[4]
			.getElementsByTagName('input')[0].value;
			
			var obj = new ItemDetail(itemCode, itemUnit, itemQty, itemPrice, itemCost);
			itemObjArray.push(obj); 
		}
		console.log("data = " + JSON.stringify(itemObjArray));
	}
	
	function fillItemDesc() {
		var obj = JSON.parse(testData);
		for (i = 0; i <= obj.length - 1; i++) {
			fillItemRow(obj[i]);
		}
	}
	
	function fillItemRow(item) {
		var x = document.getElementById('itemTable');
		var new_row = x.rows[1].cloneNode(true);
		var len = x.rows.length;

		var inp0 = new_row.cells[0].getElementsByTagName('input')[0];
		inp0.id += len;
		inp0.value = item.itemCode;

		var inp1 = new_row.cells[1].getElementsByTagName('input')[0];
		inp1.id += len;
		inp1.value = item.itemUnit;

		var inp2 = new_row.cells[2].getElementsByTagName('input')[0];
		inp2.id += len;
		inp2.value = item.itemQty;
		
		var inp3 = new_row.cells[3].getElementsByTagName('input')[0];
		inp3.id += len;
		inp3.value = item.itemPrice;
		
		var inp4 = new_row.cells[4].getElementsByTagName('input')[0];
		inp4.id += len;
		inp4.value = item.itemCost;

		x.appendChild(new_row);

	}
</script>
</head>
<body>
	<h1 style="text-align: center; color: #007399; font-size: 24px;">Item
		Breakdown Structure</h1>
	<form:form id="descItemForm" method="POST" commandName="descItemForm">
		<table id="itemTable" border="1" class="gridView">
			<tr>
				<th>Material</th>
				<th>Unit</th>
				<th>Qty</th>
				<th>Unit Price</th>
				<th>Cost</th>
				<th>Action</th>
			</tr>

			<tr>
				<td><input name="itemCode" id="itemCode" type="text" /></td>
				<td><input name="itemUnit" id="itemUnit" type="text" /></td>
				<td><input name="itemQty" id="itemQty" type="text" /></td>
				<td><input name="itemPrice" id="itemPrice" type="text" /></td>
				<td><input name="itemCost" id="itemCost" type="text" /></td>
				<td><a id="deleteItem" onclick="deleteItemRow(this)">delete</a></td>
			</tr>

		</table>
		<br>
			
		<br>
		<input type="button" id="addItem" value="Add"
			onclick="insertItemRow()" />
		<input type="button" id="saveDesc" value="Save"
			onclick="saveItemDesc()" />
		<input type="button" id="closeDesc" value="Close"
			onclick="closeDescWin()" />
		<input type="button" id="closeDesc" value="Fill"
			onclick="fillItemDesc()" />
	</form:form>
</body>
