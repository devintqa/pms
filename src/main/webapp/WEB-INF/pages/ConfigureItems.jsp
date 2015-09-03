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
		fillItemPrice();		
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
				         $(this).parents('tr:first').find('td:nth-child(1) input:nth-child(2)').val($('#itemType').val());
				    }
				});
			});
			
		});
		
		$(document).on("keyup","input[name = 'itemPrice']",function(){
			 var valid = /^\d+(\.\d{0,2})?$/.test(this.value),
		        val = this.value;
		    
		    if(!valid){
		        console.log("Invalid input!");
		        this.value = val.substring(0, val.length - 1);
		    }
		});
		
		
		function deleteItemRow(row) {
			var itemTable = document.getElementById('itemTable');
			var noOfRow = document.getElementById('itemTable').rows.length;
			if (noOfRow > 2) {
				var i = row.parentNode.parentNode.rowIndex;
				document.getElementById('itemTable').deleteRow(i);
			} else{
				document.getElementById('itemTable').rows[1].cells[0].getElementsByTagName('input')[0].value = '';
				document.getElementById('itemTable').rows[1].cells[0].getElementsByTagName('input')[1].value = '';
				document.getElementById('itemTable').rows[1].cells[1].getElementsByTagName('input')[0].value = '';
				document.getElementById('itemTable').rows[1].cells[2].getElementsByTagName('input')[0].value = '';
			}
		}
	
		function insertItemRow() {
			var itemTable = document.getElementById('itemTable');
			var new_row = itemTable.rows[1].cloneNode(true);
			var len = itemTable.rows.length;
	
			var itemName = new_row.cells[0].getElementsByTagName('input')[0];
			itemName.id += len;
			itemName.value = '';
			
			var itemType = new_row.cells[0].getElementsByTagName('input')[1];
			itemType.id += len;
			itemType.value = '';
			
			var itemUnit = new_row.cells[1].getElementsByTagName('input')[0];
			itemUnit.id += len;
			itemUnit.value = '';
			
	
			var itemPrice = new_row.cells[2].getElementsByTagName('input')[0];
			itemPrice.id += len;
			itemPrice.value = '';
			
			itemTable.appendChild(new_row);
	
		}
	
		
		function ItemDetail(itemName, itemType, itemUnit, itemPrice) {
			this.itemName = itemName;
			this.itemType = itemType;
			this.itemUnit = itemUnit;
			this.itemPrice = itemPrice;
			} 
		
		function saveItemPrice() {
			var itemTable = document.getElementById('itemTable');
			var itemObjArray = [];
			var itemDescForm = {};
			var len = itemTable.rows.length;
			var err = null;
			for (i = 1; i <= len - 1; i++) {
				var itemName = itemTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
				var itemType = itemTable.rows[i].cells[0].getElementsByTagName('input')[1].value;
				var itemUnit = itemTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
				var itemPrice = itemTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
				
				var obj = new ItemDetail(itemName, itemType, itemUnit, itemPrice );
				if(itemName && itemType && itemUnit && itemPrice){
					itemObjArray.push(obj); 
				}else{
					err = true;
				}
			}
			itemDescForm["employeeId"] = document.getElementById('employeeId').value;
			itemDescForm["projId"] = document.getElementById('projId').value;
			itemDescForm["subProjId"] = document.getElementById('subProjId').value;
			itemDescForm["itemPriceConfiguration"] = JSON.stringify(itemObjArray);
			
			console.log("data = " + JSON.stringify(itemDescForm));
			if(err){
				alert("Please make sure that all the required fields are entered.");
			}else{
				$.ajax({
					type : "POST",
					url : "saveItemPrice.do",
					contentType: "application/json",
					cache : false,
					data: JSON.stringify(itemDescForm),
					success : function(data) {
						$('#result').html(data);
					}
				});
			}
		}
		
		function fillItemPrice() {
			var len = document.getElementById('itemTable').rows.length;
			var obj = JSON.parse(document.getElementById('itemPriceConfiguration').value);
			console.log(document.getElementById('itemPriceConfiguration').value);
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
	
			var itemName = row.cells[0].getElementsByTagName('input')[0];
			itemName.id += len;
			itemName.value = item.itemName;;
			
			var itemType = row.cells[0].getElementsByTagName('input')[1];
			itemType.id += len;
			itemType.value = item.itemType;
			
			var itemUnit = row.cells[1].getElementsByTagName('input')[0];
			itemUnit.id += len;
			itemUnit.value = item.itemUnit;
			
			var itemPrice = row.cells[2].getElementsByTagName('input')[0];
			itemPrice.id += len;
			itemPrice.value = item.itemPrice;
	
			document.getElementById('itemTable').appendChild(row);

	}
		
		function syncItems(){
			var employeeId = document.getElementById('employeeId').value;
			var projectId = document.getElementById('projId').value;
			var itemTable = document.getElementById('itemTable');
			var itemPriceConfiguration = document.getElementById('itemPriceConfiguration');
			
			var itemTable = document.getElementById('itemTable');
			var itemObjArray = [];
			var itemDescForm = {};
			var len = itemTable.rows.length;
			var err = null;
			for (i = 1; i <= len - 1; i++) {
				var itemName = itemTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
				var itemType = itemTable.rows[i].cells[0].getElementsByTagName('input')[1].value;
				var itemUnit = itemTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
				var itemPrice = itemTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
				
				var obj = new ItemDetail(itemName, itemType, itemUnit, itemPrice );
				if(itemName && itemType && itemUnit && itemPrice){
					itemObjArray.push(obj); 
				}else{
					err = true;
				}
			}
			itemDescForm["employeeId"] = document.getElementById('employeeId').value;
			itemDescForm["projId"] = document.getElementById('projId').value;
			itemDescForm["subProjId"] = document.getElementById('subProjId').value;
			itemDescForm["itemPriceConfiguration"] = JSON.stringify(itemObjArray);
			
			$.ajax({
				type : 'POST',
				url : 'syncItems.do',
				contentType: "application/json",
				data : JSON.stringify(itemDescForm),
				success : function(response) {
					console.log("Successfully deleted row ");
				}
			});
			fillItemPrice();
		}
</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">	
	<form:form id="projectItemForm" method="POST" commandName="projectItemForm">
		
		<h1 style="text-align: center; color: #007399; font-size: 24px;">Configure Item Breakdown Structure</h1>
		
		<table>
        <tr>
		    <td>Type<span id="colon">:</span></td>
			<td><form:select path="itemType" cssClass="inputText" id="itemType" items="${itemTypes}"/></td>
		</tr>
		</table>
		<br>
		<table id="itemTable" border="1" class="gridView">
			<tr>
				<th>Item*</th>
				<th>Unit*</th>
				<th>Unit Price*</th>
				<th>Action</th>
			</tr>

			<tr>
				<td><input name="itemName" id="itemName" type="text" />
				<input name="itemType" id="itemType" type="hidden" /></td>
				<td><input name="itemUnit" id="itemUnit" type="text" /></td>
				<td><input name="itemPrice" id="itemPrice" type="text" /></td>
				<td><a id="deleteItem" onclick="deleteItemRow(this)"> 
				<img src="<c:url value="/resources/images/delete.png" />" /></a></td>
			</tr>

		</table>
		<br>
		<br>
		<p><font size="3" color="red">* - Required field to be filled.</font></p>
		<br>
		<div id="result" style="text-align: left; font-family: arial; color: #007399; font-size: 16px;"></div>
		<br>
		<input class="button" type="button" id="addItem" value="Add" onclick="insertItemRow()" />
		<input class="button" type="button" id="saveDesc" value="Save" onclick="saveItemPrice()" />
		<input class="button" type="button" id="pullMissingItem" value="Sync Items" onclick="syncItems()" />
		<form:hidden path="itemPriceConfiguration" id="itemPriceConfiguration"/>
		<form:hidden path="projId" id="projId"/>
		<form:hidden path="subProjId" id="subProjId"/>
		<form:hidden path="employeeId" id="employeeId"/>
	
	</form:form>
	</div>
</body>