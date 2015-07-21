<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<title>PMS :: Configure Item</title>
<%@include file="Script.jsp" %>

<script>
	
	$(document).ready(function () {
		var selector = "input[name = 'itemName']";
		fillItemDesc();		
		$(document).on('keydown.autocomplete', selector, function() {
				$(this).autocomplete({
					source: function (request, response) {
						$.getJSON("/pms/emp/myview/configureItems/searchItems.do", {
									itemName: request.term,
									itemType: $('#itemType').val()
			            	            }, response);
					},
					select: function(event, ui) { 
				         $(this).parents('tr:first').find('td:nth-child(2) input').val(ui.item.itemUnit);
				    }
				});
			});
			
		});
		
		
		$(document).on("keyup","input[name = 'itemPrice']",function(){

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
			
			itemTable.appendChild(new_row);
	
		}
	
		
		function ItemDetail(itemName, itemUnit, itemPrice) {
			this.itemName = itemName;
			this.itemUnit = itemUnit;
			this.itemPrice = itemPrice;
			} 
		
		function saveItemDesc() {
			var itemTable = document.getElementById('itemTable');
			var itemObjArray = [];
			var itemDescForm = {};
			var len = itemTable.rows.length;
			for (i = 1; i <= len - 1; i++) {
				var itemName = itemTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
				var itemUnit = itemTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
				var itemPrice = itemTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
				
				var obj = new ItemDetail(itemName, itemUnit, itemPrice );
				if(itemName){
					itemObjArray.push(obj); 
				}
			}
			itemDescForm["employeeId"] = document.getElementById('employeeId').value;
			itemDescForm["projId"] = document.getElementById('projId').value;
			itemDescForm["subProjId"] = document.getElementById('subProjId').value;
			itemDescForm["items"] = JSON.stringify(itemObjArray);
			
			console.log("data = " + JSON.stringify(itemDescForm));
			
			$.ajax({
				type : "POST",
				url : "saveItemPrice.do",
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
			var obj = JSON.parse(document.getElementById('itemPriceConfiguration').value);
			for (i = 0; i <= obj.length - 1; i++) {
				fillItemRow(obj[i]);
			}
			if(obj!= ''){
				document.getElementById('itemTable').rows[1].remove();
			}
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
			inp2.value = item.itemPrice;
	
			document.getElementById('itemTable').appendChild(row);

	}
</script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>

		
	<form:form id="projectItemForm" method="POST" commandName="projectItemForm">
		
		<h1 style="text-align: center; color: #007399; font-size: 24px;">Configure Item Breakdown Structure</h1>
		
		<table>
			<tr>
				<td>Type<span id="colon">:</span>
				</td>
				<td><form:select path="itemType" cssClass="inputText" id="itemType" >
						<option value="-- PLEASE SELECT --" selected="selected">-- PLEASE SELECT --</option>
						<option value="MATERIAL" selected="selected">MATERIAL</option>
						<option value="LABOUR">LABOUR</option>
						<option value="OTHERS">OTHERS</option>
					</form:select></td>
			</tr>
		</table>
		<br>
		<table id="itemTable" border="1" class="gridView">
			<tr>
				<th>Item</th>
				<th>Unit</th>
				<th>Unit Price</th>
				<th>Action</th>
			</tr>

			<tr>
				<td><input name="itemName" id="itemName" type="text" /></td>
				<td><input name="itemUnit" id="itemUnit" type="text" /></td>
				<td><input name="itemPrice" id="itemPrice" type="text" /></td>
				<td><a id="deleteItem" onclick="deleteItemRow(this)">
				<img src="<c:url value="/resources/images/delete.png" />" /></a></td>
			</tr>

		</table>
		<br>
		<br>

		<br>
		<input type="button" id="addItem" value="Add" onclick="insertItemRow()" />
		<input type="button" id="saveDesc" value="Save" onclick="saveItemDesc()" />
		<form:hidden path="itemPriceConfiguration" id="itemPriceConfiguration"/>
		<form:hidden path="projId" id="projId"/>
		<form:hidden path="subProjId" id="subProjId"/>
		<form:hidden path="employeeId" id="employeeId"/>
	
	</form:form>
</body>