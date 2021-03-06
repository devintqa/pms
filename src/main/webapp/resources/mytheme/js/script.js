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
	$("#depositStartDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
	$("#completionDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);
    $("#recievedDate").datetimepicker({
    	controlType: 'select',
    	dateFormat: 'yy-mm-dd',
    	timeFormat: 'HH:mm:ss'
      });
    
	
    $("#dispatchedDate").datepicker(
    		 { dateFormat: 'dd-mm-yy' , changeDate:false, changeMonth:false ,changeYear: false , maxDate: new Date() , minDate: 0 } 
    		).datepicker("setDate", new Date());
    
    $("#returnedDate").datepicker(
   		 { dateFormat: 'dd-mm-yy' , changeDate:false, changeMonth:false ,changeYear: false , maxDate: new Date() , minDate: 0 } 
   		).datepicker("setDate", new Date());
    
    $("#tentativeDeliveryDate").datepicker(
			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
			);	    
	$("#depositEndDate").datepicker(
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
	$("#depositExtensionDate").datepicker(
    			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
    			);
    $("#depositRecievedDate").datepicker(
    			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
    			);
    $("#completionDateForBonus").datepicker(
    			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
    			);
    $("#subCompletionDateForBonus").datepicker(
        			   { dateFormat: 'dd-mm-yy' , changeDate:true, changeMonth:true ,changeYear: true }
        			);
});