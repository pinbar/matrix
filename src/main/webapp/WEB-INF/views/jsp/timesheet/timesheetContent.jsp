<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="errorMessages" class="error">${error}</div>
<form:form data-id="${timesheet.id}" data-status="${timesheet.status}"
    id="timesheet" class="form-vertical" method="post"
    modelAttribute="timesheet"
    action="${pageContext.request.contextPath}/timesheet/save">
    <form:errors class="error"></form:errors>
    <div id="errorPlaceHolder" class="hide error"></div>
    <table id="timesheetTable" class="table  table-striped cf rt">
        <thead>
            <tr>
                <th>Cost Center</th>
                <th class="monHrsTh">Mon</th>
                <th class="tueHrsTh">Tue</th>
                <th class="wedHrsTh">Wed</th>
                <th class="thuHrsTh">Thu</th>
                <th class="friHrsTh">Fri</th>
                <th class="satHrsTh">Sat</th>
                <th class="sunHrsTh">Sun</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${timesheet != null}">
                <p>
                  Week ending: <strong>${timesheet.weekEnding} </strong> | Employee ID: <strong>${timesheet.employeeId}</strong>
                </p>
                <form:hidden path="weekEnding"/>
                <form:hidden path="id" />
                <form:hidden path="employeeId"/>
                <input type="hidden" id="status" value= "${timesheet.status}">

                <c:forEach items="${timesheet.tsCostCenters}"
                    varStatus="status" var="tsCostCenters">
                    <tr
                        data-costcode="${timesheet.tsCostCenters[status.index].costCode}">
                        <td><c:choose>
                                <c:when test="${not empty costCenters}">
                                    <form:select
                                        path="tsCostCenters[${status.index}].costCode"
                                        class="input-sm">
                                        <form:option value=""
                                            label="Please select.."
                                            disabled="true"></form:option>
                                        <form:options
                                            items="${costCenters}"
                                            itemValue="costCode"
                                            itemLabel="name" />
                                    </form:select>
                                </c:when>
                                <c:otherwise>
                                                  HEY! SET UP COST CENTERS CORRECTLY      
                                              </c:otherwise>
                            </c:choose>
                            <div>
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].costCode"></form:errors>
                            </div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].monday.hours"
                                type="number"
                                class="form-control timesheetHours monHrs" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].monday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].monday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].monday.costCode" />
                            <div class="msg hide"></div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].tuesday.hours"
                                type="number"
                                class="form-control timesheetHours tueHrs" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].tuesday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].tuesday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].tuesday.costCode" />
                            <div class="msg hide"></div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].wednesday.hours"
                                type="number"
                                class="form-control timesheetHours wedHrs" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].wednesday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].wednesday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].wednesday.costCode" />
                            <div class="msg hide"></div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].thursday.hours"
                                type="number"
                                class="form-control timesheetHours thuHrs" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].thursday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].thursday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].thursday.costCode" />
                            <div class="msg hide"></div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].friday.hours"
                                type="number"
                                class="form-control timesheetHours friHrs" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].friday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].friday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].friday.costCode" />
                            <div class="msg hide"></div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].saturday.hours"
                                type="number"
                                class="form-control timesheetHours weekend satHrs" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].saturday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].saturday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].saturday.costCode" />
                            <div class="msg hide"></div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].sunday.hours"
                                type="number"
                                class="form-control timesheetHours weekend sunHrs" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].sunday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].sunday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].sunday.costCode" />
                            <div class="msg hide"></div></td>
                        <td><a class="delCostCodeRow"
                            href="${pageContext.request.contextPath}/timesheet/deleteCostCodeRow?timesheetId=${timesheet.id}&costCode=${timesheet.tsCostCenters[status.index].costCode}&weekEnding=${timesheet.weekEnding}">
                                <span class="glyphicon glyphicon-trash"></span>
                        </a></td>
                    </tr>
                </c:forEach>

            </c:if>
        </tbody>
    </table>
    <div class="clear"></div>
    <div class="control-group">
        <a id="addRow"
            href="${pageContext.request.contextPath}/timesheet/addCostCodeRow?timesheetId=${timesheet.id}&weekEnding=${timesheet.weekEnding}">
            <input id="addCostCodeBtn" type="button"
            class="btn btn-info" value="Add Row">
        </a> <input type="button" id="saveTimesheet"
            class="submitTimesheet btn btn-success" value="Save">
        <input type="button" id="submitTimesheet"
            class="submitTimesheet btn btn-danger"
            value="Submit Timesheet">
        <button id="activateTimesheet" type="button"
            class="btn btn-primary">Edit Timesheet</button>
        <div>

            <br> <input type="button" value="Show/Hide Attachments"
                class="btn btn-default" id="toggleAttachments" />
        </div>
    </div>
</form:form>
<div id="attachments" class="control-group collapse">
    <form:form id="fileupload" class="form-horizontal" method="post"
        modelAttribute="timesheet"
        action="${pageContext.request.contextPath}/attachment/timesheet"
        enctype="multipart/form-data">
        <input type="hidden" name="timesheetId" id="timesheetId"
            value="${timesheet.id}" />
        <div class="fileupload-buttonbar">
            <div class="spacer"></div>
            <div class="col-12 col-sm-10 col-lg-10">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button"> <i
                    class="icon-plus icon-white"></i> <span>Add
                        files...</span> <input type="file" name="file" multiple>
                </span>
                <!-- Auto upload enabled -->
                <!-- <button type="submit"
                                    class="btn btn-primary start">
                                    <i class="icon-upload icon-white"></i>
                                    <span>Start upload</span>
                                </button> -->
                <button type="reset" class="btn btn-warning cancel">
                    <i class="icon-ban-circle icon-white"></i> <span>Cancel
                        upload</span>
                </button>
                <button type="button" class="btn btn-danger delete">
                    <i class="icon-trash icon-white"></i> <span>Delete</span>
                </button>
                <input type="checkbox" class="toggle">
                <!-- The loading indicator is shown during file processing -->
                <span class="fileupload-loading"></span>
            </div>
            <!-- The global progress information -->
            <div class="col-5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div
                    class="progress progress-success progress-striped active"
                    role="progressbar" aria-valuemin="0"
                    aria-valuemax="100">
                    <div class="bar" style="width: 0%;"></div>
                </div>
                <!-- The extended global progress information -->
                <!-- <div class="progress-extended"></div>  -->
            </div>
        </div>
        <!-- The table listing the files available for upload/download -->
        <table id="filePresentation" role="presentation"
            class="table table-striped">
            <tbody class="files" data-toggle="modal-gallery"
                data-target="#modal-gallery"></tbody>
        </table>

    </form:form>
</div>
<!-- end The template to display files available for upload -->

