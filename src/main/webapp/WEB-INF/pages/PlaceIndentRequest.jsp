<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Build Indent</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
<script>
    var windowObjectReference = null;

    function popUpClosed() {
        if (windowObjectReference) {
            window.location.reload();
        }
    }

        $(document).ready(function () {
            $("#showSearchProjectDesc").hide();
            $("#aliasProjectName").autocomplete({
                source: function (request, response) {
                    var empId = $('#employeeId').val();
                        $.getJSON("/pms/emp/myview/searchProjectDescription/searchProject.do",{
                            term: request.term,
                            employeeId : empId
                        }, response);
                }
            });

            $("#rejectIndentRequest").click(function() {
                var indentId = $('#indentId').val();
                var employeeId = $('#employeeId').val();
                var indentStatus = 'REJECTED';
                $.ajax({
    				type : "POST",
    				url : "placeIndentRequest.do",
    				cache : false,
    				data : "indentId="+indentId+"&employeeId="+employeeId+"&indentStatus="+indentStatus,
    				success : function(response) {
    					 $("#dialog-confirm").html(response);
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
    						 window.location = "/pms/emp/myview/"+employeeId;
    					 }
                         });
    					
    				}
    			});
            });
        
        $("#placeIndentRequest").click(function() {
            var indentId = $('#indentId').val();
            var employeeId = $('#employeeId').val();
            var indentStatus = $(this).attr('aria-indent-status');
            $.ajax({
				type : "POST",
				url : "placeIndentRequest.do",
				cache : false,
				data : "indentId="+indentId+"&employeeId="+employeeId+"&indentStatus="+indentStatus,
				success : function(response) {
					 $("#dialog-confirm").html(response);
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
						 window.location = "/pms/emp/myview/"+employeeId;
					 }
                     });
					
				}
			});
        });

    });


</script>
</head>
<body>
	<header>
		<jsp:include page="Header.jsp" />
	</header>
	<div id="wrapper">

		<div class="ui-widget">
			<form:form id="indentDesc" method="POST" commandName="indentDesc" action="placeIndentRequest.do">
	
			<c:if test="${indentItemSize gt 0}">
				<h1 style="text-align: center; color: #007399; font-size: 24px;">Indent Item Details</h1>
				<table id="indentList" class="display">
					<thead>
						<tr>
							<th>Item Name</th>
							<th>Item Type</th>
							<th>Item Qty</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty indentItems}">
							<c:forEach var="indent" items="${indentItems}">
								<tr>
									<td>${indent.itemName}</td>
									<td>${indent.itemType}</td>
									<td>${indent.itemQty}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<br>
				<input type="hidden" id="indentId" value="${indentDesc.indentId}"/>
				<input type="hidden" id="employeeId" value="${employeeId}"/>
				<center>
				
				<c:if test="${indentStatus eq 'NEW'}">
					<input class="button" aria-indent-status="${indentStatus}" type="button" id="placeIndentRequest" value="Place Request"/>
				</c:if>
				
				<c:if test="${(employeeObj.employeeRole eq 'Technical Manager') or (employeeObj.employeeRole eq 'General Manager') and (indentStatus ne 'PENDING PURCHASE')}">
					<input class="button" aria-indent-status="${indentStatus}" type="button" id="placeIndentRequest" value="Approve"/>
					
					<input class="button" aria-indent-status="${indentStatus}" type="button" id="rejectIndentRequest" value="Reject"/>
				</c:if>
				
				</center>
				<br>
			</c:if>
			</form:form>
		</div>
		 <div id="dialog-confirm"></div>
	</div>
	<footer>
		<jsp:include page="Footer.jsp" />
	</footer>
</body>
</html>