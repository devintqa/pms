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
		    return $(this).find('tr').length;
		};
		
		$(".collapse").accordion({
			collapsible : true,
			active : false,
			heightStyle: "content" 
		});
		
		$('.dateField').datepicker(
				   { dateFormat: 'dd-mm-yy'}
		);
		
		
		$('.getIndentButton').click(function(){
			var projDescId = $(this).attr('aria-desc-id');
			var indentId = $(this).attr('aria-indent-id');
			var indentQty = $("#plannedArea"+projDescId+indentId).val();
			
			var itemTable = document.getElementById("itemTbl"+projDescId+indentId);
			var tableLength = itemTable.rows.length;
			
			$.ajax({
				type : "GET",
				url : "getIndentItem",
				contentType: "application/json",
				cache : false,
				data : "projDescId="+projDescId+"&indentQty="+indentQty,
				success : function(response) {
					
					while(itemTable.rows.length > 1){
						itemTable.deleteRow(tableLength-1);
						tableLength--;
					}
					
					for (i = 0; i < response.length; i++) {
						
					    var new_row = itemTable.insertRow(0);
					    var cell1 = new_row.insertCell(0);
					    var cell2 = new_row.insertCell(1);
					    var cell3 = new_row.insertCell(2);
					    var cell4 = new_row.insertCell(3);
					    var cell5 = new_row.insertCell(4);
					    
						new_row.cells[0].innerHTML = response[i].itemType.toUpperCase();
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
			this.itemType = itemType;
			this.itemName = itemName;
			this.itemUnit = itemUnit;
			this.itemQty = itemQty;
			} 
		
		$('.saveIndentButton').click(function(){
			var indent = {};
			var indentDescArray = [];
			
			var indentId = $(this).attr('aria-indent-id');
			var indentStatus = $(this).attr('aria-indent-status');
			var projId = $("#projId").val();
			var employeeId = $("#employeeId").val();
			var startDate = $('#workStartDate').val();
			var endDate = $('#workEndDate').val();
			
				 $("div[name = 'collapse']").each(function() {
					 var indentDesc = {};
					 var itemObjArray = [];
					 var projDescId = $(this).attr('aria-projdesc-id');
					 var indentDescQty = $("#plannedQty"+projDescId+indentId).val();
					 var indentDescMetric = $("#metric"+projDescId+indentId).val();
					 var indentDescComment = $("#comment"+projDescId+indentId).val();
					 var itemTable = document.getElementById("itemTbl"+projDescId+indentId);
					 var tableLength = $("#itemTbl"+projDescId+indentId).rowCount();
						for (i = 1; i < tableLength; i++) {
							var new_row = itemTable.rows[i];
							var itemType = new_row.cells[0].innerHTML;
							var itemName = new_row.cells[1].innerHTML;
							var itemUnit = new_row.cells[2].innerHTML;
							var itemQty = new_row.cells[3].innerHTML;
							
							var obj = new ItemDetail(itemType, itemName, itemUnit, itemQty);
							if(itemType && itemName && itemUnit && itemQty){
								itemObjArray.push(obj); 
							}
						}
						if(indentDescQty){
							indentDesc["plannedQty"] = indentDescQty;
							indentDesc["metric"] = indentDescMetric;
							indentDesc["comments"] = indentDescComment;
							indentDesc["projDescId"] = projDescId;
							indentDesc["itemDetails"] = itemObjArray;
							indentDescArray.push(indentDesc);
						}
			     });
					indent["projId"] = projId;
					indent["employeeId"] = employeeId;
					indent["startDate"] = startDate;
					indent["endDate"] = endDate;
					indent['indentDescList'] = indentDescArray
				
				console.log(JSON.stringify(indent));
					if(indentId == '_'){
						if(startDate && endDate){
							$.ajax({
								type : "POST",
								url : "saveIndentItem.do",
								contentType: "application/json",
								cache : false,
								data : JSON.stringify(indent),
								success : function(response) {
									if(response > 0){
										window.location = "/pms/emp/myview/indent/itemToRequest?employeeId="+employeeId+"&indentId="+response+"&status=NEW";
									}else{
										$('#error').text('Error occured while saving the indent!');
									}
								}
							});
						}else{
							$('#error').text('Date field value is missing!');
						}
					}else{
						window.location = "/pms/emp/myview/indent/itemToRequest?employeeId="+employeeId+"&indentId="+indentId+"&status="+indentStatus;
					}
		});
		
	});

	
	$(document).on("keyup", "input[name = 'plannedQty']", function() {
		
		var valid = /^\d+(\.\d{0,2})?$/.test(this.value);
		var val = this.value;
		if (!valid) {
			console.log("Invalid input!");
			this.value = val.substring(0, val.length - 1);
		}else{
			var projDescId = $(this).attr('aria-projdesc-id');
			var indentDescId =  $(this).attr('aria-indentdesc-id');
			var indentQty = this.value;
			
			var itemTable = document.getElementById("itemTbl"+projDescId+indentDescId);
			var tableLength = itemTable.rows.length;
			
			$.ajax({
				type : "GET",
				url : "validateIndentDescQty",
				contentType: "application/json",
				cache : false,
				data : "projDescId="+projDescId+"&indentQty="+indentQty,
				success : function(response) {
					if(response=='valid'){
						$('#error'+projDescId).text('');
						$.ajax({
							type : "GET",
							url : "getIndentItem",
							contentType: "application/json",
							cache : false,
							data : "projDescId="+projDescId+"&indentQty="+indentQty,
							success : function(response) {
								
								while(itemTable.rows.length > 1){
									itemTable.deleteRow(tableLength-1);
									tableLength--;
								}
								
								for (i = 0; i < response.length; i++) {
									
								    var new_row = itemTable.insertRow(0);
								    var cell1 = new_row.insertCell(0);
								    var cell2 = new_row.insertCell(1);
								    var cell3 = new_row.insertCell(2);
								    var cell4 = new_row.insertCell(3);
								    var cell5 = new_row.insertCell(4);
								    
									new_row.cells[0].innerHTML = response[i].itemType.toUpperCase();
									new_row.cells[1].innerHTML = response[i].itemName;
									new_row.cells[2].innerHTML = response[i].itemUnit;
									new_row.cells[3].innerHTML = response[i].itemQty;
									new_row.cells[4].innerHTML = '<a id="deleteItem" onclick="deleteItemRow(this)"><img src="/pms/resources/images/delete.png" /></a>';

									itemTable.appendChild(new_row);
								}
							}
						});
					}else{
						$('#error'+projDescId).text(response);
						val='';
						while(itemTable.rows.length > 1){
							itemTable.deleteRow(tableLength-1);
							tableLength--;
						}
					}
				}
			});
			
			
		}
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
			<form:form id="indentForm" commandName="indent">
				
				<fieldset style="margin: 1em; text-align: left;">
						<legend>Indent Period</legend>
				<table style="width:50%">
					<tr>
						<td>Start date<span id="colon">:</span>
						</td>
						<td><input  class="dateField" id="workStartDate" value="${indent.startDate}"  placeholder="DD-MM-YYYY"  /></td>
						<td>End date<span id="colon">:</span>
						</td>
						<td><input  class="dateField" id="workEndDate" value="${indent.endDate}"  placeholder="DD-MM-YYYY"  /></td>
					</tr>
				</table>
				</fieldset>
				
				<c:forEach var="indentDesc" items="${indentDescList}">
					<div name="collapse" class="collapse"  aria-projdesc-id="${indentDesc.projDescId}" aria-indentdesc-id="${indentDesc.indentDescId}" >
						<h3>${indentDesc.aliasProjDesc}
						  <c:if test="${indentDesc.indentId eq '_'}">
                                    *
                           </c:if>
						</h3>
						<fieldset style="margin: 1em; text-align: left;">
							<table>
								<tr>
									<td>Quantity<span id="colon">:</span>
									</td>
									<td><input name="plannedQty" aria-projdesc-id="${indentDesc.projDescId}" aria-indentdesc-id="${indentDesc.indentDescId}" 
										id="plannedQty${indentDesc.projDescId}${indentDesc.indentDescId}" value="${indentDesc.plannedQty}" class="inputText" /> 
										&nbsp; <span style="color:red;" id="error${indentDesc.projDescId}"></span></td>
								</tr>
								<tr>
									<td>Metric<span id="colon">:</span>
									</td>
									<td><input id="metric${indentDesc.projDescId}${indentDesc.indentDescId}" value="${indentDesc.metric}" 
									readonly="readonly" class="inputText" /></td>
								</tr>
								<tr>
									<td>Comment<span id="colon">:</span>
									</td>
									<td><input id="comment${indentDesc.projDescId}${indentDesc.indentDescId}" value="${indentDesc.comments}" 
									class="inputText" /></td>
								</tr>
								<tr><td></td></tr>
							</table>
							
							<table id="itemTbl${indentDesc.projDescId}${indentDesc.indentDescId}" border="1" class="gridView">
								
								<tr>
									<th>Type</th>
									<th>Item</th>
									<th>Unit</th>
									<th>Adequacy</th>
									<th>Action</th>
								</tr>
								
								<c:forEach var="row" items="${indentDesc.itemDetails}">
									<tr>
										<td>${row.itemType}</td>
										<td>${row.itemName}</td>
										<td>${row.itemUnit}</td>
										<td>${row.itemQty}</td>
										<td><a id="deleteItem" onclick="deleteItemRow(this)">
												<img src="<c:url value="/resources/images/delete.png" />" />
										</a></td>
									</tr>
								</c:forEach>
							</table>
						</fieldset>
					</div>
				</c:forEach>
				<input type="hidden" value="${indent.projId}" id="projId" />
				<input type="hidden" value="${indent.employeeId}" id="employeeId" />
				
					<center>
					<br>
					<span style="color: red;" id="error"></span>
						<table>
							<tr>
								<td><input class="saveIndentButton"
									aria-indent-id="${indent.indentId}" aria-indent-status="${indent.status}"value="Next" type="button" /></td>
							</tr>
						</table>
					</center>
				
				<table style="display:none;">
					<tr id="dummyRow">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><a id="deleteItem" onclick="deleteItemRow(this)"> <img
								src="<c:url value="/resources/images/delete.png" />" />
						</a></td>
					</tr>
				</table>
			</form:form>

		</div>

	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


