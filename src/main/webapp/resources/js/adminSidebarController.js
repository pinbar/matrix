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
            "div#costCenterUpdate", "div#empResetPassword", "div#clientUpdate", "div#clientList"  ],

        _showHideForms = function(formToShow) {
            $.each(_divSelectors, function(index, data) {
                if (data !== formToShow) {
                    $(data).addClass('hide');
                } else {
                    $(data).removeClass('hide');
                }
            });
        },

        _onCostcenterAdd = function(e) {
            populateOptions({selectedName:"Internal",alwaysShow:true,url:"/admin/client/listAsJson",optionsContainer:"#clientName"});//set 'Internal' by default
        $(".text-error").addClass('hide');
        _showHideForms("div#costCenterUpdate");
        $("form#costCenterForm").find("input[type=text], textarea").val("");
    },

        _onCostcenterList = function(e) {
        _showHideForms("div#costCenterList");
    },

        _onEmpAdd = function(e) {
        populateOptions({selectedName:"Employees", alwaysShow:true,url:"/admin/group/listAsJson",optionsContainer:"#groupName"});//set 'Employees' by default
        populateOptions({selectedName:"Holiday", alwaysShow:true, url:"/admin/costCenter/listAsJson",optionsContainer:".costCode"});//set 'Employees' by default
        $(".text-error").addClass('hide');
        _showHideForms("div#empUpdate");
        $("form#employeeForm").find("input, textarea").not(':button, :submit, :reset').val("");
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
        $("form#clientForm").find("input, textarea").not(':button, :submit, :reset').val("");
    },

        _onClientList = function(e) {
        _showHideForms("div#clientList");
    },
    
    populateOptions= function(args){
        var show = args.alwaysShow
        || !($(args.container).hasClass("hide"));
        if (show) {
            $.ajax({
                url : contextPath + args.url,
                type : "GET",
                dataType : "json",
                success : function(response, textStatus, jqXHR) {
                    var html = "";
                    $.each(
                                    response,
                                    function(key, value) {
                                        var grpName = value.name;
                                        html = html
                                                + "<option value=\""
                                                + grpName
                                                + "\""
                                                + (args.selectedName
                                                        && grpName === args.selectedName ? " selected "
                                                        : "") + " >"
                                                + grpName + "</option>";
                                    });
                    $(args.optionsContainer).empty().append(html);
                },
                error : function(response, textStatus, jqXHR) {
                    alert("error");
                }
            });  
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
        populateOptions : populateOptions
    });
}();

$(document).ready(function() {
    $.each(adminSidebarController.actiontoSelectorMap, function(key, value) {
        $(value).on("click", adminSidebarController[key]);
    });

});
