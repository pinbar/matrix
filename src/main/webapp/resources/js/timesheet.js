$(document).ready(function() {

    $('#dp').datepicker({
        format : 'mm-dd-yyyy'
    });

    $('#tsCreateBtn').click(function() {
        var tsCreateDate = $('#dp').val();
        window.location = contextPath + "/timesheet/new/" + tsCreateDate;
    });

});
