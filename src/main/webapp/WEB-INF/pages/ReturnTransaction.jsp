<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>

<title>PMS :: Store Details</title>

<%@include file="Script.jsp"%>
<script>
	$(document)
			.ready(
					function() {
						if ($('#projId').val() == 0) {
							$("#itemNameField").hide();
							$("#returnDetailTable").hide();
						}

						var selector = "input[name = 'itemName']";
						$(document).on(
						'keydown.autocomplete',
							selector,function() {
							$(this).autocomplete(
									{
										source : function(
											request,response) {
											var projId = $(
													'#projId').val();
											var fieldUser = $('#fieldUser').val();
															$.getJSON(
																	"/pms/emp/myview/returnTransaction/getItemNamesInStoreForReturn.do",
																	{
																		itemName : request.term,
																		projId : projId,
																		fieldUser : fieldUser
																	},
													function(data) {response($.map(
															data,function(item) 
															{
																return {
																	label : item.itemName,
																	value : item.itemName,
																	dispatchedQuantity : item.dispatchedQuantity,
																	projId : item.itemName
																	};
															}))
															});
																},
																select : function(
																		event,
																		ui) {
																	var newItemName = ui.item.label;
																	if (validateItemNameExistence(newItemName)) {
																		alert("Item already exists!");
																		event
																				.preventDefault();
																		$(this)
																				.val(
																						'');
																	} else {
																		$(this)
																				.parents(
																						'tr:first')
																				.find(
																						'td:nth-child(2) input')
																				.val(
																						ui.item.dispatchedQuantity);
																		$(this)
																				.parents(
																						'tr:first')
																				.find(
																						'td:nth-child(3) input')
																				.val(
																						'');
																		$(this)
																				.parents(
																						'tr:first')
																				.find(
																						'td:nth-child(1) input:nth-child(2)')
																				.val(
																						ui.item.itemName);

																	}
																}
															});
										});

						$("#projId")
								.change(
										function() {
											var projId = $('#projId').val();
											var employeeId = $('#employeeId')
													.val();
											$
													.ajax({
														type : "GET",
														url : "getFieldUsersForReturn.do",
														cache : false,
														data : "projId="
																+ projId
																+ "&employeeId="
																+ employeeId,
														success : function(
																response) {
															if (response.success) {
																var obj = jQuery
																		.parseJSON(response.object);
																var options = '';
																for ( var key in obj) {
																	var attrName = key;
																	var attrValue = obj[key];
																	options = options
																			+ '<option value="' + attrValue + '">'
																			+ attrValue
																			+ '</option>';
																}
																$('#fieldUser')
																		.html(
																				options);
															} else {

																$("#projId")
																		.prop(
																				'selectedIndex',
																				0);
																$(
																		"#dialog-confirm")
																		.html(
																				response.data);
																$(
																		"#dialog-confirm")
																		.dialog(
																				{
																					modal : true,
																					title : "Warning!",
																					height : 200,
																					width : 300,
																					buttons : {
																						Ok : function() {
																							$(
																									this)
																									.dialog(
																											"close");
																						}
																					}
																				});
															}
														}
													});
											$("#itemNameField").show();
										});

						$("#fieldUser")
								.change(
										function() {
											var projId = $('#projId').val();
											var fieldUser = $('#fieldUser')
													.val();
											$
													.ajax({
														type : "GET",
														url : "validateFieldUsersForReturn.do",
														cache : false,
														data : "projId="
																+ projId
																+ "&fieldUser="
																+ fieldUser,
														success : function(
																response) {
															if (response.success) {
																var obj = jQuery
																		.parseJSON(response.object);

																$(function() {
																	 var trHTML = '';
																	 $("#returnDetailTable > tbody").html("");
																	$.each(obj,function(i,item)
																		{
																		trHTML += '<tbody id ="tbodyid"><tr><td>' + item.itemName + '</td><td>' + item.dispatchedQuantity + '</td><td><input  class="dispatchDetailStyle" name="returnedQuantity" id="returnedQuantity" type="text" value="0"></td></tr></tbody>';
																		});
																	 $('#returnDetailTable').append(trHTML);
																});
															} else {
																$("#returnDetailTable");
																
																$(
																		"#returnDetailTable")
																		.hide();
																$("#fieldUser")
																		.prop(
																				'selectedIndex',
																				0);
																$(
																		"#dialog-confirm")
																		.html(
																				response.data);
																$(
																		"#dialog-confirm")
																		.dialog(
																				{
																					modal : true,
																					title : "Warning!",
																					height : 200,
																					width : 300,
																					buttons : {
																						Ok : function() {
																							$(
																									this)
																									.dialog(
																											"close");
																						}
																					}
																				});
															}
														}
													});
											$("#returnDetailTable").show();
										});

					});

	function ReturnItems(itemName, dispatchedQuantity, returnedQuantity) {
        this.itemName = itemName;
        this.dispatchedQuantity = dispatchedQuantity;
        this.returnedQuantity = returnedQuantity;
    }

	function saveReturnedItem() {
		var itemTable = document.getElementById('returnDetailTable');
		var itemObjArray = [];
		var returnDetailForm = {};
		var len = itemTable.rows.length;
		var err = null;
		 if (1 == len - 1) {
             var itemName = $("#td1").text();
             var dispatchedQuantity = $("#td2").text();
             var returnedQuantity = itemTable.rows[1].cells[2].getElementsByTagName('input')[0].value;
             var obj = new ReturnItems(itemName, dispatchedQuantity, returnedQuantity);
             if (itemName && dispatchedQuantity && returnedQuantity || !(itemName && dispatchedQuantity && returnedQuantity)) {
                 itemObjArray.push(obj);
             } else {
                 err = true;
             }
         } else {
             for (i = 1; i <= len - 1; i++) {
            	 var itemName = $("#td1").text();
                 var dispatchedQuantity = $("#td2").text();
                 var returnedQuantity = itemTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
                 var obj = new ReturnItems(itemName, dispatchedQuantity, returnedQuantity);
                 if (itemName && dispatchedQuantity && returnedQuantity || !(itemName && dispatchedQuantity && returnedQuantity)) {
                     itemObjArray.push(obj);
                 } else {
                     err = true;
                 }
             }
         }
                
		returnDetailForm["returnItemsValue"] = JSON.stringify(itemObjArray);
		returnDetailForm["employeeId"] = document.getElementById('employeeId').value;
		returnDetailForm["projId"] = document.getElementById('projId').value;
		returnDetailForm["fieldUser"] = document.getElementById('fieldUser').value;
		if (err) {
			alert("Please make sure that all the required fields are entered.");
		} else {
			$.ajax({
				type : "POST",
				url : "saveReturnedDetail.do",
				contentType : "application/json",
				cache : false,
				data : JSON.stringify(returnDetailForm),
				success : function(data) {
					$('#result').html(data);
				}
			});
		}
	}
</script>
<style>
.dispatchDetailStyle {
	width: 200px;
}
</style>
</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">

		<div>
			<form:form method="POST" commandName="returnDetailForm"
				action="saveReturnedDetail.do">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Store Details</legend>
						<table>
							<tr id="showAliasProject">
								<td>Project Name <span id="colon">:</span>
								</td>
								<td><form:select path="projId" cssClass="inputText"
										id="projId" items="${aliasProjectList}">
									</form:select></td>
								<td><form:errors path="projId" cssClass="error" /></td>


							</tr>
							<table id="itemNameField">
								<tr>
									<td>Field Engineer<span id="colon">:</span>
									</td>
									<td><form:select path="fieldUser" cssClass="inputText"
											id="fieldUser" items="${fieldUsers}">
											<option value="${fieldUser}" selected="selected">${fieldUser}</option>

										</form:select></td>
								</tr>
								<tr>
									<td>Returned Date<span id="colon">:</span>
									</td>
									<td><form:input path="returnedDate"
											placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
									<td><form:errors path="returnedDate" cssClass="error" /></td>
								</tr>
							</table>

							<tr></tr>
						</table>
					</fieldset>
					<form:hidden path="employeeId" />

					<table id="returnDetailTable" class="gridView">
						<thead>
							<tr>
								<th width="50px">Item Name</th>
								<th width="50px">Dispatched Quantity</th>
								<th width="50px">Requested Quantity</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>



					<table>
						<tr>
							<td></td>
							<br>

							<div id="result"
								style="text-align: left; font-family: arial; color: #007399; font-size: 16px;"></div>
							<br>
							
							<td><input class="button" type="button" value="Submit"
								onclick="saveReturnedItem()" /></td>

							<td></td>
						</tr>
					</table>
				</center>
				<br>
			</form:form>
		</div>
		<div id="dialog-confirm"></div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>

