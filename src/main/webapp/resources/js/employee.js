/*
 * This code controls the interaction in the add costcode panel of employee 
 * edit/add  
 * 
 */
var empCostCodeController = function() {
    var container = $("#projects"), 
    
    groupedOptions,
    
    managerId,
    
    costCenterUrl = "/admin/costCenter/grouped/listAsJson", 

    getHtmlTemplate = function() {
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
        selectedId= selectedId ? selectedId: managerId; 
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
    
    
    setManager = function(arg){
        managerId = arg;
    }

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
            // use this for later and save a call
            // eg from ptos
            groupedOptions= response;
            container.trigger('costCodesLoaded',groupedOptions);
            var html = buildGroupedOptions(response, args.selectedName);
            $(args.optionsContainer).empty().append(html);
            $("#costCodeListSelect").multiselect().multiselectfilter();
        }).fail(function(response, textStatus, jqXHR) {
           console.log("error  =>"+ response);
        });
    };

    return ({
        onBeforeSave : onBeforeSave,
        getAllCostCodeForEmp : getAllCostCodeForEmp,
        getAllManagers:getAllManagers,
        groupedOptions: groupedOptions,
        setManager: setManager,
        container : container
    });
}();

var empPtoController = function() {
    var container = $("#ptoInput"),

    ptoUrl = "/admin/employee/ptoConfig/listAsJson",

    onBeforeSave = function() {
        var empPtos = [];
        
        var data=[];
        $('#ptoTable > tbody').find('tr').each(function(){
            //var id=$(this).attr('id');
            var row={};
            $(this).find('input:not(:disabled),h5').each(function(){
                
                row[$(this).attr('name')]=$(this).val() ? $(this).val() : $(this).text();
            });
            data.push(row);
        });
      // TODO do something here
        $("#ptos").val(JSON.stringify(data));
    },
    
    getHtml= function(args){
      var html = '<table id="ptoTable" class="table  table-striped cf rt"> <thead> <tr><th>Cost Code</th><th>Yearly Hours</th><th>Carried Over Hours</th><th>Total Hours</th></tr><tbody>';
          $.each(args,function(index,arg){
              var totalHrs=parseFloat(arg["carryOverAllowedHours"])+ arg["yearlyAllocatedHours"];
           html = html+'<tr><td><h5 name="costCode">'+arg["costCode"]+'</h5></td>'
           +'<td><input type="number" name="yearlyAllocatedHours" class="form-control" value="'+arg["yearlyAllocatedHours"]+'"></td>'
           +'<td><input type="number" name="carryOverAllowedHours" class="form-control" value="'+arg["carryOverAllowedHours"]+'"></td>'
           +'<td><input type="number"  class="form-control" disabled value="'+totalHrs+'"></td>'
           +'</tr>'
          });
      html+'</tbody></table>';
      return html;
    },
    
    convertToInitalPTO= function(args){
       var retObj=[];
        $.each(args["Paid Time Off"],function(index,arg){
            obj=[];
            obj["costCode"]= arg["costCode"];
            obj["yearlyAllocatedHours"]= 0.00;
            obj["carryOverAllowedHours"]= 0.00;
            retObj.push(obj);
       }); 
       return retObj;
    },

    populatePtoConfig= function(args){
        var employeeId = $('#employeeForm #id').val();
        if (!employeeId) {
            args.allPtos= convertToInitalPTO(args.allPtos);
            populatePtos(args); 
            return;
        }
        $.ajax({
            url : contextPath + args.url+'?employeeId='+employeeId,
            type : "GET",
            dataType : "json"
        }).done(function(response, textStatus, jqXHR) {
                args.allPtos= (response.length===0)? convertToInitalPTO(args.allPtos): response;  
            populatePtos(args); 
        }).fail(function(response, textStatus, jqXHR) {
            console.log("error  =>"+ response);
        });
    },
    
   populatePtos= function(args){
        $(args.ptoContainer).empty();
        $(args.ptoContainer).html(getHtml(args.allPtos)); 
    },

   gettAllPtosforEmp = function(args) {
        populatePtoConfig({
            allPtos: args,
            url : ptoUrl,
            ptoContainer : "#ptoInput"
        });
    };
        return ({
            onBeforeSave : onBeforeSave,
            gettAllPtosforEmp : gettAllPtosforEmp,
        });
}();

$(document).ready(function() {
    $("#employeeSave").on("click", function(e) {
        empCostCodeController.onBeforeSave();
        empPtoController.onBeforeSave();
    });
    $("#empUpdate").on("cssClassChangedVisible",function(e){
        if (!$("#empUpdate").hasClass("hide")) {
            empCostCodeController.getAllCostCodeForEmp();
            // empPtoController.gettAllPtosforEmp(empCostCodeController.groupedOptions);
            empCostCodeController.getAllManagers($('hiddenManagerId').val());
            adminSidebarController.populateOptions({
                selectedName : $("#hiddenGroupName").val(),
                alwaysShow : true,
                url : "/admin/group/listAsJson",
                optionsContainer : "#groupName"
            });
        }
    });
    
    empCostCodeController.container.on("costCodesLoaded",function(e,args){
        empPtoController.gettAllPtosforEmp(args);
   });

});
