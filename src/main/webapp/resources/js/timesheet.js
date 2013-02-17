$(document).ready(function() {
	
	$('#dp').datepicker({
		format: 'mm-dd-yyyy'
	});
	
	$('#tsCreateBtn').click(function() {
	    var tsCreateDate = $('#dp').val();
	    window.location = "http://localhost:8080/matrix/timesheet/new/"+tsCreateDate;
	  });
});
