/*
 * This code controls the interaction in the add costcode panel of employee 
 * edit/add  
 * 
 */
var empCostCodeController = function() {
    var container = $("#projects"), costCenterUrl = "/admin/costCenter/grouped/listAsJson", getHtmlTemplate = function() {
        return "<label class=\"control-label\" for=\"costCodeListSelect\" title> <span> CostCenters</span></label>"
                + "<div class=\"controls\">"
                + "<select multiple=\"multiple\" class=\"ccSelect\" id =\"costCodeListSelect\"></select>"
                + "</div>";
    },

    getAllCostCodeForEmp = function(e) {
        var employeeId = $('#employeeForm #id').val();
        // from error condition
        var prePopulatedCostCodes = $("#costCodes").val() !== ("") ? $(
                "#costCodes").val().split(",") : "";
        if (!employeeId
                || (prePopulatedCostCodes && prePopulatedCostCodes !== "")) {
            populateContainer(prePopulatedCostCodes || []);
            return;
        }
        var url = contextPath
                + "/admin/employee/costCenters/listAsJson?employeeId="
                + employeeId;
        $.ajax({
            url : url,
            type : "GET",
            dataType : "json",
            contentType : 'application/json'
        }).done(function(response, textStatus, jqXHR) {
            populateContainer(response);
        }).fail(function(response, textStatus, jqXHR) {
            alert("error");
        });
    },

    getAllManagers = function(selectedId) {
        adminSidebarController.populateOptions({
            selectedName : selectedId,
            alwaysShow : true,
            url : "/admin/employee/managers/listAsJson",
            optionsContainer : "#managerId"
        });
    },

    buildGroupedOptions = function(args, selected) {
        var html = "";
        $
                .each(
                        args,
                        function(key, value) {
                            html = html + "<optgroup label=" + key + ">";
                            $
                                    .each(
                                            value,
                                            function(index, opts) {
                                                html = html
                                                        + "<option value=\""
                                                        + opts.costCode
                                                        + "\""
                                                        + (selected
                                                                && ($
                                                                        .inArray(
                                                                                opts.costCode,
                                                                                selected) > -1) ? " selected "
                                                                : "") + " >"
                                                        + opts.name
                                                        + "</option>";
                                            });
                            html + "</optgroup>";
                        });
        return html;

    },

    populateContainer = function(response) {
        var selected = [];
        container.empty().html(getHtmlTemplate());
        $.each(response, function(key, value) {
            selected.push(value);
        });
        populateOptions({
            selectedName : selected,
            alwaysShow : true,
            url : costCenterUrl,
            optionsContainer : "#costCodeListSelect"
        });
    },

    onBeforeSave = function() {
        var selected = [];
        $(".ui-multiselect-menu input[aria-selected='true' ]").each(
                function(index, node) {
                    selected.push(($(node).attr("value")));
                });
        $("#costCodes").val(selected.join(','));
    },

    populateOptions = function(args) {
        $.ajax({
            url : contextPath + args.url,
            type : "GET",
            dataType : "json"
        }).done(function(response, textStatus, jqXHR) {
            var html = buildGroupedOptions(response, args.selectedName);
            $(args.optionsContainer).empty().append(html);
            $("#costCodeListSelect").multiselect().multiselectfilter();
        }).fail(function(response, textStatus, jqXHR) {
            alert("error");
        });
    };

    return ({
        onBeforeSave : onBeforeSave,
        getAllCostCodeForEmp : getAllCostCodeForEmp,
        getAllManagers:getAllManagers,
        container : container
    });
}();
$(document).ready(function() {
    $("#employeeSave").on("click", function(e) {
        empCostCodeController.onBeforeSave();
    });
    if (!$("#empUpdate").hasClass("hide")) {
        empCostCodeController.getAllCostCodeForEmp();
        empCostCodeController.getAllManagers($('hiddenManagerId').val());
        adminSidebarController.populateOptions({
            selectedName : $("#hiddenGroupName").val(),
            alwaysShow : true,
            url : "/admin/group/listAsJson",
            optionsContainer : "#groupName"
        });
    }

});
