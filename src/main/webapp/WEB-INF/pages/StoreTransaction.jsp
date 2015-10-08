<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>

    <title>PMS :: Store Details</title>

    <%@include file="Script.jsp" %>
    <script>
        $(document).ready(function () {
            if ($('#projId').val() == 0) {
                $("#itemNameField").hide();
            }


            $("#itemName").change(function () {
                var projId = $('#projId').val();
                var itemName = $('#itemName').val();
                $.ajax({
                    type: "GET",
                    url: "getTotalQuantity.do",
                    cache: false,
                    data: "projId=" + projId + "&itemName=" + itemName,
                    success: function (response) {
                        $("#totalQuantity").val(response);
                    }
                });
            });
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
                            var obj = jQuery.parseJSON(response.object);
                            var options = '';
                            var fieldUserOptions = '';
                            $.each(obj, function (key, value) {
                                if (key == 'items') {
                                    $.each(value, function (index, attrvalue) {
                                        options = options + '<option value="' + attrvalue + '">'
                                                + attrvalue + '</option>';
                                    });
                                }
                                if (key == 'fieldUsers') {
                                    $.each(value, function (index, attrvalue) {
                                        fieldUserOptions = fieldUserOptions + '<option value="' + attrvalue + '">'
                                                + attrvalue + '</option>';
                                    });
                                }
                            });
                            $('#itemName').html(options);
                            $('#fieldUser').html(fieldUserOptions);
                        } else {
                            $("#itemNameField").hide();
                            $("#projId").prop('selectedIndex', 0);
                            $("#dialog-confirm").html(response.data);
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
    <jsp:include page="Header.jsp"/>
</header>
<div id="wrapper">
    <div>
        <h2
                style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${successMessage}</h2>

        <h2
                style="text-align: left; font-family: arial; color: red; font-size: 14px;">${errorMessage}</h2>

    </div>
    <div>
        <form:form method="POST" commandName="dispatchDetailForm"
                   action="saveDispatchedDetail.do">
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
                            <td><form:errors path="projId" cssClass="error"/></td>


                        </tr>
                        <table id="itemNameField">
                            <tr>
                                <td>Field User<span id="colon">:</span>
                                </td>
                                <td><form:select path="fieldUser" cssClass="inputText"
                                                 id="fieldUser">
                                    <option value="${storeDetailForm.fieldUser}"
                                            selected="selected">${storeDetailForm.fieldUser}</option>

                                </form:select>
                                </td>
                            </tr>
                            <tr>
                                <td>Item Name <span id="colon">:</span>
                                </td>
                                <td><form:select path="itemName" cssClass="inputText"
                                                 id="itemName">
                                    <option value="${storeDetailForm.itemName}"
                                            selected="selected">${storeDetailForm.itemName}</option>
                                </form:select>
                                </td>
                                <td>Total Quantity<span id="colon">:</span>
                                </td>
                                <td><form:input path="totalQuantity"
                                                cssClass="inputText" readonly="true"/></td>
                                <td><form:errors path="totalQuantity" cssClass="error"/></td>
                                <td>Requested Quantity<span id="colon">:</span>
                                </td>
                                <td><form:input path="requestedQuantity"
                                                placeholder="Enter Requested Quantity" cssClass="inputText"/></td>
                                <td><form:errors path="requestedQuantity"
                                                 cssClass="error"/></td>
                            </tr>
                        </table>

                        <tr></tr>
                    </table>
                </fieldset>
                <form:hidden path="employeeId"/>

                <table>
                    <tr>
                        <td></td>
                        <td><input class="button" type="submit"/></td>
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
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>

