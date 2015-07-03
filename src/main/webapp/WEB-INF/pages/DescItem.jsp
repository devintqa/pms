<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<%@include file="Script.jsp"%>
<script>

	
	$(document).ready(function () {
		var selector = "input[name = 'itemName']";
		$(document).on('keydown.autocomplete', selector, function() {
			$(this).autocomplete({
				source: function (request, response) {
					$.getJSON("/pms/emp/myview/searchProjectDescription/searchDescItems.do", {
								itemName: request.term
		            	            }, response);
				}
			});
		});
		
		fillItemDesc();
	});
	
	function deleteItemRow(row) {
		var itemTable = document.getElementById('itemTable');
		var noOfRow = document.getElementById('itemTable').rows.length;
		if (noOfRow > 2) {
			var i = row.parentNode.parentNode.rowIndex;
			document.getElementById('itemTable').deleteRow(i);
		} else{
			document.getElementById('itemTable').rows[1].cells[0].getElementsByTagName('input')[0].value = '';
			document.getElementById('itemTable').rows[1].cells[1].getElementsByTagName('input')[0].value = '';
			document.getElementById('itemTable').rows[1].cells[2].getElementsByTagName('input')[0].value = '';
			document.getElementById('itemTable').rows[1].cells[3].getElementsByTagName('input')[0].value = '';
			document.getElementById('itemTable').rows[1].cells[4].getElementsByTagName('input')[0].value = '';
		}
	}

	function insertItemRow() {
		var itemTable = document.getElementById('itemTable');
		var new_row = itemTable.rows[1].cloneNode(true);
		var len = itemTable.rows.length;

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

		itemTable.appendChild(new_row);

	}

	$(document).on("focusout","input[name = 'itemQty']",function(){
		var qty = $(this).val()
		var amnt = $(this).parents('tr:first').find('td:nth-child(4) input').val();
		var cost = qty*amnt;
		$(this).parents('tr:first').find('td:nth-child(5) input').val(cost);
	});
	
	function calculateTotalItemCost(){
		var itemTable = document.getElementById('itemTable');
		var itemObjArray = [];
		var len = itemTable.rows.length;
		var totalItemCost = 0;
		for (i = 1; i <= len - 1; i++) {
			var itemCost = itemTable.rows[i].cells[4].getElementsByTagName('input')[0].value;
			if(itemCost){
				totalItemCost = parseInt(totalItemCost) + parseInt(itemCost);
			}
			
		}
		document.getElementById('totalItemCost').value = parseInt(totalItemCost);
	}
	
	$(document).on("focusout","input",function(){
		calculateTotalItemCost();
	});
	
	
	$(document).on("focusout","input[name = 'itemPrice']",function(){
		var qty = $(this).val()
		var amnt = $(this).parents('tr:first').find('td:nth-child(3) input').val();
		var cost = qty*amnt;
		$(this).parents('tr:first').find('td:nth-child(5) input').val(cost);
	});
	
	function ItemDetail(itemName, itemUnit, itemQty, itemPrice, itemCost) {
		this.itemName = itemName;
		this.itemUnit = itemUnit;
		this.itemQty = itemQty;
		this.itemPrice = itemPrice;
		this.itemCost = itemCost;
		} 
	
	function saveItemDesc() {
		var itemTable = document.getElementById('itemTable');
		var itemObjArray = [];
		var itemDescForm = {};
		var len = itemTable.rows.length;
		for (i = 1; i <= len - 1; i++) {
			var itemName = itemTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
			var itemUnit = itemTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
			var itemQty = itemTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
			var itemPrice = itemTable.rows[i].cells[3].getElementsByTagName('input')[0].value;
			var itemCost = itemTable.rows[i].cells[4].getElementsByTagName('input')[0].value;
			
			var obj = new ItemDetail(itemName, itemUnit, itemQty, itemPrice, itemCost);
			if(itemName){
				itemObjArray.push(obj); 
			}
		}
		itemDescForm["employeeId"] = document.getElementById('employeeId').value;
		itemDescForm["projId"] = document.getElementById('projId').value;
		itemDescForm["subProjId"] = document.getElementById('subProjId').value;
		itemDescForm["projDescId"] = document.getElementById('projDescId').value;
		itemDescForm["projDescSerial"] = document.getElementById('projDescSerial').value;
		itemDescForm["projDescItemDetail"] = JSON.stringify(itemObjArray);
		
		
		console.log("data = " + JSON.stringify(itemDescForm));
		
		$.ajax({
			type : "POST",
			url : "saveProjDescItems.do",
			contentType: "application/json",
			cache : false,
			data: JSON.stringify(itemDescForm),
			success : function(response) {
				if(response == true){
					alert("Data saved successfully");
				}
			}
		});
	}
	
	function fillItemDesc() {
		var len = document.getElementById('itemTable').rows.length;
		var obj = JSON.parse(document.getElementById('projDescItemDetail').value);
		for (i = 0; i <= obj.length - 1; i++) {
			fillItemRow(obj[i]);
		}
		if(obj!= ''){
			document.getElementById('itemTable').rows[1].remove();
		}
		calculateTotalItemCost();
	}
	
	function fillItemRow(item) {
		var row = document.getElementById('itemTable').rows[1].cloneNode(true);
		var len = document.getElementById('itemTable').rows.length;

		var inp0 = row.cells[0].getElementsByTagName('input')[0];
		inp0.id += len;
		inp0.value = item.itemName;

		var inp1 = row.cells[1].getElementsByTagName('input')[0];
		inp1.id += len;
		inp1.value = item.itemUnit;

		var inp2 = row.cells[2].getElementsByTagName('input')[0];
		inp2.id += len;
		inp2.value = item.itemQty;
		
		var inp3 = row.cells[3].getElementsByTagName('input')[0];
		inp3.id += len;
		inp3.value = item.itemPrice;
		
		var inp4 = row.cells[4].getElementsByTagName('input')[0];
		inp4.id += len;
		inp4.value = item.itemCost;

		document.getElementById('itemTable').appendChild(row);

	}
</script>
</head>
<body>
	<h1 style="text-align: center; color: #007399; font-size: 24px;">Item Breakdown Structure</h1>
	<form:form id="descItemForm" method="POST"  commandName="descItemForm" action="createProjDesc.do">
	
	<form:hidden path="projDescItemDetail" id="projDescItemDetail"/>
	<form:hidden path="projId" id="projId"/>
	<form:hidden path="subProjId" id="subProjId"/>
	<form:hidden path="projDescId" id="projDescId"/>
	<form:hidden path="projDescSerial" id="projDescSerial"/>
	<form:hidden path="employeeId" id="employeeId"/>
	
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
				<td><input name="itemName" id="itemName" type="text" /></td>
				<td><input name="itemUnit" id="itemUnit" type="text" /></td>
				<td><input name="itemQty" id="itemQty" type="text" /></td>
				<td><input name="itemPrice" id="itemPrice" type="text" /></td>
				<td><input name="itemCost" id="itemCost" type="text" /></td>
				<td><a id="deleteItem" onclick="deleteItemRow(this)"><img src="<c:url value="/resources/images/delete.png" />"/></a></td>
			</tr>

		</table>
		<br>
		Amount : <input name="totalItemCost" id="totalItemCost" type="text" />
		<br>
			
		<br>
		<input type="button" id="addItem" value="Add" onclick="insertItemRow()" />
		<input type="button" id="saveDesc" value="Save" onclick="saveItemDesc()" />
	</form:form>
</body>
