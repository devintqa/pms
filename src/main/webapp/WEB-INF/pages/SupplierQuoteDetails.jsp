<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>

<title>PMS :: Supplier Quote Details</title>

<%@include file="Script.jsp" %>

<script>
$(document).ready(
        function () {

            $('#supplierQuoteDetailsTable').on('click', 'input[type="checkbox"]', function () {
                if ($(this).is(":checked")) {
                    $('#rejectBtn').hide();
                } else {
                    $('#rejectBtn').show();
                }
            });

            if ($('#submittedForApproval').val() == 'Y') {
                $('#submitedForApproval').hide();
                $("#supplierQuoteDetailsTable").find("input,button,textarea,select,a").attr("disabled", "disabled");
                $("#supplierQuoteDetailsTable").find("input:checkbox").attr("disabled", false);
                $("#approvedQty").attr("disabled", false);
                $("#deleteItem").hide();
            }
            $('#Submit').hide();
            var selector = "input[name = 'supplierAliasName']";
            fillSupplierQuoteDetails();
            $(document).on('keydown.autocomplete', selector, function () {
                $(this).autocomplete({
                    source: function (request, response) {
                        $.getJSON("/pms/emp/myview/dispatchTransaction/getSupplierDetails.do", {
                            supplierAliasName: request.term
                        }, function (data) {
                            response($.map(data, function (item) {
                                return {label: item.aliasName, supplierAliasName: item.aliasName, emailAddress: item.emailAddress, phoneNumber: item.phoneNumber};
                            }))
                        });
                    },
                    select: function (event, ui) {
                        var supplierAliasName = ui.item.label;
                        if (validateItemNameExistence(supplierAliasName)) {
                            alert("Supplier already exists!");
                            event.preventDefault();
                            $(this).val('');
                        } else {
                            $(this).parents('tr:first').find('td:nth-child(2) input').val(ui.item.emailAddress);
                            $(this).parents('tr:first').find('td:nth-child(4) input').val('');
                            $(this).parents('tr:first').find('td:nth-child(3) input').val(ui.item.phoneNumber);
                            $(this).parents('tr:first').find('td:nth-child(1) input:nth-child(2)').val(ui.item.supplierAliasName);

                        }
                    }
                });
            });
            <c:if test="${employeeObj.employeeRole eq 'General Manager'}">
            $('#approveBtn, #rejectBtn').attr("disabled", false);
            $('#submitedForApproval').show();
            </c:if>

            $('input[name="supplierDetail"]').change(function () {
                $('#error').text('');
            });

            $('#rejectBtn').click(function () {
                var projName = document.getElementById('projName').innerHTML.trim();
                var itemType = document.getElementById('itemType').innerHTML.trim();
                var itemName = document.getElementById('itemName').innerHTML.trim();
                var employee = $('#employeeId').val();
                $("#dialog-confirm").html("Rejection will reject all the suppliers, Please confirm");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    modal: true,
                    title: "Warning!",
                    height: 200,
                    width: 400,
                    buttons: {
                        "Yes": function () {
                            $.ajax({
                                type: 'POST',
                                url: 'rejectApproval.do',
                                data: "projName=" + projName + "&itemType=" + itemType + "&itemName=" + itemName,
                                success: function (response) {
                                    if (response.success) {
                                        $("#dialog-confirm").html(response.data);
                                        $("#dialog-confirm").dialog({
                                            modal: true,
                                            title: "Success!",
                                            height: 200,
                                            width: 350,
                                            buttons: {
                                                Ok: function () {
                                                    $(this).dialog("close");
                                                }
                                            },
                                            close: function (event, ui) {
                                                window.location = "/pms/emp/myview/" + employee;
                                            }
                                        });
                                    }
                                },
                                error: function (err) {
                                    console.log("Error deleting Project ");
                                }
                            });
                        },
                        "No": function () {
                            $(this).dialog('close');

                        }
                    }
                })

            });

            $("#approveBtn").click(function () {
                var employee = $('#employeeId').val();
                var projId = $('#projId').val();
                var supplierDetails = [];
                var dispatchDetailForm = {};
                var supplierAliasName = '';
                var itemName = '';
                var approvedQty = '';
                var supplierQuoteTable = document.getElementById('supplierQuoteDetailsTable');
                var len = supplierQuoteTable.rows.length;
                len = len - 1;
                for (var i = 1; i <= len; i++) {
                    if (supplierQuoteTable.rows[i].cells[0].getElementsByTagName('input')[0].checked) {
                        var supplierAliasName = supplierQuoteTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
                        var approvedQty = supplierQuoteTable.rows[i].cells[5].getElementsByTagName('input')[0].value;
                        var itemName = document.getElementById('itemName').innerHTML.trim();
                        var itemType = document.getElementById('itemType').innerHTML.trim();
                        var obj = new SupplierDetails(supplierAliasName, itemName, approvedQty, itemType);
                        supplierDetails.push(obj);
                    }
                }
                dispatchDetailForm["quoteDetailsValue"] = JSON.stringify(supplierDetails);
                dispatchDetailForm["projName"] = document.getElementById('projName').innerHTML.trim();
                dispatchDetailForm["itemType"] = document.getElementById('itemType').innerHTML.trim();
                dispatchDetailForm["itemName"] = document.getElementById('itemName').innerHTML.trim();

                if ($('input[name="supplierDetail"]:checked').length > 0) {
                    $.ajax({
                        type: "POST",
                        url: "supplierApproval.do",
                        contentType: "application/json",
                        cache: false,
                        data: JSON.stringify(dispatchDetailForm),
                        success: function (response) {


                            if (response.success) {
                                $("#dialog-confirm").html(response.data);
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
                                    close: function (event, ui) {
                                        window.location = "/pms/emp/myview/" + employee;
                                    }
                                });

                            } else {
                                $('#result').html(response.data);
                            }

                        }
                    });
                } else {
                    $('#result').text('Please select any Supplier to Approve');
                }

            });

        });
function insertSupplierDetailRow() {
    var supplierQuoteDetailsTable = document.getElementById('supplierQuoteDetailsTable');
    var new_row = supplierQuoteDetailsTable.rows[1].cloneNode(true);
    var len = supplierQuoteDetailsTable.rows.length;

    var supplierAliasName = new_row.cells[0].getElementsByTagName('input')[0];
    supplierAliasName.id += len;
    supplierAliasName.value = '';

    var emailAddress = new_row.cells[1].getElementsByTagName('input')[0];
    emailAddress.id += len;
    emailAddress.value = '';

    var phoneNumber = new_row.cells[2].getElementsByTagName('input')[0];
    phoneNumber.id += len;
    phoneNumber.value = '';

    var quotedPrice = new_row.cells[3].getElementsByTagName('input')[0];
    quotedPrice.id += len;
    quotedPrice.value = '';

    supplierQuoteDetailsTable.appendChild(new_row);
    $('#Submit').hide();
}

function SupplierQuoteDetails(supplierAliasName, emailAddress, phoneNumber, quotedPrice) {
    this.supplierAliasName = supplierAliasName;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.quotedPrice = quotedPrice;
}
function SupplierDetails(supplierAliasName, itemName, approvedQty, itemType) {
    this.supplierAliasName = supplierAliasName;
    this.itemName = itemName;
    this.itemQty = approvedQty;
    this.itemType = itemType;
}


function getTableData() {
    var supplierQuoteTable = document.getElementById('supplierQuoteDetailsTable');
    var itemObjArray = [];
    var dispatchDetailForm = {};
    var len = supplierQuoteTable.rows.length;
    var err = null;
    if (1 == len - 1) {
        var supplierAliasName = supplierQuoteTable.rows[1].cells[0].getElementsByTagName('input')[0].value;
        var emailAddress = supplierQuoteTable.rows[1].cells[1].getElementsByTagName('input')[0].value;
        var phoneNumber = supplierQuoteTable.rows[1].cells[2].getElementsByTagName('input')[0].value;
        var quotedPrice = supplierQuoteTable.rows[1].cells[3].getElementsByTagName('input')[0].value;
        var obj = new SupplierQuoteDetails(supplierAliasName, emailAddress, phoneNumber, quotedPrice);
        if (supplierAliasName && emailAddress && phoneNumber && quotedPrice || !(supplierAliasName && emailAddress && phoneNumber && quotedPrice)) {
            itemObjArray.push(obj);
        } else {
            err = true;
        }
    } else {
        for (i = 1; i <= len - 1; i++) {
            var supplierAliasName = supplierQuoteTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
            var emailAddress = supplierQuoteTable.rows[i].cells[1].getElementsByTagName('input')[0].value;
            var phoneNumber = supplierQuoteTable.rows[i].cells[2].getElementsByTagName('input')[0].value;
            var quotedPrice = supplierQuoteTable.rows[i].cells[3].getElementsByTagName('input')[0].value;
            var obj = new SupplierQuoteDetails(supplierAliasName, emailAddress, phoneNumber, quotedPrice);
            if (supplierAliasName && emailAddress && phoneNumber && quotedPrice || !(supplierAliasName && emailAddress && phoneNumber && quotedPrice)) {
                itemObjArray.push(obj);
            } else {
                err = true;
            }
        }
    }
    dispatchDetailForm["quoteDetailsValue"] = JSON.stringify(itemObjArray);
    dispatchDetailForm["projName"] = document.getElementById('projName').innerHTML.trim();
    dispatchDetailForm["itemType"] = document.getElementById('itemType').innerHTML.trim();
    dispatchDetailForm["itemName"] = document.getElementById('itemName').innerHTML.trim();
    dispatchDetailForm["itemQty"] = document.getElementById('itemQty').innerHTML.trim();
    dispatchDetailForm["employeeId"] = $('#employeeId').val();
    return {dispatchDetailForm: dispatchDetailForm, err: err};
}


function saveQuotePriceDetails() {
    var __ret = getTableData();
    var dispatchDetailForm = __ret.dispatchDetailForm;
    var err = __ret.err;
    if (err) {
        alert("Please make sure that all the required fields are entered.");
    } else {
        $.ajax({
            type: "POST",
            url: "saveQuoteDetails.do",
            contentType: "application/json",
            cache: false,
            data: JSON.stringify(dispatchDetailForm),
            success: function (response) {
                if (response.success) {
                    $('#Submit').show();
                } else {
                    $('#Submit').hide();
                }
                $('#result').html(response.data);
            }
        });
    }
}


function submitForApproval() {
    var __ret = getTableData();
    var employee = $('#employeeId').val();
    var dispatchDetailForm = __ret.dispatchDetailForm;
    var err = __ret.err;
    if (err) {
        alert("Please make sure that all the required fields are entered.");
    } else {
        $.ajax({
            type: "POST",
            url: "submitForApproval.do",
            contentType: "application/json",
            cache: false,
            data: JSON.stringify(dispatchDetailForm),
            success: function (response) {
                if (response.success) {
                    $('#submitedForApproval').hide();
                    $("#supplierQuoteDetailsTable").find("input,button,textarea,select").attr("disabled", "disabled");
                    $("#deleteItem").hide();
                    $("#dialog-confirm").html(response.data);
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
                        close: function (event, ui) {
                            window.location = "/pms/emp/myview/" + employee;
                        }
                    });
                } else {
                    $('#submitedForApproval').show();
                }
                $('#result').html(response.data);
            }
        });
    }
}
function validateItemNameExistence(supplierAliasName) {
    var supplierQuoteTable = document.getElementById('supplierQuoteDetailsTable');

    var len = supplierQuoteTable.rows.length;
    var exists = false;
    for (i = 1; i <= len - 1; i++) {
        var itemName = supplierQuoteTable.rows[i].cells[0].getElementsByTagName('input')[0].value;
        if (itemName == supplierAliasName) {
            exists = true;
            break;
        }
    }
    return exists;
}

function fillSupplierQuoteDetails() {
    var len = document.getElementById('supplierQuoteDetailsTable').rows.length;
    var obj = JSON.parse(document.getElementById('quoteDetailsValue').value);
    for (i = 0; i <= obj.length - 1; i++) {
        fillSupplierQuoteDetail(obj[i]);
    }
    if (obj != '') {
        document.getElementById('supplierQuoteDetailsTable').rows[1].remove();
    }
}

function fillSupplierQuoteDetail(item) {
    var row = document.getElementById('supplierQuoteDetailsTable').rows[1].cloneNode(true);
    var len = document.getElementById('supplierQuoteDetailsTable').rows.length;
    var i = 0;
    <c:if test="${employeeObj.employeeRole eq 'General Manager'}">
    i = 1;
    </c:if>
    var supplierAliasName = row.cells[i].getElementsByTagName('input')[0];
    supplierAliasName.id += len;
    supplierAliasName.value = item.supplierAliasName;
    i++;
    var emailAddress = row.cells[i].getElementsByTagName('input')[0];
    emailAddress.id += len;
    emailAddress.value = item.emailAddress;
    i++;
    var phoneNumber = row.cells[i].getElementsByTagName('input')[0];
    phoneNumber.id += len;
    phoneNumber.value = item.phoneNumber;
    i++;
    var quotedPrice = row.cells[i].getElementsByTagName('input')[0];
    quotedPrice.id += len;
    if (item.quotedPrice)
        quotedPrice.value = item.quotedPrice;

    document.getElementById('supplierQuoteDetailsTable').appendChild(row);
}

function deleteItemRow(row) {
    $('#Submit').hide();
    var supplierQuoteDetailsTable = document.getElementById('supplierQuoteDetailsTable');
    var noOfRow = document.getElementById('supplierQuoteDetailsTable').rows.length;
    if (noOfRow > 2) {
        var i = row.parentNode.parentNode;
        i.parentNode.removeChild(i);
    } else {
        document.getElementById('supplierQuoteDetailsTable').rows[1].cells[0].getElementsByTagName('input')[0].value = '';
        document.getElementById('supplierQuoteDetailsTable').rows[1].cells[1].getElementsByTagName('input')[0].value = '';
        document.getElementById('supplierQuoteDetailsTable').rows[1].cells[2].getElementsByTagName('input')[0].value = '';
        document.getElementById('supplierQuoteDetailsTable').rows[1].cells[3].getElementsByTagName('input')[0].value = '';
    }
}

</script>

<style>
    .quoteDetailStyle {
        width: 200px;
    }

    .tdStyle {
        font-weight: bold;
        padding-left: 10px;
    }
</style>
</head>

<body>
<header>
    <jsp:include page="Header.jsp"/>
</header>
<div id="wrapper">
    <div>
        <form:form method="POST" commandName="quoteDetailsForm" id="quoteDetailsForm">
            <center>
                <fieldset style="margin: 1em; text-align: left;">
                    <legend>Quote Details</legend>
                    <table>
                        <tr>
                            <td>Project Name <span id="colon">:</span>
                            </td>
                            <td id="projName" class="tdStyle">${projName}</td>
                        </tr>

                        <tr>
                            <td>Item Type<span id="colon">:</span>
                            </td>
                            <td id="itemType" class="tdStyle">${itemType}</td>
                        </tr>
                        <tr>
                            <td>Item Name<span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${itemName}</td>
                        </tr>
                        <tr>
                            <td>Item Quantity<span id="colon">:</span>
                            </td>
                            <td id="itemQty" class="tdStyle">${itemQty}</td>
                        </tr>
                        <tr></tr>
                    </table>
                </fieldset>

                <table id="supplierQuoteDetailsTable" class="gridView">
                    <thead>
                    <tr>
                        <c:if test="${employeeObj.employeeRole eq 'General Manager'}">
                            <th width="50px">Select</th>
                        </c:if>

                        <th width="50px">Supplier Alias Name</th>
                        <th width="50px">Email Address</th>
                        <th width="50px">Phone Number</th>
                        <th width="50px">Quoted Price</th>
                        <th width="50px">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <c:if test="${employeeObj.employeeRole eq 'General Manager'}">
                            <td><input type="checkbox" name="supplierDetail" id="supplierDetail"/></td>
                        </c:if>

                        <td><input class="quoteDetailStyle" name="supplierAliasName" id="supplierAliasName" type="text"
                                   placeholder="Enter Supplier Name"/></td>
                        <td><input class="quoteDetailStyle" name="totalQuantity" id="emailAddress" type="text"
                                   readonly="true"/></td>
                        <td><input class="quoteDetailStyle" name="totalQuantity" id="phoneNumber" type="text"
                                   readonly="true"/></td>
                        <td><input class="quoteDetailStyle" name="quotedPrice" id="quotedPrice" type="text"
                                   placeholder="Enter Quoted Price"/></td>

                        <c:choose>
                            <c:when test="${employeeObj.employeeRole eq 'General Manager'}">
                                <td><input class="quoteDetailStyle" name="approvedQty" id="approvedQty" type="text"
                                           placeholder="Enter Approving Quantity"/></td>
                            </c:when>
                            <c:otherwise>
                                <td align="center"><a class="quoteDetailStyle" id="deleteItem"
                                                      onclick="deleteItemRow(this)">
                                    <img src="<c:url value="/resources/images/delete.png" />"/></a></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    </tbody>
                </table>

                <table id="submitedForApproval">
                    <tr>
                        <td></td>
                        <br>

                        <div id="result"
                             style="text-align: left; font-family: arial; color: #007399; font-size: 16px;"></div>
                        <br>
                        <c:choose>
                            <c:when test="${employeeObj.employeeRole eq 'General Manager'}">
                                <td><input id="approveBtn" class="button" type="button" value="Approve"/></td>
                                <td><input id="rejectBtn" class="button" type="button" value="Reject"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><input class="button" type="button" value="Add"
                                           onclick="insertSupplierDetailRow()"/></td>
                                <td><input class="button" type="button" value="Save" onclick="saveQuotePriceDetails()"/>
                                </td>
                                <td id="Submit"><input class="button" type="button" value="Submit For Approval"
                                                       onclick="submitForApproval()"/></td>
                            </c:otherwise>
                        </c:choose>
                        <td></td>
                    </tr>
                </table>
            </center>
            <br>
            <form:hidden path="quoteDetailsValue" id="quoteDetailsValue"/>
            <form:hidden path="submittedForApproval" id="submittedForApproval"/>
            <form:hidden path="employeeId" id="employeeId"/>
        </form:form>
    </div>
    <div id="dialog-confirm"></div>
</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>

