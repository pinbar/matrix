var hrTimesheetController = function() {
    "use strict";
    var actiontoSelectorMap = {
        timeSheetRejectClicked : ".timesheetReject",
        timesheetApproveClicked : ".timesheetApprove",
        timesheetEditClicked : ".timesheetEdit"
    },

    _onTimeSheetEdit = function(e) {
        var row = $(e.target).closest('tr');
        var id = $(row).data('id');
        var employeeId = $(row).data('employeeid');
        $('#timesheet').modal(
                {
                    'show' : true,
                    'remote' : contextPath + '/timesheet/' + id + '?employee='
                            + employeeId
                });
    };

    return ({
        // timesheetApproveClicked : _onTimeSheetApprove,
        // timeSheetRejectClicked : _onTimeSheetReject,
        timesheetEditClicked : _onTimeSheetEdit,
        actiontoSelectorMap : actiontoSelectorMap
    });
}();

$(document).ready(function() {
    $.each(hrTimesheetController.actiontoSelectorMap, function(key, value) {
        $(value).on("click", hrTimesheetController[key]);
    });

});