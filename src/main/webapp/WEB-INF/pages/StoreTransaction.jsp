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
$(document).ready(function () {
    $("#itemNameField").hide();
    $("#projId").change(function () {
        var projId = $('#projId').val();
        var employeeId = $('#employeeId').val();
        $.ajax({
            type: "GET",
            url: "getItemNamesInStore.do",
            cache: false,
            data: "projId=" + projId + "&employeeId=" + employeeId,
            success: function (response) {
                if (response.success) {
                    var obj = jQuery.parseJSON(response.data);
                    var options = '';
                    for (var key in obj) {
                        var attrName = key;
                        var attrValue = obj[key];
                        options = options + '<option value="' + attrValue + '">'
                                + attrValue + '</option>';
                    }
                    $('#itemName').html(options);
                } else {
                    $("#itemNameField").hide();
                    $("#dialog-confirm").html("Items are not present in the Store");
                    $("#dialog-confirm").dialog({
                        modal: true,
                        title: "Warning!",
                        height: 200,
                        width: 300,
                        buttons: {
                            Ok: function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                    $("#itemType").prop('selectedIndex', 0);
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
								<td>
									<div id="itemNameField">
										Item Name <span id="colon">:</span>
										<form:select path="itemName" cssClass="inputText"
											id="itemName">

											<option value="${storeDetailForm.itemName}"
												selected="selected">${storeDetailForm.itemName}</option>

										</form:select>
									</div>
								</td>
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

