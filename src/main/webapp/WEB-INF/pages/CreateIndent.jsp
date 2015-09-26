<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Create Indent</title>
<%@include file="Script.jsp"%>
<style>
.dateField{
    border: 1px solid black;
    border-radius: 5px;
    padding: 5px;
}

.getIndentButton{
 	background: #007399 none repeat scroll 0 0;
    border: 0 none;
    border-radius: 5px;
    color: white;
    cursor: pointer;
    padding: 5px 15px;
    }
    
.saveIndentButton{
 	background: #007399 none repeat scroll 0 0;
    border: 0 none;
    border-radius: 5px;
    color: white;
    cursor: pointer;
    padding: 5px 15px;
    }
    
</style>
<script>
	$(document).ready(function() {
		$.fn.rowCount = function() {
		    return $('tr', $(this).find('tbody')).length;
		};
		
		$(".collapse").accordion({
			collapsible : true,
			active : false
		});
		
		$('.dateField').datepicker(
				   { dateFormat: 'dd-mm-yy'}
		);
		
		$('.getIndentButton').click(function(){
			var projDescId = $(this).attr('aria-desc-id');
			var indentQty = $("#plannedArea"+projDescId).val();
			$.ajax({
				type : "GET",
				url : "getIndentItem",
				contentType: "application/json",
				cache : false,
				data : "projDescId="+projDescId+"&indentQty="+indentQty,
				success : function(response) {
					var itemTable = document.getElementById("itemTbl"+projDescId);
					for (i = 0; i <= response.length - 1; i++) {
						var new_row = itemTable.rows[1].cloneNode(true);
						var len = itemTable.rows.length;
						new_row.cells[0].innerHTML = response[i].itemType;
						new_row.cells[1].innerHTML = response[i].itemName;
						new_row.cells[2].innerHTML = response[i].itemUnit;
						new_row.cells[3].innerHTML = response[i].itemQty;
						new_row.cells[4].innerHTML = '<a id="deleteItem" onclick="deleteItemRow(this)"><img src="/pms/resources/images/delete.png" /></a>';

						itemTable.appendChild(new_row);
					}
		
				}
			});
			
		});
		
		function ItemDetail(itemType, itemName, itemUnit, itemQty) {
			this.itemType = itemName;
			this.itemName = itemUnit;
			this.itemUnit = itemUnit;
			this.itemQty = itemQty;
			} 
		
		$('.saveIndentButton').click(function(){
			var itemObjArray = [];
			var indentDescForm = {};
			var projDescId = $(this).attr('aria-desc-id');
			var projId = $("#projId").val();
			var subProjId = $("#subProjId").val();
			var employeeId = $("#employeeId").val();
			
			var startDate = $("#startDate"+projDescId).val();
			var endDate = $("#endDate"+projDescId).val();
			var indentQty = $("#plannedArea"+projDescId).val();
			var indentMetric = $("#metric"+projDescId).val();
			
			var itemTable = document.getElementById("itemTbl"+projDescId);
			var tableLength = $("#itemTbl"+projDescId).rowCount();
			for (i = 0; i <= tableLength; i++) {
				var new_row = itemTable.rows[i];
				console.log(new_row.cells[0].innerHTML);
				var itemType = new_row.cells[0].innerHTML;
				var itemName = new_row.cells[0].innerHTML;
				var itemUnit = new_row.cells[0].innerHTML;
				var itemQty = new_row.cells[0].innerHTML;
				
				var obj = new ItemDetail(itemType, itemName, itemUnit, itemQty);
				if(itemType && itemName && itemUnit && itemQty){
					itemObjArray.push(obj); 
				}
			}
			indentDescForm["employeeId"] = employeeId;
			indentDescForm["projDescId"] = projDescId;
			indentDescForm["projId"] = projId;
			indentDescForm["indentItemDetail"] = JSON.stringify(itemObjArray);
			
			console.log(JSON.stringify(indentDescForm));
		});
		
	});

	function deleteItemRow(row) {
		var id = $(row).closest("table").attr('id');
		var itemTable = document.getElementById(id);
		var noOfRow = itemTable.rows.length;
		if (noOfRow > 1) {
			$(row).closest("tr").remove();
		}
	}
	
	
	
	
</script>
</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">

		<div>
			<form:form id="indentForm" commandName="indentForm">

				<c:forEach var="indent" items="${indentList}">
					<div class="collapse">
						<h3>${indent.aliasProjDesc}</h3>
						<fieldset style="margin: 1em; text-align: left;">
							<table>
								<tr>
									<td>Start date<span id="colon">:</span>
									</td>
									<td><input  class="dateField" id="startDate${indent.projDescId}" value="${indent.startDate}"  placeholder="DD-MM-YYYY"  /></td>
								</tr>
								<tr>
									<td>End date<span id="colon">:</span>
									</td>
									<td><input  class="dateField" id="endDate${indent.projDescId}" value="${indent.endDate}" placeholder="DD-MM-YYYY"  /></td>
								</tr>
								<tr>
									<td>Quantity<span id="colon">:</span>
									</td>
									<td><input id="plannedArea${indent.projDescId}" value="${indent.plannedArea}" class="inputText" /></td>
								</tr>
								<tr>
									<td>Metric<span id="colon">:</span>
									</td>
									<td><input id="metric${indent.projDescId}" value="${indent.metric}" readonly="readonly" class="inputText" /></td>
								</tr>
								<tr><td></td></tr>
								<tr>
									<td><input class="getIndentButton" aria-desc-id="${indent.projDescId}" value="Get Material" type="button" /></td>
									<td><input class="saveIndentButton" aria-desc-id="${indent.projDescId}" value="Indent" type="button" /></td>
									<td></td>
								</tr>
								<tr><td></td></tr>
							</table>
							
							<table id="itemTbl${indent.projDescId}" border="1" class="gridView">
								<tr>
									<th>Type</th>
									<th>Item</th>
									<th>Unit</th>
									<th>Adequacy</th>
									<th>Action</th>
								</tr>
									<c:forEach var="row" items="${indent.itemDetails}">
									<tr>
									<td>${row.itemType}</td>
									<td>${row.itemName}</td>
									<td>${row.itemUnit}</td>
									<td>${row.itemQty}</td>
									<td><a id="deleteItem" onclick="deleteItemRow(this)">
									<img src="<c:url value="/resources/images/delete.png" />" /></a></td>
								</tr>
									</c:forEach>
							</table>
						</fieldset>
					</div>
				</c:forEach>
				
		<form:hidden path="projId" id="projId" />
		<form:hidden path="subProjId" id="subProjId" />
		<form:hidden path="employeeId" id="employeeId" />
			</form:form>

		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


