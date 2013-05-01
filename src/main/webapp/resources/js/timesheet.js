var timeSheetFileUpload = function() {
    "use strict";
    var upload = function() {
        $('#fileupload').fileupload(
                {
                    dataType : 'json',
                    formData : function() {
                        return $("form#attachment").serializeArray();
                    },
                    add : function(e, data) {
                        $("#upload").on(
                                "click",
                                function(e) {
                                    data.context = $('<p/>').text(
                                            'Uploading...').appendTo(
                                            document.body);
                                    data.submit();
                                });
                    },
                    done : function(e, data) {
                        data.context.text('Upload finished.');
                        $.each(data.result.files, function(index, file) {
                            $('<p/>').text(file.name).appendTo(document.body);
                        });
                    }
                });
    };
    return ({
        upload : upload
    });
}();

$(document).ready(function() {
    timeSheetFileUpload.upload();
    $('#dp').datepicker({
        format : 'mm-dd-yyyy'
    });

    $('#tsCreateBtn').click(function() {
        var tsCreateDate = $('#dp').val();
        window.location = contextPath + "/timesheet/new/" + tsCreateDate;
    });

});
