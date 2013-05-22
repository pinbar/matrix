/*
 * This code controls the interaction in the add costcode panel of employee 
 * edit/add  
 * 
 */
var empCostCodeController = function() {
    var container = $("#projects"), costCenterUrl = "/admin/costCenter/grouped/listAsJson", getHtmlTemplate = function() {
        return "<div class=\"controls\"><select multiple=\"multiple\" class=\"ccSelect\" id =\"costCodeListSelect\"></select></div>";
    }, getAllCostCodeForEmp = function(e) {
        var employeeId = $('#employeeForm #id').val();
        if (!employeeId) {
            populateContainer([]);
            return;
        }
        var url = contextPath
                + "/admin/employee/costCenters/listAsJson?employeeId="
                + employeeId;
        $.ajax(
            {
                url : url,
                type : "GET",
                dataType : "json",
                contentType : 'application/json',
                success : function(response, textStatus, jqXHR) {
                    populateContainer(response);
                },
                error : function(response, textStatus, jqXHR) {
                    alert("error");
                }
            });
    }, buildGroupedOptions = function(args, selected) {
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

    }, populateContainer = function(response) {
        var selected = [];
        container.empty().html(getHtmlTemplate());
        $.each(response, function(key, value) {
            selected.push(value);
        });
        populateOptions(
            {
                selectedName : selected,
                alwaysShow : true,
                url : costCenterUrl,
                optionsContainer : "#costCodeListSelect"
            });
    }, onBeforeSave = function() {
        var selected=[];
         $(".ui-multiselect-menu input[aria-selected='true' ]").each(function(index, node){
             selected.push(($(node).attr("value")));
         }) ;   
         $("#employeeForm").append("<input name=\"costCodeStr\" type=\"hidden\" value='"+selected.join(',')+"'>");
    }, populateOptions = function(args) {
        $
                .ajax(
                    {
                        url : contextPath + args.url,
                        type : "GET",
                        dataType : "json",
                        success : function(response, textStatus, jqXHR) {
                            var html = buildGroupedOptions(response,
                                    args.selectedName);
                            $(args.optionsContainer).empty().append(html);
                            $("#costCodeListSelect").multiselect()
                                    .multiselectfilter();
                        },
                        error : function(response, textStatus, jqXHR) {
                            alert("error");
                        }
                    });
    };

    return (
        {   onBeforeSave : onBeforeSave,
            getAllCostCodeForEmp : getAllCostCodeForEmp,
            container : container
        });
}();
$(document).ready(function() {
    $("#employeeSave").on("click",function(e){
        empCostCodeController.onBeforeSave();       
    });

});

