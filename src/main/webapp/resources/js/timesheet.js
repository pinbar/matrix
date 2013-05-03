var timeSheetFileUpload = function() {
    "use strict";
    var init = function() {
         $('#fileupload')
                .fileupload(
                        {
                            maxFileSize : 5000000,
                            autoUpload : true,
                            acceptFileTypes : /(\.|\/)(txt|pdf|xls|doc|docx)$/i,
                            prependFiles : true,
                            formData : function() {
                                return $("form#fileupload").serializeArray();
                            },
                            uploadTemplateId : null,
                            downloadTemplateId : null,
                            uploadTemplate : function(o) {
                                var rows = $();
                                $
                                        .each(
                                                o.files,
                                                function(index, file) {
                                                    var row = $('<tr class="template-upload fade">'
                                                            + '<td class="preview"><span class="fade"></span></td>'
                                                            + '<td class="name"></td>'
                                                            + '<td class="size"></td>'
                                                            + (file.error ? '<td class="error" colspan="2"></td>'
                                                                    : '<td class="span5"><div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">'
                                                                            + '<div class="bar" style="width:0%;"></div></div></td>'
                                                                            + '<td class="start"><button class="btn btn-primary start">'
                                                                            + '<i class="icon-upload icon-white"></i><span>Start upload</span>'
                                                                            + '</button></td>')
                                                            + '<td class="cancel"> <button type="reset" class="btn btn-warning cancel">'
                                                            + '<i class="icon-ban-circle icon-white"></i>'
                                                            + '<span>Cancel upload</span>'
                                                            + '</button></td></tr>');
                                                    row.find('.name').text(
                                                            file.name);
                                                    row
                                                            .find('.size')
                                                            .text(
                                                                    o
                                                                            .formatFileSize(file.size));
                                                    if (file.error) {
                                                        row
                                                                .find('.error')
                                                                .text(
                                                                        file.error);
                                                    }
                                                    rows = rows.add(row);
                                                });
                                return rows;
                            },
                            downloadTemplate : function(o) {
                                var rows = $();
                                $
                                        .each(
                                                o.files,
                                                function(index, file) {
                                                    var row = $('<tr class="template-download fade">'
                                                            + (file.error ? '<td></td><td class="name"></td>'
                                                                    + '<td class="size"></td><td class="error" colspan="2"></td>'
                                                                    : '<td class="preview"></td>'
                                                                            + '<td class="name"><a target="_blank"></a></td>'
                                                                            + '<td class="size"></td><td colspan="2"></td>')
                                                            + '<td class="delete"><button type="button" class="btn delete btn-danger"><i class="icon-trash icon-white"></i><span> Delete </span></button>'
                                                            + '</td><td><input type="checkbox" name="delete" value="1" class="toggle" style="margin-left: 4px;"></td></tr>');
                                                    row
                                                            .find('.size')
                                                            .text(
                                                                    o
                                                                            .formatFileSize(file.size));
                                                    if (file.error) {
                                                        row.find('.name').text(
                                                                file.name);
                                                        row
                                                                .find('.error')
                                                                .text(
                                                                        file.error);
                                                    } else {
                                                        row
                                                                .find('.name a')
                                                                .text(file.name);
                                                        if (file.thumbnail_url) {
                                                            row
                                                                    .find(
                                                                            '.preview')
                                                                    .append(
                                                                            '<a><img></a>')
                                                                    .find('img')
                                                                    .prop(
                                                                            'src',
                                                                            file.thumbnail_url);
                                                            row.find('a').prop(
                                                                    'rel',
                                                                    'gallery');
                                                        }
                                                        row.find('a').prop(
                                                                'href',
                                                                file.url);
                                                        row
                                                                .find('.delete')
                                                                .attr(
                                                                        'data-type',
                                                                        file.delete_type)
                                                                .attr(
                                                                        'data-url',
                                                                        file.delete_url);
                                                    }
                                                    rows = rows.add(row);
                                                });
                                return rows;
                            }
                        });
    };
    return ({
        init : init,
    });
}();

$(document).ready(function() {
    timeSheetFileUpload.init();
    $('#dp').datepicker({
        format : 'mm-dd-yyyy'
    });

    $('#tsCreateBtn').click(function() {
        var tsCreateDate = $('#dp').val();
        window.location = contextPath + "/timesheet/new/" + tsCreateDate;
    });

    $(function() {
        $('#toggleAttachments').click(function(e) {
            // TODO check for timesheetId
            if (!$("#timesheetId").val()) {
                $("#errorMessages").empty().append('You must save the timesheet before adding attachments.');
                return;
            }
            $('div#attachments').collapse("toggle");
            if ($('div#attachments').attr('class').indexOf('in') >= 0) {
                // TODO check for content
                if ($("table#filePresentation tr").length > 0 ) {
                      return;
                } else {
                    $.ajax({
                        url : contextPath + '/attachment/timesheet/all?timesheetId='+$("#timesheetId").val(),
                        type : "GET",
                        dataType : "json",
                        context: $('#fileupload')[0]
                    }).done(function(response) {
                        $(this).fileupload('option', 'done')
                        .call(this, null, {result: response});
                    }).fail( // TODO : error styling and error stuff
                    function(response, textStatus, jqXHR) {
                        alert("error");
                    });
                }
            }
        });

    });
});
