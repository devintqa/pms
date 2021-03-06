<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <title>PMS :: My Profile</title>

    <%@include file="Script.jsp" %>
    <script src="<c:url value="/resources/js/jquery-picklist.js" />"></script>
    <script>
        $(function () {
            $("#user").pickList();
            $('#projectId').change(function () {
                $("#teamName").prop('selectedIndex', 0);
                $('.pickList_sourceList li').remove();
                $('.pickList_targetList li').remove();
            });
            $('#teamName').change(function () {

                $('.pickList_sourceList li').remove();
                $('.pickList_targetList li').remove();
                var data = document.getElementById("privilegeDetails").value;
                var projectId = $('#projectId').val();
                var employee = $('#employee').val();
                var teamName = $('#teamName').val();

                $.ajax({
                    type: "GET",
                    url: "getProjectUserPrivilege.do",
                    cache: false,
                    data: "employeeId=" + employee + "&projectId=" + projectId + "&teamName=" + teamName,
                    success: function (response) {
                        if (response.success) {
                            var data = $.parseJSON(response.data);
                            $("#user").pickList();
                            $.each(data, function (i, item) {
                                $("#user").pickList("insert", {
                                    value: item.value,
                                    label: item.label,
                                    selected: item.selected
                                });
                            });
                        } else {
                            $("#dialog-confirm").html(response.data);
                            $("#dialog-confirm").dialog({
                                modal: true,
                                title: "Warning!",
                                height: 200,
                                width: 350,
                                buttons: {
                                    Ok: function () {
                                        $(this).dialog("close");
                                    }
                                }
                            });
                            $("#teamName").prop('selectedIndex', 0);
                            return;
                        }
                    }
                });
            });
        });


        function updateConsole() {
            var susers = [];
            var ausers = [];
            var employee = $('#employee').val();
            var projectId = $('#projectId').val();
            var teamName = $('#teamName').val();
            var selectedUsers = $('.pickList_targetList li');
            $.each(selectedUsers, function () {
                susers.push($(this).attr("label"));
            });
            var availableUsers = $('.pickList_sourceList li');
            $.each(availableUsers, function () {
                ausers.push($(this).attr("label"));
            });
            console.log(susers);

            $.ajax({
                type: "POST",
                url: "saveProjectUserPrivilege.do",
                cache: false,
                data: "employeeId=" + employee + "&toAuthorize=" + susers + "&projectId=" + projectId
                        + "&teamName=" + teamName + "&toDeAuthorize=" + ausers,
                success: function (response) {
                    if (response.success) {
                        $("#dialog-confirm").html("Project Authorization Successful.");
                        $("#dialog-confirm").dialog({
                            modal: true,
                            title: "Success!",
                            height: 200,
                            width: 350,
                            buttons: {
                                Ok: function () {
                                    $(this).dialog("close");
                                }
                            }
                        });
                        return;
                    } else {
                        $("#dialog-confirm").html(response.data);
                        $("#dialog-confirm").dialog({
                            modal: true,
                            title: "Warning!",
                            height: 200,
                            width: 350,
                            buttons: {
                                Ok: function () {
                                    $(this).dialog("close");
                                }
                            }
                        });
                        return;
                    }
                }
            });
        }
    </script>
    <style>
        .pickList_sourceListContainer, .pickList_controlsContainer, .pickList_targetListContainer {
            float: left;
            margin: 0.25em;
        }

        .pickList_controlsContainer {
            text-align: center;
        }

        .pickList_controlsContainer button {
            display: block;
            width: 100%;
            text-align: center;
        }

        .pickList_list {
            list-style-type: none;
            margin: 0;
            padding: 0;
            float: left;
            width: 150px;
            height: 200px;
            border: 1px inset #eee;
            overflow-y: auto;
            cursor: default;
        }

        .pickList_selectedListItem {
            background-color: #a3c8f5;
        }

        .pickList_listLabel {
            font-size: 0.9em;
            font-weight: bold;
            text-align: center;
        }

        .pickList_clear {
            clear: both;
        }


    </style>
</head>

<body>
<header>
    <jsp:include page="Header.jsp"/>
</header>
<div id="wrapper">
    <div>
        <div>
            <h2
                    style="text-align: left; font-family: arial; color: #FF0000; font-size: 14px;">${authoriseMessage}</h2>
            <br>
        </div>
        <h1 style="text-align: center; color: #007399; font-size: 24px;">Project Authorization</h1>
        <br>


        <form:form id="authorize" method="POST" commandName="authorize">
				<table align="center">
					<tr>
						<td>
							<table>
								<tr>
									<td colspan="4">Project<span id="colon">:</span></td>
									<td><form:select id="projectId" name="project"
											items="${projectList}" path="projectName"
											cssClass="inputText">
										</form:select></td>
								</tr>
								<tr>
									<td colspan="4">Team:<span id="colon">:</span></td>
									<td><form:select path="teamName" cssClass="inputText"
											items="${teamList}" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
									<td colspan="2"><br></td>
								</tr>
								<tr>
									<td colspan="2">
										<div>
											<select id="user" name="user" multiple="multiple">
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2"><br></td>
								</tr>
								<tr>
									<td></td>
									<td><input type="button" class="button" value="Authorize"
										onclick="updateConsole()" /></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>

				<form:hidden path="privilegeDetails" id="privilegeDetails"/>
            <form:hidden path="employeeId" id="employee"/>

        </form:form>
    </div>
    <div id="dialog-confirm"></div>
</div>
<footer>
    <jsp:include page="Footer.jsp"/>
</footer>


</body>
</html>


