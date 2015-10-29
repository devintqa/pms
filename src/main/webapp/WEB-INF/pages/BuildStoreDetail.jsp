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
						if ($('#itemType').val() == '--Please Select--') {
							$("#itemNameField").hide();
						} else {
							$("#itemNameField").show();
						}
						$("#itemType")
								.change(
										function() {
											var itemType = $('#itemType').val();
											var projId = $('#projId').val();
											var employeeId = $('#employeeId')
													.val();
											$
													.ajax({
														type : "GET",
														url : "getItemNames.do",
														cache : false,
														data : "itemType="
																+ itemType
																+ "&projId="
																+ projId
																+ "&employeeId="
																+ employeeId,
														success : function(
																response) {
															if (response.success) {
																var obj = jQuery
																		.parseJSON(response.data);
																var options = '';
																for ( var key in obj) {
																	var attrName = key;
																	var attrValue = obj[key];
																	options = options
																			+ '<option value="' + attrValue + '">'
																			+ attrValue
																			+ '</option>';
																}
																$('#itemName')
																		.html(
																				options);
															} else {
																$(
																		"#itemNameField")
																		.hide();
																$(
																		"#dialog-confirm")
																		.html(
																				"Item name is not configured for the selected Item Type");
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
																$("#itemType")
																		.prop(
																				'selectedIndex',
																				0);
															}
														}

													});
											$("#itemNameField").show();
										});

					});
</script>
</head>

<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${successMessage}</h2>

			<h2
				style="text-align: left; font-family: arial; color: red; font-size: 14px;">${errorMessage}</h2>

		</div>
		<div>
			<form:form method="POST" commandName="storeDetailForm"
				action="saveStoreDetail.do">
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
							<tr>
								<td>Item Type<span id="colon">:</span></td>
								<td><form:select path="itemType" cssClass="inputText"
										id="itemType" items="${itemTypes}" /></td>
								<td><form:errors path="itemType" cssClass="error" /></td>
								<td>
									<div id="itemNameField">
										Item Name
										<form:select path="itemName" cssClass="inputText"
											id="itemName" items="${itemNames}">

											<option value="${storeDetailForm.itemName}"
												selected="selected">${storeDetailForm.itemName}</option>

										</form:select>
									</div>
								</td>
							</tr>
							<tr>
								<td>Supplier Name<span id="colon">:</span>
								</td>
								<td><form:input path="supplierName"
										placeholder="Enter Supplier Name" cssClass="inputText" /></td>
								<td><form:errors path="supplierName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Vehicle Number<span id="colon">:</span>
								</td>
								<td><form:input path="vehicleNumber"
										placeholder="Enter Vehicle Number" cssClass="inputText" /></td>
								<td><form:errors path="vehicleNumber" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Quantity Recieved<span id="colon">:</span>
								</td>
								<td><form:input path="recievedQuantity"
										placeholder="Enter Recieved Quantity" cssClass="inputText" /></td>
								<td><form:errors path="recievedQuantity" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Recieved Date<span id="colon">:</span>
								</td>
								<td><form:input path="recievedDate" id="recievedDate"
										placeholder="DD-MM-YYYY" cssClass="inputText" /></td>
								<td><form:errors path="recievedDate" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Recieved By<span id="colon">:</span>
								</td>
								<td><form:input path="recievedBy"
										placeholder="Enter Recieved By" cssClass="inputText" /></td>
								<td><form:errors path="recievedBy" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Checked By<span id="colon">:</span>
								</td>
								<td><form:input path="checkedBy"
										placeholder="Enter Checked By" cssClass="inputText" /></td>
								<td><form:errors path="checkedBy" cssClass="error" /></td>
							</tr>
							<tr>
								<td>TripSheet No<span id="colon">:</span>
								</td>
								<td><form:input path="tripSheetNumber"
										placeholder="Enter TripSheet Number" cssClass="inputText" /></td>
								<td><form:errors path="tripSheetNumber" cssClass="error" /></td>
							</tr>

							<tr>
								<td>Store Type <span id="colon">:</span></td>
								<td><form:radiobutton path="storeType" value="Inside Store"
										id="insideStore" checked="true" />Inside Store</td>
								<td><form:radiobutton path="storeType" value="Outside Store"
										id="outsideStore" />Outside Store</td>
							</tr>
							<tr>
								<td>Comments<span id="colon">:</span>
								</td>
								<td><form:textarea path="comments"
										placeholder="Enter Comments" cssClass="inputText" rows="5"
										cols="40" maxlength="2000" /></td>
								<td><form:errors path="comments" cssClass="error" /></td>
							</tr>

							<tr></tr>
						</table>
					</fieldset>

					<form:hidden path="employeeId" />

					<table>
						<tr>
							<td></td>
							<td><input class="button" type="submit" /></td>
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


