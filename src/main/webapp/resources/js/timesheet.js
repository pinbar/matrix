var timesheetContentController = function() {
    "use strict";
    var _setUpToggleAttachments = function() {
        $('#toggleAttachments')
                .on(
                        "click",
                        function(e) {
                            if (!$("#timesheetId").val()) {
                                $("#errorMessages")
                                        .empty()
                                        .append(
                                                'You must save the timesheet before adding attachments.');
                                return;
                            }
                            $('div#attachments').collapse("toggle");
                            if ($('div#attachments').attr('class')
                                    .indexOf('in') >= 0) {
                                if ($("table#filePresentation tr").length > 0) {
                                    return;
                                } else {
                                    $
                                            .ajax(
                                                    {
                                                        url : contextPath
                                                                + '/attachment/timesheet/all?timesheetId='
                                                                + $(
                                                                        "#timesheetId")
                                                                        .val(),
                                                        type : "GET",
                                                        dataType : "json",
                                                        context : $('#fileupload')[0]
                                                    })
                                            .done(
                                                    function(response) {
                                                        $(this)
                                                                .fileupload(
                                                                        'option',
                                                                        'done')
                                                                .call(
                                                                        this,
                                                                        null,
                                                                        {
                                                                            result : response
                                                                        });
                                                    }).fail(
                                                    // TODO :
                                                    // error
                                                    // styling
                                                    // and error
                                                    // stuff
                                                    function(response,
                                                            textStatus, jqXHR) {
                                                        alert("error");
                                                    });
                                }
                            }
                        });
    },

    _init = function() {
        _setUpSubmitTimesheet();
        _setUpActivateTimesheet();
        _disableTimesheet();
        _setUpToggleAttachments();
        if ($("#fileupload").length > 0) {
            $('#fileupload')
                    .fileupload(
                            {
                                maxFileSize : 5000000,
                                autoUpload : true,
                                acceptFileTypes : /(\.|\/)(txt|pdf|xls|doc|docx)$/i,
                                prependFiles : true,
                                formData : function() {
                                    return $("form#fileupload")
                                            .serializeArray();
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
                                                                    .find(
                                                                            '.error')
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
                                                                        + '<td class="delete"><button type="button" class="btn delete btn-danger" onclick="timesheetContentController.removeFileErrorRow($(this));"><i class="icon-trash icon-white"></i><span> Delete </span></button>'
                                                                        : '<td class="preview"></td>'
                                                                                + '<td class="name"><a target="_blank"></a></td>'
                                                                                + '<td class="size"></td><td colspan="2"></td>'
                                                                                + '<td class="delete"><button type="button" class="btn delete btn-danger"><i class="icon-trash icon-white"></i><span> Delete </span></button>')
                                                                + '</td><td><input type="checkbox" name="delete" value="1" class="toggle" style="margin-left: 4px;"></td></tr>');
                                                        row
                                                                .find('.size')
                                                                .text(
                                                                        o
                                                                                .formatFileSize(file.size));
                                                        if (file.error) {
                                                            row
                                                                    .find(
                                                                            '.name')
                                                                    .text(
                                                                            file.name);
                                                            row
                                                                    .find(
                                                                            '.error')
                                                                    .text(
                                                                            file.error);
                                                        } else {
                                                            row
                                                                    .find(
                                                                            '.name a')
                                                                    .text(
                                                                            file.name);
                                                            if (file.thumbnail_url) {
                                                                row
                                                                        .find(
                                                                                '.preview')
                                                                        .append(
                                                                                '<a><img></a>')
                                                                        .find(
                                                                                'img')
                                                                        .prop(
                                                                                'src',
                                                                                file.thumbnail_url);
                                                                row
                                                                        .find(
                                                                                'a')
                                                                        .prop(
                                                                                'rel',
                                                                                'gallery');
                                                            }
                                                            row.find('a').prop(
                                                                    'href',
                                                                    file.url);
                                                            row
                                                                    .find(
                                                                            '.delete')
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
        }
    },

    _disableTimesheet = function() {
        var status = $('#timesheet').data('status');
        status = status ? status.toUpperCase() : null;
        if (status && status !== 'PENDING') {
            $("form#timesheet").find("input, select ,textarea").attr(
                    "disabled", "disabled");
            $("form#timesheet").find('a').attr('class', 'hide');
        } else {
            $("#activateTimesheet").removeAttr('disabled', 'disabled');
        }
    },

    _setUpActivateTimesheet = function() {
        $("#activateTimesheet")
                .on(
                        "click",
                        function() {
                            $
                                    .ajax(
                                            {
                                                url : contextPath
                                                        + '/timesheet/activate/'
                                                        + $('#timesheet').data(
                                                                'id'),
                                                type : "GET",
                                                dataType : "html"
                                            })
                                    .done(
                                            function(response) {
                                                var errNode = null;
                                                errNode = $
                                                        .parseHTML(response)
                                                        .filter(
                                                                function(node,
                                                                        index) {
                                                                    return node.id == "errorMessages";
                                                                })[0];
                                                var err = errNode ? errNode.innerHTML.length > 0
                                                        : false;
                                                if (err) {
                                                    $("#errorMessages").show();
                                                }
                                                $("#timesheetContent").html(
                                                        response);
                                                _init();
                                                $("#activateTimesheet").attr(
                                                        "disabled", "disabled");
                                            });

                        });
    },

    _setUpSubmitTimesheet = function() {
        $('.submitTimesheet')
                .on(
                        'click',
                        function(e) {
                            $("span.error").remove();
                            if (!_validateTimes()) {
                                return false;
                            }
                            var urlFragment = $(e.target).val() === "Save" ? '/timesheet/save'
                                    : '/timesheet/submit';
                            $
                                    .ajax({
                                        url : contextPath + urlFragment,
                                        type : "POST",
                                        dataType : "html",
                                        data : $("#timesheet").serialize()
                                    })
                                    .done(
                                            function(response) {
                                                var errNode = null;
                                                errNode = $
                                                        .parseHTML(response)
                                                        .filter(
                                                                function(node,
                                                                        index) {
                                                                    return node.id == "errorMessages";
                                                                })[0];
                                                var err = errNode ? errNode.innerHTML.length > 0
                                                        : false;
                                                if (err) {
                                                    $("#errorMessages").show();
                                                }
                                                $("#timesheetContent").html(
                                                        response);
                                                _init();
                                                _validateInvidualTimeInput();

                                            }).fail(function(response, textStatus, jqXHR) {
                                                $("body").html(
                                                        response.responseText);
                                                _init();
                                                _validateInvidualTimeInput();
                                            });
                        });
    },

    _removeFileErrorRow = function(node) {
        node.closest("tr").remove();
    },

    _validateInvidualTimeInput = function() {
        var overTimenodes = $('input[type="number"]').filter(function() {
            return $(this).val() > 8;
        }), weekendNodes = $('input[type="number"]').filter(function() {
            return $(this).hasClass("weekend") && $(this).val() > 0;
        });
        overTimenodes.each(function() {
            $(this).siblings('.msg').text("Overtime hours entered")
                    .removeClass('hide').addClass('error');
            $(this).closest('td').addClass("warning");
        });
        weekendNodes.each(function() {
            var text = $(this).siblings('.msg').removeClass('hide').addClass(
                    'error').text();
            $(this).siblings('.msg').removeClass('hide').addClass('error')
                    .html($.trim(text) + "<br> Weekend hours entered");
            $(this).closest('td').addClass("warning");
        });

    },

    _validateTimes = function() {
        var list = [ ".monHrs", ".tueHrs", ".wedHrs", ".thuHrs", ".friHrs",
                ".satHrs", ".sunHrs" ], valid = true;
        for ( var i = 0; i < list.length; i++) {
            var validDay = _validateTimeInputForDays(list[i], list[i] + 'Th');
            valid = valid ? valid = validDay : valid;
        }
        return valid;
    }, _validateTimeInputForDays = function(inputClass, headerClass) {
        var hrs = 0.0;
        $(inputClass).each(function(i) {
            hrs = hrs + parseFloat(this.value);
        });
        if (24 < hrs) {
            $(headerClass)
                    .append(
                            '<span class="error" style="display: inline-block;">Hours can not be more than 24</span>')
                    .addClass("warning");
            return (false);
        }
        return true;
    };

    return ({
        init : _init,
        removeFileErrorRow : _removeFileErrorRow,
        disableTimesheet : _disableTimesheet,
        setUpActivateTimesheet : _setUpActivateTimesheet
    });
}();

$(document).ready(function() {
    timesheetContentController.init();

});