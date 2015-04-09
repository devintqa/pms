	 document.createElement('header');
	 document.createElement('nav');
	 document.createElement('menu');
	 document.createElement('section');
	 document.createElement('article');
	 document.createElement('aside');
	 document.createElement('footer');

$(function() {
	$("#doj").datepicker(
	   { dateFormat: 'dd-mm-yy' , maxDate: new Date(), changeMonth:true ,changeYear: true }
	);
	/* If range of years is required , add this to jquery date picker options.... yearRange: "1990:2015" */
	
	$("#agreementDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , maxDate: new Date(), changeMonth:true ,changeYear: true }
			);
	$("#tenderDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , maxDate: new Date(), changeMonth:true ,changeYear: true }
			);
	$("#commencementDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , maxDate: new Date(), changeMonth:true ,changeYear: true }
			);
	$("#completionDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , maxDate: new Date(), changeMonth:true ,changeYear: true }
			);
	
	var today = new Date();
	   $('#requestDate').multiDatesPicker({
		   	minDate: 0,
			maxDate: 30
	   });
});