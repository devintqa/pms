	 document.createElement('header');
	 document.createElement('nav');
	 document.createElement('menu');
	 document.createElement('section');
	 document.createElement('article');
	 document.createElement('aside');
	 document.createElement('footer');

$(function() {
	$("#agreementDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#tenderDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#commencementDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#emdStartDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#completionDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#emdEndDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);	
	$("#subAgreementDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#subTenderDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#subCommencementDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#subCompletionDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#emdExtensionDate").datepicker(
    			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
    			);
});