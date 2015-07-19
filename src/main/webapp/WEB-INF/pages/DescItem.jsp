<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<%@include file="Script.jsp"%>

<script src="<c:url value="/resources/js/jquery.tabSlideOut.v1.3.js" />"></script>

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

	$(document).on("keyup","input[name = 'itemQty']",function(){
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
	
	$(document).on("keyup","input",function(){
		calculateTotalItemCost();
	});
	
	$(document).on("keyup","input[name = 'itemPrice']",function(){
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
            fixedPosition: false                      //options: true makes it stick(fixed position) on scroll
        });

    });

    </script>
    <style type="text/css">
     
	     .slide-out-div {
	         padding: 20px;
	         width: 1024px;
	         background: #ccc;
	         border: 1px solid #29216d;
	     }      
     
     </style>
     
</head>
<body>
 <div class="slide-out-div">
            <a class="handle" href="#">
            
            <img id="slideClose"  style="display:none;" src="<c:url value="/resources/images/slide-close.png" />"/>
            <img id="slideOpen" style="display:block;"  src="<c:url value="/resources/images/slide-open.png" />"/>
            </a>
            
            
            <form:form id="projDescForm" method="POST" commandName="projDescForm">
					<fieldset style="margin: 1em; text-align: left;">
						<legend><h3>Project Description Details</h3></legend>
						<table>
							<tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td><form:input id="aliasProjectName" path="aliasProjectName"
										placeholder="Enter Serial Number" cssClass="inputText"  readonly="true"/>
									</td>
							</tr>
							
							<c:if test="${projDescForm.subProjId gt '0'}">
							<tr>
								<td>Alias Project Name <span id="colon">:</span>
								</td>
								<td><form:input id="aliasSubProjectName" path="aliasSubProjectName"
										placeholder="Enter Serial Number" cssClass="inputText"  readonly="true" /></td>
							</tr>
							
							</c:if>
										
							<tr>
								<td>Serial Number<span id="colon">:</span>
								</td>
								<td><form:input id="serialNumber" path="serialNumber"
										placeholder="Enter Serial Number" cssClass="inputText"  readonly="true"/></td>
							</tr>
							<tr>
								<td>Quantity in Figures<span id="colon">:</span>
								</td>
								<td><form:input id="quantityInFig" path="quantityInFig"
										placeholder="Enter Quantity in Figures" cssClass="inputText"  readonly="true"/></td>
							</tr>
							<tr>
								<td>Quantity in unit<span id="colon">:</span>
								</td>
								<td><form:input path="quantityInUnit"
										placeholder="Enter Quantity in Unit"
										data-validation="length numeric" data-validation-length="min4"
										cssClass="inputText"  readonly="true" /></td>
							</tr>
							<tr>
								<td>Description<span id="colon">:</span>
								</td>
								<td><form:textarea path="description"
										placeholder="Enter Description" cssClass="inputText"  readonly="true"/></td>
							</tr>
							<tr>
								<td>Alias Description<span id="colon">:</span>
								</td>
								<td><form:input path="aliasDescription"
										placeholder="Enter Alias Description" cssClass="inputText"  readonly="true"/></td>
							</tr>
							<tr>
								<td>Rate in Figures<span id="colon">:</span>
								</td>
								<td><form:input path="rateInFig"
										placeholder="Enter Rate in Figures" cssClass="inputText"  readonly="true"/></td>
							</tr>
							<tr>
								<td>Rate in Words<span id="colon">:</span>
								</td>
								<td><form:input path="rateInWords"
										placeholder="Enter Rate in Words" cssClass="inputText"  readonly="true"/></td>
							</tr>
							<tr>
								<td>Amount in Rupees<span id="colon">:</span>
								</td>
								<td><form:input path="projDescAmount"
										placeholder="Enter Amount" cssClass="inputText"  readonly="true"/></td>
							</tr>
							<tr></tr>
						</table>
					</fieldset>
					</form:form>
        </div>
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
				<td><input name="itemCost" readonly="readonly" id="itemCost" type="text" /></td>
				<td><a id="deleteItem" onclick="deleteItemRow(this)"><img src="<c:url value="/resources/images/delete.png" />"/></a></td>
			</tr>

		</table>
		<br>
		Amount : <input name="totalItemCost" readonly="readonly" id="totalItemCost" type="text" />
		<br>
			
		<br>
		<input type="button" id="addItem" value="Add" onclick="insertItemRow()" />
		<input type="button" id="saveDesc" value="Save" onclick="saveItemDesc()" />
	</form:form>
</body>
