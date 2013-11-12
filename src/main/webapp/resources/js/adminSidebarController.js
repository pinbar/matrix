var adminSidebarController = function() {
    "use strict";

    var actiontoSelectorMap = {
        adminHomeLinkClicked : "a#adminHome",
        grpListLinkClicked : "a#grouplist",
        grpAddLinkClicked : "a#groupadd",
        empListLinkClicked : "a#emplist",
        empAddLinkClicked : "a#empadd",
        costcenterListLinkClicked : "a#costcenterlist",
        costcenterAddLinkClicked : "a#costcenteradd",
        clientListLinkClicked : "a#clientlist",
        clientAddLinkClicked : "a#clientadd"
    },

    _divSelectors = [ "div#adminMsgs", "div#grpList", "div#grpUpdate",
            "div#empList", "div#empUpdate", "div#costCenterList",
            "div#costCenterUpdate", "div#empResetPassword", "div#clientUpdate",
            "div#clientList" ],

    _showHideForms = function(formToShow) {
        $.each(_divSelectors, function(index, data) {
            if (data !== formToShow) {
                $(data).addClass('hide');
            } else {
                $(data).removeClass('hide');
                $(data).trigger('cssClassChangedVisible');
            }
        });
    },

    _onCostcenterAdd = function(e) {
        _populateOptions({
            selectedName : "Internal",
            alwaysShow : true,
            url : "/admin/client/listAsJson",
            optionsContainer : "#clientName"
        });
        $(".text-error").addClass('hide');
        _showHideForms("div#costCenterUpdate");
        $("form#costCenterForm").find("input[type=text], textarea").val("");
    },

    _onCostcenterList = function(e) {
        _showHideForms("div#costCenterList");
    },

    _onEmpAdd = function(e) {
        _populateOptions({
            selectedName : "Employees",
            alwaysShow : true,
            url : "/admin/group/listAsJson",
            optionsContainer : "#groupName"
        });
        $(".text-error").addClass('hide');
        _showHideForms("div#empUpdate");
        $("form#employeeForm").find("input, textarea").not(
                ':button, :submit, :reset').val("");
       /* empCostCodeController.getAllCostCodeForEmp();
        empPtoController.gettAllPtosforEmp(empCostCodeController.groupedOptions);*/
    },

    _onEmpList = function(e) {
        _showHideForms("div#empList");
    },

    _onGrpAdd = function(e) {
        $(".text-error").addClass('hide');
        _showHideForms("div#grpUpdate");
        $("form#groupForm").find("input[type=text], textarea").val("");
    },

    _onGrpList = function(e) {
        _showHideForms("div#grpList");
    },

    _onAdminHome = function(e) {
        _showHideForms("div#adminMsgs");
    },

    _onClientAdd = function(e) {
        $(".text-error").addClass('hide');
        _showHideForms("div#clientUpdate");
        $("form#clientForm").find("input, textarea").not(
                ':button, :submit, :reset').val("");
    },

    _onClientList = function(e) {
        _showHideForms("div#clientList");
    },

    _populateOptions = function(args) {
        var show = args.alwaysShow || !($(args.container).hasClass("hide"));
        if (show) {
            $
                    .ajax({
                        url : contextPath + args.url,
                        type : "GET",
                        dataType : "json"
                    })
                    .done(
                            function(response, textStatus, jqXHR) {
                                var html = "";
                                $
                                        .each(
                                                response,
                                                function(key, value) {
                                                    var dispName, dispVal;
                                                    if (value
                                                            .hasOwnProperty('firstName')) {
                                                        dispName = value.firstName+" "+value.lastName;
                                                        dispVal=value.id;
                                                    } else {
                                                        dispName = value.name;
                                                        dispVal = value.costCode ? value.costCode
                                                                : value.name;
                                                    }
                                                    html = html
                                                            + "<option value=\""
                                                            + dispVal
                                                            + "\""
                                                            + (args.selectedName
                                                                    && (dispName === args.selectedName)
                                                                    || (dispVal === args.selectedName) ? " selected "
                                                                    : "")
                                                            + " >" + dispName
                                                            + "</option>";

                                                });
                                $(args.optionsContainer).empty().append(html);
                            }).

                    fail(function(response, textStatus, jqXHR) {
                        alert("error");
                    });
        }
    };
    return ({
        costcenterAddLinkClicked : _onCostcenterAdd,
        costcenterListLinkClicked : _onCostcenterList,
        clientAddLinkClicked : _onClientAdd,
        clientListLinkClicked : _onClientList,
        empAddLinkClicked : _onEmpAdd,
        empListLinkClicked : _onEmpList,
        grpAddLinkClicked : _onGrpAdd,
        grpListLinkClicked : _onGrpList,
        adminHomeLinkClicked : _onAdminHome,
        actiontoSelectorMap : actiontoSelectorMap,
        populateOptions : _populateOptions
    });
}();

$(document).ready(function() {
    $.each(adminSidebarController.actiontoSelectorMap, function(key, value) {
        $(value).on("click", adminSidebarController[key]);
    });

});
