<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!doctype html>
<html>
<head>
<title>PMS :: Project Home</title>

<%@include file="Script.jsp"%>
<%@include file="Utility.jsp"%>

<script>
	function manageUser(userId, action) {
		var entity = {
			"user" : userId,
			"action" : action
		};
		$.ajax({
			type : 'POST',
			url : 'manageAccess.do',
			contentType : 'application/json',
			data : JSON.stringify(entity),
			dataType : "json",
			success : function(response) {
				if (response == '1') {
					location.reload();
				}
			},
			error : function(err) {
				alert("error - " + err);
			}
		});
	}
</script>
</head>


<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">
		<div>
			<h3 id="welcomeMessage">Welcome, ${employeeObj.employeeFName}&nbsp!</h3>
			<h4 id="welcomeRole">Role: ${employeeObj.employeeRole}</h4>

			<h2
				style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noProjectCreated}</h2>
		</div>
		<c:if test="${employeeObj.employeeTeam eq 'Admin'}">
			<h1 style="text-align: center; color: #007399; font-size: 24px;">New
				Signup Requests</h1>
			<table id="newSignUpRequests" class="gridView">
				<thead>
					<tr>
						<th>User Name</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Gender</th>
						<th>Team</th>
						<th>Address</th>
						<th>Mobile</th>
						<th>Mail</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty newSignupRequestList}">
						<c:forEach var="signReq" items="${newSignupRequestList}">
							<tr>
								<td>${signReq.employeeId}</td>
								<td>${signReq.employeeFName}</td>
								<td>${signReq.employeeLName}</td>
								<td>${signReq.employeeGender}</td>
								<td>${signReq.employeeTeam}</td>
								<td>${signReq.employeeAddress}</td>
								<td>${signReq.employeeMobile}</td>
								<td>${signReq.employeeMail}</td>
								<td><c:if test="${signReq.enabled eq '0'}">
										<a
											href="javascript:manageUser('${signReq.employeeId}', 'enable');"
											class="userAction">Enable</a> &nbsp;&nbsp;
                            			<a
											href="javascript:manageUser('${signReq.employeeId}', 'deny');"
											class="userAction">Deny</a>
									</c:if> 
									<c:if test="${listValue.enabled eq '1'}">
										<a
											href="javascript:manageUser('${signReq.employeeId}', 'disable');"
											class="userAction">Disable</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</c:if>

		<c:if test="${employeeObj.employeeTeam eq 'Admin'}">
			<h1 style="text-align: center; color: #007399; font-size: 18px;">Deposit
				end dates are nearing for following projects. Please take necessary
				action.</h1>
			<table id="depositDocumentList" class="gridView">
				<thead>
					<tr>
						<th>Project Name</th>
						<th>Sub Project Name</th>
						<th>Deposit Amount</th>
						<th>Deposit Start Date</th>
						<th>Deposit End Date</th>
						<th>Deposit Type</th>
						<th>Deposit Extension Date</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty depositDocumentList}">
						<c:forEach var="detail" items="${depositDocumentList}">
							<tr>
								<td>${detail.aliasProjectName}</td>
								<td>${detail.aliasSubProjectName}</td>
								<td>${detail.depositAmount}</td>
								<td>${detail.depositStartDate}</td>
								<td>${detail.depositEndDate}</td>
								<td>${detail.depositType}</td>
								<td>${detail.depositExtensionDate}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
		</c:if>

		<c:if test="${employeeObj.employeeTeam eq 'Technical'}">

        Click here to <a href="downloadTemplate.do" class="userAction">download
				template</a>
			<br>
			<br>
			<br>
        Click here to <a href="downloadItemDescTemplate.do"
				class="userAction">download Item Description Template</a>
			<br>
			<br>
			<br>

			<h1 style="text-align: center; color: #007399; font-size: 18px;">Deposit
				end dates are nearing for following projects. Please take necessary
				action.</h1>
			<table id="depositDocumentList" class="gridView">
				<thead>
					<tr>
						<th>Project Name</th>
						<th>Sub Project Name</th>
						<th>Deposit Amount</th>
						<th>Deposit Start Date</th>
						<th>Deposit End Date</th>
						<th>Deposit Type</th>
						<th>Deposit Extension Date</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty depositDocumentList}">
						<c:forEach var="detail" items="${depositDocumentList}">
							<tr>
								<td>${detail.aliasProjectName}</td>
								<td>${detail.aliasSubProjectName}</td>
								<td>${detail.depositAmount}</td>
								<td>${detail.depositStartDate}</td>
								<td>${detail.depositEndDate}</td>
								<td>${detail.depositType}</td>
								<td>${detail.depositExtensionDate}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>

			<c:if test="${employeeObj.employeeRole eq 'Technical Manager'}">
				<h1 style="text-align: center; color: #007399; font-size: 18px;">Indents
					that require immediate attention</h1>
				<table id="indentList" class="display">
					<thead>
						<tr>
							<th>Indent No</th>
							<th>Project</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty indentList}">
							<c:forEach var="indent" items="${indentList}">
								<tr>
									<td>${indent.indentId}</td>
									<td>${indent.description}</td>
									<td>${indent.startDate}</td>
									<td>${indent.endDate}</td>
									<td>${indent.status}</td>
									<td><a
										href="/pms/emp/myview/indent/createIndent?employeeId=${employeeObj.employeeId}&projectId=${indent.projId}&indentId=${indent.indentId}">Approve</a>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
		</c:if>
		<c:if test="${employeeObj.employeeTeam eq 'Management'}">
			<c:if test="${employeeObj.employeeRole eq 'General Manager'}">
				<h1 style="text-align: center; color: #007399; font-size: 18px;">Indents
					that require immediate attention</h1>
				<table id="indentList" class="display">
					<thead>
						<tr>
							<th>Indent No</th>
							<th>Project</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty indentList}">
							<c:forEach var="indent" items="${indentList}">
								<tr>
									<td>${indent.indentId}</td>
									<td>${indent.description}</td>
									<td>${indent.startDate}</td>
									<td>${indent.endDate}</td>
									<td>${indent.status}</td>
									<td><a
										href="/pms/emp/myview/indent/createIndent?employeeId=${employeeObj.employeeId}&projectId=${indent.projId}&indentId=${indent.indentId}">Approve</a>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
				
				<h1 style="text-align: center; color: #007399; font-size: 18px;">Supplier Details
					that require immediate attention</h1>
				<table id="supplierList" class="display">
					<thead>
						<tr>
							<th>Project</th>
							<th>Item Name</th>
							<th>Item Quantity</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty supplierList}">
							<c:forEach var="supplier" items="${supplierList}">
								<tr>
									<td>${supplier.aliasProjName}</td>
									<td>${supplier.itemName}</td>
									<td>${supplier.itemQty}</td>
									<td>${supplier.supplierQuoteStatus}</td>
									<td>
										<a href="/pms/emp/myview/supplierQuoteDetails/${supplier.aliasProjName}?itemName=${supplier.itemName}&itemQty=${supplier.itemQty}&employeeId=${employeeObj.employeeId}&itemType=${supplier.itemType}&status=${supplier.supplierQuoteStatus}">Approve</a>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
			</c:if>
		</c:if>

		<c:if test="${employeeObj.employeeTeam eq 'Purchase'}">
			<h1 style="text-align: center; color: #007399; font-size: 18px;">Indents
				that require immediate attention</h1>
			<table id="indentList" class="display">
				<thead>
					<tr>
						<th>Project</th>
						<th>Status</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>
					<c:if test="${not empty indentList}">
						<c:forEach var="indent" items="${indentList}">
							<tr>
								<td>${indent.description}</td>
								<td>${indent.status}</td>
								<td><a href="/pms/emp/myview/indent/itemToRequestView?employeeId=${employeeObj.employeeId}&indentId=${indent.indentId}&projId=${indent.projId}&status=${indent.status}&projName=${indent.description}">View</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<br>
			<br>
			
			<h1 style="text-align: center; color: #007399; font-size: 18px;">Approved Purchases
				that require immediate attention</h1>
			<table id="purchaseList" class="display">
				<thead>
					<tr>
						<th>Project</th>
						<th>Supplier Name</th>
						<th>Brand Name</th>
						<th>Approved Quantity</th>
						<th>Item Name</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>
					<c:if test="${not empty purchaseList}">
						<c:forEach var="purchaseItem" items="${purchaseList}">
							<tr>
								<td>${purchaseItem.aliasProjName}</td>
								<td>${purchaseItem.supplierAliasName}</td>
								<td>${purchaseItem.brandName}</td>
								<td>${purchaseItem.itemQty}</td>
								<td>${purchaseItem.itemName}</td>
  								<td><a href="/pms/emp/myview/viewPurchaseDetails/${purchaseItem.aliasProjName}?itemName=${purchaseItem.itemName}&itemType=${purchaseItem.itemType}&supplierName=${purchaseItem.supplierAliasName}&employeeId=${employeeObj.employeeId}&brandName=${purchaseItem.brandName}">Purchase Details</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<br>
			<br>
			
			<h1 style="text-align: center; color: #007399; font-size: 18px;">Items Ready For Payment</h1>
			<table id="purchaseListForPayment" class="display">
				<thead>
					<tr>
						<th>Project</th>
						<th>Supplier Name</th>
						<th>Brand Name</th>
						<th>Item Type</th>
						<th>Item Name</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>
					<c:if test="${not empty purchaseListForPayment}">
						<c:forEach var="purchaseListForPayment" items="${purchaseListForPayment}">
							<tr>
								<td>${purchaseListForPayment.aliasProjName}</td>
								<td>${purchaseListForPayment.supplierAliasName}</td>
								<td>${purchaseListForPayment.brandName}</td>
								<td>${purchaseListForPayment.itemType}</td>
								<td>${purchaseListForPayment.itemName}</td>
  								<td><a href="/pms/emp/myview/storeDetails/${employeeObj.employeeId}?aliasProjName=${purchaseListForPayment.aliasProjName}&supplierName=${purchaseListForPayment.supplierAliasName}&itemName=${purchaseListForPayment.itemName}&itemType=${purchaseListForPayment.itemType}&brandName=${purchaseListForPayment.brandName}">Make Payment</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</c:if>
		
		
		
		
		<c:if test="${employeeObj.employeeTeam eq 'Store'}">
			<h1 style="text-align: center; color: #007399; font-size: 18px;">Purchases
				that require immediate attention</h1>
			<table id="indentList" class="display">
				<thead>
					<tr>
						<th>Project</th>
						<th>Supplier Name</th>
						<th>Brand Name</th>
						<th>Item Type</th>
						<th>Item Name</th>
						<th>Status</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty purchaseList}">
						<c:forEach var="purchaseItem" items="${purchaseList}">
							<tr>
								<td>${purchaseItem.aliasProjName}</td>
								<td>${purchaseItem.supplierAliasName}</td>
								<td>${purchaseItem.brandName}</td>
								<td>${purchaseItem.itemType}</td>
								<td>${purchaseItem.itemName}</td>
								<td>${purchaseItem.supplierQuoteStatus}</td>
  								<td><a href="/pms/emp/myview/buildStoreDetail/${employeeObj.employeeId}?aliasProjName=${purchaseItem.aliasProjName}&supplierAliasName=${purchaseItem.supplierAliasName}&itemName=${purchaseItem.itemName}&supplierQuoteStatus=${purchaseItem.supplierQuoteStatus}&brandName=${purchaseItem.brandName}">Store Details</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
				
			</table>
			<br>
			<br>
			
			
		</c:if>
		
		
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>

