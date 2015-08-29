<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
    <head>
        <title>PMS :: Update Deposit Detail</title>
        <%@include file="Script.jsp" %>
        <%@include file="Utility.jsp" %>
    </head>
    <script>
    	function deleteDeposit(depositId) {
        		$.ajax({
        			type : 'POST',
        			url : 'deleteDeposit.do',
        			data : "depositId="+depositId,
        			success : function(response) {
        				location.reload();
    					console.log("Successfully deleted row ");
        			},
        			error : function(err) {
        				console.log("Error deleting Deposit");
        			}
        		});
     	}
    </script>
    <body>
	    <header>
		    <jsp:include page="Header.jsp" />
	    </header>
	    <div id="wrapper">
	    	<c:if test="${depositDetailsSize gt 0}">
        		<h1 style="text-align: center; color: #007399; font-size: 24px;">Deposit Details</h1>
	        	<table id="emdList" class="gridView">
            		<thead>
            			<tr>
            				<th>Project Name</th>
            				<th>Sub Project Name</th>
            				<th>Deposit Type</th>
            		    	<th>Deposit Amount</th>
            				<th>Deposit Start Date</th>
            				<th>Deposit End Date</th>
            				<th>Action</th>
            			</tr>
            		</thead>
					<tbody>
						<c:if test="${not empty depositDetails}">
							<c:forEach var="detail" items="${depositDetails}">
								<tr>
								    <td>${detail.aliasProjectName}</td>
								    <td>${detail.aliasSubProjectName}</td>
									<td>${detail.depositType}</td>
									<td>${detail.depositAmount}</td>
									<td>${detail.depositStartDate}</td>
									<td>${detail.depositEndDate}</td>
									<td><a href="/pms/emp/myview/buildDepositDetail/${employeeObj.employeeId}?depositId=${detail.depositId}&action=updateDepositDetail&aliasProjectName=${emd.aliasProjectName}&aliasSubProjectName=${emd.aliasSubProjectName}"
                                        class="userAction">Update</a>
									<strong> / </strong>
									<a id ="deleteRow" href ="javascript:deleteDeposit('${detail.depositId}');" style="color:red"> Delete</a>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
	        	</table>
	        </c:if>
	    </div>
        <footer>
		    <jsp:include page="Footer.jsp" />
        </footer>
</body>
</html>