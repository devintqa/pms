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

        $(document).ready(
                function () {
                    if ($('#projId').val() == 0) {
                        $("#itemNameField").hide();
                        $("#dispatchDetailTable").hide();
                    }
                    var selector = "input[name = 'itemName']";
                    $(document).on('keydown.autocomplete', selector, function () {
                        $(this).autocomplete({
                            source: function (request, response) {
                                var projId = $('#projId').val();
                                $.getJSON("/pms/emp/myview/dispatchTransaction/getItemNamesInStore.do", {
                                    itemName: request.term,
                                    projId: projId
                                }, function (data) {
                                    response($.map(data, function (item) {
                                        return { label: item.itemName, value: item.itemName, totalQuantity: item.totalQuantity, projId: item.itemName };
                                    }))
                                });
                            },
                            select: function (event, ui) {
                                var newItemName = ui.item.label;
                                if (validateItemNameExistence(newItemName)) {
                                    alert("Item already exists!");
                                    event.preventDefault();
                                    $(this).val('');
                                } else {
                                    $(this).parents('tr:first').find('td:nth-child(2) input').val(ui.item.totalQuantity);
                                    $(this).parents('tr:first').find('td:nth-child(3) input').val('');
                                    $(this).parents('tr:first').find('td:nth-child(1) input:nth-child(2)').val(ui.item.itemName);

                                }
                            }
                        });
                    });
                    $("#projId").change(
                            function () {
                                var projId = $('#projId').val();
                                var employeeId = $('#employeeId').val();
                                $.ajax({
                                    type: "GET",
                                    url: "getFieldUsers.do",
                                    cache: false,
                                    data: "projId=" + projId + "&employeeId=" + employeeId,
                                    success: function (response) {
                                        if (response.success) {
                                            var obj = jQuery
                                                    .parseJSON(response.object);
                                            var options = '';
                                            for (var key in obj) {
                                                var attrName = key;
                                                var attrValue = obj[key];
                                                options = options + '<option value="' + attrValue + '">'
                                                        + attrValue + '</option>';
                                            }
                                            $('#fieldUser').html(options);
                                        } else {
                                            $("#itemNameField").hide();
                                            $("#dispatchDetailTable").hide();
                                            $("#projId").prop('selectedIndex', 0);
                                            $("#dialog-confirm").html(response.data);
                                            $("#dialog-confirm").dialog(
                                                    {
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
                                $("#dispatchDetailTable").show();
                            });
                });
        function insertDispatchDetailRow() {
            var dispatchDetailTable = document.getElementById('dispatchDetailTable');
            var new_row = dispatchDetailTable.rows[1].cloneNode(true);
            var len = dispatchDetailTable.rows.length;

            var itemName = new_row.cells[0].getElementsByTagName('input')[0];
            itemName.id += len;
            itemName.value = '';

            var totalQuantity = new_row.cells[1].getElementsByTagName('input')[0];
            totalQuantity.id += len;
            totalQuantity.value = '';

            var requestedQuantity = new_row.cells[2].getElementsByTagName('input')[0];
            requestedQuantity.id += len;
            requestedQuantity.value = '';

            dispatchDetailTable.appendChild(new_row);
        }

        function DispatchItems(itemName, totalQuantity, requestedQuantity) {
            this.itemName = itemName;
            this.totalQuantity = totalQuantity;
            this.requestedQuantity = requestedQuantity;
        }


        function saveDispatchedItem() {
            var itemTable = document.getElementById('dispatchDetailTable');
            var itemObjArray = [];
            var dispatchDetailForm = {};
            var len = itemTable.rows.length;
            var err = null;
            if (1 == len - 1) {
                var itemName = itemTable.rows[1].cells[0].getElementsByTagName('input')[0].value;
                var totalQuantity = itemTable.rows[1].cells[1].getElementsByTagName('input')[0].value;
                var requestedQuantity = itemTable.rows[1].cells[2].getElementsByTagName('input')[0].value;
                var obj = new DispatchItems(itemName, totalQuantity, requestedQuantity);
                if (itemName && totalQuantity && requestedQuantity || !(itemName && totalQuantity && requestedQuantity)) {
                    itemObjArray.push(obj);
                } else {
                    err = true;
                }
            } else {
                for (i = 1; i <= len - 1; i++) {
                    var itemName = itemTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
                    var totalQuantity = itemTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
                    var requestedQuantity = itemTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
                    var obj = new DispatchItems(itemName, totalQuantity, requestedQuantity);
                    if (itemName && totalQuantity && requestedQuantity || !(itemName && totalQuantity && requestedQuantity)) {
                        itemObjArray.push(obj);
                    } else {
                        err = true;
                    }
                }
            }
            dispatchDetailForm["dispatchItemsValue"] = JSON.stringify(itemObjArray);
            dispatchDetailForm["employeeId"] = document.getElementById('employeeId').value;
            dispatchDetailForm["projId"] = document.getElementById('projId').value;
            dispatchDetailForm["fieldUser"] = document.getElementById('fieldUser').value;
            if (err) {
                alert("Please make sure that all the required fields are entered.");
            } else {
                $.ajax({
                    type: "POST",
                    url: "saveDispatchedDetail.do",
                    contentType: "application/json",
                    cache: false,
                    data: JSON.stringify(dispatchDetailForm),
                    success: function (data) {
                        $('#result').html(data);
                    }
                });
            }
        }
        function validateItemNameExistence(newItemName) {
            var itemTable = document.getElementById('dispatchDetailTable');

            var len = itemTable.rows.length;
            var exists = false;
            for (i = 1; i <= len - 1; i++) {
                var itemName = itemTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
                if (itemName == newItemName) {
                    exists = true;
                    break;
                }
            }
            return exists;
        }

        function deleteItemRow(row) {
            var itemTable = document.getElementById('dispatchDetailTable');
            var noOfRow = document.getElementById('dispatchDetailTable').rows.length;
            if (noOfRow > 2) {
                var i = row.parentNode.parentNode.rowIndex;
                document.getElementById('dispatchDetailTable').deleteRow(i);
            } else {
                document.getElementById('dispatchDetailTable').rows[1].cells[0].getElementsByTagName('input')[0].value = '';
                document.getElementById('dispatchDetailTable').rows[1].cells[1].getElementsByTagName('input')[0].value = '';
                document.getElementById('dispatchDetailTable').rows[1].cells[2].getElementsByTagName('input')[0].value = '';
            }
        }
    </script>
</head>

<body>
<header>
    <jsp:include page="Header.jsp"/>
</header>
<div id="wrapper">
    <div>
        <form:form method="POST" commandName="dispatchDetailForm" id="dispatchDetailForm">
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
                                                 id="fieldUser" items="${fieldUsers}">
                                    <option value="${fieldUser}" selected="selected">${fieldUser}</option>

                                </form:select></td>
                            </tr>
                            <tr>
                                <td>Dispatched Date<span id="colon">:</span>
                                </td>
                                <td><form:input path="dispatchedDate"
                                                placeholder="DD-MM-YYYY" cssClass="inputText"/></td>
                                <td><form:errors path="dispatchedDate" cssClass="error"/></td>
                            </tr>

                        </table>

                        <tr></tr>
                    </table>
                </fieldset>

                <table id="dispatchDetailTable" class="gridView">
                    <thead>
                    <tr>
                        <th width="50px">Item Name</th>
                        <th width="50px">Total Quantity</th>
                        <th width="50px">Requested Quantity</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input name="itemName" id="itemName" type="text" placeholder="Enter Item Name"/>
                        <td><input name="totalQuantity" id="totalQuantity" type="text" readonly="true"/></td>
                        <td><input name="requestedQuantity" id="requestedQuantity" type="text"
                                   placeholder="Enter Requested Quantity"/></td>
                        <td><a id="deleteItem" onclick="deleteItemRow(this)">
                            <img src="<c:url value="/resources/images/delete.png" />"/></a></td>
                    </tr>
                    </tbody>
                </table>

                <form:hidden path="employeeId"/>

                <table>
                    <tr>
                        <td></td>
                        <br>

                        <div id="result"
                             style="text-align: left; font-family: arial; color: #007399; font-size: 16px;"></div>
                        <br>
                        <td><input class="button" type="button" value="Add" onclick="insertDispatchDetailRow()"/></td>
                        <td><input class="button" type="button" value="Submit" onclick="saveDispatchedItem()"/></td>
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

