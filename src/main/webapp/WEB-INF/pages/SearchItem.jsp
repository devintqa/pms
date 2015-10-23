<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>PMS :: Search Item</title>
    <%@include file="Script.jsp" %>
    <%@include file="Utility.jsp" %>
    <script>
        function deleteItem(itemName, itemType) {
            $("#dialog-confirm").html(
                            itemName + " : Deletion Operation!, Please confirm to proceed");
            $("#dialog-confirm")
                    .dialog({
                        resizable: false,
                        modal: true,
                        title: "Warning!",
                        height: 200,
                        width: 400,
                        buttons: {
                            "Yes": function () {
                                $.ajax({
                                    type: 'POST',
                                    url: 'deleteItem.do',
                                    data: "itemName=" + itemName + "&itemType=" + itemType,
                                    success: function (response) {
                                        location.reload();
                                    }, error: function (error) {
                                        alert(error);
                                    }
                                });

                            },
                            "No": function () {
                                $(this).dialog('close');
                            }
                        }
                    });
        }
    </script>
</head>
<body>
<header>
    <jsp:include page="Header.jsp"/>
</header>
<div id="wrapper">
    <div>
        <h2
                style="text-align: left; font-family: arial; color: #007399; font-size: 14px;">${noDetailsFound}</h2>
    </div>
    <div class="ui-widget">
        <c:if test="${not empty items}">
            <h1 style="text-align: center; color: #007399; font-size: 24px;">Project
                Documents</h1>
            <table id="projDescDocList" class="display" width="100%">
                <thead>
                <tr>
                    <th>Item Name</th>
                    <th>Item Unit</th>
                    <th>Item Type</th>
                    <th>Action</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="item" items="${items}">
                    <tr>
                        <td>${item.itemName}</td>
                        <td>${item.itemUnit}</td>
                        <td>${item.itemType}</td>
                        <td>
                            <a href="/pms/emp/myview/updateItem/${employeeObj.employeeId}?itemName=${item.itemName}&itemUnit=${item.itemUnit}&itemType=${item.itemType}"
                               class="userAction">Update
                            </a> <strong> / </strong>
                            <a href="javascript:deleteItem('${item.itemName}','${item.itemType}')" class="userAction">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br>
            <br>
        </c:if>

    </div>
    <div id="dialog-confirm"></div>
</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>
</body>
</html>