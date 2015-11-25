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



$(document).ready(

        function () {
      	$("#saveBtn").click(function() {
        		
        		 var storeDetails = [];
        		 var storeDetailForm = {};
        		     var aliasProjName = document.getElementById('aliasProjName').value;
        		     var itemType = document.getElementById('itemType').value;
        		     var itemName = document.getElementById('itemName').value;
        		     var itemQty = document.getElementById('itemQty').value;
        		     var supplierAliasName = document.getElementById('supplierName').value;
        		     var vehicleNumber = document.getElementById('vehicleNumber').value;
        		     var recievedQty = document.getElementById('recievedQuantity').value;
        		     var recievedDate = document.getElementById('recievedDate').value;
        		     var recievedBy = document.getElementById('recievedBy').value;
        		     var checkedBy = document.getElementById('checkedBy').value;
        		     var tripSheetNumber = document.getElementById('tripSheetNumber').value;
        		     var storeType;     
        		     if (document.getElementById('insideStore').checked) {
        		    	 storeType = document.getElementById('insideStore').value;
        		    	}
        		     if (document.getElementById('outsideStore').checked) {
        		    	 storeType = document.getElementById('outsideStore').value;
        		    	}
        		     var comments = document.getElementById('comments').value;
   
        		     var obj = new StoreDetails(aliasProjName, itemType, itemName, itemQty, supplierAliasName, vehicleNumber, recievedQty, recievedDate, recievedBy, checkedBy, tripSheetNumber, storeType, comments);
        		     storeDetails.push(obj);
        			
        		     storeDetailForm["storeDetailsValue"] = JSON.stringify(storeDetails);
        		     storeDetailForm["aliasProjName"] = document.getElementById('aliasProjName').value;
        		     storeDetailForm["itemType"] = document.getElementById('itemType').value;
        		     storeDetailForm["itemName"] = document.getElementById('itemName').value;
        		     storeDetailForm["itemQty"] = document.getElementById('itemQty').value;
        		     storeDetailForm["supplierName"] = document.getElementById('supplierName').value;
        		     storeDetailForm["vehicleNumber"] = document.getElementById('vehicleNumber').value;
        		     storeDetailForm["recievedQuantity"] = document.getElementById('recievedQuantity').value;
        		     storeDetailForm["recievedDate"] = document.getElementById('recievedDate').value;
        		     storeDetailForm["recievedBy"] = document.getElementById('recievedBy').value;
        		     storeDetailForm["checkedBy"] = document.getElementById('checkedBy').value;
        		     storeDetailForm["tripSheetNumber"] = document.getElementById('tripSheetNumber').value;
        		     storeDetailForm["storeType"] = storeType
        		     storeDetailForm["comments"] = document.getElementById('comments').value;
        		     
        		     $.ajax({
        		              type: "POST",
        		              url: "saveStoreDetail.do",
        		             contentType: "application/json",
        		              cache: false,
        		              data: JSON.stringify(storeDetailForm),
        		              success: function (response) {
        		            	  if (response.success) {
        			                	$("#dialog-confirm").html(${successMessage});
        			                	$("#dialog-confirm").dialog({
        			                         modal: true,
        			                         title: "Message!",
        			                         height: 200,
        			                         width: 300,
        			                         buttons: {
        			                             Ok: function () {
        			                                 $(this).dialog("close");
        			                                
        			                             }
        			                         },
        								 close: function( event, ui ) {
        								 }
        			                     });
        								
        			                } else {
        			                	 $('#result').html(${errorMessage});
        			                }
        		              }        		               
        			});
        		    
        		});	
   });
   
function StoreDetails(aliasProjName, itemType, itemName, itemQty, supplierAliasName, vehicleNumber, recievedQty, recievedDate, recievedBy, checkedBy, tripSheetNumber, storeType, comments) {
	this.aliasProjName = aliasProjName;
	this.itemType = itemType;
	this.itemName = itemName;
	this.itemQty = itemQty;
    this.supplierAliasName = supplierAliasName;
    this.vehicleNumber= vehicleNumber;
    this.recievedQty=recievedQty;
    this.recievedDate = recievedDate;
    this.recievedBy = recievedBy;
    this.checkedBy = checkedBy;
    this.tripSheetNumber = tripSheetNumber;
    this.storeType = storeType;
    this.comments = comments;
    
}

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
			<form:form commandName="storeDetailForm" method="POST"
				id="storeDetailForm">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Store Details</legend>
						<table>
							<tr id="showAliasProjects">
								<td>Project Name <span id="colon">:</span>
								</td>
								<td><form:input path="aliasProjName" cssClass="inputText"
										id="aliasProjName"
										value="${supplierQuoteDetails.aliasProjName}" readonly="true" /></td>
								<td><form:errors path="aliasProjName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Item Type<span id="colon">:</span></td>
								<td><form:input path="itemType" cssClass="inputText"
										id="itemType" value="${supplierQuoteDetails.itemType}"
										readonly="true" /></td>
								<td><form:errors path="itemType" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Item Name<span id="colon">:</span></td>
								<td><form:input path="itemName" cssClass="inputText"
										id="itemName" value="${supplierQuoteDetails.itemName}"
										readonly="true" /></td>
							</tr>

							<tr>
								<td>Item Quantity<span id="colon">:</span></td>
								<td><form:input path="itemQty" cssClass="inputText"
										id="itemQty" value="${supplierQuoteDetails.itemQty}"
										readonly="true" /></td>
							</tr>
							<tr>
								<td>Supplier Name<span id="colon">:</span>
								</td>
								<td><form:input path="supplierName" id="supplierName"
										cssClass="inputText"
										value="${supplierQuoteDetails.supplierAliasName}"
										readonly="true" /></td>
								<td><form:errors path="supplierName" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Vehicle Number<span id="colon">:</span>
								</td>
								<td><form:input path="vehicleNumber" id="vehicleNumber"
										placeholder="Enter Vehicle Number" cssClass="inputText" /></td>
								<td><form:errors path="vehicleNumber" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Quantity Recieved<span id="colon">:</span>
								</td>
								<td><form:input path="recievedQuantity"
										id="recievedQuantity" placeholder="Enter Recieved Quantity"
										cssClass="inputText" /></td>
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
								<td><form:input path="recievedBy" id="recievedBy"
										placeholder="Enter Recieved By" cssClass="inputText" /></td>
								<td><form:errors path="recievedBy" cssClass="error" /></td>
							</tr>
							<tr>
								<td>Checked By<span id="colon">:</span>
								</td>
								<td><form:input path="checkedBy" id="checkedBy"
										placeholder="Enter Checked By" cssClass="inputText" /></td>
								<td><form:errors path="checkedBy" cssClass="error" /></td>
							</tr>
							<tr>
								<td>TripSheet No<span id="colon">:</span>
								</td>
								<td><form:input path="tripSheetNumber" id="tripSheetNumber"
										placeholder="Enter TripSheet Number" cssClass="inputText" /></td>
								<td><form:errors path="tripSheetNumber" cssClass="error" /></td>
							</tr>

							<tr>
								<td>Store Type <span id="colon">:</span></td>
								<td><form:radiobutton path="storeType" value="Inside Store"
										id="insideStore" checked="true" />Inside Store</td>
								<td><form:radiobutton path="storeType"
										value="Outside Store" id="outsideStore" />Outside Store</td>
							</tr>
							<tr>
								<td>Comments<span id="colon">:</span>
								</td>
								<td><form:textarea path="comments" id="comments"
										placeholder="Enter Comments" cssClass="inputText" rows="5"
										cols="40" maxlength="2000" /></td>
								<td><form:errors path="comments" cssClass="error" /></td>
							</tr>

							<tr></tr>
						</table>
					</fieldset>

					<form:hidden path="employeeId" />
					<form:hidden path="storeDetailsValue" id="storeDetailsValue" />

					<table>
						<tr>
							<td></td>
							<td><input id="saveBtn" class="button" type="button"
								value="Save" /></td>
							<td></td>
						</tr>
					</table>
				</center>
				<br>
			</form:form>
		</div>
		
		<div id="result"
                             style="text-align: left; font-family: arial; color: #007399; font-size: 16px;"></div>
                        <br>
		<div id="dialog-confirm"></div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>


