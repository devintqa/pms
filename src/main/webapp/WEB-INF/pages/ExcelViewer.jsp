<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<%@include file="Script.jsp" %>
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/handsontable.full.js" />"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/handsontable.full.min.css" />">
<script>
	document
			.addEventListener(
					"DOMContentLoaded",
					function() {

						function getNewData() {
							return [
									[
											"Name of work:   Construction of new Kalai Arangam in Omandurar Government Estate, Chennai -2. \n",
											"", "", "", "", "", "", "", "", "",
											"", "" ],
									[ "Sl. No.", "Quantity ", "DESCRIPTION",
											" PSK NEGO ", "", " TOTAL ", "",
											"", " QTY ", "", " AMOUNT ", "" ],
									[ "", "", "", " PSK RATE ", " Psk amount ",
											" QTY ", " PART RATE ", " AMOUNT ",
											" EXCESS ", " SAVINGS ",
											" EXCESS ", " SAVINGS " ],
									[ "1", "5980.00", "Earth Work Excavation",
											" 108.00 ", " 645,840.00 ",
											" 3,399.36 ", "", " 367,130.88 ",
											" -   ", " 2,580.64 ", " -   ",
											" 278,709.12 " ],
									[ "2", "275.00", "Earth Work Excavation",
											" 55.00 ", " 15,125.00 ",
											" 270.46 ", "", " 14,875.30 ",
											" -   ", " 4.54 ", " -   ",
											" 249.70 " ],
									["=SUM(B5,E3)"]];
						}
						function initData() {
							return [
									[
											"Name of work:   Construction of new Kalai Arangam in Omandurar Government Estate, Chennai -2. \n",
											"", "", "", "", "", "", "", "", "",
											"", "" ],
									[ "Sl. No.", "Quantity ", "DESCRIPTION",
											" PSK NEGO ", "", " TOTAL ", "",
											"", " QTY ", "", " AMOUNT ", "" ] ]
						}

						var $container = $("#example1");

						$container.handsontable({
							data : initData(),
							rowHeaders : true,
							colHeaders : true,
							contextMenu : true,
							manualColumnResize : true,
							manualRowResize : true,
							currentRowClassName : 'currentRow',
							currentColClassName : 'currentCol',
							mergeCells : [],
							fixedRowsTop : 0,
							fixedColumnsLeft : 0,
							minSpareRows : 1

						});

						$("#loadData").click(
								function() {
									var hotInstance = $("#example1")
											.handsontable('getInstance');
									var hot = JSON.stringify(hotInstance
											.getData());
									console.log(hot)
									hotInstance.render();
									hotInstance.loadData(getNewData());

								});
						
						$("#saveEdition").click(
								function() {
									var hotInstance = $("#example1").handsontable('getInstance');
									var excelData = JSON.stringify(hotInstance.getData());
									console.log(excelData);
									$.ajax({
										type : "POST",
										url : "saveEdition.do",
										contentType: "application/json; charset=utf-8",
										cache : false,
									    data: JSON.stringify({ edition: excelData }),
										success : function(response) {
												var obj = JSON.parse(JSON.stringify(response));
												document.getElementById("editionData").innerHTML = obj.edition;
												
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
	<div id="example1" class="hot handsontable" style="overflow-x: scroll; width:1270px;height:400px;"></div>
	
	<input type="button" id="loadData" value="load"/>
	<input type="button" id="saveEdition" value="save" />
	<div id="savedItem"><b>console:</b>
	<p id="editionData"></p>
	</div>
</body>