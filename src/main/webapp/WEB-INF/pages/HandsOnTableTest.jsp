<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>PMS :: Search Project</title>
<%@include file="Script.jsp" %>
<%@include file="Utility.jsp" %>
<link href="http://handsontable.com//styles/main.css" rel="stylesheet">
<link href="http://handsontable.com//bower_components/handsontable/dist/handsontable.full.min.css" rel="stylesheet">
<script src="http://handsontable.com//bower_components/handsontable/dist/handsontable.full.min.js"></script>
<script src="http://handsontable.com//bower_components/ruleJS/dist/full/ruleJS.all.full.js"></script>
<script src="http://handsontable.com//bower_components/handsontable-ruleJS/src/handsontable.formula.js"></script>

<style type="text/css">
body {background: white; margin: 20px;}
h2 {margin: 20px 0;}
</style>
  <script>
  $(document).ready(function () {

	  var container = document.getElementById('personal_example');
	  
	  var headerRenderer = function (instance, td, row, col, prop, value, cellProperties) {
	    Handsontable.renderers.TextRenderer.apply(this, arguments);
	    td.style.fontWeight = 'bold';
	    td.style.textAlign = 'center';
	  };
	  
	  var diffRenderer = function (instance, td, row, col, prop, value, cellProperties) {
	    Handsontable.cellTypes['formula'].renderer.apply(this, arguments);
	    td.style.backgroundColor = '#c3f89c';
	    td.style.fontWeight = (col === 13 ? 'bold' : 'normal');
	  };
	  
	  var incomeOrExpensesRenderer = function (instance, td, row, col, prop, value, cellProperties) {
	    Handsontable.renderers.TextRenderer.apply(this, arguments);
	    td.style.fontWeight = 'bold';
	    td.style.textAlign = 'left';
	    td.style.backgroundColor = '#BDD7EE'
	  };
	  
	  var boldAndAlignRenderer = function (instance, td, row, col, prop, value, cellProperties) {
	    Handsontable.renderers.TextRenderer.apply(this, arguments);
	    td.style.fontWeight = 'bold';
	    td.style.verticalAlign = 'middle';
	    td.style.textAlign = 'left';
	  };
	  
	  var personalData = [
	    ["","JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC","Total"],
	    ["Total Incomes","=SUM(B7:B12)","=SUM(C7:C12)","=SUM(D7:D12)","=SUM(E7:E12)","=SUM(F7:F12)","=SUM(G7:G12)","=SUM(H7:H12)","=SUM(I7:I12)","=SUM(J7:J12)","=SUM(K7:K12)","=SUM(L7:L12)","=SUM(M7:M12)","=SUM(B2:M2)"],
	    ["Total Expenses","=SUM(B17:B43)","=SUM(C17:C43)","=SUM(D17:D43)","=SUM(E17:E43)","=SUM(F17:F43)","=SUM(G17:G43)","=SUM(H17:H43)","=SUM(I17:I43)","=SUM(J17:J43)","=SUM(K17:K43)","=SUM(L17:L43)","=SUM(M17:M43)","=SUM(B3:M3)"],
	    ["NET (Income - Expenses)",'=B2-B3',"=C2-C3","=D2-D3","=E2-E3","=F2-F3","=G2-G3","=H2-H3","=I2-I3","=J2-J3","=K2-K3","=L2-L3","=M2-M3","=N2-N3", ""],
	    ["","","","","","","","","","","","","",""],
	    ["Income","","","","","","","","","","","","",""],
	    ["Salary",11370,11370,11370,11370,11370,11370,11370,11370,11370,11370,11370,11370,""],
	    ["Interest income",56,56,56,56,56,56,56,56,56,56,56,56,""],
	    ["Public assistance","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ",""],
	    ["Dividends","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ","-  ",""],
	    ["Gifts",300,300,300,300,300,300,300,300,300,300,300,300,""],
	    ["Other",1200,1200,1200,1200,1200,1200,1200,1200,1200,1200,1200,1200,""],
	    ["","","","","","","","","","","","","",""],
	    ["Expenses","","","","","","","","","","","","",""],
	    ["Living","","","","","","","","","","","","",""],
	    ["","","","","","","","","","","","","",""],
	    ["Rent/Mortgage",3200,3200,3200,3200,3200,3200,3200,3200,3200,3200,3200,3200,""],
	    ["Electricity",160,160,160,160,160,160,160,160,160,160,160,160,""],
	    ["Water/Gas/Sewer",80,80,80,80,80,80,80,80,80,80,80,80,""],
	    ["TV/Internet/Phone",50,50,50,50,50,50,50,50,50,50,50,50,""],
	    ["Maintenance",260,260,260,260,260,260,260,260,260,260,260,260,""],
	    ["Obligations","","","","","","","","","","","","",""],
	    ["","","","","","","","","","","","","",""],
	    ["Loans",1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,1500,""],
	    ["Credit cards",120,120,120,120,120,120,120,120,120,120,120,120,""],
	    ["Taxes","450","450","450","450","450","450","450","450","450","450","450","450",""],
	    ["Insurance","140","140","140","140","140","140","140","140","140","140","140","140",""],
	    ["Daily expenses","","","","","","","","","","","","",""],
	    ["","","","","","","","","","","","","",""],
	    ["Food",1200,1200,1200,1200,1200,1200,1200,1200,1200,1200,1200,1200,""],
	    ["Clothing",350,350,350,350,350,350,350,350,350,350,350,350,""],
	    ["Personal supplies",120,120,120,120,120,120,120,120,120,120,120,120,""],
	    ["Health care",320,320,320,320,320,320,320,320,320,320,320,320,""],
	    ["Education",540,540,540,540,540,540,540,540,540,540,540,540,""],
	    ["Entertainment",210,210,210,210,210,210,210,210,210,210,210,210,""],
	    ["Transportation",220,220,220,220,220,220,220,220,220,220,220,220,""],
	    ["Other","","","","","","","","","","","","",""],
	    ["","","","","","","","","","","","","",""],
	    ["Donations",80,80,80,80,80,80,80,80,80,80,80,80,""],
	    ["Savings",500,500,500,500,500,500,500,500,500,500,500,500,""],
	    ["Gifts",200,200,200,200,200,200,200,200,200,200,200,200,""],
	    ["Retirement",800,800,800,800,800,800,800,800,800,800,800,800,""],
	    ["Other",150,150,150,150,150,150,150,150,150,150,150,150,""]
	  ];
	  
	  var hot = new Handsontable(container, {
	    data: personalData,
	    height: 396,
	    fixedRowsTop: 1,
	    colHeaders: true,
	    rowHeaders: true,
	    formulas: true,
	    comments: true,
	    colWidths: [200, 85, 85, 85, 85, 85, 85, 85, 85, 85, 85, 85, 85, 85],
	    
	    cells: function (row, col, prop) {
	      var cellProperties = {};
	      
	      if (row === 0) {
	        cellProperties.renderer = headerRenderer;
	      } else if (row === 3) {
	        cellProperties.renderer = diffRenderer;
	      } else if (row === 5) {
	        cellProperties.renderer = incomeOrExpensesRenderer;
	      } else if (row === 13) {
	        cellProperties.renderer = incomeOrExpensesRenderer;
	      } else if (row === 14) {
	        cellProperties.renderer = boldAndAlignRenderer;
	      } else if (row === 21) {
	        cellProperties.renderer = boldAndAlignRenderer;
	      } else if (row === 27) {
	        cellProperties.renderer = boldAndAlignRenderer;
	      } else if (row === 36) {
	        cellProperties.renderer = boldAndAlignRenderer;
	      }
	      
	      if ([1, 2, 3].indexOf(row) !== -1 && col >= 1) {
	        cellProperties.readOnly = true;
	      }
	      
	      if ([1, 2, 3, 6, 7, 8, 9, 10, 11, 16, 17, 18, 19, 20, 23, 24, 25, 26, 29, 30, 31, 32, 33, 34, 35, 38, 39, 40, 41, 42].indexOf(row) !== -1 && col >= 1) {
	        cellProperties.type = 'numeric';
	        cellProperties.format = '$0,0.00';
	      }
	      
	      return cellProperties;
	    },
	    mergeCells: [
	      {row: 5, col: 0, rowspan: 1, colspan: 14},
	      {row: 13, col: 0, rowspan: 1, colspan: 14},
	      {row: 14, col: 0, rowspan: 2, colspan: 14},
	      {row: 21, col: 0, rowspan: 2, colspan: 14},
	      {row: 27, col: 0, rowspan: 2, colspan: 14},
	      {row: 36, col: 0, rowspan: 2, colspan: 14}
	    ]
	  });
	  
	});

  </script>
</head>
<body ng-app="sampleApp">
	<header>
		<jsp:include page="Header.jsp" />
	</header>
 <div id="personal_example"></div>
 <footer>
		<jsp:include page="Footer.jsp" />
	</footer>
 
</body>
</html>