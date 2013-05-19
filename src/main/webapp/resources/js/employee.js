/*
 * This code controls the interaction in the add costcode panel of employee 
 * edit/add  
 * 
 */
var empCostCodeController = function() {
    var tableId = "empCostCodeListTable", 
    tableNode = $("#" + tableId), 
     
    getHtmlTemplate = function(){
        var id = getAppendId(),
        html = "<tr data-id=\""+id+"\"id=\"costCodeListTr_" + id
                + "\" ><td><select class=\"ccSelect\" id =\"costCodeListSelect_" + id
                + "\"></select></td><td><input class=\"dp\" type=\"text\" id=\"startDate_" + id+"\"></td>"
                +"<td><input class=\"dp\" type=\"text\" id=\"endDate_" + id+"\"></td>"
                +"<td><a class=\"saveCostCodeItem\" href=\"javascript:;\"><i class=\"icon-upload\" ></i> </a></td>"
                +"<td><a class=\"delCostCodeItem\" href=\"javascript:;\"><i class=\"icon-trash\" ></i> </a></td></tr>";
        return html;  
    },
    getAppendId= function(){
        var lastTr = $("#empCostCodeListTable tbody>tr:last"), id = (lastTr.length === 0) ? 0
                : parseInt(lastTr.attr('data-id'), 10) + 1;
        return id; 
    },
    getAllCostCodeForEmp= function(e){
        var employeeId = $('#employeeForm #id').val(),
        url = "employee/costCenters/listAsJson?employeeId="+employeeId;
        $.ajax({
            url : url,
            type : "POST",
            dataType : "json",
            success : function(response, textStatus, jqXHR) {
                populateTable(response);
            },
            error: function(response, textStatus, jqXHR){
                alert("error");
            }
        });
    },
    delCostCodeItem = function(e){
        var row = $(e.target).closest('tr'), 
        costCode= row.find('select').find('option:selected').text(),
        //TODO
        url = "BASEURL/resource/deleteEMPCOSTCODE/costCode="+costCode;
        $.ajax({
            url : url,
            type : "POST",
            dataType : "json",
            success : function(response, textStatus, jqXHR) {
                populateTable(response) ; // provided the data structure is as
                                            // desired
            },
            error: function(response, textStatus, jqXHR){
                alert("error");
            }
        });
    },

    saveCostCodeItem = function(e){
        var row = $(e.target).closest('tr'),
        id = row.attr('data-id'),
        postArgs={},
        //TODO
        url = "BASEURL/resource/addEMPCOSTCODE/costCode="+costCode;
        postArgs.costCode= row.find('select').find('option:selected').text();
        postArgs.startDate=$('#startDate_'+id).val();
        postArgs.endDate=$('#endDate_'+id).val();
        $.ajax({
            url : url,
            type : "POST",
            dataType : "json",
            data:postArgs,
            success : function(response, textStatus, jqXHR) {
                populateTable(response) ; // provided the data structure is as
                                            // desired
            },
            error: function(response, textStatus, jqXHR){
                alert("error");
            }
        });
    },

    saveTable = function(e){
        //TODO
        var postData= [], url = "BASEURL/resource/addallEMPCOSTCODE";
        tableNode.find('tbody>tr').each(function(){
           var row ={},
           id = this.getAttribute('data-id');
           row.costCode= $(this).find('select').find('option:selected').text();
           row.startDate=$('#startDate_'+id).val();
           row.endDate=$('#endDate_'+id).val();
           postData.push(row);
        });
        $.ajax({
            url : url,
            type : "POST",
            dataType : "json",
            data:postData,
            success : function(response, textStatus, jqXHR) {
                populateTable(response) ; // provided the data structure is as
                                            // desired
            },
            error: function(response, textStatus, jqXHR){
                alert("error");
            }
        });
  }
    appendEmptyLastRow = function() {
        var id = getAppendId();

        tableNode.append(getHtmlTemplate());
        populateOptions({
            selectedName : "Holiday",
            alwaysShow : true,
            url : "/admin/costCenter/listAsJson",
            optionsContainer : "#costCodeListSelect_" + id,
            id: id
        });
        $("#costCodeListTr_"+id).find(".dp").datepicker({
            format : 'mm-dd-yyyy'
        });
        
    }, 
    initTable = function(args) {
        if (tableNode) {
            appendEmptyLastRow();
        }
    }, populateTable = function(args) {
        /*
         * dummy data [{costCode:"Holiday",startDate:"abc",endDate:"abc1"
         * },{costCode:"Chase",startDate:"12",endDate:"1234" }]
         */
        //args = [{costCode:"Holiday",startDate:"abc",endDate:"abc1" },{costCode:"Nationwide",startDate:"12",endDate:"1234" }];
        if(args.reRender){
            // send the rerender flag if the table needs to be redrawn
            tableNode.find('tbody').empty();
        }
        $.each(args, function(key,value){
          
            var id = getAppendId();
            tableNode.append(getHtmlTemplate());
                populateOptions({ 
                    selectedName : value.costCode,
                    alwaysShow : true,
                    url : "/admin/costCenter/listAsJson",
                    optionsContainer : "#costCodeListSelect_" + id,
                    id: id
                    });
            $('[id= startDate_'+id+']', tableNode).val(value.startDate);
            $('[id= endDate_'+id+']', tableNode).val(value.endDate);
            $("#costCodeListTr_"+id).find(".dp").datepicker({
                format : 'mm-dd-yyyy'
            });
        });
        

    }, populateOptions = function(args) {
        var selectedName = args.selectedName,
         selectNode =  $(args.optionsContainer),optionsHtml,
         optionsContainer=args.optionsContainer;
        $(".ccSelect").each(function(id, node){
            if (node.innerHTML){
                optionsHtml = node.innerHTML;
                return (false);
            }
        })
        if (optionsHtml) {
            if (selectNode.length === 0){
                var selectHtml= "<select id=\"costCodeListSelect_"+args.id+"\"></select>";
                $("#empCostCodeListTable tbody>tr:last").find('td')[0].epmty().append(selectHtml);
                optionsContainer="costCodeListSelect_"+args.id;  
            }
          $(optionsContainer).empty().append(optionsHtml);
          $(optionsContainer).find("option").filter(function(){
              return $(this).text()==selectedName; 
          }).attr("selected","selected"); 
        } else {
            // create this only once via ajax
            adminSidebarController.populateOptions(args);
        }
    };

    return ({
        initTable : initTable,
        tableNode : tableNode,
        addrow: appendEmptyLastRow,
        populateOptions: populateOptions,
        populateTable : populateTable,
        getAllCostCodeForEmp : getAllCostCodeForEmp,
        saveCostCodeItem:saveCostCodeItem,
        delCostCodeItem:delCostCodeItem,
        saveTable:saveTable,
        tableNode:tableNode
    });
}();

$(document).ready(function() {
    tableNode=empCostCodeController.tableNode;
    $(function() {
        $('#addProjectRow').click(function(e){
            empCostCodeController.addrow();
        });
        $('#projectsToggle').click(function(e) {
            // TODO check for employee exist ?
            $("div#projects").collapse("toggle");
        });
        tableNode.on("click",".saveCostCodeItem", function(e){
            empCostCodeController.saveCostCodeItem(e); 
        });
        tableNode.on("click",".delCostCodeItem",function(e){
            empCostCodeController.delCostCodeItem(e); 
        });
        $('#saveTable').on("click",function(e){
            empCostCodeController.saveTable(e); 
        });
        // dummy test
        $("#fillDummyData").on("click",function(e){
            //empCostCodeController.populateTable(); 
            empCostCodeController.getAllCostCodeForEmp(e);       
        });
    });
});