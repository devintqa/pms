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
                function(){
                    fillSupplierQuoteDetails();
                }
        );

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
            i++;
            var itemQty = row.cells[i].getElementsByTagName('input')[0];
            itemQty.id += len;
            itemQty.value = item.itemQty;
            document.getElementById('supplierQuoteDetailsTable').appendChild(row);
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
                    <legend>Supplier Details</legend>
                    <table>
                        <tr>
                            <td>Project Name <span id="colon">:</span>
                            </td>
                            <td id="projName" class="tdStyle">${projName}</td>
                        </tr>
                        <tr>
                            <td>Item Name<span id="colon">:</span>
                            </td>
                            <td id="itemName" class="tdStyle">${itemName}</td>
                        </tr>
                        <tr></tr>
                    </table>
                </fieldset>

                <table id="supplierQuoteDetailsTable" class="gridView">
                    <thead>
                    <tr>
                        <th width="50px">Supplier Alias Name</th>
                        <th width="50px">Email Address</th>
                        <th width="50px">Phone Number</th>
                        <th width="50px">Quoted Price</th>
                        <th width="50px">Quantity</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input class="quoteDetailStyle" name="supplierAliasName" id="supplierAliasName" type="text"
                                   readonly="true"/></td>
                        <td><input class="quoteDetailStyle" name="emailAddress" id="emailAddress" type="text"
                                   readonly="true"/></td>
                        <td><input class="quoteDetailStyle" name="phoneNumber" id="phoneNumber" type="text"
                                   readonly="true"/></td>
                        <td><input class="quoteDetailStyle" name="quotedPrice" id="quotedPrice" type="text"
                                   readonly="true"/></td>
                        <td><input class="quoteDetailStyle" name="itemQty" id="itemQty" type="text" readonly="true"/>
                        </td>

                    </tr>
                    </tbody>
                </table>

            </center>
            <br>
            <form:hidden path="quoteDetailsValue" id="quoteDetailsValue"/>
            <form:hidden path="submittedForApproval" id="submittedForApproval"/>
        </form:form>
    </div>
    <div id="dialog-confirm"></div>
</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>

