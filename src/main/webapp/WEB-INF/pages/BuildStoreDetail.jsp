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
function () {
    //add more file components if Add is clicked
    $('#addFileBtn')
            .click(
            function () {debugger;
                var fileIndex = $('#fileTable tr')
                        .children().length - 1;
                $('#fileTable')
                        .append(
                                '<tr><td></td>'
                                + '<td>  <input type="file" name="storeFiles[' + fileIndex + ']" />'
                                + '</td></tr>');
            });
    
    <c:if test="${employeeObj.employeeTeam eq 'Purchase'}">
    $('#itemRow').hide();
    $('#uploadRow').hide();
    $('#addFileBtn').hide();
    $('#saveBtn').hide();
    $('#recievedDate').attr("disabled", "true");
    $("#storeDetailForm").find("input,textarea,select,a,radiobutton").attr("disabled", "true");
    </c:if>
    
    $(function() {
		var table = $("#projectFileList").dataTable();
	})

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
			<form:form commandName="storeDetailForm" method="POST"
				id="storeDetailForm" action="saveStoreDetail.do" enctype="multipart/form-data">
				<center>
					<fieldset style="margin: 1em; text-align: left;">
						<legend>Store Details</legend>
						<table id = "fileTable">
							<tr>
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

							<tr id="itemRow">
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
                                <td>Brand Name<span id="colon">:</span>
                                </td>
                                <td><form:input path="brandName" id="brandName"
                                                cssClass="inputText"
                                                value="${supplierQuoteDetails.brandName}"
                                                readonly="true" /></td>
                                <td><form:errors path="brandName" cssClass="error" /></td>
                            </tr>
							
							<tr>
								<td>Invoice Number<span id="colon">:</span>
								</td>
								<td><form:input path="invoiceNumber" id="invoiceNumber"
										placeholder="Enter Invoice Number" cssClass="inputText" /></td>
								<td><form:errors path="invoiceNumber" cssClass="error" /></td>
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
							
							 <tr id="uploadRow">
				                    <td>Upload<span id="colon">:</span>
				                    </td>
				                    <td><form:input name="storeFiles[0]" type="file" path="storeFiles"
				                                    id="storeFiles"/></td>
				                    <td><form:errors path="storeFiles" cssClass="error"/></td>
		                </tr>

							<tr></tr>
						</table>
					</fieldset>

					<form:hidden path="employeeId" />
					<form:hidden path="storeDetailsValue" id="storeDetailsValue" />

					<table>
						<tr>
							<td></td>
							<td><input class="button" id="saveBtn" type="submit"	value="Save" /></td>
								<td> <input class="button" id="addFileBtn" type="button" value="Add File"/></td>
							<td></td>
						</tr>
					</table>
		<div>
			<c:if test="${invoiceFileSize gt 0}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">Uploaded 
				File Details</h1>
	
				<table id="projectFileList" class="gridView">
					<thead>
						<tr>
							<th>File Name</th>
							<th>File Path</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty invoiceFileList}">
							<c:forEach var="fileDoc" items="${invoiceFileList}">
								<tr>
									<td>${fileDoc.fileName}</td>
									<td>${fileDoc.filePath}</td>
									<td><a
										href="/pms/emp/myview/downloadFile/downloadFiles.web?path=${fileDoc.filePath}">
											Download File </a></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
		</div>
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


