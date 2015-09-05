<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<%@include file="Script.jsp"%>

<script src="<c:url value="/resources/js/jquery.tabSlideOut.v1.3.js" />"></script>

<script>
	
	window.onunload = function() {
	    if (window.opener && !window.opener.closed) {
	        window.opener.popUpClosed();
	    }
	};
	$(document).ready(function () {
		var selector = "input[name = 'itemName']";
		
		$(document).on('keydown.autocomplete', selector, function() {
			$(this).autocomplete({
				source: function (request, response) {
					$.getJSON("/pms/emp/myview/searchProjectDescription/searchDescItems.do", {
								itemName: request.term,
								itemType: $('#itemType').val(),
								projectId: $('#projId').val(),
								subProjectId: $('#subProjId').val(),
								descType: $('#descType').val()
		            	        }, response);
				},
				select: function(event, ui) { 
					if(validateItemNameExistence(ui.item.itemName)){
				        alert("Item already exists!");
				        event.preventDefault();
				        $(this).val('');
					}else{
						 $(this).parents('tr:first').find('td:nth-child(2) input').val(ui.item.itemUnit);
				         $(this).parents('tr:first').find('td:nth-child(3) input').val(ui.item.itemPrice);
					}
			         
			    }
			});
		});
		
		fillItemDesc();
		
		 
      $('#slideOpen').click(function(){
    	  	$(this).toggle();
    	  	$('#slideClose').toggle();
		});
      
      $('#slideClose').click(function(){
    	  	$(this).toggle();
    	  	$('#slideOpen').toggle();
		});
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
		calculateTotalItemCost();
	}

	function insertItemRow() {
		var itemTable = document.getElementById('itemTable');
		var new_row = itemTable.rows[1].cloneNode(true);
		var len = itemTable.rows.length;

		var itemName = new_row.cells[0].getElementsByTagName('input')[0];
		itemName.id += len;
		itemName.value = '';

		var itemUnit = new_row.cells[1].getElementsByTagName('input')[0];
		itemUnit.id += len;
		itemUnit.value = '';
		
		var itemPrice = new_row.cells[2].getElementsByTagName('input')[0];
		itemPrice.id += len;
		itemPrice.value = '';
		
		var itemQty = new_row.cells[3].getElementsByTagName('input')[0];
		itemQty.id += len;
		itemQty.value = '';
		
		var itemCost = new_row.cells[4].getElementsByTagName('input')[0];
		itemCost.id += len;
		itemCost.value = '';

		itemTable.appendChild(new_row);

	}
	
	$(document).on("keyup","input[name = 'itemPrice']",function(){
		 var valid = /^\d+(\.\d{0,2})?$/.test(this.value),
	        val = this.value;
	    
	    if(!valid){
	        console.log("Invalid input!");
	        this.value = val.substring(0, val.length - 1);
	    }
	});
	
	$(document).on("keyup","input[name = 'itemQty']",function(){
		
		var valid = /^[\d]+(\.[\d]{0,2})?$/.test(this.value);
		val = this.value;
			    
	    if(valid){
	        var qty = $(this).val()
			var price = $(this).parents('tr:first').find('td:nth-child(3) input').val();
			var cost = qty*price;
			$(this).parents('tr:first').find('td:nth-child(5) input').val(cost.toFixed(2));
	    }
	    else{
	    	 console.log("Invalid input!");
		     this.value = val.substring(0, val.length - 1);
	    }
	    calculateTotalItemCost();
		
	});
	

	$(document).on("keyup","input[name = 'itemPrice']",function(){
		var price = $(this).val()
		var qty = $(this).parents('tr:first').find('td:nth-child(4) input').val();
		var cost = qty*price;
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
				totalItemCost = parseFloat(totalItemCost) + parseFloat(itemCost);
			}
			
		}
		document.getElementById('totalItemCost').value = totalItemCost.toFixed(2);;
	}
	
	$(document).on("focusout","input",function(){
		calculateTotalItemCost();
	});
	
	$(document).on("keyup","input",function(){
		calculateTotalItemCost();
	});
	
	function ItemDetail(itemName, itemUnit, itemPrice, itemQty, itemCost) {
		this.itemName = itemName;
		this.itemUnit = itemUnit;
		this.itemPrice = itemPrice;
		this.itemQty = itemQty;
		this.itemCost = itemCost;
		} 
	function validateItemNameExistence(newItemName){
		var itemTable = document.getElementById('itemTable');
		var len = itemTable.rows.length;
		var exists = false;
		for (i = 1; i <= len - 1; i++) {
			var itemName = itemTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
			if(itemName == newItemName){
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	function saveItemDesc() {
		var itemTable = document.getElementById('itemTable');
		var itemObjArray = [];
		var itemDescForm = {};
		var err = null;
		var len = itemTable.rows.length;
		for (i = 1; i <= len - 1; i++) {
			var itemName = itemTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
			var itemUnit = itemTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
			var itemPrice = itemTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
			var itemQty = itemTable.rows[i].cells[3].getElementsByTagName('input')[0].value;
			var itemCost = itemTable.rows[i].cells[4].getElementsByTagName('input')[0].value;
			
			var obj = new ItemDetail(itemName, itemUnit, itemPrice, itemQty, itemCost);
			if(itemName && itemUnit && itemPrice && itemQty && itemCost){
				itemObjArray.push(obj); 
			}else{
				err = true;
			}
		}
		itemDescForm["employeeId"] = document.getElementById('employeeId').value;
		itemDescForm["projId"] = document.getElementById('projId').value;
		itemDescForm["subProjId"] = document.getElementById('subProjId').value;
		itemDescForm["projDescId"] = document.getElementById('projDescId').value;
		itemDescForm["projDescSerial"] = document.getElementById('projDescSerial').value;
		itemDescForm["descType"] = document.getElementById('descType').value;
		itemDescForm["descItemDetail"] = JSON.stringify(itemObjArray);
		
		console.log("data = " + JSON.stringify(itemDescForm));
		if(err){
			alert("Please make sure that all the required fields are entered");
		}else{
			$.ajax({
				type : "POST",
				url : "saveProjDescItems.do",
				contentType: "application/json",
				cache : false,
				data: JSON.stringify(itemDescForm),
				success : function(response) {
					if(response == true){
						$("#dialog-confirm").html("<center><br>Saved successfully!</center>");
					}else{
						$("#dialog-confirm").html("<center><br>Error occured!</center>");
					}
					$("#dialog-confirm").dialog({
					    resizable: false,
					    modal: true,
					    title: "Item Breakdown Structure!",
					    height: 200,
					    width: 400,
					    buttons: {
					        "Ok": function () {
					            $(this).dialog('close');
					            window.location.reload();
					        }
					    }
					});
				}
			});
		}
	}
	
	function fillItemDesc() {
		var len = document.getElementById('itemTable').rows.length;
		var obj = JSON.parse(document.getElementById('descItemDetail').value);
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
		
		var itemName = row.cells[0].getElementsByTagName('input')[0];
		itemName.id += len;
		itemName.value = item.itemName;

		var itemUnit = row.cells[1].getElementsByTagName('input')[0];
		itemUnit.id += len;
		itemUnit.value = item.itemUnit;
		
		var itemPrice = row.cells[2].getElementsByTagName('input')[0];
		itemPrice.id += len;
		itemPrice.value = item.itemPrice;
		
		var itemQty = row.cells[3].getElementsByTagName('input')[0];
		itemQty.id += len;
		itemQty.value = item.itemQty;
		
		var itemCost = row.cells[4].getElementsByTagName('input')[0];
		itemCost.id += len;
		itemCost.value = parseFloat(item.itemPrice*item.itemQty).toFixed(2);

		document.getElementById('itemTable').appendChild(row);

	}
</script>
<script type="text/javascript">
    $(function(){
        $('.slide-out-div').tabSlideOut({
            tabHandle: '.handle',                     //class of the element that will become your tab
            imageHeight: '122px',                     //height of tab image           //Optionally can be set using css
            imageWidth: '40px',                       //width of tab image            //Optionally can be set using css
            tabLocation: 'left',                      //side of screen where tab lives, top, right, bottom, or left
            speed: 300,                               //speed of animation
            action: 'click',                          //options: 'click' or 'hover', action to trigger animation
            topPos: '0px',                          //position from the top/ use if tabLocation is left or right
            leftPos: '25px',                          //position from left/ use if tabLocation is bottom or top
            fixedPosition: true                      //options: true makes it stick(fixed position) on scroll
        });

    });

    </script>
<style type="text/css">
.handle {
	width: 35px;
	height: 100%;
}

.slide-out-div {
	padding: 20px;
	width: 1024px;
	background: #ccc;
	border: 1px solid #29216d;
}
</style>

</head>
<body >
	<div class="slide-out-div" style="background: #ffffff;">
		<a class="handle" href="#"> <img id="slideClose"
			style="display: none;"
			src="<c:url value="/resources/images/slide-close.png" />" /> <img
			id="slideOpen" style="display: block;"
			src="<c:url value="/resources/images/slide-open.png" />" />
		</a>


		<form:form id="projDescForm" method="POST" commandName="projDescForm">
			<fieldset style="margin: 1em; text-align: left;">
				<legend>
					Project Description Details
				</legend>
				<table>
					<tr>
						<td>Alias Project Name <span id="colon">:</span>
						</td>
						<td><form:input id="aliasProjectName" path="aliasProjectName"
								cssClass="inputText" readonly="true" /></td>
					</tr>

					<c:if test="${projDescForm.subProjId gt '0'}">
						<tr>
							<td>Alias Project Name <span id="colon">:</span>
							</td>
							<td><form:input id="aliasSubProjectName"
									path="aliasSubProjectName" cssClass="inputText" readonly="true" /></td>
						</tr>

					</c:if>

					<tr>
						<td>Serial Number<span id="colon">:</span>
						</td>
						<td><form:input id="serialNumber" path="serialNumber"
								cssClass="inputText" readonly="true" /></td>
					</tr>
					<tr>
						<td>Quantity<span id="colon">:</span>
						</td>
						<td><form:input id="quantity" path="quantity"
								cssClass="inputText" readonly="true" /></td>
					</tr>
					<tr>
						<td>Description<span id="colon">:</span>
						</td>
						<td><form:textarea path="description"
								placeholder="Enter Description" cssClass="inputText"
								readonly="true" /></td>
					</tr>
					<tr>
						<td>Metric<span id="colon">:</span>
						</td>
						<td><form:input id="metric" path="metric"
								placeholder="Enter quantity metric" cssClass="inputText" readonly="true"/></td>
						<td><form:errors path="metric" cssClass="error" /></td>
					</tr>
					<tr>
						<td>Alias Description<span id="colon">:</span>
						</td>
						<td><form:input path="aliasDescription"
								placeholder="Enter Alias Description" cssClass="inputText"
								readonly="true" /></td>
					</tr>
					<tr>
						<td>Price Per Quantity<span id="colon">:</span>
						</td>
						<td><form:input path="pricePerQuantity" cssClass="inputText"
								readonly="true" /></td>
					</tr>
					<tr>
						<td>Total Cost in Rupees<span id="colon">:</span>
						</td>
						<td><form:input path="totalCost" cssClass="inputText"
								readonly="true" /></td>
					</tr>
					<tr></tr>
				</table>
			</fieldset>
		</form:form>
	</div>
	<h1 style="text-align: center; color: #007399; font-size: 24px;">Item
		Breakdown Structure</h1>



	<form:form id="descItemForm" method="POST" commandName="descItemForm"
		action="createProjDesc.do">

		<table>
			<tr>
				<td>Type<span id="colon">:</span></td>
				<td><form:select path="itemType" cssClass="inputText"
						id="itemType" items="${itemTypes}" /></td>
			</tr>
		</table>
		<br>
		<form:hidden path="descItemDetail" id="descItemDetail" />
		<form:hidden path="projId" id="projId" />
		<form:hidden path="subProjId" id="subProjId" />
		<form:hidden path="projDescId" id="projDescId" />
		<form:hidden path="projDescSerial" id="projDescSerial" />
		<form:hidden path="employeeId" id="employeeId" />
		<form:hidden path="descType" id="descType" />

		<table id="itemTable" border="1" class="gridView">
			<tr>
				<th>Item</th>
				<th>Unit</th>
				<th>Unit Price</th>
				<th>Adequacy</th>
				<th>Cost</th>
				<th>Action</th>
			</tr>

			<tr>
				<td><input name="itemName" id="itemName" type="text" /></td>
				<td><input name="itemUnit" readonly="readonly" id="itemUnit" type="text" /></td>
				<td><input name="itemPrice" readonly="readonly" id="itemPrice"
					type="text" /></td>
				<td><input name="itemQty" id="itemQty" type="text"></td>
				<td><input name="itemCost" readonly="readonly" id="itemCost"
					type="text" /></td>
				<td><a id="deleteItem" onclick="deleteItemRow(this)"><img
						src="<c:url value="/resources/images/delete.png" />" /></a></td>
			</tr>

		</table>
		<br>
		Amount : <input name="totalItemCost" readonly="readonly"
			id="totalItemCost" type="text" />
		<br>

		<br>
		<input class="button" type="button" id="addItem" value="Add"
			onclick="insertItemRow()" />
		<input class="button" type="button" id="saveDesc" value="Save"
			onclick="saveItemDesc()" />
	</form:form>
	<div id="dialog-confirm"></div>
</body>
