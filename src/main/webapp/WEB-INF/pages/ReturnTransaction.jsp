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
						$(document)
								.on(
										'keydown.autocomplete',
										selector,
										function() {
											$(this)
													.autocomplete(
															{
																source : function(
																		request,
																		response) {
																	var projId = $(
																			'#projId')
																			.val();
																	var fieldUser = $(
																	'#fieldUser')
																	.val();
																	$
																			.getJSON(
																					"/pms/emp/myview/returnTransaction/getItemNamesInStoreForReturn.do",
																					{
																						itemName : request.term,
																						projId : projId,
																						fieldUser : fieldUser
																					},
																					function(
																							data) {
																						response($
																								.map(
																										data,
																										function(
																												item) {
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
											$("#returnDetailTable").show();
										});						

					});
	function insertReturnDetailRow() {
		var returnDetailTable = document.getElementById('returnDetailTable');
		var new_row = returnDetailTable.rows[1].cloneNode(true);
		var len = returnDetailTable.rows.length;

		var itemName = new_row.cells[0].getElementsByTagName('input')[0];
		itemName.id += len;
		itemName.value = '';

		var dispatchedQuantity = new_row.cells[1].getElementsByTagName('input')[0];
		dispatchedQuantity.id += len;
		dispatchedQuantity.value = '';

		var returnedQuantity = new_row.cells[2].getElementsByTagName('input')[0];
		returnedQuantity.id += len;
		returnedQuantity.value = '';

		returnDetailTable.appendChild(new_row);
	}

	function validateItemNameExistence(newItemName) {
		var itemTable = document.getElementById('returnDetailTable');

		var len = itemTable.rows.length;
		var exists = false;
		for (i = 1; i <= len - 1; i++) {
			var itemName = itemTable.rows[i].cells[0]
					.getElementsByTagName('input')[0].value;
			if (itemName == newItemName) {
				exists = true;
				break;
			}
		}
		return exists;
	}
	
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
			var itemName = itemTable.rows[1].cells[0]
					.getElementsByTagName('input')[0].value;
			var dispatchedQuantity = itemTable.rows[1].cells[1]
					.getElementsByTagName('input')[0].value;
			var returnedQuantity = itemTable.rows[1].cells[2]
					.getElementsByTagName('input')[0].value;
			var obj = new ReturnItems(itemName, dispatchedQuantity,
					returnedQuantity);
			if (itemName && dispatchedQuantity && returnedQuantity
					|| !(itemName && dispatchedQuantity && returnedQuantity)) {
				itemObjArray.push(obj);
			} else {
				err = true;
			}
		} else {
			for (i = 1; i <= len - 1; i++) {
				var itemName = itemTable.rows[i].cells[0]
						.getElementsByTagName('input')[0].value;
				var dispatchedQuantity = itemTable.rows[i].cells[1]
						.getElementsByTagName('input')[0].value;
				var returnedQuantity = itemTable.rows[i].cells[2]
						.getElementsByTagName('input')[0].value;
				var obj = new ReturnItems(itemName, dispatchedQuantity,
						returnedQuantity);
				if (itemName && dispatchedQuantity && returnedQuantity
						|| !(itemName && dispatchedQuantity && returnedQuantity)) {
					itemObjArray.push(obj);
				} else {
					err = true;
				}
			}
		}
		returnDetailForm["returnItemsValue"] = JSON.stringify(itemObjArray);
		returnDetailForm["employeeId"] = document
				.getElementById('employeeId').value;
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

	function deleteItemRow(row) {
		var itemTable = document.getElementById('returnDetailTable');
		var noOfRow = document.getElementById('returnDetailTable').rows.length;
		if (noOfRow > 2) {
			var i = row.parentNode.parentNode.rowIndex;
			document.getElementById('returnDetailTable').deleteRow(i);
		} else {
			document.getElementById('returnDetailTable').rows[1].cells[0]
					.getElementsByTagName('input')[0].value = '';
			document.getElementById('returnDetailTable').rows[1].cells[1]
					.getElementsByTagName('input')[0].value = '';
			document.getElementById('returnDetailTable').rows[1].cells[2]
					.getElementsByTagName('input')[0].value = '';
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
								<th width="50px">Returned Quantity</th>
								<th width="50px">Action</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input class="dispatchDetailStyle" name="itemName" id="itemName" type="text"
									placeholder="Enter Item Name" />
								<td><input class="dispatchDetailStyle" name="dispatchedQuantity" id="dispatchedQuantity"
									type="text" readonly="true" /></td>
								<td><input class="dispatchDetailStyle" name="returnedQuantity" id="returnedQuantity"
									type="text" placeholder="Enter Returned Quantity" /></td>
								<td align="center"><a class="dispatchDetailStyle" id="deleteItem" onclick="deleteItemRow(this)"> <img
										src="<c:url value="/resources/images/delete.png" />" /></a></td>
							</tr>
						</tbody>
					</table>

					<table>
						<tr>
							<td></td>
							<br>

							<div id="result"
								style="text-align: left; font-family: arial; color: #007399; font-size: 16px;"></div>
							<br>
							<td><input class="button" type="button" value="Add"
								onclick="insertReturnDetailRow()" /></td>
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

